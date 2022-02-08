package WsServicios.Operaciones.Base;

import java.io.Serializable;

public class wsR_sPagosInfoFacturador
        implements Serializable {
    public wsFacturadores.wsFacturador Facturador = wsFacturadores.wsFacturador.ODA;
    public int vEstado = 0;
    public String vMensaje = "";
    public String vDBMensaje = "";
}
