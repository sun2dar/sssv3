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
import { getEntities } from './m-ekspedisi.reducer';
import { IMEkspedisi } from 'app/shared/model/m-ekspedisi.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IMEkspedisiProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IMEkspedisiState = IPaginationBaseState;

export class MEkspedisi extends React.Component<IMEkspedisiProps, IMEkspedisiState> {
  state: IMEkspedisiState = {
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
    const { mEkspedisiList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="m-ekspedisi-heading">
          M Ekspedisis
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new M Ekspedisi
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
                <th className="hand" onClick={this.sort('telepon')}>
                  Telepon <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('mobilephone')}>
                  Mobilephone <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('alamat')}>
                  Alamat <FontAwesomeIcon icon="sort" />
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
              {mEkspedisiList.map((mEkspedisi, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${mEkspedisi.id}`} color="link" size="sm">
                      {mEkspedisi.id}
                    </Button>
                  </td>
                  <td>{mEkspedisi.nama}</td>
                  <td>{mEkspedisi.telepon}</td>
                  <td>{mEkspedisi.mobilephone}</td>
                  <td>{mEkspedisi.alamat}</td>
                  <td>{mEkspedisi.status}</td>
                  <td>
                    <TextFormat type="date" value={mEkspedisi.createdOn} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{mEkspedisi.createdby ? mEkspedisi.createdby.login : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mEkspedisi.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mEkspedisi.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mEkspedisi.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ mEkspedisi }: IRootState) => ({
  mEkspedisiList: mEkspedisi.entities,
  totalItems: mEkspedisi.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MEkspedisi);
