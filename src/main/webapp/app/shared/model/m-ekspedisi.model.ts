import { Moment } from 'moment';
import { ITransaksi } from 'app/shared/model//transaksi.model';
import { IUser } from 'app/shared/model/user.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMEkspedisi {
  id?: number;
  nama?: string;
  telepon?: string;
  mobilephone?: string;
  alamat?: any;
  status?: Status;
  createdOn?: Moment;
  transaksis?: ITransaksi[];
  createdby?: IUser;
}

export const defaultValue: Readonly<IMEkspedisi> = {};
