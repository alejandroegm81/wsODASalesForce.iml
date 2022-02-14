package WsServicios.Entidades.Lista;

import WsServicios.Entidades.Base.wsR_PrecioODA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class wsR_PreciosODA implements Serializable {
    public int vEstado;
    public String vMensaje;
    public List<wsR_PrecioODA> Datos = new ArrayList<>();
}

