package WsServicios.Ordenes.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsRxml_sGeneraInconformidad {
    @XmlElement
    public String codAgencia;
    @XmlElement
    public String codGestor;
    @XmlElement
    public String telefono;
    @XmlElement
    public String comentarios;
    @XmlElement
    public String marca;
    @XmlElement
    public String modelo;
    @XmlElement
    public String codigoFalla;
    @XmlElement
    public String numeroSerie;
}
