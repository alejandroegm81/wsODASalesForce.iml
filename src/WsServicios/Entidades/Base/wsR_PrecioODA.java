package WsServicios.Entidades.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsR_PrecioODA implements Serializable {
    @XmlElement
    public String mandante;
    @XmlElement
    public String combinacionClave;
    @XmlElement
    public String organizacionVenta;
    @XmlElement
    public String claseCondicion;
    @XmlElement
    public String motivoPedido;
    @XmlElement
    public String grupoCondicionesCliente;
    @XmlElement
    public String grupoComisiones;
    @XmlElement
    public String descripcion;
    @XmlElement
    public String material;
    @XmlElement
    public String validoDe;
    @XmlElement
    public String validoA;
    @XmlElement
    public String desMaterial;
    @XmlElement
    public double valor;
    @XmlElement
    public String condicionMoneda;
    @XmlElement
    public long nivelODA;


/*
MANDANTE	        1	CHAR (3 Byte)
COMBINACION_CLAVE	2	CHAR (22 Byte)
ORGANIZACION_VENTA	3	CHAR (4 Byte)
CLASE_CONDICION	    4	CHAR (4 Byte)
MOTIVO_PEDIDO	    5	VARCHAR2 (10 Byte)
GRUPO_CONDICIONES_CLTE	6	VARCHAR2 (10 Byte)
GRUPO_COMISIONES	    7	VARCHAR2 (10 Byte)
DESCRIPCION	            8	VARCHAR2 (100 Byte)
MATERIAL	            9	VARCHAR2 (25 Byte)
VALIDE_DE	            10	VARCHAR2 (8 Byte)
VALIDO_DE	            11	VARCHAR2 (8 Byte)
VALIDO_A	            12	VARCHAR2 (8 Byte)
DES_MATERIAL	        13	VARCHAR2 (150 Byte)
VALOR	                14	NUMBER (10,2)
CONDICION_MONEDA	    15	CHAR (3 Byte)
NIVEL_ODA	            16	NUMBER (10)

 */
}
