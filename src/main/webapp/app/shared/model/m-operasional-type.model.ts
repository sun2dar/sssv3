import { Moment } from 'moment';
import { ITOperasional } from 'app/shared/model//t-operasional.model';
import { IUser } from 'app/shared/model/user.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMOperasionalType {
  id?: number;
  nama?: string;
  deskripsi?: any;
  status?: Status;
  createdOn?: Moment;
  toperasionals?: ITOperasional[];
  createdby?: IUser;
}

export const defaultValue: Readonly<IMOperasionalType> = {};
