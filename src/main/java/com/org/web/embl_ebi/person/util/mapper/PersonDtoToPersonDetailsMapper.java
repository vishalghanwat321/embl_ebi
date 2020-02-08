package com.org.web.embl_ebi.person.util.mapper;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.web.embl_ebi.person.dto.PersonDto;
import com.org.web.embl_ebi.person.service.request.PersonDetails;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote PersonDtoToPersonDetailsMapper will map bean PersonDto to
 *           PersonDetails using model mapper
 * @since 2020
 */
@Component
public class PersonDtoToPersonDetailsMapper {

	private static final Logger logger = LoggerFactory.getLogger(PersonDtoToPersonDetailsMapper.class);

	@Autowired
	private ModelMapper modelMapper;

	@PostConstruct
	protected void onPostConstruct() {
		this.initializeTypeMaps();
	}

	private TypeMap<PersonDto, PersonDetails> typeMapDTO;

	private void initializeTypeMaps() {
		logger.info("initializing mapper to map PersonDto to PersonDetails...");
		this.typeMapDTO = this.modelMapper.createTypeMap(PersonDto.class, PersonDetails.class)
				.addMapping(PersonDto::getAge, PersonDetails::setAge)
				.addMapping(PersonDto::getFirstName, PersonDetails::setFirstName)
				.addMapping(PersonDto::getLastName, PersonDetails::setLastName)
				.addMapping(PersonDto::getFavouriteColor, PersonDetails::setFavouriteColor)
				.addMapping(PersonDto::getId, PersonDetails::setId)
				.addMapping(PersonDto::getHobbies, PersonDetails::setHobbies);
	}

	public PersonDetails convert(PersonDto dto) {
		if (Objects.isNull(dto))
			return null;
		return this.typeMapDTO.map(dto);
	}
}