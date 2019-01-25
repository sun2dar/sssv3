export const enum KasType {
  MASUK = 'MASUK',
  DELIVERY = 'DELIVERY',
  BELILOG = 'BELILOG',
  JUALLOG = 'JUALLOG',
  BELIVENEER = 'BELIVENEER',
  JUALVENEER = 'JUALVENEER',
  BELIPLAYWOOD = 'BELIPLAYWOOD',
  JUALPLAYWOOD = 'JUALPLAYWOOD',
  OPERASIONAL = 'OPERASIONAL',
  GAJI = 'GAJI',
  HUTANGPIUTANG = 'HUTANGPIUTANG',
  BONGKAR = 'BONGKAR'
}

export interface ITKas {
  id?: number;
  tipekas?: KasType;
  nominal?: number;
  deskripsi?: any;
  objectid?: number;
}

export const defaultValue: Readonly<ITKas> = {};
