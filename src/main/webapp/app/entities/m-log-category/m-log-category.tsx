import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, getPaginationItemsNumber, JhiPagination } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './m-log-category.reducer';
import { IMLogCategory } from 'app/shared/model/m-log-category.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IMLogCategoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IMLogCategoryState = IPaginationBaseState;

export class MLogCategory extends React.Component<IMLogCategoryProps, IMLogCategoryState> {
  state: IMLogCategoryState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { mLogCategoryList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="m-log-category-heading">
          M Log Categories
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new M Log Category
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('nama')}>
                  Nama <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('diameter1')}>
                  Diameter 1 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('diameter2')}>
                  Diameter 2 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('hargaBeli')}>
                  Harga Beli <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('hargaJual')}>
                  Harga Jual <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('status')}>
                  Status <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('createdOn')}>
                  Created On <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Createdby <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Mlogtype <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mLogCategoryList.map((mLogCategory, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${mLogCategory.id}`} color="link" size="sm">
                      {mLogCategory.id}
                    </Button>
                  </td>
                  <td>{mLogCategory.nama}</td>
                  <td>{mLogCategory.diameter1}</td>
                  <td>{mLogCategory.diameter2}</td>
                  <td>{mLogCategory.hargaBeli}</td>
                  <td>{mLogCategory.hargaJual}</td>
                  <td>{mLogCategory.status}</td>
                  <td>
                    <TextFormat type="date" value={mLogCategory.createdOn} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{mLogCategory.createdby ? mLogCategory.createdby.login : ''}</td>
                  <td>
                    {mLogCategory.mlogtype ? <Link to={`m-log-type/${mLogCategory.mlogtype.id}`}>{mLogCategory.mlogtype.nama}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mLogCategory.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mLogCategory.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mLogCategory.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ mLogCategory }: IRootState) => ({
  mLogCategoryList: mLogCategory.entities,
  totalItems: mLogCategory.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MLogCategory);
