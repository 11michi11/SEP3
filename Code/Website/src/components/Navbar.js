import React from 'react';
import {Nav, NavItem, NavLink} from 'reactstrap';
import {withRouter} from 'react-router-dom'

const Navbar = (props) => {

  console.log(this);
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

export default withRouter(Navbar)