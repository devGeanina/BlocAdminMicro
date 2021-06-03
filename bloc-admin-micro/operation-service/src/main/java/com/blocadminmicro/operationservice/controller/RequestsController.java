package com.blocadminmicro.operationservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blocadminmicro.operationservice.dto.RequestDTO;
import com.blocadminmicro.operationservice.service.RequestService;


@RestController
public class RequestsController {

	private final RequestService requestService;

	public RequestsController(RequestService requestService) {
		this.requestService = requestService;
	}

	@GetMapping("/requests") 
	public List<RequestDTO> getRequests() {
		return requestService.getRequests();
	}

	@GetMapping("/requests/{id}")
	RequestDTO findRequest(@PathVariable Long id) {
		return requestService.getRequest(id);
	}

	@PostMapping("/requests/save")
	public List<RequestDTO> saveOrUpdateBudget(@RequestBody RequestDTO requestDTO) {
		requestService.saveRequest(requestDTO);
		return requestService.getRequests();
	}

	@GetMapping("/requests/delete/{id}")
	public List<RequestDTO> deleteExpense(@PathVariable Long id) {
		requestService.deleteRequest(id);
		return requestService.getRequests();
	}
}