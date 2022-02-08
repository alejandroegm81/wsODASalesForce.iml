package WsServicios.Operaciones.Base;

import java.io.Serializable;

public class wsR_info_documento
        implements Serializable {
  public String IDCliente = "";
  public String IDDocumento = "";
  public String NoDocumento = "";
  public String Referencia = "";
  public String NPE = "";
  public int DocumentoLecturaSolamente = 0;

  public wsTipoDocumentos.wsTipoDocumento TipoDocumento;

  public String FechaEmision = "";
  public String FechaVencimiento = "";

  public int DiasMora = 0;

  public double Saldo = 0.0D;
  public double SaldoVencido = 0.0D;
  public double SaldoNoVencido = 0.0D;
}
