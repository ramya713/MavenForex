package com.sprint.exchange.repository;

import com.sprint.exchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate,Integer>{

	ExchangeRate save(ExchangeRate exchangeRate);

	ExchangeRate findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
	

	List<ExchangeRate> findByDate(LocalDate date);
	
//	To get rates by providing from currency and to currency

	List<ExchangeRate> findByFromCurrencyAndToCurrencyAndDate(String fromCurrency, String toCurrency, LocalDate date);
	

}