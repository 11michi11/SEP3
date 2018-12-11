import React, { Component } from "react";
import { Link } from "react-router-dom";
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
    console.log(this.props);
    const agent = new https.Agent({
      rejectUnauthorized: false
    });

    console.log(this.props.match.path.substring(1, 9));
    if (this.props.match.path.substring(1, 9) === "advanced") {
      console.log("PROPS" + this.props);
      console.log(this.props.match.params);
      this.setState(
        {
          advSearch: {
            author: this.props.match.params.author,
            title: this.props.match.params.title,
            category: this.props.match.params.category,
            year: this.props.match.params.year,
            isbn: this.props.match.params.isbn
          }
        },
        () => {
          let title = this.state.title ? `title=${this.state.title}` : "";
          let author = this.state.author ? `&author=${this.state.author}` : "";
          let year = this.state.year ? `&year=${this.state.year}` : "";
          let isbn = this.state.isbn ? `&isbn=${this.state.isbn}` : "";
          let category = this.state.category
            ? `&category=${this.state.category}`
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
              console.log("Res data: " + res.data);
            });
          console.log(this.state.advSearch);
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
          console.log("Res data: " + res.data);
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
          <div className="offset-sm-5 col-sm-2 text-center">
            <span className="text-grey r">Loading...</span>
          </div>
        </div>
      );
    return <div className="container">{booksList}</div>;
  }
}

export default BookList;
