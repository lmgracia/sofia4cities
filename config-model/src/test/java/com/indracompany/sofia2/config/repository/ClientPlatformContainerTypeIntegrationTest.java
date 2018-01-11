/*******************************************************************************
 * © Indra Sistemas, S.A.
 * 2013 - 2018  SPAIN
 *
 * All rights reserved
 ******************************************************************************/
package com.indracompany.sofia2.config.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.indracompany.sofia2.config.model.ClientPlatformContainerType;

import com.indracompany.sofia2.config.repository.ClientPlatformContainerTypeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Luis Miguel Gracia
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class ClientPlatformContainerTypeIntegrationTest {

	@Autowired
	ClientPlatformContainerTypeRepository repository;

	private boolean noData=false;

	@Before
	public void setUp() {
		List<ClientPlatformContainerType> types = this.repository.findAll();
		if (types.isEmpty()) {
			log.info("No types en tabla.Adding...");
			noData=true;
			ClientPlatformContainerType type=new ClientPlatformContainerType();
			type.setId(1);
			type.setType("Python");
			repository.save(type);
			type=new ClientPlatformContainerType();
			type.setId(2);
			type.setType("Java");
			repository.save(type);
			type=new ClientPlatformContainerType();
			type.setId(3);
			type.setType("URL");
			repository.save(type);		
		}
	}

	@Test
	public void test1_Count() { 
		Assert.assertTrue(this.repository.count()==3);		
	}

	@Test
	public void test2_GetAll() {
		Assert.assertTrue(this.repository.findAll().size()==3);
	}


}