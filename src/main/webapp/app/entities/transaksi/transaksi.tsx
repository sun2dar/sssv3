import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import {
  openFile,
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
import { getEntities } from './transaksi.reducer';
import { ITransaksi } from 'app/shared/model/transaksi.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ITransaksiProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ITransaksiState = IPaginationBaseState;

export class Transaksi extends React.Component<ITransaksiProps, ITransaksiState> {
  state: ITransaksiState = {
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
    const { transaksiList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="transaksi-heading">
          Transaksis
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Transaksi
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('tipe')}>
                  Tipe <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('category')}>
                  Category <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('tanggal')}>
                  Tanggal <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('invoiceno')}>
                  Invoiceno <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('invoicefile')}>
                  Invoicefile <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('nopol')}>
                  Nopol <FontAwesomeIcon icon="sort" />
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
                  Shift <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Supplier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Customer <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Paytype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Ekpedisi <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Team <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Pajak <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {transaksiList.map((transaksi, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${transaksi.id}`} color="link" size="sm">
                      {transaksi.id}
                    </Button>
                  </td>
                  <td>{transaksi.tipe}</td>
                  <td>{transaksi.category}</td>
                  <td>
                    <TextFormat type="date" value={transaksi.tanggal} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{transaksi.invoiceno}</td>
                  <td>
                    {transaksi.invoicefile ? (
                      <div>
                        <a onClick={openFile(transaksi.invoicefileContentType, transaksi.invoicefile)}>
                          <img
                            src={`data:${transaksi.invoicefileContentType};base64,${transaksi.invoicefile}`}
                            style={{ maxHeight: '30px' }}
                          />
                          &nbsp;
                        </a>
                        <span>
                          {transaksi.invoicefileContentType}, {byteSize(transaksi.invoicefile)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{transaksi.nopol}</td>
                  <td>{transaksi.status}</td>
                  <td>
                    <TextFormat type="date" value={transaksi.createdOn} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{transaksi.createdby ? transaksi.createdby.login : ''}</td>
                  <td>{transaksi.shift ? <Link to={`m-shift/${transaksi.shift.id}`}>{transaksi.shift.nama}</Link> : ''}</td>
                  <td>{transaksi.supplier ? <Link to={`m-supplier/${transaksi.supplier.id}`}>{transaksi.supplier.nama}</Link> : ''}</td>
                  <td>{transaksi.customer ? <Link to={`m-customer/${transaksi.customer.id}`}>{transaksi.customer.nama}</Link> : ''}</td>
                  <td>{transaksi.paytype ? <Link to={`m-paytype/${transaksi.paytype.id}`}>{transaksi.paytype.nama}</Link> : ''}</td>
                  <td>{transaksi.ekpedisi ? <Link to={`m-ekspedisi/${transaksi.ekpedisi.id}`}>{transaksi.ekpedisi.nama}</Link> : ''}</td>
                  <td>{transaksi.team ? <Link to={`m-team/${transaksi.team.id}`}>{transaksi.team.nama}</Link> : ''}</td>
                  <td>{transaksi.pajak ? <Link to={`m-pajak/${transaksi.pajak.id}`}>{transaksi.pajak.nama}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${transaksi.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${transaksi.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${transaksi.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ transaksi }: IRootState) => ({
  transaksiList: transaksi.entities,
  totalItems: transaksi.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Transaksi);
