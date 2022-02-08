package WsServicios.Operaciones.Base;

import java.io.Serializable;

public class wsS_RegistroFormaPago
        implements Serializable {
    public int idFormaPago = 0;

    public wsTipoFormaPagoTarjetas.wsTipoFormaPagoTarjeta TipoFormaPagoTarjeta = null;
    public wsTipoFormaPagos.wsTipoFormaPago TipoFormaPago = wsTipoFormaPagos.wsTipoFormaPago.EFECTIVO;
    public wsMonedas.wsMoneda Moneda = wsMonedas.wsMoneda.LOCAL;



    public wsPagoIdentificadorBanco C_CodBanco = new wsPagoIdentificadorBanco();
    public String C_NumeroCuenta = "";
    public String C_NumeroCheque = "";



    public int T_AplicarTranTarjetaEnLinea = 0;
    public String T_Numero_Tarjeta = "";
    public String T_Vencimiento = "";
    public String T_CVC = "";
    public String T_CodAutorizacion = "";


    public double MontoPago = 0.0D;
}
