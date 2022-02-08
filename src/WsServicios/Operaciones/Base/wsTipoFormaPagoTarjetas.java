package WsServicios.Operaciones.Base;
import java.io.Serializable;

public class wsTipoFormaPagoTarjetas implements Serializable {
    public enum wsTipoFormaPagoTarjeta {
        CREDOMATIC, VISA, MASTERCARD;
    }
}
