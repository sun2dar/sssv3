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
import { IMOperasionalType } from 'app/shared/model/m-operasional-type.model';
import { getEntities as getMOperasionalTypes } from 'app/entities/m-operasional-type/m-operasional-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './t-operasional.reducer';
import { ITOperasional } from 'app/shared/model/t-operasional.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITOperasionalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITOperasionalUpdateState {
  isNew: boolean;
  createdbyId: string;
  moperasionaltypeId: string;
}

export class TOperasionalUpdate extends React.Component<ITOperasionalUpdateProps, ITOperasionalUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdbyId: '0',
      moperasionaltypeId: '0',
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
    this.props.getMOperasionalTypes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { tOperasionalEntity } = this.props;
      const entity = {
        ...tOperasionalEntity,
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
    this.props.history.push('/entity/t-operasional');
  };

  render() {
    const { tOperasionalEntity, users, mOperasionalTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.tOperasional.home.createOrEditLabel">Create or edit a TOperasional</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tOperasionalEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="t-operasional-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="tanggalLabel" for="tanggal">
                    Tanggal
                  </Label>
                  <AvField id="t-operasional-tanggal" type="date" className="form-control" name="tanggal" />
                </AvGroup>
                <AvGroup>
                  <Label id="nominalLabel" for="nominal">
                    Nominal
                  </Label>
                  <AvField id="t-operasional-nominal" type="string" className="form-control" name="nominal" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdOnLabel" for="createdOn">
                    Created On
                  </Label>
                  <AvField id="t-operasional-createdOn" type="date" className="form-control" name="createdOn" />
                </AvGroup>
                <AvGroup>
                  <Label for="createdby.login">Createdby</Label>
                  <AvInput id="t-operasional-createdby" type="select" className="form-control" name="createdby.id">
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
                  <Label for="moperasionaltype.nama">Moperasionaltype</Label>
                  <AvInput id="t-operasional-moperasionaltype" type="select" className="form-control" name="moperasionaltype.id">
                    <option value="" key="0" />
                    {mOperasionalTypes
                      ? mOperasionalTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/t-operasional" replace color="info">
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
  mOperasionalTypes: storeState.mOperasionalType.entities,
  tOperasionalEntity: storeState.tOperasional.entity,
  loading: storeState.tOperasional.loading,
  updating: storeState.tOperasional.updating,
  updateSuccess: storeState.tOperasional.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getMOperasionalTypes,
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
)(TOperasionalUpdate);
