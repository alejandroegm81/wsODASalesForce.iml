package WsServicios.Entidades.Lista;

import WsServicios.Entidades.Base.wsR_Banco;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class wsR_Bancos
        implements Serializable
{
  public int vEstado;
  public String vMensaje;
  public List<wsR_Banco> Datos = new ArrayList<>();

}
