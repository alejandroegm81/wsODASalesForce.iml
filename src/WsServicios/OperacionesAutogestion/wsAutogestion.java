package WsServicios.OperacionesAutogestion;

import FromNET.ConnectTo;
import FromNET.GlobalDB;
import FromNET.GlobalRQ;
import FromNET.SubDataTable;
import WsServicios.Bases.wGenInstanceFuntions;
import WsServicios.Bases.wsInstancias;
import WsServicios.Operaciones.Base.*;
import WsServicios.Operaciones.Lista.wsR_info_RegistroTransacciones;
import WsServicios.Operaciones.Lista.wsS_RegistroOperaciones;
import WsServicios.Operaciones.Oper.wsOperaciones_Apply;
import WsServicios.Operaciones.Oper.wsOperaciones_FIJO;
import WsServicios.Operaciones.Oper.wsOperaciones_MOVIL;
import WsServicios.OperacionesAutogestion.proceso.*;
import WsServicios.elk.toElk;
import db.BaseClass;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.math.BigDecimal;
import java.util.Date;


@WebService(serviceName = "wsAutogestion", targetNamespace = "")
public class wsAutogestion extends BaseClass {
    public Date vProcIni=new Date();
    public Date vProcFin=new Date();
    public static toElk gElk = new toElk();


