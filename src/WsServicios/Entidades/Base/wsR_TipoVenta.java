package WsServicios.Entidades.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsR_TipoVenta implements Serializable {

    @XmlElement
    public String tipoVenta;
    @XmlElement
    public String descripcion;
    @XmlElement
    public String tipoDocumento;
}
