package WsServiceActions;

import FromNET.SubDataTable;
import FromNET.g.g_info_payment;
import FromNET.g.global_apply_payments_forms;
import FromNET.wsOperations;
import ServiceActionsDB.dbAUTH;
import ServiceActionsDB.dbOperations;
import db.BaseClass;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.jws.WebMethod;
import javax.jws.WebParam;


public class wsActions
        extends BaseClass {
    private static wsOperations wsOper = new wsOperations();
    private String errLog = "";


    @WebMethod(operationName = "Authenticate")
    public wsGeneralResponse Authenticate(@WebParam(name = "Info") dbAUTH Info) {
        wsGeneralResponse vResponse = new wsGeneralResponse();
        try {
            vResponse = wsOper.Authenticate(Info);
        } catch (Exception e) {
            vResponse.vMessage = e.getMessage() + dbOperations.getErrMsg();
        }
        return vResponse;
    }


    @WebMethod(operationName = "GetLogOperationsData")
    public String GetLogOperationsData() {
        String vLogData = "";

        try {
            FileInputStream fstream = new FileInputStream("./LOG/wsActions.out");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                vLogData = vLogData + strLine;
            }
            fstream.close();
        } catch (Exception e) {
            setErrMsg(e);
        }
        return vLogData;
    }


    @WebMethod(operationName = "GetFindClienteInfo")
    public SubDataTable GetFindClienteInfo(@WebParam(name = "Autenticacion") dbAUTH InfoAuthenticate, @WebParam(name = "vcod_operador") String vcod_operador, @WebParam(name = "vNombre") String vNombre, @WebParam(name = "vTelefono") String vTelefono, @WebParam(name = "vReferencia") String vReferencia, @WebParam(name = "vCedula") String vCedula) {
        return wsOper.GetFindClienteInfo(InfoAuthenticate, vcod_operador, vNombre, vTelefono, vReferencia, vCedula);
    }


    @WebMethod(operationName = "GetFindCliente")
    public SubDataTable GetFindCliente(@WebParam(name = "Autenticacion") dbAUTH InfoAuthenticate, @WebParam(name = "vcod_operador") String vcod_operador, @WebParam(name = "vcliente") String vcliente, @WebParam(name = "vtelefono") String vtelefono, @WebParam(name = "vbarra") String vbarra) {
        return wsOper.GetFindCliente(InfoAuthenticate, vcod_operador, vcliente, vtelefono, vbarra);
    }


    @WebMethod(operationName = "GetFindFactura")
    public SubDataTable GetFindFactura(@WebParam(name = "Autenticacion") dbAUTH InfoAuthenticate, @WebParam(name = "vcod_operador") String vcod_operador, @WebParam(name = "vcliente") String vcliente, @WebParam(name = "vtelefono") String vtelefono, @WebParam(name = "vbarra") String vbarra) {
        return wsOper.GetFindFactura(InfoAuthenticate, vcod_operador, vcliente, vtelefono, vbarra);
    }


    @WebMethod(operationName = "Payments")
    public SubDataTable Payments(@WebParam(name = "Autenticacion") dbAUTH InfoAuthenticate, @WebParam(name = "payments_forms") global_apply_payments_forms[] payments_forms, @WebParam(name = "payments") g_info_payment[] payments) {
        return wsOper.SetPayments(InfoAuthenticate, payments_forms, payments);
    }


    @WebMethod(operationName = "CancelPayments")
    public SubDataTable CancelPayments(@WebParam(name = "Autenticacion") dbAUTH InfoAuthenticate, @WebParam(name = "f_corr_transac_group") String f_corr_transac_group, @WebParam(name = "txt_sup_user") String txt_sup_user, @WebParam(name = "txt_sup_password") String txt_sup_password, @WebParam(name = "txt_comentario") String txt_comentario) {
        return wsOper.SetCancelPayments(InfoAuthenticate, f_corr_transac_group, txt_sup_user, txt_sup_password, txt_comentario);
    }
}
