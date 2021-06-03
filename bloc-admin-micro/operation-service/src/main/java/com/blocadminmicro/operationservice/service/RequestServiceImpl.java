package com.blocadminmicro.operationservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blocadminmicro.operationservice.dto.RequestDTO;
import com.blocadminmicro.operationservice.entity.Request;
import com.blocadminmicro.operationservice.repository.HouseholdRepository;
import com.blocadminmicro.operationservice.repository.RequestRepository;

@Service
public class RequestServiceImpl implements RequestService {

	private final RequestRepository requestDAO;
	private final HouseholdRepository householdDAO;

	@Autowired
	public RequestServiceImpl(RequestRepository requestDAO, HouseholdRepository householdDAO) {
		this.requestDAO = requestDAO;
		this.householdDAO = householdDAO;
	}

	@Override
	public List<RequestDTO> getRequests() {
		List<RequestDTO> dtos = new ArrayList<>();

		List<Request> entities = requestDAO.findAll();
		for (Request entity : entities) {
			RequestDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private RequestDTO getDTO(Request entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		RequestDTO entityDTO = new RequestDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setDueDate(entity.getDueDate());
		entityDTO.setResolved(entity.isResolved());
		entityDTO.setName(entity.getName());
		entityDTO.setRequestType(entity.getRequestType());
		if (entity.getHousehold() != null) {
			entityDTO.setHouseholdAddress("B. ".concat(String.valueOf(entity.getHousehold().getBuildingNr()))
					.concat(", Ap. ").concat(String.valueOf(entity.getHousehold().getAppartmentNr())));
			entityDTO.setHouseholdId(entity.getHousehold().getId());
		}
		entityDTO.setId(entity.getId());
		return entityDTO;
	}

	@Override
	public void saveRequest(RequestDTO requestDTO) {
		if (requestDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Request request = new Request();
		request = getEntity(requestDTO);
		requestDAO.save(request);
	}

	private Request getEntity(RequestDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}

		Request entity = new Request();
		entity.setDueDate(entityDTO.getDueDate());
		entity.setName(entityDTO.getName());
		entity.setResolved(entityDTO.isResolved());
		entity.setRequestType(entityDTO.getRequestType());
		entity.setHousehold(householdDAO.findById(entityDTO.getHouseholdId()).get());

		if (entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());

		if (entityDTO.getId() != null)
			entity.setId(entityDTO.getId());
		return entity;
	}

	@Override
	public void deleteRequest(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		requestDAO.deleteById(id);
	}

	@Override
	public RequestDTO getRequest(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Optional<Request> entity = requestDAO.findById(id);
		RequestDTO entityDTO = getDTO(entity.get());
		return entityDTO;
	}
}
