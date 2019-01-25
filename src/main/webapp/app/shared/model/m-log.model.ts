import { Moment } from 'moment';
import { IUser } from 'app/shared/model/user.model';
import { IMLogCategory } from 'app/shared/model//m-log-category.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMLog {
  id?: number;
  nama?: string;
  diameter?: number;
  panjang?: number;
  hargaBeli?: number;
  qty?: number;
  status?: Status;
  createdOn?: Moment;
  createdby?: IUser;
  mlogcat?: IMLogCategory;
}

export const defaultValue: Readonly<IMLog> = {};
