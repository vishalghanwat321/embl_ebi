package com.org.web.embl_ebi.person.domain;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.org.web.embl_ebi.person.util.constant.FavoriteColor;
import com.org.web.embl_ebi.util.unique_sequence_generator.AbstractRandomLongIdEntity;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote Person entity
 * @since 2020
 */
@Entity
@Table(name = "person")
@Cacheable(false)
public class Person extends AbstractRandomLongIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "age", nullable = false)
	private Integer age;

	@Enumerated(EnumType.STRING)
	@Column(name = "favourite_color", nullable = false)
	private FavoriteColor favouriteColor;

	@Column(name = "hobby")
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	private Set<String> hobbies;

	@Column(name = "creation_timestamp", updatable = false, nullable = false)
	private LocalDateTime creationTimestamp;

	@Column(name = "last_modification_timestamp")
	private LocalDateTime lastModificationTimestamp;

	@PreUpdate
	protected void onPreUpdate() {
		this.lastModificationTimestamp = LocalDateTime.now(ZoneOffset.UTC);
	}

	@PrePersist
	protected void onPrePersist() {
		super.onPrePersist();
		this.creationTimestamp = LocalDateTime.now(ZoneOffset.UTC);
	}

	public LocalDateTime getCreationTimestamp() {
		return creationTimestamp;
	}

	public LocalDateTime getLastModificationTimestamp() {
		return lastModificationTimestamp;
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
