import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MVeneer from './m-veneer';
import MVeneerDetail from './m-veneer-detail';
import MVeneerUpdate from './m-veneer-update';
import MVeneerDeleteDialog from './m-veneer-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MVeneerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MVeneerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MVeneerDetail} />
      <ErrorBoundaryRoute path={match.url} component={MVeneer} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MVeneerDeleteDialog} />
  </>
);

export default Routes;
