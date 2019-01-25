import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MSupplier from './m-supplier';
import MSupplierDetail from './m-supplier-detail';
import MSupplierUpdate from './m-supplier-update';
import MSupplierDeleteDialog from './m-supplier-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MSupplierUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MSupplierUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MSupplierDetail} />
      <ErrorBoundaryRoute path={match.url} component={MSupplier} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MSupplierDeleteDialog} />
  </>
);

export default Routes;
