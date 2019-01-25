import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MVeneerCategory from './m-veneer-category';
import MVeneerCategoryDetail from './m-veneer-category-detail';
import MVeneerCategoryUpdate from './m-veneer-category-update';
import MVeneerCategoryDeleteDialog from './m-veneer-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MVeneerCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MVeneerCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MVeneerCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={MVeneerCategory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MVeneerCategoryDeleteDialog} />
  </>
);

export default Routes;
