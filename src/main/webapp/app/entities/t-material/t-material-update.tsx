import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMMaterial } from 'app/shared/model/m-material.model';
import { getEntities as getMMaterials } from 'app/entities/m-material/m-material.reducer';
import { ITransaksi } from 'app/shared/model/transaksi.model';
import { getEntities as getTransaksis } from 'app/entities/transaksi/transaksi.reducer';
import { getEntity, updateEntity, createEntity, reset } from './t-material.reducer';
import { ITMaterial } from 'app/shared/model/t-material.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITMaterialUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITMaterialUpdateState {
  isNew: boolean;
  mmaterialId: string;
  transaksiId: string;
}

export class TMaterialUpdate extends React.Component<ITMaterialUpdateProps, ITMaterialUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      mmaterialId: '0',
      transaksiId: '0',
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

    this.props.getMMaterials();
    this.props.getTransaksis();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { tMaterialEntity } = this.props;
      const entity = {
        ...tMaterialEntity,
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
    this.props.history.push('/entity/t-material');
  };

  render() {
    const { tMaterialEntity, mMaterials, transaksis, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.tMaterial.home.createOrEditLabel">Create or edit a TMaterial</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tMaterialEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="t-material-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="qtyLabel" for="qty">
                    Qty
                  </Label>
                  <AvField id="t-material-qty" type="string" className="form-control" name="qty" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaBeliLabel" for="hargaBeli">
                    Harga Beli
                  </Label>
                  <AvField id="t-material-hargaBeli" type="string" className="form-control" name="hargaBeli" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaTotalLabel" for="hargaTotal">
                    Harga Total
                  </Label>
                  <AvField id="t-material-hargaTotal" type="string" className="form-control" name="hargaTotal" />
                </AvGroup>
                <AvGroup>
                  <Label id="inoutLabel">Inout</Label>
                  <AvInput
                    id="t-material-inout"
                    type="select"
                    className="form-control"
                    name="inout"
                    value={(!isNew && tMaterialEntity.inout) || 'IN'}
                  >
                    <option value="IN">IN</option>
                    <option value="OUT">OUT</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="mmaterial.nama">Mmaterial</Label>
                  <AvInput id="t-material-mmaterial" type="select" className="form-control" name="mmaterial.id">
                    <option value="" key="0" />
                    {mMaterials
                      ? mMaterials.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="transaksi.invoiceno">Transaksi</Label>
                  <AvInput id="t-material-transaksi" type="select" className="form-control" name="transaksi.id">
                    <option value="" key="0" />
                    {transaksis
                      ? transaksis.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.invoiceno}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/t-material" replace color="info">
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
  mMaterials: storeState.mMaterial.entities,
  transaksis: storeState.transaksi.entities,
  tMaterialEntity: storeState.tMaterial.entity,
  loading: storeState.tMaterial.loading,
  updating: storeState.tMaterial.updating,
  updateSuccess: storeState.tMaterial.updateSuccess
});

const mapDispatchToProps = {
  getMMaterials,
  getTransaksis,
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
)(TMaterialUpdate);
