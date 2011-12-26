package ua.cn.stu.auction2011.domain;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NamedQueries( {
	@NamedQuery(name = "Lott.getAllTypes", query = "SELECT DISTINCT x.type FROM Lott x"),
	@NamedQuery(name = "Lott.getLotByUser", query = "SELECT x FROM Lott x WHERE x.user = ?1"), 
	@NamedQuery(name = "Lott.deleteAllByUser", query = "DELETE FROM Lott x WHERE x.user = ?1")})

public class Lott extends DomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty
	private String name;
	
	@NotEmpty
	@Lob
	private String text;
	
	private Long price;
	
	private Long realprice;
	@NotEmpty
	private String type;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datepay;
	
	private Users user;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "USER_ID", insertable = false, updatable=false)
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}

	public Long getRealprice() {
		return realprice;
	}
	public void setRealprice(Long realprice) {
		this.realprice = realprice;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Calendar getDatepay() {
		return datepay;
	}
	public void setDatepay(Calendar datepay) {
		this.datepay = datepay;
	}

	public Lott(){
		this.text="chenge details of lot";
	}
}
