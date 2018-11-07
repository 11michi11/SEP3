import React, { Component } from 'react';
import {Form, FormGroup, Label, Input, Button} from 'reactstrap'

class Home extends Component {

    componentDidMount = () => {

    }

   handleSearch = (event) => {
    this.setState({searchData: event.target.value});
    }

    handleSubmit = e => {
        e.preventDefault();
        console.log(this.props.history.push('/books'));
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
            <div className="offset-sm-3 col-sm-6 p-5 text-center">
                   <p>{this.state.searchData}</p> 
                    <Form>
                    <FormGroup>
                        <Input type="text" value={this.state.value} onChange={this.handleSearch} name="search" id="searchInput" 
                        placeholder="Book name, isbn, year, author etc." />
                        <p></p>
                        <Button color="primary" size="sm" onClick={e => this.handleSubmit(e)}>Search</Button>
                        </FormGroup>
                    </Form>
                </div>
            </div>
            </div> 
         );
    }
}
 
export default Home;