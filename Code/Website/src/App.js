import React, { Component } from "react";
import Navbar from "./components/Navbar";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Home from "./components/Home";
import BookList from "./components/BookList";
import Registration from "./components/Registration";
import Login from "./components/Login";
import Details from "./components/Details";
import Administrator from "./components/Administrator";

class App extends Component {
  state = {
    name: "",
    loggedIn: false
  };

  handleLogIn = e => {
    console.log("blah222");
  };

  render() {
    return (
      <BrowserRouter>
        <div>
          <Navbar loggedIn={this.state.loggedIn} name={this.state.name} />

          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/books" component={BookList} />
            <Route path="/search/:search_term" component={BookList} />
            <Route path="/advancedSearch/:search_term" component={BookList} />
            <Route path="/registration" component={Registration} />
            <Route
              path="/login"
              handleLogIn={this.handleLogIn}
              render={props => <Login handleLogIn={this.handleLogIn} />}
            />
            <Route path="/details/:search_term" component={Details} />
            <Route path="/admin" component={Administrator} />
          </Switch>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
