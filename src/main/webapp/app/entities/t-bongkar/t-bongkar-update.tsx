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
import { getEntity, updateEntity, createEntity, reset } from './t-bongkar.reducer';
import { ITBongkar } from 'app/shared/model/t-bongkar.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITBongkarUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITBongkarUpdateState {
  isNew: boolean;
  transaksiId: string;
}

export class TBongkarUpdate extends React.Component<ITBongkarUpdateProps, ITBongkarUpdateState> {
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
      const { tBongkarEntity } = this.props;
      const entity = {
        ...tBongkarEntity,
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
    this.props.history.push('/entity/t-bongkar');
  };

  render() {
    const { tBongkarEntity, transaksis, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.tBongkar.home.createOrEditLabel">Create or edit a TBongkar</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tBongkarEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="t-bongkar-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="rateUpahLabel" for="rateUpah">
                    Rate Upah
                  </Label>
                  <AvField id="t-bongkar-rateUpah" type="string" className="form-control" name="rateUpah" />
                </AvGroup>
                <AvGroup>
                  <Label id="volumeLabel" for="volume">
                    Volume
                  </Label>
                  <AvField id="t-bongkar-volume" type="string" className="form-control" name="volume" />
                </AvGroup>
                <AvGroup>
                  <Label for="transaksi.id">Transaksi</Label>
                  <AvInput id="t-bongkar-transaksi" type="select" className="form-control" name="transaksi.id">
                    <option value="" key="0" />
                    {transaksis
                      ? transaksis.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/t-bongkar" replace color="info">
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
  tBongkarEntity: storeState.tBongkar.entity,
  loading: storeState.tBongkar.loading,
  updating: storeState.tBongkar.updating,
  updateSuccess: storeState.tBongkar.updateSuccess
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
)(TBongkarUpdate);
