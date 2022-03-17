

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;

import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ApplicationPath("rest")
@OpenAPIDefinition(info = @Info(title = "wsODASalesForce", version = "1.0", description = "Generación de Ordenes de venta"))
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    return Stream.of(WsServicios.Rest.wsEntidades.class, WsServicios.Rest.wsOrdenes.class, OpenApiResource.class).collect(Collectors.toSet());
  }

}