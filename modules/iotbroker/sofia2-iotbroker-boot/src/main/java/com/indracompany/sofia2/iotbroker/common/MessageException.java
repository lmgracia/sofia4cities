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
package com.indracompany.sofia2.iotbroker.common;

public class MessageException {
	public final static String ERR_ONTOLOGY_AUTH = "Unauthorized ontology";
	public final static String ERR_PROCESSOR_NOT_FOUND = "Processor for %s messages not found";
	public final static String ERR_SSAP_MESSAGETYPE_MANDATORY_NOT_NULL = "MessageType is mandatory must not be null";
	public static final String ERR_SESSIONKEY_NOT_ASSINGED = "No SessionKey granted";
	public static final String ERR_SESSIONKEY_NOT_VALID = "Invalid SessionKey";
	public static final String ERR_BD_QUERY_TYPE_NOT_SUPPORTED = "QueryType %s not supported";
	public static final String ERR_ONTOLOGY_SCHEMA = "Json schema error: %s";
	public final static String ERR_FIELD_IS_MANDATORY = "%s field is mandatory for %s messages";
	public final static String ERR_DATABASE = "Database query error, check your message";

}
