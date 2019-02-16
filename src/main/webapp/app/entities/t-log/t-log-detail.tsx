import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './t-log.reducer';
import { ITLog } from 'app/shared/model/t-log.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITLogDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TLogDetail extends React.Component<ITLogDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tLogEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            TLog [<b>{tLogEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="qty">Qty</span>
            </dt>
            <dd>{tLogEntity.qty}</dd>
            <dt>
              <span id="volume">Volume</span>
            </dt>
            <dd>{tLogEntity.volume}</dd>
            <dt>
              <span id="hargaBeli">Harga Beli</span>
            </dt>
            <dd>{tLogEntity.hargaBeli}</dd>
            <dt>
              <span id="hargaTotal">Harga Total</span>
            </dt>
            <dd>{tLogEntity.hargaTotal}</dd>
            <dt>
              <span id="inout">Inout</span>
            </dt>
            <dd>{tLogEntity.inout}</dd>
            <dt>Transaksi</dt>
            <dd>{tLogEntity.transaksi ? tLogEntity.transaksi.invoiceno : ''}</dd>
            <dt>Mlog</dt>
            <dd>{tLogEntity.mlog ? tLogEntity.mlog.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/t-log" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/t-log/${tLogEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ tLog }: IRootState) => ({
  tLogEntity: tLog.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TLogDetail);
