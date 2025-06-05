import {Novedad} from '../api';

export interface NovedadConArchivo extends Novedad {
  archivoId?: number;
}
