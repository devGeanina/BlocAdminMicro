package com.blocadminmicro.webservice.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.blocadminmicro.webservice.dto.BudgetDTO;
import com.blocadminmicro.webservice.dto.ExpenseDTO;
import com.blocadminmicro.webservice.dto.HouseholdDTO;
import com.blocadminmicro.webservice.dto.LoginDTO;
import com.blocadminmicro.webservice.dto.RequestDTO;
import com.blocadminmicro.webservice.dto.UserDTO;
import com.blocadminmicro.webservice.service.HomeService;
import com.blocadminmicro.webservice.utils.PDFExporter;
import com.blocadminmicro.webservice.utils.PDFExporter.EXPORT_TYPE;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/")
@SessionAttributes({ "budgetSummary", "expenseSummary", "houseSummary", "requestSummary" })
public class GUIController {

	private static final Logger LOGGER = LogManager.getLogger(GUIController.class);
	private final RestTemplate restTemplate;
	private final HomeService homeService;

	private final String serviceHostUsers; // can also be retrieved from application.properties
	private final String serviceHostBudgets = "localhost:8083";
	private final String serviceHostOperations = "localhost:8082";

	public GUIController(RestTemplate restTemplate, @Value("${service.host}") String serviceHost,
			HomeService homeService) {
		this.restTemplate = restTemplate;
		this.serviceHostUsers = serviceHost;
		this.homeService = homeService;
	}

	@RequestMapping("/home")
	public String home(Model model) {
		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses/debt", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("housesDebt", housesDTOs.getBody());
		getSummaryInfo(model);
		return "home";
	}

	@RequestMapping("/")
	public String index(final Model model) {
		model.addAttribute("loginDTO", new LoginDTO());
		return "login";
	}

