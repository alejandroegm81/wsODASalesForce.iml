package WsServicios.Operaciones.Oper;

import FromNET.*;
import ServiceActionsDB.dbDATA;
import WsServicios.Bases.wGenInstanceFuntions;
import WsServicios.Bases.wsInstancias;
import WsServicios.Operaciones.Base.*;
import WsServicios.Operaciones.Lista.wsS_RegistroOperaciones;
import oracle.jdbc.OracleConnection;

//import javax.jws.WebParam;
//import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static db.BaseClass.setInfo;

public class wsOperaciones_Apply {
    private String vLastErrMessage="";
    private String gCodCaja="",gCodCajero="",gCodAgencia="",gSessionCorrelativo="";


    //v0 = dt.Datos.rows.get(i).getColByName("ID").value_string;

    public Boolean setPaymentSession(wsInstancias.wsInstancia Instancia,wAutenticacion Autenticacion ){
        Boolean vResult=false;
        try{
            GlobalDB db = new GlobalDB();
            SubDataTable dtBanco = new SubDataTable();
            wGenInstanceFuntions wG = new wGenInstanceFuntions();
            ConnectTo vConn = wG.getConnectTo(Instancia);
            String vCountryCode= wG.getCountryCode (Instancia);
            Boolean vAbrirCaja=false;
            if(vCountryCode.equals("507")){
                dtBanco = db.getQuery( "select b.cod_agencia, b.cod_caja, conf_cod_cajero cod_Cajero   from agencias_bancos a inner join cajas b on a.conf_direc_ip=b.direc_ip and a.conf_cod_agencia=b.cod_agencia where cod_agencia_banco='" + Autenticacion.vIDBanco + "'"   , vConn   );
                if(dtBanco.vData && dtBanco.vRows>1){
                    gCodCaja = dtBanco.Datos.rows.get(1).getColByName("COD_CAJA").value_string;
                    gCodAgencia = dtBanco.Datos.rows.get(1).getColByName("COD_AGENCIA").value_string;
                    gCodCajero = dtBanco.Datos.rows.get(1).getColByName("COD_CAJERO").value_string;
                }
            }else{
                dtBanco = db.getQuery( "select * from list_banks where code_bank='" + Autenticacion.vIDBanco + "'"   , vConn   );
                if(dtBanco.vData && dtBanco.vRows>1){
                    gCodCaja = dtBanco.Datos.rows.get(1).getColByName("COD_CAJA").value_string;
                    gCodAgencia = dtBanco.Datos.rows.get(1).getColByName("COD_AGENCIA").value_string.trim().replace(".","");
                    gCodCajero = dtBanco.Datos.rows.get(1).getColByName("COD_CAJERO").value_string;
                }
            }
            if( gCodCaja.length()<=0 || gCodCajero.length()<=0 || gCodAgencia.length()<=0 ){throw new Exception("Datos para Sesion de Pago incompletos.");}
            dtBanco = db.getQuery("select count(*) CC , max(corre) corre from movim_diarios where fec_real_pago=trunc(sysdate) and cod_caja='" + gCodCaja + "' and cod_agencia='"+gCodAgencia+"' and cod_cajero='" +gCodCajero + "' and estatus_mov='A'"  , vConn );
            if(dtBanco.vData && dtBanco.vRows>1){
                if( dtBanco.Datos.rows.get(1).getColByName("CC").value_int>0 ){
                    vAbrirCaja=false; gSessionCorrelativo =    dtBanco.Datos.rows.get(1).getColByName("CORRE").value_string;
                }else{vAbrirCaja=true;}
            }
            if(vAbrirCaja){
                dtBanco = db.getQuery("SELECT seq_movim_diarios_log.NEXTVAL ID from dual",vConn);
                if(dtBanco.vData && dtBanco.vRows>1){
                    gSessionCorrelativo = dtBanco.Datos.rows.get(1).getColByName("ID").value_string;
                }
                GlobalDBParamObjectList ls = new GlobalDBParamObjectList();
                ls.Add( new GlobalDBParamObject("agencia",gCodAgencia));
                ls.Add( new GlobalDBParamObject("caja",gCodCaja));
                ls.Add( new GlobalDBParamObject("cajero",gCodCajero));
                ls.Add( new GlobalDBParamObject("corre",gSessionCorrelativo));
                ls.Add( new GlobalDBParamObject("agencia2",gCodAgencia));
                dtBanco = db.setQuery("INSERT INTO movim_diarios(cod_agencia, cod_caja, cod_cajero, fec_ini_mov,corr_movdia, hora_ini_mov, estatus_mov, corre,fec_real_pago, cod_agencia_proc, cierre_validador) VALUES (?, ?, ?, TRUNC (SYSDATE),1, SYSDATE, 'A', ?,TRUNC (SYSDATE), ?, 0)" , ls, vConn );
            }
            vResult=true;
        }catch(Exception e){vLastErrMessage=e.getMessage();}
        return vResult;
    }


