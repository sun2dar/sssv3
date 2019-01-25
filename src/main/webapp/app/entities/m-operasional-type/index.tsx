import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MOperasionalType from './m-operasional-type';
import MOperasionalTypeDetail from './m-operasional-type-detail';
import MOperasionalTypeUpdate from './m-operasional-type-update';
import MOperasionalTypeDeleteDialog from './m-operasional-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MOperasionalTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MOperasionalTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MOperasionalTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={MOperasionalType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MOperasionalTypeDeleteDialog} />
  </>
);

export default Routes;
