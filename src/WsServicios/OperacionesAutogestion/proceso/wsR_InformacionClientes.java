package WsServicios.OperacionesAutogestion.proceso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class wsR_InformacionClientes implements Serializable {
    public int vEstado=0;
    public String vMensaje="";
    public String vIDInterno="";
    public List<wsR_InformacionClientes_Item> Items = new ArrayList<>();
}
