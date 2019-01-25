import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './t-operasional.reducer';
import { ITOperasional } from 'app/shared/model/t-operasional.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITOperasionalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TOperasionalDetail extends React.Component<ITOperasionalDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tOperasionalEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            TOperasional [<b>{tOperasionalEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="tanggal">Tanggal</span>
            </dt>
            <dd>
              <TextFormat value={tOperasionalEntity.tanggal} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="nominal">Nominal</span>
            </dt>
            <dd>{tOperasionalEntity.nominal}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={tOperasionalEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{tOperasionalEntity.createdby ? tOperasionalEntity.createdby.login : ''}</dd>
            <dt>Moperasionaltype</dt>
            <dd>{tOperasionalEntity.moperasionaltype ? tOperasionalEntity.moperasionaltype.nama : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/t-operasional" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/t-operasional/${tOperasionalEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ tOperasional }: IRootState) => ({
  tOperasionalEntity: tOperasional.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TOperasionalDetail);
