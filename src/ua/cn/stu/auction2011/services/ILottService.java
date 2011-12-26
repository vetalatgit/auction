package ua.cn.stu.auction2011.services;

import ua.cn.stu.auction2011.exceptions.GeneralServiceException;

import java.util.Calendar;
import java.util.List;

import ua.cn.stu.auction2011.domain.LotInfo;
import ua.cn.stu.auction2011.domain.Lott;
import javax.ejb.Local;
import javax.ejb.Remote;

@Remote
public interface ILottService extends IGenericService<Lott> {

	public List<Lott> getLott(String userlogn, Calendar date, String type, String name, int Price)
			throws IllegalArgumentException, GeneralServiceException;
	
	public List<Long>  getSortLott()
			throws IllegalArgumentException, GeneralServiceException;
	
	
	public List<String> getAllTypeOfLotts()
			throws IllegalArgumentException, GeneralServiceException;
	
	public Lott setLott(Lott lot, Long idUser)
			throws IllegalArgumentException, GeneralServiceException;
	
}
