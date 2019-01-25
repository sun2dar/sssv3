import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-ekspedisi.reducer';
import { IMEkspedisi } from 'app/shared/model/m-ekspedisi.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMEkspedisiDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MEkspedisiDetail extends React.Component<IMEkspedisiDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mEkspedisiEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MEkspedisi [<b>{mEkspedisiEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nama">Nama</span>
            </dt>
            <dd>{mEkspedisiEntity.nama}</dd>
            <dt>
              <span id="telepon">Telepon</span>
            </dt>
            <dd>{mEkspedisiEntity.telepon}</dd>
            <dt>
              <span id="mobilephone">Mobilephone</span>
            </dt>
            <dd>{mEkspedisiEntity.mobilephone}</dd>
            <dt>
              <span id="alamat">Alamat</span>
            </dt>
            <dd>{mEkspedisiEntity.alamat}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mEkspedisiEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mEkspedisiEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{mEkspedisiEntity.createdby ? mEkspedisiEntity.createdby.login : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-ekspedisi" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-ekspedisi/${mEkspedisiEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mEkspedisi }: IRootState) => ({
  mEkspedisiEntity: mEkspedisi.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MEkspedisiDetail);
