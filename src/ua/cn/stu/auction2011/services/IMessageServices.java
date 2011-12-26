package ua.cn.stu.auction2011.services;

import ua.cn.stu.auction2011.exceptions.GeneralServiceException;

import java.util.Calendar;
import java.util.List;
import ua.cn.stu.auction2011.domain.Message;

import javax.ejb.Remote;

@Remote
public interface IMessageServices extends IGenericService<Message> {

	public List<Message> getMessage(String userlogn,String lottname, Calendar date, 
			String author,String theme, Boolean isread, boolean ismesg)
			throws IllegalArgumentException, GeneralServiceException;
	
	public boolean delMessageByUser(Long userId)
			throws IllegalArgumentException, GeneralServiceException;

	public boolean delMessageByLott(Long lottId)
			throws IllegalArgumentException, GeneralServiceException;
	
	public Message sendMessage(Message mess, Long lottId, Long userId, Long idAuthor)
			throws IllegalArgumentException, GeneralServiceException;
	
	public boolean readdMessage(Long messId)
			throws IllegalArgumentException, GeneralServiceException;
	
	public List<String> getAllThems(Long usr) throws IllegalArgumentException,
			GeneralServiceException; 
}
