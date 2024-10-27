package com.sps.com;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sps.data.binding.EligibilityDeterminationOutput;
import com.sps.entity.CitizenAppRegistrationEntity;
import com.sps.entity.DcCaseEntity;
import com.sps.entity.DcPlanEntity;
import com.sps.repository.ICitizenAppRegistrationRepository;
import com.sps.repository.IDcCaseRepository;
import com.sps.repository.IDcPlanRepository;
import com.sps.service.IEligibilityDeterminationMngService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EligibilityDeterminationJunitTest {

	@MockBean
	private ICitizenAppRegistrationRepository citizenRepo;
	@MockBean
	private IDcCaseRepository caseRepo;
	@MockBean
	private IDcPlanRepository planRepo;
	@MockBean	
	private EligibilityDeterminationOutput output;
	@MockBean
	private IEligibilityDeterminationMngService service;

	@Test
	public void checkEligibilityDeter() {
		DcCaseEntity caseEntity = new DcCaseEntity(1, 1001, 1);
		CitizenAppRegistrationEntity citizenEntity = new CitizenAppRegistrationEntity(100, "Samar Pratap Singh",
				"samarpratap@gmail.com", 8581003656l, 858100302, "California", LocalDate.of(2005, 4, 9),
				"Samar Pratap Singh", "Samar Pratap Singh", null, null);
		DcPlanEntity planEntity = new DcPlanEntity(1, 1, "QHP", LocalDate.now(), LocalDate.now().plusYears(2),
				"This good plan", "true", "Samar Pratap Singh", "Samar Pratap Singh", null, null);
		EligibilityDeterminationOutput output = new EligibilityDeterminationOutput();
		output.setCaseNo(caseEntity.getCaseNo());
		output.setHolderName(citizenEntity.getFullName());
		output.setPlanStatus(planEntity.getActiveSw());
		output.setBenifitAmt(0.0);
		output.setPlanStartDate(planEntity.getStartDate());
		output.setPlanEndDate(planEntity.getEndData());

		// Mocking repository calls
		Mockito.when(caseRepo.findById(1)).thenReturn(Optional.of(caseEntity));
		Mockito.when(citizenRepo.findById(100)).thenReturn(Optional.of(citizenEntity));
		Mockito.when(planRepo.findById(1)).thenReturn(Optional.of(planEntity));
		Mockito.when(service.determineEligibility(1)).thenReturn(output);

		// Expected output
		EligibilityDeterminationOutput expectedOutput = new EligibilityDeterminationOutput();
		expectedOutput.setCaseNo(caseEntity.getCaseNo());
		expectedOutput.setHolderName(citizenEntity.getFullName());
		expectedOutput.setPlanStatus(planEntity.getActiveSw());
		expectedOutput.setBenifitAmt(0.0);
		expectedOutput.setPlanStartDate(planEntity.getStartDate());
		expectedOutput.setPlanEndDate(planEntity.getEndData());

		// Call the method under test
		EligibilityDeterminationOutput actualOutput = service.determineEligibility(1);

		// Assertions to compare the actual vs expected output
		assertEquals(expectedOutput.getCaseNo(), actualOutput.getCaseNo());
		assertEquals(expectedOutput.getHolderName(), actualOutput.getHolderName());
		assertEquals(expectedOutput.getPlanStatus(), actualOutput.getPlanStatus());
		assertEquals(expectedOutput.getBenifitAmt(), actualOutput.getBenifitAmt());
		assertEquals(expectedOutput.getPlanStartDate(), actualOutput.getPlanStartDate());
		assertEquals(expectedOutput.getPlanEndDate(), actualOutput.getPlanEndDate());
	}

}
