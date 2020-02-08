package com.org.web.embl_ebi.person.controller;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.web.embl_ebi.handler.builder.SpecificationsBuilder;
import com.org.web.embl_ebi.person.domain.Person;
import com.org.web.embl_ebi.person.domain.PersonSpecs;
import com.org.web.embl_ebi.person.dto.PersonDto;
import com.org.web.embl_ebi.person.service.PersonDetailsService;
import com.org.web.embl_ebi.person.service.request.PersonDetails;
import com.org.web.embl_ebi.person.util.constant.FavoriteColor;
import com.org.web.embl_ebi.person.util.exception.DuplicateRecordException;
import com.org.web.embl_ebi.person.util.exception.RecordNotFoundException;
import com.org.web.embl_ebi.person.util.mapper.PersonDtoToPersonDetailsMapper;
import com.org.web.embl_ebi.person.util.mapper.PersonToPersonDtoMapper;
import com.org.web.embl_ebi.util.constants.ApiPathConstants;

/**
 * 
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote PersonDetailsController
 * @since 2020
 */

@RestController
@RequestMapping(ApiPathConstants.PERSON)
public class PersonDetailsController {

	@Autowired
	private PersonDetailsService service;

	@Autowired
	private PersonDtoToPersonDetailsMapper personDtoToPersonDetailsMapper;

	@Autowired
	private PersonToPersonDtoMapper personToPersonDtoMapper;

	/**
	 * @param id
	 * @return PersonDto
	 * @throws RecordNotFoundException
	 */
	@GetMapping(value = "/{id:[0-9]+}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public PersonDto findPersonById(@PathVariable("id") Long id) throws RecordNotFoundException {
		Person person = this.service.query(id);
		return this.personToPersonDtoMapper.convert(person);
	}

	/**
	 * 
	 * @param page
	 * @param pageSize
	 * @param sortProperties
	 * @param sortAscending
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param favouriteColor
	 * @param hobbies
	 * @param ids
	 * @return Iterable<Person>
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = "", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public Iterable<PersonDto> listPersons(@RequestParam(required = false, value = "page") Integer page,
			@RequestParam(required = false, value = "page_size") Integer pageSize,
			@RequestParam(required = false, value = "sort_props[]") String[] sortProperties,
			@RequestParam(required = false, value = "sort_asc", defaultValue = "false") boolean sortAscending,
			@RequestParam(required = false, value = "first_name") String firstName,
			@RequestParam(required = false, value = "last_name") String lastName,
			@RequestParam(required = false, value = "age[]") Set<Integer> age,
			@RequestParam(required = false, value = "favourite_color") FavoriteColor favouriteColor,
			@RequestParam(required = false, value = "ids[]") Set<Long> ids) throws IllegalArgumentException {
		SpecificationsBuilder<Person> specificationsBuilder = new SpecificationsBuilder<>();
		specificationsBuilder.addSpecification(PersonSpecs.filterByIds(ids));
		specificationsBuilder.addSpecification(PersonSpecs.filterByFirstName(firstName));
		specificationsBuilder.addSpecification(PersonSpecs.filterByLastName(lastName));
		specificationsBuilder.addSpecification(PersonSpecs.filterByFavoriteColor(favouriteColor));
		specificationsBuilder.addSpecification(PersonSpecs.filterByAge(age));

		if (Objects.nonNull(page) && page >= 0 && Objects.nonNull(pageSize) && pageSize > 0) {
			Sort sort = new Sort(sortAscending ? Sort.Direction.ASC : Sort.Direction.DESC,
					Optional.ofNullable(sortProperties)
							.map(items -> Arrays.stream(items).filter(StringUtils::isNotBlank))
							.orElse(Stream.of("creationTimestamp")).toArray(String[]::new));
			PageRequest pageRequest = new PageRequest(page, pageSize, sort);
			Page<Person> offers = this.service.query(specificationsBuilder.build(), pageRequest);
			return offers.map(this.personToPersonDtoMapper::convert);
		}
		Iterable<Person> offers = this.service.query(specificationsBuilder.build());
		return StreamSupport.stream(offers.spliterator(), false).map(this.personToPersonDtoMapper::convert)
				.collect(Collectors.toSet());
	}

	/**
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	@DeleteMapping(value = "/{id:[0-9]+}")
	@ResponseBody
	public void deleteById(@PathVariable("id") Long id) throws RecordNotFoundException {
		this.service.delete(id);
	}

	/**
	 * @param personDto
	 * @return PersonDto
	 * @throws DuplicateRecordException
	 */
	@PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public PersonDto create(@Valid @RequestBody PersonDto personDto) throws DuplicateRecordException {
		PersonDetails personDetails = this.personDtoToPersonDetailsMapper.convert(personDto);
		Person createdPersonRecord = this.service.create(personDetails);
		return this.personToPersonDtoMapper.convert(createdPersonRecord);
	}

	/**
	 * @param personDto
	 * @return PersonDto
	 * @throws DuplicateRecordException
	 */
	@PutMapping(value = "/{id:[0-9]+}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public PersonDto modify(@PathVariable("id") Long id, @Valid @RequestBody PersonDto personDto)
			throws DuplicateRecordException {
		PersonDetails personDetails = this.personDtoToPersonDetailsMapper.convert(personDto);
		Person updatedPersonRecord = this.service.update(id, personDetails);
		return this.personToPersonDtoMapper.convert(updatedPersonRecord);
	}
}
