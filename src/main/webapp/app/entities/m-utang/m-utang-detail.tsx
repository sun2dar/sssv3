import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-utang.reducer';
import { IMUtang } from 'app/shared/model/m-utang.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMUtangDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MUtangDetail extends React.Component<IMUtangDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mUtangEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MUtang [<b>{mUtangEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nominal">Nominal</span>
            </dt>
            <dd>{mUtangEntity.nominal}</dd>
            <dt>
              <span id="sisa">Sisa</span>
            </dt>
            <dd>{mUtangEntity.sisa}</dd>
            <dt>
              <span id="duedate">Duedate</span>
            </dt>
            <dd>
              <TextFormat value={mUtangEntity.duedate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="tipe">Tipe</span>
            </dt>
            <dd>{mUtangEntity.tipe}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mUtangEntity.status}</dd>
            <dt>Transaksi</dt>
            <dd>{mUtangEntity.transaksi ? mUtangEntity.transaksi.invoiceno : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-utang" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-utang/${mUtangEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mUtang }: IRootState) => ({
  mUtangEntity: mUtang.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MUtangDetail);
