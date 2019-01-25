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
import { getEntities } from './m-veneer-category.reducer';
import { IMVeneerCategory } from 'app/shared/model/m-veneer-category.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IMVeneerCategoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IMVeneerCategoryState = IPaginationBaseState;

export class MVeneerCategory extends React.Component<IMVeneerCategoryProps, IMVeneerCategoryState> {
  state: IMVeneerCategoryState = {
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
    const { mVeneerCategoryList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="m-veneer-category-heading">
          M Veneer Categories
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new M Veneer Category
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
                <th className="hand" onClick={this.sort('type')}>
                  Type <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('subtype')}>
                  Subtype <FontAwesomeIcon icon="sort" />
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
                <th />
              </tr>
            </thead>
            <tbody>
              {mVeneerCategoryList.map((mVeneerCategory, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${mVeneerCategory.id}`} color="link" size="sm">
                      {mVeneerCategory.id}
                    </Button>
                  </td>
                  <td>{mVeneerCategory.nama}</td>
                  <td>{mVeneerCategory.deskripsi}</td>
                  <td>{mVeneerCategory.tebal}</td>
                  <td>{mVeneerCategory.panjang}</td>
                  <td>{mVeneerCategory.lebar}</td>
                  <td>{mVeneerCategory.hargaBeli}</td>
                  <td>{mVeneerCategory.hargaJual}</td>
                  <td>{mVeneerCategory.type}</td>
                  <td>{mVeneerCategory.subtype}</td>
                  <td>{mVeneerCategory.status}</td>
                  <td>
                    <TextFormat type="date" value={mVeneerCategory.createdOn} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{mVeneerCategory.createdby ? mVeneerCategory.createdby.login : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mVeneerCategory.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mVeneerCategory.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mVeneerCategory.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ mVeneerCategory }: IRootState) => ({
  mVeneerCategoryList: mVeneerCategory.entities,
  totalItems: mVeneerCategory.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MVeneerCategory);
