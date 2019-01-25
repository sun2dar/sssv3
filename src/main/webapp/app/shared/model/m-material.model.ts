import { Moment } from 'moment';
import { ITMaterial } from 'app/shared/model//t-material.model';
import { IUser } from 'app/shared/model/user.model';
import { IMSatuan } from 'app/shared/model//m-satuan.model';
import { IMMaterialType } from 'app/shared/model//m-material-type.model';

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMMaterial {
  id?: number;
  nama?: string;
  deskripsi?: any;
  harga?: number;
  qty?: number;
  status?: Status;
  createdOn?: Moment;
  tmaterials?: ITMaterial[];
  createdby?: IUser;
  satuan?: IMSatuan;
  materialtype?: IMMaterialType;
}

export const defaultValue: Readonly<IMMaterial> = {};
