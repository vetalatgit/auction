package ua.cn.stu.auction2011.domain;

/**
 * Specifies different types of users
 * 
 * @author vetal
 */
public enum Role {
	
	/**
	 * Administrator of the system
	 */
	ADMIN,
	
	/**
	 * User that can buy or sell lots
	 */
	USER,
	MODERATOR
}