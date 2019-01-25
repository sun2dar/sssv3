import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MUtang from './m-utang';
import MUtangDetail from './m-utang-detail';
import MUtangUpdate from './m-utang-update';
import MUtangDeleteDialog from './m-utang-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MUtangUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MUtangUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MUtangDetail} />
      <ErrorBoundaryRoute path={match.url} component={MUtang} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MUtangDeleteDialog} />
  </>
);

export default Routes;
