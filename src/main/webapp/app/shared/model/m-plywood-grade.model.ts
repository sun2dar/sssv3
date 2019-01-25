import { Moment } from 'moment';
import { IMPlywoodCategory } from 'app/shared/model//m-plywood-category.model';
import { IUser } from 'app/shared/model/user.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMPlywoodGrade {
  id?: number;
  nama?: string;
  deskripsi?: any;
  status?: Status;
  createdOn?: Moment;
  plywoodcategories?: IMPlywoodCategory[];
  createdby?: IUser;
}

export const defaultValue: Readonly<IMPlywoodGrade> = {};
