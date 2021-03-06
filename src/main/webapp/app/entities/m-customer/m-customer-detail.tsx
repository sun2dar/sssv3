import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-customer.reducer';
import { IMCustomer } from 'app/shared/model/m-customer.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMCustomerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MCustomerDetail extends React.Component<IMCustomerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mCustomerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MCustomer [<b>{mCustomerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nama">Nama</span>
            </dt>
            <dd>{mCustomerEntity.nama}</dd>
            <dt>
              <span id="telepon">Telepon</span>
            </dt>
            <dd>{mCustomerEntity.telepon}</dd>
            <dt>
              <span id="mobilephone">Mobilephone</span>
            </dt>
            <dd>{mCustomerEntity.mobilephone}</dd>
            <dt>
              <span id="alamat">Alamat</span>
            </dt>
            <dd>{mCustomerEntity.alamat}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mCustomerEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mCustomerEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/m-customer" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-customer/${mCustomerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mCustomer }: IRootState) => ({
  mCustomerEntity: mCustomer.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MCustomerDetail);
