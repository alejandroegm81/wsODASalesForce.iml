package WsServicios.Ordenes.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


@XmlRootElement
public class wsRxml_sInsertaOrden_Encabezado implements Serializable {
    @XmlElement
    public String codAgencia;
    @XmlElement
    public long codigoCliente;
    @XmlElement
    public String codigoGestor;
    @XmlElement
    public String codigoPais;
    @XmlElement
    public long correlativoVenta;
    @XmlElement
    public int cuentaDetalle;
    @XmlElement
    public String direccion;
    @XmlElement
    public int exento;
    @XmlElement
    public Date fechaCreacion;
    @XmlElement
    public int financiado;
    @XmlElement
    public double montoFinanciado;
    @XmlElement
    public String documentoIdentidad;
    @XmlElement
    public String nombreCliente;
    @XmlElement
    public int cuotas;
    @XmlElement
    public double prima;
    @XmlElement
    public double deposito;
    @XmlElement
    public double anticipo;
    @XmlElement
    public String registroFiscal;
    @XmlElement
    public long secuencia;
    @XmlElement
    public String tipoDocumento;
    @XmlElement
    public String tipoVenta;
    @XmlElement
    public double totalDescuento;
    @XmlElement
    public double totalDescuentoEspecial;
    @XmlElement
    public double totalOrden;
    @XmlElement
    public String departamento;
    @XmlElement
    public String municipio;
    @XmlElement
    public String variablesAdicionales;
    @XmlElement
    public String combo;
    @XmlElement
    public String giroNegocio;
    @XmlElement
    public String telefonoFinancia;
    @XmlElement
    public String contratoFinancia;

    /*
    *
    * 1- P_AGENCIA VARCHAR2,
      2-  P_CODIGOCLIENTE   IN NUMBER,
      3-  P_CODIGOGESTOR     IN VARCHAR2,
      4-  P_CODIGOPAIS        IN VARCHAR2,
      5-  P_CORRELATIVOVENTA    IN NUMBER,
      6-  P_CUENTADETALLE        IN NUMBER,
      7-  P_DIRECCION        IN VARCHAR2,
      8-  P_EXENTO        IN NUMBER,
      9-  P_FECHACREACION        IN DATE,
      10-  P_FINANCIAMIENTO    IN NUMBER,
      11-  P_MONTOFINAN        IN NUMBER,
      12-  P_NIT            IN VARCHAR2,
      13-  P_NOMBRECLIENTE        IN VARCHAR2,
      14-  P_NUMEROCUOTAS        IN NUMBER,
      15-  P_PRIMA            IN NUMBER,
      16-  P_DEPOSITO_GARANTIA  IN     NUMBER, SIMULACION - SIN FLUJO no aparecen en gt
    17- P_ANTICIPADO         IN     NUMBER, SIMULACION - SIN FLUJO no aparecen en gt
    18- P_REGISTROFISCAL    IN VARCHAR2,
    19- P_SECUENCIA        IN NUMBER,
    20- P_TIPODOC        IN VARCHAR2,
    21- P_TIPOVENTA        IN VARCHAR2,
    22- P_TOTALDESC        IN NUMBER,
    23- P_TOTALDESCESP        IN NUMBER,
    24- P_TOTALORDEN        IN NUMBER,
    25- P_DEPARTAMENTO        IN VARCHAR2,
    26- P_MUNICIPIO        IN VARCHAR2,
    27- P_VARIABLESADI        IN VARCHAR2,
    28- P_COMBO IN NUMBER, // nicaragua es varchar2
    29- P_GIRONEGOCIO IN VARCHAR2, // nicaragua no tiene este campo
    30- P_TELEFONO_FINANCIA IN VARCHAR2,
    31- P_CONTRATO_FINANCIA IN VARCHAR2,
    * */
}
