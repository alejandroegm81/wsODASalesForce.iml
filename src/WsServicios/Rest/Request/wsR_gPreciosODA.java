package WsServicios.Rest.Request;

import WsServicios.Bases.wsInstancias;
import WsServicios.Entidades.Request.wsRxml_gPreciosODA;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsR_gPreciosODA {
    @XmlElement
    public  wsInstancias.wsInstancia Instancia;

    @XmlElement
    public wsRxml_gPreciosODA Parametros;

}
