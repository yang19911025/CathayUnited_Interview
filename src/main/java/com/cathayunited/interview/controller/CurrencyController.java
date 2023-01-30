package com.cathayunited.interview.controller;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cathayunited.interview.model.Currency;
import com.cathayunited.interview.repository.CurrencyRepository;
import com.cathayunited.interview.service.CurrencyApiService;
import com.cathayunited.interview.utils.Helper;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CurrencyController {
	private static String coindeskAPI = "https://api.coindesk.com/v1/bpi/currentprice.json";
	
	@Autowired
	CurrencyApiService currencyApiService ;

	
	@GetMapping("/currency")
	public ResponseEntity<List<Currency>> showAllCurrencyInfo() {
		
		return currencyApiService.showAllCurrencyInfo();
	}
	@PostMapping("/newCurrency")
	public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency){
		return currencyApiService.createCurrency(currency);
	}
	@PutMapping("/updateCurrency/{id}")
	public ResponseEntity<Currency> updateCurrencyById(@RequestBody Currency currency,@PathVariable("id") long id){
		return currencyApiService.updateCurrencyById(currency, id);
	}
	@DeleteMapping("/deleteCurrency/{id}")
	public ResponseEntity<List<Currency>> deleteTutorialById(@PathVariable("id") long id){
		return currencyApiService.deleteTutorialById(id);
	}
	@GetMapping("/showCoindeskAPI")
	public ResponseEntity<String> showCoindeskAPI() throws Exception {
		return currencyApiService.showCoindeskAPI();
	}
	@GetMapping("/showNewCoindeskAPI")
	public ResponseEntity<String> showNewCoindeskAPI() throws Exception {
		
		return currencyApiService.showNewCoindeskAPI();
		
	}

}