    private ws_list_operations getToApply(wsInstancias.wsInstancia Instancia,wAutenticacion Autenticacion,
                                          wsOperacionServicios.wsOperacionServicio OperacionServicio, wsS_RegistroOperaciones Operaciones , wsS_RegistroFormaPago FormaPago) throws Exception{
        ws_list_operations OperApply=new ws_list_operations();
        if(Operaciones.Datos.size()<=0){throw new Exception("La lista no posee items para aplicacion de pago.");}
        //Primera Validacion de que tenga Cliente|Telefono y Valor a Pagar.
        {
            Double vValItemDetMontoPago=0.00; Boolean vValItemDetCheck=false;
            for(int i=0;i<Operaciones.Datos.size();i++){
                if(Operaciones.Datos.get(i).Cliente_Contrato==null){Operaciones.Datos.get(i).Cliente_Contrato="";}
                if(Operaciones.Datos.get(i).Telefono==null){Operaciones.Datos.get(i).Telefono="";}
                if(Operaciones.Datos.get(i).Referencia==null){Operaciones.Datos.get(i).Referencia="";}
                if(Operaciones.Datos.get(i).NoDocumento==null){Operaciones.Datos.get(i).NoDocumento="";}
                if(Operaciones.Datos.get(i).NPE==null){Operaciones.Datos.get(i).NPE="";}
                if(Operaciones.Datos.get(i).MontoPago <=0  ){ throw new Exception("Para el item de Pago (" + i + "), no se puede proceder con valor a pagar <=0 (" + Operaciones.Datos.get(i).MontoPago  + ")"); }
                vValItemDetMontoPago+=Operaciones.Datos.get(i).MontoPago;
                if(  (Operaciones.Datos.get(i).Cliente_Contrato.length()>2 || Operaciones.Datos.get(i).Telefono.length()>=8 ) && Operaciones.Datos.get(i).MontoPago>0 ){
                    vValItemDetCheck=true;
                }else{
                    throw new Exception("El Item (" + i + "), no posee Telefono o Cliente|Contrato o Valor a Pagar >0");
                }
            }
            if(vValItemDetMontoPago!=FormaPago.MontoPago){ throw new Exception("La suma de los detalles de pagos, son diferentes al total con la forma de pago. (" + vValItemDetMontoPago + "<>" + FormaPago.MontoPago + ")"); }
            if(FormaPago.MontoPago<=0){ throw new Exception("El monto a pagar, en forma de pago no puede ser <=0  (" + FormaPago.MontoPago + ")"); }
        }

        //Busqueda y asignacion de items para Pago
        wsR_gBusqueda vBusquedaItem=new wsR_gBusqueda();
        Double vL_MontoPago=0.0, vL_MontoPagoBase=0.0;
        String vL_Cliente="", vL_Telefono="", vL_Referencia="", vL_Factura="", vL_NPE="";
        Boolean vL_DocFound=false, vL_DocSaldoOk=false;
        for(int j=0;j<Operaciones.Datos.size();j++){
            vL_Cliente      = Operaciones.Datos.get(j).Cliente_Contrato;
            vL_Telefono     = Operaciones.Datos.get(j).Telefono;
            vL_Referencia   = Operaciones.Datos.get(j).Referencia ;
            vL_Factura      = Operaciones.Datos.get(j).NoDocumento ;
            vL_NPE          = Operaciones.Datos.get(j).NPE ;
            vL_MontoPago    = Operaciones.Datos.get(j).MontoPago;
            vL_MontoPagoBase    = Operaciones.Datos.get(j).MontoPago;
            vBusquedaItem = getToApplyBusquedas( Instancia, Autenticacion, OperacionServicio, vL_Cliente,vL_Telefono );
                if( vBusquedaItem.vEstado!=1 || vBusquedaItem.Clientes.size()<=0 ){ throw new Exception("No se encontro informacion para Cliente|Telefono: ( Estado: " + vBusquedaItem.vEstado + "; Cliente " + vL_Cliente + "; Telefono " + vL_Telefono + " )"  ); }
                if( vBusquedaItem.Clientes.size()>1 ){ throw new Exception("Se encontro mas de un Cliente asociado. Favor especificar el IDCliente. ( Estado: " + vBusquedaItem.vEstado + "; Cliente " + vL_Cliente + "; Telefono " + vL_Telefono + "; Clientes Encontrados: " + vBusquedaItem.Clientes.size() + " )"  ); }
            //Si el item, trae informacion de factura, entonces hay que validar que exista, si no existe da error.
            if(vL_Referencia.length()>1 || vL_Factura.length()>1 || vL_NPE.length()>1 ){
                for(int k=0; k<vBusquedaItem.Clientes.size();k++){
                    for(int kk=0 ; kk< vBusquedaItem.Clientes.get(k).Documentos.size();kk++){
                        if(  (vL_Factura.length()>1 &&  vBusquedaItem.Clientes.get(k).Documentos.get(kk).NoDocumento.equals(vL_Factura))    ||
                             (vL_Referencia.length()>1 && vBusquedaItem.Clientes.get(k).Documentos.get(kk).Referencia.equals(vL_Referencia))   ||
                             (vL_NPE.length()>1 && vBusquedaItem.Clientes.get(k).Documentos.get(kk).NPE.equals(vL_NPE) )     ){
                            vL_DocFound=true;
                            if( vL_MontoPago> vBusquedaItem.Clientes.get(k).Documentos.get(kk).Saldo )
                            { throw new Exception("Para el Documento : " + vL_Factura + ";" +  vL_Referencia  + ";" +  vL_NPE + ",  el valor a pagar es mayor al saldo pendiente. (Pago:" + vL_MontoPago + "; Saldo:" +  vBusquedaItem.Clientes.get(k).Documentos.get(kk).Saldo + ")" );
                            }else{
                                if( vL_MontoPago > 0 && vBusquedaItem.Clientes.get(k).Documentos.get(kk).Saldo >0 && vL_MontoPago<=vBusquedaItem.Clientes.get(k).Documentos.get(kk).Saldo )
                                vL_DocSaldoOk=true;
                                ws_det_operations vItem = new ws_det_operations();
                                vItem.c_cliente_nombre = vBusquedaItem.Clientes.get(k).Nombre;
                                vItem.c_cliente_cedula = vBusquedaItem.Clientes.get(k).Identificacion;
                                vItem.c_cliente_correo = vBusquedaItem.Clientes.get(k).CorreoElectronico;
                                vItem.CicloCliente = vBusquedaItem.Clientes.get(k).CicloCliente;
                                vItem.Cliente_Contrato = vBusquedaItem.Clientes.get(k).IDCliente;
                                vItem.Telefono = vL_Telefono;
                                vItem.MontoPago = vL_MontoPago;
                                vItem.FechaEmision = vBusquedaItem.Clientes.get(k).Documentos.get(kk).FechaEmision;
                                vItem.FechaVencimiento = vBusquedaItem.Clientes.get(k).Documentos.get(kk).FechaVencimiento;
                                vItem.IDDocumento = vBusquedaItem.Clientes.get(k).Documentos.get(kk).IDDocumento;
                                vItem.NPE = vBusquedaItem.Clientes.get(k).Documentos.get(kk).NPE;
                                vItem.Referencia = vBusquedaItem.Clientes.get(k).Documentos.get(kk).Referencia;
                                vItem.SaldoReferencia = vBusquedaItem.Clientes.get(k).Documentos.get(kk).Saldo;
                                vItem.NoDocumento = vBusquedaItem.Clientes.get(k).Documentos.get(kk).NoDocumento;
                                vItem.TipoDocumento = vBusquedaItem.Clientes.get(k).Documentos.get(kk).TipoDocumento;
                                OperApply.MontoPago += vL_MontoPago;
                                OperApply.Datos.add(vItem);
                                break;
                            }
                        }
                    }
                }
                if(vL_DocFound ==false && vL_DocSaldoOk ==false){throw new Exception("La informacion para el Pago con Documento: " +  vL_Factura + ";" +  vL_Referencia  + ";" +  vL_NPE + ", por valor de " + vL_MontoPago + ", no se puede ejecutar porque no se encontro referencia y saldo/" );}
            }else{
                //Significa que no colocaron documento, y por lo tanto se aplicara a Saldo.
                for(int k=0; k<vBusquedaItem.Clientes.size();k++){
                    for(int kk=0 ; kk< vBusquedaItem.Clientes.get(k).Documentos.size();kk++){
                        if( vL_MontoPagoBase > 0 && vBusquedaItem.Clientes.get(k).Documentos.get(kk).Saldo >0  ){
                            if(vL_MontoPagoBase >= vBusquedaItem.Clientes.get(k).Documentos.get(kk).Saldo){
                                vL_MontoPago = vBusquedaItem.Clientes.get(k).Documentos.get(kk).Saldo;
                                vL_MontoPagoBase -= vBusquedaItem.Clientes.get(k).Documentos.get(kk).Saldo;
                            }else{
                                vL_MontoPago = vL_MontoPagoBase;
                                vL_MontoPagoBase -= vL_MontoPago;
                            }
                            vL_DocSaldoOk=true;
                            ws_det_operations vItem = new ws_det_operations();
                            vItem.c_cliente_nombre = vBusquedaItem.Clientes.get(k).Nombre;
                            vItem.c_cliente_cedula = vBusquedaItem.Clientes.get(k).Identificacion;
                            vItem.c_cliente_correo = vBusquedaItem.Clientes.get(k).CorreoElectronico;
                            vItem.CicloCliente = vBusquedaItem.Clientes.get(k).CicloCliente;
                            vItem.Cliente_Contrato = vBusquedaItem.Clientes.get(k).IDCliente;
                            vItem.Telefono = vL_Telefono;
                            vItem.MontoPago = vL_MontoPago;
                            vItem.FechaEmision = vBusquedaItem.Clientes.get(k).Documentos.get(kk).FechaEmision;
                            vItem.FechaVencimiento = vBusquedaItem.Clientes.get(k).Documentos.get(kk).FechaVencimiento;
                            vItem.IDDocumento = vBusquedaItem.Clientes.get(k).Documentos.get(kk).IDDocumento;
                            vItem.NPE = vBusquedaItem.Clientes.get(k).Documentos.get(kk).NPE;
                            vItem.Referencia = vBusquedaItem.Clientes.get(k).Documentos.get(kk).Referencia;
                            vItem.SaldoReferencia = vBusquedaItem.Clientes.get(k).Documentos.get(kk).Saldo;
                            vItem.NoDocumento = vBusquedaItem.Clientes.get(k).Documentos.get(kk).NoDocumento;
                            vItem.TipoDocumento = vBusquedaItem.Clientes.get(k).Documentos.get(kk).TipoDocumento;
                            OperApply.MontoPago += vL_MontoPago;
                            OperApply.Datos.add(vItem);
                        }else{break;}
                    }
                    if(vL_MontoPagoBase > 0){
                        vL_DocSaldoOk=true;
                        ws_det_operations vItem = new ws_det_operations();
                        vItem.c_cliente_nombre = vBusquedaItem.Clientes.get(k).Nombre;
                        vItem.c_cliente_cedula = vBusquedaItem.Clientes.get(k).Identificacion;
                        vItem.c_cliente_correo = vBusquedaItem.Clientes.get(k).CorreoElectronico;
                        vItem.CicloCliente = vBusquedaItem.Clientes.get(k).CicloCliente;
                        vItem.Cliente_Contrato = vBusquedaItem.Clientes.get(k).IDCliente;
                        vItem.Telefono = vL_Telefono;
                        vItem.MontoPago = vL_MontoPagoBase;
                        vItem.FechaVencimiento = "";
                        vItem.IDDocumento = "";
                        vItem.NPE = "";
                        vItem.Referencia = "";
                        vItem.SaldoReferencia = 0.00;
                        vItem.NoDocumento = "";
                        OperApply.MontoPago += vL_MontoPagoBase;
                        OperApply.Datos.add(vItem);
                    }
                }

            }


        }


        return OperApply;
    }

