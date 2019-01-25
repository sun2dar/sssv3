import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-material.reducer';
import { IMMaterial } from 'app/shared/model/m-material.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMMaterialDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MMaterialDetail extends React.Component<IMMaterialDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mMaterialEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MMaterial [<b>{mMaterialEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nama">Nama</span>
            </dt>
            <dd>{mMaterialEntity.nama}</dd>
            <dt>
              <span id="deskripsi">Deskripsi</span>
            </dt>
            <dd>{mMaterialEntity.deskripsi}</dd>
            <dt>
              <span id="harga">Harga</span>
            </dt>
            <dd>{mMaterialEntity.harga}</dd>
            <dt>
              <span id="qty">Qty</span>
            </dt>
            <dd>{mMaterialEntity.qty}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mMaterialEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mMaterialEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{mMaterialEntity.createdby ? mMaterialEntity.createdby.login : ''}</dd>
            <dt>Satuan</dt>
            <dd>{mMaterialEntity.satuan ? mMaterialEntity.satuan.nama : ''}</dd>
            <dt>Materialtype</dt>
            <dd>{mMaterialEntity.materialtype ? mMaterialEntity.materialtype.nama : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-material" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-material/${mMaterialEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mMaterial }: IRootState) => ({
  mMaterialEntity: mMaterial.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MMaterialDetail);
