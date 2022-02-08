package WsServicios.Operaciones.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsS_RegistroInfoCliente implements Serializable{
    @XmlElement
    public String NombreCliente = "";
    @XmlElement
    public String Direccion = "";
    @XmlElement
    public String Identificacion = "";
    @XmlElement
    public String DocumentoTributario = "";
    @XmlElement
    public String Telefono_Contacto = "";
    @XmlElement
    public String Telefono_MovilContacto = "";
    @XmlElement
    public String CorreoElectronico = "";
    @XmlElement
    public String vInfoClienteContrato = "";
    @XmlElement
    public String vInfoTelefono = "";
}
