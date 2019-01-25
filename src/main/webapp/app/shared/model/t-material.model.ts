import { IMMaterial } from 'app/shared/model//m-material.model';
import { ITransaksi } from 'app/shared/model//transaksi.model';

export const enum InOut {
  IN = 'IN',
  OUT = 'OUT'
}

export interface ITMaterial {
  id?: number;
  qty?: number;
  hargaBeli?: number;
  hargaTotal?: number;
  inout?: InOut;
  mmaterial?: IMMaterial;
  transaksi?: ITransaksi;
}

export const defaultValue: Readonly<ITMaterial> = {};
