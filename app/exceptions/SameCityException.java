package exceptions;

public class SameCityException extends Exception {

	public SameCityException() {
		super("Destination city is same as source.");
	}

}
