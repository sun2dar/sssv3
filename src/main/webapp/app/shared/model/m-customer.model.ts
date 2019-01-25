import { Moment } from 'moment';
import { ITransaksi } from 'app/shared/model//transaksi.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMCustomer {
  id?: number;
  nama?: string;
  telepon?: number;
  mobilephone?: number;
  alamat?: any;
  status?: Status;
  createdOn?: Moment;
  transaksis?: ITransaksi[];
}

export const defaultValue: Readonly<IMCustomer> = {};
