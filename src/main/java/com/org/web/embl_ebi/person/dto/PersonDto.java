package com.org.web.embl_ebi.person.dto;

import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.org.web.embl_ebi.person.util.constant.FavoriteColor;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote PersonDto
 * @since 2020
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDto {

	@JsonProperty(value = "id", required = true)
	private Long id;

	@NotNull
	@JsonProperty(value = "first_name", required = true)
	private String firstName;

	@NotNull
	@JsonProperty(value = "last_name", required = true)
	private String lastName;

	@NotNull
	@Min(value = 0)
	@Max(value = 100)
	@JsonProperty(value = "age", required = true)
	private Integer age;

	@NotNull
	@JsonProperty(value = "favourite_color", required = true)
	private FavoriteColor favouriteColor;

	@JsonProperty(value = "hobby", required = true)
	private Set<String> hobbies;

	@JsonProperty("creationTimestamp")
	private Long creationTimestamp;

	@JsonProperty("lastModificationTimestamp")
	private Long lastModificationTimestamp;

	public Long getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Long getLastModificationTimestamp() {
		return lastModificationTimestamp;
	}

	public void setLastModificationTimestamp(Long lastModificationTimestamp) {
		this.lastModificationTimestamp = lastModificationTimestamp;
	}

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