package WsServicios.Ordenes;

import WsServicios.Bases.wsInstancias;
import WsServicios.Ordenes.Base.*;
import WsServicios.Ordenes.Response.*;
import db.BaseClass;
import oracle.jdbc.OracleConnection;

import java.sql.CallableStatement;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import FromNET.ConnectTo;
import FromNET.GlobalDB;
import FromNET.SubDataTable;

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
    result.vEstado = 1;
    result.vMensaje = "OK::Consulta de existencias satisfactoria";
    result.Datos = new wsR_Generic_Result();
    result.Datos.Resultado = "-1";

    // obteniendo existencias según los parametros
    SubDataTable dt = new SubDataTable();
    GlobalDB db = new GlobalDB();
    OracleConnection _ODA;
    CallableStatement _cmd;

    // parameters
    // - cod_producto:varchar2
    // - cod_almacen:varchar2
    // - cod_agencia:varchar2
    // - cantidad:number:out:3
    // - estado:varchar2:out:12
    String vQuery = "{CALL pkg_sas_genera.consulta_existencia(?,?,?,?,?)}";

    try {
      // conexión
      _ODA = db.getODADBConnection(getConnectTo(Instancia));

      // procedimiento
      _cmd = _ODA.prepareCall(vQuery);
      // parametros
      _cmd.setString(1, vParametros.material);
      _cmd.setString(2, vParametros.codAlmacen);
      _cmd.setString(3, vParametros.codAgencia);
      _cmd.registerOutParameter(4, 3, 1000);
      _cmd.registerOutParameter(5, 12, 1000);

      // ejecutar parametros
      dt = db.setQuery(_cmd);
      if (dt.vData) {
        if (_cmd.getString(5).startsWith("ER")) {
          result.vEstado = 0;
          result.vMensaje = "Consulta de existencias con resultado [" + _cmd.getString(5) +"]";
        } else {
          result.vMensaje = result.vMensaje + "[" + _cmd.getString(5)  + "]";
          result.Datos.Resultado = _cmd.getLong(4) + "";
        }
        _cmd.close();
      } else {
        result.vEstado = 0;
        result.vMensaje = "Consulta de existencias no obtuvo resultados";

      }

    } catch (Exception e) {
      result.vEstado = -1;
      result.vMensaje = "Consulta de existencias error [" + e.getMessage() +"]";
    }


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
    result.vEstado = 1;
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
    result.vEstado = 1;
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
    result.vEstado = 1;
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
    result.vEstado = 1;
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
    result.vEstado = 1;
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
    result.vEstado = 1;
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
    result.vEstado = 1;
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
    result.vEstado = 1;
    result.vMensaje = "Ok";
    result.Datos = new wsR_Orden_Result();
    result.Datos.Resultado = "1";
    return result;

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

}
