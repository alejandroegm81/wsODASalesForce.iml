package WsServicios.Rest;

import WsServicios.Bases.wsInstancias;
import WsServicios.Entidades.Lista.*;
import WsServicios.Rest.Request.wsR_gPreciosODA;
import WsServicios.Rest.Request.wsR_gSeriesODA;
import db.BaseClass;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("wsEntidades")
public class wsEntidades extends BaseClass {


    @GET
    @Path("gBancos/{ins}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gBancos(@PathParam("ins") wsInstancias.wsInstancia Instancia) {
        try {
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            wsR_Bancos vReturn = wE.gBancos( Instancia );

            GenericEntity<wsR_Bancos> genericEntity = new GenericEntity<wsR_Bancos>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    // ************************************************************
    // gPreciosODA
    // ************************************************************
    @POST
    @Path("gPreciosODA")
    @Produces(MediaType.APPLICATION_JSON)
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
    @POST
    @Path("gSeriesODA")
    @Produces(MediaType.APPLICATION_JSON)
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



}
