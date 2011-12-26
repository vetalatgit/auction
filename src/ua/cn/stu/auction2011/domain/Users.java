package ua.cn.stu.auction2011.domain;


import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries( {        
	@NamedQuery(name = "Users.getUserByLogin", query = "SELECT x FROM Users x WHERE x.login = ?1"), 
	@NamedQuery(name = "Users.getUserByRole", query = "SELECT x FROM Users x WHERE x.role = ?1"), 
	@NamedQuery(name = "Users.getUserByEmail", query = "SELECT x FROM Users x WHERE x.email = ?1"),
	@NamedQuery(name = "Users.decrRating", query = "UPDATE Users x SET x.rating = x.rating+x.rating-(x.rating+1) WHERE x.id = ?1"),
	@NamedQuery(name = "Users.incrRating", query = "UPDATE Users x SET x.rating = 1+x.rating WHERE x.id = ?1")})
public class Users extends DomainObject {
	
	private static final long serialVersionUID = 1L;
	@NotEmpty
	private String name;
	@NotEmpty
	private String login;
	private Role role;
	@NotEmpty
	private String email;
	@NotEmpty
	private String phone;
	@NotEmpty
	private String password;

	private Long rating;

	private Long identnumb;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Long getIdentnumb() {
		return identnumb;
	}
	public void setIdentnumb(Long identnumb) {
		this.identnumb = identnumb;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void incrRating() {
		rating++;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getRating() {
		return rating;
	}
	public void setRating(Long rating) {
		this.rating = rating;
	}
	
	public UserSf getUsersSafe(){
		UserSf usf = new UserSf();
		usf.setId(getId());
		usf.setEmail(email);
		usf.setIdentnumb(identnumb);
		usf.setLogin(login);
		usf.setName(name);
		usf.setPhone(phone);
		usf.setRating(rating);
		usf.setRole(role);
		return usf;	
	}

}
