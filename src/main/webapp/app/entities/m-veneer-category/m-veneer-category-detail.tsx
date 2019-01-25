import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-veneer-category.reducer';
import { IMVeneerCategory } from 'app/shared/model/m-veneer-category.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMVeneerCategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MVeneerCategoryDetail extends React.Component<IMVeneerCategoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mVeneerCategoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MVeneerCategory [<b>{mVeneerCategoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nama">Nama</span>
            </dt>
            <dd>{mVeneerCategoryEntity.nama}</dd>
            <dt>
              <span id="deskripsi">Deskripsi</span>
            </dt>
            <dd>{mVeneerCategoryEntity.deskripsi}</dd>
            <dt>
              <span id="tebal">Tebal</span>
            </dt>
            <dd>{mVeneerCategoryEntity.tebal}</dd>
            <dt>
              <span id="panjang">Panjang</span>
            </dt>
            <dd>{mVeneerCategoryEntity.panjang}</dd>
            <dt>
              <span id="lebar">Lebar</span>
            </dt>
            <dd>{mVeneerCategoryEntity.lebar}</dd>
            <dt>
              <span id="hargaBeli">Harga Beli</span>
            </dt>
            <dd>{mVeneerCategoryEntity.hargaBeli}</dd>
            <dt>
              <span id="hargaJual">Harga Jual</span>
            </dt>
            <dd>{mVeneerCategoryEntity.hargaJual}</dd>
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{mVeneerCategoryEntity.type}</dd>
            <dt>
              <span id="subtype">Subtype</span>
            </dt>
            <dd>{mVeneerCategoryEntity.subtype}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mVeneerCategoryEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mVeneerCategoryEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{mVeneerCategoryEntity.createdby ? mVeneerCategoryEntity.createdby.login : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-veneer-category" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-veneer-category/${mVeneerCategoryEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mVeneerCategory }: IRootState) => ({
  mVeneerCategoryEntity: mVeneerCategory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MVeneerCategoryDetail);
