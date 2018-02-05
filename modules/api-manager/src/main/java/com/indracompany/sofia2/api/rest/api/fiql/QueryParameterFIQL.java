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
/*******************************************************************************
 * © Indra Sistemas, S.A.
 * 2013 - 2014  SPAIN
 * 
 * All rights reserved
 ******************************************************************************/
package com.indracompany.sofia2.api.rest.api.fiql;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import org.springframework.context.i18n.LocaleContextHolder;

import com.indracompany.sofia2.api.rest.api.dto.ApiQueryParameterDTO;
import com.indracompany.sofia2.config.model.ApiQueryParameter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class QueryParameterFIQL {

	private static final String API_REQUERIDO = "REQUIRED";
	private static final String API_OPCIONAL = "OPTIONAL";
	private static final String API_CONSTANTE = "CONSTANT";
	private static final String API_NUMBER = "NUMBER";
	private static final String API_BOOLEAN = "BOOLEAN";
	private static final String API_STRING = "STRING";

	static Locale locale = LocaleContextHolder.getLocale();

	private QueryParameterFIQL() {
		throw new AssertionError("Instantiating utility class...");
	}

	public static ArrayList<ApiQueryParameterDTO> toQueryParamDTO(Set<ApiQueryParameter> apiqueryparams) {
		ArrayList<ApiQueryParameterDTO> apiquertparamsDTO = new ArrayList<ApiQueryParameterDTO>();
		for (ApiQueryParameter apiqueryparam : apiqueryparams) {
			ApiQueryParameterDTO apiqueryparamDTO = toQueryParamDTO(apiqueryparam);
			apiquertparamsDTO.add(apiqueryparamDTO);
		}
		return apiquertparamsDTO;
	}

	public static ApiQueryParameterDTO toQueryParamDTO(ApiQueryParameter apiqueryparam) {
		ApiQueryParameterDTO apiqueryparamDTO = new ApiQueryParameterDTO();
		apiqueryparamDTO.setNombre(apiqueryparam.getName());
		apiqueryparamDTO.setTipo(apiqueryparam.getType());
		apiqueryparamDTO.setCondicion(apiqueryparam.getCondition());
		apiqueryparamDTO.setDescripcion(apiqueryparam.getDescription());
		apiqueryparamDTO.setValor(apiqueryparam.getValue());
		return apiqueryparamDTO;
	}

	public static ApiQueryParameter copyProperties(ApiQueryParameterDTO apiqueryparamDTO) {
		ApiQueryParameter apiqueryparam = new ApiQueryParameter();

		if (apiqueryparamDTO.getNombre() == null || apiqueryparamDTO.getNombre().equals("")) {
			throw new IllegalArgumentException("com.indra.sofia2.web.api.services.QueryParamNombreRequired");
		}
		if (apiqueryparamDTO.getTipo() == null || apiqueryparamDTO.getTipo().equals("")) {
			Object parametros[] = { apiqueryparamDTO.getNombre() };
			throw new IllegalArgumentException("com.indra.sofia2.web.api.services.QueryParamTipoRequired");
		}
		if (!isValidType(apiqueryparamDTO.getTipo())) {
			Object parametros[] = { apiqueryparamDTO.getNombre() };
			throw new IllegalArgumentException("com.indra.sofia2.web.api.services.QueryParamWrongTipo");
		}
		if (apiqueryparamDTO.getCondicion() == null || apiqueryparamDTO.getCondicion().equals("")) {
			throw new IllegalArgumentException("com.indra.sofia2.web.api.services.QueryParamCondicionRequired");
		}
		if (!isValidCondition(apiqueryparamDTO.getCondicion())) {
			Object parametros[] = { apiqueryparamDTO.getNombre() };
			throw new IllegalArgumentException("com.indra.sofia2.web.api.services.QueryParamWrongCondicion");
		}
		if (!isValidTypeValue(apiqueryparamDTO.getTipo(), apiqueryparamDTO.getValor())) {
			Object parametros[] = { apiqueryparamDTO.getValor(), apiqueryparamDTO.getNombre(),
					apiqueryparamDTO.getTipo() };
			throw new IllegalArgumentException("com.indra.sofia2.web.api.services.QueryParamWrongTipoValue");
		}
		if (!isValidCondicionValue(apiqueryparamDTO.getCondicion(), apiqueryparamDTO.getValor())) {
			Object parametros[] = { apiqueryparamDTO.getValor(), apiqueryparamDTO.getNombre(),
					apiqueryparamDTO.getCondicion() };
			throw new IllegalArgumentException("com.indra.sofia2.web.api.services.QueryParamWrongCondicionValue");
		}

		apiqueryparam.setName(apiqueryparamDTO.getNombre());
		apiqueryparam.setType(apiqueryparamDTO.getTipo());
		apiqueryparam.setCondition(apiqueryparamDTO.getCondicion());
		apiqueryparam.setDescription(apiqueryparamDTO.getDescripcion());
		apiqueryparam.setValue(apiqueryparamDTO.getValor());

		return apiqueryparam;
	}

	private static boolean isValidCondicionValue(String condicion, String valor) {
		if (condicion.equals(API_CONSTANTE)) {
			return (valor != null && !valor.equals(""));
		}
		return true;
	}

	private static boolean isValidTypeValue(String tipo, String valor) {
		if (tipo.equals(API_NUMBER)) {
			try {
				Integer.parseInt(valor);
			} catch (Exception e) {
				return false;
			}
		} else if (tipo.equals(API_BOOLEAN)) {
			try {
				Boolean.parseBoolean(valor);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/*
	 * private static boolean isValidCondition(String tipo) { return
	 * (tipo.equalsIgnoreCase(API_CONSTANTE)||tipo.equalsIgnoreCase(API_OPCIONAL)||
	 * tipo.equalsIgnoreCase(API_REQUERIDO)); }
	 */

	private static boolean isValidCondition(String tipo) {
		return (tipo.equalsIgnoreCase("query") || tipo.equalsIgnoreCase("path") || tipo.equalsIgnoreCase("body"));
	}

	private static boolean isValidType(String tipo) {
		return (tipo.equalsIgnoreCase(API_STRING) || tipo.equalsIgnoreCase(API_NUMBER)
				|| tipo.equalsIgnoreCase(API_BOOLEAN));
	}
}
