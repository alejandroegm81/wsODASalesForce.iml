import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bootstrap extends HttpServlet {
  @Override
  public void init(ServletConfig config) throws ServletException {
    OpenAPI oas = new OpenAPI();
    Info info = new Info()
        .title("wsODASalesForce Documentación")
        .description("wsODASalesForce - Servicio que expone la implementación de los paquetes utilizados para creación " +
            "de ordenes y extracción de catálogos de ODA");

    oas.info(info);
    SwaggerConfiguration oasConfig = new SwaggerConfiguration()
        .openAPI(oas)
        .prettyPrint(true)
        .resourcePackages(Stream.of("WsServicios.Rest").collect(Collectors.toSet()));

    try {
      new JaxrsOpenApiContextBuilder()
          .servletConfig(config)
          .openApiConfiguration(oasConfig)
          .buildContext(true);
    } catch (OpenApiConfigurationException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
