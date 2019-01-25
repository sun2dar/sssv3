import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-log-category.reducer';
import { IMLogCategory } from 'app/shared/model/m-log-category.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMLogCategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MLogCategoryDetail extends React.Component<IMLogCategoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mLogCategoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MLogCategory [<b>{mLogCategoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nama">Nama</span>
            </dt>
            <dd>{mLogCategoryEntity.nama}</dd>
            <dt>
              <span id="diameter1">Diameter 1</span>
            </dt>
            <dd>{mLogCategoryEntity.diameter1}</dd>
            <dt>
              <span id="diameter2">Diameter 2</span>
            </dt>
            <dd>{mLogCategoryEntity.diameter2}</dd>
            <dt>
              <span id="hargaBeli">Harga Beli</span>
            </dt>
            <dd>{mLogCategoryEntity.hargaBeli}</dd>
            <dt>
              <span id="hargaJual">Harga Jual</span>
            </dt>
            <dd>{mLogCategoryEntity.hargaJual}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mLogCategoryEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mLogCategoryEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{mLogCategoryEntity.createdby ? mLogCategoryEntity.createdby.login : ''}</dd>
            <dt>Mlogtype</dt>
            <dd>{mLogCategoryEntity.mlogtype ? mLogCategoryEntity.mlogtype.nama : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-log-category" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-log-category/${mLogCategoryEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mLogCategory }: IRootState) => ({
  mLogCategoryEntity: mLogCategory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MLogCategoryDetail);
