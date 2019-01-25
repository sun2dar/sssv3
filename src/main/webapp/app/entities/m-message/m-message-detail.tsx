import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-message.reducer';
import { IMMessage } from 'app/shared/model/m-message.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMMessageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MMessageDetail extends React.Component<IMMessageDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { mMessageEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MMessage [<b>{mMessageEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="message">Message</span>
            </dt>
            <dd>{mMessageEntity.message}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{mMessageEntity.status}</dd>
            <dt>
              <span id="createdOn">Created On</span>
            </dt>
            <dd>
              <TextFormat value={mMessageEntity.createdOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="modifiedOn">Modified On</span>
            </dt>
            <dd>
              <TextFormat value={mMessageEntity.modifiedOn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Createdby</dt>
            <dd>{mMessageEntity.createdby ? mMessageEntity.createdby.login : ''}</dd>
            <dt>Modifiedby</dt>
            <dd>{mMessageEntity.modifiedby ? mMessageEntity.modifiedby.login : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/m-message" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-message/${mMessageEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ mMessage }: IRootState) => ({
  mMessageEntity: mMessage.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MMessageDetail);
