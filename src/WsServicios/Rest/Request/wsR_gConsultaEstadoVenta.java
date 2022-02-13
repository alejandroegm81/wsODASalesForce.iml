package WsServicios.Rest.Request;

import WsServicios.Bases.wsInstancias;
import WsServicios.Ordenes.Base.wsRxml_sAnulaPedido;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsR_gConsultaEstadoVenta {
    @XmlElement
    public  wsInstancias.wsInstancia Instancia;

    @XmlElement
    public String Orden;
}
