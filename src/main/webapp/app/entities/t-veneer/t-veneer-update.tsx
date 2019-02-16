import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMVeneer } from 'app/shared/model/m-veneer.model';
import { getEntities as getMVeneers } from 'app/entities/m-veneer/m-veneer.reducer';
import { ITransaksi } from 'app/shared/model/transaksi.model';
import { getEntities as getTransaksis } from 'app/entities/transaksi/transaksi.reducer';
import { getEntity, updateEntity, createEntity, reset } from './t-veneer.reducer';
import { ITVeneer } from 'app/shared/model/t-veneer.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITVeneerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITVeneerUpdateState {
  isNew: boolean;
  mveneerId: string;
  transaksiId: string;
}

export class TVeneerUpdate extends React.Component<ITVeneerUpdateProps, ITVeneerUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      mveneerId: '0',
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

    this.props.getMVeneers();
    this.props.getTransaksis();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { tVeneerEntity } = this.props;
      const entity = {
        ...tVeneerEntity,
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
    this.props.history.push('/entity/t-veneer');
  };

  render() {
    const { tVeneerEntity, mVeneers, transaksis, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.tVeneer.home.createOrEditLabel">Create or edit a TVeneer</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tVeneerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="t-veneer-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="qtyLabel" for="qty">
                    Qty
                  </Label>
                  <AvField id="t-veneer-qty" type="string" className="form-control" name="qty" />
                </AvGroup>
                <AvGroup>
                  <Label id="volumeLabel" for="volume">
                    Volume
                  </Label>
                  <AvField id="t-veneer-volume" type="string" className="form-control" name="volume" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaBeliLabel" for="hargaBeli">
                    Harga Beli
                  </Label>
                  <AvField id="t-veneer-hargaBeli" type="string" className="form-control" name="hargaBeli" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaTotalLabel" for="hargaTotal">
                    Harga Total
                  </Label>
                  <AvField id="t-veneer-hargaTotal" type="string" className="form-control" name="hargaTotal" />
                </AvGroup>
                <AvGroup>
                  <Label id="inoutLabel">Inout</Label>
                  <AvInput
                    id="t-veneer-inout"
                    type="select"
                    className="form-control"
                    name="inout"
                    value={(!isNew && tVeneerEntity.inout) || 'IN'}
                  >
                    <option value="IN">IN</option>
                    <option value="OUT">OUT</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="mveneer.id">Mveneer</Label>
                  <AvInput id="t-veneer-mveneer" type="select" className="form-control" name="mveneer.id">
                    <option value="" key="0" />
                    {mVeneers
                      ? mVeneers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="transaksi.invoiceno">Transaksi</Label>
                  <AvInput id="t-veneer-transaksi" type="select" className="form-control" name="transaksi.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/t-veneer" replace color="info">
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
  mVeneers: storeState.mVeneer.entities,
  transaksis: storeState.transaksi.entities,
  tVeneerEntity: storeState.tVeneer.entity,
  loading: storeState.tVeneer.loading,
  updating: storeState.tVeneer.updating,
  updateSuccess: storeState.tVeneer.updateSuccess
});

const mapDispatchToProps = {
  getMVeneers,
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
)(TVeneerUpdate);
