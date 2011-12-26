package ua.cn.stu.auction2011.domain;

/**
 * Safe user entity for change data with Application
 */
public class UserSf {
	
	public UserSf(){
		
	}
	private String name;

	private String login;
	public String getLogin() {
		return login;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	private String email;
	
	private Long id;

	private String phone;

	private Long rating;

	private Long identnumb;
	
	public Long getIdentnumb() {
		return identnumb;
	}
	public void setIdentnumb(Long identnumb) {
		this.identnumb = identnumb;
	}
	private Role role;
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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

	public Long getRating() {
		return rating;
	}
	public void setRating(Long rating) {
		this.rating = rating;
	}

}
