import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITransaksi } from 'app/shared/model/transaksi.model';
import { getEntities as getTransaksis } from 'app/entities/transaksi/transaksi.reducer';
import { getEntity, updateEntity, createEntity, reset } from './m-utang.reducer';
import { IMUtang } from 'app/shared/model/m-utang.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMUtangUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMUtangUpdateState {
  isNew: boolean;
  transaksiId: string;
}

export class MUtangUpdate extends React.Component<IMUtangUpdateProps, IMUtangUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getTransaksis();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { mUtangEntity } = this.props;
      const entity = {
        ...mUtangEntity,
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
    this.props.history.push('/entity/m-utang');
  };

  render() {
    const { mUtangEntity, transaksis, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.mUtang.home.createOrEditLabel">Create or edit a MUtang</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : mUtangEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="m-utang-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nominalLabel" for="nominal">
                    Nominal
                  </Label>
                  <AvField id="m-utang-nominal" type="string" className="form-control" name="nominal" />
                </AvGroup>
                <AvGroup>
                  <Label id="sisaLabel" for="sisa">
                    Sisa
                  </Label>
                  <AvField id="m-utang-sisa" type="string" className="form-control" name="sisa" />
                </AvGroup>
                <AvGroup>
                  <Label id="duedateLabel" for="duedate">
                    Duedate
                  </Label>
                  <AvField id="m-utang-duedate" type="date" className="form-control" name="duedate" />
                </AvGroup>
                <AvGroup>
                  <Label id="tipeLabel">Tipe</Label>
                  <AvInput
                    id="m-utang-tipe"
                    type="select"
                    className="form-control"
                    name="tipe"
                    value={(!isNew && mUtangEntity.tipe) || 'HUTANG'}
                  >
                    <option value="HUTANG">HUTANG</option>
                    <option value="PIUTANG">PIUTANG</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="m-utang-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && mUtangEntity.status) || 'ACT'}
                  >
                    <option value="ACT">ACT</option>
                    <option value="DIS">DIS</option>
                    <option value="DEL">DEL</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="transaksi.invoiceno">Transaksi</Label>
                  <AvInput id="m-utang-transaksi" type="select" className="form-control" name="transaksi.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/m-utang" replace color="info">
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
  transaksis: storeState.transaksi.entities,
  mUtangEntity: storeState.mUtang.entity,
  loading: storeState.mUtang.loading,
  updating: storeState.mUtang.updating,
  updateSuccess: storeState.mUtang.updateSuccess
});

const mapDispatchToProps = {
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
)(MUtangUpdate);
