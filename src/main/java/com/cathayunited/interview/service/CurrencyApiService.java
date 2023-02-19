package com.cathayunited.interview.service;

import java.util.List;

import org.apache.wink.json4j.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cathayunited.interview.model.Currency;

public interface CurrencyApiService {
	public ResponseEntity<List<Currency>> showAllCurrencyInfo();
	public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency);
	public ResponseEntity<Currency> updateCurrencyById(@RequestBody Currency currency,@PathVariable("id") long id);
	public ResponseEntity<List<Currency>> deleteTutorialById(@PathVariable("id") long id);
	public ResponseEntity<String> showCoindeskAPI() throws Exception;
	public ResponseEntity<String> showNewCoindeskAPI() throws Exception;

}
