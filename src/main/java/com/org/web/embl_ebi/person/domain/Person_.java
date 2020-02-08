package com.org.web.embl_ebi.person.domain;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.org.web.embl_ebi.person.util.constant.FavoriteColor;
import com.org.web.embl_ebi.util.unique_sequence_generator.AbstractRandomLongIdEntity_;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @since 2020
 */
@StaticMetamodel(Person.class)
public class Person_ extends AbstractRandomLongIdEntity_ {

	public static volatile SingularAttribute<Person, Integer> age;
	public static volatile SingularAttribute<Person, String> firstName;
	public static volatile SingularAttribute<Person, String> lastName;
	public static volatile SingularAttribute<Person, FavoriteColor> favouriteColor;
	public static volatile SetAttribute<Person, String> hobbies;
}
