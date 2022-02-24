package WsServicios.Entidades;

import FromNET.*;
import ServiceActionsDB.dbROW;
import WsServicios.Bases.wsInstancias;
import WsServicios.Entidades.Base.*;
import WsServicios.Entidades.Lista.*;
import WsServicios.Entidades.Request.*;
import db.BaseClass;
import oracle.jdbc.OracleConnection;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.sql.CallableStatement;


@WebService(serviceName = "wsEntidades", targetNamespace = "")
public class wsEntidades
    extends BaseClass {

  // ***********************************************************************
  // gAlmacenes
  // ***********************************************************************
  @WebMethod(operationName = "gAlmacenes")
  public wsR_Almacenes gAlmacenes(
      @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
      @WebParam(name = "Parametros") wsRxml_gAlmacenes Parametros) {

    wsR_Almacenes vResponse = new wsR_Almacenes();
    vResponse.vEstado = 1;
    vResponse.vMensaje = "Almacenes consultadas";

    // validando parametros requeridos //
    // parametros
    //  + codAgencia @requerido
    //  + codCentro
    //  + almacen
    if (Parametros.codAgencia.isEmpty()) {
      vResponse.vEstado = 0;
      vResponse.vMensaje = "ERROR::Parámetro [codAgencia] requerido para consultar";
      return vResponse;
    }


    SubDataTable dt = new SubDataTable();
    try {
      GlobalDB db = new GlobalDB();
      //GlobalDBParamObjectList vParams = new GlobalDBParamObjectList();
      String vQuery = "";
      if (Instancia == wsInstancias.wsInstancia.ODA_507) {
        vQuery = "select  a.COD_AGENCIA, a.COD_CENTRO centro, a.COD_ALMACEN almacen, b.NOM_ALMACEN descripcion " +
            "from    INV_ALMACENES a " +
            "        inner join INV_CATALOGO_ALMACENES b on b.COD_ALMACEN = a.COD_ALMACEN " +
            "where   a.cod_Agencia = '" + Parametros.codAgencia + "' ";

        //"where   a.cod_Agencia = ? ";
        //vParams.Add(new GlobalDBParamObject("a.cod_agencia", Parametros.codAgencia));

        // centro
        if (!Parametros.centro.isEmpty()) {
          vQuery += " and a.cod_centro = '" + Parametros.centro + "' ";

          //vQuery += " and a.cod_centro = ? ";
          //vParams.Add(new GlobalDBParamObject("a.cod_centro", Parametros.centro));
        }
        // almacen
        if (!Parametros.almacen.isEmpty()) {
          vQuery += " and a.cod_almacen = '" + Parametros.almacen + "' ";

          //vQuery += " and a.cod_almacen = ? ";
          //vParams.Add(new GlobalDBParamObject("a.cod_almacen", Parametros.almacen));
        }
      } else {
        vQuery = "select  a.COD_AGENCIA, a.COD_SUBINVENTARIO_IC centro, COD_LOCALIZADOR_IC almacen, b.NOM_ALMACEN descripcion " +
            "from    LOCALIZADORES a " +
            "        inner join INV_CATALOGO_ALMACENES b on b.COD_ALMACEN = a.COD_LOCALIZADOR_IC " +
            "where   a.cod_Agencia = '" + Parametros.codAgencia + "' ";

        //"where   a.cod_Agencia = ? ";
        //vParams.Add(new GlobalDBParamObject("a.cod_Agencia", Parametros.codAgencia));

        // centro
        if (Parametros.centro != null && !Parametros.centro.isEmpty()) {
          vQuery += " and a.cod_subinventario_ic = '" + Parametros.centro + "' ";

          //vQuery += " and a.cod_subinventario_ic = ? ";
          //vParams.Add(new GlobalDBParamObject("a.cod_subinventario_ic", Parametros.centro));
        }

        // almacen
        if (Parametros.almacen != null && !Parametros.almacen.isEmpty()) {
          vQuery += " and a.cod_localizador_ic = '" + Parametros.almacen + "' ";

          //vQuery += " and a.cod_localizador_ic = ? ";
          //vParams.Add(new GlobalDBParamObject("a.cod_localizador_ic", Parametros.almacen));
        }
      }

      dt = db.getQuery(vQuery, getConnectTo(Instancia));

      if (dt.vData && dt.vRows > 1) {
        for (int i = 1; i < dt.vRows; i++) {
          wsR_Almacen registro = new wsR_Almacen();
          registro.codAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_AGENCIA")).value_string;
          registro.centro = (((dbROW) dt.Datos.rows.get(i)).getColByName("CENTRO")).value_string;
          registro.almacen = (((dbROW) dt.Datos.rows.get(i)).getColByName("ALMACEN")).value_string;
          registro.descripcion = (((dbROW) dt.Datos.rows.get(i)).getColByName("DESCRIPCION")).value_string;
          vResponse.Datos.add(registro);
        }
      } else {
        vResponse.vEstado = 0;
        vResponse.vMensaje = "ERROR::No se obtuvieron resultados";
      }
    } catch (Exception e) {
      vResponse.vEstado = -1;
      vResponse.vMensaje = "ERROR::Consulta de Almacenes reporto error [" + e.getMessage() + "]";
    }


    return vResponse;


  }


  // ***********************************************************************
  // gAgencias
  // ***********************************************************************
  @WebMethod(operationName = "gAgencias")
  public wsR_Agencias gAgencias(
      @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
      @WebParam(name = "Parametros") wsRxml_gAgencias Parametros) {

    wsR_Agencias vResponse = new wsR_Agencias();
    vResponse.vEstado = 1;
    vResponse.vMensaje = "Agencias consultadas";


    // validando parametros requeridos //
    // parametros
    //  + codAgencia @requerido
    if (Parametros.codAgencia == null || Parametros.codAgencia.isEmpty()) {
      vResponse.vEstado = 0;
      vResponse.vMensaje = "ERROR::Parámetro [codAgencia] requerido para consultar";
      return vResponse;
    }


    SubDataTable dt = new SubDataTable();
    try {
      GlobalDB db = new GlobalDB();
      String vQuery = "select  a.COD_AGENCIA, a.nom_agencia, a.canal_Ventas, a.tipo_Agencia, a.cliente_sinergia " +
          "from    agencias a " +
          "where   a.cod_Agencia = '" + Parametros.codAgencia + "' ";


      dt = db.getQuery(vQuery, getConnectTo(Instancia));

      if (dt.vData && dt.vRows > 1) {
        for (int i = 1; i < dt.vRows; i++) {
          wsR_Agencia registro = new wsR_Agencia();
          registro.codAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_AGENCIA")).value_string;
          registro.nomAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("NOM_AGENCIA")).value_string;
          registro.canalVentas = (((dbROW) dt.Datos.rows.get(i)).getColByName("CANAL_VENTAS")).value_string;
          registro.tipoAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("TIPO_AGENCIA")).value_string;
          registro.clientSinergia = (((dbROW) dt.Datos.rows.get(i)).getColByName("CLIENTE_SINERGIA")).value_string;
          vResponse.Datos.add(registro);
        }
      } else {
        vResponse.vEstado = 0;
        vResponse.vMensaje = "ERROR::No se obtuvieron resultados";
      }
    } catch (Exception e) {
      vResponse.vEstado = -1;
      vResponse.vMensaje = "ERROR::Consulta de Agencias reporto error [" + e.getMessage() + "]";
    }

    return vResponse;

  }


  // ***********************************************************************
  // gTiposVenta
  // ***********************************************************************
  @WebMethod(operationName = "gTiposVenta")
  public wsR_TiposVenta gTiposVenta(
      @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
      @WebParam(name = "Parametros") wsRxml_gTiposVenta Parametros) {

    wsR_TiposVenta vResponse = new wsR_TiposVenta();
    vResponse.vEstado = 1;
    vResponse.vMensaje = "Tipos Venta consultadas";

    SubDataTable dt = new SubDataTable();
    try {
      GlobalDB db = new GlobalDB();
      String vQuery = "";
      if (Instancia == wsInstancias.wsInstancia.ODA_507) {
        vQuery = "select  cod_venta, DESCRIPCION, TIPO_DOCUMENTO " +
            "from    INV_TIPOS_VENTA " +
            "where   activo = 'S' ";
        //"and COD_VENTA = '" + Parametros.tipoVenta + "' ";
      } else {
        vQuery = "select  cod_venta, DESCRIPCION, TIPO_DOCUMENTO " +
            "from    TIPO_VENTA_PLATAFORMA " +
            "where   activo = 'S' ";
        //" and  COD_VENTA = '" + Parametros.tipoVenta + "' ";
      }

      if (Parametros.tipoVenta != null && !Parametros.tipoVenta.isEmpty()) {
        vQuery += " and  COD_VENTA = '" + Parametros.tipoVenta + "' ";
      }


      dt = db.getQuery(vQuery, getConnectTo(Instancia));

      if (dt.vData && dt.vRows > 1) {
        for (int i = 1; i < dt.vRows; i++) {
          wsR_TipoVenta registro = new wsR_TipoVenta();
          registro.tipoDocumento = (((dbROW) dt.Datos.rows.get(i)).getColByName("TIPO_DOCUMENTO")).value_string;
          registro.tipoVenta = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_VENTA")).value_string;
          registro.descripcion = (((dbROW) dt.Datos.rows.get(i)).getColByName("DESCRIPCION")).value_string;
          vResponse.Datos.add(registro);
        }
      } else {
        vResponse.vEstado = 0;
        vResponse.vMensaje = "ERROR::No se obtuvieron resultados";
      }
    } catch (Exception e) {
      vResponse.vEstado = -1;
      vResponse.vMensaje = "ERROR::Consulta de tipos de venta reporto error [" + e.getMessage() + "]";
    }

    return vResponse;
  }


  // ***********************************************************************
  // gSeriesODA
  // ***********************************************************************
  @WebMethod(operationName = "gSeriesODA")
  public wsR_Series gSeriesODA(
      @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
      @WebParam(name = "Parametros") wsRxml_gSeriesODA Parametros) {

    wsR_Series vResponse = new wsR_Series();
    vResponse.vEstado = 1;
    vResponse.vMensaje = "Series consultadas";


    // validando parametros requeridos //
    // parametros
    //  + codAgencia @requerido
    //  + codProducto @requerido
    //  + estatus
    //  + serie
    if (Parametros.codAgencia == null || Parametros.codAgencia.isEmpty()) {
      vResponse.vEstado = 0;
      vResponse.vMensaje = "ERROR::Parámetro [codAgencia] requerido para consultar";
      return vResponse;
    }

    if (Parametros.codProducto == null || Parametros.codProducto.isEmpty()) {
      vResponse.vEstado = 0;
      vResponse.vMensaje = "ERROR::Parámetro [codProducto] requerido para consultar";
      return vResponse;
    }


    SubDataTable dt = new SubDataTable();
    try {
      GlobalDB db = new GlobalDB();
      String vQuery = "select a.idagencia, a.agencia, a.estatus, a.cajero, a.ordencrm, a.idventa, a.sku, a.idsinergia, a.producto, a.almacen, a.serie " +
          "from vw_series a " +
          "where   a.idagencia = '" + Parametros.codAgencia + "' " +
          "and (a.sku = '" + Parametros.codProducto + "' or a.idsinergia = '" + Parametros.codProducto + "')";


      // estatus
      if (Parametros.estatus != null && !Parametros.estatus.isEmpty()) {
        vQuery += " and a.estatus = '" + Parametros.estatus + "' ";
      }

      // serie
      if (Parametros.serie != null && !Parametros.serie.isEmpty()) {
        vQuery += " and a.serie = '" + Parametros.serie + "' ";
      }


      dt = db.getQuery(vQuery, getConnectTo(Instancia));

      if (dt.vData && dt.vRows > 1) {
        for (int i = 1; i < dt.vRows; i++) {
          wsR_Serie registro = new wsR_Serie();
          registro.codAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("IDAGENCIA")).value_string;
          registro.nomAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("AGENCIA")).value_string;
          registro.estatus = (((dbROW) dt.Datos.rows.get(i)).getColByName("ESTATUS")).value_string;
          registro.codCajero = (((dbROW) dt.Datos.rows.get(i)).getColByName("CAJERO")).value_string;
          registro.orden = (((dbROW) dt.Datos.rows.get(i)).getColByName("ORDENCRM")).value_string;
          registro.idVenta = (((dbROW) dt.Datos.rows.get(i)).getColByName("IDVENTA")).value_int;
          registro.sku = (((dbROW) dt.Datos.rows.get(i)).getColByName("SKU")).value_string;
          registro.idSinergia = (((dbROW) dt.Datos.rows.get(i)).getColByName("IDSINERGIA")).value_string;
          registro.producto = (((dbROW) dt.Datos.rows.get(i)).getColByName("PRODUCTO")).value_string;
          registro.almacen = (((dbROW) dt.Datos.rows.get(i)).getColByName("ALMACEN")).value_string;
          registro.serie = (((dbROW) dt.Datos.rows.get(i)).getColByName("SERIE")).value_string;
          vResponse.Datos.add(registro);
        }
      } else {
        vResponse.vEstado = 0;
        vResponse.vMensaje = "ERROR::No se obtuvieron resultados";
      }
    } catch (Exception e) {
      vResponse.vEstado = -1;
      vResponse.vMensaje = "ERROR::Consulta de series reporto error [" + e.getMessage() + "]";
    }


    return vResponse;

  }

  // ***********************************************************************
  // gPreciosODA
  // ***********************************************************************
  @WebMethod(operationName = "gPreciosODA")
  public wsR_PreciosODA gPreciosODA(
      @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
      @WebParam(name = "Parametros") wsRxml_gPreciosODA Parametros) {

    wsR_PreciosODA vResponse = new wsR_PreciosODA();
    vResponse.vEstado = 1;
    vResponse.vMensaje = "Consulta de precios ODA";

    // validando parametros requeridos //
    // parametros
    //  + codProducto @requerido
    //  + motivoPedido
    //  + grupoCondiciones
    //  + grupoComisiones
    if (Parametros.codProducto == null || Parametros.codProducto.isEmpty()) {
      vResponse.vEstado = 0;
      vResponse.vMensaje = "ERROR::Parámetro [codProducto] requerido para consultar";
      return vResponse;
    }

    SubDataTable dt = new SubDataTable();
    try {
      GlobalDB db = new GlobalDB();
      String vQuery = "select  mandante, COMBINACION_CLAVE, ORGANIZACION_VENTA, CLASE_CONDICION, MOTIVO_PEDIDO, GRUPO_CONDICIONES_CLTE, GRUPO_COMISIONES, " +
          "       DESCRIPCION, MATERIAL, VALIDE_DE, VALIDO_A, DES_MATERIAL, valor, CONDICION_MONEDA, nivel_oda " +
          "from    ZSVSD_PRECIOS_VENTA a " +
          "where   a.material = '" + Parametros.codProducto + "' " ;


      // motivo
      if (Parametros.motivoPedido != null && !Parametros.motivoPedido.isEmpty()) {
        vQuery += " and a.motivo_pedido = '" + Parametros.motivoPedido + "' ";
      }

      // condiciones
      if (Parametros.grupoCondiciones != null && !Parametros.grupoCondiciones.isEmpty()) {
        vQuery += " and a.grupo_condiciones_clte = '" + Parametros.grupoCondiciones + "' ";
      }

      // comisiones
      if (Parametros.grupoCondiciones != null && !Parametros.grupoCondiciones.isEmpty()) {
        vQuery += " and a.grupo_comisiones = '" + Parametros.grupoComisiones + "' ";
      }


      dt = db.getQuery(vQuery, getConnectTo(Instancia));

      if (dt.vData && dt.vRows > 1) {
        for (int i = 1; i < dt.vRows; i++) {
          wsR_PrecioODA registro = new wsR_PrecioODA();
          registro.mandante = (((dbROW) dt.Datos.rows.get(i)).getColByName("MANDANTE")).value_string;
          registro.combinacionClave = (((dbROW) dt.Datos.rows.get(i)).getColByName("COMBINACION_CLAVE")).value_string;
          registro.organizacionVenta = (((dbROW) dt.Datos.rows.get(i)).getColByName("ORGANIZACION_VENTA")).value_string;
          registro.claseCondicion = (((dbROW) dt.Datos.rows.get(i)).getColByName("CLASE_CONDICION")).value_string;
          registro.motivoPedido = (((dbROW) dt.Datos.rows.get(i)).getColByName("MOTIVO_PEDIDO")).value_string;
          registro.grupoCondicionesCliente = (((dbROW) dt.Datos.rows.get(i)).getColByName("GRUPO_CONDICIONES_CLTE")).value_string;
          registro.grupoComisiones = (((dbROW) dt.Datos.rows.get(i)).getColByName("GRUPO_COMISIONES")).value_string;
          registro.descripcion = (((dbROW) dt.Datos.rows.get(i)).getColByName("DESCRIPCION")).value_string;
          registro.material = (((dbROW) dt.Datos.rows.get(i)).getColByName("MATERIAL")).value_string;
          registro.validoDe = (((dbROW) dt.Datos.rows.get(i)).getColByName("VALIDE_DE")).value_string;
          registro.validoA = (((dbROW) dt.Datos.rows.get(i)).getColByName("VALIDO_A")).value_string;
          registro.desMaterial = (((dbROW) dt.Datos.rows.get(i)).getColByName("DES_MATERIAL")).value_string;
          registro.valor = (((dbROW) dt.Datos.rows.get(i)).getColByName("VALOR")).value_double;
          registro.condicionMoneda = (((dbROW) dt.Datos.rows.get(i)).getColByName("CONDICION_MONEDA")).value_string;
          registro.nivelODA =(((dbROW) dt.Datos.rows.get(i)).getColByName("NIVEL_ODA")).value_int;
          vResponse.Datos.add(registro);
        }
      } else {
        vResponse.vEstado = 0;
        vResponse.vMensaje = "ERROR::No se obtuvieron resultados";
      }
    } catch (Exception e) {
      vResponse.vEstado = -1;
      vResponse.vMensaje = "ERROR::Consulta de precios reporto error [" + e.getMessage() + "]";
    }


    return vResponse;

  }

  // ***********************************************************************
  // ConnectTo
  // ***********************************************************************
  private ConnectTo getConnectTo(wsInstancias.wsInstancia vInstancia) throws Exception {
    if (vInstancia == null) throw new Exception("La Instancia es invalida. Debe de ser ODA_{pais}");
    if (vInstancia.equals(wsInstancias.wsInstancia.ODA_502)) return ConnectTo.ODA_502_db;
    if (vInstancia.equals(wsInstancias.wsInstancia.ODA_503)) return ConnectTo.ODA_503_db;
    if (vInstancia.equals(wsInstancias.wsInstancia.ODA_504)) return ConnectTo.ODA_504_db;
    if (vInstancia.equals(wsInstancias.wsInstancia.ODA_505)) return ConnectTo.ODA_505_db;
    if (vInstancia.equals(wsInstancias.wsInstancia.ODA_506)) return ConnectTo.ODA_506_db;
    if (vInstancia.equals(wsInstancias.wsInstancia.ODA_507)) return ConnectTo.ODA_507_db;
    throw new Exception("La Instancia es inválida. Debe de ser ODA_{pais}");
  }
}