	@GetMapping("/login")
	public String getLogin(final Model model) {
		model.addAttribute("loginDTO", new LoginDTO());
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(final Model model) {
		model.addAttribute("loginDTO", new LoginDTO());
		return "redirect:/login";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO, final BindingResult bindingResult,
			Model model) {
		String url = "http://" + serviceHostUsers + "/login";
		model.addAttribute("loginDTO", loginDTO);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		RequestEntity<LoginDTO> request;
		try {
			request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).body(loginDTO);
			restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<UserDTO>() {
			});
		} catch (URISyntaxException e) {
			LOGGER.error("Exception logging in the user: " + e.getMessage());
		}
		return "redirect:/home";
	}

	@GetMapping("/users")
	public String getUsers(Model model) {
		ResponseEntity<List<UserDTO>> usersDTOs = restTemplate.exchange("http://" + serviceHostUsers + "/users",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
				});
		model.addAttribute("users", usersDTOs.getBody());
		model.addAttribute("user", new UserDTO());
		return "viewUsers";
	}

	@GetMapping("/users/{id}")
	public void findUser(@PathVariable Long id, Model model) {
		ResponseEntity<UserDTO> userDTO = restTemplate.exchange("http://" + serviceHostUsers + "/users/" + id,
				HttpMethod.GET, null, new ParameterizedTypeReference<UserDTO>() {
				});
		model.addAttribute("user", userDTO);
	}

	@PostMapping("/users/save")
	String submitUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
		String url = "http://" + serviceHostUsers + "/users/save";
		userDTO.setUserType(userDTO.getUserTypeEnum().getType());
		model.addAttribute("user", userDTO);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		RequestEntity<UserDTO> request;
		try {
			request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).body(userDTO);
			ResponseEntity<List<UserDTO>> usersDTOs = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<UserDTO>>() {
					});

			model.addAttribute("users", usersDTOs.getBody());
		} catch (URISyntaxException e) {
			LOGGER.error("Exception fetching the saving the new user: " + e.getMessage());
		}
		return "redirect:/users";
	}

	@GetMapping("/users/delete/{id}")
	String deleteUser(@PathVariable Long id, Model model) {
		ResponseEntity<List<UserDTO>> usersDTOs = restTemplate.exchange(
				"http://" + serviceHostUsers + "/users/delete/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<UserDTO>>() {
				});
		model.addAttribute("users", usersDTOs.getBody());
		return "redirect:/users";
	}

	@RequestMapping("/users/export")
	public void exportUsersToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<List<UserDTO>> usersDTOs = restTemplate.exchange("http://" + serviceHostUsers + "/users",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
				});

		PDFExporter exporter = new PDFExporter();
		exporter.export(response, "users", EXPORT_TYPE.USERS, usersDTOs.getBody(), null, null, null, null);
	}

	@GetMapping("/budgets")
	public String getBudgets(Model model) {
		ResponseEntity<List<BudgetDTO>> budgetsDTOs = restTemplate.exchange("http://" + serviceHostBudgets + "/budgets",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<BudgetDTO>>() {
				});
		model.addAttribute("budgets", budgetsDTOs.getBody());
		model.addAttribute("budget", new BudgetDTO());
		// model.addAttribute("budgetSummary",
		// homeService.createBudgetSummary(budgetsDTOs.getBody()));
		return "viewBudgets";
	}

	@GetMapping("/budgets/{id}")
	public void findBudget(@PathVariable Long id, Model model) {
		ResponseEntity<BudgetDTO> budgetDTO = restTemplate.exchange("http://" + serviceHostBudgets + "/budgets/" + id,
				HttpMethod.GET, null, new ParameterizedTypeReference<BudgetDTO>() {
				});
		model.addAttribute("budget", budgetDTO);
	}

	@PostMapping("/budgets/save")
	String submitBudget(@Valid @ModelAttribute("budget") BudgetDTO budgetDTO, BindingResult result, Model model) {
		String url = "http://" + serviceHostBudgets + "/budgets/save";
		budgetDTO.setBudgetType(budgetDTO.getBudgetTypeEnum().getType());
		model.addAttribute("budget", budgetDTO);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		RequestEntity<BudgetDTO> request;
		try {
			request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).body(budgetDTO);
			ResponseEntity<List<BudgetDTO>> budgetDTOs = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<BudgetDTO>>() {
					});

			model.addAttribute("budgets", budgetDTOs.getBody());
		} catch (URISyntaxException e) {
			LOGGER.error("Exception fetching the saving the new budget: " + e.getMessage());
		}
		return "redirect:/budgets";
	}

	@GetMapping("/budgets/delete/{id}")
	String deleteBudget(@PathVariable Long id, Model model) {
		ResponseEntity<List<BudgetDTO>> budgetDTOs = restTemplate.exchange(
				"http://" + serviceHostBudgets + "/budgets/delete/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BudgetDTO>>() {
				});
		model.addAttribute("budgets", budgetDTOs.getBody());
		return "redirect:/budgets";
	}

	@RequestMapping("/budgets/export")
	public void exportBudgetsToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=budgets_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<List<BudgetDTO>> budgetDTOs = restTemplate.exchange("http://" + serviceHostBudgets + "/budgets",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<BudgetDTO>>() {
				});

		PDFExporter exporter = new PDFExporter();
		exporter.export(response, "budgets", EXPORT_TYPE.BUDGETS, null, budgetDTOs.getBody(), null, null, null);
	}

	@GetMapping("/expenses")
	public String getExpenses(Model model) {
		ResponseEntity<List<ExpenseDTO>> expensesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/expenses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ExpenseDTO>>() {
				});
		model.addAttribute("expenses", expensesDTOs.getBody());
		ResponseEntity<List<HouseholdDTO>> householdsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("houses", householdsDTOs.getBody());
		model.addAttribute("expense", new ExpenseDTO());
		// model.addAttribute("expenseSummary",
		// homeService.creatExpenseSummary(expensesDTOs.getBody()));

		return "viewExpenses";
	}

	@GetMapping("/expenses/{id}")
	public void findExpense(@PathVariable Long id, Model model) {
		ResponseEntity<ExpenseDTO> expenseDTO = restTemplate.exchange(
				"http://" + serviceHostOperations + "/expenses/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<ExpenseDTO>() {
				});
		model.addAttribute("expense", expenseDTO);
	}

	@PostMapping("/expenses/save")
	String submitExpense(@Valid @ModelAttribute("expense") ExpenseDTO expenseDTO, BindingResult result, Model model) {
		String url = "http://" + serviceHostOperations + "/expenses/save";
		expenseDTO.setExpenseType(expenseDTO.getExpenseTypeEnum().getType());
		model.addAttribute("expense", expenseDTO);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		RequestEntity<ExpenseDTO> request;
		try {
			request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).body(expenseDTO);
			ResponseEntity<List<ExpenseDTO>> expenseDTOs = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<ExpenseDTO>>() {
					});

			model.addAttribute("expenses", expenseDTOs.getBody());
		} catch (URISyntaxException e) {
			LOGGER.error("Exception fetching the saving the new expense: " + e.getMessage());
		}
		return "redirect:/expenses";
	}

	@GetMapping("/expenses/delete/{id}")
	String deleteExpense(@PathVariable Long id, Model model) {
		ResponseEntity<List<ExpenseDTO>> expenseDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/expenses/delete/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ExpenseDTO>>() {
				});
		model.addAttribute("expenses", expenseDTOs.getBody());
		return "redirect:/expenses";
	}

	@RequestMapping("/expenses/export")
	public void exportExpensesToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=expenses_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<List<ExpenseDTO>> expensesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/expenses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ExpenseDTO>>() {
				});

		PDFExporter exporter = new PDFExporter();
		exporter.export(response, "expenses", EXPORT_TYPE.EXPENSES, null, null, expensesDTOs.getBody(), null, null);
	}

	@GetMapping("/requests")
	public String getRequests(Model model) {
		ResponseEntity<List<RequestDTO>> requestsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/requests", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RequestDTO>>() {
				});
		ResponseEntity<List<HouseholdDTO>> householdsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("houses", householdsDTOs.getBody());
		model.addAttribute("requests", requestsDTOs.getBody());
		model.addAttribute("request", new RequestDTO());
		// model.addAttribute("requestSummary",
		// homeService.createRequestSummary(requestsDTOs.getBody()));

		return "viewRequests";
	}

	@GetMapping("/requests/{id}")
	public void findRequest(@PathVariable Long id, Model model) {
		ResponseEntity<RequestDTO> requestDTO = restTemplate.exchange(
				"http://" + serviceHostOperations + "/requests/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<RequestDTO>() {
				});
		model.addAttribute("request", requestDTO);
	}

	@PostMapping("/requests/save")
	String submitRequest(@Valid @ModelAttribute("request") RequestDTO requestDTO, BindingResult result, Model model) {
		String url = "http://" + serviceHostOperations + "/requests/save";
		requestDTO.setRequestType(requestDTO.getRequestTypeEnum().getType());
		model.addAttribute("request", requestDTO);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		RequestEntity<RequestDTO> request;
		try {
			request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).body(requestDTO);
			ResponseEntity<List<RequestDTO>> requestsDTOs = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<RequestDTO>>() {
					});

			model.addAttribute("requests", requestsDTOs.getBody());
		} catch (URISyntaxException e) {
			LOGGER.error("Exception fetching the saving the new request: " + e.getMessage());
		}
		return "redirect:/requests";
	}

	@GetMapping("/requests/delete/{id}")
	String deleteRequest(@PathVariable Long id, Model model) {
		ResponseEntity<List<RequestDTO>> requestsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/requests/delete/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RequestDTO>>() {
				});
		model.addAttribute("requests", requestsDTOs.getBody());
		return "redirect:/requests";
	}

	@RequestMapping("/requests/export")
	public void exportRequestsToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=requests_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<List<RequestDTO>> requestsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/requests", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RequestDTO>>() {
				});

		PDFExporter exporter = new PDFExporter();
		exporter.export(response, "requests", EXPORT_TYPE.REQUESTS, null, null, null, null, requestsDTOs.getBody());
	}

	@GetMapping("/houses")
	public String getHouseholds(Model model) {
		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("houses", housesDTOs.getBody());
		model.addAttribute("household", new HouseholdDTO());
		// model.addAttribute("houseSummary",
		// homeService.createHouseholdSummary(housesDTOs.getBody()));

		return "viewHouses";
	}

	@GetMapping("/houses/debt")
	public String getHouseholdsWithDebt(Model model) {
		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("housesDebt", housesDTOs.getBody());
		return "home";
	}

	@GetMapping("/houses/{id}")
	public void findHousehold(@PathVariable Long id, Model model) {
		ResponseEntity<HouseholdDTO> houseDTO = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<HouseholdDTO>() {
				});
		model.addAttribute("household", houseDTO);
	}

	@PostMapping("/houses/save")
	String submitHouse(@Valid @ModelAttribute("household") HouseholdDTO houseDTO, BindingResult result, Model model) {
		String url = "http://" + serviceHostOperations + "/houses/save";
		model.addAttribute("household", houseDTO);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		RequestEntity<HouseholdDTO> request;
		try {
			request = RequestEntity.post(new URI(url)).accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON).body(houseDTO);
			ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<HouseholdDTO>>() {
					});

			model.addAttribute("houses", housesDTOs.getBody());
		} catch (URISyntaxException e) {
			LOGGER.error("Exception fetching the saving the new household: " + e.getMessage());
		}
		return "redirect:/houses";
	}

	@GetMapping("/houses/delete/{id}")
	String deleteHousehold(@PathVariable Long id, Model model) {
		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses/delete/" + id, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("houses", housesDTOs.getBody());
		return "redirect:/houses";
	}

	@RequestMapping("/houses/export")
	public void exportHousesToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=houses_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});

		PDFExporter exporter = new PDFExporter();
		exporter.export(response, "households", EXPORT_TYPE.HOUSEHOLDS, null, null, null, housesDTOs.getBody(), null);
	}

	/**
	 * Create summary information for the dialogs and persist across requests, in
	 * session.
	 * 
	 * @param model
	 */
	private void getSummaryInfo(Model model) {
		// create for budget summary
		ResponseEntity<List<BudgetDTO>> budgetsDTOs = restTemplate.exchange("http://" + serviceHostBudgets + "/budgets",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<BudgetDTO>>() {
				});
		model.addAttribute("budgetSummary", homeService.createBudgetSummary(budgetsDTOs.getBody()));

		// create for expense summary
		ResponseEntity<List<ExpenseDTO>> expensesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/expenses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ExpenseDTO>>() {
				});
		model.addAttribute("expenseSummary", homeService.creatExpenseSummary(expensesDTOs.getBody()));

		// create for request summary
		ResponseEntity<List<RequestDTO>> requestsDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/requests", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<RequestDTO>>() {
				});
		model.addAttribute("requestSummary", homeService.createRequestSummary(requestsDTOs.getBody()));

		// create for house summary
		ResponseEntity<List<HouseholdDTO>> housesDTOs = restTemplate.exchange(
				"http://" + serviceHostOperations + "/houses", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<HouseholdDTO>>() {
				});
		model.addAttribute("houseSummary", homeService.createHouseholdSummary(housesDTOs.getBody()));
	}
}
