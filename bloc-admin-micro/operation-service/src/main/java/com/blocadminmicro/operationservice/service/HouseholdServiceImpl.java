package com.blocadminmicro.operationservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blocadminmicro.operationservice.dto.HouseholdDTO;
import com.blocadminmicro.operationservice.entity.Household;
import com.blocadminmicro.operationservice.repository.HouseholdRepository;

@Service
public class HouseholdServiceImpl implements HouseholdService {

	private final HouseholdRepository householdDAO;

	@Autowired
	public HouseholdServiceImpl(HouseholdRepository householdDAO) {
		this.householdDAO = householdDAO;
	}

	@Transactional
	@Override
	public List<HouseholdDTO> getHouseholds() {
		List<HouseholdDTO> dtos = new ArrayList<>();

		List<Household> entities = householdDAO.findAll();
		for (Household entity : entities) {
			HouseholdDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	@Transactional
	@Override
	public List<HouseholdDTO> getHouseholdsWithDebt() {
		List<HouseholdDTO> dtos = new ArrayList<>();
		List<Household> entities = householdDAO.getHouseholdsWithDebt();
		for (Household entity : entities) {
			HouseholdDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private HouseholdDTO getDTO(Household entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		HouseholdDTO entityDTO = new HouseholdDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setAppartmentNr(entity.getAppartmentNr());
		entityDTO.setBuildingNr(entity.getBuildingNr());
		entityDTO.setNrCurrentOccupants(entity.getNrCurrentOccupants());
		entityDTO.setRoomsNr(entity.getRoomsNr());
		entityDTO.setTotalCapacity(entity.getTotalCapacity());
		entityDTO.setOwnerName(entity.getOwnerName());
		
		if(entity.getExpenses() != null) {
			double totalDebt = entity.getExpenses().stream().filter(o -> !o.isPayedInFull() && o.getLeftoverSum() > 0.0)
					.mapToDouble(o -> o.getLeftoverSum()).sum();
			entityDTO.setTotalDebt(totalDebt);
		}else
			entityDTO.setTotalDebt(0.0);

		entityDTO.setId(entity.getId());
		return entityDTO;
	}

	@Override
	public void saveHousehold(HouseholdDTO householdDTO) {
		if (householdDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Household household = new Household();
		household = getEntity(householdDTO);
		householdDAO.save(household);
	}

	private Household getEntity(HouseholdDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		Household entity = new Household();
		entity.setAppartmentNr(entityDTO.getAppartmentNr());
		entity.setBuildingNr(entityDTO.getBuildingNr());
		entity.setNrCurrentOccupants(entityDTO.getNrCurrentOccupants());
		entity.setOwnerName(entityDTO.getOwnerName());
		entity.setRoomsNr(entityDTO.getRoomsNr());
		entity.setTotalCapacity(entityDTO.getTotalCapacity());

		if (entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());

		if (entityDTO.getId() != null)
			entity.setId(entityDTO.getId());
		return entity;
	}

	@Override
	public void deleteHousehold(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		householdDAO.deleteById(id);
	}

	@Override
	public HouseholdDTO getHousehold(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Optional<Household> entity = householdDAO.findById(id);
		HouseholdDTO entityDTO = getDTO(entity.get());
		return entityDTO;
	}
}
