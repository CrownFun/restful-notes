package pl.filewicz.grzegorz.restful.notes;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }
}
