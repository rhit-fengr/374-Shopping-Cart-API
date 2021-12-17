import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.OrderWith;

public class Tests {
	APIHandler api;
	
	public Tests() {
		// Add 3 item types and 2 carts in total
		HashMap<Integer,Item> items = new HashMap<Integer,Item>();
		HashMap<Integer,Cart> carts = new HashMap<Integer,Cart>();
		
		HashMap<String,Double> codes = new HashMap<String,Double>();
		
		Item i1 = new Item(0, "Apple", 5,"red apple","apple.jpg",5,0);
		Item i2 = new Item(1, "Beef", 8,"filet","beef.jpg",3,0.2);
		Item i3 = new Item(2, "Coffee", 5,"dark coffee","coffee.jpg",1,0);
		
		items.put(i1.IID,i1);
		items.put(i2.IID, i2);
		items.put(i3.IID, i3);
		
		Cart cart = new Cart(1,new HashMap<Item,Integer>());	
		Cart cart2 = new Cart(2,new HashMap<Item,Integer>());	
		
		carts.put(cart.CID,cart);
		carts.put(cart2.CID,cart2);
		
		cart2.addItem(i2);
		
		codes.put("HAPPYCRHISTMAS",0.5);
		
		this.api  = new APIHandler(items, carts, codes);
}
	@Test
	public void testAddRequestHappyPath() {
		Object[] addArgs = new Object[2];
		addArgs[0] = 1;
		addArgs[1] = 2;
		
		
		Request r1 = new Request(Type.ADD,addArgs);
		
		String res = api.handleRequest(r1);
		System.out.println(res);
		assertEquals("Sucessfully add the item: Coffee", res);		
}
	
	@Test
	public void testAddWhenOutofStock() {
		Object[] addArgs = new Object[2];
		addArgs[0] = 1;
		addArgs[1] = 2;
		
		
		Request r1 = new Request(Type.ADD,addArgs);
		
		api.handleRequest(r1);
		String res = api.handleRequest(r1);
		System.out.println(res);
		assertEquals("Insufficient stock of the item: Coffee", res);
		
}
	@Test
	public void testViewtheCart() {
		Object[] viewArgs = new Object[2];
		viewArgs[1] = new Address(Country.US,State.IN,"Terre Haute","Wabash Ave 5500",47803);
		viewArgs[0] = 2;		
		
		Request r1 = new Request(Type.VIEW,viewArgs);
		
		String res = api.handleRequest(r1);
		System.out.println(res);
		assertEquals("Name: Beef\n"
				+ "Description: filet\n"
				+ "Picture: beef.jpg\n"
				+ "Price: 8.0\n"
				+ "Quantity: 1\n"
				+ "Total: 6.848000000000001", res);	
		
}
	
	@Test
	public void testViewEmptyCart() {
		Object[] viewArgs = new Object[2];
		viewArgs[0] = 1;		
		viewArgs[1] = new Address(Country.US,State.IN,"Terre Haute","Wabash Ave 5500",47803);
		
		Request r1 = new Request(Type.VIEW,viewArgs);
		
		String res = api.handleRequest(r1);
		System.out.println(res);
		assertEquals("The cart is empty\n"
				+ "Total: 0.0", res);	
		
}
	@Test
	public void testModifytheCart() {
		Object[] modArgs = new Object[3];
		modArgs[0] = 2;	
		modArgs[1] = 1;
		modArgs[2] = 1;
		
		Request r1 = new Request(Type.MOD,modArgs);
		
		String res = api.handleRequest(r1);
		System.out.println(res);
		assertEquals("Sucessfully modify the item: Beef", res);	
		
}
	@Test
	public void testModifyItemNotintheCart() {
		Object[] modArgs = new Object[3];
		modArgs[0] = 2;	
		modArgs[1] = 2;
		modArgs[2] = 1;
		
		Request r1 = new Request(Type.MOD,modArgs);
		
		String res = api.handleRequest(r1);
		System.out.println(res);
		assertEquals("The item: Coffee does not exist in the cart", res);			
}
	@Test
	public void testModifyWhenOutofStock() {
		Object[] modArgs = new Object[3];
		modArgs[0] = 2;	
		modArgs[1] = 1;
		modArgs[2] = 5;
		
		Request r1 = new Request(Type.MOD,modArgs);
		
		String res = api.handleRequest(r1);
		System.out.println(res);
		assertEquals("Insufficient stock of the item to modify: Beef", res);			
}
	@Test
	public void testApplyValidDiscount() {
		Object[] disArgs = new Object[2];
		disArgs[0] = 2;	
		disArgs[1] = "HAPPYCRHISTMAS";
		
		Request r1 = new Request(Type.DISC,disArgs);
		
		String res = api.handleRequest(r1);
		System.out.println(res);
		assertEquals("The code is successfully applied", res);			
}
	@Test
	public void testApplyInvalidDiscount() {
		Object[] disArgs = new Object[2];
		disArgs[0] = 2;	
		disArgs[1] = "CRHISTMAS";
		
		Request r1 = new Request(Type.DISC,disArgs);
		
		String res = api.handleRequest(r1);
		System.out.println(res);
		assertEquals("The code is invalid", res);			
}
	
	@Test
	public void testApplyUsedDiscount() {
		Object[] disArgs = new Object[2];
		disArgs[0] = 2;	
		disArgs[1] = "HAPPYCRHISTMAS";
		
		Request r1 = new Request(Type.DISC,disArgs);
		
		api.handleRequest(r1);
		String res = api.handleRequest(r1);
		System.out.println(res);
		assertEquals("The code was applied already", res);			
}
	@Test
	public void testApplyInvalidDiscount3TimesInShortTime() {
		Object[] disArgs = new Object[2];
		disArgs[0] = 2;	
		disArgs[1] = "CRHISTMAS";
		
		Request r1 = new Request(Type.DISC,disArgs);
		
		api.handleRequest(r1);
		api.handleRequest(r1);
		api.handleRequest(r1);
		String res = api.handleRequest(r1);
		System.out.println(res);
		assertEquals("Too many violations in a short time", res);			
}
	
	
	}