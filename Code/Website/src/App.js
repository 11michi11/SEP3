import React, { Component } from 'react';
import Navbar from './components/Navbar';
import {BrowserRouter,Route, Switch} from 'react-router-dom';
import Home from './components/Home';
import BookList from './components/BookList';

class App extends Component {
  render() {
    return (
      <BrowserRouter>
      <div>

      <Navbar/>
      
        <Switch>
          <Route exact path="/" component={Home}/>
          <Route path="/books" component={BookList}></Route>
        </Switch>

      </div>
     

      
      </BrowserRouter>
    );
  }
}

export default App;
