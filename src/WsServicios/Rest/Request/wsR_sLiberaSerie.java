package WsServicios.Rest.Request;

import WsServicios.Bases.wsInstancias;
import WsServicios.Ordenes.Base.wsRxml_sLiberaSerie;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsR_sLiberaSerie {
    @XmlElement
    public wsInstancias.wsInstancia Instancia;

    @XmlElement
    public wsRxml_sLiberaSerie Parametros;
}
