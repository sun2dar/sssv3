import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MPlywood from './m-plywood';
import MPlywoodDetail from './m-plywood-detail';
import MPlywoodUpdate from './m-plywood-update';
import MPlywoodDeleteDialog from './m-plywood-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MPlywoodUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MPlywoodUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MPlywoodDetail} />
      <ErrorBoundaryRoute path={match.url} component={MPlywood} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MPlywoodDeleteDialog} />
  </>
);

export default Routes;
