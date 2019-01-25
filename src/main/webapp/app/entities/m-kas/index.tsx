import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MKas from './m-kas';
import MKasDetail from './m-kas-detail';
import MKasUpdate from './m-kas-update';
import MKasDeleteDialog from './m-kas-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MKasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MKasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MKasDetail} />
      <ErrorBoundaryRoute path={match.url} component={MKas} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MKasDeleteDialog} />
  </>
);

export default Routes;
