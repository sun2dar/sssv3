import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-log.reducer';
import { IMLog } from 'app/shared/model/m-log.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMLogDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MLogDetail extends React.Component<IMLogDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mLogEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MLog [<b>{mLogEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nama">Nama</span>
            </dt>
            <dd>{mLogEntity.nama}</dd>
            <dt>
              <span id="diameter">Diameter</span>
            </dt>
            <dd>{mLogEntity.diameter}</dd>
            <dt>
              <span id="panjang">Panjang</span>
            </dt>
            <dd>{mLogEntity.panjang}</dd>
            <dt>
              <span id="hargaBeli">Harga Beli</span>
            </dt>
            <dd>{mLogEntity.hargaBeli}</dd>
            <dt>
              <span id="qty">Qty</span>
            </dt>
            <dd>{mLogEntity.qty}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mLogEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mLogEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{mLogEntity.createdby ? mLogEntity.createdby.login : ''}</dd>
            <dt>Mlogcat</dt>
            <dd>{mLogEntity.mlogcat ? mLogEntity.mlogcat.nama : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-log" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-log/${mLogEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mLog }: IRootState) => ({
  mLogEntity: mLog.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MLogDetail);
