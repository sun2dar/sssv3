import { IMVeneerCategory } from 'app/shared/model//m-veneer-category.model';
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
  veneercategory?: IMVeneerCategory;
  transaksi?: ITransaksi;
}

export const defaultValue: Readonly<ITVeneer> = {};
