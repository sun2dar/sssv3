import { Moment } from 'moment';
import { IMMaterial } from 'app/shared/model//m-material.model';
import { IUser } from 'app/shared/model/user.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMMaterialType {
  id?: number;
  nama?: string;
  deskripsi?: any;
  status?: Status;
  createdOn?: Moment;
  materials?: IMMaterial[];
  createdby?: IUser;
}

export const defaultValue: Readonly<IMMaterialType> = {};
