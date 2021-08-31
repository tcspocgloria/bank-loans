package com.coremei.bank.loans.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.coremei.bank.loans.model.Properties;
import com.coremei.bank.loans.config.LoanServiceConfig;
import com.coremei.bank.loans.model.Customer;
import com.coremei.bank.loans.model.Loan;
import com.coremei.bank.loans.repository.LoanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
public class LoanController {

	@Autowired
	private LoanRepository repository;

	@Autowired
	private LoanServiceConfig config;
	
	private static Logger logger = LoggerFactory.getLogger(LoanController.class);

	@PostMapping("/myLoans")
	public List<Loan> getLoansDetails(@RequestHeader("bank-correlation-id") String correlationId,
			@RequestBody Customer customer) {
		logger.info("getLoansDetails() method started");
		
		List<Loan> loans = repository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		
		logger.info("getLoansDetails() method ended");
		
		return loans;
	}

	@GetMapping("/loans/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(config.getMsg(), config.getBuildVersion(), config.getMailDetails(),
				config.getActiveBranches());
		return ow.writeValueAsString(properties);
	}

}
