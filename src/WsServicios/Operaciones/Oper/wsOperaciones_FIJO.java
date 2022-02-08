package WsServicios.Operaciones.Oper;

import FromNET.ConnectTo;
import FromNET.GlobalDB;
import FromNET.SubDataTable;
import WsServicios.Bases.wsInstancias;
import WsServicios.Operaciones.Base.*;
import db.BaseClass;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.math.BigDecimal;

public class wsOperaciones_FIJO  extends BaseClass {

    String getXmlQuery(wsInstancias.wsInstancia Instancia,  int type  ){
        String vQuery = "", tagName="query";
        try{
            String vConfFile = "FIJO_" + getCountryCode(Instancia) + ".xml";
            InputStream is = getClass().getResourceAsStream(vConfFile);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);
            doc.getDocumentElement().normalize();
            switch (type){
                case 1: tagName+="_cliente"; break;
                case 2: tagName+="_telefono"; break;
                //case 3: tagName+="_fu"; break;
            }
            NodeList nodeList = doc.getElementsByTagName(tagName);
            for (int i = 0; i < nodeList.getLength(); i++) {
                vQuery = nodeList.item(i).getFirstChild().getNodeValue() ;
            }
        }catch(Exception E){}
        return vQuery;
    }


    public wsR_gBusqueda Busqueda(wsInstancias.wsInstancia Instancia,
                                  wAutenticacion Autenticacion,
                                  wsOperacionServicios.wsOperacionServicio OperacionServicio,
                                  String Cliente_Contrato,
                                  String Telefono,
                                  String NPE,
                                  String Identificacion) throws Exception {
        wsR_gBusqueda vReturn = new wsR_gBusqueda();
        String vQuery="", vQueryFU="", vQueryParameter_a="",vQueryParameter_b="",vQueryParameter_c="", vCountryCode=getCountryCode(Instancia);
        SubDataTable dt=new SubDataTable();
        GlobalDB db = new GlobalDB();

        if( !OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.FIJO ) ){ throw new Exception("Operacion no es Busqueda Fijo"); }
        if(Telefono.trim().length()==11){Telefono=Telefono.substring(4,8);}

        if(Cliente_Contrato.trim().length()>1){ vQuery = getXmlQuery(Instancia,1); }
        if(Telefono.trim().length()>1){ vQuery = getXmlQuery(Instancia,2); }
        //if( vQuery.length()<=0 ){ vQueryFU  = getXmlQuery(Instancia,3);         }


        if(vQuery==""){ throw new Exception("No se encontro archivo de configuracion"); }


        if( vCountryCode.equals("502") || vCountryCode.equals("503") ||  vCountryCode.equals("504")  || vCountryCode.equals("505") ){
            if( Telefono.length()>0 ){ vQueryParameter_a="'" +Telefono + "'"; }
            if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="'" +Cliente_Contrato + "'"; }
            if( Identificacion.length()>0 ){ vQueryParameter_c="c.passportno='" + Identificacion + "'"; }
            vQuery = vQuery.replace("{TELEFONO}", vQueryParameter_a );
            vQuery = vQuery.replace("{CLIENTE}", vQueryParameter_b );
        }else{
            if(vCountryCode.equals("507")){
                if( Telefono.length()>0 ){
                    if(Telefono.length()>8){
                        vQueryParameter_a="g.dn_num='" +  Telefono + "'";
                    }else{
                        vQueryParameter_a="g.dn_num='" + getCountryCode(Instancia) + Telefono + "'";
                    }

                }
                String vSubConsulta = "a.customer_id  IN ( select to_number(vcliente) from dual union all select customer_id from info_cust_text where text09 =vcliente UNION ALL select customer_id_high from customer_all where customer_id=to_number(vcliente) and customer_id_high is not null  ) ";
                if( Cliente_Contrato.length()>0 ){ vQueryParameter_b=  vSubConsulta.replace("vcliente","'" +Cliente_Contrato + "'")  ; }
                if( Identificacion.length()>0 ){ vQueryParameter_c="c.passportno='" + Identificacion + "'"; }
            }else{
                if( Telefono.length()>0 ){ vQueryParameter_a="g.dn_num='" + Telefono + "'"; }
                if( Cliente_Contrato.length()>0 ){ vQueryParameter_b="a.customer_id='" +Cliente_Contrato + "'"; }
                if( Identificacion.length()>0 ){ vQueryParameter_c="a.passportno='" + Identificacion + "'"; }
            }
            vQuery = vQuery.replace("{}", vQueryParameter_a + ((vQueryParameter_a.length()>0 && vQueryParameter_b.length()>0)?" and ":"")  +vQueryParameter_b +  ((vQueryParameter_b.length()>0 && vQueryParameter_c.length()>0)?" and ":"") +  vQueryParameter_c );
        }



        if(vCountryCode.equals("507")){
            //dt = db.getQuery(vQuery, ConnectTo.ODA_507_OPEN_db);
            //if(dt.vData==false){
                dt = db.getQuery(vQuery, ConnectTo.ODA_507_BSCSFU_db);
            //}
        }else{
            dt = db.getQuery(vQuery, getConnectTo(Instancia));
        }


        if(dt.vData && dt.vRows>1){
            String v0,v1,v2;
            Boolean vCFound=false, vDocumentFound=false;
            BigDecimal vSaldoTotal=new BigDecimal(0),vSaldoVencido=new BigDecimal(0),vSaldoNoVencido=new BigDecimal(0);
            wsR_info_cliente vCliente=new wsR_info_cliente();
            wsR_info_documento vDocumento = new wsR_info_documento();
            for (int i = 1; i < dt.vRows; i++) {
                vCFound =false;
                v0 = dt.Datos.rows.get(i).getColByName("ID").value_string;
                v1 = dt.Datos.rows.get(i).getColByName("CUSTOMER_ID").value_string;
                v2 = dt.Datos.rows.get(i).getColByName("DN_NUM").value_string;
                System.out.println("Datos: {"+ v1 + "," + v2 + "}"  );

                for(int iC=0;iC<vReturn.Clientes.size();iC++){
                    if( vReturn.Clientes.get(iC).IDCliente.equals( v1 )   ){ vCFound=true; vCliente=vReturn.Clientes.get(iC);  break;  }
                }
                if(vCFound==false){//Agregar Cliente
                    vCliente=new wsR_info_cliente();
                    vCliente.OperacionServicio  =   wsOperacionServicios.wsOperacionServicio.FIJO;
                    vCliente.IDCliente	        =	dt.Datos.rows.get(i).getColByName("CUSTOMER_ID").value_string;
                    vCliente.IDContrato	        =	dt.Datos.rows.get(i).getColByName("CO_ID").value_string;
                    vCliente.Telefono	        =	dt.Datos.rows.get(i).getColByName("DN_NUM").value_string;
                    vCliente.Nombre	            =	dt.Datos.rows.get(i).getColByName("NOMBRE").value_string;
                    vCliente.Identificacion	    =	dt.Datos.rows.get(i).getColByName("IDENTIFICACION").value_string;
                    vCliente.TipoCliente	    =	dt.Datos.rows.get(i).getColByName("TIPO_CLIENTE").value_string;
                    vCliente.CorreoElectronico	=	dt.Datos.rows.get(i).getColByName("CORREO").value_string;
                    vCliente.SaldoTotal	        =	0; //dt.Datos.rows.get(i).getColByName("SALDO_TOTAL").value_double;
                    vCliente.SaldoNoVencido	    =	0; //dt.Datos.rows.get(i).getColByName("SALDO_NOVENCIDO").value_double;
                    vCliente.SaldoVencido	    =	0; //dt.Datos.rows.get(i).getColByName("SALDO_VENCIDO").value_double;
                    vCliente.CntDocumentos	    =	0; //dt.Datos.rows.get(i).getColByName("CANTIDAD_DOCUMENTOS").value_int;
                    vCliente.CicloCliente	    =	getStr(dt.Datos.rows.get(i).getColByName("CICLO").value_string);
                    vReturn.Clientes.add( vCliente );
                }
                vDocumento = new wsR_info_documento();
                vDocumento.IDCliente	            =	dt.Datos.rows.get(i).getColByName("ID2").value_string;
                vDocumento.IDDocumento   	    =	dt.Datos.rows.get(i).getColByName("DOCID").value_string;
                vDocumento.NoDocumento	        =	dt.Datos.rows.get(i).getColByName("DOC").value_string;
                vDocumento.Referencia	        =	dt.Datos.rows.get(i).getColByName("REFE").value_string;
                vDocumento.NPE	                =	dt.Datos.rows.get(i).getColByName("NPE").value_string;
                vDocumento.DocumentoLecturaSolamente	=	dt.Datos.rows.get(i).getColByName("LECTURA").value_int;
                if( dt.Datos.rows.get(i).getColByName("FACTURA").value_string.equals("FA") ) {
                    vDocumento.TipoDocumento	        =wsTipoDocumentos.wsTipoDocumento.FACTURA;
                }
                if( dt.Datos.rows.get(i).getColByName("FACTURA").value_string.equals("FI") ) {
                    vDocumento.TipoDocumento	        =wsTipoDocumentos.wsTipoDocumento.FINANCIAMIENTO;
                }
                vDocumento.FechaEmision	        =	dt.Datos.rows.get(i).getColByName("EMISION").value_string;
                vDocumento.FechaVencimiento	    =	dt.Datos.rows.get(i).getColByName("VENCIMIENTO").value_string;
                vDocumento.DiasMora	            =	dt.Datos.rows.get(i).getColByName("DIASMORA").value_int;
                vDocumento.Saldo	                =	dt.Datos.rows.get(i).getColByName("SALDO").value_bigdecimal.setScale(2).doubleValue();
                vDocumento.SaldoVencido	        =	dt.Datos.rows.get(i).getColByName("SALDOVENCIDO").value_bigdecimal.setScale(2).doubleValue();
                vDocumento.SaldoNoVencido	        =	dt.Datos.rows.get(i).getColByName("SALDONOVENCIDO").value_bigdecimal.setScale(2).doubleValue();
                for(int iC=0;iC<vReturn.Clientes.size();iC++){
                    if( vReturn.Clientes.get(iC).IDCliente.equals( v1 ) && vDocumento.IDDocumento.length()>1 && vDocumento.Referencia.length()>1 ){
                        vDocumentFound=false;
                        vSaldoTotal = new BigDecimal(0).setScale(2);
                        vSaldoVencido = new BigDecimal(0).setScale(2);
                        vSaldoNoVencido = new BigDecimal(0).setScale(2);
                        for(int kk=0;kk<vReturn.Clientes.get(iC).Documentos.size();kk++){
                            if( vReturn.Clientes.get(iC).Documentos.get(kk).IDDocumento.equals(vDocumento.IDDocumento) ){vDocumentFound=true;break;}
                            vSaldoTotal     = vSaldoTotal.add(new BigDecimal(vReturn.Clientes.get(iC).Documentos.get(kk).Saldo)).setScale(2,BigDecimal.ROUND_HALF_UP) ;
                            vSaldoVencido   = vSaldoVencido.add(new BigDecimal(vReturn.Clientes.get(iC).Documentos.get(kk).SaldoVencido)).setScale(2,BigDecimal.ROUND_HALF_UP)  ;
                            vSaldoNoVencido = vSaldoNoVencido.add(new BigDecimal(vReturn.Clientes.get(iC).Documentos.get(kk).SaldoNoVencido)).setScale(2,BigDecimal.ROUND_HALF_UP)  ;
                        }
                        if(!vDocumentFound){
                            vReturn.Clientes.get(iC).Documentos.add( vDocumento );
                            vReturn.Clientes.get(iC).CntDocumentos = vReturn.Clientes.get(iC).Documentos.size();
                            vReturn.Clientes.get(iC).SaldoTotal = vSaldoTotal.add(new BigDecimal(vDocumento.Saldo)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()  ;
                            vReturn.Clientes.get(iC).SaldoVencido = vSaldoVencido.add(new BigDecimal(vDocumento.SaldoVencido)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() ;
                            vReturn.Clientes.get(iC).SaldoNoVencido = vSaldoNoVencido.add(new BigDecimal(vDocumento.SaldoNoVencido)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()  ;
                        }
                        break;
                    }
                }
            }
            vReturn.vEstado=1;
        }else{
            vReturn.vMensaje="Sin datos";
            vReturn.vEstado=0;
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
