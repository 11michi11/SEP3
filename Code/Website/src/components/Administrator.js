import React, { Component } from 'react';
import {Form, FormGroup, Input, Button} from 'reactstrap'
import {Link} from 'react-router-dom'
import axios from 'axios'

// Admin for Bookstore

class Administrator extends Component {
    state = { 
        books: [],
        searchData: ""
     }

     componentDidMount() {
        console.log("component mounted");
     }

     handleSearch = event => {
        this.setState({searchData: event.target.value})
        console.log(this.state.searchData);
     }

     handleSubmit = e => {
        e.preventDefault();
        
        
        console.log(this.state.searchData);
        axios.get("http://localhost:8080/search?searchTerm="+this.state.searchData, {crossdomain: true})
        .then(res => {

            this.setState({books: res.data});
            console.log(res.data);
        })
    }

        handleDelete = e => {
            e.preventDefault();

            console.log("delete "+ e.target.value);
        }

        // 

    

    render() { 
        const {books} = this.state;
        const booksList =  books.map(b => {
            return (
                 <div key={b.isbn} className="card">
                        
                        <div className="card-body">
                            <Link to={'/books/'+ b.isbn}>    
                            <h5 className="card-title">{b.title}</h5>
                            </Link>
                            <div className="card-subtitle text-muted">{b.author} ({b.year}) / <span className=" text-danger">{b.category}</span></div>
                            <button type="button" class="btn btn-danger" onClick={this.handleDelete} value={b.isbn}>Delete</button>
                            <p></p>
                        </div>
                    </div>
            )
        })
        return ( 

            <div className="container">
                <div className="row p-5">
                    <div className="col text-center">
                    <h1 > Admin Panel </h1>
                    </div>
                    </div>
                <div className="row">
            <div className="offset-sm-3 col-sm-6 p-5 text-center" >
                <Form>
                    <FormGroup>
                        <Input type="text" value={this.state.value} onChange={this.handleSearch} name="search" id="searchInput" 
                        placeholder="Book title, isbn, year, author etc." />
                        <p></p>
                        <Button color="primary" size="sm"  onClick={e => this.handleSubmit(e)}>Search</Button>
                        </FormGroup>
                    </Form>
               
            </div>
            </div>
            {booksList}
             </div>
             
            
         );
    }
}
 
export default Administrator;