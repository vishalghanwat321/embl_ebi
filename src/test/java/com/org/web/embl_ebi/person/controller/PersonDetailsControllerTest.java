package com.org.web.embl_ebi.person.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.web.embl_ebi.EMBL_EBI_Application;
import com.org.web.embl_ebi.config.security.WebServiceSecurityFilterTest;
import com.org.web.embl_ebi.person.dto.PersonDto;
import com.org.web.embl_ebi.person.util.constant.FavoriteColor;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote This is integration test class for Person details.
 * @since 2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { EMBL_EBI_Application.class,
		WebServiceSecurityFilterTest.class })
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonDetailsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@Before
	public void setUp() {
		this.objectMapper = new ObjectMapper();
	}

	// data-person.sql file will load data to h2
	// Below test case will be verified w.r.t. in memory Data calling h2 using JPA
	// repositories
	@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data-person.sql") })
	@Test
	@WithUserDetails("embl_ebi")
	public void testPersonController_1_ById() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/person/{id}", 1993279932301558L))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1993279932301558L)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.first_name", Matchers.is("Anna")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.last_name", Matchers.is("Hathway")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.age", Matchers.is(25)));
	}

	// Verify the list of all persons with expected count 2
	@Test
	@WithUserDetails("embl_ebi")
	public void testPersonController_2_ByListAll() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/person")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
	}

	// // Verify the list of all offers with pagination with expected count 2
	@Test
	@WithUserDetails("embl_ebi")
	public void testPersonController_3_ByListAllWithPagination() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/person?page={page}&page_size={page_size}", 0, 5))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", Matchers.is(2)));
	}

	// Create new Person
	@Test
	@WithUserDetails("embl_ebi")
	public void testPersonController_4_CreateOffer() throws Exception {

		// Create new DTO
		PersonDto personDto = new PersonDto();
		personDto.setId(1993279932888668L);
		personDto.setFirstName("James");
		personDto.setLastName("Bond");
		personDto.setAge(30);
		personDto.setCreationTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
		personDto.setLastModificationTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
		personDto.setFavouriteColor(FavoriteColor.GREEN);
		Set<String> set = new HashSet<>();
		set.add("Swimming");
		set.add("Cricket");
		personDto.setHobbies(set);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(objectMapper.writeValueAsString(personDto)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}

	// this is to verify where new record which was added in previous testCase is
	// saved successfully or not
	// New total record count should be 3.
	@Test
	@WithUserDetails("embl_ebi")
	public void testPersonController_5_PostCreatingNewPerson() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/person")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
	}

}