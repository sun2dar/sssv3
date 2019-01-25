import { ITransaksi } from 'app/shared/model//transaksi.model';

export interface ITBongkar {
  id?: number;
  rateUpah?: number;
  volume?: number;
  transaksi?: ITransaksi;
}

export const defaultValue: Readonly<ITBongkar> = {};
