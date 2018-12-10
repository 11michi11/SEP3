import React, { Component } from "react";
import { withRouter, NavLink } from "react-router-dom";

class Navbar extends Component {
  state = {};
  componentDidMount() {
    console.log(this.props.loggedIn);
  }

  handleLogout = e => {
    this.props.loggedIn = false;
    this.props.name = "";
    this.props.accountType = "";
  };
  render() {
    let validatedNavbar;
    if (this.props.loggedIn && this.props.accountType === "Customer") {
      validatedNavbar = (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <a className="navbar-brand" href="/">
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
                  <NavLink to="/profile">Profile</NavLink>
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
          <a className="navbar-brand" href="/">
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
                  <NavLink to="/librrary_admin">Control Panel</NavLink>
                </span>
              </li>
              <li className="nav-item">
                <span className="nav-link">
                  <NavLink to="/logout" onClick={this.handleLogout}>
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
          <a className="navbar-brand" href="/">
            Welcome back, Bookstore Admin
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
                  <NavLink to="/bookstore_orders">Orders</NavLink>
                </span>
              </li>
              <li className="nav-item">
                <span className="nav-link">
                  <NavLink to="/bookstore_admin">Control Panel</NavLink>
                </span>
              </li>
              <li className="nav-item">
                <span className="nav-link">
                  <NavLink to="/logout" onClick={this.handleLogout}>
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
          <a className="navbar-brand" href="/">
            Read with Panda
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
                  <NavLink to="/bookstore_orders">Orders</NavLink>
                </span>
              </li>
              <li className="nav-item">
                <span className="nav-link">
                  <NavLink to="/login">Log In</NavLink>
                </span>
              </li>
              <li className="nav-item">
                <span className="nav-link">
                  <NavLink to="/registration">Join us</NavLink>
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
