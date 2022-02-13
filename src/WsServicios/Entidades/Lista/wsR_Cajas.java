package WsServicios.Entidades.Lista;

import WsServicios.Entidades.Base.wsR_Caja;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class wsR_Cajas
        implements Serializable
{
  public int vEstado;
  public String vMensaje;
  public List<wsR_Caja> Datos = new ArrayList<>();

}
