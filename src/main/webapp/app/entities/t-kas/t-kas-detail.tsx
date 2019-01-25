import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './t-kas.reducer';
import { ITKas } from 'app/shared/model/t-kas.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITKasDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TKasDetail extends React.Component<ITKasDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tKasEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            TKas [<b>{tKasEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="tipekas">Tipekas</span>
            </dt>
            <dd>{tKasEntity.tipekas}</dd>
            <dt>
              <span id="nominal">Nominal</span>
            </dt>
            <dd>{tKasEntity.nominal}</dd>
            <dt>
              <span id="deskripsi">Deskripsi</span>
            </dt>
            <dd>{tKasEntity.deskripsi}</dd>
            <dt>
              <span id="objectid">Objectid</span>
            </dt>
            <dd>{tKasEntity.objectid}</dd>
          </dl>
          <Button tag={Link} to="/entity/t-kas" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/t-kas/${tKasEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ tKas }: IRootState) => ({
  tKasEntity: tKas.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TKasDetail);