    @WebMethod(operationName = "HistoricoFacturas")
    public wsR_HistoricoFacturas HistoricoFacturas(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,
            @WebParam(name = "OperacionServicio") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @WebParam(name = "Cliente_Contrato") String Cliente_Contrato,
            @WebParam(name = "Telefono") String Telefono) {


        wsR_HistoricoFacturas vReturn = new wsR_HistoricoFacturas();
        String vQuery="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="";
        Boolean isValid=false;
        vProcIni = new Date();
        try{
            isValid= isValidUser( Autenticacion, Instancia );
            if(isValid==false){throw  new Exception("Problema de autenticacion, usuario invalido.");}
            if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.MOVIL ){
                wsAutogestionOperacionMovil MOVIL = new wsAutogestionOperacionMovil();
                vReturn = MOVIL.HistoricoFacturas( Instancia, Autenticacion, OperacionServicio, wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.HISTORICO_FACTURA,  Cliente_Contrato, Telefono);
                MOVIL = null;
            }
            /*if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.FIJO ){
                wsOperaciones_FIJO FIJO = new wsOperaciones_FIJO();
                vReturn = FIJO.Busqueda( Instancia, Autenticacion, OperacionServicio, Cliente_Contrato, Telefono, NPE, Identificacion );
                FIJO = null;
            }*/
            //ClienteFijo
            ///vReturn = FIJO.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.FIJO, Telefono,"" , "","" );
            //if(vReturn.vEstado!=1){
            //    //ClienteMovil
            //    vReturn = MOVIL.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.MOVIL, Telefono, "", "", "" );
            //}

        }catch(Exception eM){ vReturn.vEstado = -1; vReturn.vMensaje = eM.getMessage(); }
        vProcFin = new Date();
        gElk.SendToElk( Instancia, Autenticacion, OperacionServicio, "Operaciones.gBusqueda", vProcIni, vProcFin,  vReturn.vEstado, vReturn.vMensaje,
                "1", "1", "0","0","0","0","0","0", "" );
        return vReturn;
    }




    @WebMethod(operationName = "ConsultaSaldos")
    public wsR_ConsultaSaldos ConsultaSaldos(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,
            @WebParam(name = "OperacionServicio") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @WebParam(name = "Cliente_Contrato") String Cliente_Contrato,
            @WebParam(name = "Telefono") String Telefono) {


        wsR_ConsultaSaldos vReturn = new wsR_ConsultaSaldos();
        String vQuery="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="";
        Boolean isValid=false;
        vProcIni = new Date();
        try{
            isValid= isValidUser( Autenticacion, Instancia );
            if(isValid==false){throw  new Exception("Problema de autenticacion, usuario invalido.");}
            if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.MOVIL ){
                wsAutogestionOperacionMovil MOVIL = new wsAutogestionOperacionMovil();
                vReturn = MOVIL.ConsultaSaldos( Instancia, Autenticacion, OperacionServicio, wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.CONSULTA_SALDO,  Cliente_Contrato, Telefono);
                MOVIL = null;
            }
            /*if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.FIJO ){
                wsOperaciones_FIJO FIJO = new wsOperaciones_FIJO();
                vReturn = FIJO.Busqueda( Instancia, Autenticacion, OperacionServicio, Cliente_Contrato, Telefono, NPE, Identificacion );
                FIJO = null;
            }*/
            //ClienteFijo
            ///vReturn = FIJO.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.FIJO, Telefono,"" , "","" );
            //if(vReturn.vEstado!=1){
            //    //ClienteMovil
            //    vReturn = MOVIL.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.MOVIL, Telefono, "", "", "" );
            //}

        }catch(Exception eM){ vReturn.vEstado = -1; vReturn.vMensaje = eM.getMessage(); }
        vProcFin = new Date();
        gElk.SendToElk( Instancia, Autenticacion, OperacionServicio, "Operaciones.gBusqueda", vProcIni, vProcFin,  vReturn.vEstado, vReturn.vMensaje,
                "1", "1", "0","0","0","0","0","0", "" );
        return vReturn;
    }





    @WebMethod(operationName = "ValidacionServicios")
    public wsR_ValidacionServicios ValidacionServicios(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,
            @WebParam(name = "OperacionServicio") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @WebParam(name = "Cliente_Contrato") String Cliente_Contrato,
            @WebParam(name = "Telefono") String Telefono ,
            @WebParam(name = "Identificacion") String Identificacion ) {


        wsR_ValidacionServicios vReturn = new wsR_ValidacionServicios();
        String vQuery="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="";
        Boolean isValid=false;
        vProcIni = new Date();
        try{
            isValid= isValidUser( Autenticacion, Instancia );
            if(isValid==false){throw  new Exception("Problema de autenticacion, usuario invalido.");}
            if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.MOVIL ){
                wsAutogestionOperacionMovil MOVIL = new wsAutogestionOperacionMovil();
                vReturn = MOVIL.ValidacionServicios( Instancia, Autenticacion, OperacionServicio, wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.VALIDACION_SERVICIO,  Cliente_Contrato, Telefono, Identificacion);
                MOVIL = null;
            }
            /*if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.FIJO ){
                wsOperaciones_FIJO FIJO = new wsOperaciones_FIJO();
                vReturn = FIJO.Busqueda( Instancia, Autenticacion, OperacionServicio, Cliente_Contrato, Telefono, NPE, Identificacion );
                FIJO = null;
            }*/
            //ClienteFijo
            ///vReturn = FIJO.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.FIJO, Telefono,"" , "","" );
            //if(vReturn.vEstado!=1){
            //    //ClienteMovil
            //    vReturn = MOVIL.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.MOVIL, Telefono, "", "", "" );
            //}

        }catch(Exception eM){ vReturn.vEstado = -1; vReturn.vMensaje = eM.getMessage(); }
        vProcFin = new Date();
        gElk.SendToElk( Instancia, Autenticacion, OperacionServicio, "Operaciones.gBusqueda", vProcIni, vProcFin,  vReturn.vEstado, vReturn.vMensaje,
                "1", "1", "0","0","0","0","0","0", "" );
        return vReturn;
    }



    //Riesgos de claro guatemala

    @WebMethod(operationName = "HistorialPagos")
    public wsR_HistorialPagos HistorialPagos(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,
            @WebParam(name = "OperacionServicio") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @WebParam(name = "Cliente_Contrato") String Cliente_Contrato,
            @WebParam(name = "Telefono") String Telefono ) {


        wsR_HistorialPagos vReturn = new wsR_HistorialPagos();
        String vQuery="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="";
        Boolean isValid=false;
        vProcIni = new Date();
        try{
            isValid= isValidUser( Autenticacion, Instancia );
            if(isValid==false){throw  new Exception("Problema de autenticacion, usuario invalido.");}
            if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.MOVIL ){
                wsAutogestionOperacionMovil MOVIL = new wsAutogestionOperacionMovil();
                vReturn = MOVIL.HistorialPagos( Instancia, Autenticacion, OperacionServicio, wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.HISTORIAL_PAGO,  Cliente_Contrato, Telefono);
                MOVIL = null;
            }
            /*if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.FIJO ){
                wsOperaciones_FIJO FIJO = new wsOperaciones_FIJO();
                vReturn = FIJO.Busqueda( Instancia, Autenticacion, OperacionServicio, Cliente_Contrato, Telefono, NPE, Identificacion );
                FIJO = null;
            }*/
            //ClienteFijo
            ///vReturn = FIJO.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.FIJO, Telefono,"" , "","" );
            //if(vReturn.vEstado!=1){
            //    //ClienteMovil
            //    vReturn = MOVIL.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.MOVIL, Telefono, "", "", "" );
            //}

        }catch(Exception eM){ vReturn.vEstado = -1; vReturn.vMensaje = eM.getMessage(); }
        vProcFin = new Date();
        gElk.SendToElk( Instancia, Autenticacion, OperacionServicio, "Operaciones.gBusqueda", vProcIni, vProcFin,  vReturn.vEstado, vReturn.vMensaje,
                "1", "1", "0","0","0","0","0","0", "" );
        return vReturn;
    }





    @WebMethod(operationName = "InformacionClientes")
    public wsR_InformacionClientes InformacionClientes(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,
            @WebParam(name = "OperacionServicio") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @WebParam(name = "Cliente_Contrato") String Cliente_Contrato,
            @WebParam(name = "Telefono") String Telefono ) {


        wsR_InformacionClientes vReturn = new wsR_InformacionClientes();
        String vQuery="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="";
        Boolean isValid=false;
        vProcIni = new Date();
        try{
            isValid= isValidUser( Autenticacion, Instancia );
            if(isValid==false){throw  new Exception("Problema de autenticacion, usuario invalido.");}
            if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.MOVIL ){
                wsAutogestionOperacionMovil MOVIL = new wsAutogestionOperacionMovil();
                vReturn = MOVIL.InformacionClientes( Instancia, Autenticacion, OperacionServicio, wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.INFO_CLIENTE,  Cliente_Contrato, Telefono);
                MOVIL = null;
            }
            /*if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.FIJO ){
                wsOperaciones_FIJO FIJO = new wsOperaciones_FIJO();
                vReturn = FIJO.Busqueda( Instancia, Autenticacion, OperacionServicio, Cliente_Contrato, Telefono, NPE, Identificacion );
                FIJO = null;
            }*/
            //ClienteFijo
            ///vReturn = FIJO.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.FIJO, Telefono,"" , "","" );
            //if(vReturn.vEstado!=1){
            //    //ClienteMovil
            //    vReturn = MOVIL.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.MOVIL, Telefono, "", "", "" );
            //}

        }catch(Exception eM){ vReturn.vEstado = -1; vReturn.vMensaje = eM.getMessage(); }
        vProcFin = new Date();
        gElk.SendToElk( Instancia, Autenticacion, OperacionServicio, "Operaciones.gBusqueda", vProcIni, vProcFin,  vReturn.vEstado, vReturn.vMensaje,
                "1", "1", "0","0","0","0","0","0", "" );
        return vReturn;
    }




    @WebMethod(operationName = "sAplicaVentaServicio")
    public wsR_AplicaVentaServicio sAplicaVentaServicio(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,
            @WebParam(name = "OperacionServicio") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @WebParam(name = "Telefono") String Telefono ,
            @WebParam(name = "Monto") Double Monto ,
            @WebParam(name = "SecuenciaExterna") String SecuenciaExterna ,
            @WebParam(name = "CodProducto") String CodProducto
    ) {


        wsR_AplicaVentaServicio vReturn = new wsR_AplicaVentaServicio();
        Boolean isValid=false;
        vProcIni = new Date();
        try{
            isValid= isValidUser( Autenticacion, Instancia );
            if(isValid==false){throw  new Exception("Problema de autenticacion, usuario invalido.");}
            if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.REGISTRO_VENTA ){
                wsAutogestionOperacionMovil MOVIL = new wsAutogestionOperacionMovil();
                vReturn = MOVIL.AplicaVentaServicio( Instancia, Autenticacion, OperacionServicio, Telefono, Monto, SecuenciaExterna, CodProducto);
                MOVIL = null;
            }
        }catch(Exception eM){ vReturn.vEstado = -1; vReturn.vMensaje = eM.getMessage(); }
        vProcFin = new Date();
        gElk.SendToElk( Instancia, Autenticacion, OperacionServicio, "wsAutogestion.AplicaVentaServicio", vProcIni, vProcFin,  vReturn.vEstado, vReturn.vMensaje,
                "1", "0", "0","0","0","0","0","0", "" );
        return vReturn;
    }


        @WebMethod(operationName = "sAplicaVentaServicioFactura")
    public wsR_AplicaVentaServicio sAplicaVentaServicioFactura(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,

            @WebParam(name = "Cliente_Nombre") String Cliente_Nombre ,
            @WebParam(name = "Cliente_Direccion") String Cliente_Direccion ,
            @WebParam(name = "Cliente_Cedula") String Cliente_Cedula ,
            @WebParam(name = "Cliente_Nit") String Cliente_Nit ,
            @WebParam(name = "Cliente_CorreoElectronico") String Cliente_CorreoElectronico ,

            @WebParam(name = "Telefono") String Telefono ,
            @WebParam(name = "Monto") Double Monto ,
            @WebParam(name = "SecuenciaExterna") String SecuenciaExterna ,
            @WebParam(name = "CodProducto") String CodProducto,

            @WebParam(name = "Pago_Forma") String Pago_Forma,
            @WebParam(name = "Pago_Tarjeta") String Pago_Tarjeta,
            @WebParam(name = "Pago_Autorizacion") String Pago_Autorizacion,
            @WebParam(name = "Pago_Vencimiento") String Pago_Vencimiento ) {

        wsR_AplicaVentaServicio vReturn = new wsR_AplicaVentaServicio();
        wsOperacionServicios.wsOperacionServicio OperacionServicio = wsOperacionServicios.wsOperacionServicio.REGISTRO_VENTA;

        Boolean isValid=false;
        vProcIni = new Date();
        try{
            isValid= isValidUser( Autenticacion, Instancia );
            if(isValid==false){throw  new Exception("Problema de autenticacion, usuario invalido.");}
            wsAutogestionOperacionMovil MOVIL = new wsAutogestionOperacionMovil();
            vReturn = MOVIL.AplicaVentaServicioFactura( Instancia, Autenticacion, Cliente_Nombre, Cliente_Direccion, Cliente_Cedula, Cliente_Nit, Cliente_CorreoElectronico,  Telefono, Monto, SecuenciaExterna, CodProducto , Pago_Forma, Pago_Tarjeta, Pago_Autorizacion, Pago_Vencimiento);
            MOVIL = null;
        }catch(Exception eM){ vReturn.vEstado = -1; vReturn.vMensaje = eM.getMessage(); }
        vProcFin = new Date();
        gElk.SendToElk( Instancia, Autenticacion, OperacionServicio, "wsAutogestion.AplicaVentaServicioFactura", vProcIni, vProcFin,  vReturn.vEstado, vReturn.vMensaje,
                "1", "0", "0","0","0","0","0","0", "" );
        return vReturn;
    }




    @WebMethod(operationName = "sConsultaServicios")
    public wsR_gBusqueda sConsultaServicios(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,
            @WebParam(name = "OperacionServicio") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @WebParam(name = "Cliente_Contrato") String Cliente_Contrato,
            @WebParam(name = "Telefono") String Telefono) {
        wsR_gBusqueda vReturn = new wsR_gBusqueda();
        String vQuery="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="";
        Boolean isValid=false;
        vProcIni = new Date();
        try{
            isValid= isValidUser( Autenticacion, Instancia );
            if(isValid==false){throw  new Exception("Problema de autenticacion, usuario invalido.");}
            if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.MOVIL ){
                wsOperaciones_MOVIL MOVIL = new wsOperaciones_MOVIL();
                vReturn = MOVIL.Busqueda( Instancia, Autenticacion, OperacionServicio, Cliente_Contrato, Telefono, "", "" );
                MOVIL = null;
            }
            if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.FIJO ){
                wsOperaciones_FIJO FIJO = new wsOperaciones_FIJO();
                vReturn = FIJO.Busqueda( Instancia, Autenticacion, OperacionServicio, Cliente_Contrato, Telefono, "", "" );
                FIJO = null;
            }
            //Metodo de Busqueda de Servicio, solo operara con Telefono
            if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.BUSQUEDA_SERVICIO ){
                if(Cliente_Contrato.length()>0){throw new Exception("Busqueda de Servicio, solo utiliza el valor del campo Telefono");}
                //Si el valor es de 8, Iniciara busqueda como telefono Movil
                wsOperaciones_MOVIL MOVIL = new wsOperaciones_MOVIL();
                wsOperaciones_FIJO FIJO = new wsOperaciones_FIJO();
                if(Telefono.length()==8){
                    vReturn = MOVIL.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.MOVIL, "", Telefono, "", "" );
                    if(vReturn.vEstado!=1){
                        vReturn = FIJO.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.FIJO, "", Telefono, "","" );
                        if(vReturn.vEstado!=1){
                            vReturn = MOVIL.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.MOVIL, Telefono, "", "", "" );
                            if(vReturn.vEstado!=1){
                                vReturn = FIJO.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.FIJO, Telefono,"" , "","" );
                            }
                        }
                    }
                }else{
                    //ClienteFijo
                    vReturn = FIJO.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.FIJO, Telefono,"" , "","" );
                    if(vReturn.vEstado!=1){
                        //ClienteMovil
                        vReturn = MOVIL.Busqueda( Instancia, Autenticacion, wsOperacionServicios.wsOperacionServicio.MOVIL, Telefono, "", "", "" );
                    }
                }
                MOVIL = null;
                FIJO = null;
            }

        }catch(Exception eM){ vReturn.vEstado = -1; vReturn.vMensaje = eM.getMessage(); }
        vProcFin = new Date();
        gElk.SendToElk( Instancia, Autenticacion, OperacionServicio, "wsAutogestion.sConsulta", vProcIni, vProcFin,  vReturn.vEstado, vReturn.vMensaje,
                "1", "1", "0","0","0","0","0","0", "" );
        return vReturn;
    }


    @WebMethod(operationName = "sPagoServicios")
    public wsS_PagosAutogestion sPagoServicios(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,
            @WebParam(name = "IDBusquedaExterna") int IDBusquedaExterna,
            @WebParam(name = "OperacionServicio") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @WebParam(name = "Operacion") wsS_RegistroOperacion Operacion,
            @WebParam(name = "FormaPago") wsS_FormaPagoAutogestion FormaPago
            ) {
        wsR_sPagos vReturnPagos = new wsR_sPagos();
        wsS_PagosAutogestion  vReturn = new wsS_PagosAutogestion();
        Boolean isValid=false;
        vProcIni = new Date();
        try{
            isValid= isValidUser( Autenticacion, Instancia );
            if(isValid==false){throw  new Exception("Usuario invalido");}

            wsS_RegistroInfoCliente InfoCliente = new wsS_RegistroInfoCliente();
            wsS_RegistroFormaPago FormaPagoLocal = new wsS_RegistroFormaPago();
            wsS_RegistroOperaciones Operaciones = new wsS_RegistroOperaciones();
            FormaPagoLocal.TipoFormaPago = FormaPago.TipoFormaPago;
            FormaPagoLocal.T_Numero_Tarjeta = FormaPago.T_Numero_Tarjeta;
            FormaPagoLocal.T_CodAutorizacion = FormaPago.T_CodAutorizacion;
            FormaPagoLocal.MontoPago = Operacion.MontoPago;

            Operaciones.Datos.add( Operacion );

            wsOperaciones_Apply wApp = new wsOperaciones_Apply();
            vReturnPagos = wApp.SetPayments( Instancia,  Autenticacion, IDBusquedaExterna, OperacionServicio, InfoCliente, Operaciones, FormaPagoLocal);
            vReturn.vEstado = vReturnPagos.vEstado;
            vReturn.vMensaje = vReturnPagos.vMensaje;
            vReturn.vIDPago = vReturnPagos.vIDPago;
            vReturn.vID = vReturnPagos.vID;

        }catch(Exception eM){ vReturn.vEstado = -1; vReturn.vMensaje = eM.getMessage(); }
        vProcFin = new Date();
        gElk.SendToElk( Instancia, Autenticacion, OperacionServicio, "Operaciones.sPagos", vProcIni, vProcFin,  vReturn.vEstado, vReturn.vMensaje,
                "1", "1", "0","0","0","0","0","0", FormaPago.TipoFormaPago.toString() );

        return vReturn;
    }


    @WebMethod(operationName = "sAnulacionPagoServicios")
    public wsS_PagosAutogestion sAnulacionPagoServicios(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,
            @WebParam(name = "OperacionServicio") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @WebParam(name = "IDPago") int IDPago,
            @WebParam(name = "Comentario") String Comentario) {
        wsR_sPagos vReturnPagos = new wsR_sPagos();
        wsS_PagosAutogestion vReturn = new wsS_PagosAutogestion();
        Boolean isValid=false;
        vProcIni = new Date();
        try{
            isValid= isValidUser( Autenticacion, Instancia );
            if(isValid==false){throw  new Exception("Usuario invalido");}

            wsOperaciones_Apply wApp = new wsOperaciones_Apply();
            vReturnPagos = wApp.sAnulacion( Instancia,  Autenticacion, OperacionServicio, IDPago, Comentario);
            vReturn.vEstado = vReturnPagos.vEstado;
            vReturn.vMensaje = vReturnPagos.vMensaje;
            vReturn.vIDPago = vReturnPagos.vIDPago;
            vReturn.vID = vReturnPagos.vID;

        }catch(Exception eM){ vReturn.vEstado = -1; vReturn.vMensaje = eM.getMessage(); }
        vProcFin = new Date();
        gElk.SendToElk( Instancia, Autenticacion, OperacionServicio, "Operaciones.sAnulacion", vProcIni, vProcFin,  vReturn.vEstado, vReturn.vMensaje,
                "1", "1", "0","0","0","0","0","0", "" );
        return vReturn;
    }


    @WebMethod(operationName = "sReportePagosServicios")
    public wsR_info_RegistroTransacciones sReportePagosServicios(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,
            @WebParam(name = "OperacionServicio") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @WebParam(name = "FechaOperacion") String FechaOperacion) {
        wsR_info_RegistroTransacciones vReturn = new wsR_info_RegistroTransacciones();
        Boolean isValid=false;
        vProcIni = new Date();
        try{
            isValid= isValidUser( Autenticacion, Instancia );
            if(isValid==false){throw  new Exception("Usuario invalido");}

            SubDataTable dt = new SubDataTable();
            GlobalDB db = new GlobalDB();

            String vQuery="", vQueryParameter_a="", vQueryParameter_b="",vQueryParameter_c="";
            GlobalRQ vgQReporte = new GlobalRQ(); vQuery = vgQReporte.vQuery;


            if( FechaOperacion.length()>0 ){
                if(FechaOperacion.indexOf("/")>0){
                    vQueryParameter_a="a.fec_real_pago= to_date('" + FechaOperacion + "','dd/MM/yyyy')";
                }else{
                    vQueryParameter_a="a.fec_real_pago= to_date('" + FechaOperacion + "','ddMMyyyy')";
                }
            }
            if( OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL )  ){ vQueryParameter_b="a.cod_operador in (select cod_operador from operadores where sap_entrycode='INPER' ) ";  }
            if( OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.FIJO )  ){ vQueryParameter_b ="a.cod_operador in (select cod_operador from operadores where sap_entrycode='INGAR' )";  }
            if( OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.REGISTRO_VENTA )  ){ vQueryParameter_b="a.cod_operador between 50";  }
            vQueryParameter_c =" AND a.cod_agencia in (select cod_agencia from list_banks where code_bank='" + Autenticacion.vIDBanco + "' )";

            vQuery = vQuery.replace("{}", vQueryParameter_a + ((vQueryParameter_a.length()>0 && vQueryParameter_b.length()>0)?" and ":"")  +vQueryParameter_b + vQueryParameter_c  );
            dt = db.getQuery(vQuery, getConnectTo(Instancia));
            if(dt.vData && dt.vRows>1) {
                for (int i = 1; i < dt.vRows; i++) {
                    wsR_info_RegistroTransaccion vRec = new wsR_info_RegistroTransaccion();
                    vRec.vID = dt.Datos.rows.get(i).getColByName("ID").value_int;
                    vRec.vIDPago = dt.Datos.rows.get(i).getColByName("IDPAGO").value_int;
                    vRec.vEstado = ( dt.Datos.rows.get(i).getColByName("ESTATUS_TRANSAC").value_string.equals("A") )?"ANULADO":"PAGADO";
                    vRec.FechaTransaccion = getString(dt.Datos.rows.get(i).getColByName("FECHA").value_string);
                    vRec.HoraTransaccion = getString(dt.Datos.rows.get(i).getColByName("HORA").value_string);
                    vRec.OperacionServicio = getEquivOperacion(dt.Datos.rows.get(i).getColByName("COD_OPERADOR").value_string);
                    vRec.Cliente_Contrato = getString(dt.Datos.rows.get(i).getColByName("CLIENTE").value_string);
                    vRec.Telefono = getString(dt.Datos.rows.get(i).getColByName("TELEFONO").value_string);
                    vRec.IDDocumento = getString(dt.Datos.rows.get(i).getColByName("IDDOCUMENTO").value_string);
                    vRec.NoDocumento = getString( dt.Datos.rows.get(i).getColByName("NODOCUMENTO").value_string);
                    vRec.Referencia = getString(dt.Datos.rows.get(i).getColByName("REFERENCIA").value_string);
                    vRec.vIDBusquedaExterno = getString(dt.Datos.rows.get(i).getColByName("SEC_BANCO").value_string);
                    vRec.TipoDocumento =  wsTipoDocumentos.wsTipoDocumento.FACTURA; // dt.Datos.rows.get(i).getColByName("TIPODOCUMENTO").value_string;
                    vRec.TipoFormaPago =getEquivFormaPago( dt.Datos.rows.get(i).getColByName("TIP_PAG_TRAN").value_string);
                    vRec.C_CodBanco = getString(dt.Datos.rows.get(i).getColByName("COD_BANCO").value_string);
                    vRec.C_NumeroCuenta = getString(dt.Datos.rows.get(i).getColByName("NUMEROCUENTA").value_string);
                    vRec.C_NumeroCheque = getString(dt.Datos.rows.get(i).getColByName("NUMEROCHEQUE").value_string);
                    vRec.T_AplicarTranTarjetaEnLinea = dt.Datos.rows.get(i).getColByName("ENLINEA").value_int;
                    vRec.T_Numero_Tarjeta = getString(dt.Datos.rows.get(i).getColByName("NUM_TARJETA").value_string);
                    vRec.T_Vencimiento = getString(dt.Datos.rows.get(i).getColByName("FECHA_VENCIMIENTO").value_string);
                    vRec.T_CVC = getString(dt.Datos.rows.get(i).getColByName("CVC").value_string);
                    vRec.T_CodAutorizacion =getString(dt.Datos.rows.get(i).getColByName("COD_AUTORIZA").value_string);
                    vRec.MontoPago = dt.Datos.rows.get(i).getColByName("TOTAL_PAGAR").value_double;
                    vReturn.Datos.add(vRec);
                    vReturn.vEstado=1;
                }
            }

        }catch(Exception eM){ vReturn.vEstado = -1; vReturn.vMensaje = eM.getMessage(); }
        vProcFin = new Date();
        gElk.SendToElk( Instancia, Autenticacion, OperacionServicio, "Operaciones.sReporteTransacciones", vProcIni, vProcFin,  vReturn.vEstado, vReturn.vMensaje,
                "1", "0", "0","0","0","0","0","0", "" );

        return vReturn;
    }




