import React, { Component } from 'react';

import {Form, FormGroup, Input, Button} from 'reactstrap'
class Registration extends Component {

    handleSubmit = e => {

    }
    state = { 
        name: "",
        password: "",
        address: "",
        phoneNo: ""

     }
    render() { 
        return (  <div className="container">


        <div className="row">
            <div className="col-sm-6 offset-sm-3 pt-5">
                <h2 className="text-center display-4">Become a member</h2>
            </div>
            
        </div>
        <div className="row">
        <div className="offset-sm-3 col-sm-6 p-5" >
              <p>Enter your credentials in order to sign up :</p>
                <Form>
                <FormGroup>
                    <p>Name:
                    <Input type="text" value={this.state.value} onChange={this.handleSearch} name="registration" id="name" 
                    placeholder="name" /></p>
                    <p></p>
                    <p>Email:
                    <Input type="text" value={this.state.value} onChange={this.handleSearch} name="registration" id="email" 
                    placeholder="email" /></p>
                    <p></p>
                    <p>Address:
                    <Input type="text" value={this.state.value} onChange={this.handleSearch} name="registration" id="address" 
                    placeholder="address" /></p>
                    <p></p>
                    <p>Phone number:
                    <Input type="text" value={this.state.value} onChange={this.handleSearch} name="registration" id="phoneNum" 
                    placeholder="phone number" /></p>
                    <div className=" text-center" >
                    <Button color="primary" size="sm"  onClick={e => this.handleSubmit(e)}>Sign Up</Button>
                    </div>
                    </FormGroup>
                </Form>
            </div>
        </div>
        </div> );
    }
}
 
export default Registration;