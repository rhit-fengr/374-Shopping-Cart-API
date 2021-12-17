import java.io.Serializable;

public class Item implements Serializable{

public int IID;
public String name, description, picture;
public int stock;
public double price, applicableDisc;

public Item(int IID, String name, double price, String description, String picture, int stock, double disc) {
	this.IID = IID;
	this.name = name;
	this.price = price;
	this.description = description;
	this.picture = picture;
	this.stock = stock;
	this.applicableDisc = disc;
}


public void setDiscount(double disc) {
	this.applicableDisc = disc;
}

@Override
public String toString() {
	return "Name: " + name + "\nDescription: " + description+"\nPicture: "+picture+"\nPrice: "+price;
}
	
}
