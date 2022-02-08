package WsServicios.Rest;

import WsServicios.Bases.wsInstancias;
import WsServicios.Operaciones.Base.wAutenticacion;
import WsServicios.Operaciones.Base.wsOperacionServicios;
import WsServicios.Operaciones.Base.wsR_gBusqueda;
import WsServicios.OperacionesAutogestion.proceso.*;
import db.BaseClass;

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

@Path("wsAutogestion")
public class wsAutogestion  extends BaseClass {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html><title>wsAutogestion</title><body>wsAutogestion</body></html>";
    }


    @GET
    @Path("HistoricoFacturas/{instancia}/{autenticacion}/{OperacionServicio}/{telefono}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response HistoricoFacturas(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("telefono") String Telefono) {
        //wsR_gBusqueda
        try {

            wsR_HistoricoFacturas vReturn =  new wsR_HistoricoFacturas();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.HistoricoFacturas ( Instancia, wA, OperacionServicio, "", Telefono);

            GenericEntity<wsR_HistoricoFacturas> genericEntity = new GenericEntity<wsR_HistoricoFacturas>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }





    @GET
    @Path("ConsultaSaldos/{instancia}/{autenticacion}/{OperacionServicio}/TELEFONO/{telefono}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ConsultaSaldos_Telefono(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("telefono") String Telefono) {
        //wsR_gBusqueda
        try {

            wsR_ConsultaSaldos vReturn =  new wsR_ConsultaSaldos();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.ConsultaSaldos ( Instancia, wA, OperacionServicio, "", Telefono);

            GenericEntity<wsR_ConsultaSaldos> genericEntity = new GenericEntity<wsR_ConsultaSaldos>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    @GET
    @Path("ConsultaSaldos/{instancia}/{autenticacion}/{OperacionServicio}/CLIENTE/{cliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ConsultaSaldos_Cliente(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("cliente") String Cliente) {
        //wsR_gBusqueda
        try {

            wsR_ConsultaSaldos vReturn =  new wsR_ConsultaSaldos();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.ConsultaSaldos ( Instancia, wA, OperacionServicio, Cliente, "");

            GenericEntity<wsR_ConsultaSaldos> genericEntity = new GenericEntity<wsR_ConsultaSaldos>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }





    @GET
    @Path("ValidacionServicios/{instancia}/{autenticacion}/{OperacionServicio}/TELEFONO/{telefono}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ValidacionServicios_Telefono(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("telefono") String Telefono) {
        //wsR_gBusqueda
        try {

            wsR_ValidacionServicios vReturn =  new wsR_ValidacionServicios();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.ValidacionServicios ( Instancia, wA, OperacionServicio,  "", Telefono,"");

            GenericEntity<wsR_ValidacionServicios> genericEntity = new GenericEntity<wsR_ValidacionServicios>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    @GET
    @Path("ValidacionServicios/{instancia}/{autenticacion}/{OperacionServicio}/CLIENTE/{cliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ValidacionServicios_Cliente(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("cliente") String Cliente) {
        //wsR_gBusqueda
        try {

            wsR_ValidacionServicios vReturn =  new wsR_ValidacionServicios();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.ValidacionServicios ( Instancia, wA, OperacionServicio,  Cliente, "","");

            GenericEntity<wsR_ValidacionServicios> genericEntity = new GenericEntity<wsR_ValidacionServicios>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    @GET
    @Path("ValidacionServicios/{instancia}/{autenticacion}/{OperacionServicio}/IDENTIFICACION/{identificacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ValidacionServicios_Identificacion(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("identificacion") String Identificacion) {
        //wsR_gBusqueda
        try {

            wsR_ValidacionServicios vReturn =  new wsR_ValidacionServicios();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.ValidacionServicios ( Instancia, wA, OperacionServicio,  "", "",Identificacion);

            GenericEntity<wsR_ValidacionServicios> genericEntity = new GenericEntity<wsR_ValidacionServicios>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }





    @GET
    @Path("HistorialPagos/{instancia}/{autenticacion}/{OperacionServicio}/TELEFONO/{telefono}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response HistorialPagos_Telefono(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("telefono") String Telefono) {
        //wsR_gBusqueda
        try {

            wsR_HistorialPagos vReturn =  new wsR_HistorialPagos();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.HistorialPagos ( Instancia, wA, OperacionServicio,  "", Telefono);

            GenericEntity<wsR_HistorialPagos> genericEntity = new GenericEntity<wsR_HistorialPagos>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    @GET
    @Path("HistorialPagos/{instancia}/{autenticacion}/{OperacionServicio}/CLIENTE/{cliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response HistorialPagos_Cliente(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("cliente") String Cliente) {
        //wsR_gBusqueda
        try {

            wsR_HistorialPagos vReturn =  new wsR_HistorialPagos();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.HistorialPagos ( Instancia, wA, OperacionServicio,  Cliente, "");

            GenericEntity<wsR_HistorialPagos> genericEntity = new GenericEntity<wsR_HistorialPagos>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }







    @GET
    @Path("InformacionClientes/{instancia}/{autenticacion}/{OperacionServicio}/TELEFONO/{telefono}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response InformacionClientes_Telefono(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("telefono") String Telefono) {
        //wsR_gBusqueda
        try {

            wsR_InformacionClientes vReturn =  new wsR_InformacionClientes();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.InformacionClientes ( Instancia, wA, OperacionServicio,  "", Telefono);

            GenericEntity<wsR_InformacionClientes> genericEntity = new GenericEntity<wsR_InformacionClientes>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    @GET
    @Path("InformacionClientes/{instancia}/{autenticacion}/{OperacionServicio}/CLIENTE/{cliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response InformacionClientes_Cliente(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("cliente") String Cliente) {
        //wsR_gBusqueda
        try {

            wsR_InformacionClientes vReturn =  new wsR_InformacionClientes();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.InformacionClientes ( Instancia, wA, OperacionServicio,  Cliente, "");

            GenericEntity<wsR_InformacionClientes> genericEntity = new GenericEntity<wsR_InformacionClientes>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }






    @GET
    @Path("AplicaVentaServicio/{instancia}/{autenticacion}/{OperacionServicio}/{telefono}/{monto}/{secuencia}/{codproducto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response AplicaVentaServicio(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("telefono") String Telefono,
            @PathParam("monto") Double Monto,
            @PathParam("secuencia") String SecuenciaExterna,
            @PathParam("codproducto") String CodProducto ) {
        //wsR_gBusqueda
        try {

            wsR_AplicaVentaServicio vReturn =  new wsR_AplicaVentaServicio();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.sAplicaVentaServicio ( Instancia, wA, OperacionServicio,  Telefono, Monto, SecuenciaExterna,CodProducto);

            GenericEntity<wsR_AplicaVentaServicio> genericEntity = new GenericEntity<wsR_AplicaVentaServicio>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }



    @GET
    @Path("AplicaVentaServicioFactura/{instancia}/{autenticacion}/{Cliente_Nombre}/{Cliente_Direccion}/{Cliente_Cedula}/{Cliente_Nit}/{Cliente_CorreoElectronico}/{telefono}/{monto}/{secuencia}/{codproducto}/{Pago_Forma}/{Pago_Tarjeta}/{Pago_Autorizacion}/{Pago_Vencimiento}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response AplicaVentaServicioFactura(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,

            @PathParam("Cliente_Nombre") String Cliente_Nombre ,
            @PathParam("Cliente_Direccion") String Cliente_Direccion ,
            @PathParam("Cliente_Cedula") String Cliente_Cedula ,
            @PathParam("Cliente_Nit") String Cliente_Nit ,
            @PathParam("Cliente_CorreoElectronico") String Cliente_CorreoElectronico ,

            @PathParam("telefono") String Telefono,
            @PathParam("monto") Double Monto,
            @PathParam("secuencia") String SecuenciaExterna,
            @PathParam("codproducto") String CodProducto,

            @PathParam("Pago_Forma") String Pago_Forma,
            @PathParam("Pago_Tarjeta") String Pago_Tarjeta,
            @PathParam("Pago_Autorizacion") String Pago_Autorizacion,
            @PathParam("Pago_Vencimiento") String Pago_Vencimiento
    ) {
        //wsR_gBusqueda
        try {

            wsR_AplicaVentaServicio vReturn =  new wsR_AplicaVentaServicio();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.sAplicaVentaServicioFactura ( Instancia, wA, Cliente_Nombre, Cliente_Direccion, Cliente_Cedula, Cliente_Nit, Cliente_CorreoElectronico,  Telefono, Monto, SecuenciaExterna, CodProducto , Pago_Forma, Pago_Tarjeta, Pago_Autorizacion, Pago_Vencimiento);

            GenericEntity<wsR_AplicaVentaServicio> genericEntity = new GenericEntity<wsR_AplicaVentaServicio>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }





    @GET
    @Path("sConsultaServicios/{instancia}/{autenticacion}/{OperacionServicio}/TELEFONO/{telefono}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sConsultaServicios_Telefono(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("telefono") String Telefono) {
        //wsR_gBusqueda
        try {

            wsR_gBusqueda vReturn =  new wsR_gBusqueda();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.sConsultaServicios ( Instancia, wA, OperacionServicio,  "", Telefono);

            GenericEntity<wsR_gBusqueda> genericEntity = new GenericEntity<wsR_gBusqueda>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    @GET
    @Path("sConsultaServicios/{instancia}/{autenticacion}/{OperacionServicio}/CLIENTE/{cliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sConsultaServicios_Cliente(
            @PathParam("instancia") wsInstancias.wsInstancia Instancia,
            @PathParam("autenticacion") String IDBanco,
            @PathParam("OperacionServicio")  wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @PathParam("cliente") String Cliente) {
        //wsR_gBusqueda
        try {

            wsR_gBusqueda vReturn =  new wsR_gBusqueda();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            wAutenticacion wA = new wAutenticacion();
            wA.vIDBanco = IDBanco;
            vReturn = wO.sConsultaServicios ( Instancia, wA, OperacionServicio,  Cliente, "");

            GenericEntity<wsR_gBusqueda> genericEntity = new GenericEntity<wsR_gBusqueda>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }






    @POST
    @Path("/sPagoServicios")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sPagos(wsAutogestion_sPagoServicios vDatos){
        try {
            wsS_PagosAutogestion vReturn =  new wsS_PagosAutogestion();
            WsServicios.OperacionesAutogestion.wsAutogestion wO = new WsServicios.OperacionesAutogestion.wsAutogestion();
            vReturn = wO.sPagoServicios( vDatos.Instancia, vDatos.Autenticacion, vDatos.IDBusquedaExterna, vDatos.OperacionServicio, vDatos.Operacion, vDatos.FormaPago );

            GenericEntity<wsS_PagosAutogestion> genericEntity = new GenericEntity<wsS_PagosAutogestion>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


}
