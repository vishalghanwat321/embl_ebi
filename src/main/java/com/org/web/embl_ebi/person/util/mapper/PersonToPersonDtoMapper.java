package com.org.web.embl_ebi.person.util.mapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.web.embl_ebi.person.domain.Person;
import com.org.web.embl_ebi.person.dto.PersonDto;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote PersonToPersonDtoMapper will map bean Person to PersonDto using
 *           model mapper
 * @since 2020
 */
@Component
public class PersonToPersonDtoMapper {

	private static final Logger logger = LoggerFactory.getLogger(PersonToPersonDtoMapper.class);

	@Autowired
	private ModelMapper modelMapper;

	@PostConstruct
	protected void onPostConstruct() {
		this.initializeTypeMaps();
	}

	private TypeMap<Person, PersonDto> typeMapDTO;

	private void initializeTypeMaps() {
		logger.info("initializing mapper to map Person to PersonDto...");
		Converter<LocalDateTime, Long> converterTimestamp = ctx -> Objects.isNull(ctx.getSource()) ? null
				: ctx.getSource().toInstant(ZoneOffset.UTC).toEpochMilli();
		this.typeMapDTO = this.modelMapper.createTypeMap(Person.class, PersonDto.class)
				.addMapping(Person::getAge, PersonDto::setAge).addMapping(Person::getFirstName, PersonDto::setFirstName)
				.addMapping(Person::getLastName, PersonDto::setLastName)
				.addMapping(Person::getFavouriteColor, PersonDto::setFavouriteColor)
				.addMapping(Person::getId, PersonDto::setId)
				.addMapping(Person::getHobbies, PersonDto::setHobbies)
				.addMappings(mapper -> {
					mapper.using(converterTimestamp).map(Person::getCreationTimestamp, PersonDto::setCreationTimestamp);
					mapper.using(converterTimestamp).map(Person::getLastModificationTimestamp,
							PersonDto::setLastModificationTimestamp);
				});
	}

	public PersonDto convert(Person dto) {
		if (Objects.isNull(dto))
			return null;
		return this.typeMapDTO.map(dto);
	}
}