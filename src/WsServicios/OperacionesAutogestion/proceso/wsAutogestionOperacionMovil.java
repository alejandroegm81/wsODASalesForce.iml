package WsServicios.OperacionesAutogestion.proceso;

import FromNET.ConnectTo;
import FromNET.GlobalDB;
import FromNET.SubDataTable;
import WsServicios.Bases.wGenInstanceFuntions;
import WsServicios.Bases.wsInstancias;
import WsServicios.Operaciones.Base.*;
import db.BaseClass;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.jws.WebParam;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.sql.*;

public class wsAutogestionOperacionMovil extends BaseClass  {

    String getXmlQuery(wsInstancias.wsInstancia Instancia , wsAutogestionTipoConsultas.wsAutogestionTipoConsulta vTipoConsulta  ){
        String vQuery = "", vSegmento="";

        try{
            String vConfFile = ".xml";
            if (  vTipoConsulta.equals(wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.HISTORICO_FACTURA)  ||
                  vTipoConsulta.equals(wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.CONSULTA_SALDO)   ||
                  vTipoConsulta.equals(wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.VALIDACION_SERVICIO)){
                    vConfFile = "HISTORICO_MOVIL.xml";
            }
            if (  vTipoConsulta.equals(wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.HISTORIAL_PAGO)  ){
                vConfFile = "HISTORICO_MOVIL_PAGOS.xml";
            }
            if (  vTipoConsulta.equals(wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.INFO_CLIENTE)  ){
                vConfFile = "INFORMACION_MOVIL.xml";
            }
            if (  vTipoConsulta.equals(wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.INFO_CLIENTE_ADDON)  ){
                vConfFile = "INFORMACION_MOVIL.xml";
                vSegmento="query_hist_factura_addon_" + (getCountryCode(Instancia).equals("507")?"507":"50_");
            }
            if (  vTipoConsulta.equals(wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.INFO_CLIENTE_VELOCIDAD)  ){
                vConfFile = "INFORMACION_MOVIL.xml";
                vSegmento="query_hist_factura_velocidad_" + (getCountryCode(Instancia).equals("507")?"507":"50_");
            }

            InputStream is = getClass().getResourceAsStream(vConfFile);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName( (vSegmento.length()>1)?vSegmento:"query_hist_factura_" + getCountryCode(Instancia));
            for (int i = 0; i < nodeList.getLength(); i++) {
                vQuery = nodeList.item(i).getFirstChild().getNodeValue() ;
            }
        }catch(Exception E){}
        return vQuery;
    }
    //query_hist_factura_addon_50_


    public wsR_HistoricoFacturas  HistoricoFacturas(wsInstancias.wsInstancia Instancia,
                                  wAutenticacion Autenticacion,
                                  wsOperacionServicios.wsOperacionServicio OperacionServicio,
                                  wsAutogestionTipoConsultas.wsAutogestionTipoConsulta vTipoConsulta,
                                  String Cliente_Contrato,
                                  String Telefono) throws Exception {
        wsR_HistoricoFacturas vReturn = new wsR_HistoricoFacturas();
        String vQuery="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="", vCountryCode=getCountryCode(Instancia);
        SubDataTable dt=new SubDataTable();
        GlobalDB db = new GlobalDB();

        if( !OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL ) ){ throw new Exception("Operacion no es Busqueda Movil"); }


        vQuery = getXmlQuery(Instancia,vTipoConsulta);if(vQuery==""){ throw new Exception("No se encontro archivo de configuracion"); }

