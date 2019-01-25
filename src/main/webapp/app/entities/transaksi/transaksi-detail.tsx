import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './transaksi.reducer';
import { ITransaksi } from 'app/shared/model/transaksi.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITransaksiDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TransaksiDetail extends React.Component<ITransaksiDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { transaksiEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Transaksi [<b>{transaksiEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="tipe">Tipe</span>
            </dt>
            <dd>{transaksiEntity.tipe}</dd>
            <dt>
              <span id="category">Category</span>
            </dt>
            <dd>{transaksiEntity.category}</dd>
            <dt>
              <span id="tanggal">Tanggal</span>
            </dt>
            <dd>
              <TextFormat value={transaksiEntity.tanggal} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="invoiceno">Invoiceno</span>
            </dt>
            <dd>{transaksiEntity.invoiceno}</dd>
            <dt>
              <span id="invoicefile">Invoicefile</span>
            </dt>
            <dd>
              {transaksiEntity.invoicefile ? (
                <div>
                  <a onClick={openFile(transaksiEntity.invoicefileContentType, transaksiEntity.invoicefile)}>
                    <img
                      src={`data:${transaksiEntity.invoicefileContentType};base64,${transaksiEntity.invoicefile}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {transaksiEntity.invoicefileContentType}, {byteSize(transaksiEntity.invoicefile)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="nopol">Nopol</span>
            </dt>
            <dd>{transaksiEntity.nopol}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{transaksiEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={transaksiEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{transaksiEntity.createdby ? transaksiEntity.createdby.login : ''}</dd>
            <dt>Shift</dt>
            <dd>{transaksiEntity.shift ? transaksiEntity.shift.nama : ''}</dd>
            <dt>Supplier</dt>
            <dd>{transaksiEntity.supplier ? transaksiEntity.supplier.nama : ''}</dd>
            <dt>Customer</dt>
            <dd>{transaksiEntity.customer ? transaksiEntity.customer.nama : ''}</dd>
            <dt>Paytype</dt>
            <dd>{transaksiEntity.paytype ? transaksiEntity.paytype.nama : ''}</dd>
            <dt>Ekpedisi</dt>
            <dd>{transaksiEntity.ekpedisi ? transaksiEntity.ekpedisi.nama : ''}</dd>
            <dt>Team</dt>
            <dd>{transaksiEntity.team ? transaksiEntity.team.nama : ''}</dd>
            <dt>Pajak</dt>
            <dd>{transaksiEntity.pajak ? transaksiEntity.pajak.nama : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/transaksi" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/transaksi/${transaksiEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ transaksi }: IRootState) => ({
  transaksiEntity: transaksi.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TransaksiDetail);
