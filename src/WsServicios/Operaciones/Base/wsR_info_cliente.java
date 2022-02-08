package WsServicios.Operaciones.Base;

import WsServicios.Operaciones.Lista.wsR_info_documentos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class wsR_info_cliente
        implements Serializable
{
    public String IDCliente = "";
    public String IDContrato = "";
    public wsOperacionServicios.wsOperacionServicio OperacionServicio = null;
    public String Telefono = "";
    public String Nombre = "";
    public String Identificacion = "";
    public String TipoCliente = "";
    public String CorreoElectronico = "";
    public String CicloCliente = "";

    public double SaldoTotal = 0.0D;
    public double SaldoNoVencido = 0.0D;
    public double SaldoVencido = 0.0D;
    public int CntDocumentos = 0;
    public List<wsR_info_documento> Documentos = new ArrayList<>();
}
