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
package com.indracompany.sofia2.iotbroker.plugable.impl.gateway.reference.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		//		config.enableStompBrokerRelay("/topic/message")
		//		.setRelayHost("localhost")
		//		.setRelayPort(1884);
		config.enableSimpleBroker("/topic/message");
		config.setApplicationDestinationPrefixes("/stomp");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/message");
		registry.addEndpoint("/message").setAllowedOrigins("*").withSockJS();
	}

	//	@Bean
	//	public MessagingTemplate messageTemplate() {
	//		final MessageChannel m = new MessageChannel() {
	//
	//			@Override
	//			public boolean send(Message<?> message, long timeout) {
	//				// TODO Auto-generated method stub
	//				return false;
	//			}
	//
	//			@Override
	//			public boolean send(Message<?> message) {
	//				// TODO Auto-generated method stub
	//				return false;
	//			}
	//		};
	//
	//		final MessagingTemplate messagingTemplate = new MessagingTemplate(m);
	//
	//		MessageChannel defaultDestination = new MessageChannel() {
	//
	//			@Override
	//			public boolean send(Message<?> message, long timeout) {
	//				// TODO Auto-generated method stub
	//				return false;
	//			}
	//
	//			@Override
	//			public boolean send(Message<?> message) {
	//				// TODO Auto-generated method stub
	//				return false;
	//			}
	//		};
	//		messagingTemplate.setDefaultDestination(defaultDestination );
	//		return messagingTemplate;
	//	}
}