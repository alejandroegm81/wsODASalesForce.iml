package WsServicios.Entidades.Base;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class wsR_Cajero implements Serializable {

  @XmlElement
  public String codAgencia;

  @XmlElement
  public String codCajero;
  @XmlElement
  public String nomCajero;
  @XmlElement
  public String codEmpleado;

  @XmlElement(name = "contrasena")
  //@XmlJavaTypeAdapter(value=CDATAAdapter.class)
  public String contrasena;

  @XmlElement
  public String tipoCajero;

  @XmlElement
  public int cuentaBloqueada;

  @XmlElement
  public String fechaBloqueo;

  @XmlElement
  public String codGestorCRM;

  @XmlElement
  public String fechaContrasenaExpira;
}
