package com.aldidb.backenddb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aldidb.backenddb.exception.CustomGenericException;
import com.aldidb.backenddb.message.BaseRequest;
import com.aldidb.backenddb.message.BaseResponse;
import com.aldidb.backenddb.message.RequestCreateInvoice;
import com.aldidb.backenddb.message.RequestCreateOrganization;
import com.aldidb.backenddb.message.RequestRegisterUser;
import com.aldidb.backenddb.service.CurrencyService;
import com.aldidb.backenddb.service.ExternalService;
import com.aldidb.backenddb.service.InvoiceService;
import com.aldidb.backenddb.service.OrganizationService;
import com.aldidb.backenddb.service.ProductService;
import com.aldidb.backenddb.service.UserService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "API")
public class Controller {

	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private ExternalService externalService;

	// TODO Create controller for user
	// <==============================================================================================================================================>
	@PostMapping(path = "/login-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> loginUser(@RequestParam String username, @RequestParam String password) {
		return userService.loginUser(username, password);
	}

	@PostMapping(path = "/register-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registerUser(@RequestBody RequestRegisterUser request) {
		return userService.registerUser(request);
	}

	@PutMapping(path = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> updateUser(@RequestBody RequestRegisterUser request) {
		return userService.updateUser(request);
	}

	@GetMapping(value = "/get-users")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> getAllUser() {
		return userService.getAllUser();
	}

	@GetMapping(value = "/get-user/{name}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> getUser(@PathVariable("name") String name) {
		return userService.getUser(name);
	}

	@DeleteMapping(value = "/delete-user/{name}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> deleteUser(@PathVariable("name") String name) {
		return userService.deleteUser(name);
	}

	// TODO Create controller for organization
	// <==============================================================================================================================================>
	@PostMapping(path = "/create-organization", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> createOrganization(@RequestBody @Valid RequestCreateOrganization request) {
		return organizationService.createOrganization(request);
	}

	@GetMapping(value = "/get-organization")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> getOrganization(@RequestParam String uuiduser) {
		BaseResponse response = new BaseResponse();
		response.setMessage(BaseResponse.NOT_FOUND);
		if (uuiduser.isEmpty())
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

		return organizationService.getOrganization(uuiduser);
	}

	@GetMapping(value = "/get-organizations")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> getOrganization() {
		return organizationService.getOrganization("");
	}

	@PutMapping(path = "/update-organization/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> updateOrganization(@PathVariable("uuid") String uuid,
			@RequestBody @Valid RequestCreateOrganization request) {
		return organizationService.updateOrganization(uuid, request);
	}

	@DeleteMapping(value = "/delete-organization/{uuid}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	public ResponseEntity<Object> deleteOrganization(@PathVariable("uuid") String uuid) {

		return organizationService.deleteOrganization(uuid);
	}

	// TODO Create controller for product
	// <==============================================================================================================================================>
	@PostMapping(path = "/create-product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> createProduct(@RequestBody BaseRequest request) {
		return productService.createProduct(request);
	}

	@GetMapping(value = "/get-products")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR') or hasAuthority('ROLE_CUSTOMERSERVICE')")
	public ResponseEntity<Object> getProducts() {
		return productService.getProducts();
	}

	@DeleteMapping(value = "/delete-product/{uuid}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> deleteProduct(@PathVariable("uuid") String uuid) {
		return productService.deleteProduct(uuid);
	}

	// TODO Create controller for currency
	// <==============================================================================================================================================>
	@PostMapping(path = "/create-currency", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> createCurrency(@RequestBody BaseRequest request) {
		return currencyService.createCurrency(request);
	}

	@GetMapping(value = "/get-currencys")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR') or hasAuthority('ROLE_CUSTOMERSERVICE')")
	public ResponseEntity<Object> getCurrencys() {
		return currencyService.getCurrency();
	}

	@DeleteMapping(value = "/delete-currency/{uuid}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> deleteCurrency(@PathVariable("uuid") String uuid) {
		return currencyService.deleteCurrency(uuid);
	}

	// TODO Create controller for invoices
	// <==============================================================================================================================================>
	@PostMapping(path = "/create-invoice", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR') or hasAuthority('ROLE_CUSTOMERSERVICE')")
	public ResponseEntity<Object> createInvoice(@RequestBody RequestCreateInvoice request) {
		return invoiceService.createInvoice(request);
	}

	@GetMapping(value = "/get-invoice/{businessKey}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR') or hasAuthority('ROLE_CUSTOMERSERVICE')")
	public ResponseEntity<Object> getInvoice(@PathVariable("businessKey") String businessKey) {
		return invoiceService.getInvoiceDetail(businessKey);
	}

	@GetMapping(value = "/get-invoices")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR') or hasAuthority('ROLE_CUSTOMERSERVICE')")
	public ResponseEntity<Object> getInvoices(@RequestParam String userUuid, Pageable pageable) {
		return invoiceService.getInvoices(userUuid, pageable);
	}

	@PutMapping(path = "/update-invoice/{businessKey}/{status}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> updateInvoice(@PathVariable("businessKey") String businessKey,
			@PathVariable("status") String status) {
		return invoiceService.updateInvoice(businessKey, status);
	}

	// TODO Create controller for raja ongkir
	// <==============================================================================================================================================>
	@GetMapping(value = "/get-rajaongkir-province-all")
	public ResponseEntity<Object> getProvince(
			@RequestHeader(value = "key", required = false) String key) {
		return externalService.getAllProvince(key);
	}

}
