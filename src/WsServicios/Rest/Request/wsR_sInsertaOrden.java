package WsServicios.Rest.Request;

import WsServicios.Bases.wsInstancias;
import WsServicios.Ordenes.Base.wsRxml_sInsertaOrden;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsR_sInsertaOrden {
    @XmlElement
    public wsInstancias.wsInstancia Instancia;

    @XmlElement
    public wsRxml_sInsertaOrden vParametros;

}
