import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import axios from "axios";
import https from "https";
import Cookies from "js-cookie";

class Details extends Component {
  state = {
    book: {},
    libraries: [],
    bookstores: [],
    customerId: this.props.customerId
  };

  componentDidMount() {
    console.log(this.props.match.params.search_term);
    const isbn = this.props.match.params.search_term;
    console.log({ isbn });
    const agent = new https.Agent({
      rejectUnauthorized: false
    });
    axios
      .get("https://localhost:8080/bookDetails/" + isbn, {
        crossdomain: true,
        httpsAgent: agent,
        withCredentials: true
      })
      .then(res => {
        this.setState({ book: res.data.book });
        this.setState({ libraries: res.data.libraries });
        this.setState({ bookstores: res.data.bookstores });
        console.log(this.state);
      });
  }
  handleBuy = (isbn, bookstoreid, customerId) => {
    console.log("SESSION KEY: ");
    console.log(Cookies.get("sessionKey"));
    const agent = new https.Agent({
      rejectUnauthorized: false
    });
    console.log("ISBN: " + isbn);
    console.log("bookstoreId: " + bookstoreid);
    console.log("customerId: " + customerId);
    axios
      .post(
        "https://localhost:8080/buy",
        { isbn: isbn, institutionId: bookstoreid, customerID: customerId },
        { withCredentials: true, crossdomain: true, httpsAgent: agent }
      )
      .then(res => {
        var str = "The order was made successfully";

        window.alert(`${str}`);
          console.log(this.props.match.params.search_term);
          const isbn = this.props.match.params.search_term;
          console.log({ isbn });
          const agent = new https.Agent({
            rejectUnauthorized: false
          });
          axios
            .get("https://localhost:8080/bookDetails/" + isbn, {
              crossdomain: true,
              httpsAgent: agent,
              withCredentials: true
            })
            .then(res => {
              this.setState({ book: res.data.book });
              this.setState({ libraries: res.data.libraries });
              this.setState({ bookstores: res.data.bookstores });
              console.log(this.state);
            });
            })
            .catch(error => {
              window.alert(`${error}
                                Something went wrong
                                `);
            });
  };
  handleBorrow = (isbn, libraryid, customerId) => {
    console.log("SESSION KEY: ");
    console.log(Cookies.get("sessionKey"));
    const agent = new https.Agent({
      rejectUnauthorized: false
    });
    axios
      .post(
        "https://localhost:8080/borrow",
        { isbn: isbn, institutionId: libraryid, customerID: customerId },
        { withCredentials: true, crossdomain: true, httpsAgent: agent }
      )
      .then(res => {
        var str = "The order was made successfully";

        window.alert(`${str}`);
          console.log(this.props.match.params.search_term);
          const isbn = this.props.match.params.search_term;
          console.log({ isbn });
          const agent = new https.Agent({
            rejectUnauthorized: false
          });
          axios
            .get("https://localhost:8080/bookDetails/" + isbn, {
              crossdomain: true,
              httpsAgent: agent,
              withCredentials: true
            })
            .then(res => {
              this.setState({ book: res.data.book });
              this.setState({ libraries: res.data.libraries });
              this.setState({ bookstores: res.data.bookstores });
              console.log(this.state);
            });
      })
      .catch(error => {
        window.alert(`${error}
                           Something went wrong
                           `);
      });
  };
  handleNotLoggedIn = e => {
    if (!(this.props.loggedIn && this.props.accountType === "Customer")) {
      window.alert(`In order to buy or borrow books you have to be logged in as a customer.`);
    }
  };

  render() {
    const { book } = this.state;
    const libraryList = this.state.libraries.length ? (
      this.state.libraries.map(library => {
        return library.quantity > 0 ? (
          <div className="collection-item" key={library.libraryid}>
            <div className="row">
              <div className="col-sm-7">
                <span>{library.libraryName}</span>
              </div>
              <div className="col-sm-1">
                <span>{library.quantity}</span>
              </div>
              <div className="col-sm-3">
                <a
                  href="#"
                  className="btn btn-success btn-sm mr-1 m-1"
                  onClick={(e, f, g) =>
                    this.handleBorrow(
                      this.state.book.isbn,
                      library.libraryid,
                      this.state.customerId
                    )
                  }
                  onMouseLeave={e => this.handleNotLoggedIn(e)}
                >
                  Borrow
                </a>
              </div>
            </div>
          </div>
        ) : <p className="center">Not available</p>;
      })
    ) : (
      <p className="center">Not available</p>
    );
    const bookstoreList = this.state.bookstores.length ? (
      this.state.bookstores.map(bookstore => {
        return (
          <div className="collection-item" key={bookstore.bookstoreid}>
            <div className="row">
              <div className="col-sm-9">
                <span>{bookstore.name}</span>
              </div>
              <div className="col-sm-3">
                <a
                  href="#"
                  className="btn btn-primary btn-sm mr-1 m-1"
                  onClick={(e, f, g) =>
                    this.handleBuy(
                      this.state.book.isbn,
                      bookstore.bookstoreid,
                      this.state.customerId
                    )
                  }
                  onMouseLeave={e => this.handleNotLoggedIn(e)}
                >
                  Buy
                </a>
              </div>
            </div>
          </div>
        );
      })
    ) : (
      <p className="center">Not available</p>
    );

    const bookDetails =
      this.state.book != null ? (
        <div className="row p-5 m-5">
          <div className="offset-sm-1 col-sm-10 pt-2 pb-1">
            <div className="card pt-2 pb-2">
              <div className="card-body">
                <h4 className="card-title">{book.title}</h4>
                <div className="card-subtitle text-muted">
                  {book.author} ({book.year}) /{" "}
                  <span className=" text-danger">{book.category}</span>
                  <div>isbn: {book.isbn}</div>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col-sm-6">
                <div className="row">
                  <div className="col-sm-6">
                    <h6 className="text-success">Available in libraries:</h6>
                  </div>
                  <div className="col-sm-4">
                    <h6 className="text-success">Amount:</h6>
                  </div>
                </div>
                {libraryList}
              </div>
              <div className="col-sm-6">
                <h6 className="text-primary">Available in bookstores:</h6>
                {bookstoreList}
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div>Loading...</div>
      );

    return <div className="container">{bookDetails}</div>;
  }
}

export default withRouter(Details);
