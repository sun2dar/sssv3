import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MLog from './m-log';
import MLogDetail from './m-log-detail';
import MLogUpdate from './m-log-update';
import MLogDeleteDialog from './m-log-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MLogUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MLogUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MLogDetail} />
      <ErrorBoundaryRoute path={match.url} component={MLog} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MLogDeleteDialog} />
  </>
);

export default Routes;
