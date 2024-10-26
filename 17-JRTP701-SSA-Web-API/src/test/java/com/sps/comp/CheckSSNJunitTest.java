package com.sps.comp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.sps.ms.CheckSSN;

@WebMvcTest(CheckSSN.class)
public class CheckSSNJunitTest {

	@MockBean
	private CheckSSN checkSSN;
	@Autowired
	private MockMvc mockMvc;

	@Test
	void isCaliforniaSSN() throws Exception {
		/*mockMvc.perform(get("/ssa-web-api/getState/123456789"))
		.andExpect(status().isOk())
		.andExpect((ResultMatcher) content().string("California"));*/
		
		Mockito.when(checkSSN.ssnIsCalifornia(123456602)).thenReturn(ResponseEntity.ok("California"));
		
	    MvcResult mvcResult = mockMvc.perform(get("/ssa-web-api/getState/123456602"))
		.andExpect(status().isOk())
		.andReturn();
		
	   String stateName = mvcResult.getResponse().getContentAsString();
	   String expectedState="California";
	   assertEquals(expectedState, stateName);
	}
	
	
}
