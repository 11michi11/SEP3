import React, { Component } from 'react';
import { Link } from "react-router-dom";
import axios from "axios";
import LoadingCanvas from "../canvas/LoadingCanvas";
import https from "https";
import {Button} from "reactstrap";

class BookstoreOrders extends Component {
    state = {
      orders: []
    };

    componentDidMount() {
      const agent = new https.Agent({
        rejectUnauthorized: false
      });
  
      axios
        .get("https://localhost:9090/orders", {
          crossdomain: true,
          httpsAgent: agent,
          withCredentials: true
        })
        .then(res => {
          this.setState({ orders: res.data });
          console.log(res.data);
        });
    }
    componentDidUpdate(){
      const agent = new https.Agent({
        rejectUnauthorized: false
      });

      axios
      .get("https://localhost:9090/orders", {
        crossdomain: true,
        httpsAgent: agent,
        withCredentials: true
      })
      .then(res => {
        this.setState({ orders: res.data });
        console.log(res.data);
      });
    }
    handleConfirm = (e)=>{
        console.log(e)
        const agent = new https.Agent({
          rejectUnauthorized: false
        });
        axios
          .delete( "https://localhost:9090/orders/"+e,
            { crossdomain: true,
              httpsAgent: agent,
              withCredentials: true }
          )
          .then(res => {
            var str = "The order has been confirmed successfully";
    
            window.alert(`${str}`);
          })
          .catch(error => {
            window.alert(`${error}
                           Something went wrong
                           `);
          });
    }
    render() {
      const { orders } = this.state;
      const orderList =
        this.state.orders.length > 0 ? (
          orders.map(o => {
            return (
              <div key={o.orderId}>
                <div className="row">
                  <div className="col-md-2">{o.isbn}</div>
                  <div className="col-md-2">{o.bookName}</div>
                  <div className="col-md-2">{o.customerName}</div>
                  <div className="col-md-2">{o.customerEmail}</div>
                  <div className="col-md-2">{o.customerAddress}</div>
                  <div className="col-md-1">{o.customerPhoneNum}</div>
                  <div className="col-md-1"><a href="#" className="btn btn-success btn-sm mr-1 m-1" onClick={(e)=>{this.handleConfirm(o.orderId)}}>Confirm</a></div>
                </div>
              </div>  
            );
         })
        ) : (
          <div className="row p-5 m-5">
            <div className="offset-sm-2 col-sm-6 text-center">
              <span className="text-grey r">There are no unfinished orders</span>
            </div>
          </div>
        );
        return <div className="container">
          <div className="row">
            <div className="col-md-2">isbn:</div>
            <div className="col-md-2">title:</div>
            <div className="col-md-2">name:</div>
            <div className="col-md-2">email:</div>
            <div className="col-md-2">address:</div>
            <div className="col-md-1">phone:</div>
          </div>
          {orderList}
          </div>;
    }
    
}
export default BookstoreOrders;