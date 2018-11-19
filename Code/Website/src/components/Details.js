import React, { Component } from 'react';
import axios from 'axios';

class Details extends Component {
    state = { 
        book: {}
    }

    componentDidMount() {
    //    console.log(this.props.match.params.isbn);
    //    const {isbn} = this.props.match.params;
    //    axios.get("http://localhost:8080/bookDetails/"+isbn, {crossdomain: true})
    //    .then(res => {

       
    //        this.setState({book: res.data})
    //        console.log(this.state.book);
    //    })

    this.setState({
        book: {
            title: "Black holes",
            category: "Science",
            available: true,
            year: "25.06.1990",
            author: "Jackobsen Jakob Rem",
            description: "",
            qty: 0
        }
    })

    // localhost:8080/isbn
    // book: isbn, title, year, libraries[ libraryid, bookid, available: bool], bookstores: [bookstoreid]
    }
    

    render() { 
        const {book} = this.state;
        const smallFont = {
            fontSize: 10
        }
        
        const qtIs =  book.qty>1 || book.qty === 0 ? (<span>are</span>) : (<span>is</span>); // qty is more than 1 or 0 then gramaticaly is "are"
        const sNoS = book.qty>1 || book.qty === 0 ? ("s") : (""); // book or books
        const qtyVal = book.qty>0 ? (<span className="text-success">{book.qty} </span>) : (<span className="text-danger">no</span>) // show number or write 'no'
        const bookDetails = this.state.book ? (  
            <div className="row p-5 m-5">
                <div className="offset-sm-1 col-sm-10 pt-2 pb-1">
                <div className="card pt-2 pb-2">
                    <div className="card-body">
                    <h4 className="card-title">{book.title}</h4>
                    <div className="card-subtitle text-muted">{book.author} ({book.year}) / <span className=" text-danger">{book.category}</span></div>
                       <p className="card-text text-justify"> {book.description}</p>
                       <a href="#" className="btn btn-primary mr-1">Borrow</a>  <a href="#" className="btn btn-warning">Buy</a>
                       <p>
                       <span style={smallFont}>

                          There {qtIs} currently {qtyVal} book{sNoS} available in the library
                       </span>
                           </p>
                      
                    </div>
                    </div>
                </div>
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