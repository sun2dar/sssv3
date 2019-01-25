import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './t-veneer.reducer';
import { ITVeneer } from 'app/shared/model/t-veneer.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITVeneerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TVeneerDetail extends React.Component<ITVeneerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tVeneerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            TVeneer [<b>{tVeneerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="qty">Qty</span>
            </dt>
            <dd>{tVeneerEntity.qty}</dd>
            <dt>
              <span id="volume">Volume</span>
            </dt>
            <dd>{tVeneerEntity.volume}</dd>
            <dt>
              <span id="hargaBeli">Harga Beli</span>
            </dt>
            <dd>{tVeneerEntity.hargaBeli}</dd>
            <dt>
              <span id="hargaTotal">Harga Total</span>
            </dt>
            <dd>{tVeneerEntity.hargaTotal}</dd>
            <dt>
              <span id="inout">Inout</span>
            </dt>
            <dd>{tVeneerEntity.inout}</dd>
            <dt>Veneercategory</dt>
            <dd>{tVeneerEntity.veneercategory ? tVeneerEntity.veneercategory.nama : ''}</dd>
            <dt>Transaksi</dt>
            <dd>{tVeneerEntity.transaksi ? tVeneerEntity.transaksi.invoiceno : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/t-veneer" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/t-veneer/${tVeneerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ tVeneer }: IRootState) => ({
  tVeneerEntity: tVeneer.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TVeneerDetail);
