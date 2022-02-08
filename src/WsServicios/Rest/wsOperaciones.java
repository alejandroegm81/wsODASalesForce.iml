package WsServicios.Rest;


import FromNET.SubDataTable;
import WsServicios.Bases.wsInstancias;
import WsServicios.Bases.wsTipoAccionManttos;
import WsServicios.Entidades.Base.wsR_Banco;
import WsServicios.Entidades.Lista.wsR_Agencias;
import WsServicios.Operaciones.Base.*;
import WsServicios.Operaciones.Lista.wsR_info_RegistroTransacciones;
import WsServicios.Operaciones.Lista.wsS_RegistroOperaciones;
import db.BaseClass;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("wsOperaciones")
public class wsOperaciones  extends BaseClass {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html><title>wsOperaciones</title><body>wsOperaciones</body></html>";
    }

    @GET
    @Path("gBusqueda/{instancia}/{autenticacion}/{operacion}/{telefono}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gBusqueda(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("operacion") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("telefono") String Telefono) {
        //wsR_gBusqueda
        try {
            wsR_gBusqueda vReturn =  new wsR_gBusqueda();
            WsServicios.Operaciones.wsOperaciones wO = new WsServicios.Operaciones.wsOperaciones();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.gBusqueda( Instancia, wA, OperacionServicio, "", Telefono, "", "");

            GenericEntity<wsR_gBusqueda> genericEntity = new GenericEntity<wsR_gBusqueda>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }







    @GET
    @Path("sReporteTransacciones/{instancia}/{autenticacion}/{operacion}/{fecha}")
    @Produces(MediaType.APPLICATION_JSON)
    @WebMethod(operationName = "sReporteTransacciones")
    public Response sReporteTransacciones(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("operacion") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("fecha") String FechaOperacion) {
        try {
            wsR_info_RegistroTransacciones vReturn =  new wsR_info_RegistroTransacciones();
            WsServicios.Operaciones.wsOperaciones wO = new WsServicios.Operaciones.wsOperaciones();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.sReporteTransacciones( Instancia, wA, OperacionServicio, FechaOperacion);

            GenericEntity<wsR_info_RegistroTransacciones> genericEntity = new GenericEntity<wsR_info_RegistroTransacciones>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @POST
    @Path("/sPagos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sPagos(wsOperaciones_sPagos vDatos){
        try {
            wsR_sPagos vReturn =  new wsR_sPagos();
            WsServicios.Operaciones.wsOperaciones wO = new WsServicios.Operaciones.wsOperaciones();
            vReturn = wO.sPagos( vDatos.Instancia, vDatos.Autenticacion, vDatos.IDBusqueda, vDatos.OperacionServicio, vDatos.InfoCliente, vDatos.Operaciones, vDatos.FormaPago );

            GenericEntity<wsR_sPagos> genericEntity = new GenericEntity<wsR_sPagos>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @POST
    @Path("/sAnulacion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sAnulacion(wsOperaciones_sAnulacion vDatos) {
        try {
            wsR_sPagos vReturn =  new wsR_sPagos();
            WsServicios.Operaciones.wsOperaciones wO = new WsServicios.Operaciones.wsOperaciones();
            vReturn = wO.sAnulacion( vDatos.Instancia, vDatos.Autenticacion, vDatos.OperacionServicio, vDatos.IDPago, vDatos.Comentario);

            GenericEntity<wsR_sPagos> genericEntity = new GenericEntity<wsR_sPagos>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }




}
