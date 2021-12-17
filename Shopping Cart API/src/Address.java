
public class Address {
Country country;
State state;
String city;
String streetLine;
int ZIP;

public Address(Country country, State state, String city, String streetLine, int ZIP) {
	this.country = country;
	this.state = state;
	this.city = city;
	this.streetLine = streetLine;
	this.ZIP = ZIP;
}
public double getTotalAfterTax(double total) {
	switch(this.state) {
	case IN:
		return total*1.07;
	case FL:
		if(total>100) return (total-100)*0.1+total+8;
		else return total*1.08;
	case CA:
		return total*1.15;
	}
		
	return total;	
}
}
