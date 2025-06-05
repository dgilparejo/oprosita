import { Noticia } from '../api';

export interface NoticiaConPlank extends Noticia {
  texto: string;
  fechaCreacion: string;
  tipoDestinatario: 'profesor' | 'alumno';
}
