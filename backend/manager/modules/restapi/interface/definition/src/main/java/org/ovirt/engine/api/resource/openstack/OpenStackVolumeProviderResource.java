/*
* Copyright (c) 2015 Red Hat, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.ovirt.engine.api.resource.openstack;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.OpenStackVolumeProvider;
import org.ovirt.engine.api.resource.ApiMediaType;
import org.ovirt.engine.api.resource.ExternalProviderResource;

@Produces({ApiMediaType.APPLICATION_XML, ApiMediaType.APPLICATION_JSON})
public interface OpenStackVolumeProviderResource extends ExternalProviderResource {
    @GET
    OpenStackVolumeProvider get();

    @PUT
    @Consumes({ApiMediaType.APPLICATION_XML, ApiMediaType.APPLICATION_JSON})
    OpenStackVolumeProvider update(OpenStackVolumeProvider provider);

    @Path("volumetypes")
    OpenStackVolumeTypesResource getOpenStackVolumeTypes();

    @Path("authenticationkeys")
    OpenStackVolumeAuthenticationKeysResource getOpenStackVolumeAuthenticationKeys();

    @DELETE
    Response remove();
}
