import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './t-bongkar.reducer';
import { ITBongkar } from 'app/shared/model/t-bongkar.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITBongkarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TBongkarDetail extends React.Component<ITBongkarDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tBongkarEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            TBongkar [<b>{tBongkarEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="rateUpah">Rate Upah</span>
            </dt>
            <dd>{tBongkarEntity.rateUpah}</dd>
            <dt>
              <span id="volume">Volume</span>
            </dt>
            <dd>{tBongkarEntity.volume}</dd>
            <dt>Transaksi</dt>
            <dd>{tBongkarEntity.transaksi ? tBongkarEntity.transaksi.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/t-bongkar" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/t-bongkar/${tBongkarEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ tBongkar }: IRootState) => ({
  tBongkarEntity: tBongkar.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TBongkarDetail);
