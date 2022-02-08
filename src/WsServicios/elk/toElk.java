package WsServicios.elk;

import WsServicios.Bases.wsInstancias;
import WsServicios.Operaciones.Base.wAutenticacion;
import WsServicios.Operaciones.Base.wsOperacionServicios;
import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import javax.ws.rs.core.GenericEntity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class toElk {

    public Boolean SendToKibana( elkResponse vResponse ){
        Boolean vResult = false;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        GenericEntity<elkResponse> genericEntity = new GenericEntity<elkResponse>(vResponse){};
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://172.20.169.87:8083/");

            Gson gson = new Gson();
            String json = gson.toJson(vResponse);


            StringEntity params = new StringEntity(json );
            httpPost.setEntity(params);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println( response.getHeaders().toString());

            httpClient.close();
            vResult=true;

        } catch (Exception ex) { vResult=false; }
        return vResult;
    }

    public void SendToElk(wsInstancias.wsInstancia Instancia, wAutenticacion Autenticacion, wsOperacionServicios.wsOperacionServicio OperacionServicio ,
                          String OperacionWs, Date vIni, Date vFin, int vEstado, String vMensajeGlobal,
                          String vODA, String vBSCS, String vFU, String vOpen, String vGaia, String vPisa, String vTecnomen, String vEsb, String vFormaPago   ){

        String pattern = "YYYY-MM-DD ";
        SimpleDateFormat DFormat = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat TFormat = new SimpleDateFormat("HH:mm:ssZ");
        String sIni = DFormat.format(vIni) + "T" + TFormat.format(vIni) ;
        String sFin =  DFormat.format(vFin) + "T" + TFormat.format(vFin) ;

        elkResponse vResponse = new elkResponse();
        vResponse.INSTANCIA= Instancia.toString() ;
        vResponse.IDBANCO= Autenticacion.vIDBanco ;
        vResponse.BANCO="";
        vResponse.OPERACION_WS= OperacionWs;
        vResponse.FECHA_HORA_INI=sIni;
        vResponse.FECHA_HORA_FIN=sFin;
        vResponse.OPERACION=OperacionServicio.toString() ;
        vResponse.ESTADO= String.valueOf( vEstado);
        vResponse.MENSAJE_GLOBAL=(vMensajeGlobal==null)?"OK":vMensajeGlobal;
        vResponse.FACTURADOR_ODA= vODA;
        vResponse.FACTURADOR_BSCS=vBSCS;
        vResponse.FACTURADOR_FU=vFU;
        vResponse.FACTURADOR_OPEN=vOpen;
        vResponse.FACTURADOR_GAIA=vGaia;
        vResponse.FACTURADOR_PISA=vPisa;
        vResponse.FACTURADOR_TECNOMEN=vTecnomen;
        vResponse.FACTURADOR_ESB=vEsb;
        vResponse.FORMA_PAGO=vFormaPago;

        vResponse.MONEDA = "LOCAL";
        vResponse.TARJETA_ENLINEA = "0";
        SendToKibana( vResponse );
    }




    /*
    *     @WebParam(name = "Instancia") wsInstancias.wsInstancia Instancia,
            @WebParam(name = "Autenticacion") wAutenticacion Autenticacion,
            @WebParam(name = "OperacionServicio") wsOperacionServicios.wsOperacionServicio OperacionServicio,
            @WebParam(name = "Cliente_Contrato") String Cliente_Contrato,
            @WebParam(name = "Telefono") String Telefono,
            @WebParam(name = "NPE") String NPE,
            @WebParam(name = "Identificacion") String Identificacion) {*/




}
