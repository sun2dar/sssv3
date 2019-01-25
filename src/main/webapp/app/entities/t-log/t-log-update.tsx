import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMLogCategory } from 'app/shared/model/m-log-category.model';
import { getEntities as getMLogCategories } from 'app/entities/m-log-category/m-log-category.reducer';
import { ITransaksi } from 'app/shared/model/transaksi.model';
import { getEntities as getTransaksis } from 'app/entities/transaksi/transaksi.reducer';
import { getEntity, updateEntity, createEntity, reset } from './t-log.reducer';
import { ITLog } from 'app/shared/model/t-log.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITLogUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITLogUpdateState {
  isNew: boolean;
  mlogcatId: string;
  transaksiId: string;
}

export class TLogUpdate extends React.Component<ITLogUpdateProps, ITLogUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      mlogcatId: '0',
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

    this.props.getMLogCategories();
    this.props.getTransaksis();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { tLogEntity } = this.props;
      const entity = {
        ...tLogEntity,
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
    this.props.history.push('/entity/t-log');
  };

  render() {
    const { tLogEntity, mLogCategories, transaksis, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.tLog.home.createOrEditLabel">Create or edit a TLog</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tLogEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="t-log-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="panjangLabel" for="panjang">
                    Panjang
                  </Label>
                  <AvField id="t-log-panjang" type="string" className="form-control" name="panjang" />
                </AvGroup>
                <AvGroup>
                  <Label id="qtyLabel" for="qty">
                    Qty
                  </Label>
                  <AvField id="t-log-qty" type="string" className="form-control" name="qty" />
                </AvGroup>
                <AvGroup>
                  <Label id="volumeLabel" for="volume">
                    Volume
                  </Label>
                  <AvField id="t-log-volume" type="string" className="form-control" name="volume" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaBeliLabel" for="hargaBeli">
                    Harga Beli
                  </Label>
                  <AvField id="t-log-hargaBeli" type="string" className="form-control" name="hargaBeli" />
                </AvGroup>
                <AvGroup>
                  <Label id="hargaTotalLabel" for="hargaTotal">
                    Harga Total
                  </Label>
                  <AvField id="t-log-hargaTotal" type="string" className="form-control" name="hargaTotal" />
                </AvGroup>
                <AvGroup>
                  <Label id="inoutLabel">Inout</Label>
                  <AvInput
                    id="t-log-inout"
                    type="select"
                    className="form-control"
                    name="inout"
                    value={(!isNew && tLogEntity.inout) || 'IN'}
                  >
                    <option value="IN">IN</option>
                    <option value="OUT">OUT</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="mlogcat.nama">Mlogcat</Label>
                  <AvInput id="t-log-mlogcat" type="select" className="form-control" name="mlogcat.id">
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
                <AvGroup>
                  <Label for="transaksi.invoiceno">Transaksi</Label>
                  <AvInput id="t-log-transaksi" type="select" className="form-control" name="transaksi.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/t-log" replace color="info">
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
  mLogCategories: storeState.mLogCategory.entities,
  transaksis: storeState.transaksi.entities,
  tLogEntity: storeState.tLog.entity,
  loading: storeState.tLog.loading,
  updating: storeState.tLog.updating,
  updateSuccess: storeState.tLog.updateSuccess
});

const mapDispatchToProps = {
  getMLogCategories,
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
)(TLogUpdate);
