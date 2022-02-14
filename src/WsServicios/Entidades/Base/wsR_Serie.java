package WsServicios.Entidades.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsR_Serie implements Serializable {

    @XmlElement
    public String codAgencia;
    @XmlElement
    public String nomAgencia;
    @XmlElement
    public String estatus;
    @XmlElement
    public String codCajero;
    @XmlElement
    public String orden;
    @XmlElement
    public long idVenta;
    @XmlElement
    public String sku;
    @XmlElement
    public String idSinergia;
    @XmlElement
    public String producto;
    @XmlElement
    public String almacen;
    @XmlElement
    public String serie;

/*
IDAGENCIA	1	VARCHAR2 (10 Byte)
AGENCIA	2	VARCHAR2 (50 Byte)
ESTATUS	3	VARCHAR2 (2 Byte)
CAJERO	4	VARCHAR2 (30 Byte)
ORDENCRM	5	VARCHAR2 (100 Byte)
IDVENTA	6	NUMBER (10)
SKU	7	VARCHAR2 (25 Byte)
IDSINERGIA	8	VARCHAR2 (30 Byte)
PRODUCTO	9	VARCHAR2 (150 Byte)
ALMACEN	10	VARCHAR2 (20 Byte)
SERIE	11	VARCHAR2 (50 Byte)
*/

}
