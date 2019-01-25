import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, getPaginationItemsNumber, JhiPagination } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './m-utang.reducer';
import { IMUtang } from 'app/shared/model/m-utang.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IMUtangProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IMUtangState = IPaginationBaseState;

export class MUtang extends React.Component<IMUtangProps, IMUtangState> {
  state: IMUtangState = {
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
    const { mUtangList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="m-utang-heading">
          M Utangs
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new M Utang
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('nominal')}>
                  Nominal <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('sisa')}>
                  Sisa <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('duedate')}>
                  Duedate <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('tipe')}>
                  Tipe <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('status')}>
                  Status <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Transaksi <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mUtangList.map((mUtang, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${mUtang.id}`} color="link" size="sm">
                      {mUtang.id}
                    </Button>
                  </td>
                  <td>{mUtang.nominal}</td>
                  <td>{mUtang.sisa}</td>
                  <td>
                    <TextFormat type="date" value={mUtang.duedate} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{mUtang.tipe}</td>
                  <td>{mUtang.status}</td>
                  <td>{mUtang.transaksi ? <Link to={`transaksi/${mUtang.transaksi.id}`}>{mUtang.transaksi.invoiceno}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${mUtang.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mUtang.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${mUtang.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ mUtang }: IRootState) => ({
  mUtangList: mUtang.entities,
  totalItems: mUtang.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MUtang);
