package WsServicios.Operaciones.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wAutenticacion implements Serializable {
    @XmlElement
    public String vIDBanco = "";
    @XmlElement
    public String vUsuario = "";
    @XmlElement
    public String vContrasena = "";
}
