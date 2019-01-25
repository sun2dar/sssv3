import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MEkspedisi from './m-ekspedisi';
import MEkspedisiDetail from './m-ekspedisi-detail';
import MEkspedisiUpdate from './m-ekspedisi-update';
import MEkspedisiDeleteDialog from './m-ekspedisi-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MEkspedisiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MEkspedisiUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MEkspedisiDetail} />
      <ErrorBoundaryRoute path={match.url} component={MEkspedisi} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MEkspedisiDeleteDialog} />
  </>
);

export default Routes;
