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

  //public int getvEstado() { return this.vEstado; }
  //public void setvEstado(int vEstado) { this.vEstado = vEstado; }
  //public String getvMensaje() { return this.vMensaje; }
  //public void setvMensaje(String vMensaje) { this.vMensaje = vMensaje; }
  //public List<wsR_Banco> getDatos() { return this.Datos; }
  //public void setDatos(List<wsR_Banco> datos) { this.Datos = datos; }
}
