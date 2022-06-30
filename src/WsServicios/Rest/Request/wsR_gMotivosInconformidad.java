package WsServicios.Rest.Request;

import WsServicios.Bases.wsInstancias;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsR_gMotivosInconformidad {
    @XmlElement
    public wsInstancias.wsInstancia Instancia;
}
