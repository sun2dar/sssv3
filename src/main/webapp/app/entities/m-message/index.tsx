import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MMessage from './m-message';
import MMessageDetail from './m-message-detail';
import MMessageUpdate from './m-message-update';
import MMessageDeleteDialog from './m-message-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MMessageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MMessageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MMessageDetail} />
      <ErrorBoundaryRoute path={match.url} component={MMessage} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MMessageDeleteDialog} />
  </>
);

export default Routes;
