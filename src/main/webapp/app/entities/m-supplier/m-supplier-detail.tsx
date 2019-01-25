import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-supplier.reducer';
import { IMSupplier } from 'app/shared/model/m-supplier.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMSupplierDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MSupplierDetail extends React.Component<IMSupplierDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mSupplierEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MSupplier [<b>{mSupplierEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nama">Nama</span>
            </dt>
            <dd>{mSupplierEntity.nama}</dd>
            <dt>
              <span id="telepon">Telepon</span>
            </dt>
            <dd>{mSupplierEntity.telepon}</dd>
            <dt>
              <span id="mobilephone">Mobilephone</span>
            </dt>
            <dd>{mSupplierEntity.mobilephone}</dd>
            <dt>
              <span id="alamat">Alamat</span>
            </dt>
            <dd>{mSupplierEntity.alamat}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mSupplierEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mSupplierEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{mSupplierEntity.createdby ? mSupplierEntity.createdby.login : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-supplier" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-supplier/${mSupplierEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mSupplier }: IRootState) => ({
  mSupplierEntity: mSupplier.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MSupplierDetail);
