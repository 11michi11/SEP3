import React, { Component } from 'react';
import {Form, FormGroup, Input, Button, Collapse, CardBody, Card} from 'reactstrap'


class Home extends Component {

    componentDidMount = () => {
            console.log(this.props);
    }

   handleSearch = (event) => {
    this.setState({searchData: event.target.value});
    
    }

    handleSubmit = e => {
        e.preventDefault();
        
        // console.log(this.state.searchData);
        // const agent = new https.Agent({
        //     rejectUnauthorized: false
        //   });
        // axios.get("https://localhost:8080/search?searchTerm="+this.state.searchData, {crossdomain: true, httpsAgent: agent})
        // .then(res => {
        //  
        
        //     console.log(res.data);
        // })
        this.props.history.push('/search/'+this.state.searchData);
    }
    constructor(props) {
        super(props);
        this.toggle = this.toggle.bind(this);
        this.state = { collapse: false };
      }
    
      toggle() {
        this.setState({ collapse: !this.state.collapse });
      }

    state = { 
        searchData: ''
     }
    render() { 
        return (
            <div className="container">
                <div className="row">
                    <div className="col-sm-6 offset-sm-3 pt-5">
                        <h2 className="text-center display-4">Fall in love with words</h2>
                        <p className="text-muted">And search our database for YOUR book</p>
                    </div>
                </div>
                <div className="row">
                    <div className="offset-sm-3 col-sm-6 p-5 text-center" >
                        <Form>
                        <FormGroup>
                            <div className="row">
                                <div className="col-sm-10" >
                                    <Input type="text" value={this.state.value} onChange={this.handleSearch} name="search" id="searchInput" 
                                    placeholder="Book name, isbn, year, author etc." />
                                 </div>
                                <div className="col-sm-2 p-1" >
                                    <Button outline color="secondary" size="sm" onClick={this.toggle} style={{ marginBottom: '1rem' }}>Advanced search</Button>
                                </div>
                            </div>
                            <Collapse isOpen={this.state.collapse}>
                                <Card>
                                 <CardBody>
                                    <Form>
                                    <FormGroup>
                                        <Input type="text" value={this.state.value} onChange={this.handleSearch} name="advancedSearch" id="author" 
                                        placeholder="author" />
                                         <p/>
                                         <Input type="text" value={this.state.value} onChange={this.handleSearch} name="advancedSearch" id="title" 
                                         placeholder="title" />
                                         <p/>
                                         <Input type="text" value={this.state.value} onChange={this.handleSearch} name="advancedSearch" id="category" 
                                         placeholder="category" />
                                         <p/>
                                        <Input type="text" value={this.state.value} onChange={this.handleSearch} name="advancedSearch" id="year" 
                                        placeholder="year" />
                                        <p/>
                                        <Input type="text" value={this.state.value} onChange={this.handleSearch} name="advancedSearch" id="isbn" 
                                        placeholder="isbn" />
                                    </FormGroup>
                                    </Form>
                                </CardBody>
                                </Card>
                            </Collapse>
                            <p/>

                            <Button color="primary" size="sm"  onClick={e => this.handleSubmit(e)}>Search</Button>
                            </FormGroup>
                        </Form>
                    </div>
                </div>
            </div> 
         );
    }
}
 
export default Home;
