package WsServicios.Entidades.Lista;

import WsServicios.Entidades.Base.wsR_TipoVenta;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class wsR_TiposVenta implements Serializable {

    @XmlElement
    public int vEstado;
    @XmlElement
    public String vMensaje;
    @XmlElement
    public List<wsR_TipoVenta> Datos = new ArrayList<>();

}
