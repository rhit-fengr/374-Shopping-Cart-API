import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class Cart implements Serializable{

int CID;
HashMap<Item,Integer> items = new HashMap<Item,Integer>();
int violationCount;
double discount;
String appliedcode;
LocalDateTime lastTimeFailed;


public Cart(int CID, HashMap<Item,Integer> items) {
	this.CID = CID;
	this.items = items;
	this.violationCount = 0;
	this.discount = 0.0;
	this.appliedcode = "";
}

public void addItem(Item item) {
	if(items.containsKey(item)) {
		items.put(item, items.get(item)+1);
	}else {
		items.put(item, 1);
	}
}

public double getTotalAfterDisc() {
	double total=0;
	
	for(Item it:this.items.keySet()) {
		total+=it.price*(1-it.applicableDisc)*items.get(it);
	}
	total*=(1-discount);
	return total;
	
}

}
