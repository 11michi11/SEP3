package model;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "libraryorder", schema = "public")
public class LibraryOrder {

    @EmbeddedId
    private LibraryOrderID id;

    @Column(name = "dateoforder")
    @Temporal(TemporalType.DATE)
    private Calendar dateOfOrder;

    @Column(name = "returndate")
    @Temporal(TemporalType.DATE)
    private Calendar returnDate;

    public LibraryOrder() {
    }

    public LibraryOrderID getId() {
        return id;
    }

    public void setId(LibraryOrderID id) {
        this.id = id;
    }

    public Calendar getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Calendar dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public Calendar getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Calendar returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "LibraryOrder{" +
                "id=" + id +
                ", dateOfOrder=" + dateOfOrder +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibraryOrder that = (LibraryOrder) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dateOfOrder != null ? !dateOfOrder.equals(that.dateOfOrder) : that.dateOfOrder != null) return false;
        return returnDate != null ? returnDate.equals(that.returnDate) : that.returnDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateOfOrder != null ? dateOfOrder.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        return result;
    }
}
