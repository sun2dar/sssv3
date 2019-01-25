import { Moment } from 'moment';
import { ITransaksi } from 'app/shared/model//transaksi.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMPajak {
  id?: number;
  nama?: string;
  deskripsi?: any;
  nominal?: number;
  status?: Status;
  createdOn?: Moment;
  transaksis?: ITransaksi[];
}

export const defaultValue: Readonly<IMPajak> = {};
