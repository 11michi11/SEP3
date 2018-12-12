package model;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class AuthenticationData {

	public enum UserType {Customer, LibraryAdmin, BookStoreAdmin, All}

	public String institutionId;
	public UserType userType;
	public Calendar expirationDate;

	public AuthenticationData(String institutionId, UserType userType, Calendar expirationData) {
		this.institutionId = institutionId;
		this.userType = userType;
		this.expirationDate = expirationData;
	}

	public boolean authenticate(){
		Calendar now = GregorianCalendar.getInstance(TimeZone.getDefault());
		System.out.println(now.getTime());
		switch(userType){
			case All:
				return true;
			case Customer:
			case LibraryAdmin:
			case BookStoreAdmin:
				return now.before(expirationDate);
			default:
				return false;
		}
	}

	public boolean authenticateFromInstitution(String institutionId){
		Calendar now = GregorianCalendar.getInstance(TimeZone.getDefault());
		System.out.println(now.getTime());
		switch(userType){
			case All:
				return true;
			case LibraryAdmin:
			case BookStoreAdmin:
				return now.before(expirationDate) || institutionId.equals(this.institutionId);
			case Customer:
			default:
				return false;
		}
	}

	@Override
	public String toString() {
		return "AuthenticationData{" +
				"institutionId='" + institutionId + '\'' +
				", userType=" + userType +
				", expirationDate=" + expirationDate +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AuthenticationData)) return false;

		AuthenticationData that = (AuthenticationData) o;

		if (institutionId != null ? !institutionId.equals(that.institutionId) : that.institutionId != null)
			return false;
		if (userType != that.userType) return false;
		return expirationDate != null ? expirationDate.equals(that.expirationDate) : that.expirationDate == null;
	}

	@Override
	public int hashCode() {
		int result = institutionId != null ? institutionId.hashCode() : 0;
		result = 31 * result + (userType != null ? userType.hashCode() : 0);
		result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
		return result;
	}
}
