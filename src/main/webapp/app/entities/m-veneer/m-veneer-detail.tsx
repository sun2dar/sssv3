import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-veneer.reducer';
import { IMVeneer } from 'app/shared/model/m-veneer.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMVeneerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MVeneerDetail extends React.Component<IMVeneerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mVeneerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MVeneer [<b>{mVeneerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="hargaBeli">Harga Beli</span>
            </dt>
            <dd>{mVeneerEntity.hargaBeli}</dd>
            <dt>
              <span id="qty">Qty</span>
            </dt>
            <dd>{mVeneerEntity.qty}</dd>
            <dt>
              <span id="qtyProduksi">Qty Produksi</span>
            </dt>
            <dd>{mVeneerEntity.qtyProduksi}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mVeneerEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mVeneerEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{mVeneerEntity.createdby ? mVeneerEntity.createdby.login : ''}</dd>
            <dt>Veneercategory</dt>
            <dd>{mVeneerEntity.veneercategory ? mVeneerEntity.veneercategory.nama : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-veneer" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-veneer/${mVeneerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mVeneer }: IRootState) => ({
  mVeneerEntity: mVeneer.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MVeneerDetail);
