package com.dofany.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private Long userId;

	@Column(nullable = false, length = 20, unique = true)
	@JsonProperty
	private String id;
	@JsonIgnore
	private String pswd1;
	@JsonProperty
	private String name;
	@JsonIgnore
	private String yy;
	@JsonIgnore
	private String mm;
	@JsonIgnore
	private String dd;
	@JsonProperty
	private String gender;
	@JsonProperty
	private String email;
	@JsonProperty
	private String adress;
	@JsonIgnore
	private String mobile;

	public Long getUserId() {
		return userId;
	}

	public boolean matchUserId(Long newUserId) {
		if (newUserId == null) {
			return false;
		}
		return newUserId.equals(userId);
	}

	public String getId() {
		return id;
	}

	public void setUserId(String id) {
		this.id = id;
	}

	public boolean matchPassword(String newPswd1) {
		if (newPswd1 == null) {
			return false;
		}
		return newPswd1.equals(pswd1);
	}

	public void setPswd1(String pswd1) {
		this.pswd1 = pswd1;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setYy(String yy) {
		this.yy = yy;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public void setDd(String dd) {
		this.dd = dd;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void update(User newUser) {
		this.pswd1 = newUser.pswd1;
		this.name = newUser.name;
		this.yy = newUser.yy;
		this.mm = newUser.mm;
		this.dd = newUser.dd;
		this.gender = newUser.gender;
		this.email = newUser.email;
		this.adress = newUser.adress;
		this.mobile = newUser.mobile;

	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", id=" + id + ", pswd1=" + pswd1 + ",  name=" + name + ", yy=" + yy + ", mm="
				+ mm + ", dd=" + dd + ", gender=" + gender + ", email=" + email + ", adress=" + adress + ", mobile="
				+ mobile + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
