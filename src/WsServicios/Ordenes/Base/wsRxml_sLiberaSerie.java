package WsServicios.Ordenes.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsRxml_sLiberaSerie implements Serializable {
    @XmlElement
    public String orden;
    @XmlElement
    public String serie;
}
