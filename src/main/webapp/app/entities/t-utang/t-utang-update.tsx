import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMUtang } from 'app/shared/model/m-utang.model';
import { getEntities as getMUtangs } from 'app/entities/m-utang/m-utang.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './t-utang.reducer';
import { ITUtang } from 'app/shared/model/t-utang.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITUtangUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITUtangUpdateState {
  isNew: boolean;
  mutangId: string;
}

export class TUtangUpdate extends React.Component<ITUtangUpdateProps, ITUtangUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      mutangId: '0',
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

    this.props.getMUtangs();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { tUtangEntity } = this.props;
      const entity = {
        ...tUtangEntity,
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
    this.props.history.push('/entity/t-utang');
  };

  render() {
    const { tUtangEntity, mUtangs, loading, updating } = this.props;
    const { isNew } = this.state;

    const { deskripsi } = tUtangEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.tUtang.home.createOrEditLabel">Create or edit a TUtang</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tUtangEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="t-utang-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nominalLabel" for="nominal">
                    Nominal
                  </Label>
                  <AvField id="t-utang-nominal" type="string" className="form-control" name="nominal" />
                </AvGroup>
                <AvGroup>
                  <Label id="deskripsiLabel" for="deskripsi">
                    Deskripsi
                  </Label>
                  <AvInput id="t-utang-deskripsi" type="textarea" name="deskripsi" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdOnLabel" for="createdOn">
                    Created On
                  </Label>
                  <AvField id="t-utang-createdOn" type="date" className="form-control" name="createdOn" />
                </AvGroup>
                <AvGroup>
                  <Label for="mutang.id">Mutang</Label>
                  <AvInput id="t-utang-mutang" type="select" className="form-control" name="mutang.id">
                    <option value="" key="0" />
                    {mUtangs
                      ? mUtangs.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/t-utang" replace color="info">
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
  mUtangs: storeState.mUtang.entities,
  tUtangEntity: storeState.tUtang.entity,
  loading: storeState.tUtang.loading,
  updating: storeState.tUtang.updating,
  updateSuccess: storeState.tUtang.updateSuccess
});

const mapDispatchToProps = {
  getMUtangs,
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
)(TUtangUpdate);
