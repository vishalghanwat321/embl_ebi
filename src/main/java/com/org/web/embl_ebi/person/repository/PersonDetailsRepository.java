package com.org.web.embl_ebi.person.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.org.web.embl_ebi.person.domain.Person;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote PersonDetailsRepository
 * @since 2020
 */
@Repository
public interface PersonDetailsRepository
		extends PagingAndSortingRepository<Person, Long>, JpaSpecificationExecutor, Serializable {
	boolean existsById(Long id);
}
