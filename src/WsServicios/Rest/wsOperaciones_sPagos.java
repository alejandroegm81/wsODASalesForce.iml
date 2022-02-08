package WsServicios.Rest;

import WsServicios.Bases.wsInstancias;
import WsServicios.Operaciones.Base.wAutenticacion;
import WsServicios.Operaciones.Base.wsOperacionServicios;
import WsServicios.Operaciones.Base.wsS_RegistroFormaPago;
import WsServicios.Operaciones.Base.wsS_RegistroInfoCliente;
import WsServicios.Operaciones.Lista.wsS_RegistroOperaciones;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsOperaciones_sPagos {
    @XmlElement
    public wsInstancias.wsInstancia Instancia;
    @XmlElement
    public wAutenticacion Autenticacion;
    @XmlElement
    public int IDBusqueda;
    @XmlElement
    public wsOperacionServicios.wsOperacionServicio OperacionServicio;
    @XmlElement
    public wsS_RegistroInfoCliente InfoCliente;
    @XmlElement
    public wsS_RegistroOperaciones Operaciones;
    @XmlElement
    public wsS_RegistroFormaPago FormaPago;
}
