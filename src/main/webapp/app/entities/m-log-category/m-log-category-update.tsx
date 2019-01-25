import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IMLogType } from 'app/shared/model/m-log-type.model';
import { getEntities as getMLogTypes } from 'app/entities/m-log-type/m-log-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './m-log-category.reducer';
import { IMLogCategory } from 'app/shared/model/m-log-category.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMLogCategoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMLogCategoryUpdateState {
  isNew: boolean;
  createdbyId: string;
  mlogtypeId: string;
}

export class MLogCategoryUpdate extends React.Component<IMLogCategoryUpdateProps, IMLogCategoryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdbyId: '0',
      mlogtypeId: '0',
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
    this.props.getMLogTypes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { mLogCategoryEntity } = this.props;
      const entity = {
        ...mLogCategoryEntity,
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
    this.props.history.push('/entity/m-log-category');
  };

  render() {
    const { mLogCategoryEntity, users, mLogTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.mLogCategory.home.createOrEditLabel">Create or edit a MLogCategory</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mLogCategoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="m-log-category-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="namaLabel" for="nama">
                    Nama
                  </Label>
                  <AvField
                    id="m-log-category-nama"
                    type="text"
                    name="nama"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="diameter1Label" for="diameter1">
                    Diameter 1
                  </Label>
                  <AvField
                    id="m-log-category-diameter1"
                    type="string"
                    className="form-control"
                    name="diameter1"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="diameter2Label" for="diameter2">
                    Diameter 2
                  </Label>
                  <AvField
                    id="m-log-category-diameter2"
                    type="string"
                    className="form-control"
                    name="diameter2"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaBeliLabel" for="hargaBeli">
                    Harga Beli
                  </Label>
                  <AvField
                    id="m-log-category-hargaBeli"
                    type="string"
                    className="form-control"
                    name="hargaBeli"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaJualLabel" for="hargaJual">
                    Harga Jual
                  </Label>
                  <AvField id="m-log-category-hargaJual" type="string" className="form-control" name="hargaJual" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="m-log-category-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && mLogCategoryEntity.status) || 'ACT'}
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
                  <AvField id="m-log-category-createdOn" type="date" className="form-control" name="createdOn" />
                </AvGroup>
                <AvGroup>
                  <Label for="createdby.login">Createdby</Label>
                  <AvInput id="m-log-category-createdby" type="select" className="form-control" name="createdby.id">
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
                  <Label for="mlogtype.nama">Mlogtype</Label>
                  <AvInput id="m-log-category-mlogtype" type="select" className="form-control" name="mlogtype.id">
                    <option value="" key="0" />
                    {mLogTypes
                      ? mLogTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/m-log-category" replace color="info">
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
  mLogTypes: storeState.mLogType.entities,
  mLogCategoryEntity: storeState.mLogCategory.entity,
  loading: storeState.mLogCategory.loading,
  updating: storeState.mLogCategory.updating,
  updateSuccess: storeState.mLogCategory.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getMLogTypes,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MLogCategoryUpdate);
