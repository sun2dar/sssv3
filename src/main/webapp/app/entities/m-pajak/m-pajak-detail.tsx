import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-pajak.reducer';
import { IMPajak } from 'app/shared/model/m-pajak.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMPajakDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MPajakDetail extends React.Component<IMPajakDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mPajakEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MPajak [<b>{mPajakEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nama">Nama</span>
            </dt>
            <dd>{mPajakEntity.nama}</dd>
            <dt>
              <span id="deskripsi">Deskripsi</span>
            </dt>
            <dd>{mPajakEntity.deskripsi}</dd>
            <dt>
              <span id="nominal">Nominal</span>
            </dt>
            <dd>{mPajakEntity.nominal}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mPajakEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mPajakEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/m-pajak" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-pajak/${mPajakEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mPajak }: IRootState) => ({
  mPajakEntity: mPajak.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MPajakDetail);
