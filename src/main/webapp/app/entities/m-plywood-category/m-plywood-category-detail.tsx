import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-plywood-category.reducer';
import { IMPlywoodCategory } from 'app/shared/model/m-plywood-category.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMPlywoodCategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MPlywoodCategoryDetail extends React.Component<IMPlywoodCategoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mPlywoodCategoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MPlywoodCategory [<b>{mPlywoodCategoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nama">Nama</span>
            </dt>
            <dd>{mPlywoodCategoryEntity.nama}</dd>
            <dt>
              <span id="deskripsi">Deskripsi</span>
            </dt>
            <dd>{mPlywoodCategoryEntity.deskripsi}</dd>
            <dt>
              <span id="tebal">Tebal</span>
            </dt>
            <dd>{mPlywoodCategoryEntity.tebal}</dd>
            <dt>
              <span id="panjang">Panjang</span>
            </dt>
            <dd>{mPlywoodCategoryEntity.panjang}</dd>
            <dt>
              <span id="lebar">Lebar</span>
            </dt>
            <dd>{mPlywoodCategoryEntity.lebar}</dd>
            <dt>
              <span id="hargaBeli">Harga Beli</span>
            </dt>
            <dd>{mPlywoodCategoryEntity.hargaBeli}</dd>
            <dt>
              <span id="hargaJual">Harga Jual</span>
            </dt>
            <dd>{mPlywoodCategoryEntity.hargaJual}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mPlywoodCategoryEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mPlywoodCategoryEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{mPlywoodCategoryEntity.createdby ? mPlywoodCategoryEntity.createdby.login : ''}</dd>
            <dt>Plywoodgrade</dt>
            <dd>{mPlywoodCategoryEntity.plywoodgrade ? mPlywoodCategoryEntity.plywoodgrade.nama : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-plywood-category" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-plywood-category/${mPlywoodCategoryEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mPlywoodCategory }: IRootState) => ({
  mPlywoodCategoryEntity: mPlywoodCategory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MPlywoodCategoryDetail);
