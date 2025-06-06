import { Noticia } from 'opro-angular-client';

export interface NoticiaConPlank extends Noticia {
  texto: string;
  fechaCreacion: string;
  tipoDestinatario: 'profesor' | 'alumno';
}
