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
package com.indracompany.sofia2.iotbroker.processor.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indracompany.sofia2.iotbroker.processor.GatewayNotifier;
import com.indracompany.sofia2.ssap.SSAPMessage;
import com.indracompany.sofia2.ssap.body.SSAPBodyCommandMessage;
import com.indracompany.sofia2.ssap.enums.SSAPMessageDirection;
import com.indracompany.sofia2.ssap.enums.SSAPMessageTypes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path="/")
@EnableAutoConfiguration
@CrossOrigin(origins = "*")
public class CommandProcessor {

	@Autowired
	GatewayNotifier notifier;
	@Autowired
	ObjectMapper mapper;

	@RequestMapping(value="/commandAsync/{command}", method=RequestMethod.POST)
	public boolean sendAsync(@PathVariable(name="command") String command, String sessionKey, @RequestBody JsonNode params) {
		final SSAPMessage<SSAPBodyCommandMessage> cmd = new SSAPMessage<>();
		cmd.setBody(new SSAPBodyCommandMessage());
		cmd.setDirection(SSAPMessageDirection.REQUEST);
		cmd.setMessageType(SSAPMessageTypes.COMMAND);
		cmd.setSessionKey(sessionKey);
		cmd.getBody().setCommandId(UUID.randomUUID().toString());
		cmd.getBody().setCommand(command);
		cmd.getBody().setParams(params);

		notifier.sendCommandAsync(cmd);

		return true;
	}

	//	@RequestMapping(value="/commandSync/{command}", method=RequestMethod.POST)
	//	public JsonNode sendSync(@PathVariable(name="command") String command, String sessionKey, @RequestBody JsonNode params) {
	//
	//		final SSAPMessage<SSAPBodyCommandMessage> cmd = new SSAPMessage<>();
	//		cmd.setBody(new SSAPBodyCommandMessage());
	//		cmd.setDirection(SSAPMessageDirection.REQUEST);
	//		cmd.setMessageType(SSAPMessageTypes.COMMAND);
	//		cmd.setSessionKey(sessionKey);
	//		cmd.getBody().setCommand(UUID.randomUUID().toString());
	//		cmd.getBody().setCommand(command);
	//		cmd.getBody().setParams(params);
	//
	//		notifier.sendCommandSync(cmd);
	//
	//		return JsonNodeFactory.instance.nullNode();
	//	}

}
