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
import { getEntity, updateEntity, createEntity, setBlob, reset } from './m-veneer-category.reducer';
import { IMVeneerCategory } from 'app/shared/model/m-veneer-category.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMVeneerCategoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMVeneerCategoryUpdateState {
  isNew: boolean;
  createdbyId: string;
}

export class MVeneerCategoryUpdate extends React.Component<IMVeneerCategoryUpdateProps, IMVeneerCategoryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdbyId: '0',
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
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { mVeneerCategoryEntity } = this.props;
      const entity = {
        ...mVeneerCategoryEntity,
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
    this.props.history.push('/entity/m-veneer-category');
  };

  render() {
    const { mVeneerCategoryEntity, users, loading, updating } = this.props;
    const { isNew } = this.state;

    const { deskripsi } = mVeneerCategoryEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.mVeneerCategory.home.createOrEditLabel">Create or edit a MVeneerCategory</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mVeneerCategoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="m-veneer-category-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="namaLabel" for="nama">
                    Nama
                  </Label>
                  <AvField
                    id="m-veneer-category-nama"
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
                  <AvInput id="m-veneer-category-deskripsi" type="textarea" name="deskripsi" />
                </AvGroup>
                <AvGroup>
                  <Label id="tebalLabel" for="tebal">
                    Tebal
                  </Label>
                  <AvField id="m-veneer-category-tebal" type="string" className="form-control" name="tebal" />
                </AvGroup>
                <AvGroup>
                  <Label id="panjangLabel" for="panjang">
                    Panjang
                  </Label>
                  <AvField id="m-veneer-category-panjang" type="string" className="form-control" name="panjang" />
                </AvGroup>
                <AvGroup>
                  <Label id="lebarLabel" for="lebar">
                    Lebar
                  </Label>
                  <AvField id="m-veneer-category-lebar" type="string" className="form-control" name="lebar" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaBeliLabel" for="hargaBeli">
                    Harga Beli
                  </Label>
                  <AvField id="m-veneer-category-hargaBeli" type="string" className="form-control" name="hargaBeli" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaJualLabel" for="hargaJual">
                    Harga Jual
                  </Label>
                  <AvField id="m-veneer-category-hargaJual" type="string" className="form-control" name="hargaJual" />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel">Type</Label>
                  <AvInput
                    id="m-veneer-category-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && mVeneerCategoryEntity.type) || 'BASAH'}
                  >
                    <option value="BASAH">BASAH</option>
                    <option value="KERING">KERING</option>
                    <option value="REPAIR">REPAIR</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="subtypeLabel">Subtype</Label>
                  <AvInput
                    id="m-veneer-category-subtype"
                    type="select"
                    className="form-control"
                    name="subtype"
                    value={(!isNew && mVeneerCategoryEntity.subtype) || 'FB'}
                  >
                    <option value="FB">FB</option>
                    <option value="LC">LC</option>
                    <option value="SC">SC</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="m-veneer-category-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && mVeneerCategoryEntity.status) || 'ACT'}
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
                  <AvField id="m-veneer-category-createdOn" type="date" className="form-control" name="createdOn" />
                </AvGroup>
                <AvGroup>
                  <Label for="createdby.login">Createdby</Label>
                  <AvInput id="m-veneer-category-createdby" type="select" className="form-control" name="createdby.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/m-veneer-category" replace color="info">
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
  mVeneerCategoryEntity: storeState.mVeneerCategory.entity,
  loading: storeState.mVeneerCategory.loading,
  updating: storeState.mVeneerCategory.updating,
  updateSuccess: storeState.mVeneerCategory.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
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
)(MVeneerCategoryUpdate);
