import React, { Component } from "react";
import axios from "axios";
import https from "https";

import { Form, FormGroup, Input, Button } from "reactstrap";
class Registration extends Component {
  handleSubmit = e => {
    e.preventDefault();
    const agent = new https.Agent({
      rejectUnauthorized: false
    });
    axios
      .post(
        "https://localhost:8080/signUp",
        {
          name: this.state.name,
          email: this.state.email,
          address: this.state.address,
          phoneNum: this.state.phoneNum,
          password: this.state.password
        },
        { crossdomain: true, httpsAgent: agent }
      )
      .then(res => {
        var str = "SUCCEEDED!";

        window.alert(`REGISTRATION  ${str}!`);
      })
      .catch(error => {
        window.alert(`${error}
                      Something went wrong, registration failed - check the fields please
                       `);
      });
  };
  state = {
    name: "",
    email: "",
    password: "",
    address: "",
    phoneNum: ""
  };

  handleUserDataChange = e => {
    switch (e.target.id) {
      case "name":
        {
          this.setState({
            name: e.target.value
          });
        }
        break;
      case "password":
        {
          this.setState({
            password: e.target.value
          });
        }
        break;
      case "email":
        {
          this.setState({
            email: e.target.value
          });
        }
        break;
      case "address":
        {
          this.setState({
            address: e.target.value
          });
        }
        break;
      case "phoneNum":
        {
          this.setState({
            phoneNum: e.target.value
          });
        }
        break;
    }
    console.log(this.state);
  };

  render() {
    return (
      <div className="container">
        <div className="row">
          <div className="col-sm-6 offset-sm-3 pt-5">
            <h2 className="text-center display-4">Become a member</h2>
          </div>
        </div>
        <div className="row">
          <div className="offset-sm-3 col-sm-6 p-5">
            <p>Enter your credentials in order to sign up :</p>
            <Form>
              <FormGroup>
                <p>
                  Name:
                  <Input
                    type="text"
                    value={this.state.name}
                    onChange={this.handleUserDataChange}
                    name="registration"
                    id="name"
                    placeholder="name"
                  />
                </p>
                <p />
                <p>
                  Password:
                  <Input
                    type="password"
                    value={this.state.password}
                    onChange={this.handleUserDataChange}
                    name="password"
                    id="password"
                    placeholder="password"
                  />
                </p>
                <p />
                <p>
                  Email:
                  <Input
                    type="text"
                    value={this.state.email}
                    onChange={this.handleUserDataChange}
                    name="registration"
                    id="email"
                    placeholder="email"
                  />
                </p>
                <p />
                <p>
                  Address:
                  <Input
                    type="text"
                    value={this.state.address}
                    onChange={this.handleUserDataChange}
                    name="registration"
                    id="address"
                    placeholder="address"
                  />
                </p>
                <p />
                <p>
                  Phone number:
                  <Input
                    type="text"
                    value={this.state.phoneNum}
                    onChange={this.handleUserDataChange}
                    name="registration"
                    id="phoneNum"
                    placeholder="phone number"
                  />
                </p>
                <div className=" text-center">
                  <Button
                    color="primary"
                    size="sm"
                    onClick={e => this.handleSubmit(e)}
                  >
                    Sign Up
                  </Button>
                </div>
              </FormGroup>
            </Form>
          </div>
        </div>
      </div>
    );
  }
}

export default Registration;
