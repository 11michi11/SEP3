import React, { Component } from "react";
import Navbar from "./components/Navbar";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Home from "./components/Home";
import BookList from "./components/BookList";
import Registration from "./components/Registration";
import Login from "./components/Login";
import Details from "./components/Details";
import Administrator from "./components/Administrator";
import Orders from "./components/Orders";
import AdministratorLibrary from "./components/AdministratorLibrary";

class App extends Component {
  state = {
    name: "",
    loggedIn: false,
    accountType: ""
  };

  handleLogIn = (name, accountType) => {
    this.setState({ name: name });
    this.setState({ accountType: accountType });
    this.setState({ loggedIn: true });
  };

  render() {
    return (
      <BrowserRouter>
        <div>
          <Navbar
            loggedIn={this.state.loggedIn}
            name={this.state.name}
            accountType={this.state.accountType}
          />

          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/books" component={BookList} />
            <Route path="/search/:search_term" component={BookList} />
            <Route
              path="/advancedSearch/:title?/:author?/:year?/:isbn?/:category?"
              component={BookList}
            />
            <Route path="/orders" component={Orders} />
            <Route path="/registration" component={Registration} />
            <Route
              path="/login"
              handleLogIn={this.handleLogIn}
              render={props => <Login handleLogIn={this.handleLogIn} />}
            />
            <Route path="/details/:search_term" component={Details} />
            <Route path="/bookstore_admin" component={Administrator} />
            <Route path="/library_admin" component={AdministratorLibrary} />
          </Switch>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
