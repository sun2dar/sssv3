import { Moment } from 'moment';
import { IMUtang } from 'app/shared/model//m-utang.model';

export interface ITUtang {
  id?: number;
  nominal?: number;
  deskripsi?: any;
  createdOn?: Moment;
  mutang?: IMUtang;
}

export const defaultValue: Readonly<ITUtang> = {};
