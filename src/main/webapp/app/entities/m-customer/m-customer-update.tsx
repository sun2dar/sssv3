import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './m-customer.reducer';
import { IMCustomer } from 'app/shared/model/m-customer.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMCustomerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMCustomerUpdateState {
  isNew: boolean;
}

export class MCustomerUpdate extends React.Component<IMCustomerUpdateProps, IMCustomerUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { mCustomerEntity } = this.props;
      const entity = {
        ...mCustomerEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/m-customer');
  };

  render() {
    const { mCustomerEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { alamat } = mCustomerEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.mCustomer.home.createOrEditLabel">Create or edit a MCustomer</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mCustomerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="m-customer-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="namaLabel" for="nama">
                    Nama
                  </Label>
                  <AvField
                    id="m-customer-nama"
                    type="text"
                    name="nama"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="teleponLabel" for="telepon">
                    Telepon
                  </Label>
                  <AvField id="m-customer-telepon" type="text" name="telepon" />
                </AvGroup>
                <AvGroup>
                  <Label id="mobilephoneLabel" for="mobilephone">
                    Mobilephone
                  </Label>
                  <AvField id="m-customer-mobilephone" type="text" name="mobilephone" />
                </AvGroup>
                <AvGroup>
                  <Label id="alamatLabel" for="alamat">
                    Alamat
                  </Label>
                  <AvInput id="m-customer-alamat" type="textarea" name="alamat" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="m-customer-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && mCustomerEntity.status) || 'ACT'}
                  >
                    <option value="ACT">ACT</option>
                    <option value="DIS">DIS</option>
                    <option value="DEL">DEL</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="createdOnLabel" for="createdOn">
                    Created On
                  </Label>
                  <AvField id="m-customer-createdOn" type="date" className="form-control" name="createdOn" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/m-customer" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  mCustomerEntity: storeState.mCustomer.entity,
  loading: storeState.mCustomer.loading,
  updating: storeState.mCustomer.updating,
  updateSuccess: storeState.mCustomer.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MCustomerUpdate);
