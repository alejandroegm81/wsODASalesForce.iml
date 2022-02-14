package WsServicios.Rest.Request;

import WsServicios.Bases.wsInstancias;
import WsServicios.Entidades.Request.wsRxml_gTiposVenta;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsR_gTiposVenta {


    @XmlElement
    public  wsInstancias.wsInstancia Instancia;

    @XmlElement
    public wsRxml_gTiposVenta Parametros;
}
