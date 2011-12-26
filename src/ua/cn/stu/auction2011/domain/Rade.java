package ua.cn.stu.auction2011.domain;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NamedQueries( {
	@NamedQuery(name = "Rade.getRadeByAuth", query = "SELECT x FROM Rade x WHERE x.user = ?1 order by x.timerate"), 
	@NamedQuery(name = "Rade.getRadeByLott", query = "SELECT x FROM Rade x WHERE x.lott = ?1 order by x.timerate"), 
	@NamedQuery(name = "Rade.getSpendMoney", query = "SELECT SUM(x.price) FROM Rade x WHERE x.id =?1 and x.isdone=true"),
	@NamedQuery(name = "Rade.doneRade", query = "UPDATE Rade x SET x.isdone = true WHERE x.id = ?1"),
	@NamedQuery(name = "Rade.deleteAllByUser", query = "DELETE FROM Rade x WHERE x.user = ?1"),
    @NamedQuery(name = "Rade.deleteAllByLott", query = "DELETE FROM Rade x WHERE x.lott = ?1")}) 
public class Rade extends DomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long price;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar timerate;
	
	private Boolean isdone;
	
	private Users user;

	private Lott lott;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "LOTT_ID", insertable = false, updatable=false)
	public Lott getLott() {
		return lott;
	}
	public void setLott(Lott lott) {
		this.lott = lott;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "USER_ID", insertable = false, updatable=false)
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Calendar getTimerate() {
		return timerate;
	}
	public void setTimerate(Calendar timerate) {
		this.timerate = timerate;
	}
	public Boolean getIsdone() {
		return isdone;
	}
	public void setIsdone(Boolean isdone) {
		this.isdone = isdone;
	}
		
}
