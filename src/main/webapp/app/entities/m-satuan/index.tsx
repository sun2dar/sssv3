import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MSatuan from './m-satuan';
import MSatuanDetail from './m-satuan-detail';
import MSatuanUpdate from './m-satuan-update';
import MSatuanDeleteDialog from './m-satuan-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MSatuanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MSatuanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MSatuanDetail} />
      <ErrorBoundaryRoute path={match.url} component={MSatuan} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MSatuanDeleteDialog} />
  </>
);

export default Routes;
