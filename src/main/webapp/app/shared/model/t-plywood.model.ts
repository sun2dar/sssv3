import { IMPlywood } from 'app/shared/model//m-plywood.model';
import { ITransaksi } from 'app/shared/model//transaksi.model';

export const enum InOut {
  IN = 'IN',
  OUT = 'OUT'
}

export interface ITPlywood {
  id?: number;
  qty?: number;
  volume?: number;
  hargaBeli?: number;
  hargaTotal?: number;
  inout?: InOut;
  mplywood?: IMPlywood;
  transaksi?: ITransaksi;
}

export const defaultValue: Readonly<ITPlywood> = {};
