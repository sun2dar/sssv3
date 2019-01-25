import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TPlywood from './t-plywood';
import TPlywoodDetail from './t-plywood-detail';
import TPlywoodUpdate from './t-plywood-update';
import TPlywoodDeleteDialog from './t-plywood-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TPlywoodUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TPlywoodUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TPlywoodDetail} />
      <ErrorBoundaryRoute path={match.url} component={TPlywood} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TPlywoodDeleteDialog} />
  </>
);

export default Routes;
