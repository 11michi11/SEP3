import React, { Component } from 'react';
import {Link} from 'react-router-dom'

class BookList extends Component {
    state = { 
         books: [
             {id: 1, title: "Rick", category: "Fantasy", description: "orem dkasodk kasdok oasd oduasdj oaisdjoais jasjodjas idjoaisjd[jas[djidjasjdoiasdpiasjd asdaspd dpokjaspod po]]"},
             {id: 2, title: "Rick", category: "Fantasy", description: "orem dkasodk kasdok oasd oduasdj oaisdjoais jasjodjas idjoaisjd[jas[djidjasjdoiasdpiasjd asdaspd dpokjaspod po]]"}
         ]
     }
    render() { 
        const {books} = this.state;
        const booksList = books.map(m => {
            return (
                 <div key={m.id} className="post card">
                        
                        <div className="card-content">
                            <Link to={'/'+ m.id}>    
                            <span className="card-title red-text">{m.title}</span>
                            </Link>
                            <p>{m.description}</p>
                        </div>
                    </div>
            )
        })
        return ( 

            <div className="container">
                {booksList}
            </div>
            
         );
    }
}
 
export default BookList;