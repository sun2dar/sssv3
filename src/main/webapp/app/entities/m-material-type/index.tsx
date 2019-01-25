import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MMaterialType from './m-material-type';
import MMaterialTypeDetail from './m-material-type-detail';
import MMaterialTypeUpdate from './m-material-type-update';
import MMaterialTypeDeleteDialog from './m-material-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MMaterialTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MMaterialTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MMaterialTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={MMaterialType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MMaterialTypeDeleteDialog} />
  </>
);

export default Routes;
