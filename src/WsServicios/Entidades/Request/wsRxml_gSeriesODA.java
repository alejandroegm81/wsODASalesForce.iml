package WsServicios.Entidades.Request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsRxml_gSeriesODA implements Serializable {
    @XmlElement
    public String serie;
    @XmlElement
    public String codProducto;
    @XmlElement
    public String estatus;
    @XmlElement
    public String codAgencia;
}
