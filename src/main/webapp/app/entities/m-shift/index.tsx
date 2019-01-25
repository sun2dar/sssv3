import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MShift from './m-shift';
import MShiftDetail from './m-shift-detail';
import MShiftUpdate from './m-shift-update';
import MShiftDeleteDialog from './m-shift-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MShiftUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MShiftUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MShiftDetail} />
      <ErrorBoundaryRoute path={match.url} component={MShift} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MShiftDeleteDialog} />
  </>
);

export default Routes;
