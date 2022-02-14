package WsServicios.Entidades.Lista;

import WsServicios.Entidades.Base.wsR_Agencia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class wsR_Agencias         implements Serializable {
  public int vEstado;
  public String vMensaje;
  public List<wsR_Agencia> Datos = new ArrayList<>();

}
