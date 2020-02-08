package com.org.web.embl_ebi.person.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.web.embl_ebi.person.domain.Person;
import com.org.web.embl_ebi.person.repository.PersonDetailsRepository;
import com.org.web.embl_ebi.person.service.request.PersonDetails;
import com.org.web.embl_ebi.person.util.exception.DuplicateRecordException;
import com.org.web.embl_ebi.person.util.exception.RecordNotFoundException;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote PersonDetailsService
 * @since 2020
 */
@Service
public class PersonDetailsService {

	@Autowired
	private PersonDetailsRepository repository;

	/**
	 * 
	 * @param specification
	 * @return Iterable<Person>
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Iterable<Person> query(Specification<Person> specification) {
		return this.repository.findAll(specification);
	}

	/**
	 * 
	 * @param specification
	 * @param pageable
	 * @return Iterable<Person>
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Page<Person> query(Specification<Person> specification, Pageable pageable) {
		return this.repository.findAll(specification, pageable);
	}

	/**
	 * 
	 * @param id
	 * @return Person
	 * @throws IllegalArgumentException
	 * @throws RecordNotFoundException
	 */
	@Transactional(readOnly = true)
	public Person query(Long id) throws IllegalArgumentException, RecordNotFoundException {
		if (Objects.isNull(id))
			throw new IllegalArgumentException("Failed to query Person (reason: invalid id).");
		return this.repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not found..."));
	}

	/**
	 * 
	 * @param id
	 * @throws RecordNotFoundException
	 */
	@Transactional
	public void delete(Long id) throws RecordNotFoundException {
		if (!this.repository.existsById(id))
			throw new RecordNotFoundException("Record not found...");
		else
			this.repository.delete(query(id));
	}

	/**
	 * @param personDetails
	 * @return Person
	 * @throws DuplicateRecordException
	 * @throws IllegalStateException
	 */
	@Transactional
	public Person create(PersonDetails personDetails) throws DuplicateRecordException, IllegalStateException {
		// check is the offer already exists
		if (this.repository.existsById(personDetails.getId()))
			throw new DuplicateRecordException("Record already exists...");

		Person person = new Person();

		person.setAge(Objects.nonNull(personDetails.getAge()) ? personDetails.getAge() : 0);
		if (Objects.nonNull(personDetails.getFavouriteColor()))
			person.setFavouriteColor(personDetails.getFavouriteColor());
		if (Objects.nonNull(personDetails.getFirstName()))
			person.setFirstName(personDetails.getFirstName());
		if (Objects.nonNull(personDetails.getLastName()))
			person.setLastName(personDetails.getLastName());
		if (Objects.nonNull(personDetails.getHobbies()) && personDetails.getHobbies().size() > 0)
			person.setHobbies(personDetails.getHobbies());

		Person savedData = this.repository.save(person);
		if (Objects.isNull(savedData))
			throw new IllegalStateException("Failed to save details...");
		return savedData;
	}

	/**
	 * @param personDetails
	 * @return Person
	 * @throws RecordNotFoundException
	 * @throws IllegalStateException
	 */
	@Transactional
	public Person update(Long id, PersonDetails personDetails) throws RecordNotFoundException, IllegalStateException {
		// check is the offer already exists
		Person personEntity = this.query(id);

		personEntity.setAge(personDetails.getAge());
		personEntity.setFavouriteColor(personDetails.getFavouriteColor());
		personEntity.setFirstName(personDetails.getFirstName());
		personEntity.setLastName(personDetails.getLastName());
		personEntity.setHobbies(personDetails.getHobbies());

		Person updatedData = this.repository.save(personEntity);
		if (Objects.isNull(updatedData))
			throw new IllegalStateException("Failed to update/modify details...");
		return updatedData;
	}
}
