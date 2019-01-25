import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Transaksi from './transaksi';
import TransaksiDetail from './transaksi-detail';
import TransaksiUpdate from './transaksi-update';
import TransaksiDeleteDialog from './transaksi-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TransaksiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TransaksiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TransaksiDetail} />
      <ErrorBoundaryRoute path={match.url} component={Transaksi} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TransaksiDeleteDialog} />
  </>
);

export default Routes;
