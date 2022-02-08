package WsServicios.Operaciones.Base;
import java.io.Serializable;

public class wsTipoDocumentos implements Serializable {
    public enum wsTipoDocumento {
        FACTURA, FINANCIAMIENTO;
    }
}
