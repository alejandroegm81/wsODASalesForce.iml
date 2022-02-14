package WsServicios.Entidades.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsR_Almacen implements Serializable {
    @XmlElement
    public String codAgencia;
    @XmlElement
    public String centro;
    @XmlElement
    public String almacen;
    @XmlElement
    public String descripcion;

}
