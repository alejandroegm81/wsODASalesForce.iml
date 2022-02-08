package WsServicios.Operaciones.Oper;

import WsServicios.Operaciones.Base.wsTipoDocumentos;
import java.io.Serializable;

public class ws_det_operations implements Serializable {
    public String Cliente_Contrato = "";
    public String Telefono = "";
    public String IDDocumento = "";
    public String NoDocumento = "";
    public String Referencia = "";
    public wsTipoDocumentos.wsTipoDocumento TipoDocumento = null;
    public String NPE = "";
    public double MontoPago = 0.0D;
    public double SaldoReferencia=0.0D;
    public String FechaVencimiento="";
    public String FechaEmision="";
    public String CicloCliente="";

    public String c_cliente_nombre="";
    public String c_cliente_cedula="";
    public String c_cliente_correo="";
}

