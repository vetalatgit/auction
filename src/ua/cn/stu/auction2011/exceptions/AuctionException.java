package ua.cn.stu.auction2011.exceptions;

/**
 * General exception for the whole project
 * 
 */
public class AuctionException extends Exception {

	private static final long serialVersionUID = 1L;

	public AuctionException() {
		super();
	}

	public AuctionException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuctionException(String message) {
		super(message);
	}

	public AuctionException(Throwable cause) {
		super(cause);
	}

}