/*
    public void SendToElk(wsInstancias.wsInstancia Instancia, wAutenticacion Autenticacion, wsOperacionServicios.wsOperacionServicio OperacionServicio ,
                          String OperacionWs, Date vIni, Date vFin, String vEstado, String vMensajeGlobal,
                          String vODA, String vBSCS, String vFU, String vOpen, String vGaia, String vPisa, String vTecnomen, String vEsb, String vFormaPago   ){
*/










    private String getCountryCode(wsInstancias.wsInstancia Instancia){
        String vCode="";
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_502 )){ vCode="502"; }
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_503 )){ vCode="503"; }
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_504 )){ vCode="504"; }
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_505 )){ vCode="505"; }
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_506 )){ vCode="506"; }
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_507 )){ vCode="507"; }
        return vCode;
    }
    private ConnectTo getConnectTo(wsInstancias.wsInstancia vInstancia) throws Exception {
        if (vInstancia == null) throw new Exception("La Instancia es invalida. Debe de ser ODA_{pais}");
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_502)) return ConnectTo.ODA_502_db;
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_503)) return ConnectTo.ODA_503_db;
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_504)) return ConnectTo.ODA_504_db;
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_505)) return ConnectTo.ODA_505_db;
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_506)) return ConnectTo.ODA_506_db;
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_507)) return ConnectTo.ODA_507_db;
        throw new Exception("La Instancia es invalida. Debe de ser ODA_{pais}");
    }
    private Boolean isValidUser( wAutenticacion Autenticacion, wsInstancias.wsInstancia Instancia){
        Boolean vReturn=false;
        try{
            wGenInstanceFuntions wG = new wGenInstanceFuntions();
            String vCountry = wG.getCountryCode(Instancia); String vQuery="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="";
            if(vCountry.equals("507")){
                vQuery="select 1 from agencias_bancos where {}";
                if( Autenticacion.vIDBanco.length() > 0 ){ vQueryParameter_a +=" cod_agencia_banco='" + Autenticacion.vIDBanco + "'"; }
                if( Autenticacion.vUsuario.length() > 0 ){ vQueryParameter_b +=" conf_cod_cajer0='" + Autenticacion.vUsuario + "'"; }
                if( Autenticacion.vContrasena.length() > 0 ){ vQueryParameter_c +=" conf_cod_cajero_password='" + Autenticacion.vContrasena + "'"; }
            }else{
                vQuery="select 1 from list_banks where {}";
                if( Autenticacion.vIDBanco.length() > 0 ){ vQueryParameter_a +=" code_bank='" + Autenticacion.vIDBanco + "'"; }
                if( Autenticacion.vUsuario.length() > 0 ){ vQueryParameter_b +=" user_name='" + Autenticacion.vUsuario + "'"; }
                if( Autenticacion.vContrasena.length() > 0 ){ vQueryParameter_c +=" pass='" + Autenticacion.vContrasena + "'"; }
            }

            GlobalDB db = new GlobalDB();
            SubDataTable dt = new SubDataTable();
            vQuery = vQuery.replace("{}", vQueryParameter_a + ((vQueryParameter_a.length()>0 && vQueryParameter_b.length()>0)?" and ":"")  +vQueryParameter_b +  ((vQueryParameter_b.length()>0 && vQueryParameter_c.length()>0)?" and ":"") +  vQueryParameter_c );
            dt = db.getQuery(  vQuery,  getConnectTo(Instancia) );
            if(dt.vData && dt.vRows>1){ vReturn=true; }
        }catch(Exception e ){  }
        return vReturn;
    }
    private wsTipoFormaPagos.wsTipoFormaPago getEquivFormaPago( String Val ){
        wsTipoFormaPagos.wsTipoFormaPago vTipo = wsTipoFormaPagos.wsTipoFormaPago.EFECTIVO;
        if( Val.equals("E") ){ vTipo = wsTipoFormaPagos.wsTipoFormaPago.EFECTIVO; }
        if( Val.equals("C") ){ vTipo = wsTipoFormaPagos.wsTipoFormaPago.CHEQUE; }
        if( Val.equals("T") ){ vTipo = wsTipoFormaPagos.wsTipoFormaPago.TARJETA; }
        return vTipo;
    }
    private wsOperacionServicios.wsOperacionServicio getEquivOperacion( String Val ){
        wsOperacionServicios.wsOperacionServicio vOperacion = wsOperacionServicios.wsOperacionServicio.OPERACIONES_VARIAS;
        int CodOperador = Integer.valueOf( Val );
        if( CodOperador>= 995 && CodOperador<=998 ){ vOperacion = wsOperacionServicios.wsOperacionServicio.MOVIL; }
        if( CodOperador>= 555 && CodOperador<=558 ){ vOperacion = wsOperacionServicios.wsOperacionServicio.FIJO; }
        if( CodOperador==50 ){ vOperacion = wsOperacionServicios.wsOperacionServicio.REGISTRO_VENTA; }
        return vOperacion;
    }
    private String getString( String value ){
        return (value==null)?"":value;
    }
}

