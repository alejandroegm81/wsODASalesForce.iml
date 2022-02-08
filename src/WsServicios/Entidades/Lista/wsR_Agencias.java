package WsServicios.Entidades.Lista;

import WsServicios.Entidades.Base.wsR_Agencia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class wsR_Agencias         implements Serializable {
  public int vEstado;
  public String vMensaje;
  public List<wsR_Agencia> Datos = new ArrayList<>();

  //public int getvEstado() { return this.vEstado; }
  //public void setvEstado(int vEstado) { this.vEstado = vEstado; }
  //public String getvMensaje() { return this.vMensaje; }
  //public void setvMensaje(String vMensaje) { this.vMensaje = vMensaje; }
  //public List<wsR_Agencia> getDatos() { return this.Datos; }
  //public void setDatos(List<wsR_Agencia> datos) { this.Datos = datos; }
}
