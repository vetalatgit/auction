package ua.cn.stu.auction2011.services;

import ua.cn.stu.auction2011.exceptions.GeneralServiceException;

import java.util.Calendar;
import java.util.List;

import ua.cn.stu.auction2011.domain.Rade;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.persistence.Query;

@Local
public interface IRadeService extends IGenericService<Rade> {

	public boolean delRadeByUser(Long userId)
			throws IllegalArgumentException, GeneralServiceException;

	public boolean delRadeByLott(Long lottId)
			throws IllegalArgumentException, GeneralServiceException;
	
	/*public List<Rade> getRadeByAuth(Calendar date, Long userId, Long price,String sale)
			throws IllegalArgumentException, GeneralServiceException;
	*/
	public List<Rade> getRads(Calendar date, String lottname, String userLogn, Long price,String sale)
			throws IllegalArgumentException, GeneralServiceException;
	
	public List<Rade> getRadsByLU(Long lottId, Long userId)
			throws IllegalArgumentException, GeneralServiceException;
	
	public boolean doneRade(Long radeId)
			throws IllegalArgumentException, GeneralServiceException;
	
	public boolean doRade(Rade rade, Long idUser, Long idLot )
			throws IllegalArgumentException, GeneralServiceException;
	
	public int getSpendMoney(Long userId)
			throws IllegalArgumentException, GeneralServiceException;
	
}
