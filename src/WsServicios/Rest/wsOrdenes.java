package WsServicios.Rest;

import WsServicios.Ordenes.Response.wsR_Anula;
import WsServicios.Ordenes.Response.wsR_Generic;
import WsServicios.Ordenes.Response.wsR_Inconformidad;
import WsServicios.Ordenes.Response.wsR_Orden;
import WsServicios.Rest.Request.*;
import db.BaseClass;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("wsOrdenes")
public class wsOrdenes extends BaseClass {

    // *******************************************************
    // Default Endpoint to Rest
    // *******************************************************
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html><title>wsOrdenes</title><body>wsOrdenes</body></html>";
    }

    // *******************************************************
    // gExistencias: PKG_SAS_GENERA.CONSULTA_EXISTENCIAS
    // *******************************************************
    @POST
    @Path("gExistencias")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response gExistencias(wsR_gExistencias vDatos){
        try {

            WsServicios.Ordenes.wsOrdenes wO = new WsServicios.Ordenes.wsOrdenes();
            wsR_Generic vReturn = wO.gExistencias(vDatos.Instancia, vDatos.Parametros);
            return Response.ok(vReturn, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    // *******************************************************
    // sReservaSeries: PKG_SAS_GENERA.RESERVA_SERIES
    // *******************************************************
    @POST
    @Path("sReservaSerie")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sReservaSerie(wsR_sReservaSerie vDatos){
        try {

            WsServicios.Ordenes.wsOrdenes wO = new WsServicios.Ordenes.wsOrdenes();
            wsR_Generic vReturn = wO.sReservaSerie(vDatos.Instancia, vDatos.Parametros);
            return Response.ok(vReturn, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    // *******************************************************
    // sLiberaSerie: PKG_SAS_GENERA.LIBERA_SERIES
    // *******************************************************
    @POST
    @Path("sLiberaSerie")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sLiberaSerie(wsR_sLiberaSerie vDatos){
        try {

            WsServicios.Ordenes.wsOrdenes wO = new WsServicios.Ordenes.wsOrdenes();
            wsR_Generic vReturn = wO.sLiberSerie(vDatos.Instancia, vDatos.Parametros);
            return Response.ok(vReturn, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    // **************************************************************
    // gConsultaInconformidad: PKG_SAS_GENERA.CONSULTA_INCONFORMIDAD
    // **************************************************************
    @POST
    @Path("gConsultaInconformidad")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response gConsultaInconformidad(wsR_gConsultaInconformidad vDatos){
        try {

            WsServicios.Ordenes.wsOrdenes wO = new WsServicios.Ordenes.wsOrdenes();
            wsR_Inconformidad vReturn = wO.gConsultaInconformidad(vDatos.Instancia, vDatos.Inconformidad);
            return Response.ok(vReturn, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    // ******************************************************************
    // sActualizaInconformidad: PKG_SAS_GENERA.ACTUALIZA_INCONFORMIDAD
    // ******************************************************************
    @POST
    @Path("sActualizaInconformidad")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sActualizaInconformidad(wsR_sActualizaInconformidad vDatos){
        try {

            WsServicios.Ordenes.wsOrdenes wO = new WsServicios.Ordenes.wsOrdenes();
            wsR_Generic vReturn = wO.sActualizaInconformidad(vDatos.Instancia, vDatos.Inconformidad);
            return Response.ok(vReturn, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    // ******************************************************************
    // sAnulaPedido: PKG_CRM_ODA_ANULA.ANULA_VENTA
    // ******************************************************************
    @POST
    @Path("sAnulaPedido")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sAnulaPedido(wsR_sAnulaPedido vDatos){
        try {

            WsServicios.Ordenes.wsOrdenes wO = new WsServicios.Ordenes.wsOrdenes();
            wsR_Anula vReturn = wO.sAnulaPedido(vDatos.Instancia, vDatos.Parametros);
            return Response.ok(vReturn, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    // ******************************************************************
    // gConsultaEstadoVenta: PKG_CRM_ODA_ANULA.CONSULTA_ESTADO_VENTA
    // ******************************************************************
    @POST
    @Path("gConsultaEstadoVenta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response gConsultaEstadoVenta(wsR_gConsultaEstadoVenta vDatos){
        try {

            WsServicios.Ordenes.wsOrdenes wO = new WsServicios.Ordenes.wsOrdenes();
            wsR_Anula vReturn = wO.gConsultaEstadoVenta(vDatos.Instancia, vDatos.Orden);
            return Response.ok(vReturn, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    // **********************************************************************
    // gConsultaEstadoAnulacion: PKG_CRM_ODA_ANULA.CONSULTA_ESTADO_ANULACION
    // **********************************************************************
    @POST
    @Path("gConsultaEstadoAnulacion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response gConsultaEstadoAnulacion(wsR_gConsultaEstadoAnulacion vDatos){
        try {

            WsServicios.Ordenes.wsOrdenes wO = new WsServicios.Ordenes.wsOrdenes();
            wsR_Anula vReturn = wO.gConsultaEstadoAnulacion(vDatos.Instancia, vDatos.Orden);
            return Response.ok(vReturn, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    // **********************************************************************
    // sInsertaOrden: PKG_SIV_CAJA_ODA
    // **********************************************************************
    @POST
    @Path("sInsertaOrden")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sInsertaOrden(wsR_sInsertaOrden vDatos){
        try {

            WsServicios.Ordenes.wsOrdenes wO = new WsServicios.Ordenes.wsOrdenes();
            wsR_Orden vReturn = wO.sInsertaOrden(vDatos.Instancia, vDatos.vParametros);
            return Response.ok(vReturn, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


}
