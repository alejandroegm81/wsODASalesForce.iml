package WsServicios.Rest;

import WsServicios.Bases.wsInstancias;
import WsServicios.Bases.wsTipoAccionManttos;
import WsServicios.Entidades.Base.wsR_Banco;
import WsServicios.Entidades.Base.wsR_Caja;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsEntidades_sBancos {

    @XmlElement
    public  wsInstancias.wsInstancia Instancia;

    @XmlElement
    public wsR_Banco vDatosBanco;

    @XmlElement
    public wsTipoAccionManttos.wsTipoAccionMantto vTipoAccion;


}
