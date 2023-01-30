package com.cathayunited.interview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cathayunited.interview.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency,Long> {
	

}
