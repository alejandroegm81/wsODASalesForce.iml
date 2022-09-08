package WsServicios.Rest;

import WsServicios.Entidades.Lista.*;
import WsServicios.Rest.Request.*;
import db.BaseClass;

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
    public Response gPreciosODA(wsR_gPreciosODA parametros){
        try {
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            wsR_PreciosODA vReturn = wE.gPreciosODA( parametros.Instancia, parametros.Parametros );

            GenericEntity<wsR_PreciosODA> genericEntity = new GenericEntity<wsR_PreciosODA>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    // ************************************************************
    // gSeriesODA
    // ************************************************************
    @POST
    @Path("gSeriesODA")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gSeriesODA(wsR_gSeriesODA parametros){
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

    // ************************************************************
    // gAlmacenes
    // ************************************************************
    @POST
    @Path("gMotivosInconformidad")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gMotivosInconformidad(wsR_gMotivosInconformidad Parametros) {
        try {
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            wsR_MotivosInconformidad vReturn = wE.gMotivosInconformidad( Parametros.Instancia );
            GenericEntity<wsR_MotivosInconformidad> genericEntity = new GenericEntity<wsR_MotivosInconformidad>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


}
