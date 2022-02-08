package WsServicios.Rest;

import WsServicios.Bases.wsInstancias;
import WsServicios.Operaciones.Base.*;
import WsServicios.Operaciones.Lista.wsS_RegistroOperaciones;
import WsServicios.OperacionesAutogestion.proceso.wsS_FormaPagoAutogestion;

import javax.jws.WebParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsAutogestion_sPagoServicios{
    @XmlElement
    public wsInstancias.wsInstancia Instancia;
    @XmlElement
    public wAutenticacion Autenticacion;
    @XmlElement
    public int IDBusquedaExterna;
    @XmlElement
    public wsOperacionServicios.wsOperacionServicio OperacionServicio;
    @XmlElement
    public wsS_RegistroOperacion Operacion;
    @XmlElement
    public wsS_FormaPagoAutogestion FormaPago;
}
