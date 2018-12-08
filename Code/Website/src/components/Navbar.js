import React, { Component } from "react";
import { withRouter, NavLink } from "react-router-dom";

class Navbar extends Component {
  state = {};
  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <a className="navbar-brand" href="/">
          Navbar
        </a>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item active">
              <span className="nav-link">
                <NavLink to="/">Home</NavLink>
              </span>
            </li>
            <li className="nav-item">
              <span className="nav-link">
                <NavLink to="/orders">Orders</NavLink>
              </span>
            </li>
            <li className="nav-item">
              <span className="nav-link">
                <NavLink to="/registration">Join us</NavLink>
              </span>
            </li>
            <li className="nav-item">
              <span className="nav-link">
                <NavLink to="/login">Login</NavLink>
              </span>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
}

export default withRouter(Navbar);
