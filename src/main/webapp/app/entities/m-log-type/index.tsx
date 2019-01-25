import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MLogType from './m-log-type';
import MLogTypeDetail from './m-log-type-detail';
import MLogTypeUpdate from './m-log-type-update';
import MLogTypeDeleteDialog from './m-log-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MLogTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MLogTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MLogTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={MLogType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MLogTypeDeleteDialog} />
  </>
);

export default Routes;
