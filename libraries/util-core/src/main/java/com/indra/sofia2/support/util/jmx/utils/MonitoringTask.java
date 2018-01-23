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
 * 2013 - 2015  SPAIN
 * 
 * All rights reserved
 ******************************************************************************/

package com.indra.sofia2.support.util.jmx.utils;

public abstract class MonitoringTask<T> implements Runnable {
	private T result;

	public T getResult() {
		return result;
	}

	public abstract T getFallbackValue();	
	
	protected abstract T execute();
	
	@Override
	public void run(){
		result = this.execute();
	}
}
