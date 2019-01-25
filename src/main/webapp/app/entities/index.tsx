import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MSupplier from './m-supplier';
import MTeam from './m-team';
import MShift from './m-shift';
import MEkspedisi from './m-ekspedisi';
import MCustomer from './m-customer';
import MPaytype from './m-paytype';
import MLogType from './m-log-type';
import MLogCategory from './m-log-category';
import MLog from './m-log';
import TLog from './t-log';
import MVeneerCategory from './m-veneer-category';
import MVeneer from './m-veneer';
import TVeneer from './t-veneer';
import MPlywoodGrade from './m-plywood-grade';
import MPlywoodCategory from './m-plywood-category';
import MPlywood from './m-plywood';
import TPlywood from './t-plywood';
import MSatuan from './m-satuan';
import MMaterialType from './m-material-type';
import MMaterial from './m-material';
import TMaterial from './t-material';
import MKas from './m-kas';
import TKas from './t-kas';
import MConstant from './m-constant';
import MOperasionalType from './m-operasional-type';
import TOperasional from './t-operasional';
import MUtang from './m-utang';
import TUtang from './t-utang';
import Transaksi from './transaksi';
import TBongkar from './t-bongkar';
import MMessage from './m-message';
import MPajak from './m-pajak';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/m-supplier`} component={MSupplier} />
      <ErrorBoundaryRoute path={`${match.url}/m-team`} component={MTeam} />
      <ErrorBoundaryRoute path={`${match.url}/m-shift`} component={MShift} />
      <ErrorBoundaryRoute path={`${match.url}/m-ekspedisi`} component={MEkspedisi} />
      <ErrorBoundaryRoute path={`${match.url}/m-customer`} component={MCustomer} />
      <ErrorBoundaryRoute path={`${match.url}/m-paytype`} component={MPaytype} />
      <ErrorBoundaryRoute path={`${match.url}/m-log-type`} component={MLogType} />
      <ErrorBoundaryRoute path={`${match.url}/m-log-category`} component={MLogCategory} />
      <ErrorBoundaryRoute path={`${match.url}/m-log`} component={MLog} />
      <ErrorBoundaryRoute path={`${match.url}/t-log`} component={TLog} />
      <ErrorBoundaryRoute path={`${match.url}/m-veneer-category`} component={MVeneerCategory} />
      <ErrorBoundaryRoute path={`${match.url}/m-veneer`} component={MVeneer} />
      <ErrorBoundaryRoute path={`${match.url}/t-veneer`} component={TVeneer} />
      <ErrorBoundaryRoute path={`${match.url}/m-plywood-grade`} component={MPlywoodGrade} />
      <ErrorBoundaryRoute path={`${match.url}/m-plywood-category`} component={MPlywoodCategory} />
      <ErrorBoundaryRoute path={`${match.url}/m-plywood`} component={MPlywood} />
      <ErrorBoundaryRoute path={`${match.url}/t-plywood`} component={TPlywood} />
      <ErrorBoundaryRoute path={`${match.url}/m-satuan`} component={MSatuan} />
      <ErrorBoundaryRoute path={`${match.url}/m-material-type`} component={MMaterialType} />
      <ErrorBoundaryRoute path={`${match.url}/m-material`} component={MMaterial} />
      <ErrorBoundaryRoute path={`${match.url}/t-material`} component={TMaterial} />
      <ErrorBoundaryRoute path={`${match.url}/m-kas`} component={MKas} />
      <ErrorBoundaryRoute path={`${match.url}/t-kas`} component={TKas} />
      <ErrorBoundaryRoute path={`${match.url}/m-constant`} component={MConstant} />
      <ErrorBoundaryRoute path={`${match.url}/m-operasional-type`} component={MOperasionalType} />
      <ErrorBoundaryRoute path={`${match.url}/t-operasional`} component={TOperasional} />
      <ErrorBoundaryRoute path={`${match.url}/m-utang`} component={MUtang} />
      <ErrorBoundaryRoute path={`${match.url}/t-utang`} component={TUtang} />
      <ErrorBoundaryRoute path={`${match.url}/transaksi`} component={Transaksi} />
      <ErrorBoundaryRoute path={`${match.url}/t-bongkar`} component={TBongkar} />
      <ErrorBoundaryRoute path={`${match.url}/m-message`} component={MMessage} />
      <ErrorBoundaryRoute path={`${match.url}/m-pajak`} component={MPajak} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
