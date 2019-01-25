import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TLog from './t-log';
import TLogDetail from './t-log-detail';
import TLogUpdate from './t-log-update';
import TLogDeleteDialog from './t-log-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TLogUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TLogUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TLogDetail} />
      <ErrorBoundaryRoute path={match.url} component={TLog} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TLogDeleteDialog} />
  </>
);

export default Routes;
