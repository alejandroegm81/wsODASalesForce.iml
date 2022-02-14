package WsServicios.Entidades.Lista;

import WsServicios.Entidades.Base.wsR_Serie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class wsR_Series implements Serializable {
    public int vEstado;
    public String vMensaje;
    public List<wsR_Serie> Datos = new ArrayList<>();
}
