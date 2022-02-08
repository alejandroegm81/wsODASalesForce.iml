package WsServicios.Rest;


import WsServicios.Bases.wsInstancias;
import WsServicios.Bases.wsTipoAccionManttos;
import WsServicios.Entidades.Base.wsR_Agencia;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsEntidades_sAgencias {
    @XmlElement
    public  wsInstancias.wsInstancia Instancia;

    @XmlElement
    public wsR_Agencia vDatosAgencia;

    @XmlElement
    public wsTipoAccionManttos.wsTipoAccionMantto vTipoAccion;


}
