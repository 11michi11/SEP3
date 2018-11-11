import React from 'react';
import {Nav, NavItem, NavLink} from 'reactstrap';

const Navbar = () => {

    return (
    <Nav>
          <NavItem>
            <NavLink href="/">Home</NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="/contact">Contact</NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="/about">About us</NavLink>
          </NavItem>
        </Nav>
    )

}

export default Navbar