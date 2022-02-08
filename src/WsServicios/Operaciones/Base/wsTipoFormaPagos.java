package WsServicios.Operaciones.Base;
import java.io.Serializable;

public class wsTipoFormaPagos implements Serializable {
    public enum wsTipoFormaPago {
        EFECTIVO, CHEQUE, TARJETA, REMESA;
    }
}
