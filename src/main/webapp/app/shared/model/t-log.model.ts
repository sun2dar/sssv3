import { ITransaksi } from 'app/shared/model//transaksi.model';
import { IMLog } from 'app/shared/model//m-log.model';

export const enum InOut {
  IN = 'IN',
  OUT = 'OUT'
}

export interface ITLog {
  id?: number;
  qty?: number;
  volume?: number;
  hargaBeli?: number;
  hargaTotal?: number;
  inout?: InOut;
  transaksi?: ITransaksi;
  mlog?: IMLog;
}

export const defaultValue: Readonly<ITLog> = {};
