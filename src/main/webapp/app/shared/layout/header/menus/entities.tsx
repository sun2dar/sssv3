import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/m-supplier">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Supplier
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-team">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Team
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-shift">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Shift
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-ekspedisi">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Ekspedisi
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-customer">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Customer
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-paytype">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Paytype
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-log-type">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Log Type
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-log-category">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Log Category
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-log">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Log
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/t-log">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;T Log
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-veneer-category">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Veneer Category
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-veneer">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Veneer
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/t-veneer">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;T Veneer
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-plywood-grade">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Plywood Grade
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-plywood-category">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Plywood Category
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-plywood">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Plywood
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/t-plywood">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;T Plywood
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-satuan">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Satuan
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-material-type">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Material Type
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-material">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Material
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/t-material">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;T Material
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-kas">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Kas
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/t-kas">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;T Kas
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-constant">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Constant
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-operasional-type">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Operasional Type
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/t-operasional">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;T Operasional
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-utang">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Utang
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/t-utang">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;T Utang
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/transaksi">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Transaksi
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/t-bongkar">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;T Bongkar
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-message">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Message
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/m-pajak">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;M Pajak
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
