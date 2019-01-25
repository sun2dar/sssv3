import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MTeam from './m-team';
import MTeamDetail from './m-team-detail';
import MTeamUpdate from './m-team-update';
import MTeamDeleteDialog from './m-team-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MTeamUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MTeamUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MTeamDetail} />
      <ErrorBoundaryRoute path={match.url} component={MTeam} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MTeamDeleteDialog} />
  </>
);

export default Routes;
