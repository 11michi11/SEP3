import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import axios from "axios";
import https from "https";

class BookList extends Component {
  state = {
    books: [],
    advSearch: {
      author: "",
      title: "",
      category: "",
      year: "",
      isbn: ""
    }
  };

  componentDidMount() {
    const { search_term } = this.props.match.params;

    const agent = new https.Agent({
      rejectUnauthorized: false
    });

    if (this.props.match.path.substring(1, 9) === "advanced") {
      this.setState(
        {
          advSearch: {
            author: this.props.match.params.author
              ? this.props.match.params.author
              : "",
            title: this.props.match.params.title
              ? this.props.match.params.title
              : "",
            category: this.props.match.params.category
              ? this.props.match.params.category
              : "",
            year: this.props.match.params.year
              ? this.props.match.params.year
              : "",
            isbn: this.props.match.params.isbn
              ? this.props.match.params.isbn
              : ""
          }
        },
        () => {
          let title =
            this.state.advSearch.title !== "none"
              ? `title=${this.state.advSearch.title}`
              : "";
          let author =
            this.state.advSearch.author !== "none"
              ? `&author=${this.state.advSearch.author}`
              : "";
          let year =
            this.state.advSearch.year !== "none"
              ? `&year=${this.state.advSearch.year}`
              : "";
          let isbn =
            this.state.advSearch.isbn !== "none"
              ? `&isbn=${this.state.advSearch.isbn}`
              : "";
          let category =
            this.state.advSearch.category !== "none"
              ? `&category=${this.state.advSearch.category}`
              : "";

          axios
            .get(
              `https://localhost:8080/advancedSearch?${title}${author}${year}${isbn}${category}`,
              {
                crossdomain: true,
                httpsAgent: agent,
                withCredentials: true
              }
            )
            .then(res => {
              this.setState({ books: res.data });
            });
        }
      );
    } else {
      axios
        .get("https://localhost:8080/search?searchTerm=" + search_term, {
          crossdomain: true,
          httpsAgent: agent,
          withCredentials: true
        })
        .then(res => {
          this.setState({
            books: res.data
          });
        });
    }
  }
  render() {
    const { books } = this.state;
    const booksList =
      this.state.books.length > 0 ? (
        books.map(b => {
          return (
            <div key={b.isbn} className="card">
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
          <div className="offset-sm-3 col-sm-6 text-center">
            <span className="text-grey r">
              No book matching searching criteria.
            </span>
          </div>
        </div>
      );
    return <div className="container">{booksList}</div>;
  }
}

export default withRouter(BookList);
