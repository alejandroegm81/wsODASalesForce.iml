package WsServicios.Rest;

import WsServicios.Bases.wsInstancias;
import WsServicios.Operaciones.Base.wAutenticacion;
import WsServicios.Operaciones.Base.wsOperacionServicios;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsOperaciones_sAnulacion {
    @XmlElement
    public wsInstancias.wsInstancia Instancia;
    @XmlElement
    public wAutenticacion Autenticacion;
    @XmlElement
    public wsOperacionServicios.wsOperacionServicio OperacionServicio;
    @XmlElement
    public int IDPago;
    @XmlElement
    public String Comentario;
}
