package com.coremei.bank.loans.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coremei.bank.loans.model.Loan;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Integer> {
	
	List<Loan> findByCustomerIdOrderByStartDtDesc(int customerId);
}
