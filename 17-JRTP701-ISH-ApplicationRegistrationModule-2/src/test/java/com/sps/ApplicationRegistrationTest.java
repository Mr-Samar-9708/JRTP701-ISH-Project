package com.sps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sps.bindigData.AppRegistrationInput;
import com.sps.controller.ApplicationRegistrationRestController;
import com.sps.service.CitizenApplicationRegistrationImpl;

@WebMvcTest(ApplicationRegistrationRestController.class)
public class ApplicationRegistrationTest {

	@MockBean
	private CitizenApplicationRegistrationImpl service;
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void saveCitizenIsTrue() throws Exception {
		AppRegistrationInput appRegist = new AppRegistrationInput("Samar Pratap Singh",19, "samarpratap9708@gmail.com",
				8581003656l, 25656502, LocalDate.of(2024, 9, 17));
		Mockito.when(service.citizenRegistration(appRegist)).thenReturn(1);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String jsonContent = objectMapper.writeValueAsString(objectMapper);

		MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.post("/AR-module-api/saveCitizen")
				.contentType("application/json").content(jsonContent);

		MvcResult result = mockMvc.perform(reqBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String actualResult = response.getContentAsString();
		
		int expectedResult = 1;
		assertEquals(actualResult, expectedResult);
	}

}
