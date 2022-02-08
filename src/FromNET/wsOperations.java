package FromNET;

import FromNET.g.Transacc_Session;
import FromNET.g.g_info_payment;
import FromNET.g.global_apply_payments_forms;
import ServiceActionsDB.dbAUTH;
import ServiceActionsDB.dbCOL;
import ServiceActionsDB.dbDATA;
import ServiceActionsDB.dbROW;
import WsServiceActions.wsGeneralResponse;
import db.BaseClass;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import oracle.jdbc.OracleConnection;






public class wsOperations
  extends BaseClass
{
  public Transacc_Session Ses = new Transacc_Session();

  
  public wsGeneralResponse Authenticate(dbAUTH Info) {
    wsGeneralResponse vResponse = new wsGeneralResponse();
    int ResponseAuth = 0;
    
    try { 
      try { GlobalDB db = new GlobalDB();
        OracleConnection _ODA = db.getODADBConnection(ConnectTo.ODA_db);
        CallableStatement cl = _ODA.prepareCall("{? =  call WS_EXTERNAL.ws_authenticate(?,?,?)}");
        cl.registerOutParameter(1, 4);
        cl.setString(2, Info.IP);
        cl.setString(3, Info.user);
        cl.setString(4, Info.password);
        cl.execute();
        ResponseAuth = cl.getInt(1);
        cl.close(); 
        try { _ODA.close(); } catch (Exception exception) {}
        switch (ResponseAuth) { case 0:
            vResponse.vMessage = "Ocurrio un error al autenticar. Favor revisar con soporte."; break;
          case -1: vResponse.vMessage = "No fue posible autenticar, favor revise ip-usuario-contrasena"; break; }
        
        if (ResponseAuth > 0) {
          SubDataTable dt = db.getQuery("select t.*, to_char(t.fec_real_pago,'dd/MM/yyyy') fecha from movim_diarios t where corre=" + ResponseAuth, ConnectTo.ODA_db);
          if (dt.vData.booleanValue() && dt.vRows > 1) {
            this.Ses.Fec_Real_Pago = (((dbROW)dt.Datos.rows.get(1)).getColByName("FECHA")).value_string;
            this.Ses.Cod_Cajero = (((dbROW)dt.Datos.rows.get(1)).getColByName("COD_CAJERO")).value_string;
            this.Ses.Cod_Caja = (((dbROW)dt.Datos.rows.get(1)).getColByName("COD_CAJA")).value_string;
            this.Ses.Cod_Agencia_Rec = (((dbROW)dt.Datos.rows.get(1)).getColByName("COD_AGENCIA_PROC")).value_string;
            this.Ses.Cod_Agencia = (((dbROW)dt.Datos.rows.get(1)).getColByName("COD_AGENCIA")).value_string;
            this.Ses.Sesion_Correlativo = (((dbROW)dt.Datos.rows.get(1)).getColByName("CORRE")).value_string;
            vResponse.vResult = Boolean.valueOf(true);
          } 
        }  }
      catch (Exception e) { setErrMsg(e); vResponse.vMessage = "Error: " + e.getMessage(); }  }
    catch (Exception e) { setErrMsg(e); vResponse.vMessage = "Error: " + e.getMessage(); }
     return vResponse;
  }



  
  public SubDataTable GetFindClienteInfo(dbAUTH Info, String vcod_operador, String vNombre, String vTelefono, String vReferencia, String vCedula) {
    SubDataTable dt = new SubDataTable();
    SubDataTable dtBarra = new SubDataTable();
    String vQuery = "", vQueryParameters = "";
    Boolean vQueryParametersAssigned = Boolean.valueOf(false);
    String vBaseParameters = "";
    
    Boolean vScriptFound = Boolean.valueOf(false);
    String vEmpresa = "", vMontoNPE = "";

    
    try { Boolean Auth = (Authenticate(Info)).vResult;
      if (!Auth.booleanValue()) throw new Exception("Ocurrio un error en la autenticacion del usuario.");

      
      GlobalDB db = new GlobalDB();

      
      vQuery = "select * from OPERADORES_EMPRESAS where cod_operador = '" + vcod_operador + "' and operacion='BB'";
      dt = db.getQuery(vQuery, ConnectTo.ODA_db);
      
      if (dt.vData.booleanValue() && dt.vRows>1) {
        vScriptFound = Boolean.valueOf(true);
        vQuery = (((dbROW)dt.Datos.rows.get(1)).getColByName("SCRIPT")).value_string;
        vBaseParameters = (((dbROW)dt.Datos.rows.get(1)).getColByName("PARAMETROS")).value_string;
        vEmpresa = (((dbROW)dt.Datos.rows.get(1)).getColByName("COD_EMPRESA")).value_string;
        String[] vParameters = vBaseParameters.split("&");
        if (vReferencia.length() > 0 && vEmpresa.equals("BSCS")) vQueryParametersAssigned = Boolean.valueOf(true); 
        if (vParameters != null && 
          vParameters.length > 0) {
          for (int i = 0; i < vParameters.length; i++) {
            if (vParameters[i].toLowerCase().indexOf("vnombre") >= 0 && 
              vNombre.length() > 1) {
              vQueryParameters = vQueryParameters + " and " + vParameters[i].toLowerCase().replace("vnombre", "'" + vNombre.replace(" ", "%") + "'");
              vQueryParametersAssigned = Boolean.valueOf(true);
            } 
            
            if (vParameters[i].toLowerCase().indexOf("vtelefono") >= 0 && 
              vTelefono.length() > 1) {
              vQueryParameters = vQueryParameters + " and " + vParameters[i].toLowerCase().replace("vtelefono", "'" + vTelefono + "'");
              vQueryParametersAssigned = Boolean.valueOf(true);
            } 
            
            if (vParameters[i].toLowerCase().indexOf("vreferencia") >= 0 && 
              vReferencia.length() > 1) {
              vQueryParameters = vQueryParameters + " and " + vParameters[i].toLowerCase().replace("vreferencia", "'" + vReferencia + "'");
              vQueryParametersAssigned = Boolean.valueOf(true);
            } 
            
            if (vParameters[i].toLowerCase().indexOf("vcedula") >= 0 && 
              vCedula.length() > 1) {
              if (vEmpresa.equals("BSCS")) {
                vQueryParameters = vQueryParameters + " and " + vParameters[i].toLowerCase().replace("vcedula", "'" + vCedula.trim() + "'");
              } else {
                vQueryParameters = vQueryParameters + " and " + vParameters[i].toLowerCase().replace("vcedula", "'" + vCedula.replace("-", "%").trim() + "'");
              } 
              vQueryParametersAssigned = Boolean.valueOf(true);
            } 
          } 
        }
      } 


      
      if (!vQueryParametersAssigned.booleanValue()) throw new Exception("Sin parametros de busqueda");
      
      if (vScriptFound.booleanValue()) {
        vQuery = vQuery + vQueryParameters;
        if (vEmpresa.equals("BSCSFU")) {
          dt = db.getQuery(vQuery, ConnectTo.BSCSFU_db);
        }
        if (vEmpresa.equals("BSCS")) {
          dt = db.getQuery(vQuery, ConnectTo.BSCS_db);
        }
        if (vEmpresa.equals("OPEN")) {
          vQuery = vQuery + vQueryParameters;
          dt = db.getQuery(vQuery, ConnectTo.OPEN_db);
        } 
        if (vEmpresa.equals("SAP") || vEmpresa.equals("TECNO")) dt = db.getQuery(vQuery, ConnectTo.ODA_db); 
      }  }
    catch (Exception e) { setErrMsg(e); dt.vDBMessage = e.getMessage(); }
     return dt;
  }




  
  public SubDataTable GetFindCliente(dbAUTH Info, String vcod_operador, String vcliente, String vtelefono, String vbarra) {
    SubDataTable dt = new SubDataTable();
    SubDataTable dtBarra = new SubDataTable();
    String vQuery = "", vQueryParameters = "";
    Boolean vQueryParametersAssigned = Boolean.valueOf(false);
    String vBaseParameters = "";
    
    Boolean vScriptFound = Boolean.valueOf(false);
    String vEmpresa = "", vMontoNPE = "";
    try {
      Boolean Auth = (Authenticate(Info)).vResult;
      if (!Auth.booleanValue()) throw new Exception("Ocurrio un error en la autenticacion del usuario.");




      
      GlobalDB db = new GlobalDB();













      
      vQuery = "select * from OPERADORES_EMPRESAS where cod_operador = '" + vcod_operador + "' and operacion='CL'";
      dt = db.getQuery(vQuery, ConnectTo.ODA_db);
      
      if (dt.vData.booleanValue() && dt.vRows>1) {
        vScriptFound = Boolean.valueOf(true);
        vQuery = (((dbROW)dt.Datos.rows.get(1)).getColByName("SCRIPT")).value_string;
        vBaseParameters = (((dbROW)dt.Datos.rows.get(1)).getColByName("PARAMETROS")).value_string;
        vEmpresa = (((dbROW)dt.Datos.rows.get(1)).getColByName("COD_EMPRESA")).value_string;
        String[] vParameters = vBaseParameters.split("&");
        if (vParameters != null && 
          vParameters.length > 0) {
          for (int i = 0; i < vParameters.length; i++) {
            if (vParameters[i].toLowerCase().indexOf("vcliente") >= 0 && 
              vcliente.length() > 1) {
              vQueryParameters = vQueryParameters + " and " + vParameters[i].toLowerCase().replace("vcliente", "'" + vcliente + "'");
              vQueryParametersAssigned = Boolean.valueOf(true);
            } 
            
            if (vParameters[i].toLowerCase().indexOf("vtelefono") >= 0 && 
              vtelefono.length() > 1) {
              vQueryParameters = vQueryParameters + " and " + vParameters[i].toLowerCase().replace("vtelefono", "'" + vtelefono + "'");
              vQueryParametersAssigned = Boolean.valueOf(true);
            } 
          } 
        }
      } 

      
      if (!vQueryParametersAssigned.booleanValue()) throw new Exception("Sin parametros de busqueda");
      
      if (vScriptFound.booleanValue()) {
        vQuery = vQuery + vQueryParameters;
        if (vEmpresa.equals("BSCSFU")) dt = db.getQuery(vQuery, ConnectTo.BSCSFU_db); 
        if (vEmpresa.equals("BSCS")) dt = db.getQuery(vQuery, ConnectTo.BSCS_db); 
        if (vEmpresa.equals("OPEN")) dt = db.getQuery(vQuery, ConnectTo.OPEN_db); 
        if (vEmpresa.equals("SAP") || vEmpresa.equals("TECNO")) dt = db.getQuery(vQuery, ConnectTo.ODA_db);
        

      
      }

    
    }
    catch (Exception e) {
      
      setErrMsg(e); dt.vDBMessage = e.getMessage();
    } 
    return dt;
  }


  
  public SubDataTable GetFindFactura(dbAUTH Info, String vcod_operador, String vcliente, String vtelefono, String vbarra) {
    SubDataTable dt = new SubDataTable();
    String vQuery = "", vQueryParameters = "";
    Boolean vQueryParametersAssigned = Boolean.valueOf(false);
    String vBaseParameters = "";
    
    Boolean vScriptFound = Boolean.valueOf(false);
    String vEmpresa = "";
    try {
      Boolean Auth = (Authenticate(Info)).vResult;
      if (!Auth.booleanValue()) throw new Exception("Ocurrio un error en la autenticacion del usuario.");




      
      GlobalDB db = new GlobalDB();
      
      vQuery = "select * from OPERADORES_EMPRESAS where cod_operador = '" + vcod_operador + "' and operacion='FF'";
      dt = db.getQuery(vQuery, ConnectTo.ODA_db);
      
      if (dt.vData.booleanValue() && dt.vRows > 1) {
        vScriptFound = Boolean.valueOf(true);
        vQuery = (((dbROW)dt.Datos.rows.get(1)).getColByName("SCRIPT")).value_string;
        vBaseParameters = (((dbROW)dt.Datos.rows.get(1)).getColByName("PARAMETROS")).value_string;
        vEmpresa = (((dbROW)dt.Datos.rows.get(1)).getColByName("COD_EMPRESA")).value_string;
        String[] vParameters = vBaseParameters.split(",");
        if (vParameters != null && 
          vParameters.length > 0) {
          for (int i = 0; i < vParameters.length; i++) {
            if (vParameters[i].toLowerCase().indexOf("vcliente") >= 0 && 
              vcliente.length() > 1) {
              vQueryParameters = vQueryParameters + " and " + vParameters[i].toLowerCase().replace("vcliente", "'" + vcliente + "'");
              vQueryParametersAssigned = Boolean.valueOf(true);
            } 
            
            if (vParameters[i].toLowerCase().indexOf("vtelefono") >= 0 && 
              vtelefono.length() > 1) {
              vQueryParameters = vQueryParameters + " and " + vParameters[i].toLowerCase().replace("vtelefono", "'" + vtelefono + "'");
              vQueryParametersAssigned = Boolean.valueOf(true);
            } 
          } 
        }
      } 

      
      if (!vQueryParametersAssigned.booleanValue()) throw new Exception("Sin parametros de busqueda");
      
      if (vScriptFound.booleanValue()) {
        vQuery = vQuery + vQueryParameters;
        if (vEmpresa.equals("BSCSFU")) dt = db.getQuery(vQuery, ConnectTo.BSCSFU_db); 
        if (vEmpresa.equals("BSCS")) dt = db.getQuery(vQuery, ConnectTo.BSCS_db); 
        if (vEmpresa.equals("OPEN")) dt = db.getQuery(vQuery, ConnectTo.OPEN_db); 
        if (vEmpresa.equals("SAP") || vEmpresa.equals("TECNO")) dt = db.getQuery(vQuery, ConnectTo.ODA_db);
      
      } 
    } catch (Exception e) {
      setErrMsg(e); dt.vDBMessage = e.getMessage();
    } 
    return dt;
  }



  
  public SubDataTable SetPayments(dbAUTH Info, global_apply_payments_forms[] payments_forms, g_info_payment[] payments) {
    SubDataTable dt = new SubDataTable();
    SubDataTable dt_ = new SubDataTable();
    SubDataTable dtCagencia = new SubDataTable();
    String vQuery = "", vBaseQuery = "", vQueryParameters = "";
    String vBaseParameters = "";
    
    Boolean vScriptFound = Boolean.valueOf(false);
    OracleConnection _ODA = null;
    OracleConnection _ODATran = null;
    OracleConnection _BSCSFUTran = null;
    OracleConnection _BSCSTran = null;
    OracleConnection _OPENTran = null;
    Boolean vAppliedFull = Boolean.valueOf(false);
    Boolean vError_BSCSFU = Boolean.valueOf(false), vBSCSFU_Applied = Boolean.valueOf(false);
    Boolean vError_BSCS = Boolean.valueOf(false), vBSCS_Applied = Boolean.valueOf(false);
    Boolean vError_OPEN = Boolean.valueOf(false), vOPEN_Applied = Boolean.valueOf(false);
    String vCodAgenciaOpen = "", vCodSucursalOpen = "";
    Boolean _connTo_bscs = Boolean.valueOf(false), _connTo_open = Boolean.valueOf(false), _connTo_bscsfu = Boolean.valueOf(false);
    String vOpenCuponValue = "";
    Boolean vIsAdvance = Boolean.valueOf(false);
    Boolean vApplyCodAgenciaColector = Boolean.valueOf(false);

    
    String T_CORR_TRANSAC_GROUP = "", T_CORR_TRANSAC_PAGO = "", T_CORR_ENC_TRANSAC = "", T_CORR_TRANSAC = "", T_BSCS_PAYMENT_API_ACCOUNT = "", T_BSCSFU_PAYMENT_API_ACCOUNT = "";
    
    try {
      Boolean Auth = (Authenticate(Info)).vResult;
      if (!Auth.booleanValue()) throw new Exception("Ocurrio un error en la autenticacion del usuario.");

      
      GlobalDB db = new GlobalDB();
      _ODA = db.getODADBConnection(ConnectTo.ODA_db);
      
      OracleConnection _BSCSFU = null;
      OracleConnection _BSCS = null;
      OracleConnection _OPEN = null;

      
      for (int i = 0; i < payments.length; i++) {
        switch ((payments[i])._operacion) {
          case "218":
            if (_connTo_bscsfu.booleanValue() != true) {
              _BSCSFU = db.getODADBConnection(ConnectTo.BSCSFU_db);
              _connTo_bscsfu = Boolean.valueOf(true);
            } 
            break;
          case "101":
            if (_connTo_bscs.booleanValue() != true) {
              _BSCS = db.getODADBConnection(ConnectTo.BSCS_db);
              _connTo_bscs = Boolean.valueOf(true);
            } 
            break;
          case "208":
            if (_connTo_open.booleanValue() != true) {
              _OPEN = db.getODADBConnection(ConnectTo.OPEN_db);
              _connTo_open = Boolean.valueOf(true);
            } 
            break;
        } 

      
      } 
      
      try { vQuery = "select T_CORR_TRANSAC_GROUP.nextval from dual";
        dt = db.getQuery(vQuery, _ODA);
        if (dt.vData.booleanValue() && dt.vRows>1) T_CORR_TRANSAC_GROUP = ((dbCOL)((dbROW)dt.Datos.rows.get(1)).cols.get(0)).value_string;
        vQuery = "select T_CORR_TRANSAC_PAGO.nextval from dual";
        dt = db.getQuery(vQuery, _ODA);
        if (dt.vData.booleanValue() && dt.vRows>1) T_CORR_TRANSAC_PAGO = ((dbCOL)((dbROW)dt.Datos.rows.get(1)).cols.get(0)).value_string;
        vQuery = "select get_parametro('BSCS_USER_PAYMENT_API') parametro from dual";
        dt = db.getQuery(vQuery, _ODA);
        if (dt.vData.booleanValue() && dt.vRows>1) T_BSCS_PAYMENT_API_ACCOUNT = ((dbCOL)((dbROW)dt.Datos.rows.get(1)).cols.get(0)).value_string;
        vQuery = "select get_parametro('BSCSFU_USER_PAYMENT_API') parametro from dual";
        dt = db.getQuery(vQuery, _ODA);
        if (dt.vData.booleanValue() && dt.vRows>1) T_BSCSFU_PAYMENT_API_ACCOUNT = ((dbCOL)((dbROW)dt.Datos.rows.get(1)).cols.get(0)).value_string;


        
        Double vMontoTotalPago = Double.valueOf(0.0D);
        
        for (int i = 0; i < payments_forms.length; i++) {
          vBaseQuery = "INSERT INTO CCAJA.TRANSACCIONES_REG_PAGO(CORR_TRANSAC_PAGO,NUM_CORR,TIP_PAG_TRAN,COD_BANCO,COD_BANCO_TIPO,CREDITO_DEBITO,NUM_CTA_BANCO,NUM_SER_CUENTA,NUMERO_TARJETA,COD_AUTORIZA,FECHA_VENCIMIENTO,PAGO_DOLARES, TOTAL_RECIBIDO, VUELTO)   VALUES(?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yy'),?, ?, ?) ";
          
          PreparedStatement _cmd_t_regpago = _ODA.prepareStatement(vBaseQuery);
          _cmd_t_regpago.setString(1, T_CORR_TRANSAC_PAGO);
          _cmd_t_regpago.setInt(2, i);
          _cmd_t_regpago.setString(3, (payments_forms[i]).tip_pag_tran);
          _cmd_t_regpago.setString(4, (payments_forms[i]).cod_banco);
          _cmd_t_regpago.setString(5, (payments_forms[i]).cod_banco_tipo);
          _cmd_t_regpago.setString(6, (payments_forms[i]).credito_debito);
          _cmd_t_regpago.setString(7, (payments_forms[i]).num_cta_banco);
          _cmd_t_regpago.setString(8, (payments_forms[i]).num_ser_cuenta);
          _cmd_t_regpago.setString(9, (payments_forms[i]).numero_tarjeta);
          _cmd_t_regpago.setString(10, (payments_forms[i]).cod_autoriza);
          if ((payments_forms[i]).fecha_vencimiento == null) {
            _cmd_t_regpago.setString(11, null);
          }
          else if ((payments_forms[i]).fecha_vencimiento.length() > 2) {
            if ((payments_forms[i]).fecha_vencimiento.length() <= 4) {
              _cmd_t_regpago.setString(11, "01/" + (payments_forms[i]).fecha_vencimiento.substring(0, 2) + "/" + (payments_forms[i]).fecha_vencimiento.substring(3));
            } else {
              _cmd_t_regpago.setString(11, (payments_forms[i]).fecha_vencimiento);
            } 
          } else {
            _cmd_t_regpago.setString(11, null);
          } 
          
          _cmd_t_regpago.setString(12, (payments_forms[i]).pago_dolares);
          _cmd_t_regpago.setString(13, (payments_forms[i]).total_recibido);
          _cmd_t_regpago.setString(14, (payments_forms[i]).vuelto);
          vMontoTotalPago = Double.valueOf(vMontoTotalPago.doubleValue() + Double.parseDouble((payments_forms[i]).pago_dolares));
          
          dt_ = db.setQuery(_cmd_t_regpago);
          if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>"); }
        
        } 


        
        for (int i = 0; i < payments.length; i++) {
          PreparedStatement _cmd_t_tran_u; CallableStatement _cmd_t_tran_open; PreparedStatement _cmd_t_tran_u_pb, _cmd_t_tran_u_p; CallableStatement _cmd_t_tran_bscs, _cmd_t_tran_bscsfu; vBaseQuery = "INSERT INTO CCAJA.TRANSACCIONES(CORR_TRANSAC_GROUP,CORR_TRANSAC,CORR_ENC_TRANSAC,CORR_TRANSAC_PAGO,COD_AGENCIA,COD_AGENCIA_PROC,COD_CAJA,COD_CAJERO,FEC_REAL_PAGO,    COD_OPERADOR,NUM_REFERENCIA,NUM_TELEFONO,NUM_CLIENTE,MONTO_TRANSAC,PAGO_DOLARES,FEC_VEN_FACTURA,ESTATUS_TRANSAC,PROCE_TRANSAC,NPE,TIPO_MONEDA,TOTAL_PAGAR,ESTADO_PAGO,VALOR_CHEQUE,CLIENTE_CICLO,SESION_CORRELATIVO,ID_ARCHIVOS_PAGO, ID_ARCHIVOS_PAGO_LINEA) values( ?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),   ?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?,?,?,?,?, ?, ?)";


          
          PreparedStatement _cmd_t_tran = _ODA.prepareStatement(vBaseQuery);
          vQuery = "select T_CORR_TRANSAC.nextval from dual";
          dt = db.getQuery(vQuery, _ODA);
          if (dt.vData.booleanValue() && dt.vRows>1) T_CORR_TRANSAC = ((dbCOL)((dbROW)dt.Datos.rows.get(1)).cols.get(0)).value_string;
          
          vQuery = "select T_CORR_ENC_TRANSAC.nextval from dual";
          dt = db.getQuery(vQuery, _ODA);
          if (dt.vData.booleanValue() && dt.vRows>1) T_CORR_ENC_TRANSAC = ((dbCOL)((dbROW)dt.Datos.rows.get(1)).cols.get(0)).value_string;
          
          _cmd_t_tran.setString(1, T_CORR_TRANSAC_GROUP);
          _cmd_t_tran.setString(2, T_CORR_TRANSAC);
          
          vBaseQuery = "INSERT INTO TRANSACCIONES_ENC(CORR_ENC_TRANSAC,NOMBRE_CLIENTE,DIRECCION_CLIENTE,NUM_REGISTRO,GIRO,NIT,EXENTO,TIPO,MUNICIPIO,DEPARTAMENTO,NUMERO_FCF,DUI,TELEFONO_ORIGEN,TELEFONO_REFERENCIA,CORREO)   VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
          
          PreparedStatement _cmd_t_enc = _ODA.prepareStatement(vBaseQuery);
          _cmd_t_enc.setString(1, T_CORR_ENC_TRANSAC);
          _cmd_t_enc.setString(2, (payments[i])._g_info_invoice_cliente.nombre);
          _cmd_t_enc.setString(3, (payments[i])._g_info_invoice_cliente.direccion);
          _cmd_t_enc.setString(4, (payments[i])._g_info_invoice_cliente.no_registro);
          _cmd_t_enc.setString(5, "");
          _cmd_t_enc.setString(6, (payments[i])._g_info_invoice_cliente.nit);
          _cmd_t_enc.setString(7, "");
          _cmd_t_enc.setString(8, (payments[i])._g_info_invoice_cliente.tipo_cliente);
          _cmd_t_enc.setString(9, "");
          _cmd_t_enc.setString(10, (payments[i])._g_info_invoice_cliente.departamento);
          _cmd_t_enc.setString(11, (payments[i])._g_info_invoice_cliente.grupo_cliente);
          _cmd_t_enc.setString(12, (payments[i])._g_info_invoice_cliente.dui);
          _cmd_t_enc.setString(13, (payments[i])._g_info_invoice_cliente.telefono);
          _cmd_t_enc.setString(14, (payments[i])._g_info_invoice_cliente.telefono_casa);
          _cmd_t_enc.setString(15, (payments[i])._g_info_invoice_cliente.correo);
          
          dt_ = db.setQuery(_cmd_t_enc);
          if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>"); }


          
          _cmd_t_tran.setString(3, T_CORR_ENC_TRANSAC);
          _cmd_t_tran.setString(4, T_CORR_TRANSAC_PAGO);
          _cmd_t_tran.setString(5, this.Ses.Cod_Agencia);
          vApplyCodAgenciaColector = Boolean.valueOf(false);
          if ((payments[i])._cod_agencia_colector != null && 
            (payments[i])._cod_agencia_colector.length() > 2) vApplyCodAgenciaColector = Boolean.valueOf(true);

          
          _cmd_t_tran.setString(6, vApplyCodAgenciaColector.booleanValue() ? (payments[i])._cod_agencia_colector : this.Ses.Cod_Agencia_Rec);
          _cmd_t_tran.setString(7, this.Ses.Cod_Caja);
          _cmd_t_tran.setString(8, this.Ses.Cod_Cajero);
          _cmd_t_tran.setString(9, this.Ses.Fec_Real_Pago);
          _cmd_t_tran.setString(10, (payments[i])._operacion);
          _cmd_t_tran.setString(11, (payments[i])._referencia);
          _cmd_t_tran.setString(12, (payments[i])._telefono);
          _cmd_t_tran.setString(13, (payments[i])._cliente);
          _cmd_t_tran.setString(14, (payments[i])._documento);
          _cmd_t_tran.setString(15, (payments[i])._pago);
          _cmd_t_tran.setString(16, ((payments[i])._vencimiento.length() <= 0) ? DateToString(getToday(), "dd/MM/yyyy") : (payments[i])._vencimiento);
          _cmd_t_tran.setString(17, "E");
          _cmd_t_tran.setString(18, "L");
          _cmd_t_tran.setString(19, (payments[i])._barra);
          _cmd_t_tran.setString(20, "1");
          _cmd_t_tran.setString(21, (payments[i])._pago);
          _cmd_t_tran.setString(22, ((payments[i])._documento == (payments[i])._pago) ? "T" : "P");
          _cmd_t_tran.setDouble(23, vMontoTotalPago.doubleValue());
          _cmd_t_tran.setString(24, (payments[i])._g_info_invoice_cliente.ciclo);
          _cmd_t_tran.setString(25, this.Ses.Sesion_Correlativo);
          _cmd_t_tran.setString(26, (payments[i])._id_archivos_pago);
          _cmd_t_tran.setString(27, (payments[i])._id_archivos_pago_linea);


          
          dt_ = db.setQuery(_cmd_t_tran);
          if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>"); }


          
          switch ((payments[i])._operacion) {
            case "218":
              vBaseQuery = "{CALL PAGOS_ODA.ppayment(?,?,?,?,?,?,?,?,?)}";
              vIsAdvance = Boolean.valueOf(((payments[i])._referencia.length() <= 0));

              
              _cmd_t_tran_bscsfu = _BSCSFU.prepareCall(vBaseQuery);
              _cmd_t_tran_bscsfu.setString(1, vIsAdvance.booleanValue() ? null : (payments[i])._cliente);
              _cmd_t_tran_bscsfu.setString(2, (payments[i])._pago);
              _cmd_t_tran_bscsfu.setString(3, T_BSCSFU_PAYMENT_API_ACCOUNT);
              _cmd_t_tran_bscsfu.setString(4, T_CORR_TRANSAC);
              _cmd_t_tran_bscsfu.setString(5, vIsAdvance.booleanValue() ? "CE2CO" : "CE2IN-X3");
              _cmd_t_tran_bscsfu.setString(6, (payments[i])._cliente);
              _cmd_t_tran_bscsfu.setString(7, this.Ses.Cod_Agencia);
              _cmd_t_tran_bscsfu.setString(8, DateToString(getToday(), "ddMMyyyyHHmmss"));
              _cmd_t_tran_bscsfu.registerOutParameter(9, 12, 1000);
              
              dt_ = db.setQuery(_cmd_t_tran_bscsfu);
              if (_cmd_t_tran_bscsfu.getString(9) == null) _cmd_t_tran_bscsfu.setString(9, ""); 
              if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>"); }
               if (!_cmd_t_tran_bscsfu.getString(9).equals("Succesfull")) {
                _BSCSFU.rollback();
                vError_BSCSFU = Boolean.valueOf(true);
                dt.vDBMessage = dt_.vMessage + dt_.vDBMessage + _cmd_t_tran_bscsfu.getString(9);
                throw new Exception("Ocurrio Error aplicando Transaccion en BSCS/FU. ");
              } 
              vBSCSFU_Applied = Boolean.valueOf(true);
              
              vOpenCuponValue = _cmd_t_tran_bscsfu.getString(9);
              _cmd_t_tran_u_p = _ODA.prepareCall("UPDATE CCAJA.TRANSACCIONES SET ID_OPERACION_PAGO=? WHERE CORR_TRANSAC_GROUP=? AND CORR_TRANSAC=?");
              _cmd_t_tran_u_p.setString(1, vOpenCuponValue);
              _cmd_t_tran_u_p.setString(2, T_CORR_TRANSAC_GROUP);
              _cmd_t_tran_u_p.setString(3, T_CORR_TRANSAC);
              dt_ = db.setQuery(_cmd_t_tran_u_p);
              if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>"); }
              
              break;


            
            case "101":
              try {
                vBaseQuery = "{call SYSADM.pcv_referencia_pago(?,?)  }";
                CallableStatement _cmd_t_tran_bscs_reference = _BSCS.prepareCall(vBaseQuery);
                _cmd_t_tran_bscs_reference.setString(1, (payments[i])._cliente);
                _cmd_t_tran_bscs_reference.registerOutParameter(2, 12, 1000);
                dt_ = db.getQuery(_cmd_t_tran_bscs_reference);
                
                PreparedStatement _cmd_t_tran_u_ref = _ODA.prepareCall("UPDATE CCAJA.TRANSACCIONES SET SUB_REFERENCIA=? WHERE CORR_TRANSAC_GROUP=? AND CORR_TRANSAC=?");
                _cmd_t_tran_u_ref.setString(1, _cmd_t_tran_bscs_reference.getString(2));
                _cmd_t_tran_u_ref.setString(2, T_CORR_TRANSAC_GROUP);
                _cmd_t_tran_u_ref.setString(3, T_CORR_TRANSAC);
                dt_ = db.setQuery(_cmd_t_tran_u_ref);
              } catch (Exception exception) {}

              
              vBaseQuery = "{CALL PAGOS_ODA.ppayment(?,?,?,?,?,?,?,?,?)  }";
              vIsAdvance = Boolean.valueOf(((payments[i])._referencia.length() <= 0));

              
              _cmd_t_tran_bscs = _BSCS.prepareCall(vBaseQuery);
              _cmd_t_tran_bscs.setString(1, vIsAdvance.booleanValue() ? null : (payments[i])._referencia);
              _cmd_t_tran_bscs.setString(2, (payments[i])._pago);
              _cmd_t_tran_bscs.setString(3, T_BSCS_PAYMENT_API_ACCOUNT);
              _cmd_t_tran_bscs.setString(4, T_CORR_TRANSAC);
              _cmd_t_tran_bscs.setString(5, vIsAdvance.booleanValue() ? "CE2CO" : "CE2IN-X1");
              _cmd_t_tran_bscs.setString(6, (payments[i])._cliente);
              _cmd_t_tran_bscs.setString(7, this.Ses.Cod_Agencia);
              _cmd_t_tran_bscs.setString(8, DateToString(getToday(), "ddMMyyyyHHmmss"));
              _cmd_t_tran_bscs.registerOutParameter(9, 12, 1000);
              
              dt_ = db.setQuery(_cmd_t_tran_bscs);
              if (_cmd_t_tran_bscs.getString(9) == null) _cmd_t_tran_bscs.setString(9, ""); 
              if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>"); }
               if (!_cmd_t_tran_bscs.getString(9).equals("Succesfull")) {
                _BSCS.rollback();
                vError_BSCS = Boolean.valueOf(true);
                dt.vDBMessage = dt_.vMessage + dt_.vDBMessage + _cmd_t_tran_bscs.getString(9);
                throw new Exception("Ocurrio Error aplicando Transaccion en BSCS. ");
              } 
              vBSCS_Applied = Boolean.valueOf(true);
              
              vOpenCuponValue = _cmd_t_tran_bscs.getString(9);
              _cmd_t_tran_u_pb = _ODA.prepareCall("UPDATE CCAJA.TRANSACCIONES SET ID_OPERACION_PAGO=? WHERE CORR_TRANSAC_GROUP=? AND CORR_TRANSAC=?");
              _cmd_t_tran_u_pb.setString(1, vOpenCuponValue);
              _cmd_t_tran_u_pb.setString(2, T_CORR_TRANSAC_GROUP);
              _cmd_t_tran_u_pb.setString(3, T_CORR_TRANSAC);
              dt_ = db.setQuery(_cmd_t_tran_u_pb);
              if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>"); }
              
              break;
            
            case "208":
              vOpenCuponValue = "";
              if (vApplyCodAgenciaColector.booleanValue()) {
                vQuery = "select * from agencias where cod_agencia = '" + (payments[i])._cod_agencia_colector + "' and open_cod_agencia is not null";
              } else {
                vQuery = "select * from agencias where cod_agencia = '" + this.Ses.Cod_Agencia + "' and open_cod_agencia is not null";
              } 
              dtCagencia = db.getQuery(vQuery, _ODA);
              if (!dtCagencia.vData.booleanValue() || dtCagencia.vRows <= 0) throw new Exception("La agencia no puede realizar anulaciones de OPEN. </br>Confirme con su Administrador para configurar la agencia."); 
              vCodAgenciaOpen = ((((dbROW)dtCagencia.Datos.rows.get(1)).getColByName("OPEN_COD_AGENCIA")).value_string == null) ? "" : (((dbROW)dtCagencia.Datos.rows.get(1)).getColByName("OPEN_COD_AGENCIA")).value_string;
              vCodSucursalOpen = (((dbROW)dtCagencia.Datos.rows.get(1)).getColByName("OPEN_COD_SUCURSAL") == null) ? "" : (((dbROW)dtCagencia.Datos.rows.get(1)).getColByName("OPEN_COD_SUCURSAL")).value_string;
              if (vCodSucursalOpen.length() <= 0) vCodSucursalOpen = vCodAgenciaOpen;
              
              vBaseQuery = "{ CALL smart.OS_REGPAGOSCONTRATO(?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?) }";
              
              _cmd_t_tran_open = _OPEN.prepareCall(vBaseQuery);
              
              _cmd_t_tran_open.setString(1, vCodAgenciaOpen);
              _cmd_t_tran_open.setString(2, vCodSucursalOpen);
              _cmd_t_tran_open.setString(3, DateToString(getToday(), "dd/MM/yyyy"));
              _cmd_t_tran_open.setString(4, (payments[i])._pago);
              _cmd_t_tran_open.setString(5, (payments[i])._cliente);
              _cmd_t_tran_open.registerOutParameter(6, 12, 1000);
              _cmd_t_tran_open.registerOutParameter(7, 12, 1000);
              _cmd_t_tran_open.registerOutParameter(8, 12, 1000);
              
              dt_ = db.setQuery(_cmd_t_tran_open);
              if (_cmd_t_tran_open.getString(6) == null) _cmd_t_tran_open.setString(6, ""); 
              if (_cmd_t_tran_open.getString(7) == null) _cmd_t_tran_open.setString(7, ""); 
              if (_cmd_t_tran_open.getString(8) == null) _cmd_t_tran_open.setString(8, ""); 
              if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>"); }
               if (!_cmd_t_tran_open.getString(7).equals("0")) {
                _OPEN.rollback();
                vError_OPEN = Boolean.valueOf(true);
                dt.vDBMessage = dt_.vMessage + dt_.vDBMessage + _cmd_t_tran_open.getString(7) + _cmd_t_tran_open.getString(8);
                throw new Exception("Ocurrio Error aplicando Transaccion en OPEN. ");
              } 
              
              vOPEN_Applied = Boolean.valueOf(true);
              vOpenCuponValue = _cmd_t_tran_open.getString(6);
              _cmd_t_tran_u = _ODA.prepareCall("UPDATE CCAJA.TRANSACCIONES SET ID_OPERACION_PAGO=? WHERE CORR_TRANSAC_GROUP=? AND CORR_TRANSAC=?");
              _cmd_t_tran_u.setString(1, vOpenCuponValue);
              _cmd_t_tran_u.setString(2, T_CORR_TRANSAC_GROUP);
              _cmd_t_tran_u.setString(3, T_CORR_TRANSAC);
              dt_ = db.setQuery(_cmd_t_tran_u);
              if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>"); }
              
              break;
          } 




        
        } 
        _ODA.commit();
        vAppliedFull = Boolean.valueOf(true);
        if (!vError_BSCSFU.booleanValue() && vBSCSFU_Applied.booleanValue()) _BSCSFU.commit(); 
        if (!vError_BSCS.booleanValue() && vBSCS_Applied.booleanValue()) _BSCS.commit(); 
        if (!vError_OPEN.booleanValue() && vOPEN_Applied.booleanValue()) _OPEN.commit();


        
        try { vBaseQuery = "{CALL PKG_TRANSACTION_PROCESS.process_transaction_end(?) }";
          PreparedStatement _cmd_t_tran_oda_final = _ODA.prepareCall(vBaseQuery);
          _cmd_t_tran_oda_final.setString(1, T_CORR_TRANSAC_GROUP);
          dt_ = db.setQuery(_cmd_t_tran_oda_final); }
        catch (Exception e) { setErrMsg(e);
           }

        
         }
      
      catch (Exception eCC)
      
      { _ODA.rollback(); dt = HandleExceptionDB(eCC, dt); } 
    } catch (Exception e) {
      dt = HandleExceptionDB(e, dt);
    } 

    
    dt.vData = vAppliedFull;
    dt.vCorrelativoGroup = T_CORR_TRANSAC_GROUP;
    return dt;
  }





  
  public SubDataTable SetCancelPayments(dbAUTH Info, String f_corr_transac_group, String txt_sup_user, String txt_sup_password, String txt_comentario) {
    SubDataTable dt = new SubDataTable();
    SubDataTable dt_ = new SubDataTable();
    SubDataTable dtID = new SubDataTable();
    String vQuery = "", vBaseQuery = "";
    OracleConnection _ODA = null;
    OracleConnection _ODATran = null;
    OracleConnection _BSCSFUTran = null;
    OracleConnection _BSCSTran = null;
    OracleConnection _OPENTran = null;
    Boolean vAppliedFull = Boolean.valueOf(false);
    
    Boolean vError_BSCSFU = Boolean.valueOf(false), vBSCSFU_Applied = Boolean.valueOf(false);
    String vMessage_BSCSFU = "";
    Boolean vError_BSCS = Boolean.valueOf(false), vBSCS_Applied = Boolean.valueOf(false);
    Boolean vError_OPEN = Boolean.valueOf(false), vOPEN_Applied = Boolean.valueOf(false);
    Boolean _connTo_bscsfu = Boolean.valueOf(false), _connTo_bscs = Boolean.valueOf(false), _connTo_open = Boolean.valueOf(false);
    String vCodResultAnula = "";
    String vCorrTransacGroup = "";
    String T_BSCS_PAYMENT_API_ACCOUNT = "", T_BSCSFU_PAYMENT_API_ACCOUNT = "";
    
    try {
      Boolean Auth = (Authenticate(Info)).vResult;
      if (!Auth.booleanValue()) throw new Exception("Ocurrio un error en la autenticacion del usuario.");


      
      GlobalDB db = new GlobalDB();
      _ODA = db.getODADBConnection(ConnectTo.ODA_db);
      
      OracleConnection _BSCSFU = null;
      OracleConnection _BSCS = null;
      OracleConnection _OPEN = null;

      
      vQuery = "select * from cajeros WHERE cod_agencia = '" + this.Ses.Cod_Agencia + "' AND cod_cajero = '" + txt_sup_user + "' AND password = securityutils.encrypt('" + txt_sup_password + "') and fecha_expira is null and trunc(password_expira) >= trunc(sysdate)";
      dt = db.getQuery(vQuery, _ODA);
      if (!dt.vData.booleanValue() || dt.vRows <= 1) throw new Exception("Las credenciales proporcionadas son incorrectas.");

      
      try {
        vQuery = "select * from transacciones where corr_transac_group = '" + f_corr_transac_group + "'";
        dt = db.getQuery(vQuery, _ODA);
        
        vQuery = "select get_parametro('BSCS_USER_PAYMENT_API') parametro from dual";
        dtID = db.getQuery(vQuery, _ODA);
        if (dtID.vData.booleanValue() && dtID.vRows>1) T_BSCS_PAYMENT_API_ACCOUNT = ((dbCOL)((dbROW)dtID.Datos.rows.get(1)).cols.get(0)).value_string;
        vQuery = "select get_parametro('BSCSFU_USER_PAYMENT_API') parametro from dual";
        dtID = db.getQuery(vQuery, _ODA);
        if (dtID.vData.booleanValue() && dtID.vRows>1) T_BSCSFU_PAYMENT_API_ACCOUNT = ((dbCOL)((dbROW)dtID.Datos.rows.get(1)).cols.get(0)).value_string;


        
        if (dt.vData.booleanValue() && dt.vRows>1) {
          for (int i = 1; i < dt.vRows; i++) {
            CallableStatement _cmd_t_tran_open, _cmd_t_tran_bscs, _cmd_t_tran_bscsfu; vCodResultAnula = "";

            
            vMessage_BSCSFU = "";
            switch ((((dbROW)dt.Datos.rows.get(i)).getColByName("COD_OPERADOR")).value_string) {
              case "218":
                if (_connTo_bscsfu.booleanValue() != true) {
                  _BSCSFU = db.getODADBConnection(ConnectTo.BSCSFU_db);
                  _connTo_bscsfu = Boolean.valueOf(true);
                } 
                
                vBaseQuery = "{CALL PAGOS_ODA.ppayment(?,?,?,?,?,?,?,?,?)}";
                
                _cmd_t_tran_bscsfu = _BSCSFU.prepareCall(vBaseQuery);
                _cmd_t_tran_bscsfu.setString(1, (((dbROW)dt.Datos.rows.get(i)).getColByName("NUM_CLIENTE")).value_string);
                _cmd_t_tran_bscsfu.setString(2, (((dbROW)dt.Datos.rows.get(i)).getColByName("TOTAL_PAGAR")).value_string);
                _cmd_t_tran_bscsfu.setString(3, T_BSCSFU_PAYMENT_API_ACCOUNT);
                _cmd_t_tran_bscsfu.setString(4, (((dbROW)dt.Datos.rows.get(i)).getColByName("CORR_TRANSAC")).value_string);
                _cmd_t_tran_bscsfu.setString(5, "RV-CE2IN");
                _cmd_t_tran_bscsfu.setString(6, (((dbROW)dt.Datos.rows.get(i)).getColByName("NUM_CLIENTE")).value_string);
                _cmd_t_tran_bscsfu.setString(7, this.Ses.Cod_Agencia);
                _cmd_t_tran_bscsfu.setString(8, DateToString(getToday(), "ddMMyyyyHHmmss"));
                _cmd_t_tran_bscsfu.registerOutParameter(9, 12, 1000);


                
                dt_ = db.setQuery(_cmd_t_tran_bscsfu);
                if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Anulacion>" + dt_.vMessage + dt_.vDBMessage); }
                 vMessage_BSCSFU = vMessage_BSCSFU + "[BSCSFU:" + _cmd_t_tran_bscsfu.getString(9) + "]";
                if (!_cmd_t_tran_bscsfu.getString(9).equals("Succesfull")) {
                  _BSCSFU.rollback();
                  vError_BSCSFU = Boolean.valueOf(true);
                  dt.vDBMessage = vMessage_BSCSFU;
                  throw new Exception("Ocurrio un error anulando en BSCS FU:");
                }  vBSCSFU_Applied = Boolean.valueOf(true); vCodResultAnula = vMessage_BSCSFU;
                break;
              
              case "101":
                if (_connTo_bscs.booleanValue() != true) {
                  _BSCS = db.getODADBConnection(ConnectTo.BSCS_db);
                  _connTo_bscs = Boolean.valueOf(true);
                } 

                
                vBaseQuery = "{CALL PAGOS_ODA.ppayment(?,?,?,?,?,?,?,?,?)  }";
                
                _cmd_t_tran_bscs = _BSCS.prepareCall(vBaseQuery);
                _cmd_t_tran_bscs.setString(1, "");
                _cmd_t_tran_bscs.setString(2, (((dbROW)dt.Datos.rows.get(i)).getColByName("TOTAL_PAGAR")).value_string);
                _cmd_t_tran_bscs.setString(3, T_BSCS_PAYMENT_API_ACCOUNT);
                _cmd_t_tran_bscs.setString(4, (((dbROW)dt.Datos.rows.get(i)).getColByName("CORR_TRANSAC")).value_string);
                _cmd_t_tran_bscs.setString(5, "RV-CE2IN");
                _cmd_t_tran_bscs.setString(6, (((dbROW)dt.Datos.rows.get(i)).getColByName("NUM_CLIENTE")).value_string);
                _cmd_t_tran_bscs.setString(7, this.Ses.Cod_Agencia);
                _cmd_t_tran_bscs.setString(8, DateToString(getToday(), "ddMMyyyyHHmmss"));
                _cmd_t_tran_bscs.registerOutParameter(9, 12, 1000);
                
                dt_ = db.setQuery(_cmd_t_tran_bscs);
                if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Anulacion>" + dt_.vMessage + dt_.vDBMessage); }
                 vMessage_BSCSFU = vMessage_BSCSFU + "[BSCS:" + _cmd_t_tran_bscs.getString(9) + "]";
                if (!_cmd_t_tran_bscs.getString(9).equals("Succesfull")) {
                  _BSCS.rollback();
                  vError_BSCS = Boolean.valueOf(true);
                  dt.vDBMessage = ((dt_.vMessage == null) ? "" : dt_.vMessage) + ((dt_.vDBMessage == null) ? "" : dt_.vDBMessage) + ((_cmd_t_tran_bscs.getString(9) == null) ? "" : _cmd_t_tran_bscs.getString(9));
                  throw new Exception("Ocurrio un error anulando en BSCS:");
                }  vBSCS_Applied = Boolean.valueOf(true); vCodResultAnula = vMessage_BSCSFU;
                break;
              case "208":
                if (_connTo_open.booleanValue() != true) {
                  _OPEN = db.getODADBConnection(ConnectTo.OPEN_db);
                  _connTo_open = Boolean.valueOf(true);
                } 
                
                vBaseQuery = "{ CALL smart.PAYMENTDELETE(?,?,?) }";
                _cmd_t_tran_open = _OPEN.prepareCall(vBaseQuery);
                
                _cmd_t_tran_open.setString(1, (((dbROW)dt.Datos.rows.get(i)).getColByName("ID_OPERACION_PAGO")).value_string);
                _cmd_t_tran_open.registerOutParameter(2, 12, 1000);
                _cmd_t_tran_open.registerOutParameter(3, 12, 1000);
                
                dt_ = db.setQuery(_cmd_t_tran_open);
                if (!dt_.vData.booleanValue()) { dt.vDBMessage = ((dt_.vMessage == null) ? "" : dt_.vMessage) + ((dt_.vDBMessage == null) ? "" : dt_.vDBMessage); throw new Exception("Error en Anulacion>" + ((dt_.vMessage == null) ? "" : dt_.vMessage) + ((dt_.vDBMessage == null) ? "" : dt_.vDBMessage)); }
                 vMessage_BSCSFU = "[OPEN:" + _cmd_t_tran_open.getString(2) + "|" + _cmd_t_tran_open.getString(3) + "]";
                if (!_cmd_t_tran_open.getString(2).equals("0")) {
                  _OPEN.rollback();
                  vError_OPEN = Boolean.valueOf(true);
                  dt.vDBMessage = vMessage_BSCSFU;
                  throw new Exception("Ocurrio un error anulando en OPEN:");
                } 
                vOPEN_Applied = Boolean.valueOf(true);
                vCodResultAnula = _cmd_t_tran_open.getString(2);
                break;
            } 


            
            vBaseQuery = "UPDATE TRANSACCIONES SET ESTATUS_TRANSAC='A', COD_CAJERO_ANULA=?, FEC_TRAN_ANULA=sysdate, COMENTARIO=?, ID_OPERACION_ANULA=? where CORR_TRANSAC_GROUP =? and CORR_TRANSAC=?";
            PreparedStatement _cmd_t_tran = _ODA.prepareCall(vBaseQuery);
            _cmd_t_tran.setString(1, this.Ses.Cod_Cajero);
            _cmd_t_tran.setString(2, txt_comentario);
            _cmd_t_tran.setString(3, vMessage_BSCSFU + vCodResultAnula);
            _cmd_t_tran.setString(4, (((dbROW)dt.Datos.rows.get(i)).getColByName("CORR_TRANSAC_GROUP")).value_string);
            _cmd_t_tran.setString(5, (((dbROW)dt.Datos.rows.get(i)).getColByName("CORR_TRANSAC")).value_string);
            
            dt_ = db.setQuery(_cmd_t_tran);
            if (!dt_.vData.booleanValue()) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage;
              throw new Exception("Error en Anulacion>"); }
            
            vCorrTransacGroup = (((dbROW)dt.Datos.rows.get(i)).getColByName("CORR_TRANSAC_GROUP")).value_string;
          } 
        }
        
        _ODA.commit();



        
        try { vBaseQuery = "PKG_TRANSACTION_PROCESS.process_transaction_end";
          PreparedStatement _cmd_t_tran_oda_final = _ODA.prepareCall(vBaseQuery);
          _cmd_t_tran_oda_final.setString(1, vCorrTransacGroup);
          dt_ = db.setQuery(_cmd_t_tran_oda_final); }
        catch (Exception e) { setErrMsg(e); }

        
        vAppliedFull = Boolean.valueOf(true);
        if (!vError_BSCSFU.booleanValue() && vBSCSFU_Applied.booleanValue()) _BSCSFUTran.commit(); 
        if (!vError_BSCS.booleanValue() && vBSCS_Applied.booleanValue()) _BSCSTran.commit(); 
        if (!vError_OPEN.booleanValue() && vOPEN_Applied.booleanValue()) _OPENTran.commit();
      
      } catch (Exception eCC) {
        _ODA.rollback();
        HandleExceptionDB(eCC, dt);
        if (_connTo_bscsfu.booleanValue()) {
          _BSCSFU.rollback();
        }
        if (_connTo_bscs.booleanValue()) {
          _BSCS.rollback();
        }
        if (_connTo_open.booleanValue()) {
          _OPEN.rollback();
        }
      } 
    } catch (Exception eODA) {
      HandleExceptionDB(eODA, dt); dt.vData = Boolean.valueOf(false);
    } 

    
    dt.vData = vAppliedFull;
    return dt;
  }






  
  SubDataTable HandleExceptionDB(Exception e, SubDataTable dt) {
    dt.Assign(Boolean.valueOf(false), new dbDATA());
    dt.vDBMessage += e.getMessage();
    dt.vMessage = "Ocurrio un error >" + dt.vMessage;
    return dt;
  }
}
