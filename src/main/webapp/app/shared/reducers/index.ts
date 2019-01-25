import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import mSupplier, {
  MSupplierState
} from 'app/entities/m-supplier/m-supplier.reducer';
// prettier-ignore
import mTeam, {
  MTeamState
} from 'app/entities/m-team/m-team.reducer';
// prettier-ignore
import mShift, {
  MShiftState
} from 'app/entities/m-shift/m-shift.reducer';
// prettier-ignore
import mEkspedisi, {
  MEkspedisiState
} from 'app/entities/m-ekspedisi/m-ekspedisi.reducer';
// prettier-ignore
import mCustomer, {
  MCustomerState
} from 'app/entities/m-customer/m-customer.reducer';
// prettier-ignore
import mPaytype, {
  MPaytypeState
} from 'app/entities/m-paytype/m-paytype.reducer';
// prettier-ignore
import mLogType, {
  MLogTypeState
} from 'app/entities/m-log-type/m-log-type.reducer';
// prettier-ignore
import mLogCategory, {
  MLogCategoryState
} from 'app/entities/m-log-category/m-log-category.reducer';
// prettier-ignore
import mLog, {
  MLogState
} from 'app/entities/m-log/m-log.reducer';
// prettier-ignore
import tLog, {
  TLogState
} from 'app/entities/t-log/t-log.reducer';
// prettier-ignore
import mVeneerCategory, {
  MVeneerCategoryState
} from 'app/entities/m-veneer-category/m-veneer-category.reducer';
// prettier-ignore
import mVeneer, {
  MVeneerState
} from 'app/entities/m-veneer/m-veneer.reducer';
// prettier-ignore
import tVeneer, {
  TVeneerState
} from 'app/entities/t-veneer/t-veneer.reducer';
// prettier-ignore
import mPlywoodGrade, {
  MPlywoodGradeState
} from 'app/entities/m-plywood-grade/m-plywood-grade.reducer';
// prettier-ignore
import mPlywoodCategory, {
  MPlywoodCategoryState
} from 'app/entities/m-plywood-category/m-plywood-category.reducer';
// prettier-ignore
import mPlywood, {
  MPlywoodState
} from 'app/entities/m-plywood/m-plywood.reducer';
// prettier-ignore
import tPlywood, {
  TPlywoodState
} from 'app/entities/t-plywood/t-plywood.reducer';
// prettier-ignore
import mSatuan, {
  MSatuanState
} from 'app/entities/m-satuan/m-satuan.reducer';
// prettier-ignore
import mMaterialType, {
  MMaterialTypeState
} from 'app/entities/m-material-type/m-material-type.reducer';
// prettier-ignore
import mMaterial, {
  MMaterialState
} from 'app/entities/m-material/m-material.reducer';
// prettier-ignore
import tMaterial, {
  TMaterialState
} from 'app/entities/t-material/t-material.reducer';
// prettier-ignore
import mKas, {
  MKasState
} from 'app/entities/m-kas/m-kas.reducer';
// prettier-ignore
import tKas, {
  TKasState
} from 'app/entities/t-kas/t-kas.reducer';
// prettier-ignore
import mConstant, {
  MConstantState
} from 'app/entities/m-constant/m-constant.reducer';
// prettier-ignore
import mOperasionalType, {
  MOperasionalTypeState
} from 'app/entities/m-operasional-type/m-operasional-type.reducer';
// prettier-ignore
import tOperasional, {
  TOperasionalState
} from 'app/entities/t-operasional/t-operasional.reducer';
// prettier-ignore
import mUtang, {
  MUtangState
} from 'app/entities/m-utang/m-utang.reducer';
// prettier-ignore
import tUtang, {
  TUtangState
} from 'app/entities/t-utang/t-utang.reducer';
// prettier-ignore
import transaksi, {
  TransaksiState
} from 'app/entities/transaksi/transaksi.reducer';
// prettier-ignore
import tBongkar, {
  TBongkarState
} from 'app/entities/t-bongkar/t-bongkar.reducer';
// prettier-ignore
import mMessage, {
  MMessageState
} from 'app/entities/m-message/m-message.reducer';
// prettier-ignore
import mPajak, {
  MPajakState
} from 'app/entities/m-pajak/m-pajak.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly mSupplier: MSupplierState;
  readonly mTeam: MTeamState;
  readonly mShift: MShiftState;
  readonly mEkspedisi: MEkspedisiState;
  readonly mCustomer: MCustomerState;
  readonly mPaytype: MPaytypeState;
  readonly mLogType: MLogTypeState;
  readonly mLogCategory: MLogCategoryState;
  readonly mLog: MLogState;
  readonly tLog: TLogState;
  readonly mVeneerCategory: MVeneerCategoryState;
  readonly mVeneer: MVeneerState;
  readonly tVeneer: TVeneerState;
  readonly mPlywoodGrade: MPlywoodGradeState;
  readonly mPlywoodCategory: MPlywoodCategoryState;
  readonly mPlywood: MPlywoodState;
  readonly tPlywood: TPlywoodState;
  readonly mSatuan: MSatuanState;
  readonly mMaterialType: MMaterialTypeState;
  readonly mMaterial: MMaterialState;
  readonly tMaterial: TMaterialState;
  readonly mKas: MKasState;
  readonly tKas: TKasState;
  readonly mConstant: MConstantState;
  readonly mOperasionalType: MOperasionalTypeState;
  readonly tOperasional: TOperasionalState;
  readonly mUtang: MUtangState;
  readonly tUtang: TUtangState;
  readonly transaksi: TransaksiState;
  readonly tBongkar: TBongkarState;
  readonly mMessage: MMessageState;
  readonly mPajak: MPajakState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  mSupplier,
  mTeam,
  mShift,
  mEkspedisi,
  mCustomer,
  mPaytype,
  mLogType,
  mLogCategory,
  mLog,
  tLog,
  mVeneerCategory,
  mVeneer,
  tVeneer,
  mPlywoodGrade,
  mPlywoodCategory,
  mPlywood,
  tPlywood,
  mSatuan,
  mMaterialType,
  mMaterial,
  tMaterial,
  mKas,
  tKas,
  mConstant,
  mOperasionalType,
  tOperasional,
  mUtang,
  tUtang,
  transaksi,
  tBongkar,
  mMessage,
  mPajak,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
