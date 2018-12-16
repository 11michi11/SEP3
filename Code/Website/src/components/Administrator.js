import React, { Component } from "react";
import { Form, FormGroup, Input, Button } from "reactstrap";
import { Link } from "react-router-dom";
import axios from "axios";
import { Collapse, Card, CardBody } from "reactstrap";
import https from "https";

// Admin for Bookstore

class Administrator extends Component {
  state = {
    books: [],
    searchData: "",
    displayAdd: true,
    author: "",
    title: "",
    category: "",
    year: "",
    isbn: "",
    collapse: false,
    newBook: {
      title: "",
      author: "",
      year: "",
      isbn: "",
      category: ""
    }
  };

  componentDidMount() {
    console.log("component mounted");
  }

  displayAdvanced = () => {
    this.setState({ collapse: !this.state.collapse });
  };

  handleSearch = event => {
    this.setState({ searchData: event.target.value });
    console.log(this.state.searchData);
  };

  handleSubmit = e => {
    e.preventDefault();

    this.setState({ displayAdd: false });
    console.log(this.state.searchData);
    const agent = new https.Agent({
      rejectUnauthorized: false
    });
    if (this.state.collapse) {
      if (isNaN(this.state.year)) {
        alert("Year has to be a number");
        return;
      }
      let title = this.state.title ? `title=${this.state.title}` : "";
      let author = this.state.author ? `&author=${this.state.author}` : "";
      let year = this.state.year ? `&year=${this.state.year}` : "";
      let isbn = this.state.isbn ? `&isbn=${this.state.isbn}` : "";
      let category = this.state.category
        ? `&category=${this.state.category}`
        : "";
      axios
        .get(
          `https://localhost:9090/advancedSearch?${title}${author}${year}${isbn}${category}`,
          {
            crossdomain: true,
            httpsAgent: agent,
            withCredentials: true
          }
        )
        .then(res => {
          this.setState({ books: res.data });
        });
    } else {
      axios
        .get(
          "https://localhost:9090/search?searchTerm=" + this.state.searchData,
          {
            crossdomain: true,
            httpsAgent: agent,
            withCredentials: true
          }
        )
        .then(res => {
          this.setState({ books: res.data });
          console.log(res.data);
        });
    }
  };

  handleDelete = e => {
    e.preventDefault();
    const agent = new https.Agent({
      rejectUnauthorized: false
    });
    axios
      .delete("https://localhost:9090/book/" + e.target.value, {
        crossdomain: true,
        httpsAgent: agent,
        withCredentials: true
      })
      .then(res => {
        console.log(res);
        window.alert("Succesfuly deleted a book!");
        this.props.history.push("/bookstore_admin");
        axios
          .get(
            "https://localhost:9090/search?searchTerm=" + this.state.searchData,
            {
              crossdomain: true,
              httpsAgent: agent,
              withCredentials: true
            }
          )
          .then(res => {
            this.setState({ books: res.data });
            console.log(res.data);
          });
      })
      .catch(error => {
        window.alert(error + "Cannot delete a book");
      });

    console.log("delete " + e.target.value);
  };

  displayAdd = () => {
    this.setState({ displayAdd: !this.state.displayAdd });
  };

  handleAdvancedSearchChange = e => {
    switch (e.target.id) {
      case "author":
        {
          this.setState({
            author: e.target.value
          });
        }
        break;
      case "category":
        {
          this.setState({
            category: e.target.value
          });
        }
        break;
      case "title":
        {
          this.setState({
            title: e.target.value
          });
        }
        break;
      case "year":
        {
          this.setState({
            year: e.target.value
          });
        }
        break;
      case "isbn":
        {
          this.setState({
            isbn: e.target.value
          });
        }
        break;
    }
    console.log(this.state);
  };

