import { Moment } from 'moment';
import { IMPlywood } from 'app/shared/model//m-plywood.model';
import { ITPlywood } from 'app/shared/model//t-plywood.model';
import { IUser } from 'app/shared/model/user.model';
import { IMPlywoodGrade } from 'app/shared/model//m-plywood-grade.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMPlywoodCategory {
  id?: number;
  nama?: string;
  deskripsi?: any;
  tebal?: number;
  panjang?: number;
  lebar?: number;
  hargaBeli?: number;
  hargaJual?: number;
  status?: Status;
  createdOn?: Moment;
  mplywoods?: IMPlywood[];
  tplywoods?: ITPlywood[];
  createdby?: IUser;
  plywoodgrade?: IMPlywoodGrade;
}

export const defaultValue: Readonly<IMPlywoodCategory> = {};
