package WsServicios.Rest;

import WsServicios.Bases.wsInstancias;
import WsServicios.Bases.wsTipoAccionManttos;
import WsServicios.Entidades.Base.wsR_Agencia;
import WsServicios.Entidades.Base.wsR_Cajero;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class wsEntidades_sCajeros {

    @XmlElement
    public  wsInstancias.wsInstancia Instancia;

    @XmlElement
    public wsR_Cajero vDatosCajero;

    @XmlElement
    public wsTipoAccionManttos.wsTipoAccionMantto vTipoAccion;



}
