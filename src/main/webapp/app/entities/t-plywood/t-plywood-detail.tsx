import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './t-plywood.reducer';
import { ITPlywood } from 'app/shared/model/t-plywood.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITPlywoodDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TPlywoodDetail extends React.Component<ITPlywoodDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tPlywoodEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            TPlywood [<b>{tPlywoodEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="qty">Qty</span>
            </dt>
            <dd>{tPlywoodEntity.qty}</dd>
            <dt>
              <span id="volume">Volume</span>
            </dt>
            <dd>{tPlywoodEntity.volume}</dd>
            <dt>
              <span id="hargaBeli">Harga Beli</span>
            </dt>
            <dd>{tPlywoodEntity.hargaBeli}</dd>
            <dt>
              <span id="hargaTotal">Harga Total</span>
            </dt>
            <dd>{tPlywoodEntity.hargaTotal}</dd>
            <dt>
              <span id="inout">Inout</span>
            </dt>
            <dd>{tPlywoodEntity.inout}</dd>
            <dt>Mplywood</dt>
            <dd>{tPlywoodEntity.mplywood ? tPlywoodEntity.mplywood.id : ''}</dd>
            <dt>Transaksi</dt>
            <dd>{tPlywoodEntity.transaksi ? tPlywoodEntity.transaksi.invoiceno : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/t-plywood" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/t-plywood/${tPlywoodEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ tPlywood }: IRootState) => ({
  tPlywoodEntity: tPlywood.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TPlywoodDetail);
