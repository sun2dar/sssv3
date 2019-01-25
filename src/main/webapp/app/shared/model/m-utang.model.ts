import { Moment } from 'moment';
import { ITUtang } from 'app/shared/model//t-utang.model';
import { ITransaksi } from 'app/shared/model//transaksi.model';

export const enum TIPEUTANG {
  HUTANG = 'HUTANG',
  PIUTANG = 'PIUTANG'
}

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface IMUtang {
  id?: number;
  nominal?: number;
  sisa?: number;
  duedate?: Moment;
  tipe?: TIPEUTANG;
  status?: Status;
  tutangs?: ITUtang[];
  transaksi?: ITransaksi;
}

export const defaultValue: Readonly<IMUtang> = {};
