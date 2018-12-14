import React, { Component } from "react";
import {
  Form,
  FormGroup,
  Input,
  Button,
  Collapse,
  CardBody,
  Card
} from "reactstrap";

class Home extends Component {
  componentDidMount = () => {
    console.log(this.props);
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

  handleSearch = event => {
    this.setState({
      //searchData: event.target.value
      [event.target.id]: event.target.value
    });
  };

  handleSubmit = e => {
    e.preventDefault();
    if (this.state.collapse) {
      let url = `/advancedSearch/${
        this.state.title ? this.state.title + "/" : ""
      }${this.state.author ? this.state.author + "/" : ""}
        ${this.state.year ? this.state.year + "/" : ""}${
        this.state.isbn ? this.state.isbn + "/" : ""
      }${this.state.category ? this.state.category : ""}`;

      if (url.substr(url.length - 1) === "/") {
        url = url.substr(0, url.length - 2);
      }
      this.props.history.push(url);
    } else {
      this.props.history.push("/search/" + this.state.searchData);
    }

    //   `/advancedSearch?title=${this.state.title}&author=${
    //       this.state.author
    //       }&year=${this.state.year}&isbn=${this.state.isbn}&category=${
    //       this.state.category
    //       }
    //     `

    // console.log(this.state.searchData);
    // const agent = new https.Agent({
    //     rejectUnauthorized: false
    //   });
    // axios.get("https://localhost:8080/search?searchTerm="+this.state.searchData, {crossdomain: true, httpsAgent: agent})
    // .then(res => {
    //

    //     console.log(res.data);
    // })
  };

  constructor(props) {
    super(props);
    this.toggle = this.toggle.bind(this);
    this.state = { collapse: false };
  }

  toggle() {
    this.setState({ collapse: !this.state.collapse });
  }

  state = {
    searchData: "",
    author: "",
    title: "",
    category: "",
    year: "",
    isbn: "",
    collapse: false
  };
  render() {
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
    return (
      <div className="container">
        <div className="row">
          <div className="col-sm-6 offset-sm-3 pt-5">
            <h2 className="text-center display-4">Fall in love with words</h2>
            <p className="text-muted">And search our database for YOUR book</p>
          </div>
        </div>
        <div className="row">
          <div className="offset-sm-3 col-sm-6 p-5 text-center">
            <Form onSubmit={e => this.handleSubmit(e)}>
              <FormGroup>
                <div className="row">
                  <div className="col-sm-10">{inputSearch}</div>
                  <div className="col-sm-2 p-1">
                    <Button
                      outline
                      color="secondary"
                      size="sm"
                      onClick={this.toggle}
                      style={{ marginBottom: "1rem" }}
                    >
                      Advanced search
                    </Button>
                  </div>
                </div>

                <p />

                <Button color="primary" size="sm">
                  Search
                </Button>
              </FormGroup>
            </Form>
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
                      id="category">
                        <option></option>
                        <option>Fantasy</option>
                        <option>Sci-Fi</option>
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
      </div>
    );
  }
}

export default Home;
