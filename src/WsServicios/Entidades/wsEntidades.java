package WsServicios.Entidades;

import FromNET.ConnectTo;
import WsServicios.Bases.wsInstancias;
import WsServicios.Entidades.Base.*;
import WsServicios.Entidades.Lista.*;
import WsServicios.Entidades.Request.*;
import db.BaseClass;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@WebService(serviceName = "wsEntidades", targetNamespace = "")
public class wsEntidades
        extends BaseClass {

    // ***********************************************************************
    // gAlmacenes
    // ***********************************************************************
    @WebMethod(operationName = "gAlmacenes")
    public wsR_Almacenes gAlmacenes(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Parametros") wsRxml_gAlmacenes Parametros ) {

        wsR_Almacenes vResponse = new wsR_Almacenes();

        wsR_Almacen registro = new wsR_Almacen();
        registro.codAgencia = "P200";
        registro.centro = "P200";
        registro.almacen = "H000";
        registro.descripcion = "Ventas";

        vResponse.vEstado = 1;
        vResponse.vMensaje = "Almacenes consultadas";
        vResponse.Datos.add(registro);
        vResponse.Datos.add(registro);
        vResponse.Datos.add(registro);
        vResponse.Datos.add(registro);
        vResponse.Datos.add(registro);

        return vResponse;

    }


    // ***********************************************************************
    // gAgencias
    // ***********************************************************************
    @WebMethod(operationName = "gAgencias")
    public wsR_Agencias gAgencias(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Parametros") wsRxml_gAgencias Parametros ) {

        wsR_Agencias vResponse = new wsR_Agencias();

        wsR_Agencia agencia = new wsR_Agencia();
        agencia.codAgencia = "P200";
        agencia.nomAgencia = "Agencia";
        agencia.canalVentas = "13";
        agencia.tipoAgencia = "A";
        agencia.clientSinergia = "15";

        vResponse.vEstado = 1;
        vResponse.vMensaje = "Agencias consultadas";
        vResponse.Datos.add(agencia);
        vResponse.Datos.add(agencia);
        vResponse.Datos.add(agencia);
        vResponse.Datos.add(agencia);

        return vResponse;

    }


    // ***********************************************************************
    // gTiposVenta
    // ***********************************************************************
    @WebMethod(operationName = "gTiposVenta")
    public wsR_TiposVenta gTiposVenta(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Parametros") wsRxml_gTiposVenta Parametros ) {

        wsR_TiposVenta vResponse = new wsR_TiposVenta();

        wsR_TipoVenta registro = new wsR_TipoVenta();
        registro.tipoVenta = "TA";
        registro.descripcion = "Tiempo aire";
        registro.tipoDocumento = "ZTA";

        vResponse.vEstado = 1;
        vResponse.vMensaje = "Tipos Venta consultadas";
        vResponse.Datos.add(registro);
        vResponse.Datos.add(registro);
        vResponse.Datos.add(registro);

        return vResponse;

    }


    // ***********************************************************************
    // gSeriesODA
    // ***********************************************************************
    @WebMethod(operationName = "gSeriesODA")
    public wsR_Series gSeriesODA(
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Parametros") wsRxml_gSeriesODA Parametros ) {

        wsR_Series vResponse = new wsR_Series();

        wsR_Serie serie = new wsR_Serie();
        serie.almacen = "H000";
        serie.serie = "123";
        serie.codAgencia = "P202";
        serie.codCajero = "cajero";
        serie.estatus = "VE";
        serie.idSinergia = "10000";
        serie.idVenta = 123;
        serie.nomAgencia = "Agencia";
        serie.orden = "12344";
        serie.producto = "SIM";
        serie.sku = "i09";

        vResponse.vEstado = 1;
        vResponse.vMensaje = "Series consultadas";
        vResponse.Datos.add(serie);
        vResponse.Datos.add(serie);
        vResponse.Datos.add(serie);
        vResponse.Datos.add(serie);
        vResponse.Datos.add(serie);

        return vResponse;

    }

    // ***********************************************************************
    // gPreciosODA
    // ***********************************************************************
    @WebMethod(operationName = "gPreciosODA")
    public wsR_PreciosODA gPreciosODA (
            @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Parametros") wsRxml_gPreciosODA Parametros) {

        wsR_PreciosODA vResponse = new wsR_PreciosODA();

        wsR_PrecioODA precio = new wsR_PrecioODA();
        precio.claseCondicion = "clase";
        precio.combinacionClave = "combinacion";
        precio.descripcion = "descripcion";
        precio.condicionMoneda = "moneda";
        precio.desMaterial = "des material";
        precio.grupoComisiones = "grupo comisiones";
        precio.grupoCondicionesCliente = "condiciones";
        precio.mandante = "800";
        precio.material = "100000";
        precio.motivoPedido = "motivo";
        precio.nivelODA = 1;
        precio.organizacionVenta = "13";
        precio.valor = 100.00;
        precio.validoA = "2022-01-01";
        precio.validoDe = "2022-01-01";

        vResponse.vEstado = 1 ;
        vResponse.vMensaje = "Consulta de precios ODA";

        vResponse.Datos.add(precio);
        vResponse.Datos.add(precio);
        vResponse.Datos.add(precio);
        vResponse.Datos.add(precio);
        vResponse.Datos.add(precio);
        vResponse.Datos.add(precio);

        return vResponse;

    }

//    @WebMethod(operationName = "gBancos")
//    public wsR_Bancos gBancos(@WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia) {
//        wsR_Bancos vResponse = new wsR_Bancos();
//        SubDataTable dt = new SubDataTable();
//
//        try {
//            GlobalDB db = new GlobalDB();
//            dt = db.getQuery("select ACTIVE ACTIVO, CODE_BANK , COD_CLAROTV, 1 COD_MONEDA, COD_TECNOMEN, PASS, DESCRIPTION,  CONCI_PATH, CONCI_RESPONSE_PATH, ID_BANCO, TIME_OUT, USER_NAME, COD_AGENCIA, COD_CAJA, COD_CAJERO, COD_SUPERVISOR   from list_banks order by ID_BANCO asc", getConnectTo(Instancia));
//            if (dt.vData.booleanValue() && dt.vRows>1) {
//                for (int i = 1; i < dt.vRows; i++) {
//                    wsR_Banco vRow = new wsR_Banco();
//                    vRow.activo = (((dbROW) dt.Datos.rows.get(i)).getColByName("ACTIVO")).value_string;
//                    vRow.codBanco = (((dbROW) dt.Datos.rows.get(i)).getColByName("CODE_BANK")).value_string;
//                    vRow.codClaroTV = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_CLAROTV")).value_string;
//                    vRow.codMoneda = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_MONEDA")).value_int;
//                    vRow.codTecnomen = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_TECNOMEN")).value_string;
//                    vRow.contrasena = (((dbROW) dt.Datos.rows.get(i)).getColByName("PASS")).value_string;
//                    vRow.descripcion = (((dbROW) dt.Datos.rows.get(i)).getColByName("DESCRIPTION")).value_string;
//                    vRow.dirConciliacion = (((dbROW) dt.Datos.rows.get(i)).getColByName("CONCI_PATH")).value_string;
//                    vRow.dirConciliacionRespuesta = (((dbROW) dt.Datos.rows.get(i)).getColByName("CONCI_RESPONSE_PATH")).value_string;
//                    vRow.idBanco = (((dbROW) dt.Datos.rows.get(i)).getColByName("ID_BANCO")).value_string;
//                    vRow.timeOut = (((dbROW) dt.Datos.rows.get(i)).getColByName("TIME_OUT")).value_int;
//                    vRow.usuario = (((dbROW) dt.Datos.rows.get(i)).getColByName("USER_NAME")).value_string;
//                    vRow.vAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_AGENCIA")).value_string;
//                    vRow.vCaja = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_CAJA")).value_string;
//                    vRow.vCajero = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_CAJERO")).value_string;
//                    vRow.vCodCajeroSupervisor = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_SUPERVISOR")).value_string;
//                    vResponse.Datos.add(vRow);
//                }
//                vResponse.vEstado = 1;
//            }
//        } catch (Exception e) {
//            vResponse.vEstado = -1;
//            vResponse.vMensaje = e.getMessage() + dt.vDBMessage;
//        }
//        return vResponse;
//    }


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
