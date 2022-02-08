package WsServicios.OperacionesAutogestion.proceso;

import java.io.Serializable;

public class wsR_HistorialPagos_Item implements Serializable {

    public String MetodoPago = "";
    public String FechaPago = "";
    public String NumeroFactura="";
    public double MontoCancelado = 0.0;
    public String FechaVencimiento="";
    public String IdentificadorTransaccion="";

}
