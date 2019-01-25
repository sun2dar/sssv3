import { Moment } from 'moment';
import { IUser } from 'app/shared/model/user.model';
import { IMVeneerCategory } from 'app/shared/model//m-veneer-category.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMVeneer {
  id?: number;
  hargaBeli?: number;
  qty?: number;
  qtyProduksi?: number;
  status?: Status;
  createdOn?: Moment;
  createdby?: IUser;
  veneercategory?: IMVeneerCategory;
}

export const defaultValue: Readonly<IMVeneer> = {};
