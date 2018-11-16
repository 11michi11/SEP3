import React, { Component } from 'react';
import axios from 'axios';

class Details extends Component {
    state = { 
        book: {}
    }

    componentDidMount() {
       console.log(this.props.match.params.isbn);
       const {isbn} = this.props.match.params;
       axios.get("http://localhost:8080/bookDetails/"+isbn, {crossdomain: true})
       .then(res => {

       
           this.setState({book: res.data})
           console.log(this.state.book);
       })
    }
    

    render() { 
        const {book} = this.state;
        const bookDetails = this.state.book ? (  
        <div className="card">
            <div className="div card-header"> {book.title}</div>
        </div> 
) : (<div>Loading...</div>)
        
        return ( 
            

                <div className="container">
                    
                    {bookDetails}

                </div>

           
         );
    }
}
 
export default Details;