import java.util.*;

public class Request {
	Type type;
	
	// ADD: args[0]: Cart ID, args[1]: Item ID
	// VIEW: args[0]: Cart ID, args[1]: Address
	// MOD: args[0]: Cart ID, args[1]: Item ID, args[2]: quantity
	// DISC: args[0]: Cart ID, args[1]: discount code
	Object[] args;
	
	
	public Request(Type type, Object[] args) {
		this.type = type;
		this.args = args;
	}
	
	
	
}
