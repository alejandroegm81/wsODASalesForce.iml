package FromNET;

public class GlobalRQ {
    public String vQuery = "select \n" +
            "a.corr_transac id, \n" +
            "a.corr_transac_pago idpago,  \n" +
            "to_char( fec_transaccion, 'dd/MM/yyyy') fecha,\n" +
            "to_char( fec_transaccion, 'HH24:mm:ss') hora,\n" +
            "a.cod_operador,\n" +
            "num_cliente cliente,\n" +
            "num_telefono telefono,\n" +
            "'' iddocumento,\n" +
            "npe nodocumento,\n" +
            "a.num_referencia referencia,\n" +
            "a.sec_banco  Sec_Banco, \n " +
            "'FA' tipodocumento,\n" +
            "b.tip_pag_tran, \n" +
            "b.cod_banco,\n" +
            "b.num_cta_banco numeroCuenta , \n" +
            "b.num_ser_cuenta numeroCCheque, \n" +
            "0 EnLinea,\n" +
            "substr(sec.decrypt(b.numero_tarjeta),1,4) num_tarjeta, '' fecha_vencimiento, '' cvc, b.cod_autoriza, a.total_pagar, a.estatus_transac  \n" +
            "from transacciones a \n" +
            "inner join transacciones_reg_pago b on a.corr_transac_pago=b.corr_transac_pago\n" +
            "where {}   ";
}
