package WsServicios.Entidades.Lista;

import WsServicios.Entidades.Base.wsR_PrecioODA;
import WsServicios.Entidades.Base.wsR_PrecioODADetalle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class wsR_PreciosODA implements Serializable {
    public int vEstado;
    public String vMensaje;
    public wsR_PrecioODADetalle Datos;
}

