import { Moment } from 'moment';
import { IUser } from 'app/shared/model/user.model';
import { IMOperasionalType } from 'app/shared/model//m-operasional-type.model';

export interface ITOperasional {
  id?: number;
  tanggal?: Moment;
  nominal?: number;
  createdOn?: Moment;
  createdby?: IUser;
  moperasionaltype?: IMOperasionalType;
}

export const defaultValue: Readonly<ITOperasional> = {};
