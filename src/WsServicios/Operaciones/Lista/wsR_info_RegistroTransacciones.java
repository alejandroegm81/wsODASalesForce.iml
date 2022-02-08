package WsServicios.Operaciones.Lista;

import WsServicios.Operaciones.Base.wsR_info_RegistroTransaccion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class wsR_info_RegistroTransacciones implements Serializable {
    public int vEstado;
    public String vMensaje;
    public List<wsR_info_RegistroTransaccion> Datos = new ArrayList<>();
}
