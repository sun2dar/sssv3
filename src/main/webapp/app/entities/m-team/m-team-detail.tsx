import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-team.reducer';
import { IMTeam } from 'app/shared/model/m-team.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMTeamDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MTeamDetail extends React.Component<IMTeamDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mTeamEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MTeam [<b>{mTeamEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nama">Nama</span>
            </dt>
            <dd>{mTeamEntity.nama}</dd>
            <dt>
              <span id="jumlah">Jumlah</span>
            </dt>
            <dd>{mTeamEntity.jumlah}</dd>
            <dt>
              <span id="deskripsi">Deskripsi</span>
            </dt>
            <dd>{mTeamEntity.deskripsi}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mTeamEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mTeamEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{mTeamEntity.createdby ? mTeamEntity.createdby.login : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-team" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-team/${mTeamEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mTeam }: IRootState) => ({
  mTeamEntity: mTeam.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MTeamDetail);
