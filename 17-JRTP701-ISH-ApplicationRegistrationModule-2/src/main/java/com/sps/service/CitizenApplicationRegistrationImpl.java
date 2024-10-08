package com.sps.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.sps.bindigData.AppRegistrationInput;
import com.sps.entity.CitizenAppRegistrationEntity;
import com.sps.repository.CitizenAppRegistrationRepo;

@Service
public class CitizenApplicationRegistrationImpl implements ICitizenApplicationRegistration {

	@Autowired
	private CitizenAppRegistrationRepo repository;
	@Autowired
	private WebClient client;
	/*@Autowired //Injecting from @Bean Method which available ApplicationConfiguration Class
	private RestTemplate template;*/
	@Value("${api.process_ssn.url}")
	private String ssnWebUrl;
	@Value("${required.stateName}")
	private String requiredStateName;

	@Override
	public Integer citizenRegistration(AppRegistrationInput ssn) {
		/*ResponseEntity<String> res = template.exchange(ssnWebUrl, HttpMethod.GET, null, String.class, ssn.getSsn());
		String stateNam = res.getBody();*/

		// Compare than RestTemplate, WebClient is good
		String stateName = client.get()
				                                        .uri(ssnWebUrl, ssn.getSsn())
				                                        .retrieve()
				                                        .bodyToMono(String.class)
				                                        .block();

		// Empty entity class object for data holding
		CitizenAppRegistrationEntity entityObj = new CitizenAppRegistrationEntity();
		BeanUtils.copyProperties(ssn, entityObj);

		// Checking state name , is state name based on required name.
		if (stateName != null && stateName.equalsIgnoreCase(requiredStateName)) {
			entityObj.setStateName(stateName);
			entityObj.setCreatedBy(ssn.getFullName());
			// Returning app I'd
			return  repository.save(entityObj).getAppId();
		}
		return 0;
	}

}
