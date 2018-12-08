import React, { Component } from 'react';
import { Link } from "react-router-dom";
import axios from "axios";
import LoadingCanvas from "./../canvas/LoadingCanvas";
import https from "https";

class Orders extends Component {
    state = {
      orders: []
    };

    componentDidMount() {
  
      const agent = new https.Agent({
        rejectUnauthorized: false
      });
  
      axios
        .get("https://localhost:8080/search?searchTerm=" + search_term, {
          crossdomain: true,
          httpsAgent: agent,
          withCredentials: true
        })
        .then(res => {
          this.setState({ orders: res.data });
          console.log(res.data);
        });
    }
    render() {
      const { orders } = this.state;
      const orderList =
        this.state.orders.length > 0 ? (
          orders.map(o => {
            return (
              <div key={o.id} className="card">
                <div className="card-body">
                  <h5 className="card-title">
                    <Link to={"/details/" + b.isbn}>{b.title}</Link>
                  </h5>
  
                  <div className="card-subtitle text-muted">
                    {b.author} ({b.year}) /{" "}
                    <span className=" text-danger">{b.category}</span>
                  </div>
                  <p />
                </div>
              </div>
            );
          })
        ) : (
          <div className="row p-5 m-5">
            <div className="offset-sm-5 col-sm-2 text-center">
              <span className="text-grey r">Loading...</span>
            </div>
          </div>
        );
      return <div className="container">{orderList}</div>;
    }
}
export default Orders;