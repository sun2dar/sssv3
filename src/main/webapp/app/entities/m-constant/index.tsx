import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MConstant from './m-constant';
import MConstantDetail from './m-constant-detail';
import MConstantUpdate from './m-constant-update';
import MConstantDeleteDialog from './m-constant-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MConstantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MConstantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MConstantDetail} />
      <ErrorBoundaryRoute path={match.url} component={MConstant} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MConstantDeleteDialog} />
  </>
);

export default Routes;
