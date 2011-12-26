package ua.cn.stu.auction2011.ImplService;

import ua.cn.stu.auction2011.domain.Message;
import ua.cn.stu.auction2011.exceptions.GeneralServiceException;
import ua.cn.stu.auction2011.services.ILottService;
import ua.cn.stu.auction2011.services.IMessageServices;
import ua.cn.stu.auction2011.services.IUserService;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
@Remote
public class MessageServiceImpl extends GenericServiceImpl<Message> implements IMessageServices {

	@EJB
	IUserService usrserv;
	
	@EJB
	ILottService lotserv;
	
	
	public MessageServiceImpl() {
		super(Message.class);
	}
	
	public MessageServiceImpl(Class<Message> persistentClass) {
		super(persistentClass);
	}

	@Override
	public boolean delMessageByUser(Long userId)
			throws IllegalArgumentException, GeneralServiceException {
		executeUpdateQuery("Message.deleteAllByUser", true, usrserv.getEntityById(userId));
		return true;
	}

	@Override
	protected void beforeEntityAddUpdate(Message entity)
			throws GeneralServiceException {
	}
	
	@Override
	public List<Message> getAllEntites(){
		try {
			return executeQuery("Message.getAllMess",true,false);
		} catch (IllegalArgumentException e) {
		return null;
		} catch (GeneralServiceException e) {
			return null;
		}
	}
	 
	@Override
	protected void beforeEntityDelete(Message entity)
			throws GeneralServiceException {
	}


	@Override
	public boolean delMessageByLott(Long lottId)
			throws IllegalArgumentException, GeneralServiceException {
		executeUpdateQuery("Message.deleteAllByLott", true, lotserv.getEntityById(lottId));
		return true;
	}

	@Override
	public Message sendMessage(Message mess,Long lotId, Long destuserId, Long idAuthor)
			throws IllegalArgumentException, GeneralServiceException {
		/*if((usrserv.getEntityById(idAuthor)==null)){
			throw new IllegalArgumentException("author is null, or bouth not null parameters " +
					"desination User and lott");
		}else{*/
		if(idAuthor==null)mess.setAuthorname(null); else mess.setAuthorname(usrserv.getEntityById(idAuthor).getLogin());	
		if(destuserId==null)mess.setUser(null); else mess.setUser(usrserv.getEntityById(destuserId));
			if(lotId==null)mess.setLott(null); else mess.setLott(lotserv.getEntityById(lotId));
		//	mess.setAuthorname(usrserv.getEntityById(idAuthor).getLogin());
		return save(mess);
		
	}

	@Override
	public List<Message> getMessage(String userlogn, String lottname, Calendar date,
			String author, String theme, Boolean isread, boolean ismesg) throws IllegalArgumentException,
			GeneralServiceException {
		List<Message> messags=getAllEntites();
		/*for(int i=0; i<messags.size();){
			if(!ismesg&&(messags.get(i).getUser()!=null)){messags.remove(i);continue;}
			else
			if((userlogn!=null)&&(!messags.get(i).getUser().getLogin().contains(userlogn))){messags.remove(i);continue;}
			if(ismesg&&(messags.get(i).getLott()!=null)){messags.remove(i);continue;}
			else
			if((lottname!=null)&&(!lottname.equals(messags.get(i).getLott().getName()))){messags.remove(i);continue;}
			if((author!=null)&&(author!=messags.get(i).getAuthorname())){messags.remove(i);continue;}
			if((theme!=null)&&(!messags.get(i).getThem().contains(theme))){messags.remove(i);continue;}
			if((date!=null)&&(date.getTimeInMillis()>messags.get(i).getDate().getTimeInMillis())){messags.remove(i);continue;}
			if((isread!=null)&&(!isread==messags.get(i).isRead())){messags.remove(i);continue;}
			i++;
		}*/
		return messags;

	}

	@Override
	public boolean readdMessage(Long messId) throws IllegalArgumentException,
			GeneralServiceException {
		executeUpdateQuery("Message.readMessage", true, messId);
		return true;
	}
	
	@Override
	public List<String> getAllThems(Long usr) throws IllegalArgumentException,
			GeneralServiceException {
		return executeQuery("Message.getAllThems", true, false, usrserv.getEntityById(usr));
	}
	
}
