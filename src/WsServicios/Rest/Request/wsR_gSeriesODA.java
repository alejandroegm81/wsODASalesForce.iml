package WsServicios.Rest.Request;

import WsServicios.Bases.wsInstancias;
import WsServicios.Entidades.Request.wsRxml_gSeriesODA;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Schema
public class wsR_gSeriesODA {

    @XmlElement
    @Parameter
    public  wsInstancias.wsInstancia Instancia;

    @XmlElement
    @Parameter
    public wsRxml_gSeriesODA Parametros;

}
