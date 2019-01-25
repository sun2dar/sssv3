import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './t-utang.reducer';
import { ITUtang } from 'app/shared/model/t-utang.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITUtangDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TUtangDetail extends React.Component<ITUtangDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tUtangEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            TUtang [<b>{tUtangEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nominal">Nominal</span>
            </dt>
            <dd>{tUtangEntity.nominal}</dd>
            <dt>
              <span id="deskripsi">Deskripsi</span>
            </dt>
            <dd>{tUtangEntity.deskripsi}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={tUtangEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Mutang</dt>
            <dd>{tUtangEntity.mutang ? tUtangEntity.mutang.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/t-utang" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/t-utang/${tUtangEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ tUtang }: IRootState) => ({
  tUtangEntity: tUtang.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TUtangDetail);
