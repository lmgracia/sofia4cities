/**
 * Copyright Indra Sistemas, S.A.
 * 2013-2018 SPAIN
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.indracompany.sofia2.simulator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class FieldRandomizerServiceImpl implements FieldRandomizerService {

	private static final String FIXED_NUMBER = "FIXED_NUMBER";
	private static final String FIXED_STRING = "FIXED_STRING";
	private static final String FIXED_DATE = "FIXED_DATE";
	private static final String FIXED_INTEGER = "FIXED_INTEGER";
	private static final String COSINE_NUMBER = "COSINE_NUMBER";
	private static final String SINE_NUMBER = "SINE NUMBER";
	private static final String RANDOM_NUMBER = "RANDOM_NUMBER";
	private static final String RANDOM_INTEGER = "RANDOM_INTEGER";
	private static final String RANDOM_DATE = "RANDOM_DATE";
	private static final String RANDOM_STRING = "RANDOM_STRING";
	private static final String NULL = "NULL";

	@Override
	public JsonNode randomizeFields(JsonNode json) {
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode map = mapper.createObjectNode() ;
		
		Iterator<String> fields = json.fieldNames();
		while (fields.hasNext()) {
			String field = fields.next();
			String function = json.path(field).get("function").asText();

			switch (function) {
			case FIXED_NUMBER:
				((ObjectNode) map).put(field, json.path(field).get("value").asDouble());
				break;
			case FIXED_INTEGER:
				((ObjectNode) map).put(field, json.path(field).get("value").asInt());
				break;
			case RANDOM_NUMBER:
				((ObjectNode) map).put(field, this.randomizeDouble(json.path(field).get("from").asDouble(),
						json.path(field).get("to").asDouble(), json.path(field).get("precision").asInt()));
				break;
			case RANDOM_INTEGER:
				((ObjectNode) map).put(field,
						this.randomizeInt(json.path(field).get("from").asInt(), json.path(field).get("to").asInt()));
				break;
			case COSINE_NUMBER:
				break;
			case SINE_NUMBER:
				break;
			case FIXED_STRING:
				((ObjectNode) map).put(field, (String) json.path(field).get("value").asText());
				break;
			case RANDOM_STRING:
				((ObjectNode) map).put(field, (String) this.randomizeStrings(json.path(field).get("list").asText()));
				break;
			case FIXED_DATE:
				Date date;
				try {
					date = DateFormat.getInstance().parse(json.path(field).get("value").asText());
				} catch (ParseException e) {
					date = new Date();
				}
				JsonNode dateJson = mapper.createObjectNode();
				((ObjectNode) dateJson).put("$date", date.getTime());
				((ObjectNode) map).set(field, dateJson);

				break;
			case RANDOM_DATE:
				break;
			case NULL:
				((ObjectNode) map).set(field, null);
				break;

			}

		}

		return map;
	}

	public String randomizeStrings(String list) {
		List<String> words = new ArrayList<String>(Arrays.asList(list.split(",")));
		if (words.size() >= 1) {
			int selection = this.randomizeInt(0, words.size()-1);
			return words.get(selection);
		} else
			return list;

	}

	public int randomizeInt(int min, int max) {
		Random random = new Random();
		int randomInt = random.nextInt((max - min) + 1) + min;
		return randomInt;
	}

	public double randomizeDouble(double min, double max, int precision) {
		Random random = new Random();
		Double randomDouble = min + (max - min) * random.nextDouble();
		Double randomDoubleTruncated = BigDecimal.valueOf(randomDouble).setScale(precision, RoundingMode.HALF_UP)
				.doubleValue();
		return randomDoubleTruncated;
	}
}
