import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IMSatuan } from 'app/shared/model/m-satuan.model';
import { getEntities as getMSatuans } from 'app/entities/m-satuan/m-satuan.reducer';
import { IMMaterialType } from 'app/shared/model/m-material-type.model';
import { getEntities as getMMaterialTypes } from 'app/entities/m-material-type/m-material-type.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './m-material.reducer';
import { IMMaterial } from 'app/shared/model/m-material.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMMaterialUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMMaterialUpdateState {
  isNew: boolean;
  createdbyId: string;
  satuanId: string;
  materialtypeId: string;
}

export class MMaterialUpdate extends React.Component<IMMaterialUpdateProps, IMMaterialUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdbyId: '0',
      satuanId: '0',
      materialtypeId: '0',
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

    this.props.getUsers();
    this.props.getMSatuans();
    this.props.getMMaterialTypes();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { mMaterialEntity } = this.props;
      const entity = {
        ...mMaterialEntity,
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
    this.props.history.push('/entity/m-material');
  };

  render() {
    const { mMaterialEntity, users, mSatuans, mMaterialTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    const { deskripsi } = mMaterialEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.mMaterial.home.createOrEditLabel">Create or edit a MMaterial</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mMaterialEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="m-material-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="namaLabel" for="nama">
                    Nama
                  </Label>
                  <AvField
                    id="m-material-nama"
                    type="text"
                    name="nama"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="deskripsiLabel" for="deskripsi">
                    Deskripsi
                  </Label>
                  <AvInput id="m-material-deskripsi" type="textarea" name="deskripsi" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaLabel" for="harga">
                    Harga
                  </Label>
                  <AvField
                    id="m-material-harga"
                    type="string"
                    className="form-control"
                    name="harga"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="qtyLabel" for="qty">
                    Qty
                  </Label>
                  <AvField
                    id="m-material-qty"
                    type="string"
                    className="form-control"
                    name="qty"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="m-material-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && mMaterialEntity.status) || 'ACT'}
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
                  <AvField id="m-material-createdOn" type="date" className="form-control" name="createdOn" />
                </AvGroup>
                <AvGroup>
                  <Label for="createdby.login">Createdby</Label>
                  <AvInput id="m-material-createdby" type="select" className="form-control" name="createdby.id">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="satuan.nama">Satuan</Label>
                  <AvInput id="m-material-satuan" type="select" className="form-control" name="satuan.id">
                    <option value="" key="0" />
                    {mSatuans
                      ? mSatuans.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="materialtype.nama">Materialtype</Label>
                  <AvInput id="m-material-materialtype" type="select" className="form-control" name="materialtype.id">
                    <option value="" key="0" />
                    {mMaterialTypes
                      ? mMaterialTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/m-material" replace color="info">
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
  users: storeState.userManagement.users,
  mSatuans: storeState.mSatuan.entities,
  mMaterialTypes: storeState.mMaterialType.entities,
  mMaterialEntity: storeState.mMaterial.entity,
  loading: storeState.mMaterial.loading,
  updating: storeState.mMaterial.updating,
  updateSuccess: storeState.mMaterial.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getMSatuans,
  getMMaterialTypes,
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
)(MMaterialUpdate);
