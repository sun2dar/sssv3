import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TBongkar from './t-bongkar';
import TBongkarDetail from './t-bongkar-detail';
import TBongkarUpdate from './t-bongkar-update';
import TBongkarDeleteDialog from './t-bongkar-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TBongkarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TBongkarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TBongkarDetail} />
      <ErrorBoundaryRoute path={match.url} component={TBongkar} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TBongkarDeleteDialog} />
  </>
);

export default Routes;
