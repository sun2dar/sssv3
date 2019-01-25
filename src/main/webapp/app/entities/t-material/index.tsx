import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TMaterial from './t-material';
import TMaterialDetail from './t-material-detail';
import TMaterialUpdate from './t-material-update';
import TMaterialDeleteDialog from './t-material-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TMaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TMaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TMaterialDetail} />
      <ErrorBoundaryRoute path={match.url} component={TMaterial} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TMaterialDeleteDialog} />
  </>
);

export default Routes;
