import React, { Component } from 'react';
import {Form, FormGroup, Input, Button, Collapse, CardBody, Card} from 'reactstrap'
import Cookies from 'js-cookie'


class Home extends Component {

    componentDidMount = () => {
            console.log(this.props);
            Cookies.set('sessionKey', '9440b6fe-b4c4-4dab-b13b-99c37ec173ed');
    }

   handleSearch = (event) => {
    this.setState({
        //searchData: event.target.value
        [event.target.id]: event.target.value
    })
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
    handleAdvancedSubmit = e => {
        e.preventDefault();
        this.props.history.push('/advancedSearch/'+this.state.author+this.state.title+this.state.category+this.state.year+this.state.isbn);
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
        searchData: '',
        author: '',
        title:'',
        category:'',
        year:'',
        isbn:''
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
                        <Form onSubmit={e => this.handleSubmit(e)}>
                        <FormGroup>
                            <div className="row">
                                <div className="col-sm-10" >
                                    <Input type="text" value={this.state.value} onChange={this.handleSearch} name="search" id="searchData" 
                                    placeholder="Book name, isbn, year, author etc." />
                                 </div>
                                <div className="col-sm-2 p-1" >
                                    <Button outline color="secondary" size="sm" onClick={this.toggle} style={{ marginBottom: '1rem' }}>Advanced search</Button>
                                </div>
                            </div>
                            
                            <p/>

                            <Button color="primary" size="sm">Search</Button>
                            </FormGroup>
                        </Form>
                        <Collapse isOpen={this.state.collapse}>
                                <Card>
                                 <CardBody>
                                    <Form  onSubmit={e => this.handleAdcancedSubmit(e)}>
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
                                        <p/>
                                        <Button color="primary" size="sm">Search</Button>
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
