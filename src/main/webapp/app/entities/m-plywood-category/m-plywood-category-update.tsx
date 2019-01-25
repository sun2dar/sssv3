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
import { IMPlywoodGrade } from 'app/shared/model/m-plywood-grade.model';
import { getEntities as getMPlywoodGrades } from 'app/entities/m-plywood-grade/m-plywood-grade.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './m-plywood-category.reducer';
import { IMPlywoodCategory } from 'app/shared/model/m-plywood-category.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMPlywoodCategoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMPlywoodCategoryUpdateState {
  isNew: boolean;
  createdbyId: string;
  plywoodgradeId: string;
}

export class MPlywoodCategoryUpdate extends React.Component<IMPlywoodCategoryUpdateProps, IMPlywoodCategoryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdbyId: '0',
      plywoodgradeId: '0',
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
    this.props.getMPlywoodGrades();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { mPlywoodCategoryEntity } = this.props;
      const entity = {
        ...mPlywoodCategoryEntity,
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
    this.props.history.push('/entity/m-plywood-category');
  };

  render() {
    const { mPlywoodCategoryEntity, users, mPlywoodGrades, loading, updating } = this.props;
    const { isNew } = this.state;

    const { deskripsi } = mPlywoodCategoryEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.mPlywoodCategory.home.createOrEditLabel">Create or edit a MPlywoodCategory</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mPlywoodCategoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="m-plywood-category-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="namaLabel" for="nama">
                    Nama
                  </Label>
                  <AvField
                    id="m-plywood-category-nama"
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
                  <AvInput id="m-plywood-category-deskripsi" type="textarea" name="deskripsi" />
                </AvGroup>
                <AvGroup>
                  <Label id="tebalLabel" for="tebal">
                    Tebal
                  </Label>
                  <AvField id="m-plywood-category-tebal" type="string" className="form-control" name="tebal" />
                </AvGroup>
                <AvGroup>
                  <Label id="panjangLabel" for="panjang">
                    Panjang
                  </Label>
                  <AvField id="m-plywood-category-panjang" type="string" className="form-control" name="panjang" />
                </AvGroup>
                <AvGroup>
                  <Label id="lebarLabel" for="lebar">
                    Lebar
                  </Label>
                  <AvField id="m-plywood-category-lebar" type="string" className="form-control" name="lebar" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaBeliLabel" for="hargaBeli">
                    Harga Beli
                  </Label>
                  <AvField id="m-plywood-category-hargaBeli" type="string" className="form-control" name="hargaBeli" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaJualLabel" for="hargaJual">
                    Harga Jual
                  </Label>
                  <AvField id="m-plywood-category-hargaJual" type="string" className="form-control" name="hargaJual" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="m-plywood-category-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && mPlywoodCategoryEntity.status) || 'ACT'}
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
                  <AvField id="m-plywood-category-createdOn" type="date" className="form-control" name="createdOn" />
                </AvGroup>
                <AvGroup>
                  <Label for="createdby.login">Createdby</Label>
                  <AvInput id="m-plywood-category-createdby" type="select" className="form-control" name="createdby.id">
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
                  <Label for="plywoodgrade.nama">Plywoodgrade</Label>
                  <AvInput id="m-plywood-category-plywoodgrade" type="select" className="form-control" name="plywoodgrade.id">
                    <option value="" key="0" />
                    {mPlywoodGrades
                      ? mPlywoodGrades.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/m-plywood-category" replace color="info">
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
  mPlywoodGrades: storeState.mPlywoodGrade.entities,
  mPlywoodCategoryEntity: storeState.mPlywoodCategory.entity,
  loading: storeState.mPlywoodCategory.loading,
  updating: storeState.mPlywoodCategory.updating,
  updateSuccess: storeState.mPlywoodCategory.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getMPlywoodGrades,
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
)(MPlywoodCategoryUpdate);
