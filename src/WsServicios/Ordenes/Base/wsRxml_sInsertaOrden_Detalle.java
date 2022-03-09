package WsServicios.Ordenes.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

// detalle de ordenes

@XmlRootElement
public class wsRxml_sInsertaOrden_Detalle implements Serializable {
    @XmlElement
    public int cantidad;
    @XmlElement
    public String codigoDatos;
    @XmlElement
    public String codigoPlan;
    @XmlElement
    public long contrato;
    @XmlElement
    public long correlativoVenta;
    @XmlElement
    public String jerarquiaProducto;
    @XmlElement
    public double descuentoEspecial;
    @XmlElement
    public double descuentoProducto;
    @XmlElement
    public String imei;
    @XmlElement
    public String nivel;
    @XmlElement
    public String plazo;
    @XmlElement
    public double precioProducto;
    @XmlElement
    public String sim;
    @XmlElement
    public double precioSim;
    @XmlElement
    public String skuSim;
    @XmlElement
    public String sku;
    @XmlElement
    public String solicitudBilling;
    @XmlElement
    public String telefono;
    @XmlElement
    public String tipoDescuentoEspecial;
    @XmlElement
    public String almacen;
    @XmlElement
    public String codAgencia;
    @XmlElement
    public String codigoItem;
    @XmlElement
    public double prima;
    @XmlElement
    public double montoFinanciado;
    @XmlElement
    public int combo;

    /*

1-P_CANTIDAD              IN NUMBER,
2-P_CODIGODATOS           IN VARCHAR2,
3-P_CODIGOPLAN            IN VARCHAR2,
4-P_CONTRATO              IN NUMBER,
5-P_CORRELATIVOVENTA      IN NUMBER,
6-P_JERARQUIA_PRODUCTOS   IN VARCHAR2,
7-P_DESCUENTOESPEC        IN NUMBER,
8-P_DESCUENTOPROD         IN NUMBER,
9-P_IMEI                  IN VARCHAR2,
10-P_NIVEL                 IN VARCHAR2,
11-P_PLAZO                 IN VARCHAR2,
12-P_PRECIOPRODUCTO        IN NUMBER,
13-P_SIM                   IN VARCHAR2,
14-P_SIMPRICE              IN NUMBER,
15-P_SIM_SKU               IN VARCHAR2,
16-P_SKU                   IN VARCHAR2,
17-P_SOLICITUDBILLING      IN VARCHAR2,
18-P_TELEFONO              IN VARCHAR2,
19-P_TIPODESCESPE          IN VARCHAR2,
20-P_ALMACEN               IN VARCHAR2,
21-P_AGENCIA               IN VARCHAR2, // todos menos sv
22-P_CODIGO_ITEM VARCHAR2,
23-P_PRIMA         IN NUMBER,
24-P_MONTO_FINAN   IN NUMBER,
25-P_COMBO                 IN VARCHAR2, // pa int, gt, hn

     */
}
