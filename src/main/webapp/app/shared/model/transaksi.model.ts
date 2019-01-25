import { Moment } from 'moment';
import { ITLog } from 'app/shared/model//t-log.model';
import { ITVeneer } from 'app/shared/model//t-veneer.model';
import { ITPlywood } from 'app/shared/model//t-plywood.model';
import { ITMaterial } from 'app/shared/model//t-material.model';
import { ITBongkar } from 'app/shared/model//t-bongkar.model';
import { IMUtang } from 'app/shared/model//m-utang.model';
import { IUser } from 'app/shared/model/user.model';
import { IMShift } from 'app/shared/model//m-shift.model';
import { IMSupplier } from 'app/shared/model//m-supplier.model';
import { IMCustomer } from 'app/shared/model//m-customer.model';
import { IMPaytype } from 'app/shared/model//m-paytype.model';
import { IMEkspedisi } from 'app/shared/model//m-ekspedisi.model';
import { IMTeam } from 'app/shared/model//m-team.model';
import { IMPajak } from 'app/shared/model//m-pajak.model';

export const enum TransaksiType {
  PEMBELIAN = 'PEMBELIAN',
  PENJUALAN = 'PENJUALAN',
  PRODUKSI = 'PRODUKSI',
  STOCKOPNAME = 'STOCKOPNAME',
  REFUND = 'REFUND'
}

export const enum TransaksiCategory {
  LOG = 'LOG',
  VENEER = 'VENEER',
  PLYWOOD = 'PLYWOOD',
  MATERIAL = 'MATERIAL'
}

export const enum Status {
  ACT = 'ACT',
  DIS = 'DIS',
  DEL = 'DEL'
}

export interface ITransaksi {
  id?: number;
  tipe?: TransaksiType;
  category?: TransaksiCategory;
  tanggal?: Moment;
  invoiceno?: string;
  invoicefileContentType?: string;
  invoicefile?: any;
  nopol?: string;
  status?: Status;
  createdOn?: Moment;
  tlogs?: ITLog[];
  tveneers?: ITVeneer[];
  tplywoods?: ITPlywood[];
  tmaterials?: ITMaterial[];
  tbongkars?: ITBongkar[];
  mutangs?: IMUtang[];
  createdby?: IUser;
  shift?: IMShift;
  supplier?: IMSupplier;
  customer?: IMCustomer;
  paytype?: IMPaytype;
  ekpedisi?: IMEkspedisi;
  team?: IMTeam;
  pajak?: IMPajak;
}

export const defaultValue: Readonly<ITransaksi> = {};
