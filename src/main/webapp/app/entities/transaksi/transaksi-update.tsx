import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IMShift } from 'app/shared/model/m-shift.model';
import { getEntities as getMShifts } from 'app/entities/m-shift/m-shift.reducer';
import { IMSupplier } from 'app/shared/model/m-supplier.model';
import { getEntities as getMSuppliers } from 'app/entities/m-supplier/m-supplier.reducer';
import { IMCustomer } from 'app/shared/model/m-customer.model';
import { getEntities as getMCustomers } from 'app/entities/m-customer/m-customer.reducer';
import { IMPaytype } from 'app/shared/model/m-paytype.model';
import { getEntities as getMPaytypes } from 'app/entities/m-paytype/m-paytype.reducer';
import { IMEkspedisi } from 'app/shared/model/m-ekspedisi.model';
import { getEntities as getMEkspedisis } from 'app/entities/m-ekspedisi/m-ekspedisi.reducer';
import { IMTeam } from 'app/shared/model/m-team.model';
import { getEntities as getMTeams } from 'app/entities/m-team/m-team.reducer';
import { IMPajak } from 'app/shared/model/m-pajak.model';
import { getEntities as getMPajaks } from 'app/entities/m-pajak/m-pajak.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './transaksi.reducer';
import { ITransaksi } from 'app/shared/model/transaksi.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITransaksiUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITransaksiUpdateState {
  isNew: boolean;
  createdbyId: string;
  shiftId: string;
  supplierId: string;
  customerId: string;
  paytypeId: string;
  ekpedisiId: string;
  teamId: string;
  pajakId: string;
}

export class TransaksiUpdate extends React.Component<ITransaksiUpdateProps, ITransaksiUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdbyId: '0',
      shiftId: '0',
      supplierId: '0',
      customerId: '0',
      paytypeId: '0',
      ekpedisiId: '0',
      teamId: '0',
      pajakId: '0',
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
    this.props.getMShifts();
    this.props.getMSuppliers();
    this.props.getMCustomers();
    this.props.getMPaytypes();
    this.props.getMEkspedisis();
    this.props.getMTeams();
    this.props.getMPajaks();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { transaksiEntity } = this.props;
      const entity = {
        ...transaksiEntity,
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
    this.props.history.push('/entity/transaksi');
  };

  render() {
    const {
      transaksiEntity,
      users,
      mShifts,
      mSuppliers,
      mCustomers,
      mPaytypes,
      mEkspedisis,
      mTeams,
      mPajaks,
      loading,
      updating
    } = this.props;
    const { isNew } = this.state;

    const { invoicefile, invoicefileContentType } = transaksiEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="ssv3App.transaksi.home.createOrEditLabel">Create or edit a Transaksi</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : transaksiEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="transaksi-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="tipeLabel">Tipe</Label>
                  <AvInput
                    id="transaksi-tipe"
                    type="select"
                    className="form-control"
                    name="tipe"
                    value={(!isNew && transaksiEntity.tipe) || 'PEMBELIAN'}
                  >
                    <option value="PEMBELIAN">PEMBELIAN</option>
                    <option value="PENJUALAN">PENJUALAN</option>
                    <option value="PRODUKSI">PRODUKSI</option>
                    <option value="STOCKOPNAME">STOCKOPNAME</option>
                    <option value="REFUND">REFUND</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="categoryLabel">Category</Label>
                  <AvInput
                    id="transaksi-category"
                    type="select"
                    className="form-control"
                    name="category"
                    value={(!isNew && transaksiEntity.category) || 'LOG'}
                  >
                    <option value="LOG">LOG</option>
                    <option value="VENEER">VENEER</option>
                    <option value="PLYWOOD">PLYWOOD</option>
                    <option value="MATERIAL">MATERIAL</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="tanggalLabel" for="tanggal">
                    Tanggal
                  </Label>
                  <AvField
                    id="transaksi-tanggal"
                    type="date"
                    className="form-control"
                    name="tanggal"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="invoicenoLabel" for="invoiceno">
                    Invoiceno
                  </Label>
                  <AvField id="transaksi-invoiceno" type="text" name="invoiceno" />
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="invoicefileLabel" for="invoicefile">
                      Invoicefile
                    </Label>
                    <br />
                    {invoicefile ? (
                      <div>
                        <a onClick={openFile(invoicefileContentType, invoicefile)}>
                          <img src={`data:${invoicefileContentType};base64,${invoicefile}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {invoicefileContentType}, {byteSize(invoicefile)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('invoicefile')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_invoicefile" type="file" onChange={this.onBlobChange(true, 'invoicefile')} accept="image/*" />
                    <AvInput type="hidden" name="invoicefile" value={invoicefile} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="nopolLabel" for="nopol">
                    Nopol
                  </Label>
                  <AvField id="transaksi-nopol" type="text" name="nopol" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="transaksi-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && transaksiEntity.status) || 'ACT'}
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
                  <AvField id="transaksi-createdOn" type="date" className="form-control" name="createdOn" />
                </AvGroup>
                <AvGroup>
                  <Label for="createdby.login">Createdby</Label>
                  <AvInput id="transaksi-createdby" type="select" className="form-control" name="createdby.id">
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
                  <Label for="shift.nama">Shift</Label>
                  <AvInput id="transaksi-shift" type="select" className="form-control" name="shift.id">
                    <option value="" key="0" />
                    {mShifts
                      ? mShifts.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="supplier.nama">Supplier</Label>
                  <AvInput id="transaksi-supplier" type="select" className="form-control" name="supplier.id">
                    <option value="" key="0" />
                    {mSuppliers
                      ? mSuppliers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="customer.nama">Customer</Label>
                  <AvInput id="transaksi-customer" type="select" className="form-control" name="customer.id">
                    <option value="" key="0" />
                    {mCustomers
                      ? mCustomers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="paytype.nama">Paytype</Label>
                  <AvInput id="transaksi-paytype" type="select" className="form-control" name="paytype.id">
                    <option value="" key="0" />
                    {mPaytypes
                      ? mPaytypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="ekpedisi.nama">Ekpedisi</Label>
                  <AvInput id="transaksi-ekpedisi" type="select" className="form-control" name="ekpedisi.id">
                    <option value="" key="0" />
                    {mEkspedisis
                      ? mEkspedisis.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="team.nama">Team</Label>
                  <AvInput id="transaksi-team" type="select" className="form-control" name="team.id">
                    <option value="" key="0" />
                    {mTeams
                      ? mTeams.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="pajak.nama">Pajak</Label>
                  <AvInput id="transaksi-pajak" type="select" className="form-control" name="pajak.id">
                    <option value="" key="0" />
                    {mPajaks
                      ? mPajaks.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nama}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/transaksi" replace color="info">
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
  mShifts: storeState.mShift.entities,
  mSuppliers: storeState.mSupplier.entities,
  mCustomers: storeState.mCustomer.entities,
  mPaytypes: storeState.mPaytype.entities,
  mEkspedisis: storeState.mEkspedisi.entities,
  mTeams: storeState.mTeam.entities,
  mPajaks: storeState.mPajak.entities,
  transaksiEntity: storeState.transaksi.entity,
  loading: storeState.transaksi.loading,
  updating: storeState.transaksi.updating,
  updateSuccess: storeState.transaksi.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getMShifts,
  getMSuppliers,
  getMCustomers,
  getMPaytypes,
  getMEkspedisis,
  getMTeams,
  getMPajaks,
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
)(TransaksiUpdate);
