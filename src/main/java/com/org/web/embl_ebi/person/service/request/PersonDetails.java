package com.org.web.embl_ebi.person.service.request;

import java.io.Serializable;
import java.util.Set;

import com.org.web.embl_ebi.person.util.constant.FavoriteColor;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote StorePersonDetails
 * @since 2020
 */
public class PersonDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String firstName;

	private String lastName;

	private Integer age;

	private FavoriteColor favouriteColor;

	private Set<String> hobbies;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public FavoriteColor getFavouriteColor() {
		return favouriteColor;
	}

	public void setFavouriteColor(FavoriteColor favouriteColor) {
		this.favouriteColor = favouriteColor;
	}

	public Set<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(Set<String> hobbies) {
		this.hobbies = hobbies;
	}
}
