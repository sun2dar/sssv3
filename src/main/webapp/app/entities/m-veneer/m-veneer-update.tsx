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
import { IMVeneerCategory } from 'app/shared/model/m-veneer-category.model';
import { getEntities as getMVeneerCategories } from 'app/entities/m-veneer-category/m-veneer-category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './m-veneer.reducer';
import { IMVeneer } from 'app/shared/model/m-veneer.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMVeneerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMVeneerUpdateState {
  isNew: boolean;
  createdbyId: string;
  veneercategoryId: string;
}

export class MVeneerUpdate extends React.Component<IMVeneerUpdateProps, IMVeneerUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdbyId: '0',
      veneercategoryId: '0',
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
    this.props.getMVeneerCategories();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { mVeneerEntity } = this.props;
      const entity = {
        ...mVeneerEntity,
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
    this.props.history.push('/entity/m-veneer');
  };

  render() {
    const { mVeneerEntity, users, mVeneerCategories, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.mVeneer.home.createOrEditLabel">Create or edit a MVeneer</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mVeneerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="m-veneer-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="hargaBeliLabel" for="hargaBeli">
                    Harga Beli
                  </Label>
                  <AvField id="m-veneer-hargaBeli" type="string" className="form-control" name="hargaBeli" />
                </AvGroup>
                <AvGroup>
                  <Label id="qtyLabel" for="qty">
                    Qty
                  </Label>
                  <AvField id="m-veneer-qty" type="string" className="form-control" name="qty" />
                </AvGroup>
                <AvGroup>
                  <Label id="qtyProduksiLabel" for="qtyProduksi">
                    Qty Produksi
                  </Label>
                  <AvField id="m-veneer-qtyProduksi" type="string" className="form-control" name="qtyProduksi" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="m-veneer-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && mVeneerEntity.status) || 'ACT'}
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
                  <AvField id="m-veneer-createdOn" type="date" className="form-control" name="createdOn" />
                </AvGroup>
                <AvGroup>
                  <Label for="createdby.login">Createdby</Label>
                  <AvInput id="m-veneer-createdby" type="select" className="form-control" name="createdby.id">
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
                  <Label for="veneercategory.nama">Veneercategory</Label>
                  <AvInput id="m-veneer-veneercategory" type="select" className="form-control" name="veneercategory.id">
                    <option value="" key="0" />
                    {mVeneerCategories
                      ? mVeneerCategories.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/m-veneer" replace color="info">
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
  mVeneerCategories: storeState.mVeneerCategory.entities,
  mVeneerEntity: storeState.mVeneer.entity,
  loading: storeState.mVeneer.loading,
  updating: storeState.mVeneer.updating,
  updateSuccess: storeState.mVeneer.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getMVeneerCategories,
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
)(MVeneerUpdate);
