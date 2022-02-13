package WsServicios.Rest;

import FromNET.SubDataTable;
import WsServicios.Bases.wsInstancias;
import WsServicios.Bases.wsTipoAccionManttos;
import WsServicios.Entidades.Base.wsR_Agencia;
import WsServicios.Entidades.Base.wsR_Banco;
import WsServicios.Entidades.Base.wsR_Caja;
import WsServicios.Entidades.Base.wsR_Cajero;
import WsServicios.Entidades.Lista.wsR_Agencias;
import WsServicios.Entidades.Lista.wsR_Bancos;
import WsServicios.Entidades.Lista.wsR_Cajas;
import WsServicios.Entidades.Lista.wsR_Cajeros;
import db.BaseClass;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("wsEntidades")
public class wsEntidades extends BaseClass {


    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html><title>wsEntidades</title><body>wsEntidades</body></html>";
    }


    @GET
    @Path("gAgencias/{ins}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gAgencias(@PathParam("ins") wsInstancias.wsInstancia Instancia) {
        try {
            wsR_Agencias vReturn =  new wsR_Agencias();
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            vReturn = wE.gAgencias( Instancia );

            GenericEntity<wsR_Agencias> genericEntity = new GenericEntity<wsR_Agencias>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("gCajeros/{ins}/{agencia}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gCajeros(@PathParam("ins") wsInstancias.wsInstancia Instancia, @PathParam("agencia") String vAgencia) {
        try {
            wsR_Cajeros vReturn =  new wsR_Cajeros();
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            vReturn = wE.gCajeros( Instancia, vAgencia );

            GenericEntity<wsR_Cajeros> genericEntity = new GenericEntity<wsR_Cajeros>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @GET
    @Path("gCajas/{ins}/{agencia}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gCajas(@PathParam("ins") wsInstancias.wsInstancia Instancia, @PathParam("agencia") String vAgencia) {
        try {
            wsR_Cajas vReturn =  new wsR_Cajas();
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            vReturn = wE.gCajas( Instancia, vAgencia );

            GenericEntity<wsR_Cajas> genericEntity = new GenericEntity<wsR_Cajas>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("gBancos/{ins}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gBancos(@PathParam("ins") wsInstancias.wsInstancia Instancia) {
        try {
            wsR_Bancos vReturn =  new wsR_Bancos();
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            vReturn = wE.gBancos( Instancia );

            GenericEntity<wsR_Bancos> genericEntity = new GenericEntity<wsR_Bancos>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }



    @POST
    @Path("sAgencias")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sAgencias( wsEntidades_sAgencias vDatos ){
        try {
            SubDataTable vReturn =  new SubDataTable();
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            vReturn = wE.sAgencias( vDatos.Instancia, vDatos.vDatosAgencia, vDatos.vTipoAccion );

            GenericEntity<SubDataTable> genericEntity = new GenericEntity<SubDataTable>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("sAgencias_info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sAgencias_info(  ){
        try {
            wsEntidades_sAgencias vDatos =new  wsEntidades_sAgencias();
            vDatos.Instancia = wsInstancias.wsInstancia.ODA_503;
            vDatos.vDatosAgencia  = new wsR_Agencia();
            vDatos.vDatosAgencia.codAgencia = "CODIGO";
            vDatos.vDatosAgencia.nomAgencia = "NOMBRE";
            vDatos.vDatosAgencia.codSinergia = "SINERGIA";
            vDatos.vTipoAccion = wsTipoAccionManttos.wsTipoAccionMantto.A;

            GenericEntity<wsEntidades_sAgencias> genericEntity = new GenericEntity<wsEntidades_sAgencias>(vDatos){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @POST
    @Path("/sCajeros")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sCajeros(wsEntidades_sCajeros vDatos) {
        try {
            SubDataTable vReturn =  new SubDataTable();
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            vReturn = wE.sCajeros( vDatos.Instancia, vDatos.vDatosCajero, vDatos.vTipoAccion );

            GenericEntity<SubDataTable> genericEntity = new GenericEntity<SubDataTable>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/sCajas")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sCajas(wsEntidades_sCajas vDatos) {
        try {
            SubDataTable vReturn =  new SubDataTable();
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            vReturn = wE.sCajas( vDatos.Instancia, vDatos.vDatosCaja, vDatos.vTipoAccion );

            GenericEntity<SubDataTable> genericEntity = new GenericEntity<SubDataTable>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @POST
    @Path("/sBancos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sBancos(wsEntidades_sBancos vDatos) {
        try {
            SubDataTable vReturn =  new SubDataTable();
            WsServicios.Entidades.wsEntidades wE = new WsServicios.Entidades.wsEntidades();
            vReturn = wE.sBancos( vDatos.Instancia, vDatos.vDatosBanco, vDatos.vTipoAccion );

            GenericEntity<SubDataTable> genericEntity = new GenericEntity<SubDataTable>(vReturn){};
            return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }



}
