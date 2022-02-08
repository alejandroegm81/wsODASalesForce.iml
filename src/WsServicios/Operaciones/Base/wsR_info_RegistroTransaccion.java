package WsServicios.Operaciones.Base;

import java.io.Serializable;

public class wsR_info_RegistroTransaccion
        implements Serializable {
    public int vID = 0;
    public int vIDPago = 0;
    public String vEstado="";

    public String vIDBusquedaExterno="";

    public String FechaTransaccion = "";
    public String HoraTransaccion = "";
    public wsOperacionServicios.wsOperacionServicio OperacionServicio = null;

    public String Cliente_Contrato = "";
    public String Telefono = "";

    public String IDDocumento = "";
    public String NoDocumento = "";
    public String Referencia = "";
    public wsTipoDocumentos.wsTipoDocumento TipoDocumento = null;

    public wsTipoFormaPagos.wsTipoFormaPago TipoFormaPago = wsTipoFormaPagos.wsTipoFormaPago.EFECTIVO;
    public String C_CodBanco = ""; // new wsPagoIdentificadorBanco();
    public String C_NumeroCuenta = "";
    public String C_NumeroCheque = "";


    public int T_AplicarTranTarjetaEnLinea = 0;
    public String T_Numero_Tarjeta = "";
    public String T_Vencimiento = "";
    public String T_CVC = "";
    public String T_CodAutorizacion = "";

    public double MontoPago = 0.0D;
}
