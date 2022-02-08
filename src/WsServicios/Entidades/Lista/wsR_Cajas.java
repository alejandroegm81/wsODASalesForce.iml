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

  //public int getvEstado() { return this.vEstado; }
  //public void setvEstado(int vEstado) { this.vEstado = vEstado; }
  //public String getvMensaje() { return this.vMensaje; }
  //public void setvMensaje(String vMensaje) { this.vMensaje = vMensaje; }
  //public List<wsR_Caja> getDatos() { return this.Datos; }
  //public void setDatos(List<wsR_Caja> datos) { this.Datos = datos; }
}
