package com.redhat.cloud.policies.engine.lightweight;

import io.vertx.core.json.JsonObject;
import org.hawkular.alerts.api.services.LightweightEngine;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import java.util.Map;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Path("/lightweight-engine")
public class LightweightEngineResource {

    private static final Logger LOGGER = Logger.getLogger(LightweightEngineResource.class);

    @Inject
    LightweightEngine lightweightEngine;

    @PUT
    @Path("/validate")
    @Consumes(TEXT_PLAIN)
    public Response validateCondition(@NotNull String condition) {
        try {
            lightweightEngine.validateCondition(condition);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.debugf(e, "Validation failed for condition %s", condition);
            JsonObject errorMessage = new JsonObject(Map.of("errorMsg", e.getMessage()));
            return Response.status(BAD_REQUEST).entity(errorMessage).build();
        }
    }
}
