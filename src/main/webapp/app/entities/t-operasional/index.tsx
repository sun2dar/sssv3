import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TOperasional from './t-operasional';
import TOperasionalDetail from './t-operasional-detail';
import TOperasionalUpdate from './t-operasional-update';
import TOperasionalDeleteDialog from './t-operasional-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TOperasionalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TOperasionalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TOperasionalDetail} />
      <ErrorBoundaryRoute path={match.url} component={TOperasional} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TOperasionalDeleteDialog} />
  </>
);

export default Routes;
