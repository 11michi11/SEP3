import React, { Component } from 'react';
import axios from 'axios';
import https from 'https';

class Details extends Component {
    state = { 
        book: {},
        libraries: [],
        bookstores: []
    }

    componentDidMount() {
       console.log(this.props.match.params.search_term);
       const isbn = this.props.match.params.search_term;
       console.log({isbn});
    const agent = new https.Agent({
        rejectUnauthorized: false
      });
       axios.get("https://localhost:8080/bookDetails/"+isbn, {crossdomain: true, httpsAgent: agent})
       .then(res => {
       
       
           this.setState({book: res.data.book})
           console.log(this.state)
           this.setState({libraries: res.data.libraries})
           this.setState({bookstores: res.data.bookstores})
       })

    // this.setState({
    //     book: {
    //         title: "Black holes",
    //         category: "Science",
    //         available: true,
    //         year: "25.06.1990",
    //         author: "Jackobsen Jakob Rem",
    //         description: "",
    //         isbn: "978-83-246-7758-23"
    //     },
    //     libraries: [{
    //         name: "First Library",
    //         id: "ce78ef57-77ec-4bb7-82a2-1a78d3789aef",
    //         quantity: 2
    //     }, {
    //         name: "Second Library",
    //         id: "ce78ef57-77ec-4bb7-82a2-1a78d3789eaf",
    //         quantity: 0
    //     }, {
    //         name: "Third Library",
    //         id: "ec78ef57-77ec-4bb7-82a2-1a78d3789eaf",
    //         quantity: 125
    //     }],
    //     bookstores: [{
    //         name:"First Book Store",
    //         id:"eb3777c8-77fe-4acd-962d-6853da2e05e0"
    //     }, {
    //         name:"Second Book Store",
    //         id:"eb3777c8-77fe-4aed-962d-6853da2e05e0"
    //     }]
    // })

    // localhost:8080/isbn
    // book: isbn, title, year, libraries[ libraryid, bookid, available: bool], bookstores: [bookstoreid]
    }
    

    render() { 
        const {book} = this.state;
        const smallFont = {
            fontSize: 10
        }
        
        // const qtIs =  book.qty>1 || book.qty === 0 ? (<span>are</span>) : (<span>is</span>); // qty is more than 1 or 0 then gramaticaly is "are"
        // const sNoS = book.qty>1 || book.qty === 0 ? ("s") : (""); // book or books
        // const qtyVal = book.qty>0 ? (<span className="text-success">{book.qty} </span>) : (<span className="text-danger">no</span>) // show number or write 'no'
        const libraryList=this.state.libraries.length ? (
            this.state.libraries.map(library => {
                return library.quantity > 0 ?(
                    <div className="collection-item" key={library.id}>
                        <div className="row">
                            <div className="col-sm-7">
                                <span>{library.libraryName}</span>
                            </div><div className="col-sm-1">
                                <span>{library.quantity}</span>
                            </div><div className="col-sm-3">
                                <a href="#" className="btn btn-success btn-sm mr-1 m-1">Borrow</a>
                            </div>
                        </div>
                    </div>
                ) : (null)
            })
        ) : (<p className="center">Not available</p>)
        const bookstoreList=this.state.bookstores.length ? (
            this.state.bookstores.map(bookstore => {
                return (
                    <div className="collection-item" key={bookstore.id}>
                        <div className="row">
                            <div className="col-sm-9">
                                <span>{bookstore.name}</span>
                            </div>
                            <div className="col-sm-3">
                            <a href="#" className="btn btn-primary btn-sm mr-1 m-1">Buy</a>
                            </div>
                        </div>
                    </div>
                )
            })
        ) : (<p className="center">Not available</p>)


        const bookDetails = this.state.book ? (  
            <div className="row p-5 m-5">
                <div className="offset-sm-1 col-sm-10 pt-2 pb-1">
                <div className="card pt-2 pb-2">
                    <div className="card-body">
                    <h4 className="card-title">{book.title}</h4>
                    <div className="card-subtitle text-muted">{book.author} ({book.year}) / <span className=" text-danger">{book.category}</span>
                        <div>isbn: {book.isbn}</div></div>
                       {/* <a href="#" className="btn btn-primary mr-1">Borrow</a>  <a href="#" className="btn btn-warning">Buy</a>
                       <p>
                       <span style={smallFont}>

                          There {qtIs} currently {qtyVal} book{sNoS} available in the library
                       </span>
                           </p> */}
                    </div>
                    </div>
                    <div className="row">
                        <div className="col-sm-6">
                            <div className="row">
                                <div className="col-sm-6">
                                    <h6 className="text-success">Available in libraries:</h6> 
                                </div>
                                <div className="col-sm-4">
                                    <h6 className="text-success">Amount:</h6> 
                                </div>
                            </div>
                            {libraryList}
                        </div>
                        <div className="col-sm-6">
                            <h6 className="text-primary">Available in bookstores:</h6>
                            {bookstoreList}
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