  handleBookFormChange = e => {
    switch (e.target.id) {
      case "bookNameInput":
        {
          this.setState({
            newBook: { ...this.state.newBook, title: e.target.value }
          });
        }
        break;
      case "authorInput":
        {
          this.setState({
            newBook: { ...this.state.newBook, author: e.target.value }
          });
        }
        break;
      case "isbnInput":
        {
          this.setState({
            newBook: { ...this.state.newBook, isbn: e.target.value }
          });
        }
        break;
      case "yearInput":
        {
          this.setState({
            newBook: { ...this.state.newBook, year: e.target.value }
          });
        }
        break;

      case "categoryInput":
        {
          this.setState({
            newBook: { ...this.state.newBook, category: e.target.value }
          });
        }
        break;
    }
    console.log(this.state.newBook);
  };

  sendAddBookRequest = e => {
    e.preventDefault();
    if (
      this.state.newBook.title === "" ||
      this.state.newBook.author === "" ||
      this.state.newBook.year === "" ||
      this.state.newBook.isbn === "" ||
      this.state.newBook.category === ""
    ) {
      window.alert("All fields must be filled");
    } else if (this.state.newBook.isbn.length > 17) {
      window.alert("ISBN has to be shorter than 18 characters");
    } else if (this.state.newBook.year.match(/[a-z]/i)) {
      window.alert("Year cannot contain letters");
    } else {
      const agent = new https.Agent({
        rejectUnauthorized: false
      });
      axios
        .post(
          "https://localhost:9090/book",
          {
            title: this.state.newBook.title,
            author: this.state.newBook.author,
            year: this.state.newBook.year,
            isbn: this.state.newBook.isbn,
            category: this.state.newBook.category
          },
          { crossdomain: true, httpsAgent: agent, withCredentials: true }
        )
        .then(res => {
          var str = "SUCCESS!";

          window.alert(`${str} You\'ve added a book with following data: 
          ${this.state.newBook.title},
          ${this.state.newBook.author},
          ${this.state.newBook.year},
          ${this.state.newBook.isbn},
          ${this.state.newBook.category},`);

          this.setState({
            newBook: {
              title: "",
              author: "",
              year: "",
              isbn: "",
              category: ""
            }
          });
        })
        .catch(error => {
          window.alert(`${error}
                        Something went wrong...
                        `);
        });
    }
  };

  //

