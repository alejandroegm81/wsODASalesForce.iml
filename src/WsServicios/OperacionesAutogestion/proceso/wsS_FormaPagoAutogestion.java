package WsServicios.OperacionesAutogestion.proceso;

import WsServicios.Operaciones.Base.wsMonedas;
import WsServicios.Operaciones.Base.wsPagoIdentificadorBanco;
import WsServicios.Operaciones.Base.wsTipoFormaPagoTarjetas;
import WsServicios.Operaciones.Base.wsTipoFormaPagos;

import java.io.Serializable;

public class wsS_FormaPagoAutogestion  implements Serializable {
    public wsTipoFormaPagos.wsTipoFormaPago TipoFormaPago = wsTipoFormaPagos.wsTipoFormaPago.TARJETA;
    public String T_Numero_Tarjeta = "";
    public String T_CodAutorizacion = "";

}
