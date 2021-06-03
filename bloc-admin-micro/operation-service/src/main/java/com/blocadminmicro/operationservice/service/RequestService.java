package com.blocadminmicro.operationservice.service;

import java.util.List;

import com.blocadminmicro.operationservice.dto.RequestDTO;


public interface RequestService {
	
	public abstract List<RequestDTO> getRequests();

	public abstract void saveRequest(RequestDTO requestDTO);

	public abstract void deleteRequest(Long id);

	public abstract RequestDTO getRequest(Long id);
}
