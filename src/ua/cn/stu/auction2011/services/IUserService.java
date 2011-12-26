package ua.cn.stu.auction2011.services;

import ua.cn.stu.auction2011.exceptions.GeneralServiceException;

import java.util.List;

import ua.cn.stu.auction2011.domain.Role;
import ua.cn.stu.auction2011.domain.Users;
import ua.cn.stu.auction2011.domain.UserSf;
import javax.ejb.Local;
import javax.ejb.Remote;

@Remote
public interface IUserService extends IGenericService <Users>{


	
	public List<UserSf> getUsersByRole(Role role)
			throws IllegalArgumentException, GeneralServiceException;
	
	public UserSf getUserByLogin(String login)
			;
	
	public UserSf getUserByMail(String mail)
			throws IllegalArgumentException, GeneralServiceException;
		
	public Users regUser(Users usr)
			throws IllegalArgumentException, GeneralServiceException;
	
	public UserSf updateUser(UserSf usr)
			throws IllegalArgumentException, GeneralServiceException;
	
	/*public boolean loginUser(String login,String passwd)
			throws IllegalArgumentException, GeneralServiceException;
	*/
	public void incrUserRating(Long userId)
			throws IllegalArgumentException, GeneralServiceException;
	
	public void decrUserRating(Long userId)
			throws IllegalArgumentException, GeneralServiceException;
	
	public void setUserRating(Long userId, Long rating)
			throws IllegalArgumentException, GeneralServiceException;
	
}
