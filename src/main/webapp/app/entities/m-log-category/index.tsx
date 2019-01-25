import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MLogCategory from './m-log-category';
import MLogCategoryDetail from './m-log-category-detail';
import MLogCategoryUpdate from './m-log-category-update';
import MLogCategoryDeleteDialog from './m-log-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MLogCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MLogCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MLogCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={MLogCategory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MLogCategoryDeleteDialog} />
  </>
);

export default Routes;