  render() {
    const { books } = this.state;
    const inputSearch = this.state.collapse ? (
      <Input
        type="text"
        value={this.state.value}
        onChange={this.handleSearch}
        name="search"
        id="searchData"
        placeholder="Book name, isbn, year, author etc."
        disabled
      />
    ) : (
      <Input
        type="text"
        value={this.state.value}
        onChange={this.handleSearch}
        name="search"
        id="searchData"
        placeholder="Book name, isbn, year, author etc."
      />
    );
    const advancedSearch = this.state.collapse ? (
      <div className="row mb-2">
        <div className="col-md-4 offset-md-4">
          <Collapse isOpen={this.state.collapse}>
            <Card>
              <CardBody>
                <Form>
                  <FormGroup>
                    <Input
                      type="text"
                      value={this.state.title}
                      onChange={this.handleAdvancedSearchChange}
                      name="advancedSearch"
                      id="title"
                      placeholder="title"
                    />
                    <p />
                    <Input
                      type="text"
                      value={this.state.author}
                      onChange={this.handleAdvancedSearchChange}
                      name="advancedSearch"
                      id="author"
                      placeholder="author"
                    />
                    <p />
                    <Input
                      type="select"
                      value={this.state.category}
                      onChange={this.handleAdvancedSearchChange}
                      name="category"
                      id="category"
                    >
                      <option />
                      <option>Fantasy</option>
                      <option>SciFi</option>
                      <option>Criminal</option>
                      <option>Science</option>
                      <option>Drama</option>
                      <option>Children</option>
                      <option>Horror</option>
                      <option>Poetry</option>
                    </Input>

                    <p />
                    <Input
                      type="text"
                      value={this.state.year}
                      onChange={this.handleAdvancedSearchChange}
                      name="advancedSearch"
                      id="year"
                      placeholder="year"
                    />
                    <p />
                    <Input
                      type="text"
                      value={this.state.isbn}
                      onChange={this.handleAdvancedSearchChange}
                      name="advancedSearch"
                      id="isbn"
                      placeholder="isbn"
                    />
                    <p />
                  </FormGroup>
                </Form>
              </CardBody>
            </Card>
          </Collapse>
        </div>
      </div>
    ) : (
      ""
    );
    const addForm = this.state.displayAdd ? (
      <div className="row">
        <div className="offset-sm-4 col-sm-4 pb-2 pt-2 mb-2 border-success border">
          <div className="row text-center">
            <div className="col">
              <h2>Add book form</h2>
            </div>
          </div>
          <form>
            <label htmlFor="bookNameInput">Title</label>
            <input
              id="bookNameInput"
              value={this.state.newBook.title}
              onChange={this.handleBookFormChange}
              value={this.state.newBook.title}
              className="form-control"
              type="text"
              placeholder="Title"
            />
            <br />

            <label htmlFor="authorInput">Author</label>
            <input
              id="authorInput"
              value={this.state.newBook.author}
              onChange={this.handleBookFormChange}
              value={this.state.newBook.author}
              className="form-control"
              type="text"
              placeholder="Author"
            />
            <br />

            <label htmlFor="isbnInput">Isbn</label>
            <input
              id="isbnInput"
              value={this.state.newBook.isbn}
              onChange={this.handleBookFormChange}
              value={this.state.newBook.isbn}
              className="form-control"
              type="text"
              placeholder="ISBN"
            />
            <br />

            <label htmlFor="yearInput">Year</label>
            <input
              id="yearInput"
              value={this.state.newBook.year}
              onChange={this.handleBookFormChange}
              value={this.state.newBook.year}
              className="form-control"
              type="text"
              placeholder="Year"
            />
            <br />

            <label htmlFor="categoryInput">Category</label>
            <Input
              type="select"
              value={this.state.newBook.category}
              onChange={this.handleBookFormChange}
              className="form-control"
              id="categoryInput"
            >
              <option />
              <option>Fantasy</option>
              <option>SciFi</option>
              <option>Criminal</option>
              <option>Science</option>
              <option>Drama</option>
              <option>Children</option>
              <option>Horror</option>
              <option>Poetry</option>
            </Input>
            <br />

            <button
              type="button"
              className="btn btn-sm btn-success"
              onClick={this.sendAddBookRequest}
            >
              Confirm
            </button>
          </form>
        </div>
      </div>
    ) : (
      <div>
        <hr />
      </div>
    );

    const booksList = books.map(b => {
      return (
        <div key={b.isbn} className="card">
          <div className="card-body">
            <h5 className="card-title">{b.title}</h5>

            <div className="card-subtitle text-muted">
              {b.author} ({b.year}) /{" "}
              <span className=" text-danger">{b.category}</span>
            </div>
            <br />
            <button
              type="button"
              className="btn btn-danger"
              onClick={e => {
                if (
                  window.confirm("Are you sure you want to delete this book?")
                )
                  this.handleDelete(e);
              }}
              value={b.isbn}
            >
              Delete
            </button>

            <p />
          </div>
        </div>
      );
    });
    return (
      <div className="container">
        <div className="row p-5">
          <div className="col text-center">
            <h1 className="display-4"> Bookstore Admin Panel </h1>
          </div>
        </div>
        <div className="row">
          <div className="offset-sm-3 col-sm-6 p-5 text-center">
            <Form>
              <FormGroup>
                {inputSearch}
                <p />
                <Button
                  color="primary"
                  size="sm"
                  onClick={e => this.handleSubmit(e)}
                >
                  Search
                </Button>
                <button
                  type="button"
                  className="btn btn-sm btn-success ml-1"
                  onClick={this.displayAdd}
                >
                  Add Book
                </button>
                <button
                  type="button"
                  className="btn btn-sm btn-secondary ml-1"
                  onClick={this.displayAdvanced}
                >
                  Advanced
                </button>
              </FormGroup>
            </Form>
          </div>
        </div>
        {advancedSearch}
        {addForm}
        {booksList}
      </div>
    );
  }
}

export default Administrator;
