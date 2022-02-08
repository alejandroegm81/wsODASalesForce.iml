package WsServicios.Operaciones.Base;

import WsServicios.Operaciones.Lista.wsR_sPagosInfoFacturadores;
import java.io.Serializable;




public class wsR_sPagos
        implements Serializable
{
  public int vEstado;
  public String vMensaje;
  public int vID;
  public int vIDPago;
  public wsR_sPagosInfoFacturadores vEstadoFacturadores = new wsR_sPagosInfoFacturadores();



  public wsS_RegistroFormaPago FormaPago = new wsS_RegistroFormaPago();
}
