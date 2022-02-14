package WsServicios.Ordenes;

import WsServicios.Bases.wsInstancias;
import WsServicios.Ordenes.Base.*;
import WsServicios.Ordenes.Response.*;
import db.BaseClass;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "wsOrdenes", targetNamespace = "")
public class wsOrdenes extends BaseClass {

    // *****************************************************************************
    // gExistencias
    // *****************************************************************************
    @WebMethod(operationName = "gExistencias")
    public wsR_Generic gExistencias(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "vParametros") wsRxml_gExistencias vParametros) {

        wsR_Generic result = new wsR_Generic();
        result.vEstado = 0;
        result.vMensaje = "Ok";
        result.Datos = new wsR_Generic_Result();
        result.Datos.Resultado = "1";
        return result;

    }

    // *****************************************************************************
    // sReservaSerie
    // *****************************************************************************
    @WebMethod(operationName = "sReservaSerie")
    public wsR_Generic sReservaSerie(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "vParametros") wsRxml_sReservaSerie vParametros) {

        wsR_Generic result = new wsR_Generic();
        result.vEstado = 0;
        result.vMensaje = "Ok";
        result.Datos = new wsR_Generic_Result();
        result.Datos.Resultado = "1";
        return result;

    }

    // *****************************************************************************
    // sLiberaSerie
    // *****************************************************************************
    @WebMethod(operationName = "sLiberaSerie")
    public wsR_Generic sLiberSerie(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "vParametros") wsRxml_sLiberaSerie vParametros) {

        wsR_Generic result = new wsR_Generic();
        result.vEstado = 0;
        result.vMensaje = "Ok";
        result.Datos = new wsR_Generic_Result();
        result.Datos.Resultado = "1";
        return result;

    }


    // *****************************************************************************
    // gConsultaInconformidad
    // *****************************************************************************
    @WebMethod(operationName = "gConsultaInconformidad")
    public wsR_Inconformidad gConsultaInconformidad(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "vInconformidad") String vInconformidad) {

        wsR_Inconformidad result = new wsR_Inconformidad();
        result.vEstado = 0;
        result.vMensaje = "Ok";
        result.Datos = new wsR_Inconformidad_Result();
        result.Datos.Contrato = "1";
        result.Datos.Monto = 100.00;
        result.Datos.Nivel = "1000";
        return result;

    }


    // *****************************************************************************
    // sActualizaInconformidad
    // *****************************************************************************
    @WebMethod(operationName = "sActualizaInconformidad")
    public wsR_Generic sActualizaInconformidad(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "vInconformidad") String vInconformidad) {

        wsR_Generic result = new wsR_Generic();
        result.vEstado = 0;
        result.vMensaje = "Ok";
        result.Datos = new wsR_Generic_Result();
        result.Datos.Resultado = "1";
        return result;

    }


    // *****************************************************************************
    // sAnulaPedido
    // *****************************************************************************
    @WebMethod(operationName = "sAnulaPedido")
    public wsR_Anula sAnulaPedido(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "vParametros") wsRxml_sAnulaPedido vParametros) {

        wsR_Anula result = new wsR_Anula();
        result.vEstado = 0;
        result.vMensaje = "Ok";
        result.Datos = new wsR_Anula_Result();
        result.Datos.Codigo = "1";
        result.Datos.Descripcion = "X";
        return result;

    }

    // *****************************************************************************
    // gConsultaEstadoVenta
    // *****************************************************************************
    @WebMethod(operationName = "gConsultaEstadoVenta")
    public wsR_Anula gConsultaEstadoVenta(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "vOrden") String vOrden) {

        wsR_Anula result = new wsR_Anula();
        result.vEstado = 0;
        result.vMensaje = "Ok";
        result.Datos = new wsR_Anula_Result();
        result.Datos.Codigo = "1";
        result.Datos.Descripcion = "X";
        return result;

    }


    // *****************************************************************************
    // gConsultaEstadoAnulacion
    // *****************************************************************************
    @WebMethod(operationName = "gConsultaEstadoAnulacion")
    public wsR_Anula gConsultaEstadoAnulacion(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "vOrden") String vOrden) {

        wsR_Anula result = new wsR_Anula();
        result.vEstado = 0;
        result.vMensaje = "Ok";
        result.Datos = new wsR_Anula_Result();
        result.Datos.Codigo = "1";
        result.Datos.Descripcion = "X";
        return result;

    }


    // *****************************************************************************
    // sInsertaOrden
    // *****************************************************************************
    @WebMethod(operationName = "sInsertaOrden")
    public wsR_Orden sInsertaOrden(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "vOrden") wsRxml_sInsertaOrden vOrden) {

        wsR_Orden result = new wsR_Orden();
        result.vEstado = 0;
        result.vMensaje = "Ok";
        result.Datos = new wsR_Orden_Result();
        result.Datos.Resultado = "1";
        return result;

    }


}
