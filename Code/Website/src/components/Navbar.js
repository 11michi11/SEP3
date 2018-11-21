import React from 'react';
import {withRouter, NavLink} from 'react-router-dom'

const Navbar = (props) => {

  console.log(this);
    return (
     
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
  <a className="navbar-brand" href="/">Navbar</a>
  <div className="collapse navbar-collapse" id="navbarNav">
    <ul className="navbar-nav">
      <li className="nav-item active">
      <span className="nav-link"><NavLink to="/">Home</NavLink></span>
      </li>
      <li className="nav-item">
      <span className="nav-link"><NavLink to="/login">Login</NavLink></span>
      
      </li>
      <li className="nav-item">
      <span className="nav-link"><NavLink to="/registration">Join us</NavLink></span>
      </li>
      
    </ul>
  </div>
</nav>
    )

}

export default withRouter(Navbar)