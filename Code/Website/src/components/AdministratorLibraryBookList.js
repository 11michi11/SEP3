import React, { Component } from "react";
import axios from "axios";
import https from "https";

class AdministratorLibraryBookList extends Component {
  state = {
    books: [],
    bookDetails: "",
    bookIsbn: ""
  };

  handleDelete = e => {
    e.preventDefault();
    const agent = new https.Agent({
      rejectUnauthorized: false
    });
    axios
      .delete("https://localhost:5001/book/" + e.target.value, {
        crossdomain: true,
        httpsAgent: agent,
        withCredentials: true
      })
      .then(res => {
        window.alert("Succesfuly deleted a book!");
        this.fetchData();
      })
      .catch(error => {
        window.alert(error + "Cannot delete a book");
      });
  };
  componentDidMount() {
    this.fetchData();
  }

  fetchData() {
    console.log(this.props.match.params.isbn);
    const isbn = this.props.match.params.isbn;
    this.setState({ bookIsbn: isbn }, () => {
      const agent = new https.Agent({
        rejectUnauthorized: false
      });
      axios
        .get("https://localhost:5001/bookDetails/" + this.state.bookIsbn, {
          crossdomain: true,
          httpsAgent: agent,
          withCredentials: true
        })
        .then(res => {
          console.log(res);
          this.setState({ books: res.data.libraryBooks });
        });
    });
  }

  render() {
    const { books } = this.state;
    const booksList = this.state.books ? (
      books.map(b => {
        return (
          <div key={b.bookid} className="card">
            <div className="card-body">
              <h5 className="card-title">{b.bookid}</h5>

              <div className="card-subtitle text-muted">
                Available:{" "}
                <span className=" text-primary">{b.available + ""}</span>
                <button
                  type="button"
                  className="btn btn-danger"
                  onClick={(e) => { if( window.confirm('Are you sure you want to delete this book?')) this.handleDelete(e)  }}
                  value={b.bookid}
                >
                  Delete
                </button>
              </div>
              <p />
            </div>
          </div>
        );
      })
    ) : (
      <div className="row p-5 m-5">
        <div className="offset-sm-5 col-sm-2 text-center">
          <span className="text-grey r">. . .</span>
        </div>
      </div>
    );
    return <div>{booksList}</div>;
  }
}

export default AdministratorLibraryBookList;
