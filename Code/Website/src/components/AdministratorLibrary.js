import React, { Component } from "react";
import https from 'https';

class AdministratorLibrary extends Component {
  state = {
    books: [],
    searchData: "",
    displayAdd: true
  };

  componentDidMount() {
    console.log("component mounted");
  }

  handleSearch = event => {
    this.setState({ searchData: event.target.value });
    console.log(this.state.searchData);
  };

  handleSubmit = e => {
    e.preventDefault();

    console.log(this.state.searchData);
    const agent = new https.Agent({
      rejectUnauthorized: false
    });
    axios
      .get("https://localhost:8080/search?searchTerm=" + this.state.searchData, {
        crossdomain: true,
        httpsAgent: agent
      })
      .then(res => {
        this.setState({ books: res.data });
        console.log(res.data);
      });
  };

  handleDelete = e => {
    e.preventDefault();

    console.log("delete " + e.target.value);
  };

  displayAdd() {
    return this.state.displayAdd ? (
      <div className="row">
        <div className="offset-sm-4 col-sm-4" />
        <form>
          <input className="form-control" type="text" placeholder="Book name" />
          <input className="form-control" type="text" placeholder="Book name" />
          <input className="form-control" type="text" placeholder="Book name" />
          <input className="form-control" type="text" placeholder="Book name" />
        </form>
      </div>
    ) : (
      <div />
    );
  }

  //

  render() {
    const { books } = this.state;
    const addForm = () => this.displayAdd();
    const booksList = books.map(b => {
      return (
        <div key={b.isbn} className="card">
          <div className="card-body">
            <Link to={"/books/" + b.isbn}>
              <h5 className="card-title">{b.title}</h5>
            </Link>
            <div className="card-subtitle text-muted">
              {b.author} ({b.year}) /{" "}
              <span className=" text-danger">{b.category}</span>
            </div>
            <button
              type="button"
              class="btn btn-danger"
              onClick={this.handleDelete}
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
            <h1 className="display-2"> Library Admin Panel </h1>
          </div>
        </div>
        <div className="row">
          <div className="offset-sm-3 col-sm-6 p-5 text-center">
            <Form>
              <FormGroup>
                <Input
                  type="text"
                  value={this.state.value}
                  onChange={this.handleSearch}
                  name="search"
                  id="searchInput"
                  placeholder="Book title, isbn, year, author etc."
                />
                <p />
                <Button
                  color="primary"
                  size="sm"
                  onClick={e => this.handleSubmit(e)}
                >
                  Search
                </Button>
                <Button
                  color="success"
                  size="sm"
                  onClick={() => this.displayAdd()}
                >
                  Add Book
                </Button>
              </FormGroup>
            </Form>
          </div>
        </div>
        {addForm}
        {booksList}
      </div>
    );
  }
}

export default AdministratorLibrary;
