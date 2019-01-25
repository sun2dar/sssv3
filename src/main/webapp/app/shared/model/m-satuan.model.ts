import { Moment } from 'moment';
import { IMMaterial } from 'app/shared/model//m-material.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMSatuan {
  id?: number;
  nama?: string;
  deskripsi?: string;
  status?: Status;
  createdOn?: Moment;
  materials?: IMMaterial[];
}

export const defaultValue: Readonly<IMSatuan> = {};
