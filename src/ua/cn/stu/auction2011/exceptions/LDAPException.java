package ua.cn.stu.auction2011.exceptions;

import ua.cn.stu.auction2011.services.IGenericService;
/**
 * General exception for the whole project
 */

public class LDAPException extends AuctionException {

	private static final long serialVersionUID = 1L;

	/** Service that caused exception */
	protected IGenericService<?> service;

	public LDAPException() {
		super();
	}

	/**
	 * @param message
	 *            Message with exception
	 * @param cause
	 *            Cause of exception
	 */
	public LDAPException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 *            Message with exception
	 */
	public LDAPException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 *            Cause of exception
	 */
	public LDAPException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param service
	 *            {@link IGenericService} object
	 */
	public LDAPException(IGenericService<?> service) {
		super();
		this.service = service;
	}

	/**
	 * @param message
	 *            Message with exception
	 * @param cause
	 *            Cause of exception
	 * @param service
	 *            {@link IGenericService} object
	 */
	public LDAPException(String message, Throwable cause,
			IGenericService<?> service) {
		super(message, cause);
		this.service = service;
	}

	/**
	 * @param message
	 *            Message with exception
	 * @param service
	 *            {@link IGenericService} object
	 */
	public LDAPException(String message, IGenericService<?> service) {
		super(message);
		this.service = service;
	}

	/**
	 * @param cause
	 *            Cause of exception
	 * @param service
	 *            {@link IGenericService} object
	 */
	public LDAPException(Throwable cause,
			IGenericService<?> service) {
		super(cause);
		this.service = service;
	}

	/**
	 * @return the service
	 */
	public IGenericService<?> getService() {
		return service;
	}

}
