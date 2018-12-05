import React, { Component } from 'react';
import {Form, FormGroup, Input, Button, NavLink} from 'reactstrap'

class Login extends Component {
    state = {  }
    render() { 
        return (
            <div className="container">


            <div className="row">
                <div className="col-sm-6 offset-sm-3 pt-5">
                    <h2 className="text-center display-4">Log in</h2>
                </div>
                
            </div>
            <div className="row">
            <div className="offset-sm-3 col-sm-6 p-5" >
                  <p>Enter your credentials in order to log in:</p>
                    <Form>
                    <FormGroup>
                        <p>Email:
                        <Input type="text" value={this.state.value} onChange={this.handleSearch} name="registration" id="email" 
                        placeholder="email" /></p>
                        <p/>
                        <p>Password:
                        <Input type="password" value={this.state.value} onChange={this.handleSearch} name="password" id="password" placeholder="password" /></p>
                         <p/>
                            <div className="text-center" >
                            <Button color="primary" size="sm"  onClick={e => this.handleSubmit(e)}>Log in</Button>
                            <NavLink  href="#">Forgot your password?</NavLink>
                            </div>
                    </FormGroup>
                    </Form>
            </div>
            </div>
            </div> 
         );
    }
}
 
export default Login;