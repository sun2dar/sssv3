import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MPlywoodCategory from './m-plywood-category';
import MPlywoodCategoryDetail from './m-plywood-category-detail';
import MPlywoodCategoryUpdate from './m-plywood-category-update';
import MPlywoodCategoryDeleteDialog from './m-plywood-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MPlywoodCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MPlywoodCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MPlywoodCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={MPlywoodCategory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MPlywoodCategoryDeleteDialog} />
  </>
);

export default Routes;
