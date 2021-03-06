import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMPlywood } from 'app/shared/model/m-plywood.model';
import { getEntities as getMPlywoods } from 'app/entities/m-plywood/m-plywood.reducer';
import { ITransaksi } from 'app/shared/model/transaksi.model';
import { getEntities as getTransaksis } from 'app/entities/transaksi/transaksi.reducer';
import { getEntity, updateEntity, createEntity, reset } from './t-plywood.reducer';
import { ITPlywood } from 'app/shared/model/t-plywood.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITPlywoodUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITPlywoodUpdateState {
  isNew: boolean;
  mplywoodId: string;
  transaksiId: string;
}

export class TPlywoodUpdate extends React.Component<ITPlywoodUpdateProps, ITPlywoodUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      mplywoodId: '0',
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

    this.props.getMPlywoods();
    this.props.getTransaksis();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { tPlywoodEntity } = this.props;
      const entity = {
        ...tPlywoodEntity,
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
    this.props.history.push('/entity/t-plywood');
  };

  render() {
    const { tPlywoodEntity, mPlywoods, transaksis, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.tPlywood.home.createOrEditLabel">Create or edit a TPlywood</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tPlywoodEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="t-plywood-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="qtyLabel" for="qty">
                    Qty
                  </Label>
                  <AvField id="t-plywood-qty" type="string" className="form-control" name="qty" />
                </AvGroup>
                <AvGroup>
                  <Label id="volumeLabel" for="volume">
                    Volume
                  </Label>
                  <AvField id="t-plywood-volume" type="string" className="form-control" name="volume" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaBeliLabel" for="hargaBeli">
                    Harga Beli
                  </Label>
                  <AvField id="t-plywood-hargaBeli" type="string" className="form-control" name="hargaBeli" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaTotalLabel" for="hargaTotal">
                    Harga Total
                  </Label>
                  <AvField id="t-plywood-hargaTotal" type="string" className="form-control" name="hargaTotal" />
                </AvGroup>
                <AvGroup>
                  <Label id="inoutLabel">Inout</Label>
                  <AvInput
                    id="t-plywood-inout"
                    type="select"
                    className="form-control"
                    name="inout"
                    value={(!isNew && tPlywoodEntity.inout) || 'IN'}
                  >
                    <option value="IN">IN</option>
                    <option value="OUT">OUT</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="mplywood.id">Mplywood</Label>
                  <AvInput id="t-plywood-mplywood" type="select" className="form-control" name="mplywood.id">
                    <option value="" key="0" />
                    {mPlywoods
                      ? mPlywoods.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="transaksi.invoiceno">Transaksi</Label>
                  <AvInput id="t-plywood-transaksi" type="select" className="form-control" name="transaksi.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/t-plywood" replace color="info">
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
  mPlywoods: storeState.mPlywood.entities,
  transaksis: storeState.transaksi.entities,
  tPlywoodEntity: storeState.tPlywood.entity,
  loading: storeState.tPlywood.loading,
  updating: storeState.tPlywood.updating,
  updateSuccess: storeState.tPlywood.updateSuccess
});

const mapDispatchToProps = {
  getMPlywoods,
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
)(TPlywoodUpdate);
