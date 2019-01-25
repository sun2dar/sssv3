import { IMLogCategory } from 'app/shared/model//m-log-category.model';
import { ITransaksi } from 'app/shared/model//transaksi.model';

export const enum InOut {
  IN = 'IN',
  OUT = 'OUT'
}

export interface ITLog {
  id?: number;
  panjang?: number;
  qty?: number;
  volume?: number;
  hargaBeli?: number;
  hargaTotal?: number;
  inout?: InOut;
  mlogcat?: IMLogCategory;
  transaksi?: ITransaksi;
}

export const defaultValue: Readonly<ITLog> = {};
