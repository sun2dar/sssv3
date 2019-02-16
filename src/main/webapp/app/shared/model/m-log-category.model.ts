import { Moment } from 'moment';
import { IMLog } from 'app/shared/model//m-log.model';
import { IUser } from 'app/shared/model/user.model';
import { IMLogType } from 'app/shared/model//m-log-type.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMLogCategory {
  id?: number;
  nama?: string;
  diameter1?: number;
  diameter2?: number;
  hargaBeli?: number;
  hargaJual?: number;
  status?: Status;
  createdOn?: Moment;
  mlogs?: IMLog[];
  createdby?: IUser;
  mlogtype?: IMLogType;
}

export const defaultValue: Readonly<IMLogCategory> = {};
