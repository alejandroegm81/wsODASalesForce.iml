package WsServicios.Entidades.Request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsRxml_gAgencias implements Serializable {
    @XmlElement
    public String codAgencia;
}
