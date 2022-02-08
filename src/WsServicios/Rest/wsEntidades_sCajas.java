package WsServicios.Rest;

import WsServicios.Bases.wsInstancias;
import WsServicios.Bases.wsTipoAccionManttos;
import WsServicios.Entidades.Base.wsR_Caja;
import WsServicios.Entidades.Base.wsR_Cajero;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class wsEntidades_sCajas implements Serializable {

    @XmlElement
    public  wsInstancias.wsInstancia Instancia;

    @XmlElement
    public wsR_Caja vDatosCaja;

    @XmlElement
    public wsTipoAccionManttos.wsTipoAccionMantto vTipoAccion;

}
