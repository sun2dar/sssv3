import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MPlywoodGrade from './m-plywood-grade';
import MPlywoodGradeDetail from './m-plywood-grade-detail';
import MPlywoodGradeUpdate from './m-plywood-grade-update';
import MPlywoodGradeDeleteDialog from './m-plywood-grade-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MPlywoodGradeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MPlywoodGradeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MPlywoodGradeDetail} />
      <ErrorBoundaryRoute path={match.url} component={MPlywoodGrade} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MPlywoodGradeDeleteDialog} />
  </>
);

export default Routes;
