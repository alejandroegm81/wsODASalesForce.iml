package WsServicios.Ordenes;

import WsServicios.Bases.wsInstancias;
import WsServicios.Ordenes.Base.*;
import WsServicios.Ordenes.Response.*;
import db.BaseClass;
import oracle.jdbc.OracleConnection;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

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
    OracleConnection _ODA = null;
    CallableStatement _cmd = null;

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
      } else {
        result.vEstado = 0;
        result.vMensaje = "Consulta de existencias no obtuvo resultados";

      }

    } catch (Exception e) {
      result.vEstado = -1;
      result.vMensaje = "Consulta de existencias error [" + e.getMessage() +"]";
    } finally {
      try { _cmd.close();} catch (Exception ignored) {}
      try { _ODA.close();} catch (Exception ignored) {}
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
    result.vMensaje = "OK::Reserva de series satisfactoria";
    result.Datos = new wsR_Generic_Result();
    result.Datos.Resultado = "-1";


    // obteniendo existencias según los parametros
    SubDataTable dt = new SubDataTable();
    GlobalDB db = new GlobalDB();
    OracleConnection _ODA = null;
    CallableStatement _cmd = null;

    // parameters
    // - orden:varchar2
    // - serie:varchar2
    // - cod_producto:varchar2
    // - almacen:varchar2
    // - agencia:varchar2
    // - resultado:varchar2:out:12
    String vQuery = "{CALL pkg_sas_genera.reserva_serie(?,?,?,?,?,?)}";

    try {
      // conexión
      _ODA = db.getODADBConnection(getConnectTo(Instancia));
      // procedimiento
      _cmd = _ODA.prepareCall(vQuery);
      // parametros
      _cmd.setString(1, vParametros.orden);
      _cmd.setString(2, vParametros.serie);
      _cmd.setString(3, vParametros.material);
      _cmd.setString(4, vParametros.codAlmacen);
      _cmd.setString(5, vParametros.codAgencia);
      _cmd.registerOutParameter(6, 12, 1000);

      // ejecutar parametros
      dt = db.setQuery(_cmd);
      if (dt.vData) {
        if (!_cmd.getString(6).equals("0")) {
          result.vEstado = 0;
          result.Datos.Resultado = _cmd.getString(6);
          result.vMensaje = "Reserva de series con resultado [" + _cmd.getString(6) +"]";
        } else {
          result.vMensaje = result.vMensaje + " [" + _cmd.getString(6)  + "]";
          result.Datos.Resultado = _cmd.getString(6);
        }
      } else {
        result.vEstado = 0;
        result.vMensaje = "Reserva de series no obtuvo resultados";

      }

    } catch (Exception e) {
      result.vEstado = -1;
      result.vMensaje = "Reserva de series error [" + e.getMessage() +"]";
    } finally {
      try { _cmd.close();} catch (Exception ignored) {}
      try { _ODA.close();} catch (Exception ignored) {}
    }



    return result;

  }

  // *****************************************************************************
  // sLiberaSerie
  // *****************************************************************************
  @WebMethod(operationName = "sLiberaSerie")
  public wsR_Generic sLiberaSerie(
      @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
      @WebParam(name = "vParametros") wsRxml_sLiberaSerie vParametros) {

    wsR_Generic result = new wsR_Generic();
    result.vEstado = 1;
    result.vMensaje = "OK::Liberación de series satisfactoria";
    result.Datos = new wsR_Generic_Result();
    result.Datos.Resultado = "-1";


    // obteniendo existencias según los parametros
    SubDataTable dt = new SubDataTable();
    GlobalDB db = new GlobalDB();
    OracleConnection _ODA = null;
    CallableStatement _cmd = null;

    // parameters
    // - orden:varchar2
    // - serie:varchar2
    // - resultado:varchar2:out:12
    String vQuery = "{CALL pkg_sas_genera.libera_serie(?,?,?)}";

    try {
      // conexión
      _ODA = db.getODADBConnection(getConnectTo(Instancia));
      // procedimiento
      _cmd = _ODA.prepareCall(vQuery);
      // parametros
      _cmd.setString(1, vParametros.orden);
      _cmd.setString(2, vParametros.serie);
      _cmd.registerOutParameter(3, 12, 1000);

      // ejecutar parametros
      dt = db.setQuery(_cmd);
      if (dt.vData) {
        if (!_cmd.getString(3).equals("0")) {
          result.vEstado = 0;
          result.Datos.Resultado = _cmd.getString(3);
          result.vMensaje = "Liberación de series con resultado [" + _cmd.getString(3) +"]";
        } else {
          result.vMensaje = result.vMensaje + " [" + _cmd.getString(3)  + "]";
          result.Datos.Resultado = _cmd.getString(3);
        }
      } else {
        result.vEstado = 0;
        result.vMensaje = "Liberación de series no obtuvo resultados";

      }

    } catch (Exception e) {
      result.vEstado = -1;
      result.vMensaje = "Liberación de series error [" + e.getMessage() +"]";
    } finally {
      try { _cmd.close();} catch (Exception ignored) {}
      try { _ODA.close();} catch (Exception ignored) {}
    }



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
    result.vMensaje = "Ok::Consulta inconformidad satisfactoria";
    result.Datos = new wsR_Inconformidad_Result();
    result.Datos.Contrato = "0";
    result.Datos.Monto = "0.00";
    result.Datos.Nivel = "0";

    if (Instancia == wsInstancias.wsInstancia.ODA_502) {

      // obteniendo existencias según los parametros
      SubDataTable dt = new SubDataTable();
      GlobalDB db = new GlobalDB();
      OracleConnection _ODA = null;
      CallableStatement _cmd = null;

      // parameters
      // - inconformidad:varchar2
      // - monto:varchar2:out:12
      // - contrato:varchar2:out:12
      // - nivel:varchar2:out:12
      String vQuery = "{CALL pkg_sas_genera.consulta_inconformidad(?,?,?,?)}";

      try {
        // conexión
        _ODA = db.getODADBConnection(getConnectTo(Instancia));
        // procedimiento
        _cmd = _ODA.prepareCall(vQuery);
        // parametros
        _cmd.setString(1, vInconformidad);
        _cmd.registerOutParameter(2, 12, 1000);
        _cmd.registerOutParameter(3, 12, 1000);
        _cmd.registerOutParameter(4, 12, 1000);

        // ejecutar parametros
        dt = db.setQuery(_cmd);
        if (dt.vData) {
          result.Datos.Contrato = _cmd.getString(2);
          result.Datos.Monto = _cmd.getString(3);
          result.Datos.Nivel = _cmd.getString(4);
        } else {
          result.vEstado = 0;
          result.vMensaje = "Consulta inconformidad sin resultados";
        }

      } catch (Exception e) {
        result.vEstado = -1;
        result.vMensaje = "Consulta inconformidad error [" + e.getMessage() +"]";
      } finally {
        try { _cmd.close();} catch (Exception ignored) {}
        try { _ODA.close();} catch (Exception ignored) {}
      }

    } else {
      result.vEstado = 0;
      result.vMensaje = "ERROR::Consulta inconformidad no implementada para el pais";
    }



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
    result.vMensaje = "OK::Actualización de inconformidad satisfactoria";
    result.Datos = new wsR_Generic_Result();
    result.Datos.Resultado = "-1";

    if (Instancia == wsInstancias.wsInstancia.ODA_502) {


      // obteniendo existencias según los parametros
      SubDataTable dt = new SubDataTable();
      GlobalDB db = new GlobalDB();
      OracleConnection _ODA = null;
      CallableStatement _cmd = null;

      // parameters\
      // - inconformidad:varchar2
      // - resultado:varchar2:out:12
      String vQuery = "{CALL pkg_sas_genera.actualiza_inconformidad(?,?)}";

      try {
        // conexión
        _ODA = db.getODADBConnection(getConnectTo(Instancia));
        // procedimiento
        _cmd = _ODA.prepareCall(vQuery);
        // parametros
        _cmd.setString(1, vInconformidad);
        _cmd.registerOutParameter(2, 12, 1000);

        // ejecutar parametros
        dt = db.setQuery(_cmd);
        if (dt.vData) {
          if (!_cmd.getString(2).startsWith("OK")) {
            result.vEstado = 0;
            result.Datos.Resultado = _cmd.getString(2);
            result.vMensaje = "Actualización de Inconformidad con resultado [" + _cmd.getString(2) + "]";
          } else {
            result.vMensaje = result.vMensaje + " [" + _cmd.getString(2) + "]";
            result.Datos.Resultado = _cmd.getString(2);
          }
        } else {
          result.vEstado = 0;
          result.vMensaje = "Actualización de Inconformidad no obtuvo resultados";

        }

      } catch (Exception e) {
        result.vEstado = -1;
        result.vMensaje = "Actualización de Inconformidad error [" + e.getMessage() + "]";
      } finally {
        try {
          _cmd.close();
        } catch (Exception ignored) {
        }
        try {
          _ODA.close();
        } catch (Exception ignored) {
        }
      }

    } else {
      result.vEstado = 0;
      result.vMensaje = "ERROR::Actualización de inconformidad no implementada para el pais";

    }


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
    result.vMensaje = "Ok::Anulación de pedido satisfactorio";
    result.Datos = new wsR_Anula_Result();
    result.Datos.Codigo = "1";
    result.Datos.Descripcion = "X";


    // obteniendo existencias según los parametros
    SubDataTable dt = new SubDataTable();
    GlobalDB db = new GlobalDB();
    OracleConnection _ODA = null;
    CallableStatement _cmd = null;

    // parameters
    // - correlativo:varchar2
    // - usuario:varchar2
    // - codigo:varchar2:out:12
    // - descripcion:varchar2:out:12
    String vQuery = "{CALL pkg_crm_oda_anula.anula_venta(?,?,?,?)}";

    try {
      // conexión
      _ODA = db.getODADBConnection(getConnectTo(Instancia));
      // procedimiento
      _cmd = _ODA.prepareCall(vQuery);
      // parametros
      _cmd.setString(1, vParametros.orden);
      _cmd.setString(2, vParametros.usuario);
      _cmd.registerOutParameter(3, 12, 1000);
      _cmd.registerOutParameter(4, 12, 1000);

      // ejecutar parametros
      dt = db.setQuery(_cmd);
      if (dt.vData) {
        if (!_cmd.getString(3).equals("OK")) {
          result.vEstado = 0;
          result.Datos.Codigo = _cmd.getString(3);
          result.Datos.Descripcion = _cmd.getString(4);
          result.vMensaje = "Anulación de pedido con resultado [" + _cmd.getString(3) +"]";
        } else {
          result.vMensaje = result.vMensaje + " [" + _cmd.getString(3)  + "]";
          result.Datos.Codigo = _cmd.getString(3);
          result.Datos.Descripcion = _cmd.getString(4);
        }
      } else {
        result.vEstado = 0;
        result.vMensaje = "Anulación de pedido no obtuvo resultados";

      }

    } catch (Exception e) {
      result.vEstado = -1;
      result.vMensaje = "Anulación de pedido error [" + e.getMessage() +"]";
    } finally {
      try { _cmd.close();} catch (Exception ignored) {}
      try { _ODA.close();} catch (Exception ignored) {}
    }



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
    result.vMensaje = "Ok::Consulta Estado Venta Satisfactorio";
    result.Datos = new wsR_Anula_Result();
    result.Datos.Codigo = "1";
    result.Datos.Descripcion = "X";


    // obteniendo existencias según los parametros
    SubDataTable dt = new SubDataTable();
    GlobalDB db = new GlobalDB();
    OracleConnection _ODA = null;
    CallableStatement _cmd = null;

    // parameters
    // - orden:varchar2
    // - codigo:varchar2:out:12
    // - descripcion:varchar2:out:12
    String vQuery = "{CALL pkg_crm_oda_anula.consulta_estado_venta(?,?,?)}";

    try {
      // conexión
      _ODA = db.getODADBConnection(getConnectTo(Instancia));
      // procedimiento
      _cmd = _ODA.prepareCall(vQuery);
      // parametros
      _cmd.setString(1, vOrden);
      _cmd.registerOutParameter(2, 12, 1000);
      _cmd.registerOutParameter(3, 12, 1000);

      // ejecutar parametros
      dt = db.setQuery(_cmd);
      if (dt.vData) {
          result.Datos.Codigo = _cmd.getString(2);
          result.Datos.Descripcion = _cmd.getString(3);
          result.vMensaje = "Consulta estado venta realizada";
      } else {
        result.vEstado = 0;
        result.vMensaje = "Consulta estado venta no obtuvo resultados";

      }

    } catch (Exception e) {
      result.vEstado = -1;
      result.vMensaje = "Consulta estado venta error [" + e.getMessage() +"]";
    } finally {
      try { _cmd.close();} catch (Exception ignored) {}
      try { _ODA.close();} catch (Exception ignored) {}
    }



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
    result.vMensaje = "Ok::Consulta Estado Anulación Satisfactorio";
    result.Datos = new wsR_Anula_Result();
    result.Datos.Codigo = "1";
    result.Datos.Descripcion = "X";


    // obteniendo existencias según los parametros
    SubDataTable dt = new SubDataTable();
    GlobalDB db = new GlobalDB();
    OracleConnection _ODA = null;
    CallableStatement _cmd = null;

    // parameters
    // - orden:varchar2
    // - codigo:varchar2:out:12
    // - descripcion:varchar2:out:12
    String vQuery = "{CALL pkg_crm_oda_anula.consulta_estado_anulacion(?,?,?)}";

    try {
      // conexión
      _ODA = db.getODADBConnection(getConnectTo(Instancia));
      // procedimiento
      _cmd = _ODA.prepareCall(vQuery);
      // parametros
      _cmd.setString(1, vOrden);
      _cmd.registerOutParameter(2, 12, 1000);
      _cmd.registerOutParameter(3, 12, 1000);

      // ejecutar parametros
      dt = db.setQuery(_cmd);
      if (dt.vData) {
        result.Datos.Codigo = _cmd.getString(2);
        result.Datos.Descripcion = _cmd.getString(3);
        result.vMensaje = "Consulta estado Anulación realizada";
      } else {
        result.vEstado = 0;
        result.vMensaje = "Consulta estado Anulación no obtuvo resultados";

      }

    } catch (Exception e) {
      result.vEstado = -1;
      result.vMensaje = "Consulta estado Anulación error [" + e.getMessage() +"]";
    } finally {
      try { _cmd.close();} catch (Exception ignored) {}
      try { _ODA.close();} catch (Exception ignored) {}
    }



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
    result.vMensaje = "Ok::Proceso de inserción de Orden realizada con éxito";
    result.Datos = new wsR_Orden_Result();
    result.Datos.Resultado = "1";


    // obteniendo existencias según los parametros
    SubDataTable dt = new SubDataTable(); SubDataTable dtDetail = new SubDataTable(); SubDataTable dtImp = new SubDataTable();
    GlobalDB db = new GlobalDB();
    OracleConnection _ODA = null;
    CallableStatement _cmd = null;
    CallableStatement _cmd_detail = null;
    CallableStatement _cmd_imp = null;

    // *****************************************************************************
    // Encabezado de la Orden
    // *****************************************************************************
    String vQuery = "{CALL PKG_SIV_CAJA_ODA.INSERT_HEADER(?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?,?,?,?,?,? ,?,?)}";

    try {
      // conexión
      _ODA = db.getODADBConnection(getConnectTo(Instancia));
      // set autocommit to false
      _ODA.setAutoCommit(false);

      // procedimiento
      _cmd = _ODA.prepareCall(vQuery);
      // parametros
      _cmd.setString(1, vOrden.Encabezado.codAgencia);
      _cmd.setLong(2, vOrden.Encabezado.codigoCliente);
      _cmd.setString(3, vOrden.Encabezado.codigoGestor);
      _cmd.setString(4, vOrden.Encabezado.codigoPais);
      _cmd.setLong(5, vOrden.Encabezado.correlativoVenta);
      _cmd.setInt(6, vOrden.Encabezado.cuentaDetalle);
      _cmd.setString(7, vOrden.Encabezado.direccion);
      _cmd.setInt(8, vOrden.Encabezado.exento);
      java.sql.Date sqlFechaCreacion = new java.sql.Date(vOrden.Encabezado.fechaCreacion.getTime());
      _cmd.setDate(9, sqlFechaCreacion);
      _cmd.setInt(10, vOrden.Encabezado.financiado);

      _cmd.setDouble(11, vOrden.Encabezado.montoFinanciado);
      _cmd.setString(12, vOrden.Encabezado.documentoIdentidad);
      _cmd.setString(13, vOrden.Encabezado.nombreCliente);
      _cmd.setInt(14, vOrden.Encabezado.cuotas);
      _cmd.setDouble(15, vOrden.Encabezado.prima);
      _cmd.setDouble(16, vOrden.Encabezado.deposito);
      _cmd.setDouble(17, vOrden.Encabezado.anticipo);
      _cmd.setString(18, vOrden.Encabezado.registroFiscal);
      _cmd.setNull(19, Types.NUMERIC);
      _cmd.setString(20, vOrden.Encabezado.tipoDocumento);

      _cmd.setString(21, vOrden.Encabezado.tipoVenta);
      _cmd.setDouble(22, vOrden.Encabezado.totalDescuento);
      _cmd.setDouble(23, vOrden.Encabezado.totalDescuentoEspecial);
      _cmd.setDouble(24, vOrden.Encabezado.totalOrden);
      _cmd.setString(25, vOrden.Encabezado.departamento);
      _cmd.setString(26, vOrden.Encabezado.municipio);
      _cmd.setString(27, vOrden.Encabezado.variablesAdicionales);
      _cmd.setString(28, vOrden.Encabezado.combo);
      _cmd.setString(29, vOrden.Encabezado.giroNegocio);
      _cmd.setString(30, vOrden.Encabezado.telefonoFinancia);

      _cmd.setString(31, vOrden.Encabezado.contratoFinancia);
      _cmd.registerOutParameter(32, 2, 100);

      // ejecutar parametros
      dt = db.setQuery(_cmd);
      if (dt.vData && dt.vDBMessage.equals("")) {
        int hResult = _cmd.getInt(32);
        if ( hResult == -1) {
          result.Datos.Resultado = "0";
          result.vMensaje = "ERROR::Encabezado de Orden con correlativo " + String.valueOf(vOrden.Encabezado.correlativoVenta) + " ya existe";
          // rollback
          _ODA.rollback();

        } else if (hResult == 0) {
          result.Datos.Resultado = "0";
          result.vMensaje = "ERROR::Orden con correlativo " + String.valueOf(vOrden.Encabezado.correlativoVenta) + " no pudo ser insertada";
          // rollback
          _ODA.rollback();
        } else {

          // *****************************************************************************
          // Detalle de la Orden
          // *****************************************************************************
          result.vMensaje = result.vMensaje + "[H:" + Integer.toString(hResult) + "]";

          // procedimiento
          String vQueryDet = "{CALL PKG_SIV_CAJA_ODA.INSERT_DETAIL(?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?)}";
          // Parametros
          _cmd_detail = _ODA.prepareCall(vQueryDet);

          for (wsRxml_sInsertaOrden_Detalle det : vOrden.Detalle) {

            // parametros
            _cmd_detail.setInt(1, det.cantidad);
            _cmd_detail.setString(2, det.codigoDatos);
            _cmd_detail.setString(3, det.codigoPlan);
            _cmd_detail.setLong(4, det.contrato);
            _cmd_detail.setLong(5, det.correlativoVenta);
            _cmd_detail.setString(6, det.jerarquiaProducto);
            _cmd_detail.setDouble(7, det.descuentoEspecial);
            _cmd_detail.setDouble(8, det.descuentoProducto);
            _cmd_detail.setString(9, det.imei);
            _cmd_detail.setString(10, det.nivel);

            _cmd_detail.setString(11, det.plazo);
            _cmd_detail.setDouble(12, det.precioProducto);
            _cmd_detail.setString(13, det.sim );
            _cmd_detail.setDouble(14, det.precioSim);
            _cmd_detail.setString(15, det.sku);
            _cmd_detail.setString(16, det.solicitudBilling);
            _cmd_detail.setString(17, det.telefono);
            _cmd_detail.setString(18, det.tipoDescuentoEspecial);
            _cmd_detail.setString(19, det.almacen);
            _cmd_detail.setString(20, det.codAgencia);

            _cmd_detail.setString(21, det.codigoItem);
            _cmd_detail.setDouble(22, det.prima);
            _cmd_detail.setDouble(23, det.montoFinanciado);
            _cmd_detail.setDouble(24, det.combo);
            _cmd_detail.registerOutParameter(25, 2, 100);

            // ejecutar parametros
            dtDetail = db.setQuery(_cmd_detail);
            if (dtDetail.vData && dtDetail.vDBMessage.equals("")) {

              int dResult = _cmd_detail.getInt(25);
              if ( dResult == -1) {
                result.Datos.Resultado = "0";
                result.vMensaje = "ERROR::Detalle de Orden con correlativo " + String.valueOf(det.correlativoVenta) + " ya existe";
                // rollback
                _ODA.rollback();
                break;

              } else if (dResult == 0) {
                result.Datos.Resultado = "0";
                result.vMensaje = "ERROR::Detalle Orden con correlativo " + String.valueOf(det.correlativoVenta) + " no pudo ser insertada";
                // rollback
                _ODA.rollback();
                break;
              }

            } else {
              result.vEstado = 0;
              result.Datos.Resultado = "0";
              result.vMensaje = "ERROR::Ocurrio un error insertando el detalle de la orden [" + dtDetail.vDBMessage + "]";
              // rollback
              _ODA.rollback();
              break;
            }

          }

          // solo se actualiza si el estado sigue siendo 1
          if (result.vEstado == 1) {
            result.vMensaje = result.vMensaje + "[D:1]";
          }

          // *****************************************************************************
          // Impuestos de la Orden
          // *****************************************************************************
          // Solo aplica para
          // GT - 502
          // CR - 506
          // HN - 504
          // PA - 507
          if (Instancia != wsInstancias.wsInstancia.ODA_503 && Instancia != wsInstancias.wsInstancia.ODA_505) {

            // procedimiento
            String vQueryImp = "{CALL PKG_SIV_CAJA_ODA.insert_tax_exemption(?,?,?,?,?,?,?)}";
            // Parametros
            _cmd_imp = _ODA.prepareCall(vQueryImp);

            for (wsRxml_sInsertaOrden_Impuesto imp : vOrden.Impuesto) {
              // parametros
              _cmd_imp.setLong(1, imp.correlativoVenta);
              _cmd_imp.setInt(2, imp.idImpuestoExento);
              _cmd_imp.setString(3, imp.tipoComprobante);
              _cmd_imp.setString(4, imp.registroExento);
              _cmd_imp.setString(5, imp.actividad);
              _cmd_imp.setString(6, imp.documentoExento);
              java.sql.Date sqlFecEmision = new java.sql.Date(imp.fechaEmision.getTime());
              _cmd_imp.setDate(6, sqlFecEmision);


              // ejecutar parametros
              dtImp = db.setQuery(_cmd_imp);
              if (!dtImp.vData || dtImp.vDBMessage.equals("")) {
                result.vEstado = 0;
                result.Datos.Resultado = "0";
                result.vMensaje = "ERROR::Ocurrio un error insertando el impuesto de la orden [" + dtImp.vDBMessage + "]";
                // rollback
                _ODA.rollback();
                break;
              }


            }

            // solo se actualiza si el estado sigue siendo 1
            if (result.vEstado == 1) {
              result.vMensaje = result.vMensaje + "[I:1]";
            }
          }

          // commit;
          _ODA.commit();

        }
      } else {
        result.vEstado = 0;
        result.Datos.Resultado = "0";
        result.vMensaje = "ERROR::Encabezado Orden - ocurrio un error en la inserción del a orden [" + dt.vDBMessage + "]";
        // rollback
        _ODA.rollback();
      }


    } catch (Exception e) {
      try { _ODA.rollback();} catch (SQLException ignored) {}
      result.vEstado = 0;
      result.Datos.Resultado = "-1";
      result.vMensaje = "Proceso de inserción de Orden genero error [" + e.getMessage() +"]";
    } finally {
      try { _cmd.close();} catch (Exception ignored) {}
      try { _cmd_detail.close();} catch (Exception ignored) {}
      try { _cmd_imp.close();} catch (Exception ignored) {}
      try { _ODA.close();} catch (Exception ignored) {}
    }

    return result;

  }

  // *****************************************************************************
  // ConnecTo
  // *****************************************************************************
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
