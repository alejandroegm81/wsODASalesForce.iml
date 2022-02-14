package WsServicios.Entidades.Lista;

import WsServicios.Entidades.Base.wsR_Cajero;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class wsR_Cajeros implements Serializable
{
  public int vEstado;
  public String vMensaje;
  public List<wsR_Cajero> Datos = new ArrayList<>();

}
