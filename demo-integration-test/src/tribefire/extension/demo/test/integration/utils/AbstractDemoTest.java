// ============================================================================
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License along with this library; See http://www.gnu.org/licenses/.
// ============================================================================
package tribefire.extension.demo.test.integration.utils;

import org.junit.After;
import org.junit.Before;

import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSessionFactory;
import com.braintribe.product.rat.imp.ImpApi;
import com.braintribe.testing.internal.tribefire.tests.AbstractTribefireQaTest;

import tribefire.extension.demo.model.deployment.DemoAccess;

/**
 * Creates a new DemoAccess and provides subclasses with <br>
 * a session to it: {@link #demoAccessSession}<br>
 * a session factory to the cortex: {@link #globalCortexSessionFactory}<br>
 * an imp {@link #globalImp}
 * and the configured demo model: {@link #configuredDemoModel} <br>
 * <br>
 * undeploys the created accesss in the end
 * 
 * @author Neidhart
 *
 */

public abstract class AbstractDemoTest extends AbstractTribefireQaTest implements DemoConstants {
	
	protected PersistenceGmSession demoAccessSession;
	protected GmMetaModel configuredDemoModel;
	protected ImpApi globalImp;
	protected PersistenceGmSessionFactory globalCortexSessionFactory;

	private DemoAccess demoAccess;

	@Before
	public void initBase() throws GmSessionException {
		logger.info("Preparing Demo Integration Test...");

		globalImp = apiFactory().build();
		globalCortexSessionFactory = apiFactory().buildSessionFactory();
		
		String newDemoAccessId = nameWithTimestamp("DemoAccess");
		configuredDemoModel = globalImp.model(CONFIGURED_DEMO_MODEL_ID).get();
		
		DemoAccess defaultDemoAccess = globalImp.deployable(DemoAccess.T, DEMO_ACCESS_ID).get();
		
		demoAccess = globalImp.deployable().access()
				.createIncremental(DemoAccess.T, newDemoAccessId, newDemoAccessId, configuredDemoModel)
				.get();
		
		demoAccess.setInitDefaultPopulation(true);
		demoAccess.setServiceModel(globalImp.model(CONFIGURED_DEMO_SERVICE_MODEL_ID).get());
		demoAccess.setAspectConfiguration(defaultDemoAccess.getAspectConfiguration());

		globalImp.deployable(demoAccess).commitAndDeploy();

		demoAccessSession = globalImp.switchToAccess(demoAccess.getExternalId()).session();

		logger.info("##################################### actual test begins #############################");
	}

	@After
	public void tearDown() {
		logger.info("##################################### tear down #############################");
		eraseTestEntities();
	}
}
