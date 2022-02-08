package WsServicios.Entidades.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsR_Caja implements Serializable {
  @XmlElement
  public String codAgencia;
  @XmlElement
  public String codCaja;
  @XmlElement
  public String nomCaja;
  @XmlElement
  public String direcIP;
  @XmlElement
  public String numRegistro;
  @XmlElement
  public String cguichet;
  @XmlElement
  public String tty;
}
