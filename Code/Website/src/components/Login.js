import React, { Component } from "react";
import { Form, FormGroup, Input, Button, NavLink } from "reactstrap";
import { withRouter } from "react-router-dom";
import axios from "axios";
import https from "https";

class Login extends Component {
  componentDidMount() {
    console.log(this.props);
  }
  state = {
    email: "",
    password: ""
  };

  handleSubmit = e => {
    e.preventDefault();
    console.log(this.state.email + " " + this.state.password);
    const agent = new https.Agent({
      rejectUnauthorized: false
    });
    axios
      .post(
        "https://localhost:8080/login",
        {
          email: this.state.email,
          password: this.state.password,
          withCredentials: true
        },
        { crossdomain: true, httpsAgent: agent }
      )
      .then(res => {
        var str = "SUCCESS!";
        console.log(res);
        this.props.handleLogIn(
          res.data.name,
          res.data.userType,
          res.data.sessionKey,
          res.data.userId,
          res.data.url
        );

        window.alert(`${str}`);
        this.props.history.push("/");
      })
      .catch(error => {
        window.alert(`${error}
                       Something went wrong
                       `);
      });
  };

  handleLogInkFormChange = e => {
    switch (e.target.id) {
      case "email":
        {
          this.setState({
            email: e.target.value
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
    }
  };

  render() {
    return (
      <div className="container">
        <div className="row">
          <div className="col-sm-6 offset-sm-3 pt-5">
            <h2 className="text-center display-4">Log in</h2>
          </div>
        </div>
        <div className="row">
          <div className="offset-sm-3 col-sm-6 p-5">
            <p>Enter your credentials in order to log in:</p>
            <Form>
              <FormGroup>
                <p>
                  Email:
                  <Input
                    type="text"
                    value={this.state.email}
                    onChange={this.handleLogInkFormChange}
                    name="emailInput"
                    id="email"
                    placeholder="email"
                  />
                </p>
                <p />
                <p>
                  Password:
                  <Input
                    type="password"
                    value={this.state.password}
                    onChange={this.handleLogInkFormChange}
                    name="password"
                    id="password"
                    placeholder="password"
                  />
                </p>
                <p />
                <div className="text-center">
                  <Button
                    color="primary"
                    size="sm"
                    onClick={e => this.handleSubmit(e)}
                  >
                    Log in
                  </Button>
                  <NavLink href="#">Forgot your password?</NavLink>
                </div>
              </FormGroup>
            </Form>
          </div>
        </div>
      </div>
    );
  }
}

export default withRouter(Login);
