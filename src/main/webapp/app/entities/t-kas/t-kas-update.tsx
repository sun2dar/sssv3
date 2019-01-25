import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './t-kas.reducer';
import { ITKas } from 'app/shared/model/t-kas.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITKasUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITKasUpdateState {
  isNew: boolean;
}

export class TKasUpdate extends React.Component<ITKasUpdateProps, ITKasUpdateState> {
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
      const { tKasEntity } = this.props;
      const entity = {
        ...tKasEntity,
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
    this.props.history.push('/entity/t-kas');
  };

  render() {
    const { tKasEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { deskripsi } = tKasEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.tKas.home.createOrEditLabel">Create or edit a TKas</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tKasEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="t-kas-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="tipekasLabel">Tipekas</Label>
                  <AvInput
                    id="t-kas-tipekas"
                    type="select"
                    className="form-control"
                    name="tipekas"
                    value={(!isNew && tKasEntity.tipekas) || 'MASUK'}
                  >
                    <option value="MASUK">MASUK</option>
                    <option value="DELIVERY">DELIVERY</option>
                    <option value="BELILOG">BELILOG</option>
                    <option value="JUALLOG">JUALLOG</option>
                    <option value="BELIVENEER">BELIVENEER</option>
                    <option value="JUALVENEER">JUALVENEER</option>
                    <option value="BELIPLAYWOOD">BELIPLAYWOOD</option>
                    <option value="JUALPLAYWOOD">JUALPLAYWOOD</option>
                    <option value="OPERASIONAL">OPERASIONAL</option>
                    <option value="GAJI">GAJI</option>
                    <option value="HUTANGPIUTANG">HUTANGPIUTANG</option>
                    <option value="BONGKAR">BONGKAR</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="nominalLabel" for="nominal">
                    Nominal
                  </Label>
                  <AvField id="t-kas-nominal" type="string" className="form-control" name="nominal" />
                </AvGroup>
                <AvGroup>
                  <Label id="deskripsiLabel" for="deskripsi">
                    Deskripsi
                  </Label>
                  <AvInput id="t-kas-deskripsi" type="textarea" name="deskripsi" />
                </AvGroup>
                <AvGroup>
                  <Label id="objectidLabel" for="objectid">
                    Objectid
                  </Label>
                  <AvField id="t-kas-objectid" type="string" className="form-control" name="objectid" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/t-kas" replace color="info">
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
  tKasEntity: storeState.tKas.entity,
  loading: storeState.tKas.loading,
  updating: storeState.tKas.updating,
  updateSuccess: storeState.tKas.updateSuccess
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
)(TKasUpdate);
