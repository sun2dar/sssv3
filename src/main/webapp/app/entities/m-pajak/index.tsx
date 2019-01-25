import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MPajak from './m-pajak';
import MPajakDetail from './m-pajak-detail';
import MPajakUpdate from './m-pajak-update';
import MPajakDeleteDialog from './m-pajak-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MPajakUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MPajakUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MPajakDetail} />
      <ErrorBoundaryRoute path={match.url} component={MPajak} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MPajakDeleteDialog} />
  </>
);

export default Routes;
