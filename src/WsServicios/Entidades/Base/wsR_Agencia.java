package WsServicios.Entidades.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@XmlRootElement
public class wsR_Agencia implements Serializable {
  @XmlElement
  public String codAgencia;
  @XmlElement
  public String nomAgencia;
  @XmlElement
  public String codSinergia;
}
