import React, { Component } from "react";
import Navbar from "./components/Navbar";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Home from "./components/Home";
import BookList from "./components/BookList";
import Registration from "./components/Registration";
import Login from "./components/Login";
import Details from "./components/Details";
import Administrator from "./components/Administrator";
import BookstoreOrders from "./components/BookstoreOrders";
import AdministratorLibrary from "./components/AdministratorLibrary";
import LibraryOrders from "./components/LibraryOrders";
import Cookies from "js-cookie";
import AdministratorLibraryBookList from "./components/AdministratorLibraryBookList";

class App extends Component {
  state = {
    name: "",
    loggedIn: false,
    accountType: "",
    customerId: ""
  };

  handleLogIn = (name, accountType, sessionKey, customerId) => {
    this.setState({ name: name });
    this.setState({ accountType: accountType });
    this.setState({ loggedIn: true });
    Cookies.set("sessionKey", sessionKey + "");
    this.setState({ customerId: customerId });
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
            <Route
              path="/search/:search_term"
              customerId={this.state.customerId}
              render={props => <BookList customerId={this.state.customerId} />}
            />
            <Route
              path="/advancedSearch/:title?/:author?/:year?/:isbn?/:category?"
              customerId={this.state.customerId}
              render={props => (
                <BookList {...props} customerId={this.state.customerId} />
              )}
            />
            <Route path="/bookstore_orders" component={BookstoreOrders} />
            <Route path="/library_orders" component={LibraryOrders} />
            <Route path="/registration" component={Registration} />
            <Route
              path="/login"
              handleLogIn={this.handleLogIn}
              render={props => <Login handleLogIn={this.handleLogIn} />}
            />
            <Route path="/details/:search_term" component={Details} />
            <Route path="/bookstore_admin" component={Administrator} />
            <Route
              path="/library_admin/bookList/:isbn"
              component={AdministratorLibraryBookList}
            />
            <Route path="/library_admin" component={AdministratorLibrary} />
          </Switch>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
