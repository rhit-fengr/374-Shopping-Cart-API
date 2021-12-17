import java.sql.Time;
import java.time.LocalDateTime;
import java.util.*;

public class APIHandler {
HashMap<Integer,Item> items = new HashMap<Integer,Item>();
HashMap<Integer,Cart> carts = new HashMap<Integer,Cart>();

HashMap<String,Double> codes = new HashMap<String,Double>();

	public APIHandler(HashMap<Integer,Item> items, HashMap<Integer,Cart> carts) {
		this.items = items;
		this.carts = carts;
	}

	public APIHandler(HashMap<Integer,Item> items, HashMap<Integer,Cart> carts, HashMap<String,Double> codes) {
		this.items = items;
		this.carts = carts;
		this.codes = codes;
	}
	
	public String handleRequest(Request request) {
		int cid;
		Cart cart;
		Item item;
		int iid;
		switch (request.type) {
		case VIEW:
			cid = (int) request.args[0];
			cart = carts.get(cid);
			Address addr = (Address) request.args[1];
			
			String s = "";
			if(cart.items.isEmpty()) {
				s+="The cart is empty";
			}else {
				for(Item it:cart.items.keySet()) {
					s+=it.toString();
					s+="\nQuantity: "+cart.items.get(it);
				}
			}
			
			s+="\nTotal: "+addr.getTotalAfterTax(cart.getTotalAfterDisc());
			return s;
			
			
		case ADD:
			cid = (int) request.args[0];
			iid = (int) request.args[1];	
			
			
			item = items.get(iid);
			cart = carts.get(cid);
			
			if(item.stock>0) {
				cart.addItem(item);
				item.stock--;
				return "Sucessfully add the item: "+ item.name;
			}else {
				return "Insufficient stock of the item: "+item.name;
			}
			
			
		case MOD:
			cid = (int) request.args[0];
			iid = (int) request.args[1];
			int q = (int) request.args[2];
			
			item = items.get(iid);
			cart = carts.get(cid);
			
			if(q<0) {
				return "Quantity cannot be negative";
			}else if(q>item.stock) {
				return "Insufficient stock of the item to modify: "+item.name;
			}else {
				if(!cart.items.containsKey(item)) {
					return "The item: "+item.name+" does not exist in the cart";
				}
				cart.items.put(item, q);
				item.stock-=q;
				return "Sucessfully modify the item: "+ item.name;
			}
			
			
		case DISC:
			cid = (int) request.args[0];
			String code = (String) request.args[1];
			
			cart = carts.get(cid);
			
			
			if(cart.violationCount>=3) {
				return "Too many violations in a short time";
			}
			else if(codes.containsKey(code)) {
				if(cart.appliedcode.contains(code)) {
					cart.violationCount++;
					cart.lastTimeFailed = LocalDateTime.now();
					return "The code was applied already";
				}
				 cart.discount+=codes.get(code);
				 cart.appliedcode+=code;
				 return "The code is successfully applied";
			}else {
				cart.violationCount++;
				cart.lastTimeFailed = LocalDateTime.now();
				return "The code is invalid";
			}
			
			
		}
		
		return null;
	}
}
