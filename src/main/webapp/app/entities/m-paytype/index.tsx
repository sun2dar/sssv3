import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MPaytype from './m-paytype';
import MPaytypeDetail from './m-paytype-detail';
import MPaytypeUpdate from './m-paytype-update';
import MPaytypeDeleteDialog from './m-paytype-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MPaytypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MPaytypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MPaytypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={MPaytype} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MPaytypeDeleteDialog} />
  </>
);

export default Routes;
