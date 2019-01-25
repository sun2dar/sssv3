import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TUtang from './t-utang';
import TUtangDetail from './t-utang-detail';
import TUtangUpdate from './t-utang-update';
import TUtangDeleteDialog from './t-utang-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TUtangUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TUtangUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TUtangDetail} />
      <ErrorBoundaryRoute path={match.url} component={TUtang} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TUtangDeleteDialog} />
  </>
);

export default Routes;
