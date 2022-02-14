package WsServicios.Ordenes.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsRxml_sReservaSerie implements Serializable {
    @XmlElement
    public String codAgencia;
    @XmlElement
    public String codAlmacen;
    @XmlElement
    public String material;
    @XmlElement
    public String serie;
    @XmlElement
    public String orden;
}
