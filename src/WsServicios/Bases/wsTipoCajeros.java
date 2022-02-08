package WsServicios.Bases;
import java.io.Serializable;

public class wsTipoCajeros implements Serializable {
    public enum wsTipoCajero {
        Supervisor, Cajero, Vendedor;
    }
}
