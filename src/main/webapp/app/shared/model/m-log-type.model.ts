import { Moment } from 'moment';
import { IMLogCategory } from 'app/shared/model//m-log-category.model';
import { IUser } from 'app/shared/model/user.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMLogType {
  id?: number;
  nama?: string;
  deskripsi?: any;
  status?: Status;
  createdOn?: Moment;
  mlogs?: IMLogCategory[];
  createdby?: IUser;
}

export const defaultValue: Readonly<IMLogType> = {};
