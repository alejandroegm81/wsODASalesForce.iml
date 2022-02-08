package WsServicios.Operaciones.Base;
import java.io.Serializable;

public class wsOperacionServicios implements Serializable {
    public enum wsOperacionServicio {
        FIJO, MOVIL, BUSQUEDA_SERVICIO,  REGISTRO_VENTA, RECARGA, PAQUETE, DISTRIBUIDOR, OPERACIONES_VARIAS;
    }
}