        if(vCountryCode.equals("507")){
            if( Telefono.length()>0 ){ vQueryParameter_a="g.dn_num='" + Telefono + "'"; }
            if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="a.customer_id='" +Cliente_Contrato + "'"; }
        }else{
            if( Telefono.length()>0 ){ vQueryParameter_a="g.dn_num='" + getCountryCode(Instancia)+ Telefono + "'"; }
            if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="a.customer_id='" +Cliente_Contrato + "'"; }
        }

        vQuery = vQuery.replace("{}", vQueryParameter_a + ((vQueryParameter_a.length()>0 && vQueryParameter_b.length()>0)?" and ":"")  +vQueryParameter_b +  ((vQueryParameter_b.length()>0 && vQueryParameter_c.length()>0)?" and ":"") +  vQueryParameter_c );

        if(vCountryCode.equals("507")){
            dt = db.getQuery(vQuery, ConnectTo.ODA_507_BSCS_db);
            if(dt.vData==false){
                dt = db.getQuery(vQuery, ConnectTo.ODA_507_BSCSFU_db);
            }
        }else{
            dt = db.getQuery(vQuery, getConnectTo(Instancia));
        }


        if(dt.vData && dt.vRows>1){
            int vC =0;
            for (int i = 1; i < dt.vRows; i++) {
                wsR_HistoricoFacturas_Item vItem=new wsR_HistoricoFacturas_Item();
                vItem.RazonSocial           =	dt.Datos.rows.get(i).getColByName("RAZON_SOCIAL").value_string;
                vItem.SaldoRestante         =	dt.Datos.rows.get(i).getColByName("SALDO").value_double;
                vItem.MontoFacturado        =   dt.Datos.rows.get(i).getColByName("MONTOFACTURADO").value_double;
                vItem.yFacturacion          =   dt.Datos.rows.get(i).getColByName("YFACTURACION").value_string;
                vItem.FechaVencimiento      =   dt.Datos.rows.get(i).getColByName("FECHAVENCIMIENTO").value_string;
                vItem.FechaEmision          =   dt.Datos.rows.get(i).getColByName("FECHAEMISION").value_string;
                vItem.mFacturacion          =   dt.Datos.rows.get(i).getColByName("MFACTURACION").value_string;
                vItem.NumeroFacturaOnBase   =   dt.Datos.rows.get(i).getColByName("FACTONBASE").value_string;
                vItem.NumeroFacturaPago     =   dt.Datos.rows.get(i).getColByName("FACTPAGO").value_string;
                vItem.NumeroServicio        =   dt.Datos.rows.get(i).getColByName("TELEFONO").value_string;
                vReturn.Items.add( vItem );
                vC++;
                if(vC>=12){break;}
            }
            vReturn.vEstado=1;
        }else{
            vReturn.vMensaje="Sin datos";
            vReturn.vEstado=0;
        }
        return vReturn;
    }




    public wsR_ConsultaSaldos  ConsultaSaldos(wsInstancias.wsInstancia Instancia,
                                                    wAutenticacion Autenticacion,
                                                    wsOperacionServicios.wsOperacionServicio OperacionServicio,
                                                    wsAutogestionTipoConsultas.wsAutogestionTipoConsulta vTipoConsulta,
                                                    String Cliente_Contrato,
                                                    String Telefono) throws Exception {
        wsR_ConsultaSaldos vReturn = new wsR_ConsultaSaldos();
        String vQuery="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="", vCountryCode=getCountryCode(Instancia);
        SubDataTable dt=new SubDataTable();
        GlobalDB db = new GlobalDB();

        if( !OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL ) ){ throw new Exception("Operacion no es Busqueda Movil"); }


        vQuery = getXmlQuery(Instancia,vTipoConsulta);if(vQuery==""){ throw new Exception("No se encontro archivo de configuracion"); }

        if(vCountryCode.equals("507")){
            if( Telefono.length()>0 ){ vQueryParameter_a="g.dn_num='" + Telefono + "'"; }
            if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="a.customer_id='" +Cliente_Contrato + "'"; }
            vQueryParameter_c = " and ohopnamt_doc > 0 ";
        }else{
            if( Telefono.length()>0 ){ vQueryParameter_a="g.dn_num='" + getCountryCode(Instancia)+ Telefono + "'"; }
            if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="a.customer_id='" +Cliente_Contrato + "'"; }
            vQueryParameter_c = " and ohopnamt_doc > 0 ";
        }

        vQuery = vQuery.replace("{}", vQueryParameter_a + ((vQueryParameter_a.length()>0 && vQueryParameter_b.length()>0)?" and ":"")  +vQueryParameter_b +  ((vQueryParameter_b.length()>0 && vQueryParameter_c.length()>0)?" and ":"") +  vQueryParameter_c );

        if(vCountryCode.equals("507")){
            dt = db.getQuery(vQuery, ConnectTo.ODA_507_BSCS_db);
            if(dt.vData==false){
                dt = db.getQuery(vQuery, ConnectTo.ODA_507_BSCSFU_db);
            }
        }else{
            dt = db.getQuery(vQuery, getConnectTo(Instancia));
        }


        if(dt.vData && dt.vRows>1){
            String v0,v1;
            Boolean vCFound=false;

            for (int i = 1; i < dt.vRows; i++) {
                wsR_ConsultaSaldos_Item vItem=new wsR_ConsultaSaldos_Item();
                vItem.NumeroServicio        =   dt.Datos.rows.get(i).getColByName("TELEFONO").value_string;
                vItem.RazonSocial           =	dt.Datos.rows.get(i).getColByName("RAZON_SOCIAL").value_string;
                vItem.NumeroFactura         =   dt.Datos.rows.get(i).getColByName("FACTONBASE").value_string;
                vItem.FechaVencimiento      =   dt.Datos.rows.get(i).getColByName("FECHAVENCIMIENTO").value_string;
                vItem.SaldoPendiente         =	dt.Datos.rows.get(i).getColByName("SALDO").value_double;
                //vItem.MontoFacturado        =   dt.Datos.rows.get(i).getColByName("MONTOFACTURADO").value_double;
                //vItem.yFacturacion          =   dt.Datos.rows.get(i).getColByName("YFACTURACION").value_string;
                //vItem.FechaEmision          =   dt.Datos.rows.get(i).getColByName("FECHAEMISION").value_string;
                //vItem.mFacturacion          =   dt.Datos.rows.get(i).getColByName("MFACTURACION").value_string;
                //vItem.NumeroFacturaPago     =   dt.Datos.rows.get(i).getColByName("FACTPAGO").value_string;
                vReturn.Items.add( vItem );
            }
            vReturn.vEstado=1;
        }else{
            vReturn.vMensaje="Sin datos";
            vReturn.vEstado=0;
        }
        return vReturn;
    }



    public wsR_ValidacionServicios  ValidacionServicios(wsInstancias.wsInstancia Instancia,
                                              wAutenticacion Autenticacion,
                                              wsOperacionServicios.wsOperacionServicio OperacionServicio,
                                              wsAutogestionTipoConsultas.wsAutogestionTipoConsulta vTipoConsulta,
                                              String Cliente_Contrato,
                                              String Telefono,
                                              String Identificacion) throws Exception {
        wsR_ValidacionServicios vReturn = new wsR_ValidacionServicios();
        String vQuery="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="", vCountryCode=getCountryCode(Instancia);
        SubDataTable dt=new SubDataTable();
        GlobalDB db = new GlobalDB();

        if( !OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL ) ){ throw new Exception("Operacion no es Busqueda Movil"); }


        vQuery = getXmlQuery(Instancia,vTipoConsulta);if(vQuery==""){ throw new Exception("No se encontro archivo de configuracion"); }

        if(vCountryCode.equals("507")){
            if( Telefono.length()>0 ){ vQueryParameter_a="g.dn_num='" + Telefono + "'"; }
            if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="a.customer_id='" +Cliente_Contrato + "'"; }
            //if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="a.customer_id='" +Cliente_Contrato + "'"; }
            vQueryParameter_c = " and ohopnamt_doc > 0 ";
        }else{
            if( Telefono.length()>0 ){ vQueryParameter_a="g.dn_num='" + getCountryCode(Instancia)+ Telefono + "'"; }
            if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="a.customer_id='" +Cliente_Contrato + "'"; }
            if( Identificacion.length()>0 ){ vQueryParameter_c="a.passportno ='" +Identificacion + "'"; }
        }

        vQuery = vQuery.replace("{}", vQueryParameter_a + ((vQueryParameter_a.length()>0 && vQueryParameter_b.length()>0)?" and ":"")  +vQueryParameter_b +  ((vQueryParameter_b.length()>0 && vQueryParameter_c.length()>0)?" and ":"") +  vQueryParameter_c );

        if(vCountryCode.equals("507")){
            dt = db.getQuery(vQuery, ConnectTo.ODA_507_BSCS_db);
            if(dt.vData==false){
                dt = db.getQuery(vQuery, ConnectTo.ODA_507_BSCSFU_db);
            }
        }else{
            dt = db.getQuery(vQuery, getConnectTo(Instancia));
        }


        if(dt.vData && dt.vRows>1){
            String v0,v1;
            Boolean vCFound=false;

            //Facturas
            try{
                int vCFactura=0;

                for (int i = 1; i < dt.vRows; i++) {
                    String vFactura = dt.Datos.rows.get(i).getColByName("FACTONBASE").value_string;
                    //wsR_ValidacionServicios_Factura vItem=new wsR_ValidacionServicios_Factura();
                    //vItem.NumeroFactura         =   dt.Datos.rows.get(i).getColByName("FACTONBASE").value_string;
                    //vItem.FechaVencimiento      =   dt.Datos.rows.get(i).getColByName("FECHAVENCIMIENTO").value_string;
                    //vItem.SaldoPendiente         =	dt.Datos.rows.get(i).getColByName("SALDO").value_double;
                    //vItem.MontoFacturado        =   dt.Datos.rows.get(i).getColByName("MONTOFACTURADO").value_double;
                    //vItem.yFacturacion          =   dt.Datos.rows.get(i).getColByName("YFACTURACION").value_string;
                    //vItem.FechaEmision          =   dt.Datos.rows.get(i).getColByName("FECHAEMISION").value_string;
                    //vItem.mFacturacion          =   dt.Datos.rows.get(i).getColByName("MFACTURACION").value_string;
                    //vItem.NumeroFacturaPago     =   dt.Datos.rows.get(i).getColByName("FACTPAGO").value_string;
                    if( !vReturn.Facturas.contains( vFactura ) ){
                        vReturn.Facturas.add( vFactura );  vCFactura++;
                    }
                    if(vCFactura>=3){ break; }
                }
            }catch(Exception e){}

            //Telefonos
            try{
                int vCTelefonos=0;

                for (int i = 1; i < dt.vRows; i++) {
                    //wsR_ValidacionServicios_Telefono vItemTel=new wsR_ValidacionServicios_Telefono();
                    //vItemTel.NumeroServicio       =   dt.Datos.rows.get(i).getColByName("TELEFONO").value_string;
                    //vItem.FechaVencimiento      =   dt.Datos.rows.get(i).getColByName("FECHAVENCIMIENTO").value_string;
                    //vItem.SaldoPendiente        =	dt.Datos.rows.get(i).getColByName("SALDO").value_double;
                    //vItem.MontoFacturado        =   dt.Datos.rows.get(i).getColByName("MONTOFACTURADO").value_double;
                    //vItem.yFacturacion          =   dt.Datos.rows.get(i).getColByName("YFACTURACION").value_string;
                    //vItem.FechaEmision          =   dt.Datos.rows.get(i).getColByName("FECHAEMISION").value_string;
                    //vItem.mFacturacion          =   dt.Datos.rows.get(i).getColByName("MFACTURACION").value_string;
                    //vItem.NumeroFacturaPago     =   dt.Datos.rows.get(i).getColByName("FACTPAGO").value_string;
                    String vServicio = dt.Datos.rows.get(i).getColByName("TELEFONO").value_string;
                    if( !vReturn.NumerosServicios.contains( vServicio ) ){
                        vReturn.NumerosServicios.add(vServicio  );  vCTelefonos++;
                    }
                    //if(vCTelefonos>=3){ break; }
                }
            }catch(Exception e){}


            vReturn.vEstado=1;
        }else{
            vReturn.vMensaje="Sin datos";
            vReturn.vEstado=0;
        }
        return vReturn;
    }





    public wsR_HistorialPagos  HistorialPagos(wsInstancias.wsInstancia Instancia,
                                                        wAutenticacion Autenticacion,
                                                        wsOperacionServicios.wsOperacionServicio OperacionServicio,
                                                        wsAutogestionTipoConsultas.wsAutogestionTipoConsulta vTipoConsulta,
                                                        String Cliente_Contrato,
                                                        String Telefono) throws Exception {
        wsR_HistorialPagos vReturn = new wsR_HistorialPagos();
        String vQuery="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="", vCountryCode=getCountryCode(Instancia);
        SubDataTable dt=new SubDataTable();
        GlobalDB db = new GlobalDB();

        if( !OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL ) ){ throw new Exception("Operacion no es Busqueda Movil"); }


        vQuery = getXmlQuery(Instancia,vTipoConsulta);if(vQuery==""){ throw new Exception("No se encontro archivo de configuracion"); }

        if(vCountryCode.equals("507")){
            if( Telefono.length()>0 ){ vQueryParameter_a="g.dn_num='" + Telefono + "'"; }
            if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="a.customer_id='" +Cliente_Contrato + "'"; }
        }else{
            if( Telefono.length()>0 ){ vQueryParameter_a="g.dn_num='" + getCountryCode(Instancia)+ Telefono + "'"; }
            if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="a.customer_id='" +Cliente_Contrato + "'"; }
        }

        vQuery = vQuery.replace("{}", vQueryParameter_a + ((vQueryParameter_a.length()>0 && vQueryParameter_b.length()>0)?" and ":"")  +vQueryParameter_b +  ((vQueryParameter_b.length()>0 && vQueryParameter_c.length()>0)?" and ":"") +  vQueryParameter_c );

        if(vCountryCode.equals("507")){
            dt = db.getQuery(vQuery, ConnectTo.ODA_507_BSCS_db);
            if(dt.vData==false){
                dt = db.getQuery(vQuery, ConnectTo.ODA_507_BSCSFU_db);
            }
        }else{
            dt = db.getQuery(vQuery, getConnectTo(Instancia));
        }


        if(dt.vData && dt.vRows>1){
            int vC=0;
            Boolean vCFound=false;
            try{
                for (int i = 1; i < dt.vRows; i++) {
                    wsR_HistorialPagos_Item vItem=new wsR_HistorialPagos_Item();
                    vItem.MetodoPago                =   dt.Datos.rows.get(i).getColByName("USUARIO").value_string;
                    vItem.FechaPago                 =   dt.Datos.rows.get(i).getColByName("FECHAPAGO").value_string;
                    vItem.NumeroFactura             =	dt.Datos.rows.get(i).getColByName("FACTURA").value_string;
                    vItem.MontoCancelado            =   dt.Datos.rows.get(i).getColByName("PAGO").value_double;
                    vItem.FechaVencimiento          =   dt.Datos.rows.get(i).getColByName("FECHAVENCIMIENTO").value_string;
                    vItem.IdentificadorTransaccion  =   dt.Datos.rows.get(i).getColByName("CORRELATIVOPAGOODA").value_string;
                    vReturn.Items.add(vItem);
                    vC ++;
                    if(vC>=12){break;}
                }
            }catch(Exception e){}
            vReturn.vEstado=1;
        }else{
            vReturn.vMensaje="Sin datos";
            vReturn.vEstado=0;
        }
        return vReturn;
    }


    public wsR_InformacionClientes  InformacionClientes(wsInstancias.wsInstancia Instancia,
                                              wAutenticacion Autenticacion,
                                              wsOperacionServicios.wsOperacionServicio OperacionServicio,
                                              wsAutogestionTipoConsultas.wsAutogestionTipoConsulta vTipoConsulta,
                                              String Cliente_Contrato,
                                              String Telefono) throws Exception {
        wsR_InformacionClientes vReturn = new wsR_InformacionClientes();
        String vQuery="", vQueryAddOn="", vQueryVelocidad="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="", vCountryCode=getCountryCode(Instancia);
        SubDataTable dt=new SubDataTable();
        SubDataTable dtAddOn=new SubDataTable();
        SubDataTable dtVelocidad=new SubDataTable();
        GlobalDB db = new GlobalDB();

        if( !OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL ) ){ throw new Exception("Operacion no es Busqueda Movil"); }


        vQuery = getXmlQuery(Instancia,vTipoConsulta);if(vQuery==""){ throw new Exception("No se encontro archivo de configuracion"); }
        vQueryAddOn = getXmlQuery(Instancia, wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.INFO_CLIENTE_ADDON);
        vQueryVelocidad = getXmlQuery(Instancia, wsAutogestionTipoConsultas.wsAutogestionTipoConsulta.INFO_CLIENTE_VELOCIDAD);

        if(vCountryCode.equals("507")){
            if( Telefono.length()>0 ){ vQueryParameter_a="g.dn_num='" + Telefono + "'"; }
            if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="a.customer_id='" +Cliente_Contrato + "'"; }
        }else{
            if( Telefono.length()>0 ){ vQueryParameter_a="c.dn_num='" + getCountryCode(Instancia)+ Telefono + "'"; }
            if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="b.customer_id='" +Cliente_Contrato + "'"; }
        }

        vQuery = vQuery.replace("{}", vQueryParameter_a + ((vQueryParameter_a.length()>0 && vQueryParameter_b.length()>0)?" and ":"")  +vQueryParameter_b +  ((vQueryParameter_b.length()>0 && vQueryParameter_c.length()>0)?" and ":"") +  vQueryParameter_c );
        vQueryAddOn = vQueryAddOn.replace("{}", vQueryParameter_a + ((vQueryParameter_a.length()>0 && vQueryParameter_b.length()>0)?" and ":"")  +vQueryParameter_b +  ((vQueryParameter_b.length()>0 && vQueryParameter_c.length()>0)?" and ":"") +  vQueryParameter_c );
        vQueryAddOn = vQueryAddOn.replace("c.","dn.").replace("b.","CA.");

        vQueryVelocidad = vQueryVelocidad.replace("{}", vQueryParameter_a + ((vQueryParameter_a.length()>0 && vQueryParameter_b.length()>0)?" and ":"")  +vQueryParameter_b +  ((vQueryParameter_b.length()>0 && vQueryParameter_c.length()>0)?" and ":"") +  vQueryParameter_c );
        vQueryVelocidad = vQueryVelocidad.replace("b.","cua.");


        if(vCountryCode.equals("507")){
            dt = db.getQuery(vQuery, ConnectTo.ODA_507_BSCS_db);
            if(dt.vData==false){
                dt = db.getQuery(vQuery, ConnectTo.ODA_507_BSCSFU_db);
                dtAddOn = db.getQuery(vQueryAddOn, getConnectTo(Instancia));
                dtVelocidad = db.getQuery(vQueryVelocidad, getConnectTo(Instancia));
            }
        }else{
            dt = db.getQuery(vQuery, getConnectTo(Instancia));
            dtAddOn = db.getQuery(vQueryAddOn, getConnectTo(Instancia));
            dtVelocidad = db.getQuery(vQueryVelocidad, getConnectTo(Instancia));
        }


        if(dt.vData && dt.vRows>1){
            String v0,v1;
            Boolean vCFound=false;
            try{
                for (int i = 1; i < dt.vRows; i++) {
                    wsR_InformacionClientes_Item vItem=new wsR_InformacionClientes_Item();
                    vItem.RazonSocial=   dt.Datos.rows.get(i).getColByName("NOMBRE_CLIENTE").value_string;
                    vItem.Identificacion=   dt.Datos.rows.get(i).getColByName("IDENTIFICACION").value_string;
                    vItem.NombrePlan=   dt.Datos.rows.get(i).getColByName("PLAN").value_string;
                    vItem.FechaVencimiento=   dt.Datos.rows.get(i).getColByName("FECHA_VENCIMIENTO").value_string;
                    vItem.FechaActivacion=   dt.Datos.rows.get(i).getColByName("FECHA_CONTRATO").value_string;
                    vItem.FechaRenovacionPlan=   dt.Datos.rows.get(i).getColByName("FECHA_ACTIVACION_CLIENTE").value_string;
                    vItem.EstadoPlan=   dt.Datos.rows.get(i).getColByName("ESTATUS_COID").value_string;
                    vItem.PrecioPlan=   dt.Datos.rows.get(i).getColByName("PRECIO_PLAN").value_double;
                    vItem.CodigoPlan=   dt.Datos.rows.get(i).getColByName("CODIGOPLAN").value_string;
                    vItem.NumeroContrato=   dt.Datos.rows.get(i).getColByName("CUSTCODE").value_string;
                    vItem.Ciclo=   dt.Datos.rows.get(i).getColByName("CICLO").value_string;

                    if(dtVelocidad.vData && dtVelocidad.vRows>1){
                        for(int k= 1; k < dtVelocidad.vRows; k++){
                            if( dt.Datos.rows.get(i).getColByName("CODIGO_CONTRATO").value_string.equals( dtVelocidad.Datos.rows.get(k).getColByName("CONTRACT_ID").value_string )  ){
                                vItem.VelocidadNavegacion = dtVelocidad.Datos.rows.get(k).getColByName("VELOCIDAD").value_string;
                            }
                        }
                    }

                    if(dtAddOn.vData && dtAddOn.vRows>1){
                        for(int j= 1; j < dtAddOn.vRows; j++){
                            if( dt.Datos.rows.get(i).getColByName("CODIGO_CLIENTE").value_string.equals( dtAddOn.Datos.rows.get(j).getColByName("CODIGO_CLIENTE").value_string )  ){
                                wsR_InformacionClietnes_Item_AddOn vItemAddon = new wsR_InformacionClietnes_Item_AddOn(  );
                                vItemAddon.AddOnNombre= dtAddOn.Datos.rows.get(j).getColByName("NOMBRE").value_string;
                                vItemAddon.AddOnNombreAlterno= dtAddOn.Datos.rows.get(j).getColByName("NOMBREALTERNO").value_string;
                                vItemAddon.AddOnPrecio= dtAddOn.Datos.rows.get(j).getColByName("PRECIO").value_double;
                                vItemAddon.AddOnEstado= dtAddOn.Datos.rows.get(j).getColByName("ESTADO").value_string;
                                vItemAddon.AddOnFechaActivacion= dtAddOn.Datos.rows.get(j).getColByName("FECHAACTIVACION").value_string;
                                vItemAddon.AddOnFechaRenovacion= dtAddOn.Datos.rows.get(j).getColByName("FECHARENOVACION").value_string;
                                vItemAddon.AddOnNumeroServicioAsociado= dtAddOn.Datos.rows.get(j).getColByName("NUMEROSERVICIOASOCIADO").value_string;
                                vItem.Addons.add( vItemAddon );
                            }
                        }
                    }

                    //vItem.VelocidadNavegacion=   dt.Datos.rows.get(i).getColByName("").value_string;
                    //vItem.AddOnNombre=   dt.Datos.rows.get(i).getColByName("USUARIO").value_string;
                    //vItem.AddOnNombreAlterno=   dt.Datos.rows.get(i).getColByName("USUARIO").value_string;
                    //vItem.AddOnPrecio=   dt.Datos.rows.get(i).getColByName("USUARIO").value_string;
                    //vItem.AddOnEstado=   dt.Datos.rows.get(i).getColByName("USUARIO").value_string;
                    //vItem.AddOnFechaActivacion=   dt.Datos.rows.get(i).getColByName("USUARIO").value_string;
                    //vItem.AddOnNumeroServicioAsociado=   dt.Datos.rows.get(i).getColByName("USUARIO").value_string;

                    vReturn.Items.add(vItem);
                }
            }catch(Exception e){}
            vReturn.vEstado=1;
        }else{
            vReturn.vMensaje="Sin datos";
            vReturn.vEstado=0;
        }
        return vReturn;
    }



    ////////////////APLICACIONES DE TRANSACCIONES -> ACTUAL


    public wsR_AplicaVentaServicio AplicaVentaServicio(
                                                        wsInstancias.wsInstancia Instancia,
                                                        wAutenticacion Autenticacion,
                                                        wsOperacionServicios.wsOperacionServicio OperacionServicio,
                                                        String Telefono ,
                                                        Double Monto ,
                                                        String SecuenciaExterna ,
                                                        String CodProducto ) throws Exception {
        wsR_AplicaVentaServicio vReturn = new wsR_AplicaVentaServicio();
        String vR_Corr="",vR_Res="", vR_Men="", vQuery="";
        SubDataTable dt=new SubDataTable();
        GlobalDB db = new GlobalDB();
        OracleConnection _ODA = null;
        wGenInstanceFuntions wGen = new wGenInstanceFuntions();
        CallableStatement cl = null;

        if( !OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.REGISTRO_VENTA ) ){ throw new Exception("Operacion no es Registro de Venta"); }

        try{
            _ODA = db.getODADBConnection( wGen.getConnectTo(Instancia) );
            _ODA.setAutoCommit(true);
            /*
            PROCEDURE aplicar_pago_venta_servicios (
                v_telefono       IN       VARCHAR2,
                fecha_proc       IN       VARCHAR2,
                vcod_banco       IN       VARCHAR2,
                monto_pagar      IN       NUMBER,
                vsec_banco       IN       VARCHAR2,
                cod_producto_sku IN       varchar2,
                v_corr_transac   OUT       VARCHAR2,
                v_respuesta      OUT      VARCHAR2,
                MESSAGE          OUT      VARCHAR2
            */
            cl = _ODA.prepareCall("{call PKG_AUTOGESTION.aplicar_pago_venta_servicios( ?,?,?,?,?,?,   ?,?,? )}");
            cl.setString(1, Telefono);
            cl.setString(2, ""); //FechaProc No se utiliza
            cl.setString(3, Autenticacion.vIDBanco );
            cl.setDouble(4, Monto );
            cl.setString(5, SecuenciaExterna );
            cl.setString(6, CodProducto );
            cl.registerOutParameter(7, Types.VARCHAR  ) ;
            cl.registerOutParameter(8, Types.VARCHAR  ) ;
            cl.registerOutParameter(9, Types.VARCHAR  ) ;
            cl.execute();
            vR_Corr= cl.getString(7) ;
            vR_Res= cl.getString(8);
            vR_Men= cl.getString(9);
            vReturn.vEstado=0;vReturn.vMensaje="";
            if( vR_Corr==null ){vR_Corr="";}
            if(vR_Corr.length()>2){
                vQuery = "select num_referencia from transacciones where fec_real_pago=trunc(sysdate) and corr_transac='" +vR_Corr + "'";
                dt = db.getQuery( vQuery, getConnectTo(Instancia) );
                if(dt.vData && dt.vRows>1){
                    vReturn.Factura = dt.Datos.rows.get(1).getColByName("NUM_REFERENCIA").value_string;
                }
            }
        }catch(Exception eX){
            vReturn.vEstado=-1; vReturn.vMensaje = eX.getMessage();
            eX.printStackTrace();
        }
        finally {
            try {
                cl.close();
                _ODA.close();
            } catch (SQLException e) {}
        }



        if( vR_Res.equals("00") && vR_Corr.length()>2 ){
            vReturn.vEstado=1;  vReturn.Cod_Respuesta = vR_Res; vReturn.Transaccion = vR_Corr; vReturn.vMensaje = vR_Men;
        }else{
            vReturn.vEstado=-1; if(vR_Men.length()>1){vReturn.vMensaje = vR_Men;};
        }
        return vReturn;
    }

    public wsR_AplicaVentaServicio AplicaVentaServicioFactura(
            wsInstancias.wsInstancia Instancia,
            wAutenticacion Autenticacion,
            String vCliente_Nombre,
            String vCliente_Direccion,
            String vCliente_Cedula,
            String vCliente_Nit,
            String vCliente_CorreoElectronico,
            String Telefono ,
            Double Monto ,
            String SecuenciaExterna ,
            String CodProducto ,
            String vPagoForma ,
            String vPagoTarjeta ,
            String vPagoAutorizacion ,
            String vPagoVencimiento) throws Exception {
        wsR_AplicaVentaServicio vReturn = new wsR_AplicaVentaServicio();
        String vR_Corr="",vR_Res="", vR_Men="", vQuery="";
        SubDataTable dt=new SubDataTable();
        GlobalDB db = new GlobalDB();
        OracleConnection _ODA = null;
        wGenInstanceFuntions wGen = new wGenInstanceFuntions();
        CallableStatement cl = null;

        try{
            _ODA = db.getODADBConnection( wGen.getConnectTo(Instancia) );
            _ODA.setAutoCommit(true);
            /*
                PROCEDURE AplicaVentaServicioFactura (
                    vcod_banco  IN       VARCHAR2,
                    vsec_banco  IN       VARCHAR2,
                    vnombre     IN       VARCHAR2,
                    vdireccion  IN       VARCHAR2,
                    vcedula     IN       VARCHAR2,
                    vnit        IN       VARCHAR2,
                    vcorreoelectronico IN       VARCHAR2,
                    vtelefono   IN       VARCHAR2,
                    vcliente    IN       VARCHAR2,
                    vsku        IN       VARCHAR2,
                    vmonto      in       NUMBER,
                    vformapago  IN       VARCHAR2,
                    vtarjeta    IN       VARCHAR2,
                    vautorizacion   IN       VARCHAR2,
                    vfec_vence  IN       VARCHAR2,
                    vresult           out sys_refcursor
            */
            cl = _ODA.prepareCall("{call PKG_AUTOGESTION.AplicaVentaServicioFactura( ?,?,   ?,?,?,?,?,  ?,?,?,?,    ?,?,?,?,  ? )}");
            cl.setString(1, Autenticacion.vIDBanco );
            cl.setString(2, SecuenciaExterna );

            cl.setString(3, vCliente_Nombre );
            cl.setString(4, vCliente_Direccion );
            cl.setString(5, vCliente_Cedula );
            cl.setString(6, vCliente_Nit );
            cl.setString(7, vCliente_CorreoElectronico );

            cl.setString(8, Telefono);
            cl.setString(9, ""); //Cliente NoAplica
            cl.setString(10, CodProducto );
            cl.setDouble(11, Monto );

            cl.setString(12, vPagoForma);
            cl.setString(13, vPagoTarjeta);
            cl.setString(14, vPagoAutorizacion);
            cl.setString(15, vPagoVencimiento);

            cl.registerOutParameter(16, OracleTypes.CURSOR  ) ;
            cl.execute();

            ResultSet vRs = (ResultSet) cl.getObject(16);
            RowSetFactory rowSetFactory = RowSetProvider.newFactory();
            CachedRowSet crs = rowSetFactory.createCachedRowSet();
            crs.populate(vRs);
            dt.Assign(Boolean.valueOf(true), db.RsToRows(crs));

            if(dt.vData && dt.vRows>1){
                vReturn.Factura = dt.Datos.rows.get(1).getColByName("FACTURA").value_string;
                vReturn.Transaccion = dt.Datos.rows.get(1).getColByName("TRANSACCION").value_string;
                vReturn.Cod_Respuesta = dt.Datos.rows.get(1).getColByName("COD_RESPUESTA").value_string;
                vReturn.Detalle = dt.Datos.rows.get(1).getColByName("DETALLE").value_string;
                vReturn.vMensaje = dt.Datos.rows.get(1).getColByName("DETALLE").value_string;
                if(vReturn.Transaccion!=null){
                    if(vReturn.Transaccion.length()>2){vReturn.vEstado = 1;}
                }
                try{
                    if( vReturn.Transaccion==null ){vReturn.Transaccion="";}
                    if(vReturn.Transaccion.length()>2){
                        vQuery = "select num_referencia from transacciones where fec_real_pago=trunc(sysdate) and corr_transac='" +vReturn.Transaccion + "'";
                        dt = db.getQuery( vQuery, getConnectTo(Instancia) );
                        if(dt.vData && dt.vRows>1){
                            vReturn.Factura = dt.Datos.rows.get(1).getColByName("NUM_REFERENCIA").value_string;
                        }
                    }
                }catch (Exception eX){}
            }else{
                vReturn.vEstado = 0;
                vReturn.vMensaje = "Sin respuesta de Metodo. pkgAutogestion";
            }
        }catch(Exception eX){
            vReturn.vEstado=-1; vReturn.vMensaje = eX.getMessage();
            eX.printStackTrace();
        }
        finally {
            try {
                cl.close();
                _ODA.close();
            } catch (SQLException e) {}
        }

        return vReturn;
    }






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

    private String getStr( Object val ){
        return (val==null)?"": String.valueOf(val);
    }



}
