package WsServicios.Rest.Request;

import WsServicios.Bases.wsInstancias;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsR_gConsultaInconformidad {
    @XmlElement
    public  wsInstancias.wsInstancia Instancia;
    @XmlElement
    public String Inconformidad;
}
