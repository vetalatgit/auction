package ua.cn.stu.auction2011.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NamedQueries( {
@NamedQuery(name = "Address.deleteByUser", query = "DELETE FROM Address x WHERE x.user = ?1"),
@NamedQuery(name = "Address.getAddressByUser", query = "SELECT x FROM Address x WHERE x.user = ?1")}) 

public class Address extends DomainObject {
	/**
	 * Country of the user
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty
	private String country=new String();
	/**
	 * Postal code of the user
	 */
	@NotEmpty
	private String code=new String();
	/**
	 * City of the user
	 */
	@NotEmpty	
	private String img;
	
	@NotEmpty
	private String city=new String();
	/**
	 *Address of the user
	 */
	@NotEmpty
	private String addr=new String();
	/**
	 * @return the country
	 */
	private Users user;
	
	public String getCountry() {
		return country;
	}
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "USER_ID", insertable = false, updatable=false)
	public Users getUser() {
		return user;
	}
	
	
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the postal code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the address
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * default constructor
	 */
	public Address() {
		super();
	}
	/**
	 * constructor with parameters
	 * @param country the country to set
	 * @param code  the code to set
	 * @param city the city to set
	 * @param addr the addr to set
	 * @param phone the phone to set
	 */
	public Address(String country, String code, String city, String addr) {
		super();
		this.img="wave.med.gif";
		this.country = country;
		this.code = code;
		this.city = city;
		this.addr = addr;
	}
	
	
}
