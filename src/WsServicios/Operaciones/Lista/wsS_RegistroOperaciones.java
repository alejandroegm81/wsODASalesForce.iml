package WsServicios.Operaciones.Lista;

import WsServicios.Operaciones.Base.wsS_RegistroOperacion;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@XmlRootElement
public class wsS_RegistroOperaciones implements Serializable {
    //@XmlElement
    public List<wsS_RegistroOperacion> Datos = new ArrayList<>();
}

