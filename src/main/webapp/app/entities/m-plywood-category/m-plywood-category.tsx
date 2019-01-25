import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
  byteSize,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './m-plywood-category.reducer';
import { IMPlywoodCategory } from 'app/shared/model/m-plywood-category.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IMPlywoodCategoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IMPlywoodCategoryState = IPaginationBaseState;

export class MPlywoodCategory extends React.Component<IMPlywoodCategoryProps, IMPlywoodCategoryState> {
  state: IMPlywoodCategoryState = {
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
    const { mPlywoodCategoryList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="m-plywood-category-heading">
          M Plywood Categories
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new M Plywood Category
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
                <th className="hand" onClick={this.sort('deskripsi')}>
                  Deskripsi <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('tebal')}>
                  Tebal <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('panjang')}>
                  Panjang <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('lebar')}>
                  Lebar <FontAwesomeIcon icon="sort" />
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
                  Plywoodgrade <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mPlywoodCategoryList.map((mPlywoodCategory, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${mPlywoodCategory.id}`} color="link" size="sm">
                      {mPlywoodCategory.id}
                    </Button>
                  </td>
                  <td>{mPlywoodCategory.nama}</td>
                  <td>{mPlywoodCategory.deskripsi}</td>
                  <td>{mPlywoodCategory.tebal}</td>
                  <td>{mPlywoodCategory.panjang}</td>
                  <td>{mPlywoodCategory.lebar}</td>
                  <td>{mPlywoodCategory.hargaBeli}</td>
                  <td>{mPlywoodCategory.hargaJual}</td>
                  <td>{mPlywoodCategory.status}</td>
                  <td>
                    <TextFormat type="date" value={mPlywoodCategory.createdOn} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{mPlywoodCategory.createdby ? mPlywoodCategory.createdby.login : ''}</td>
                  <td>
                    {mPlywoodCategory.plywoodgrade ? (
                      <Link to={`m-plywood-grade/${mPlywoodCategory.plywoodgrade.id}`}>{mPlywoodCategory.plywoodgrade.nama}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mPlywoodCategory.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mPlywoodCategory.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mPlywoodCategory.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ mPlywoodCategory }: IRootState) => ({
  mPlywoodCategoryList: mPlywoodCategory.entities,
  totalItems: mPlywoodCategory.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MPlywoodCategory);
