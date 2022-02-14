package WsServicios.Entidades.Lista;

import WsServicios.Entidades.Base.wsR_Almacen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class wsR_Almacenes implements Serializable {
    public int vEstado;
    public String vMensaje;
    public List<wsR_Almacen> Datos = new ArrayList<>();
}
