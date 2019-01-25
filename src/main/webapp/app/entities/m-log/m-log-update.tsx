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
import { IMLogCategory } from 'app/shared/model/m-log-category.model';
import { getEntities as getMLogCategories } from 'app/entities/m-log-category/m-log-category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './m-log.reducer';
import { IMLog } from 'app/shared/model/m-log.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMLogUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMLogUpdateState {
  isNew: boolean;
  createdbyId: string;
  mlogcatId: string;
}

export class MLogUpdate extends React.Component<IMLogUpdateProps, IMLogUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdbyId: '0',
      mlogcatId: '0',
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
    this.props.getMLogCategories();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { mLogEntity } = this.props;
      const entity = {
        ...mLogEntity,
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
    this.props.history.push('/entity/m-log');
  };

  render() {
    const { mLogEntity, users, mLogCategories, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.mLog.home.createOrEditLabel">Create or edit a MLog</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mLogEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="m-log-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="namaLabel" for="nama">
                    Nama
                  </Label>
                  <AvField
                    id="m-log-nama"
                    type="text"
                    name="nama"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="diameterLabel" for="diameter">
                    Diameter
                  </Label>
                  <AvField
                    id="m-log-diameter"
                    type="string"
                    className="form-control"
                    name="diameter"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="panjangLabel" for="panjang">
                    Panjang
                  </Label>
                  <AvField
                    id="m-log-panjang"
                    type="string"
                    className="form-control"
                    name="panjang"
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
                  <AvField id="m-log-hargaBeli" type="string" className="form-control" name="hargaBeli" />
                </AvGroup>
                <AvGroup>
                  <Label id="qtyLabel" for="qty">
                    Qty
                  </Label>
                  <AvField id="m-log-qty" type="string" className="form-control" name="qty" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="m-log-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && mLogEntity.status) || 'ACT'}
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
                  <AvField id="m-log-createdOn" type="date" className="form-control" name="createdOn" />
                </AvGroup>
                <AvGroup>
                  <Label for="createdby.login">Createdby</Label>
                  <AvInput id="m-log-createdby" type="select" className="form-control" name="createdby.id">
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
                  <Label for="mlogcat.nama">Mlogcat</Label>
                  <AvInput id="m-log-mlogcat" type="select" className="form-control" name="mlogcat.id">
                    <option value="" key="0" />
                    {mLogCategories
                      ? mLogCategories.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/m-log" replace color="info">
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
  mLogCategories: storeState.mLogCategory.entities,
  mLogEntity: storeState.mLog.entity,
  loading: storeState.mLog.loading,
  updating: storeState.mLog.updating,
  updateSuccess: storeState.mLog.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getMLogCategories,
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
)(MLogUpdate);
