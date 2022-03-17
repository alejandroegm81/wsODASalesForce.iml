package WsServicios.Rest;

import WsServicios.Bases.wsInstancias;
import WsServicios.Entidades.Lista.*;
import WsServicios.Rest.Request.*;
import db.BaseClass;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("wsEntidades")
public class wsEntidades extends BaseClass {

    // *******************************************************
    // Default Endpoint to Rest
    // *******************************************************
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html><title>wsEntidades</title><body>wsEntidades</body></html>";
    }


    // ************************************************************
    // gPreciosODA
    // ************************************************************
    @POST
    @Path("gPreciosODA")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Consulta listado de precios",
        description = "Consulta la vista ZSVSD_PRECIOS_VENTA obteniendo los precios activos en base a los parametros provistos: " +
            "1. Instancia* " +
            "2. motivoPedido " +
            "3. codigoProducto* " +
            "4. grupoCondiciones " +
            "5. grupoComisiones",
        tags = {"wsEntidades"},
        operationId = "gPreciosODA"
    )
    public Response gPreciosODA(wsR_gPreciosODA parametros){
        try {
            wsR_PreciosODA vReturn =  new wsR_PreciosODA();
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            vReturn = wE.gPreciosODA( parametros.Instancia, parametros.Parametros );

            GenericEntity<wsR_PreciosODA> genericEntity = new GenericEntity<wsR_PreciosODA>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    // ************************************************************
    // gSeriesODA
    // ************************************************************
    @Operation(
        summary = "Consulta series por agencia y producto",
        description = "Consulta la vista vw_series obteniendo los datos de series pertenecientes a la agencia y " +
            "material consultado ",
        tags = {"wsEntidades"},
        operationId = "gSeriesODA"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the book",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = wsR_Series.class)) }),
        @ApiResponse(responseCode = "400", description = "Parámetros no fueron suplidos",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error en la ejecución del servicio",
            content = @Content)})
    @POST
    @Path("gSeriesODA")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gSeriesODA(
        @RequestBody(
            required = true,
            ref = "wsR_gSeriesODA",
            description = "Parametros de entrada para consultar las series",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = wsR_gSeriesODA.class),
                    examples = {
                        @ExampleObject(
                            name = "Instancia",
                            summary = "País que ejecuta el servicio",
                            value = "ODA_503"
                        )
                    }
                )
            }
        ) wsR_gSeriesODA parametros){
        try {
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            wsR_Series vReturn = wE.gSeriesODA( parametros.Instancia, parametros.Parametros );

            GenericEntity<wsR_Series> genericEntity = new GenericEntity<wsR_Series>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }



    // ************************************************************
    // gAgencias
    // ************************************************************
    @POST
    @Path("gAgencias")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gAgencias(wsR_gAgencias parametros){
        try {
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            wsR_Agencias vReturn = wE.gAgencias( parametros.Instancia, parametros.Parametros );

            GenericEntity<wsR_Agencias> genericEntity = new GenericEntity<wsR_Agencias>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    // ************************************************************
    // gTiposVenta
    // ************************************************************
    @POST
    @Path("gTiposVenta")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gTiposVenta(wsR_gTiposVenta parametros){
        try {
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            wsR_TiposVenta vReturn = wE.gTiposVenta( parametros.Instancia, parametros.Parametros );

            GenericEntity<wsR_TiposVenta> genericEntity = new GenericEntity<wsR_TiposVenta>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    // ************************************************************
    // gAlmacenes
    // ************************************************************
    @POST
    @Path("gAlmacenes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gAlmacenes(wsR_gAlmacenes parametros){
        try {
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            wsR_Almacenes vReturn = wE.gAlmacenes( parametros.Instancia, parametros.Parametros );
            GenericEntity<wsR_Almacenes> genericEntity = new GenericEntity<wsR_Almacenes>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }



}
