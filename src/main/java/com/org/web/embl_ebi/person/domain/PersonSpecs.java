package com.org.web.embl_ebi.person.domain;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.jpa.domain.Specification;

import com.org.web.embl_ebi.person.util.constant.FavoriteColor;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @since 2020
 */
public class PersonSpecs {

	public static Specification<Person> filterByIds(Iterable<Long> filterIds) {
		return Optional
				.ofNullable(filterIds).map(items -> StreamSupport.stream(items.spliterator(), false)
						.filter(Objects::nonNull).collect(Collectors.toSet()))
				.map(validIds -> (Specification<Person>) (root, query, cb) -> {
					query.distinct(true);
					return root.get(Person_.id).in(validIds);
				}).orElse(emptyAnd());
	}

	public static Specification<Person> filterByFirstName(String firstName) {
		return Optional.ofNullable(firstName).map(pattern -> {
			String trimmedPattern = pattern.trim();
			if (trimmedPattern.isEmpty())
				return emptyAnd();
			return (Specification<Person>) (root, query, cb) -> cb.like(cb.lower(root.get(Person_.firstName)),
					String.format("%%%s%%", trimmedPattern).toLowerCase());
		}).orElse(emptyAnd());
	}

	public static Specification<Person> filterByLastName(String lastName) {
		return Optional.ofNullable(lastName).map(pattern -> {
			String trimmedPattern = pattern.trim();
			if (trimmedPattern.isEmpty())
				return emptyAnd();
			return (Specification<Person>) (root, query, cb) -> cb.like(cb.lower(root.get(Person_.lastName)),
					String.format("%%%s%%", trimmedPattern).toLowerCase());
		}).orElse(emptyAnd());
	}

	public static Specification<Person> filterByFavoriteColor(FavoriteColor favouriteColor) {
		if (Objects.isNull(favouriteColor))
			return emptyAnd();
		return (root, query, cb) -> cb.equal(root.get(Person_.favouriteColor), favouriteColor);
	}

	public static Specification<Person> filterByAge(Iterable<Integer> age) {
		return Optional.ofNullable(age).map(items -> StreamSupport.stream(items.spliterator(), false)
				.filter(Objects::nonNull).collect(Collectors.toSet()))
				.map(validAge -> (Specification<Person>) (root, query, cb) -> {
					query.distinct(true);
					return root.get(Person_.age).in(validAge);
				}).orElse(emptyAnd());
	}

	public static Specification<Person> filterByHobbies(Set<String> hobbies) {
		if (Objects.isNull(hobbies) || hobbies.isEmpty())
			return emptyAnd();
		return (root, query, cb) -> {
			query.distinct(true);
			return root.get(Person_.hobbies).in(hobbies);
		};
	}

	private static Specification<Person> emptyAnd() {
		return (root, query, cb) -> cb.and();
	}
}