    private wsR_gBusqueda getToApplyBusquedas(wsInstancias.wsInstancia Instancia,wAutenticacion Autenticacion,
                                              wsOperacionServicios.wsOperacionServicio OperacionServicio , String Cliente_Contrato, String Telefono ) throws Exception {
        wsR_gBusqueda vWsRes = new wsR_gBusqueda();
        wsOperaciones_MOVIL vWsFind_Movil = new wsOperaciones_MOVIL();
        wsOperaciones_FIJO vWsFind_Fijo = new wsOperaciones_FIJO();
        if( OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL ) ){
            if(Cliente_Contrato.length()>1){
                vWsRes = vWsFind_Movil.Busqueda( Instancia, Autenticacion, OperacionServicio, Cliente_Contrato, "", "",""  );
                return vWsRes;
            }
            if(Telefono.length()>1){
                vWsRes = vWsFind_Movil.Busqueda( Instancia, Autenticacion, OperacionServicio,  "", Telefono, "",""  );
                return vWsRes;
            }
        }
        if( OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.FIJO ) ){
            if( Cliente_Contrato.length()>1){
                vWsRes = vWsFind_Fijo.Busqueda( Instancia, Autenticacion, OperacionServicio, Cliente_Contrato, "", "",""  );
                return vWsRes;
            }
            if( Telefono.length()>1){
                vWsRes = vWsFind_Fijo.Busqueda( Instancia, Autenticacion, OperacionServicio,  "", Telefono, "",""  );
                return vWsRes;
            }
        }
        return vWsRes;
    }




    public wsR_sPagos SetPayments(
            wsInstancias.wsInstancia Instancia,
            wAutenticacion Autenticacion,
            long IDBusqueda,
            wsOperacionServicios.wsOperacionServicio OperacionServicio,
            wsS_RegistroInfoCliente InfoCliente,
            wsS_RegistroOperaciones Operaciones,
            wsS_RegistroFormaPago FormaPago)  throws Exception
    {
        wGenInstanceFuntions wGen = new wGenInstanceFuntions();
        SubDataTable dt = new SubDataTable();
        SubDataTable dt_ = new SubDataTable();
        String vCountryCode = wGen.getCountryCode(Instancia);
        String vQuery = "", vBaseQuery = "",T_BSCS_PAYMENT_API_ACCOUNT="", T_BSCSFU_PAYMENT_API_ACCOUNT="",vCodOperadorTransaccion="";
        OracleConnection _ODA = null;
        OracleConnection _BSCSFU = null;
        Boolean vAppliedFull = false;
        Boolean vError_BSCSFU = false, vBSCSFU_Applied = false;
        Boolean _connTo_bscsfu=false;
        wsR_sPagos vReturn = new wsR_sPagos();


        //Global INFO
        String T_CORR_TRANSAC_GROUP = "", T_CORR_TRANSAC_PAGO = "", T_CORR_ENC_TRANSAC = "", T_CORR_TRANSAC = "", T_RESPUESTA_PAGO=""; Double T_TASA = 0.00;Double T_TASA_504 = 0.00;


        Boolean vPaymentSession = false, WithoutFact=false;
        try {
            vPaymentSession = setPaymentSession(Instancia,Autenticacion);
            if(!vPaymentSession){ throw new Exception("Ocurrio un error en la apertura de sesion." + vLastErrMessage); }
            GlobalDB db = new GlobalDB();
            _ODA = db.getODADBConnection( wGen.getConnectTo(Instancia) );
            _ODA.setAutoCommit(false);
            try {
                //Secuencias
                {
                    if(vCountryCode.equals("507")){
                        vQuery = "select T_CORR_TRANSAC_GROUP.nextval from dual";
                        dt = db.getQuery(vQuery, _ODA);
                        if(dt.vData && dt.vRows>1) {T_CORR_TRANSAC_GROUP  = dt.Datos.rows.get(1).cols.get(0).value_string;}
                        vQuery = "select T_CORR_TRANSAC_PAGO.nextval from dual";
                        dt = db.getQuery(vQuery, _ODA);
                        if(dt.vData && dt.vRows>1) { T_CORR_TRANSAC_PAGO = dt.Datos.rows.get(1).cols.get(0).value_string; }
                        vQuery = "select get_parametro('BSCS_USER_PAYMENT_API') parametro from dual";
                        dt = db.getQuery(vQuery, _ODA);
                        if(dt.vData && dt.vRows>1) { T_BSCS_PAYMENT_API_ACCOUNT = dt.Datos.rows.get(1).cols.get(0).value_string; }
                        vQuery = "select get_parametro('BSCSFU_USER_PAYMENT_API') parametro from dual";
                        dt = db.getQuery(vQuery, _ODA);
                        if(dt.vData && dt.vRows>1) { T_BSCSFU_PAYMENT_API_ACCOUNT = dt.Datos.rows.get(1).cols.get(0).value_string; }
                        T_TASA = 1.0;
                    }else{
                        //Registra en TransaccionesRegPago
                        vQuery = "select corr_transac_pago.nextval from dual";
                        dt = db.getQuery(vQuery, _ODA);
                        if(dt.vData && dt.vRows>1) { T_CORR_TRANSAC_PAGO = dt.Datos.rows.get(1).cols.get(0).value_string; }

                        //Obtiene Tasa en Dolar
                        vQuery = "select get_parametro_dolar() from dual";
                        dt = db.getQuery(vQuery, _ODA);
                        if(dt.vData && dt.vRows>1) { T_TASA =   dt.Datos.rows.get(1).cols.get(0).value_double ; } else{ T_TASA = 1.0; }
                    }
                }



                //Registro de TransaccioneRegPago
                {
                    if( vCountryCode.equals("507")  ){
                        vBaseQuery = "INSERT INTO TRANSACCIONES_REG_PAGO(CORR_TRANSAC_PAGO,NUM_CORR,TIP_PAG_TRAN,COD_BANCO,COD_BANCO_TIPO,CREDITO_DEBITO,     NUM_CTA_BANCO,NUM_SER_CUENTA,NUMERO_TARJETA,COD_AUTORIZA,FECHA_VENCIMIENTO,PAGO_DOLARES, TOTAL_RECIBIDO, VUELTO)   " +
                                "VALUES(?,?,?,?,?,?,?,?,to_date(?,'ddMMyy'),?,?,?,?,?) ";
                        PreparedStatement _cmd_t_regpago =  _ODA.prepareStatement (vBaseQuery);
                        _cmd_t_regpago.setString(1,T_CORR_TRANSAC_PAGO);
                        _cmd_t_regpago.setInt(2,  0 );
                        _cmd_t_regpago.setString(3, wGen.GetTipPagTran( Instancia, FormaPago.TipoFormaPago) );
                        _cmd_t_regpago.setString(4, FormaPago.C_CodBanco.Banco);
                        _cmd_t_regpago.setString(5, "-1");
                        _cmd_t_regpago.setString(6, (wGen.GetTipPagTran( Instancia, FormaPago.TipoFormaPago).equals("T")?"CREDITO":""));

                        _cmd_t_regpago.setString(7, FormaPago.C_NumeroCuenta);
                        _cmd_t_regpago.setString(8, FormaPago.C_NumeroCheque);
                        _cmd_t_regpago.setString(9,FormaPago.T_Numero_Tarjeta);
                        _cmd_t_regpago.setString(10,FormaPago.T_CodAutorizacion);
                        if( FormaPago.T_Vencimiento==null){
                            _cmd_t_regpago.setString(11,null);
                        } else{
                            if(FormaPago.T_Vencimiento.length()>2) {
                                if(FormaPago.T_Vencimiento.length()<=4) {
                                    _cmd_t_regpago.setString(11,"01" +"/" + FormaPago.T_Vencimiento.substring(0,2) + "/" +  FormaPago.T_Vencimiento.substring(3));
                                } else {
                                    _cmd_t_regpago.setString(11,FormaPago.T_Vencimiento );
                                }
                            } else {
                                _cmd_t_regpago.setString(11,null);
                            }
                        }
                        _cmd_t_regpago.setDouble(12,FormaPago.MontoPago);
                        _cmd_t_regpago.setDouble(13,0.0);
                        _cmd_t_regpago.setDouble(14,0.0);
                        dt_ = db.setQuery(_cmd_t_regpago);
                        if(!dt_.vData){ dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception( "Error en Pago>" + dt.vDBMessage );  }
                        _cmd_t_regpago.close();
                    }else{
                        if(vCountryCode.equals("503")){
                            vBaseQuery = "INSERT INTO TRANSACCIONES_REG_PAGO(CORR_TRANSAC_PAGO,NUM_CORR,TIP_PAG_TRAN,COD_BANCO,NUM_CTA_BANCO,NUM_SER_CUENTA,NUMERO_TARJETA,COD_AUTORIZA,FECHA_VENCIMIENTO,PAGO_DOLARES,PAGO_COLONES)   " +
                                    "VALUES(?,?,?,?,?,?,?,?,to_date(?,'ddMMyy'),?,Round( ? *get_parametro_dolar(),2)) ";
                        }else{
                            if( FormaPago.Moneda == wsMonedas.wsMoneda.LOCAL ) {
                                vBaseQuery = "INSERT INTO TRANSACCIONES_REG_PAGO(CORR_TRANSAC_PAGO,NUM_CORR,TIP_PAG_TRAN,COD_BANCO,NUM_CTA_BANCO,NUM_SER_CUENTA,NUMERO_TARJETA,COD_AUTORIZA,FECHA_VENCIMIENTO,PAGO_DOLARES,PAGO_COLONES)   " +
                                        "VALUES(?,?,?,?,?,?,?,?,to_date(?,'ddMMyy'),Round(?/get_parametro_dolar(),2) , ?) ";
                            }else{
                                vBaseQuery = "INSERT INTO TRANSACCIONES_REG_PAGO(CORR_TRANSAC_PAGO,NUM_CORR,TIP_PAG_TRAN,COD_BANCO,NUM_CTA_BANCO,NUM_SER_CUENTA,NUMERO_TARJETA,COD_AUTORIZA,FECHA_VENCIMIENTO,PAGO_DOLARES,PAGO_COLONES)   " +
                                        "VALUES(?,?,?,?,?,?,?,?,to_date(?,'ddMMyy'), ? , Round(?*get_parametro_dolar(),2) ) ";
                            }
                        }
                        PreparedStatement _cmd_t_regpago =  _ODA.prepareStatement (vBaseQuery);
                        _cmd_t_regpago.setString(1,T_CORR_TRANSAC_PAGO);
                        _cmd_t_regpago.setInt(2,  0 );
                        _cmd_t_regpago.setString(3, wGen.GetTipPagTran( Instancia, FormaPago.TipoFormaPago) );
                        _cmd_t_regpago.setString(4, FormaPago.C_CodBanco.Banco);
                        _cmd_t_regpago.setString(5, FormaPago.C_NumeroCuenta);
                        _cmd_t_regpago.setString(6, FormaPago.C_NumeroCheque);
                        _cmd_t_regpago.setString(7,FormaPago.T_Numero_Tarjeta);
                        _cmd_t_regpago.setString(8,FormaPago.T_CodAutorizacion);
                        _cmd_t_regpago.setString(9,(FormaPago.T_Vencimiento.length()>2)?"01"+FormaPago.T_Vencimiento:"");
                        _cmd_t_regpago.setDouble(10,FormaPago.MontoPago);
                        _cmd_t_regpago.setDouble(11,FormaPago.MontoPago);

                        dt_ = db.setQuery(_cmd_t_regpago);
                        if(!dt_.vData){ dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception( "Error en Pago>" + dt.vDBMessage );  }
                        _cmd_t_regpago.close();
                    }
                }


                //Detalles y Validaciones previas a Aplicacion. Segmento para Evaluacion de Items a Aplicar para detallar las facturas y los pagos.
                ws_list_operations wListOperationsToApply = new ws_list_operations();
                wListOperationsToApply = getToApply(Instancia, Autenticacion, OperacionServicio, Operaciones, FormaPago);



                //RegistraEncTransaccion
                {
                    if(vCountryCode.equals("507")){
                        vQuery = "select T_CORR_ENC_TRANSAC.nextval from dual";
                    }else{
                        vQuery = "select corr_enc_transac_sid.nextval from dual";
                    }
                    dt = db.getQuery(vQuery, _ODA);
                    if(dt.vData && dt.vRows>1) { T_CORR_ENC_TRANSAC = dt.Datos.rows.get(1).cols.get(0).value_string; }

                    if(vCountryCode.equals("507")){
                        vBaseQuery = "INSERT INTO TRANSACCIONES_ENC(CORR_ENC_TRANSAC,NOMBRE_CLIENTE,DIRECCION_CLIENTE,NUM_REGISTRO,GIRO,NIT,EXENTO,TIPO,MUNICIPIO,DEPARTAMENTO,NUMERO_FCF,DUI,TELEFONO_ORIGEN,TELEFONO_REFERENCIA)   " +
                                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                    }else{
                        vBaseQuery = "INSERT INTO ENC_TRANSACCION(CORR_ENC_TRANSAC,NOMBRE_CLIENTE,DIRECCION_CLIENTE,NUM_REGISTRO,GIRO,NIT,EXENTO,TIPO,MUNICIPIO,DEPARTAMENTO,NUMERO_FCF,DUI,TELEFONO_ORIGEN,TELEFONO_REFERENCIA)   " +
                                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
                    }
                    PreparedStatement _cmd_t_enc = _ODA.prepareStatement (vBaseQuery);
                    _cmd_t_enc.setString(1,T_CORR_ENC_TRANSAC);
                    _cmd_t_enc.setString(2,wListOperationsToApply.Datos.get(0).c_cliente_nombre); // InfoCliente.NombreCliente);//Nombre
                    _cmd_t_enc.setString(3,InfoCliente.Direccion);
                    _cmd_t_enc.setString(4,InfoCliente.Identificacion); //NumRegistro
                    _cmd_t_enc.setString(5,"");//Giro
                    _cmd_t_enc.setString(6,InfoCliente.DocumentoTributario);//Nit
                    _cmd_t_enc.setString(7,"N"); //Exento
                    _cmd_t_enc.setString(8,"");//Tipo
                    _cmd_t_enc.setString(9,"");//Municipio
                    _cmd_t_enc.setString(10,"");//Departamento
                    _cmd_t_enc.setString(11,InfoCliente.DocumentoTributario) ; //NumeroFCF
                    _cmd_t_enc.setString(12,InfoCliente.Identificacion); //Dui
                    _cmd_t_enc.setString(13,InfoCliente.Telefono_Contacto); //Telefono_Origen
                    _cmd_t_enc.setString(14,InfoCliente.Telefono_MovilContacto); //TelefonoReferencia
                    /////_cmd_t_enc.ExecuteNonQuery();
                    dt_ = db.setQuery(_cmd_t_enc);
                    if(!dt_.vData) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>" + dt.vDBMessage); }
                    _cmd_t_enc.close();
                }




                //Registra en Transacciones
                for(int i = 0;i < wListOperationsToApply.Datos.size();i++) {
                    if( vCountryCode.equals("507") ){
                        vQuery = "select T_CORR_TRANSAC.nextval , 1 TASA from dual";
                    }else{
                        vQuery = "select corr_transac_sid.nextval , GET_PARAMETRO_DOLAR TASA from dual";
                    }
                    dt = db.getQuery(vQuery, _ODA);
                    if(dt.vData && dt.vRows>1) {
                        T_CORR_TRANSAC = dt.Datos.rows.get(1).cols.get(0).value_string;
                        T_TASA = dt.Datos.rows.get(1).cols.get(1).value_double;
                    }
                    if(vCountryCode.equals("504")  ){
                        if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.MOVIL && wListOperationsToApply.Datos.get(i).Referencia.trim().length()>0){
                            vQuery = "select max(c.rate)            " +
                                    "from orderhdr_all@bscs oh, pfh_conversion_rates@bscs c     " +
                                    "where ohcostcent = 5                       " +
                                    "AND oh.ohrefdate = c.reference_date(+)     " +
                                    "and ohstatus in ('IN','CM')                " +
                                    "and ohrefnum not like 'AM%'                " +
                                    "and ohrefnum =vfactura                     " +
                                    "and oh.ohopnamt_doc >= 0                   ";
                            vQuery = vQuery.replace("vfactura","'" + wListOperationsToApply.Datos.get(i).Referencia.trim() + "'");
                            dt = db.getQuery(vQuery, _ODA);
                            if(dt.Datos.rows.size()>1){
                                if( dt.Datos.rows.get(1).cols.get(0).value != null ){
                                    T_TASA = dt.Datos.rows.get(1).cols.get(0).value_double;
                                }
                            }
                        }
                        if(OperacionServicio == wsOperacionServicios.wsOperacionServicio.FIJO ){
                            if( wListOperationsToApply.Datos.get(i).Referencia.trim().length()>0 && wListOperationsToApply.Datos.get(i).FechaEmision.length()>0 ){
                                vQuery = "select smart.ser_obtener_tasa_cambio@open_hond( to_date('vfecha','dd/MM/yyyy') ) from dual";
                                vQuery = vQuery.replace("vfecha",wListOperationsToApply.Datos.get(i).FechaEmision);
                            }else {
                                vQuery = "select smart.ser_obtener_tasa_cambio@open_hond( trunc(sysdate) ) from dual";
                            }
                            dt = db.getQuery(vQuery, _ODA);
                            if(dt.Datos.rows.size()>1){
                                if( dt.Datos.rows.get(1).cols.get(0).value != null ){
                                    T_TASA = dt.Datos.rows.get(1).cols.get(0).value_double;
                                }
                            }
                        }
                    }




                    //Registrar en Transacciones
                    if(vCountryCode.equals("507")){
                        vBaseQuery = "INSERT INTO TRANSACCIONES(CORR_TRANSAC_GROUP,CORR_TRANSAC,CORR_ENC_TRANSAC,CORR_TRANSAC_PAGO,COD_AGENCIA,COD_AGENCIA_PROC,COD_CAJA,COD_CAJERO,FEC_REAL_PAGO,    " +
                                "COD_OPERADOR,NUM_REFERENCIA,NUM_TELEFONO,NUM_CLIENTE,MONTO_TRANSAC,PAGO_DOLARES,FEC_VEN_FACTURA, " +
                                "ESTATUS_TRANSAC,PROCE_TRANSAC,NPE,TIPO_MONEDA,TOTAL_PAGAR,ESTADO_PAGO,VALOR_CHEQUE,CLIENTE_CICLO,SESION_CORRELATIVO, SEC_BANCO) " +
                                "values( ?,?,?,?,?,?,?,?,trunc(sysdate),   " +
                                "?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),   " +
                                "?,?,?,?,?,?,?,?,?, ?)";

                        PreparedStatement _cmd_t_tran = _ODA.prepareStatement (vBaseQuery);
                        _cmd_t_tran.setString(1,T_CORR_TRANSAC_GROUP);
                        _cmd_t_tran.setString(2,T_CORR_TRANSAC);
                        _cmd_t_tran.setString(3,T_CORR_ENC_TRANSAC);
                        _cmd_t_tran.setString(4,T_CORR_TRANSAC_PAGO);

                        _cmd_t_tran.setString(5,gCodAgencia);
                        _cmd_t_tran.setString(6,gCodAgencia);
                        _cmd_t_tran.setString(7,gCodCaja);
                        _cmd_t_tran.setString(8,gCodCajero);
                        //FecRealPago / Dentro de Query
                        if( wListOperationsToApply.Datos.get(i).Referencia.trim().length() > 2 || wListOperationsToApply.Datos.get(i).NPE.trim().length() > 8 ){ WithoutFact = false; }else{ WithoutFact = true; }
                        vCodOperadorTransaccion  = wGen.GetCodOperador( Instancia, OperacionServicio, WithoutFact );
                        //WithoutFact = (wListOperationsToApply.Datos.get(i).NPE.trim().length()<=0||wListOperationsToApply.Datos.get(i).Referencia.trim().length()<=0) ; //|| OperacionServicio.equals(wsOperacionServicios.wsOperacionServicio.MOVIL))?true:false
                        _cmd_t_tran.setString(9,  vCodOperadorTransaccion );
                        _cmd_t_tran.setString(10, wListOperationsToApply.Datos.get(i).Referencia);
                        _cmd_t_tran.setString(11, wListOperationsToApply.Datos.get(i).Telefono);
                        _cmd_t_tran.setString(12, wListOperationsToApply.Datos.get(i).Cliente_Contrato);
                        _cmd_t_tran.setDouble(13, wListOperationsToApply.Datos.get(i).SaldoReferencia ); //MontTransacc
                        _cmd_t_tran.setDouble(14,Double_Round(wListOperationsToApply.Datos.get(i).MontoPago )); //PagoDolares
                        _cmd_t_tran.setString(15, wListOperationsToApply.Datos.get(i).FechaVencimiento); //Fechavencimiento

                        _cmd_t_tran.setString(16,"E");
                        _cmd_t_tran.setString(17,"L");

                        _cmd_t_tran.setString(18,wListOperationsToApply.Datos.get(i).NPE);
                        _cmd_t_tran.setString(19,"1");

                        _cmd_t_tran.setDouble(20,wListOperationsToApply.Datos.get(i).MontoPago); //TotalPagar
                        _cmd_t_tran.setString(21,  ( Double_Round(wListOperationsToApply.Datos.get(i).SaldoReferencia)==Double_Round(wListOperationsToApply.Datos.get(i).MontoPago)  )?"T":"P"  );
                        _cmd_t_tran.setDouble(22,FormaPago.MontoPago); //Valor Cheque
                        _cmd_t_tran.setString(23, wListOperationsToApply.Datos.get(i).CicloCliente );  //ClienteCiclo

                        _cmd_t_tran.setString(24, gSessionCorrelativo );  //SesionCorrelativo
                        _cmd_t_tran.setString(25, String.valueOf(IDBusqueda) ); //SecBanco
                        dt_ = db.setQuery(_cmd_t_tran);
                        if(!dt_.vData) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>" + dt.vDBMessage ); }
                        _cmd_t_tran.close();
                        //ODA/GT/SV/HN/NI/CR**


                        //Transacciones hacia BSCSfu
                        //Operaciones con Facturadores
                        switch(vCodOperadorTransaccion) {
                            case "218":
                            case "219":
                            case "220":
                            case "318":
                            case "319":
                            case "230":
                            case "231":
                            case "232":
                                if(_connTo_bscsfu!=true){
                                    _BSCSFU = db.getODADBConnection(ConnectTo.ODA_507_BSCSFU_db);
                                    _BSCSFU.setAutoCommit(false);
                                    _connTo_bscsfu=true;
                                }
                                if( WithoutFact==false  &&  wListOperationsToApply.Datos.get(i).Referencia.length()>3   ){
                                    vBaseQuery = "{CALL PAGOS_ODA.ppayment(?,?,?,?,?,?,?,?)}";
                                    CallableStatement _cmd_t_tran_bscsfu_x1 = _BSCSFU.prepareCall(vBaseQuery);
                                    String vOhxAct="";

                                    dt = db.getQuery("select ohxact from orderhdr_all where customer_id='" + wListOperationsToApply.Datos.get(i).Cliente_Contrato + "' and ohrefnum='" + wListOperationsToApply.Datos.get(i).Referencia + "'", _BSCSFU);
                                    if(dt.Datos.rows.size()>1){
                                        if( dt.Datos.rows.get(1).cols.get(0).value != null ){
                                            vOhxAct = dt.Datos.rows.get(1).cols.get(0).value_string;
                                        }
                                    }
                                    _cmd_t_tran_bscsfu_x1.setString(1, vOhxAct);
                                    _cmd_t_tran_bscsfu_x1.setDouble(2, wListOperationsToApply.Datos.get(i).MontoPago);
                                    _cmd_t_tran_bscsfu_x1.setString(3, T_BSCSFU_PAYMENT_API_ACCOUNT);
                                    _cmd_t_tran_bscsfu_x1.setString(4, T_CORR_TRANSAC);
                                    _cmd_t_tran_bscsfu_x1.setString(5, "CE2IN-X1");
                                    _cmd_t_tran_bscsfu_x1.setString(6, gCodAgencia);
                                    _cmd_t_tran_bscsfu_x1.setString(7, DateToString(   new Date() , "ddMMyyyyHHmmss"));
                                    _cmd_t_tran_bscsfu_x1.registerOutParameter(8, Types.VARCHAR, 1000);
                                    dt_ = db.setQuery(_cmd_t_tran_bscsfu_x1);
                                    if (_cmd_t_tran_bscsfu_x1.getString(8) == null) {_cmd_t_tran_bscsfu_x1.setString(8, "");}
                                    if (!dt_.vData) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>" + dt.vDBMessage); }
                                    T_RESPUESTA_PAGO =_cmd_t_tran_bscsfu_x1.getString(8);
                                    try {_cmd_t_tran_bscsfu_x1.close(); } catch (Exception e1) {}
                                }else{
                                    vBaseQuery = "{CALL PAGOS_ODA.ppayment(?,?,?,?,?,?,?,?,?)}";
                                    CallableStatement _cmd_t_tran_bscsfu = _BSCSFU.prepareCall(vBaseQuery);
                                    _cmd_t_tran_bscsfu.setString(1, wListOperationsToApply.Datos.get(i).Cliente_Contrato );
                                    _cmd_t_tran_bscsfu.setDouble(2, Double_Round(wListOperationsToApply.Datos.get(i).MontoPago));
                                    _cmd_t_tran_bscsfu.setString(3, T_BSCSFU_PAYMENT_API_ACCOUNT);
                                    _cmd_t_tran_bscsfu.setString(4, T_CORR_TRANSAC);
                                    _cmd_t_tran_bscsfu.setString(5, "CE2IN-X3");
                                    _cmd_t_tran_bscsfu.setString(6,  wListOperationsToApply.Datos.get(i).Cliente_Contrato);
                                    _cmd_t_tran_bscsfu.setString(7, gCodAgencia);
                                    _cmd_t_tran_bscsfu.setString(8, DateToString(   new Date() , "ddMMyyyyHHmmss"));
                                    _cmd_t_tran_bscsfu.registerOutParameter(9, Types.VARCHAR, 1000);
                                    dt_ = db.setQuery(_cmd_t_tran_bscsfu);
                                    if (_cmd_t_tran_bscsfu.getString(9) == null) {_cmd_t_tran_bscsfu.setString(9, "");}
                                    if (!dt_.vData) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>" + dt.vDBMessage); }
                                    T_RESPUESTA_PAGO =_cmd_t_tran_bscsfu.getString(9);
                                    try {_cmd_t_tran_bscsfu.close(); } catch (Exception e1) {}
                                }
                                if (!T_RESPUESTA_PAGO.equals("Succesfull")) {
                                    _BSCSFU.rollback();
                                    vError_BSCSFU = true;
                                    dt.vDBMessage = dt_.vMessage + dt_.vDBMessage +T_RESPUESTA_PAGO ;
                                    throw new Exception("Ocurrio Error aplicando Transaccion en BSCS/FU. {Erro>" +dt.vDBMessage);
                                } else {
                                    vBSCSFU_Applied = true;
                                    PreparedStatement _cmd_t_tran_u_p = _ODA.prepareCall("UPDATE TRANSACCIONES SET ID_OPERACION_PAGO=? WHERE CORR_TRANSAC_GROUP=? AND CORR_TRANSAC=?");
                                    _cmd_t_tran_u_p.setString(1, T_RESPUESTA_PAGO);
                                    _cmd_t_tran_u_p.setString(2, T_CORR_TRANSAC_GROUP);
                                    _cmd_t_tran_u_p.setString(3, T_CORR_TRANSAC);
                                    dt_ = db.setQuery(_cmd_t_tran_u_p);
                                    if (!dt_.vData) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>" + dt.vDBMessage);}
                                    try { _cmd_t_tran_u_p.close(); } catch (Exception e2) { }
                                }
                                //try {_cmd_t_tran_bscsfu.close();} catch (Exception e3) {}
                                break;
                        }


                    }else{
                        //ODA/GT/SV/HN/NI/CR
                        //Completar Datos y Validacion, acorde a la data recibida en el arreglo
                        /*
                            1	CORR_TRANSAC,
                            2	CORR_ENC_TRANSAC,
                            3	CORR_TRANSAC_PAGO,
                            4	COD_AGENCIA,
                            5	COD_AGENCIA_PROC,
                            6	COD_CAJA,
                            7	COD_CAJERO,
                            TRUNC(SYSDATE)	FEC_REAL_PAGO,
                            8	COD_TIPO_TRAN,
                            9	COD_OPERADOR,
                            10	NUM_REFERENCIA,
                            11	NUM_TELEFONO,
                            12	NUM_CLIENTE,
                            13	MONTO_TRANSAC,
                            14	PAGO_DOLARES,
                            15	PAGO_COLONES,
                            16	MONTO_FACTURA_EQ,
                            17	FEC_VEN_FACTURA,
                            18	ESTATUS_TRANSAC,
                            19	NPE,
                            20	TIPO_MONEDA,
                            21	TOTAL_PAGAR,
                            22	ESTADO_PAGO,
                            23	VALOR_CHEQUE,
                            24	CLIENTE_CICLO,
                            25	SEC_BANCO,
                            N	EXENTO,
                            N	GENERA_TICKET,
                            SYSDATE	FEC_TRANSACCION,
                            E	TIP_PAG_TRAN,
                            L	PROCE_TRANSAC,
                            26	TOT_MP,
                            N	ERROR,
                            27	CORR_TICKET,
                            28	COD_BANCO
                        */
                        if(vCountryCode.equals("504")){
                            vBaseQuery = "INSERT INTO TRANSACCIONES(CORR_TRANSAC,CORR_ENC_TRANSAC,CORR_TRANSAC_PAGO,COD_AGENCIA,COD_AGENCIA_PROC,COD_CAJA,COD_CAJERO,FEC_REAL_PAGO,COD_TIPO_TRAN,    " +
                                    "COD_OPERADOR,NUM_REFERENCIA,NUM_TELEFONO,NUM_CLIENTE,MONTO_TRANSAC,PAGO_DOLARES,PAGO_COLONES,MONTO_FACTURA_EQ,FEC_VEN_FACTURA," +
                                    "ESTATUS_TRANSAC,NPE,TIPO_MONEDA,TOTAL_PAGAR,ESTADO_PAGO,VALOR_CHEQUE,CLIENTE_CICLO,SEC_BANCO, EXENTO, GENERA_TICKET,FEC_TRANSACCION,TIP_PAG_TRAN,PROCE_TRANSAC,TOT_MP,ERROR,CORR_TICKET, COD_BANCO, TASA_CALC) " +
                                    "values( ?,?,?,?,?,?,?,trunc(sysdate),?,   " +
                                    "?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy')," +
                                    "?,?,?,?,?,?,?,?, 'N', 'N',SYSDATE,'E','L',?,'N',0, ?, ?)";

                        }else{
                            vBaseQuery = "INSERT INTO TRANSACCIONES(CORR_TRANSAC,CORR_ENC_TRANSAC,CORR_TRANSAC_PAGO,COD_AGENCIA,COD_AGENCIA_PROC,COD_CAJA,COD_CAJERO,FEC_REAL_PAGO,COD_TIPO_TRAN,    " +
                                    "COD_OPERADOR,NUM_REFERENCIA,NUM_TELEFONO,NUM_CLIENTE,MONTO_TRANSAC,PAGO_DOLARES,PAGO_COLONES,MONTO_FACTURA_EQ,FEC_VEN_FACTURA," +
                                    "ESTATUS_TRANSAC,NPE,TIPO_MONEDA,TOTAL_PAGAR,ESTADO_PAGO,VALOR_CHEQUE,CLIENTE_CICLO,SEC_BANCO, EXENTO, GENERA_TICKET,FEC_TRANSACCION,TIP_PAG_TRAN,PROCE_TRANSAC,TOT_MP,ERROR,CORR_TICKET, COD_BANCO) " +
                                    "values( ?,?,?,?,?,?,?,trunc(sysdate),?,   " +
                                    "?,?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy')," +
                                    "?,?,?,?,?,?,?,?, 'N', 'N',SYSDATE,'E','L',?,'N',0, ?)";

                        }


                        PreparedStatement _cmd_t_tran = _ODA.prepareStatement (vBaseQuery);
                        _cmd_t_tran.setString(1,T_CORR_TRANSAC);
                        _cmd_t_tran.setString(2,T_CORR_ENC_TRANSAC);
                        _cmd_t_tran.setString(3,T_CORR_TRANSAC_PAGO);
                        _cmd_t_tran.setString(4,gCodAgencia);
                        _cmd_t_tran.setString(5,gCodAgencia);
                        _cmd_t_tran.setString(6,gCodCaja);
                        _cmd_t_tran.setString(7,gCodCajero);
                        _cmd_t_tran.setString(8,getCodTipoTran(vCountryCode, OperacionServicio));

                        if( wListOperationsToApply.Datos.get(i).Referencia.trim().length() > 2 || wListOperationsToApply.Datos.get(i).NPE.trim().length() > 8 ){ WithoutFact = false; }else{ WithoutFact = true; }
                        //WithoutFact = (wListOperationsToApply.Datos.get(i).NPE.trim().length()<=0||wListOperationsToApply.Datos.get(i).Referencia.trim().length()<=1) ; //|| OperacionServicio.equals(wsOperacionServicios.wsOperacionServicio.MOVIL))?true:false
                        String vOperacionAplicar = "";
                        vOperacionAplicar = wGen.GetCodOperador( Instancia, OperacionServicio, WithoutFact );
                        if(   vCountryCode.equals("503")   ){
                            if( wListOperationsToApply.Datos.get(i).TipoDocumento == wsTipoDocumentos.wsTipoDocumento.FINANCIAMIENTO  ){
                                vOperacionAplicar = "210";
                            }
                        }
                        _cmd_t_tran.setString(9,   vOperacionAplicar);


                        _cmd_t_tran.setString(10, wListOperationsToApply.Datos.get(i).Referencia);
                        _cmd_t_tran.setString(11, wListOperationsToApply.Datos.get(i).Telefono);
                        _cmd_t_tran.setString(12, wListOperationsToApply.Datos.get(i).Cliente_Contrato);
                        _cmd_t_tran.setDouble(13, wListOperationsToApply.Datos.get(i).SaldoReferencia ); //MontTransacc
                        if( vCountryCode.equals("503") || vCountryCode.equals("507") ){
                            _cmd_t_tran.setDouble(14,Double_Round(wListOperationsToApply.Datos.get(i).MontoPago )); //PagoDolares porque recibo dolar
                            _cmd_t_tran.setDouble(15,Double_Round(wListOperationsToApply.Datos.get(i).MontoPago * T_TASA) ); //PagoColones porque recibo dolar
                            _cmd_t_tran.setDouble(16,Double_Round(wListOperationsToApply.Datos.get(i).MontoPago )); //PagoDolares porque recibo dolar
                        }else{
                            if( FormaPago.Moneda == wsMonedas.wsMoneda.LOCAL ){
                                if( vCountryCode.equals("504") &&  (OperacionServicio == wsOperacionServicios.wsOperacionServicio.MOVIL || OperacionServicio == wsOperacionServicios.wsOperacionServicio.FIJO ) ) { //Todas las operaciones de PAGO se hacen como Dolar
                                    _cmd_t_tran.setDouble(14, Double_Round(wListOperationsToApply.Datos.get(i).MontoPago / T_TASA)); //PagoDolares
                                    _cmd_t_tran.setDouble(15, Double_Round(wListOperationsToApply.Datos.get(i).MontoPago)); //PagoColones
                                    _cmd_t_tran.setDouble(16, Double_Round(wListOperationsToApply.Datos.get(i).MontoPago / T_TASA)); //PagoDolares
                                }else{
                                    _cmd_t_tran.setDouble(14,Double_Round(wListOperationsToApply.Datos.get(i).MontoPago / T_TASA)); //PagoDolares
                                    _cmd_t_tran.setDouble(15,Double_Round(wListOperationsToApply.Datos.get(i).MontoPago) ); //PagoColones
                                    _cmd_t_tran.setDouble(16,Double_Round(wListOperationsToApply.Datos.get(i).MontoPago) ); //PagoColones
                                }
                            }else{
                                    _cmd_t_tran.setDouble(14,Double_Round(wListOperationsToApply.Datos.get(i).MontoPago)); //PagoDolares
                                    _cmd_t_tran.setDouble(15,Double_Round(wListOperationsToApply.Datos.get(i).MontoPago * T_TASA) ); //PagoColones
                                    _cmd_t_tran.setDouble(16,Double_Round(wListOperationsToApply.Datos.get(i).MontoPago)); //PagoDolares
                            }
                        }
                        _cmd_t_tran.setString(17, wListOperationsToApply.Datos.get(i).FechaVencimiento); //Fechavencimiento
                        _cmd_t_tran.setString(18,"E");
                        _cmd_t_tran.setString(19,wListOperationsToApply.Datos.get(i).NPE);
                        if( vCountryCode.equals("504") &&  FormaPago.Moneda==wsMonedas.wsMoneda.LOCAL && (OperacionServicio == wsOperacionServicios.wsOperacionServicio.MOVIL || OperacionServicio == wsOperacionServicios.wsOperacionServicio.FIJO ) ) { //Todas las operaciones de PAGO se hacen como Dolar
                            _cmd_t_tran.setString(20, "2" );//Se Registrara como Dolar
                            _cmd_t_tran.setDouble(21, Double_Round(wListOperationsToApply.Datos.get(i).MontoPago / T_TASA)); //TotalPagar
                        }else{
                            _cmd_t_tran.setString(20,wGen.GetTipoMoneda( Instancia, FormaPago  ) );
                            _cmd_t_tran.setDouble(21, Double_Round(wListOperationsToApply.Datos.get(i).MontoPago )); //TotalPagar
                        }
                        if(WithoutFact){
                            _cmd_t_tran.setString(22,  "T"  );
                        }else{
                            _cmd_t_tran.setString(22,  ( Double_Round(wListOperationsToApply.Datos.get(i).SaldoReferencia)==Double_Round(wListOperationsToApply.Datos.get(i).MontoPago)  )?"T":"P"  );
                        }
                        if( vCountryCode.equals("504") &&  FormaPago.Moneda==wsMonedas.wsMoneda.LOCAL && (OperacionServicio == wsOperacionServicios.wsOperacionServicio.MOVIL || OperacionServicio == wsOperacionServicios.wsOperacionServicio.FIJO ) ) { //Todas las operaciones de PAGO se hacen como Dolar
                            _cmd_t_tran.setDouble(23,Double_Round(FormaPago.MontoPago / T_TASA)); //Valor Cheque
                        }else{
                            _cmd_t_tran.setDouble(23,FormaPago.MontoPago); //Valor Cheque
                        }
                        _cmd_t_tran.setString(24, wListOperationsToApply.Datos.get(i).CicloCliente );  //ClienteCiclo
                        _cmd_t_tran.setString(25, String.valueOf(IDBusqueda) ); //SecBanco
                        _cmd_t_tran.setString(26,wGen.GetTipoMoneda( Instancia, FormaPago  ) );
                        _cmd_t_tran.setString(27,Autenticacion.vIDBanco);
                        if(vCountryCode.equals("504")){
                            _cmd_t_tran.setDouble(28,T_TASA);
                        }
                        dt_ = db.setQuery(_cmd_t_tran);
                        if(!dt_.vData) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Pago>" + dt.vDBMessage ); }
                        _cmd_t_tran.close();
                        //ODA/GT/SV/HN/NI/CR**
                    }
                }


                if(_connTo_bscsfu){
                    if(vError_BSCSFU == false && vBSCSFU_Applied) { _BSCSFU.commit(); }
                    _ODA.commit();
                }else{
                    _ODA.commit();
                }

                if(vCountryCode.equals("507")){
                    try{
                        //Procedimiento al finalizar el Pago
                        vBaseQuery = "{CALL PKG_TRANSACTION_PROCESS.process_transaction_end(?) }";
                        PreparedStatement _cmd_t_tran_oda_final = _ODA.prepareCall (vBaseQuery);
                        _cmd_t_tran_oda_final.setString(1,T_CORR_TRANSAC_GROUP);
                        dt_ = db.setQuery(_cmd_t_tran_oda_final);
                        try{_ODA.commit();}catch (Exception eD){}
                        try{_cmd_t_tran_oda_final.close();}catch (Exception e3){}
                    } catch(Exception e) { }
                }




                try{  _ODA.close();     }catch(Exception eO1){}
                try{   _BSCSFU.close();     }catch(Exception eO3){}

                vReturn.vID = Integer.parseInt(T_CORR_TRANSAC);
                vReturn.vIDPago = Integer.parseInt(T_CORR_TRANSAC_PAGO);
                vReturn.vMensaje="ok";
                vReturn.vMensaje="OK";
                vReturn.vEstado=1;
                FormaPago.idFormaPago = Integer.parseInt(T_CORR_TRANSAC_PAGO);
                vReturn.FormaPago = FormaPago;
                wsR_sPagosInfoFacturador vEstado=new wsR_sPagosInfoFacturador();
                vEstado.Facturador= wsFacturadores.wsFacturador.ODA;vEstado.vEstado=1;
                vReturn.vEstadoFacturadores.Datos.add( vEstado );

            } catch(Exception eCC) {

                setInfo(eCC.getMessage() ); eCC.printStackTrace();

                try {_ODA.rollback();} catch (Exception eOR) {}
                try {_ODA.close();} catch (Exception eO1) {}
                try{  _BSCSFU.rollback();     }catch(Exception eO2){}
                try{  _BSCSFU.close();     }catch(Exception eO2){}

                vReturn.vMensaje = eCC.getMessage();
                vReturn.vEstado = -1;
                wsR_sPagosInfoFacturador vEstado = new wsR_sPagosInfoFacturador();
                vEstado.Facturador = wsFacturadores.wsFacturador.ODA;
                vEstado.vEstado = -1;
                vEstado.vMensaje = eCC.getMessage();
                vReturn.vEstadoFacturadores.Datos.add(vEstado);
            }
        } catch(Exception ePP) {
                setInfo("Error:PP>>");
                setInfo(ePP.getMessage() ); ePP.printStackTrace();

                vReturn.vMensaje=ePP.getMessage();
                vReturn.vEstado=-1;
                wsR_sPagosInfoFacturador vEstado=new wsR_sPagosInfoFacturador();
                vEstado.Facturador= wsFacturadores.wsFacturador.ODA;vEstado.vEstado=-1; vEstado.vMensaje = ePP.getMessage();
                vReturn.vEstadoFacturadores.Datos.add( vEstado );
        }finally {
            if(_ODA!=null){
                try{if( _ODA.isClosed()==false ){_ODA.close();}}catch(Exception exC){}
            }
        }
        return vReturn;
    }



    public wsR_sPagos sAnulacion(
            wsInstancias.wsInstancia Instancia,
            wAutenticacion Autenticacion,
            wsOperacionServicios.wsOperacionServicio OperacionServicio,
            int IDPago,
            String Comentario) {
        wGenInstanceFuntions wGen = new wGenInstanceFuntions();
        SubDataTable dt = new SubDataTable();
        SubDataTable dt_ = new SubDataTable();
        SubDataTable dtID = new SubDataTable();

        String T_BSCS_PAYMENT_API_ACCOUNT="",T_BSCSFU_PAYMENT_API_ACCOUNT="", vMessage_BSCSFU="", vCodOperador="", T_RESPUESTA_ANULACION="", vCodResultAnula="",vCorrTransacGroup="";

        String vCountryCode = wGen.getCountryCode(Instancia);
        String vQuery = "", vBaseQuery = "";
        OracleConnection _ODA = null;
        OracleConnection _BSCSFU = null;

        Boolean vAppliedFull = false;
        Boolean vError_BSCSFU = false, vBSCSFU_Applied = false;
        Boolean _connTo_bscsfu=false;
        Boolean vIsAdvance=false;

        wsR_sPagos vReturn = new wsR_sPagos();
        wsS_RegistroFormaPago FormaPago = new wsS_RegistroFormaPago();

        //Global INFO
        String T_CORR_TRANSAC_GROUP = "", T_CORR_TRANSAC_PAGO = String.valueOf( IDPago ) , T_CORR_ENC_TRANSAC = "", T_CORR_TRANSAC = ""; int vExiste=0;

        Boolean vPaymentSession = false;
        try {
            vPaymentSession = setPaymentSession(Instancia,Autenticacion);
            if(!vPaymentSession){ throw new Exception("Ocurrio un error en la apertura de sesion." + vLastErrMessage); }
            GlobalDB db = new GlobalDB();
            _ODA = db.getODADBConnection( wGen.getConnectTo(Instancia) );
            _ODA.setAutoCommit(false);
            try {
                Double vMontoTotalPago = 0.0;
                //Registra en Transacciones
                    vQuery = "select count(*) EXISTE, MAX(CORR_TRANSAC) ID, max(estatus_transac) estatus_transac from transacciones where corr_transac_pago='" + T_CORR_TRANSAC_PAGO +  "' and fec_real_pago=trunc(sysdate) ";
                    dt = db.getQuery(vQuery, _ODA);
                    if(dt.vData && dt.vRows>1) {
                        if(dt.Datos.rows.get(1).getColByName("ESTATUS_TRANSAC").value_string.equals("A")){
                            throw new Exception("El IDPago :" + T_CORR_TRANSAC_PAGO + ", ya se encuentra ANULADO.");
                        }
                        vExiste =dt.Datos.rows.get(1).getColByName("EXISTE").value_int;
                        T_CORR_TRANSAC =dt.Datos.rows.get(1).getColByName("ID").value_string;
                    }
                    if(vExiste<=0){ throw new Exception("El IDPago :" + T_CORR_TRANSAC_PAGO + ", no fue encontrado dentro de los registros del dia de hoy."); }

                    vQuery = "select * from TRANSACCIONES_REG_PAGO where corr_transac_pago='" + T_CORR_TRANSAC_PAGO +  "' ";
                    dt = db.getQuery(vQuery, _ODA);
                    if(dt.vData && dt.vRows>1) {
                        FormaPago.idFormaPago = dt.Datos.rows.get(1).getColByName("CORR_TRANSAC_PAGO").value_int;

                        switch(vCountryCode){
                            case "503": case "507":
                                FormaPago.MontoPago = dt.Datos.rows.get(1).getColByName("PAGO_DOLARES").value_double;
                                break;
                            default:
                                FormaPago.MontoPago = dt.Datos.rows.get(1).getColByName("PAGO_COLONES").value_double;
                        }
                        FormaPago.Moneda = wsMonedas.wsMoneda.LOCAL;
                        if(dt.Datos.rows.get(1).getColByName("TIP_PAG_TRAN").value_string.equals("E")){FormaPago.TipoFormaPago = wsTipoFormaPagos.wsTipoFormaPago.EFECTIVO;}
                        if(dt.Datos.rows.get(1).getColByName("TIP_PAG_TRAN").value_string.equals("C")){FormaPago.TipoFormaPago = wsTipoFormaPagos.wsTipoFormaPago.CHEQUE;}
                        if(dt.Datos.rows.get(1).getColByName("TIP_PAG_TRAN").value_string.equals("T")){FormaPago.TipoFormaPago = wsTipoFormaPagos.wsTipoFormaPago.TARJETA;}
                    }

                    if(vCountryCode.equals("507")){

                        vQuery = "select get_parametro('BSCS_USER_PAYMENT_API') parametro from dual";
                        dtID = db.getQuery(vQuery, _ODA);
                        if (dtID.vData && dtID.vRows > 0) { T_BSCS_PAYMENT_API_ACCOUNT = dtID.Datos.rows.get(1).cols.get(0).value_string; }
                        vQuery = "select get_parametro('BSCSFU_USER_PAYMENT_API') parametro from dual";
                        dtID = db.getQuery(vQuery, _ODA);
                        if (dtID.vData && dtID.vRows > 0) { T_BSCSFU_PAYMENT_API_ACCOUNT = dtID.Datos.rows.get(1).cols.get(0).value_string; }

                        //Anulaciones por Tipo de Operacion
                        vBaseQuery =    "select CORR_TRANSAC_GROUP, CORR_TRANSAC, CORR_ENC_TRANSAC, CORR_TRANSAC_PAGO, COD_AGENCIA, COD_AGENCIA_PROC, COD_CAJA, COD_CAJERO,                                                 " +
                                "FEC_REAL_PAGO, nvl(b.cod_operador_grupo, b.COD_OPERADOR) cod_operador, NUM_REFERENCIA, SUB_REFERENCIA, NUM_TELEFONO, NUM_CLIENTE, MONTO_TRANSAC, PAGO_DOLARES, FEC_TRANSACCION,    " +
                                "FEC_VEN_FACTURA, ESTATUS_TRANSAC, PROCE_TRANSAC, COD_CAJERO_ANULA, FEC_TRAN_ANULA, NPE, TIPO_MONEDA, TOTAL_PAGAR, ESTADO_PAGO,                                                     " +
                                "VALOR_CHEQUE, GENERA_TICKET, CORR_TICKET, PRINTED, ERROR, DESCRIPCION_ERROR, COMENTARIO, ID_OPERACION_PAGO, ID_OPERACION_ANULA,                                                    " +
                                "CLIENTE_CICLO, CORR_TICKET_ANULA, ID_AUTORIZA_ANULA, SEC_BANCO, SOAP_ID, SESION_CORRELATIVO, ID_ARCHIVOS_PAGO, ID_ARCHIVOS_PAGO_LINEA,                                             " +
                                "CORR_TRANSAC_VENTA                                                                                             " +
                                "from transacciones a                                                                                           " +
                                "inner join operadores b on b.cod_operador = a.cod_operador                                                     " +
                                "where corr_transac_pago='" + T_CORR_TRANSAC_PAGO + "' order by a.corr_transac desc";
                        dt = db.getQuery(vBaseQuery, _ODA);
                        if(dt.vData && dt.vRows>1){
                            for (int i = 1; i < dt.vRows; i++) {
                                String vCorrTransac = dt.Datos.rows.get(i).getColByName("CORR_TRANSAC").value_string;
                                vCorrTransacGroup=dt.Datos.rows.get(i).getColByName("CORR_TRANSAC_GROUP").value_string;
                                vCodOperador =dt.Datos.rows.get(i).getColByName("COD_OPERADOR").value_string;
                                switch ( vCodOperador ){
                                    case "218":
                                    case "219":
                                    case "220":
                                    case "318":
                                    case "319":
                                    case "230":
                                    case "231":
                                    case "232":
                                        if(_connTo_bscsfu!=true){
                                            _BSCSFU = db.getODADBConnection(ConnectTo.ODA_507_BSCSFU_db);
                                            _BSCSFU.setAutoCommit(false);
                                            _connTo_bscsfu=true;
                                        }
                                        vIsAdvance = ((dt.Datos.rows.get(i).getColByName("NUM_REFERENCIA").value_string.length() <= 0   ) ? true : false);
                                        if (vCodOperador.equals("126") || vCodOperador.equals("318")) { vIsAdvance = true; }

                                        vBaseQuery = "{CALL PAGOS_ODA.ppayment(?,?,?,?,?,?,?,?,?)}";
                                        CallableStatement _cmd_t_tran_bscsfu =  _BSCSFU.prepareCall(vBaseQuery);
                                        _cmd_t_tran_bscsfu.setString(1, ((vIsAdvance) ? null : dt.Datos.rows.get(i).getColByName("NUM_REFERENCIA").value_string ));
                                        _cmd_t_tran_bscsfu.setDouble(2,  Double_Round(dt.Datos.rows.get(i).getColByName("TOTAL_PAGAR").value_double));
                                        _cmd_t_tran_bscsfu.setString(3, T_BSCSFU_PAYMENT_API_ACCOUNT);
                                        _cmd_t_tran_bscsfu.setString(4, dt.Datos.rows.get(i).getColByName("CORR_TRANSAC").value_string);
                                        _cmd_t_tran_bscsfu.setString(5, "RV-CE2IN");
                                        _cmd_t_tran_bscsfu.setString(6, dt.Datos.rows.get(i).getColByName("NUM_CLIENTE").value_string);
                                        _cmd_t_tran_bscsfu.setString(7, dt.Datos.rows.get(i).getColByName("COD_AGENCIA").value_string);
                                        _cmd_t_tran_bscsfu.setString(8, DateToString(   new Date() , "ddMMyyyyHHmmss"));
                                        _cmd_t_tran_bscsfu.registerOutParameter(9, Types.VARCHAR, 1000);
                                        dt_ = db.setQuery(_cmd_t_tran_bscsfu);
                                        if (_cmd_t_tran_bscsfu.getString(9) == null) {_cmd_t_tran_bscsfu.setString(9, "");}
                                        if (!dt_.vData) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Anulacion>" + dt.vDBMessage); }
                                        T_RESPUESTA_ANULACION =_cmd_t_tran_bscsfu.getString(9);
                                        try {_cmd_t_tran_bscsfu.close(); } catch (Exception e1) {}
                                        vMessage_BSCSFU += "[BSCSFU:" + T_RESPUESTA_ANULACION + "]";
                                        if ( !T_RESPUESTA_ANULACION.equals("Succesfull")  ) {
                                            _BSCSFU.rollback();
                                            vError_BSCSFU = true;
                                            dt.vDBMessage = dt_.vMessage + dt_.vDBMessage +T_RESPUESTA_ANULACION ;
                                            throw new Exception("Ocurrio un error anulando en BSCS FU:. {Erro>" +dt.vDBMessage);
                                        } else { vBSCSFU_Applied = true; vCodResultAnula = vMessage_BSCSFU; }
                                        break;
                                }//End.Switch

                                //Registra en Transacciones
                                PreparedStatement _cmd_t_tran_=null;
                                vBaseQuery = "UPDATE TRANSACCIONES SET ESTATUS_TRANSAC='A', COD_CAJERO_ANULA=?, FEC_TRAN_ANULA=sysdate, COMENTARIO=?, ID_OPERACION_ANULA=? where CORR_TRANSAC_GROUP =? and CORR_TRANSAC=?";
                                _cmd_t_tran_ = _ODA.prepareStatement (vBaseQuery);
                                _cmd_t_tran_.setString(1, dt.Datos.rows.get(i).getColByName("COD_CAJERO").value_string);
                                _cmd_t_tran_.setString(2,Comentario);
                                _cmd_t_tran_.setString(3,vMessage_BSCSFU + vCodResultAnula);
                                _cmd_t_tran_.setString(4, dt.Datos.rows.get(i).getColByName("CORR_TRANSAC_GROUP").value_string);
                                _cmd_t_tran_.setString(5, dt.Datos.rows.get(i).getColByName("CORR_TRANSAC").value_string);
                                dt_ = db.setQuery(_cmd_t_tran_);
                                if(!dt_.vData) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Anulacion>" + dt.vDBMessage ); }
                                _cmd_t_tran_.close();
                            } //END FOR de Lista de Registros de Transacciones a Anular
                        }//end if dt.Rows>1
                    }//End for 507

                    if(!vCountryCode.equals("507")){
                        PreparedStatement _cmd_t_tran=null;
                        switch(vCountryCode){
                            case "502":
                                vBaseQuery = "UPDATE TRANSACCIONES set ESTATUS_TRANSAC='A', COD_CAJERO_ANULA=?,  COMENTARIO_ANULA=?, FEC_ANULA=SYSDATE where CORR_TRANSAC_PAGO=?" ;
                                _cmd_t_tran = _ODA.prepareStatement (vBaseQuery);
                                _cmd_t_tran.setString(1,gCodCajero);
                                _cmd_t_tran.setString(2,Comentario);
                                _cmd_t_tran.setString(3,T_CORR_TRANSAC_PAGO);
                                break;
                            case "503":
                                vBaseQuery = "UPDATE TRANSACCIONES set ESTATUS_TRANSAC='A', COD_CAJERO_ANULA=? where CORR_TRANSAC_PAGO=?" ;
                                _cmd_t_tran = _ODA.prepareStatement (vBaseQuery);
                                _cmd_t_tran.setString(1,gCodCajero);
                                _cmd_t_tran.setString(2,T_CORR_TRANSAC_PAGO);
                                break;
                            case "504": case "505": case "506":
                                vBaseQuery = "UPDATE TRANSACCIONES set ESTATUS_TRANSAC='A', COD_CAJERO_ANULA=?, COMENTARIO_ANULA=? where CORR_TRANSAC_PAGO=?" ;
                                _cmd_t_tran = _ODA.prepareStatement (vBaseQuery);
                                _cmd_t_tran.setString(1,gCodCajero);
                                _cmd_t_tran.setString(2,Comentario);
                                _cmd_t_tran.setString(3,T_CORR_TRANSAC_PAGO);
                                break;
                        }
                        dt_ = db.setQuery(_cmd_t_tran);
                        if(!dt_.vData) { dt.vDBMessage = dt_.vMessage + dt_.vDBMessage; throw new Exception("Error en Anulacion>" + dt.vDBMessage ); }
                        _cmd_t_tran.close();
                    }

                if(_connTo_bscsfu){
                    if(vError_BSCSFU == false && vBSCSFU_Applied) { _BSCSFU.commit(); }
                    _ODA.commit();
                }else{
                    _ODA.commit();
                }

                if(vCountryCode.equals("507") && vCorrTransacGroup.length()>2){
                    try {
                        //Procedimiento al finalizar el Pago
                        vBaseQuery = "PKG_TRANSACTION_PROCESS.process_transaction_end";
                        PreparedStatement _cmd_t_tran_oda_final = _ODA.prepareStatement (vBaseQuery);
                        _cmd_t_tran_oda_final.setString(1, vCorrTransacGroup);
                        dt_ = db.setQuery( _cmd_t_tran_oda_final);
                        try{                        _ODA.commit();                    }catch(Exception eCC){}
                    } catch (Exception e) {  }
                }





                vAppliedFull = true;
                try{  _ODA.close();     }catch(Exception eO1){}
                try{   _BSCSFU.close();     }catch(Exception eO3){}


                vReturn.vID = Integer.parseInt(T_CORR_TRANSAC);
                vReturn.vIDPago = Integer.parseInt(T_CORR_TRANSAC_PAGO);
                vReturn.vMensaje="ok";
                vReturn.vMensaje="OK";
                vReturn.vEstado=1;
                FormaPago.idFormaPago = Integer.parseInt(T_CORR_TRANSAC_PAGO);
                vReturn.FormaPago = FormaPago;
                wsR_sPagosInfoFacturador vEstado=new wsR_sPagosInfoFacturador();
                vEstado.Facturador= wsFacturadores.wsFacturador.ODA;vEstado.vEstado=1;
                vReturn.vEstadoFacturadores.Datos.add( vEstado );
            } catch(Exception eCC) {
                setInfo(eCC.getMessage() ); eCC.printStackTrace();

                try {_ODA.rollback();} catch (Exception eOR) {}
                try {_ODA.close();} catch (Exception eO1) {}
                try{  _BSCSFU.rollback();     }catch(Exception eO2){}
                try{  _BSCSFU.close();     }catch(Exception eO2){}


                vReturn.vMensaje = eCC.getMessage();
                vReturn.vEstado = -1;
                wsR_sPagosInfoFacturador vEstado = new wsR_sPagosInfoFacturador();
                vEstado.Facturador = wsFacturadores.wsFacturador.ODA;
                vEstado.vEstado = -1;
                vEstado.vMensaje = eCC.getMessage();
                vReturn.vEstadoFacturadores.Datos.add(vEstado);
            }
        } catch(Exception ePP) {
            setInfo("Error:PP>>");
            setInfo(ePP.getMessage() ); ePP.printStackTrace();

            vReturn.vMensaje=ePP.getMessage();
            vReturn.vEstado=-1;
            wsR_sPagosInfoFacturador vEstado=new wsR_sPagosInfoFacturador();
            vEstado.Facturador= wsFacturadores.wsFacturador.ODA;vEstado.vEstado=-1; vEstado.vMensaje = ePP.getMessage();
            vReturn.vEstadoFacturadores.Datos.add( vEstado );
        }finally {
            if(_ODA!=null){
                try{if( _ODA.isClosed()==false ){_ODA.close();}}catch(Exception exC){}
            }
        }

        return vReturn;
    }





    private String getStr( String value ){ return (value==null)?"":value;}
    private double Double_Round(double value) {
        int places=2;
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private String getCodTipoTran( String vCountryCode, wsOperacionServicios.wsOperacionServicio OperacionServicio ){
        String vCodTran="";
        if( OperacionServicio == wsOperacionServicios.wsOperacionServicio.MOVIL ){
            if( vCountryCode.equals("503") ){ vCodTran="00010"; }
            if( vCountryCode.equals("505") || vCountryCode.equals("502") || vCountryCode.equals("504") || vCountryCode.equals("506")  ){ vCodTran="00002"; }
        }
        if( OperacionServicio == wsOperacionServicios.wsOperacionServicio.FIJO ){
            if( vCountryCode.equals("503") ){ vCodTran="00002"; }
            if( vCountryCode.equals("505") || vCountryCode.equals("502") || vCountryCode.equals("504") || vCountryCode.equals("506")  ){ vCodTran="00014"; }
        }
        return vCodTran;
    }

    public String DateToString(Date vDateValue, String vDateFormat ){
        String vResult="";
        try{
            DateFormat formatter ;
            formatter = new SimpleDateFormat(vDateFormat);
            vResult  = formatter.format(vDateValue);
        }catch(Exception e){}
        return vResult;
    }


}
