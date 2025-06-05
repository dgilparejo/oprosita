import Keycloak from 'keycloak-js';
import { Injectable } from '@angular/core';
import {map, Observable} from 'rxjs';
import {Usuario} from '../api';
import {HttpClient} from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class KeycloakService {
  constructor(private http: HttpClient) {}
  private keycloak!: Keycloak;
  async init(): Promise<void> {
    const storedToken = localStorage.getItem('kc_token');
    const storedRefreshToken = localStorage.getItem('kc_refreshToken');

    this.keycloak = new Keycloak({
      url: 'http://localhost:8081',
      realm: 'oprosita',
      clientId: 'angular-app'
    });

    await this.keycloak.init({
      onLoad: 'check-sso',
      pkceMethod: 'S256',
      checkLoginIframe: false,
      scope: 'openid offline_access',
      token: storedToken || undefined,
      refreshToken: storedRefreshToken || undefined
    });

    if (!this.keycloak.authenticated) {
      try {
        await this.keycloak.login({ redirectUri: window.location.origin });
      } catch (err) {
        console.error('Login failed', err);
      }
    } else {
      localStorage.setItem('kc_token', this.keycloak.token!);
      localStorage.setItem('kc_refreshToken', this.keycloak.refreshToken!);
    }

    this.keycloak.onTokenExpired = () => {
      this.keycloak.updateToken(30).then(() => {
        localStorage.setItem('kc_token', this.keycloak.token!);
        localStorage.setItem('kc_refreshToken', this.keycloak.refreshToken!);
      }).catch(() => this.logout());
    };

    setInterval(() => {
      this.keycloak.updateToken(60).then(refreshed => {
        if (refreshed) {
          localStorage.setItem('kc_token', this.keycloak.token!);
          localStorage.setItem('kc_refreshToken', this.keycloak.refreshToken!);
        }
      }).catch(() => this.logout());
    }, 300000);
  }


  get token(): string {
    return this.keycloak.token!;
  }

  get tokenParsed(): Keycloak.KeycloakTokenParsed | undefined {
    return this.keycloak?.tokenParsed;
  }

  hasRole(role: string): boolean {
    return this.keycloak.tokenParsed?.realm_access?.roles.includes(role) || false;
  }

  getUserId(): string | undefined {
    return this.tokenParsed?.sub;
  }

  getGrupoIdFromToken(): Observable<number> {
    const userId = this.getUserId();
    return this.http.get<Usuario>(`/api/v1/usuarios/${userId}`).pipe(
      map(usuario => (usuario as any).grupoId)
    );
  }

  getToken(): string {
    return <string>this.keycloak.token;
  }

  getProfesorPorGrupo(grupoId: number): Observable<Usuario> {
    return this.http.get<Usuario>(`/api/v1/grupos/${grupoId}/profesor`);
  }


  logout(): void {
    localStorage.removeItem('kc_token');
    localStorage.removeItem('kc_refreshToken');
    this.keycloak.logout({ redirectUri: window.location.origin });
  }
}
