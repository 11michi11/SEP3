import React, { Component } from 'react';
import Navbar from './components/Navbar';
import {BrowserRouter,Route, Switch} from 'react-router-dom';
import Home from './components/Home';
import BookList from './components/BookList';
import Registration from './components/Registration';
import Login from './components/Login';
import Details from './components/Details';
import Administrator from './components/Administrator';


class App extends Component {
  render() {
    return (
      <BrowserRouter>
      <div>

      <Navbar/>
      
        <Switch>
          <Route exact path="/" component={Home}/>
          <Route path="/books" component={BookList}></Route>
          <Route path="/search/:search_term" component={BookList}></Route>
          <Route path="/registration" component={Registration}></Route>
          <Route path="/login" component={Login}></Route>
          <Route path="/details/:search_term" component={Details}></Route>
          <Route path="/admin" component={Administrator}></Route>
        </Switch>

      </div>
     

      
      </BrowserRouter>
    );
  }
}

export default App;
