import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios'
import LoadingCanvas from './../canvas/LoadingCanvas'

class BookList extends Component {
    state = { 
         books: []
         
     }

     componentDidMount() {
        console.log(this.props.match.params.search_term);
        const {search_term} = this.props.match.params;
        axios.get("http://localhost:8080/search?searchTerm="+search_term, {crossdomain: true})
        .then(res => {

        
            this.setState({books: res.data})
            console.log(res.data);
        })
     }
    render() { 
        const {books} = this.state;
        const booksList =  this.state.books.length > 0 ? (books.map(b => {
            return (
                 <div key={b.isbn} className="card">
                        
                        <div className="card-body">
                            <Link to={'/books/'+ b.isbn}>    
                            <h5 className="card-title"><Link to={"/details/" + b.isbn}>{b.title}</Link></h5>
                            </Link>
                            <div className="card-subtitle text-muted">{b.author} ({b.year}) / <span className=" text-danger">{b.category}</span></div>
                            <p></p>
                        </div>
                    </div>
            )
        })) : (
        <div className="row p-5 m-5">
            <div className="offset-sm-5 col-sm-2 text-center">
            <span className="text-grey r">Loading...</span>
            </div>
        </div> )
        return ( 

            <div className="container">
                {booksList}
            </div>
            
         );
    }
}
 
export default BookList;