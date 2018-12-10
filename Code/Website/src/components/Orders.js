import React, { Component } from 'react';
import { Link } from "react-router-dom";
import axios from "axios";
import LoadingCanvas from "./../canvas/LoadingCanvas";
import https from "https";
import {Button} from "reactstrap";

class Orders extends Component {
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
    // handleConfirm = (e, orderid)=>{
    //     e.preventdefault();
    //     //???
    //     //const orderid= this.orders.orderid;
    //     const agent = new https.Agent({
    //       rejectUnauthorized: false
    //     });
    //     axios
    //     //???
    //       .delete( "https://localhost:9090/orders/"+orderid,
    //         { crossdomain: true,
    //           httpsAgent: agent,
    //           withCredentials: true }
    //       )
    //       .then(res => {
    //         var str = "SUCCESS!";
    
    //         window.alert(`${str}`);
    //       })
    //       .catch(error => {
    //         window.alert(`${error}
    //                        Something went wrong
    //                        `);
    //       });
    //}
    render() {
      const { orders } = this.state;
      const orderList =
        this.state.orders.length > 0 ? (
          // orders.map(o => {
          //   return (
          //     <div key={o.orderId}>
          //      <p>
          //           {o.isbn} 
          //           {/* {o.bookName} {o.customerName} {o.customerEmail} {o.customerAddress} {o.customerPhoneNum} */}
          //           </p>
          //         </div>
                  
          //   );
          
         // })
         <div> {orders.orderId}</div>
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