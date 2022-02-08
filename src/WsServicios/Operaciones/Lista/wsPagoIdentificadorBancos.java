package WsServicios.Operaciones.Lista;

import WsServicios.Operaciones.Base.wsPagoIdentificadorBanco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class wsPagoIdentificadorBancos
        implements Serializable {
    public int vEstado;
    public String vMensaje;
    public List<wsPagoIdentificadorBanco> Datos = new ArrayList<>();

    //public int getvEstado() {return this.vEstado;    }
    //public void setvEstado(int vEstado) {      this.vEstado = vEstado;   }
    //public String getvMensaje() {       return this.vMensaje;    }
    //public void setvMensaje(String vMensaje) {        this.vMensaje = vMensaje;    }
    //public List<wsPagoIdentificadorBanco> getDatos() {        return this.Datos;    }
    //public void setDatos(List<wsPagoIdentificadorBanco> datos) {        this.Datos = datos;    }
}

