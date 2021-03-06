import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-material-type.reducer';
import { IMMaterialType } from 'app/shared/model/m-material-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMMaterialTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MMaterialTypeDetail extends React.Component<IMMaterialTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mMaterialTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MMaterialType [<b>{mMaterialTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nama">Nama</span>
            </dt>
            <dd>{mMaterialTypeEntity.nama}</dd>
            <dt>
              <span id="deskripsi">Deskripsi</span>
            </dt>
            <dd>{mMaterialTypeEntity.deskripsi}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mMaterialTypeEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mMaterialTypeEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{mMaterialTypeEntity.createdby ? mMaterialTypeEntity.createdby.login : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-material-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-material-type/${mMaterialTypeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mMaterialType }: IRootState) => ({
  mMaterialTypeEntity: mMaterialType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MMaterialTypeDetail);
