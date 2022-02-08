package WsServicios.OperacionesAutogestion.proceso;

import WsServicios.Operaciones.Base.wsR_info_cliente;

import java.util.ArrayList;
import java.util.List;

public class wsR_HistoricoFacturas {
    public int vEstado=0;
    public String vMensaje="";
    public String vIDInterno="";
    public List<wsR_HistoricoFacturas_Item> Items = new ArrayList<>();

}
