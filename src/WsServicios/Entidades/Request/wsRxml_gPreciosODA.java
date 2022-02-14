package WsServicios.Entidades.Request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsRxml_gPreciosODA implements Serializable {

    @XmlElement
    public String motivoPedido;
    @XmlElement
    public String codProducto;
    @XmlElement
    public String grupoCondiciones;
    @XmlElement
    public String grupoComisiones;


}
