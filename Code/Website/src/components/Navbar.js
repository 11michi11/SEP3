import React, { Component } from "react";
import { withRouter, NavLink } from "react-router-dom";
import https from "https";
import axios from "axios";
import mainLogo from "./readwithpanda.png";

class Navbar extends Component {
  state = {};
  componentDidMount() {
    console.log(this.props.loggedIn);
  }

  handleLogout = e => {
    //e.preventDefault();
    this.props.handleLogOut();
  };
  render() {
    let validatedNavbar;
    const logoStyle = {
      maxWidth: "80px"
    };
    if (this.props.loggedIn && this.props.accountType === "Customer") {
      validatedNavbar = (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <a className="navbar-brand" href="#">
            Welcome back, {this.props.name}
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
                  <NavLink to="/" onClick={this.handleLogout}>
                    Log out
                  </NavLink>
                </span>
              </li>
            </ul>
          </div>
        </nav>
      );
    } else if (
      this.props.loggedIn &&
      this.props.accountType === "LibraryAdmin"
    ) {
      validatedNavbar = (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <a className="navbar-brand" href="#">
            Welcome back, Library Admin
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
                  <NavLink to="/library_orders">Orders</NavLink>
                </span>
              </li>
              <li className="nav-item">
                <span className="nav-link">
                  <NavLink to="/library_admin">Control Panel</NavLink>
                </span>
              </li>
              <li className="nav-item">
                <span className="nav-link">
                  <NavLink to="/" onClick={this.handleLogout}>
                    Log out
                  </NavLink>
                </span>
              </li>
            </ul>
          </div>
        </nav>
      );
    } else if (
      this.props.loggedIn &&
      this.props.accountType === "BookStoreAdmin"
    ) {
      validatedNavbar = (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <a className="navbar-brand" href="#">
            Welcome back, Bookstore Admin
          </a>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item active">
                <span className="nav-link">
                  <NavLink to="/bookstore_admin">Home</NavLink>
                </span>
              </li>
              <li className="nav-item">
                <span className="nav-link">
                  <NavLink to="/bookstore_orders">Orders</NavLink>
                </span>
              </li>
              <li className="nav-item">
                <span className="nav-link">
                  <NavLink to="/" onClick={this.handleLogout}>
                    Log out
                  </NavLink>
                </span>
              </li>
            </ul>
          </div>
        </nav>
      );
    } else {
      validatedNavbar = (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <a className="navbar-brand" href="#">
            <img src={mainLogo} alt="logo" style={logoStyle} />
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
                  <NavLink to="/login">Log In</NavLink>
                </span>
              </li>
              <li className="nav-item">
                <span className="nav-link">
                  <NavLink to="/registration">Sign up</NavLink>
                </span>
              </li>
            </ul>
          </div>
        </nav>
      );
    }

    return <div>{validatedNavbar}</div>;
  }
}

export default withRouter(Navbar);
