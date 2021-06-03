package com.blocadminmicro.operationservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocadminmicro.operationservice.dto.HouseholdDTO;
import com.blocadminmicro.operationservice.service.HouseholdService;


@RestController
public class HouseholdsController {

	private final HouseholdService householdService;

	public HouseholdsController(HouseholdService householdService) {
		this.householdService = householdService;
	}

	@GetMapping("/houses")
	public List<HouseholdDTO> getHouseholds() {
		return householdService.getHouseholds();
	}
	
	@GetMapping("/houses/debt")
	public List<HouseholdDTO> getHouseholdsWithDebt() {
		return householdService.getHouseholdsWithDebt();
	}

	@GetMapping("/houses/{id}")
	HouseholdDTO findHousehold(@PathVariable Long id) {
		return householdService.getHousehold(id);
	}

	@PostMapping("/houses/save")
	public List<HouseholdDTO> saveOrUpdateHousehold(@RequestBody HouseholdDTO householdDTO) {
		householdService.saveHousehold(householdDTO);
		return householdService.getHouseholds();
	}

	@GetMapping("/houses/delete/{id}")
	public List<HouseholdDTO> deleteHousehold(@PathVariable Long id) {
		householdService.deleteHousehold(id);
		return householdService.getHouseholds();
	}
}