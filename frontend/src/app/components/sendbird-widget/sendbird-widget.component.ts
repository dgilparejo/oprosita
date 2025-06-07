import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { KeycloakService } from '../../services/keycloak.service';
import SendbirdChat from '@sendbird/chat';
import {
  GroupChannelModule,
  GroupChannel,
  GroupChannelCreateParams,
  GroupChannelHandler,
  SendbirdGroupChat, QueryType
} from '@sendbird/chat/groupChannel';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsuariosService } from '../../api/api/usuarios.service';
import { Usuario } from '../../api/model/usuario';

@Component({
  selector: 'app-sendbird-widget',
  templateUrl: './sendbird-widget.component.html',
  styleUrls: ['./sendbird-widget.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class SendbirdWidgetComponent implements OnInit, OnDestroy {
  private keycloakService = inject(KeycloakService);
  private usuariosService = inject(UsuariosService);

  public usersToChat: Array<Usuario> | undefined = [];
  public selectedUser: Usuario | null = null;
  public messages: { sender: string; content: string; isOwn: boolean }[] = [];
  public messageInput = '';

  private sb!: SendbirdGroupChat;
  private channel: GroupChannel | null = null;
  private userId: string = '';
  private nickname: string = '';
  private isProfesor: boolean = false;

  async ngOnInit(): Promise<void> {
    this.userId = this.keycloakService.getUserId() || 'guest';
    this.nickname = this.keycloakService.tokenParsed?.['preferred_username'] || 'Invitado';
    this.isProfesor = this.keycloakService.hasRole('profesor');

    this.sb = await SendbirdChat.init({
      appId: '69010198-378C-47E0-8FD5-CA23BE426BA1',
      modules: [new GroupChannelModule()]
    }) as SendbirdGroupChat;

    await this.sb.connect(this.userId);
    await this.sb.updateCurrentUserInfo({ nickname: this.nickname });

    // Carga la lista de usuarios disponibles para chatear
    const tipo = this.isProfesor ? 'alumno' : 'profesor';
    this.usersToChat = await this.usuariosService.obtenerUsuarios(tipo).toPromise();
    console.log('Usuarios disponibles para chatear:', this.usersToChat);
  }

  async selectUser(user: Usuario): Promise<void> {
    this.selectedUser = user;
    this.channel = await this.ensure1to1Channel(String(user.id));

    const messages = await this.channel.getMessagesByTimestamp(Date.now(), {
      prevResultSize: 20,
      nextResultSize: 0
    });

    this.messages = messages
      .filter(m => (m as any).isUserMessage?.())
      .map(m => {
        const msg = m as any;
        return {
          sender: msg.sender?.nickname || 'Desconocido',
          content: msg.message,
          isOwn: msg.sender?.userId === this.userId
        };
      });

    const handler = new GroupChannelHandler();
    handler.onMessageReceived = (_, message) => {
      const msg = message as any;
      if (msg.isUserMessage?.() && this.selectedUser?.id === msg.sender?.userId || msg.sender?.userId === this.userId) {
        this.messages.push({
          sender: msg.sender?.nickname || 'Desconocido',
          content: msg.message,
          isOwn: msg.sender?.userId === this.userId
        });
      }
    };
    this.sb.groupChannel.addGroupChannelHandler('chat-handler', handler);
  }

  ngOnDestroy(): void {
    this.sb?.groupChannel.removeGroupChannelHandler?.('chat-handler');
  }

  async sendMessage(): Promise<void> {
    if (this.channel && this.messageInput.trim()) {
      const content = this.messageInput;
      this.messageInput = ''; // Limpia input antes para UX más rápida

      const sent = await this.channel.sendUserMessage({ message: content });

      // Usa los datos enviados para mostrar inmediatamente
      this.messages.push({
        sender: this.nickname || 'Tú',
        content: content,
        isOwn: true
      });
    }
  }

  private async ensure1to1Channel(otherId: string): Promise<GroupChannel> {
    const query = this.sb.groupChannel.createMyGroupChannelListQuery({
      includeEmpty: true,
      userIdsFilter: {
        userIds: [this.userId, otherId],
        includeMode: true,
        queryType: QueryType.OR // cambia a OR para obtener todos los canales compartidos
      }
    });

    const channels = await query.next();

    // Buscar un canal 1:1 existente entre estos dos
    for (const ch of channels) {
      const members = ch.members.map((m: any) => m.userId);
      if (members.length === 2 && members.includes(this.userId) && members.includes(otherId)) {
        return ch;
      }
    }

    // Si no existe, créalo
    const params: GroupChannelCreateParams = {
      invitedUserIds: [otherId],
      isDistinct: true
    };

    return await this.sb.groupChannel.createChannel(params);
  }
}
