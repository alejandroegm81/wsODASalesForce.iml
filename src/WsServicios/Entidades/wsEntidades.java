package WsServicios.Entidades;

import FromNET.ConnectTo;
import FromNET.GlobalDB;
import FromNET.GlobalDBParamObject;
import FromNET.GlobalDBParamObjectList;
import FromNET.SubDataTable;
import ServiceActionsDB.dbOperations;
import ServiceActionsDB.dbROW;
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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.sql.PreparedStatement;


@WebService(serviceName = "wsEntidades", targetNamespace = "")
public class wsEntidades
        extends BaseClass {
    @WebMethod(operationName = "gAgencias")
    public wsR_Agencias gAgencias(@WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia) {
        wsR_Agencias vResponse = new wsR_Agencias();
        SubDataTable dt = new SubDataTable();

        try {
            GlobalDB db = new GlobalDB();
            dt = db.getQuery("select COD_AGENCIA, NOM_AGENCIA, CLIENTE_SINERGIA COD_SINERGIA from agencias where envio_gl='S' order by nom_agencia", getConnectTo(Instancia));
            if (dt.vData.booleanValue() && dt.vRows>1) {
                for (int i = 1; i < dt.vRows; i++) {
                    wsR_Agencia vRow = new wsR_Agencia();
                    vRow.codAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_AGENCIA")).value_string;
                    vRow.nomAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("NOM_AGENCIA")).value_string;
                    vRow.codSinergia = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_SINERGIA")).value_string;
                    vResponse.Datos.add( vRow );
                }
                vResponse.vEstado = 1;
            }
        } catch (Exception e) {
            vResponse.vEstado = -1;
            vResponse.vMensaje = e.getMessage() + dt.vDBMessage;
        }
        return vResponse;
    }


    private boolean isPassValid(  String value ){
        boolean vResult=false;
        try{
            char[] vVals = value.toCharArray();
            int vAsc=0;
            for(int i=0;i<vVals.length;i++){
                vAsc = (int) vVals[i];
                if(vAsc<=20){vResult=false;break;}else{vResult=true;}
            }
            } catch(Exception e){vResult=false;}
        return vResult;
    }

    @WebMethod(operationName = "gCajeros")
    public wsR_Cajeros gCajeros(@WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia, @WebParam(name = "vAgencia") String vAgencia) {
        wsR_Cajeros vResponse = new wsR_Cajeros();
        SubDataTable dt = new SubDataTable();

        try {
            GlobalDB db = new GlobalDB();
            GlobalDBParamObjectList vParams = new GlobalDBParamObjectList();
            vParams.Add(new GlobalDBParamObject("AGENCIA", vAgencia));
            dt = db.getQuery("select COD_AGENCIA, COD_CAJERO, COD_EMPLEADO, COD_GESTOR_SAS, SECURITYUTILS.DECRYPT(PASSWORD) PASSWORD, CUENTA_BLOQUEADA, TO_CHAR(FECHA_BLOQUEO,'DD/MM/YYYY') FECHA_BLOQUEO, TO_CHAR(PASSWORD_EXPIRA,'DD/MM/YYYY') PASSWORD_EXPIRA, NOM_CAJERO, TIPO_USUARIO from cajeros where cod_agencia=? order by cod_cajero", vParams, getConnectTo(Instancia));
            if (dt.vData.booleanValue() && dt.vRows>1) {
                for (int i = 1; i < dt.vRows; i++) {
                    wsR_Cajero vRow = new wsR_Cajero();
                    vRow.codAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_AGENCIA")).value_string;
                    vRow.codCajero = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_CAJERO")).value_string;
                    vRow.codEmpleado = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_EMPLEADO")).value_string;
                    vRow.codGestorCRM = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_GESTOR_SAS")).value_string;
                    if( isPassValid((((dbROW) dt.Datos.rows.get(i)).getColByName("PASSWORD")).value_string) ){
                        vRow.contrasena =    (((dbROW) dt.Datos.rows.get(i)).getColByName("PASSWORD")).value_string;
                    }else{vRow.contrasena =    "*********";}
                    vRow.cuentaBloqueada = (((dbROW) dt.Datos.rows.get(i)).getColByName("CUENTA_BLOQUEADA")).value_int;
                    vRow.fechaBloqueo = (((dbROW) dt.Datos.rows.get(i)).getColByName("FECHA_BLOQUEO")).value_string;
                    vRow.fechaContrasenaExpira = (((dbROW) dt.Datos.rows.get(i)).getColByName("PASSWORD_EXPIRA")).value_string;
                    vRow.nomCajero = (((dbROW) dt.Datos.rows.get(i)).getColByName("NOM_CAJERO")).value_string;
                    vRow.tipoCajero = (((dbROW) dt.Datos.rows.get(i)).getColByName("TIPO_USUARIO")).value_string;
                    vResponse.Datos.add(vRow);
                }
                vResponse.vEstado = 1;
            }
        } catch (Exception e) {
            vResponse.vEstado = -1;
            vResponse.vMensaje = e.getMessage() + dt.vDBMessage;
        }
        return vResponse;
    }

    @WebMethod(operationName = "gCajas")
    public wsR_Cajas gCajas(@WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia, @WebParam(name = "vAgencia") String vAgencia) {
        wsR_Cajas vResponse = new wsR_Cajas();
        SubDataTable dt = new SubDataTable();

        try {
            GlobalDB db = new GlobalDB();
            GlobalDBParamObjectList vParams = new GlobalDBParamObjectList();
            vParams.Add(new GlobalDBParamObject("AGENCIA", vAgencia));
            dt = db.getQuery("select COD_AGENCIA, COD_CAJA, NOM_CAJA, DIREC_IP, NUM_REGISTRO, CGUICHET, TTY from cajas where cod_agencia=? order by cod_caja asc", vParams, getConnectTo(Instancia));
            if (dt.vData.booleanValue() && dt.vRows>1) {
                for (int i = 1; i < dt.vRows; i++) {
                    wsR_Caja vRow = new wsR_Caja();
                    vRow.codAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_AGENCIA")).value_string;
                    vRow.cguichet = (((dbROW) dt.Datos.rows.get(i)).getColByName("CGUICHET")).value_string;
                    vRow.codCaja = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_CAJA")).value_string;
                    vRow.direcIP = (((dbROW) dt.Datos.rows.get(i)).getColByName("DIREC_IP")).value_string;
                    vRow.nomCaja = (((dbROW) dt.Datos.rows.get(i)).getColByName("NOM_CAJA")).value_string;
                    vRow.numRegistro = (((dbROW) dt.Datos.rows.get(i)).getColByName("NUM_REGISTRO")).value_string;
                    vRow.tty = (((dbROW) dt.Datos.rows.get(i)).getColByName("TTY")).value_string;
                    vResponse.Datos.add(vRow);
                }
                vResponse.vEstado = 1;
            }
        } catch (Exception e) {
            vResponse.vEstado = -1;
            vResponse.vMensaje = e.getMessage() + dt.vDBMessage;
        }
        return vResponse;
    }

    @WebMethod(operationName = "gBancos")
    public wsR_Bancos gBancos(@WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia) {
        wsR_Bancos vResponse = new wsR_Bancos();
        SubDataTable dt = new SubDataTable();

        try {
            GlobalDB db = new GlobalDB();
            dt = db.getQuery("select ACTIVE ACTIVO, CODE_BANK , COD_CLAROTV, 1 COD_MONEDA, COD_TECNOMEN, PASS, DESCRIPTION,  CONCI_PATH, CONCI_RESPONSE_PATH, ID_BANCO, TIME_OUT, USER_NAME, COD_AGENCIA, COD_CAJA, COD_CAJERO, COD_SUPERVISOR   from list_banks order by ID_BANCO asc", getConnectTo(Instancia));
            if (dt.vData.booleanValue() && dt.vRows>1) {
                for (int i = 1; i < dt.vRows; i++) {
                    wsR_Banco vRow = new wsR_Banco();
                    vRow.activo = (((dbROW) dt.Datos.rows.get(i)).getColByName("ACTIVO")).value_string;
                    vRow.codBanco = (((dbROW) dt.Datos.rows.get(i)).getColByName("CODE_BANK")).value_string;
                    vRow.codClaroTV = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_CLAROTV")).value_string;
                    vRow.codMoneda = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_MONEDA")).value_int;
                    vRow.codTecnomen = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_TECNOMEN")).value_string;
                    vRow.contrasena = (((dbROW) dt.Datos.rows.get(i)).getColByName("PASS")).value_string;
                    vRow.descripcion = (((dbROW) dt.Datos.rows.get(i)).getColByName("DESCRIPTION")).value_string;
                    vRow.dirConciliacion = (((dbROW) dt.Datos.rows.get(i)).getColByName("CONCI_PATH")).value_string;
                    vRow.dirConciliacionRespuesta = (((dbROW) dt.Datos.rows.get(i)).getColByName("CONCI_RESPONSE_PATH")).value_string;
                    vRow.idBanco = (((dbROW) dt.Datos.rows.get(i)).getColByName("ID_BANCO")).value_string;
                    vRow.timeOut = (((dbROW) dt.Datos.rows.get(i)).getColByName("TIME_OUT")).value_int;
                    vRow.usuario = (((dbROW) dt.Datos.rows.get(i)).getColByName("USER_NAME")).value_string;
                    vRow.vAgencia = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_AGENCIA")).value_string;
                    vRow.vCaja = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_CAJA")).value_string;
                    vRow.vCajero = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_CAJERO")).value_string;
                    vRow.vCodCajeroSupervisor = (((dbROW) dt.Datos.rows.get(i)).getColByName("COD_SUPERVISOR")).value_string;
                    vResponse.Datos.add(vRow);
                }
                vResponse.vEstado = 1;
            }
        } catch (Exception e) {
            vResponse.vEstado = -1;
            vResponse.vMensaje = e.getMessage() + dt.vDBMessage;
        }
        return vResponse;
    }

    @WebMethod(operationName = "sAgencias")
    public SubDataTable sAgencias(@WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia, @WebParam(name = "vDatosAgencia") wsR_Agencia vDatosAgencia, @WebParam(name = "vTipoAccionMantto") wsTipoAccionManttos.wsTipoAccionMantto vTipoAccion) {
        SubDataTable dt = new SubDataTable();
        String vQuery = "";

        try {
            GlobalDB db = new GlobalDB();
            GlobalDBParamObjectList vParams = new GlobalDBParamObjectList();
            switch (vTipoAccion) {
                case A:
                    if(Instancia.equals( wsInstancias.wsInstancia.ODA_504 ) ) {
                        vQuery = "insert into agencias (cod_agencia, nom_agencia, cliente_sinergia, tipo_agencia, envio_gl, cod_munic, cod_depto) values(?,?,?,'B','S','1','1')";
                    }else{
                        vQuery = "insert into agencias (cod_agencia, nom_agencia, cliente_sinergia, tipo_agencia, envio_gl) values(?,?,?,'B','S')";
                    }
                    vParams.Add(new GlobalDBParamObject("COD_AGENCIA", vDatosAgencia.codAgencia));
                    vParams.Add(new GlobalDBParamObject("NOM_AGENCIA", vDatosAgencia.nomAgencia));
                    vParams.Add(new GlobalDBParamObject("CLIENTE_SINERGIA", vDatosAgencia.codSinergia));
                    break;
                case E:
                    vQuery = "update agencias set nom_agencia=?,  cliente_sinergia=? where cod_agencia=?";
                    vParams.Add(new GlobalDBParamObject("NOM_AGENCIA", vDatosAgencia.nomAgencia));
                    vParams.Add(new GlobalDBParamObject("CLIENTE_SINERGIA", vDatosAgencia.codSinergia));
                    vParams.Add(new GlobalDBParamObject("COD_AGENCIA", vDatosAgencia.codAgencia));
                    break;
                case X:
                    //throw new Exception("Actualmente se encuentra desactivada la opcion");
                    vQuery = "delete from agencias where cod_agencia=?";
                    vParams.Add(new GlobalDBParamObject("COD_AGENCIA", vDatosAgencia.codAgencia));
                    break;

            }

            if(Instancia.equals( wsInstancias.wsInstancia.ODA_504 ) ){ vQuery = vQuery.replace("agencias","agencias_c"); }
            dt = db.setQuery(vQuery, vParams, getConnectTo(Instancia));
            if(Instancia.equals( wsInstancias.wsInstancia.ODA_504 ) ){
                GlobalDBParamObjectList vL = new GlobalDBParamObjectList();
                db.setQuery( "begin \n dbms_mview.refresh('AGENCIAS'); \n end;", vL,  getConnectTo(Instancia) );
            }

        } catch (Exception e) {
            dt.vData = Boolean.valueOf(false);
            dt.vRows = 0;
            dt.vMessage = e.getMessage() + dbOperations.getErrMsg();
        }
        return dt;
    }

    @WebMethod(operationName = "sCajeros")
    public SubDataTable sCajeros(@WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia, @WebParam(name = "vDatosCajero") wsR_Cajero vDatosCajero, @WebParam(name = "vTipoAccionMantto") wsTipoAccionManttos.wsTipoAccionMantto vTipoAccion) {
        SubDataTable dt = new SubDataTable();
        String vQuery = "";

        try {
            GlobalDB db = new GlobalDB();
            GlobalDBParamObjectList vParams = new GlobalDBParamObjectList();
            switch (vTipoAccion) {
                case A:
                    vQuery = "insert into cajeros (COD_CAJERO, COD_AGENCIA, NOM_CAJERO, TIPO_USUARIO, COD_EMPLEADO, CUENTA_BLOQUEADA, PASSWORD, PASSWORD_EXPIRA,COD_GESTOR_SAS  , COD_EST_CAJ, LOGIN_VALID_GAIA) values(?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,'1 ',1 )";
                    vParams.Add(new GlobalDBParamObject("COD_CAJERO", vDatosCajero.codCajero));
                    vParams.Add(new GlobalDBParamObject("COD_AGENCIA", vDatosCajero.codAgencia));
                    vParams.Add(new GlobalDBParamObject("NOM_CAJERO", vDatosCajero.nomCajero));
                    vParams.Add(new GlobalDBParamObject("TIPO_USUARIO", vDatosCajero.tipoCajero));
                    vParams.Add(new GlobalDBParamObject("COD_EMPLEADO", vDatosCajero.codEmpleado));
                    vParams.Add(new GlobalDBParamObject("CUENTA_BLOQUEADA", Integer.valueOf(vDatosCajero.cuentaBloqueada)));
                    vParams.Add(new GlobalDBParamObject("PASSWORD", vDatosCajero.contrasena));
                    vParams.Add(new GlobalDBParamObject("PASSWORD_EXPIRA", vDatosCajero.fechaContrasenaExpira));
                    vParams.Add(new GlobalDBParamObject("COD_GESTOR_SAS", vDatosCajero.codGestorCRM));
                    break;
                case E:
                    vQuery = "update cajeros set NOM_CAJERO=?, TIPO_USUARIO=?, CUENTA_BLOQUEADA=?, PASSWORD=SecurityUtils.encrypt(?), PASSWORD_EXPIRA=to_date(?,'dd/MM/yyyy'), fecha_bloqueo=to_date(?,'dd/MM/yyyy'), cod_gestor_sas=? where cod_cajero=? and cod_agencia=? ";
                    vParams.Add(new GlobalDBParamObject("NOM_CAJERO", vDatosCajero.nomCajero));
                    vParams.Add(new GlobalDBParamObject("TIPO_USUARIO", vDatosCajero.tipoCajero));
                    vParams.Add(new GlobalDBParamObject("CUENTA_BLOQUEADA", Integer.valueOf(vDatosCajero.cuentaBloqueada)));
                    vParams.Add(new GlobalDBParamObject("PASSWORD", vDatosCajero.contrasena));
                    vParams.Add(new GlobalDBParamObject("PASSWORD_EXPIRA", vDatosCajero.fechaContrasenaExpira));
                    vParams.Add(new GlobalDBParamObject("FECHA_BLOQUEO", vDatosCajero.fechaBloqueo));
                    vParams.Add(new GlobalDBParamObject("COD_GESTOR_SAS", vDatosCajero.codGestorCRM));

                    vParams.Add(new GlobalDBParamObject("COD_CAJERO", vDatosCajero.codCajero));
                    vParams.Add(new GlobalDBParamObject("COD_AGENCIA", vDatosCajero.codAgencia));
                    break;
                case X:
                    //throw new Exception("Actualmente se encuentra desactivada la opcion");
                    vQuery = "delete from cajeros where cod_cajero=? and cod_agencia=? ";
                    vParams.Add(new GlobalDBParamObject("COD_CAJERO", vDatosCajero.codCajero));
                    vParams.Add(new GlobalDBParamObject("COD_AGENCIA", vDatosCajero.codAgencia));
                    break;
            }

            if(Instancia.equals( wsInstancias.wsInstancia.ODA_504 ) ){ vQuery = vQuery.replace("cajeros","cajeros_c"); }
            dt = db.setQuery(vQuery, vParams, getConnectTo(Instancia));
            if(Instancia.equals( wsInstancias.wsInstancia.ODA_504 ) ){
                GlobalDBParamObjectList vL = new GlobalDBParamObjectList();
                db.setQuery( "begin \n dbms_mview.refresh('CAJEROS'); \n end;", vL,  getConnectTo(Instancia) );
            }

        } catch (Exception e) {
            dt.vData = Boolean.valueOf(false);
            dt.vRows = 0;
            dt.vMessage = e.getMessage() + dbOperations.getErrMsg();
        }
        return dt;
    }

    @WebMethod(operationName = "sCajas")
    public SubDataTable sCajas(@WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia, @WebParam(name = "vDatosCaja") wsR_Caja vDatosCaja, @WebParam(name = "vTipoAccionMantto") wsTipoAccionManttos.wsTipoAccionMantto vTipoAccion) {
        SubDataTable dt = new SubDataTable();
        String vQuery = "";

        try {
            GlobalDB db = new GlobalDB();
            GlobalDBParamObjectList vParams = new GlobalDBParamObjectList();
            switch (vTipoAccion) {
                case A:
                    vQuery = "insert into cajas (COD_CAJA, COD_AGENCIA, NOM_CAJA, DIREC_IP,  NUM_REGISTRO, CGUICHET, TTY , EST_CAJA, TIPO_CAJA) values(?,?,?,?,?,?,?,  'A','D')";
                    vParams.Add(new GlobalDBParamObject("COD_CAJA", vDatosCaja.codCaja));
                    vParams.Add(new GlobalDBParamObject("COD_AGENCIA", vDatosCaja.codAgencia));
                    vParams.Add(new GlobalDBParamObject("NOM_CAJA", vDatosCaja.nomCaja));
                    vParams.Add(new GlobalDBParamObject("DIREC_IP", vDatosCaja.direcIP));
                    vParams.Add(new GlobalDBParamObject("NUM_REGISTRO", vDatosCaja.numRegistro));
                    vParams.Add(new GlobalDBParamObject("CGUICHET", vDatosCaja.cguichet));
                    vParams.Add(new GlobalDBParamObject("TTY", vDatosCaja.tty));
                    break;
                case E:
                    vQuery = "update cajas set NOM_CAJA=?, DIREC_IP=?, NUM_REGISTRO=?, CGUICHET=?, TTY=?  where cod_caja=? and cod_agencia=? ";
                    vParams.Add(new GlobalDBParamObject("NOM_CAJA", vDatosCaja.nomCaja));
                    vParams.Add(new GlobalDBParamObject("DIREC_IP", vDatosCaja.direcIP));
                    vParams.Add(new GlobalDBParamObject("NUM_REGISTRO", vDatosCaja.numRegistro));
                    vParams.Add(new GlobalDBParamObject("CGUICHET", vDatosCaja.cguichet));
                    vParams.Add(new GlobalDBParamObject("TTY", vDatosCaja.tty));

                    vParams.Add(new GlobalDBParamObject("COD_CAJA", vDatosCaja.codCaja));
                    vParams.Add(new GlobalDBParamObject("COD_AGENCIA", vDatosCaja.codAgencia));
                    break;
                case X:
                    //throw new Exception("Actualmente se encuentra desactivada la opcion");
                    vQuery = "delete from cajas where cod_caja=? and cod_agencia=? ";
                    vParams.Add(new GlobalDBParamObject("COD_CAJA", vDatosCaja.codCaja));
                    vParams.Add(new GlobalDBParamObject("COD_AGENCIA", vDatosCaja.codAgencia));

            }

            if(Instancia.equals( wsInstancias.wsInstancia.ODA_504 ) ){ vQuery = vQuery.replace("cajas","cajas_c"); }
            dt = db.setQuery(vQuery, vParams, getConnectTo(Instancia));
            if(Instancia.equals( wsInstancias.wsInstancia.ODA_504 ) ){
                GlobalDBParamObjectList vL = new GlobalDBParamObjectList();
                db.setQuery( "begin \n dbms_mview.refresh('CAJAS'); \n end;", vL,  getConnectTo(Instancia) );
            }

        } catch (Exception e) {
            dt.vData = Boolean.valueOf(false);
            dt.vRows = 0;
            dt.vMessage = e.getMessage() + dbOperations.getErrMsg();
        }
        return dt;
    }

    @WebMethod(operationName = "sBancos")
    public SubDataTable sBancos(@WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia, @WebParam(name = "vDatosBanco") wsR_Banco vDatosBanco, @WebParam(name = "vTipoAccionMantto") wsTipoAccionManttos.wsTipoAccionMantto vTipoAccion) {
        SubDataTable dt = new SubDataTable();
        String vQuery = "";

        try {
            GlobalDB db = new GlobalDB();
            GlobalDBParamObjectList vParams = new GlobalDBParamObjectList();
            switch (vTipoAccion) {
                case A:
                    vQuery = "insert into LIST_BANKS ( ID_BANCO, DESCRIPTION, USER_NAME, PASS, ACTIVE, CODE_BANK, COD_AGENCIA, COD_CAJA, COD_CAJERO, COD_CLAROTV, COD_TECNOMEN, TIME_OUT, COD_SUPERVISOR, CONCI_PATH, CONCI_RESPONSE_PATH   ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    vParams.Add(new GlobalDBParamObject("ID_BANCO", vDatosBanco.idBanco));
                    vParams.Add(new GlobalDBParamObject("DESCRIPTION", vDatosBanco.descripcion));
                    vParams.Add(new GlobalDBParamObject("USUARIO", vDatosBanco.usuario));
                    vParams.Add(new GlobalDBParamObject("PASS", vDatosBanco.contrasena));
                    vParams.Add(new GlobalDBParamObject("ACTIVE", vDatosBanco.activo));
                    vParams.Add(new GlobalDBParamObject("CODE_BANK", vDatosBanco.codBanco));
                    vParams.Add(new GlobalDBParamObject("COD_AGENCIA", vDatosBanco.vAgencia));
                    vParams.Add(new GlobalDBParamObject("COD_CAJA", vDatosBanco.vCaja));
                    vParams.Add(new GlobalDBParamObject("COD_CAJERO", vDatosBanco.vCajero));
                    vParams.Add(new GlobalDBParamObject("COD_CLAROTV", vDatosBanco.codClaroTV));
                    vParams.Add(new GlobalDBParamObject("COD_TECNOMEN", vDatosBanco.codTecnomen));
                    vParams.Add(new GlobalDBParamObject("TIMEOUT", Integer.valueOf(vDatosBanco.timeOut)));
                    vParams.Add(new GlobalDBParamObject("COD_SUPERVISOR", vDatosBanco.vCodCajeroSupervisor));
                    vParams.Add(new GlobalDBParamObject("CONCI_PATH", vDatosBanco.dirConciliacion));
                    vParams.Add(new GlobalDBParamObject("CONCI_PATH_RESPONSE", vDatosBanco.dirConciliacionRespuesta));
                    break;
                case E:
                    vQuery = "UPDATE LIST_BANKS SET  DESCRIPTION=?, USER_NAME=?, PASS=?, ACTIVE=?, CODE_BANK=?, COD_AGENCIA=?, COD_CAJA=?, COD_CAJERO=?, COD_CLAROTV=?, COD_TECNOMEN=?, TIME_OUT=?, COD_SUPERVISOR=?, CONCI_PATH=?, CONCI_RESPONSE_PATH =?  WHERE ID_BANCO=?";
                    vParams.Add(new GlobalDBParamObject("DESCRIPTION", vDatosBanco.descripcion));
                    vParams.Add(new GlobalDBParamObject("USUARIO", vDatosBanco.usuario));
                    vParams.Add(new GlobalDBParamObject("PASS", vDatosBanco.contrasena));
                    vParams.Add(new GlobalDBParamObject("ACTIVE", vDatosBanco.activo));
                    vParams.Add(new GlobalDBParamObject("CODE_BANK", vDatosBanco.codBanco));
                    vParams.Add(new GlobalDBParamObject("COD_AGENCIA", vDatosBanco.vAgencia));
                    vParams.Add(new GlobalDBParamObject("COD_CAJA", vDatosBanco.vCaja));
                    vParams.Add(new GlobalDBParamObject("COD_CAJERO", vDatosBanco.vCajero));
                    vParams.Add(new GlobalDBParamObject("COD_CLAROTV", vDatosBanco.codClaroTV));
                    vParams.Add(new GlobalDBParamObject("COD_TECNOMEN", vDatosBanco.codTecnomen));
                    vParams.Add(new GlobalDBParamObject("TIMEOUT", Integer.valueOf(vDatosBanco.timeOut)));
                    vParams.Add(new GlobalDBParamObject("COD_SUPERVISOR", vDatosBanco.vCodCajeroSupervisor));
                    vParams.Add(new GlobalDBParamObject("CONCI_PATH", vDatosBanco.dirConciliacion));
                    vParams.Add(new GlobalDBParamObject("CONCI_PATH_RESPONSE", vDatosBanco.dirConciliacionRespuesta));

                    vParams.Add(new GlobalDBParamObject("ID_BANCO", vDatosBanco.idBanco));
                    break;
                case X:
                    vQuery = "DELETE FROM LIST_BANKS  WHERE ID_BANCO=?";
                    vParams.Add(new GlobalDBParamObject("ID_BANCO", vDatosBanco.idBanco));
            }


            dt = db.setQuery(vQuery, vParams, getConnectTo(Instancia));
        } catch (Exception e) {
            dt.vData = Boolean.valueOf(false);
            dt.vRows = 0;
            dt.vMessage = e.getMessage() + dbOperations.getErrMsg();
        }
        return dt;
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
