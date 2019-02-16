import { Moment } from 'moment';
import { ITLog } from 'app/shared/model//t-log.model';
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
  tlogs?: ITLog[];
  createdby?: IUser;
  mlogcat?: IMLogCategory;
}

export const defaultValue: Readonly<IMLog> = {};
