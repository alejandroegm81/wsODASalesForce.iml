package WsServicios.Ordenes.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
public class wsRxml_sInsertaOrden_Impuesto implements Serializable {

    @XmlElement
    public long correlativoVenta;
    @XmlElement
    public int idImpuestoExento;
    @XmlElement
    public String tipoComprobante;
    @XmlElement
    public String registroExento;
    @XmlElement
    public String actividad;
    @XmlElement
    public String documentoExento;
    @XmlElement
    public Date fechaEmision;
    @XmlElement
    public double porcentaje;

/* la clase no aparece para nicaragua . el salvador,

P_CORRELATIVOVENTA IN NUMBER,
P_ID_IMPUESTO_EXENTO IN NUMBER, -- identificador de impuesto en ODA.
P_TIPO_COMPROBANTE IN VARCHAR2,
P_REGISTRO_EXENTO IN VARCHAR2,
P_ACTIVIDAD IN VARCHAR2,
P_DOC_EXENTO IN VARCHAR2,
P_FEC_EMISION IN DATE ,
P_PORCENTAJE  IN NUMBER -- solo cr

 */

}
