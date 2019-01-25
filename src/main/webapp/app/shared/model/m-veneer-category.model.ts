import { Moment } from 'moment';
import { IMVeneer } from 'app/shared/model//m-veneer.model';
import { ITVeneer } from 'app/shared/model//t-veneer.model';
import { IUser } from 'app/shared/model/user.model';

export const enum VeneerType {
  BASAH = 'BASAH',
  KERING = 'KERING',
  REPAIR = 'REPAIR'
}

export const enum VeneerSubType {
  FB = 'FB',
  LC = 'LC',
  SC = 'SC'
}

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMVeneerCategory {
  id?: number;
  nama?: string;
  deskripsi?: any;
  tebal?: number;
  panjang?: number;
  lebar?: number;
  hargaBeli?: number;
  hargaJual?: number;
  type?: VeneerType;
  subtype?: VeneerSubType;
  status?: Status;
  createdOn?: Moment;
  mveneers?: IMVeneer[];
  tveneers?: ITVeneer[];
  createdby?: IUser;
}

export const defaultValue: Readonly<IMVeneerCategory> = {};
