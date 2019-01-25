import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TVeneer from './t-veneer';
import TVeneerDetail from './t-veneer-detail';
import TVeneerUpdate from './t-veneer-update';
import TVeneerDeleteDialog from './t-veneer-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TVeneerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TVeneerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TVeneerDetail} />
      <ErrorBoundaryRoute path={match.url} component={TVeneer} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TVeneerDeleteDialog} />
  </>
);

export default Routes;
