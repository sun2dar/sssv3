import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './t-material.reducer';
import { ITMaterial } from 'app/shared/model/t-material.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITMaterialDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TMaterialDetail extends React.Component<ITMaterialDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tMaterialEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            TMaterial [<b>{tMaterialEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="qty">Qty</span>
            </dt>
            <dd>{tMaterialEntity.qty}</dd>
            <dt>
              <span id="hargaBeli">Harga Beli</span>
            </dt>
            <dd>{tMaterialEntity.hargaBeli}</dd>
            <dt>
              <span id="hargaTotal">Harga Total</span>
            </dt>
            <dd>{tMaterialEntity.hargaTotal}</dd>
            <dt>
              <span id="inout">Inout</span>
            </dt>
            <dd>{tMaterialEntity.inout}</dd>
            <dt>Mmaterial</dt>
            <dd>{tMaterialEntity.mmaterial ? tMaterialEntity.mmaterial.nama : ''}</dd>
            <dt>Transaksi</dt>
            <dd>{tMaterialEntity.transaksi ? tMaterialEntity.transaksi.invoiceno : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/t-material" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/t-material/${tMaterialEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ tMaterial }: IRootState) => ({
  tMaterialEntity: tMaterial.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TMaterialDetail);
