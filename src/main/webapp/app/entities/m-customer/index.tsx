import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MCustomer from './m-customer';
import MCustomerDetail from './m-customer-detail';
import MCustomerUpdate from './m-customer-update';
import MCustomerDeleteDialog from './m-customer-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MCustomerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MCustomerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MCustomerDetail} />
      <ErrorBoundaryRoute path={match.url} component={MCustomer} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MCustomerDeleteDialog} />
  </>
);

export default Routes;
