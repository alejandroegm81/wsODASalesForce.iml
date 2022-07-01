package WsServicios.Rest.Request;

import WsServicios.Bases.wsInstancias;
import WsServicios.Ordenes.Base.wsRxml_sGeneraInconformidad;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsR_sGeneraInconformidad {
    @XmlElement
    public wsInstancias.wsInstancia Instancia;
    @XmlElement
    public wsRxml_sGeneraInconformidad Parametros;


}
