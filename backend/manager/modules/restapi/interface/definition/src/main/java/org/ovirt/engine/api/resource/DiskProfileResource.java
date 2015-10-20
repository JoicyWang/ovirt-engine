package org.ovirt.engine.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.ovirt.engine.api.model.DiskProfile;

@Produces({ ApiMediaType.APPLICATION_XML, ApiMediaType.APPLICATION_JSON })
public interface DiskProfileResource {
    @GET
    DiskProfile get();

    @PUT
    @Consumes({ApiMediaType.APPLICATION_XML, ApiMediaType.APPLICATION_JSON})
    DiskProfile update(DiskProfile profile);

    @DELETE
    Response remove();

    @Path("permissions")
    public AssignedPermissionsResource getPermissionsResource();
}
