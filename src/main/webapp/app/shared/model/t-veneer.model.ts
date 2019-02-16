import { IMVeneer } from 'app/shared/model//m-veneer.model';
import { ITransaksi } from 'app/shared/model//transaksi.model';

export const enum InOut {
  IN = 'IN',
  OUT = 'OUT'
}

export interface ITVeneer {
  id?: number;
  qty?: number;
  volume?: number;
  hargaBeli?: number;
  hargaTotal?: number;
  inout?: InOut;
  mveneer?: IMVeneer;
  transaksi?: ITransaksi;
}

export const defaultValue: Readonly<ITVeneer> = {};
