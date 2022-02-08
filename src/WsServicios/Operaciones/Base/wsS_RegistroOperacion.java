package WsServicios.Operaciones.Base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

//@XmlRootElement
public class wsS_RegistroOperacion implements Serializable{
    //@XmlElement(nillable=false,required = true)
    public String Cliente_Contrato = "";

    //@XmlElement(nillable=false,required = true)
    public String Telefono = "";

    //@XmlElement(nillable=true,required = false)
    public String IDDocumento = "";

    //@XmlElement(nillable=true,required = false)
    public String NoDocumento = "";

    //@XmlElement(nillable=true,required = false)
    public String Referencia = "";

    //@XmlElement(nillable=false,required = true)
    public wsTipoDocumentos.wsTipoDocumento TipoDocumento = null;

    //@XmlElement(nillable=true,required = false)
    public String NPE = "";

    //@XmlElement(nillable=true,required = true)
    public double MontoPago = 0.0D;
}
