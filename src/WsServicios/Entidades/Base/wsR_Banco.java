package WsServicios.Entidades.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsR_Banco implements Serializable {
  @XmlElement
  public String idBanco;
  @XmlElement
  public String descripcion;
  @XmlElement
  public String codBanco;
  @XmlElement
  public String usuario;
  @XmlElement
  public String contrasena;
  @XmlElement
  public String activo;
  @XmlElement
  public String vAgencia;
  @XmlElement
  public String vCajero;
  @XmlElement
  public String vCodCajeroSupervisor;
  @XmlElement
  public String vCaja;
  @XmlElement
  public String codClaroTV;
  @XmlElement
  public String codTecnomen;
  @XmlElement
  public int timeOut;
  @XmlElement
  public int codMoneda;
  @XmlElement
  public String dirConciliacion;
  @XmlElement
  public String dirConciliacionRespuesta;
}
