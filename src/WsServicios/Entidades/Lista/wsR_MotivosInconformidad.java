package WsServicios.Entidades.Lista;

import WsServicios.Entidades.Base.wsR_MotivoInconformidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class wsR_MotivosInconformidad implements Serializable {
    public int vEstado;
    public String vMensaje;
    public List<wsR_MotivoInconformidad> Datos = new ArrayList<>();
}
