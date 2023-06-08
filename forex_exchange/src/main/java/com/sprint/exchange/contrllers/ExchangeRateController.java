package com.sprint.exchange.contrllers;

import com.sprint.exchange.dto.CommonDTO;
import com.sprint.exchange.dto.ExchangeRateDto;
import com.sprint.exchange.dto.RateConvertDTO;
import com.sprint.exchange.model.ExchangeRate;
import com.sprint.exchange.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin()
@RestController
public class ExchangeRateController {
	@Autowired
	private ExchangeRateService exchangeRateService;

	
	@PostMapping("/rate/convert")
	public ResponseEntity<CommonDTO<Double>> convertAmount(@RequestBody RateConvertDTO r){
		Double convertedAmount = exchangeRateService.convertAmount(r.getAmount(), r.getFromCurrency(), r.getToCurrency());
		return ResponseEntity.ok(new CommonDTO<>("Converted", convertedAmount));
		
	}

	@PostMapping("/rate/save")
	public ExchangeRateDto convertAmount(@RequestBody ExchangeRateDto r){
		return exchangeRateService.saveExchangeRate(r);
	}


	
// To get all the rates by entering date
	@GetMapping("/exchange-rates/{date}")
	public ResponseEntity<List<ExchangeRate>> getRatesByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String dateString = date.format(formatter);
	    List<ExchangeRate> rates = exchangeRateService.getRatesByDate(dateString);
	    return new ResponseEntity<>(rates, HttpStatus.OK);
	    
	}
	@PutMapping("/exchangeRate/update")
	public ResponseEntity<CommonDTO<ExchangeRate>> modifyRate(@RequestBody ExchangeRate exchangeRate) {
		ExchangeRate updatedexchangeRate = exchangeRateService.updateExchangeRate(exchangeRate);
		return new ResponseEntity<>(new CommonDTO<>("Exchange rate successfully updated",updatedexchangeRate), HttpStatus.OK);


	}

	
//	To get rates by providing from currency and to currency and date
	@GetMapping("/exchange-rates/{fromCurrency}/{toCurrency}/{date}")
	public ResponseEntity<List<ExchangeRate>> getRatesByFromToCurrencyAndDate(@PathVariable("fromCurrency")String fromCurrency, @PathVariable("toCurrency")String toCurrency ,@PathVariable("date")@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
	    List<ExchangeRate> rates = exchangeRateService.getRatesByFromToCurrencyAndDate(fromCurrency, toCurrency, date);
	    return new ResponseEntity<>(rates, HttpStatus.OK);
	    
	}

	@GetMapping("/exchange-rate/{fromCurrency}/{toCurrency}")
	public ExchangeRate getRatesByFromToCurrency(@PathVariable("fromCurrency")String fromCurrency, @PathVariable("toCurrency")String toCurrency) {
		return exchangeRateService.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
	}
//	To get all rates
	@GetMapping("/rate/all")
	public List<ExchangeRate> findAllRates() {
		return exchangeRateService.findAllRates();
		
	}
	
	
	@GetMapping("/rate/{id}")
	public ResponseEntity<ExchangeRate> getExchangeRate(@PathVariable("id") int Id) {
		ExchangeRate exchangeRate = exchangeRateService.getById(Id);
		return new ResponseEntity<>(exchangeRate, HttpStatus.OK);
	}
	


}
