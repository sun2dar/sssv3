import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MMaterial from './m-material';
import MMaterialDetail from './m-material-detail';
import MMaterialUpdate from './m-material-update';
import MMaterialDeleteDialog from './m-material-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MMaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MMaterialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MMaterialDetail} />
      <ErrorBoundaryRoute path={match.url} component={MMaterial} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MMaterialDeleteDialog} />
  </>
);

export default Routes;
