import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TKas from './t-kas';
import TKasDetail from './t-kas-detail';
import TKasUpdate from './t-kas-update';
import TKasDeleteDialog from './t-kas-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TKasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TKasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TKasDetail} />
      <ErrorBoundaryRoute path={match.url} component={TKas} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TKasDeleteDialog} />
  </>
);

export default Routes;
