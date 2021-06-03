package com.blocadminmicro.operationservice.service;

import java.util.List;

import com.blocadminmicro.operationservice.dto.HouseholdDTO;



public interface HouseholdService {
	
	public abstract List<HouseholdDTO> getHouseholds();

	public abstract void saveHousehold(HouseholdDTO householdDTO);

	public abstract void deleteHousehold(Long id);

	public abstract HouseholdDTO getHousehold(Long id);

	public abstract List<HouseholdDTO> getHouseholdsWithDebt();
}
