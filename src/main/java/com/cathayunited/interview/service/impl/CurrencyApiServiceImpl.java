package com.cathayunited.interview.service.impl;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cathayunited.interview.model.Currency;
import com.cathayunited.interview.repository.CurrencyRepository;
import com.cathayunited.interview.service.CurrencyApiService;
import com.cathayunited.interview.utils.Helper;

@Service
public class CurrencyApiServiceImpl implements CurrencyApiService {
	private static String coindeskAPI = "https://api.coindesk.com/v1/bpi/currentprice.json";

	@Autowired
	CurrencyRepository currencyRepository;

	@Override
	public ResponseEntity<List<Currency>> showAllCurrencyInfo() {
		try {
			List<Currency> tutorials = new ArrayList<Currency>();

			for (Currency currency : currencyRepository.findAll()) {
				tutorials.add(currency);
			}
			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Currency> createCurrency(Currency currency) {
		try {
			Currency newCurrency = currencyRepository.save(new Currency(currency.getCode(), currency.getDescription()));

			return new ResponseEntity<>(newCurrency, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Currency> updateCurrencyById(Currency currency, long id) {
		try {
			Optional<Currency> currencyData = currencyRepository.findById(id);

			if (currencyData.isPresent()) {
				Currency newCurrency = currencyData.get();
				newCurrency.setCode(currency.getCode());
				newCurrency.setDescription(currency.getDescription());
				return new ResponseEntity<>(currencyRepository.save(newCurrency), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<List<Currency>> deleteTutorialById(long id) {
		try {
			currencyRepository.deleteById(id);

			return showAllCurrencyInfo();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<String> showCoindeskAPI() throws Exception {
		URIBuilder builder = new URIBuilder(coindeskAPI);
		HttpGet get = new HttpGet(builder.toString());
		return new ResponseEntity<>(Helper.callApi(get), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> showNewCoindeskAPI() throws Exception {
		String rawData = showCoindeskAPI().getBody();
		JSONObject jo = new JSONObject(rawData);
		JSONObject time = null;
		JSONObject bpi = null;
		JSONObject tmp = null;
		JSONObject result = new JSONObject();
		JSONArray currency = new JSONArray();
		time = jo.getJSONObject("time");
		String isoTimestamp = time.getString("updatedISO");
		String updatedTime = Helper.getDateStringFromISOTimeStamp(isoTimestamp);
		result.put("updatedTime", updatedTime);
		
		
		bpi = jo.getJSONObject("bpi");
		
		for(String s : Helper.getCurrencyList()) {
			tmp = bpi.getJSONObject(s.split(":")[0]);
			currency.add(new JSONObject().put("code", s.split(":")[0]).put("description", s.split(":")[1]).put("rate", tmp.getString("rate")));
		}
		
		result.put("currencyList", currency);

		return new ResponseEntity<>(result.toString(), HttpStatus.OK);
	}

}
