import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, getSortState, IPaginationBaseState, getPaginationItemsNumber, JhiPagination } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './t-log.reducer';
import { ITLog } from 'app/shared/model/t-log.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ITLogProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ITLogState = IPaginationBaseState;

export class TLog extends React.Component<ITLogProps, ITLogState> {
  state: ITLogState = {
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
    const { tLogList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="t-log-heading">
          T Logs
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new T Log
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('panjang')}>
                  Panjang <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('qty')}>
                  Qty <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('volume')}>
                  Volume <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('hargaBeli')}>
                  Harga Beli <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('hargaTotal')}>
                  Harga Total <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('inout')}>
                  Inout <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Mlogcat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Transaksi <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tLogList.map((tLog, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${tLog.id}`} color="link" size="sm">
                      {tLog.id}
                    </Button>
                  </td>
                  <td>{tLog.panjang}</td>
                  <td>{tLog.qty}</td>
                  <td>{tLog.volume}</td>
                  <td>{tLog.hargaBeli}</td>
                  <td>{tLog.hargaTotal}</td>
                  <td>{tLog.inout}</td>
                  <td>{tLog.mlogcat ? <Link to={`m-log-category/${tLog.mlogcat.id}`}>{tLog.mlogcat.nama}</Link> : ''}</td>
                  <td>{tLog.transaksi ? <Link to={`transaksi/${tLog.transaksi.id}`}>{tLog.transaksi.invoiceno}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tLog.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tLog.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tLog.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ tLog }: IRootState) => ({
  tLogList: tLog.entities,
  totalItems: tLog.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TLog);
