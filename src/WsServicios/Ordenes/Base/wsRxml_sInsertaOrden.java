package WsServicios.Ordenes.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class wsRxml_sInsertaOrden implements Serializable {
    @XmlElement
    public wsRxml_sInsertaOrden_Encabezado Encabezado;

    @XmlElement
    public List<wsRxml_sInsertaOrden_Detalle> Detalle = new ArrayList<>();

    @XmlElement
    public List<wsRxml_sInsertaOrden_Impuesto> Impuesto = new ArrayList<>();

}
