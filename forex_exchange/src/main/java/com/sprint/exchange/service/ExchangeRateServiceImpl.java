package com.sprint.exchange.service;



import com.sprint.exchange.dto.ExchangeRateDto;
import com.sprint.exchange.exception.AdminNotFoundException;
import com.sprint.exchange.exception.ExchangeRateNotFoundException;
import com.sprint.exchange.model.Admin;
import com.sprint.exchange.model.ExchangeRate;
import com.sprint.exchange.repository.ExchangeRateRepository;
import com.sprint.exchange.util.AdminFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AdminFeignClient adminFeignClient;

	@Override
	public ExchangeRateDto saveExchangeRate(ExchangeRateDto exchangeRateDto) {

		ExchangeRate exRate =new ExchangeRate();
         exRate.setFromCurrency(exchangeRateDto.getFromCurrency());
         exRate.setToCurrency(exchangeRateDto.getToCurrency());
         exRate.setRate(exchangeRateDto.getRate());
         exRate.setDate(exchangeRateDto.getDate());
        
		Optional<Admin> optionalAdmin = adminFeignClient.findById(exchangeRateDto.getAdminId());
		if (optionalAdmin.isEmpty()) {

			throw new AdminNotFoundException(" admin is not exists");

			}
		
             Admin admin=optionalAdmin.get();
              exRate.setAdmin(admin);
             ExchangeRate newExchangeRate = exchangeRateRepository.save(exRate);


             

		return exchangeRateDto;
	}

	@Override
	public Double convertAmount(Double amount, String fromCurrency, String toCurrency) {
		
		ExchangeRate exchangeRate = exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
		if (exchangeRate == null) {

			throw new ExchangeRateNotFoundException("Excahnge Rate Not found " +" "+ fromCurrency + "/" + toCurrency);
		}
		Double rate = exchangeRate.getRate();

		return rate * amount;
	}


//	To get all the rates by entering date
	@Override
	public List<ExchangeRate> getRatesByDate(String dateString) throws ExchangeRateNotFoundException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(dateString, formatter);
		List<ExchangeRate> exchangeRates = exchangeRateRepository.findByDate(date);
		if (exchangeRates.isEmpty()) {

			throw new ExchangeRateNotFoundException("exchangeRatenotfound" + date);
		}
		
		
		return exchangeRates;
	}

	@Override
	public ExchangeRate findByFromCurrencyAndToCurrency(String from, String to) {
		return exchangeRateRepository.findByFromCurrencyAndToCurrency(from,to);
	}

	//	To get rates by providing from currency and to currency and date
	@Override
	public List<ExchangeRate> getRatesByFromToCurrencyAndDate(String fromCurrency, String toCurrency, LocalDate date)
			throws ExchangeRateNotFoundException {
		
		List<ExchangeRate> exchangeRates = exchangeRateRepository.findByFromCurrencyAndToCurrencyAndDate(fromCurrency,
				toCurrency, date);
		if (exchangeRates.isEmpty()) {

			throw new ExchangeRateNotFoundException("exchangeRate.not.found" +" "+ fromCurrency + "/" + toCurrency + " "+ date + "");
		}
		return exchangeRates;

	}
//To update the rates
	@Override
	public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate)throws ExchangeRateNotFoundException {
		
		Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findById(exchangeRate.getId());
		if (optionalExchangeRate.isEmpty()) {
			throw new ExchangeRateNotFoundException("Rate not existing with id:" + exchangeRate.getId());
		}
		return exchangeRateRepository.save(exchangeRate);
		
	}

	@Override
	public ExchangeRate getById(int Id)throws ExchangeRateNotFoundException {
		Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findById(Id);
		if (optionalExchangeRate.isEmpty()) {
			throw new ExchangeRateNotFoundException("exchangeRate.not.found"+Id);
		}
		return optionalExchangeRate.get();

	}

	@Override
	public List<ExchangeRate> findAllRates() {
		return exchangeRateRepository.findAll();
		
	}

	
}