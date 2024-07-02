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
package tribefire.extension.demo.initializer.wire.space;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.demo.initializer.wire.contract.DemoInitializerModelsContract;
import tribefire.extension.demo.initializer.wire.contract.ExistingInstancesContract;

/**
 * @see DemoInitializerModelsContract
 */
@Managed
public class DemoInitializerModelsSpace extends AbstractInitializerSpace implements DemoInitializerModelsContract {

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Managed
	@Override
	public GmMetaModel configuredDemoDeploymentModel() {
		GmMetaModel model = create(GmMetaModel.T);
		model.setName(ExistingInstancesContract.GROUP_ID + ":configured-demo-deployment-model");
		model.getDependencies().add(existingInstances.demoDeploymentModel());

		return model;
	}

	@Managed
	@Override
	public GmMetaModel configuredDemoServiceModel() {
		GmMetaModel model = create(GmMetaModel.T);
		model.setName(ExistingInstancesContract.GROUP_ID + ":configured-demo-api-model");
		model.getDependencies().add(existingInstances.demoServiceModel());

		return model;
	}

	@Managed
	@Override
	public GmMetaModel configuredDemoCortexServiceModel() {
		GmMetaModel model = create(GmMetaModel.T);
		model.setName(ExistingInstancesContract.GROUP_ID + ":configured-demo-cortex-api-model");
		model.getDependencies().add(existingInstances.demoCortexServiceModel());

		return model;
	}

	@Managed
	@Override
	public GmMetaModel configuredDemoModel() {
		GmMetaModel model = create(GmMetaModel.T);
		model.setName(ExistingInstancesContract.GROUP_ID + ":configured-demo-model");
		model.getDependencies().add(existingInstances.demoModel());

		return model;
	}

	@Managed
	@Override
	public GmMetaModel demoWorkbenchModel() {
		GmMetaModel model = create(GmMetaModel.T);
		model.setName(ExistingInstancesContract.GROUP_ID + ":demo-workbench-model");
		model.getDependencies().add(existingInstances.demoModel());
		model.getDependencies().add(existingInstances.demoServiceModel());
		model.getDependencies().add(coreInstances.workbenchModel());
		model.getDependencies().add(coreInstances.essentialMetaDataModel());

		return model;
	}
}
