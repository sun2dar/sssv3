import { Moment } from 'moment';
import { IUser } from 'app/shared/model/user.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMMessage {
  id?: number;
  message?: string;
  status?: Status;
  createdOn?: Moment;
  modifiedOn?: Moment;
  createdby?: IUser;
  modifiedby?: IUser;
}

export const defaultValue: Readonly<IMMessage> = {};
