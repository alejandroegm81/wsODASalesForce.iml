package WsServicios.Bases;

import FromNET.ConnectTo;
import WsServicios.Operaciones.Base.*;

public class wGenInstanceFuntions {

    public String getCountryCode(wsInstancias.wsInstancia Instancia){
        String vCode="";
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_502 )){ vCode="502"; }
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_503 )){ vCode="503"; }
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_504 )){ vCode="504"; }
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_505 )){ vCode="505"; }
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_506 )){ vCode="506"; }
        if(Instancia.equals( wsInstancias.wsInstancia.ODA_507 )){ vCode="507"; }
        return vCode;
    }
    public ConnectTo getConnectTo(wsInstancias.wsInstancia vInstancia) throws Exception {
        if (vInstancia == null) throw new Exception("La Instancia es invalida. Debe de ser ODA_{pais}");
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_502)) return ConnectTo.ODA_502_db;
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_503)) return ConnectTo.ODA_503_db;
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_504)) return ConnectTo.ODA_504_db;
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_505)) return ConnectTo.ODA_505_db;
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_506)) return ConnectTo.ODA_506_db;
        if (vInstancia.equals(wsInstancias.wsInstancia.ODA_507)) return ConnectTo.ODA_507_db;
        throw new Exception("La Instancia es invalida. Debe de ser ODA_{pais}");
    }

    public String GetTipPagTran(wsInstancias.wsInstancia Instancia, wsTipoFormaPagos.wsTipoFormaPago TipoFormaPago){
        String vTipPagTran="E"; int Country= Integer.parseInt(getCountryCode(Instancia));
        switch ( Country ){
            case 502:
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.EFECTIVO )){vTipPagTran="E";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.TARJETA )){vTipPagTran="T";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.CHEQUE )){vTipPagTran="C";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.REMESA )){vTipPagTran="E";}
                break;
            case 503:
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.EFECTIVO )){vTipPagTran="E";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.TARJETA )){vTipPagTran="T";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.CHEQUE )){vTipPagTran="C";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.REMESA )){vTipPagTran="E";}
                break;
            case 504:
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.EFECTIVO )){vTipPagTran="E";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.TARJETA )){vTipPagTran="T";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.CHEQUE )){vTipPagTran="C";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.REMESA )){vTipPagTran="E";}
                break;
            case 505:
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.EFECTIVO )){vTipPagTran="E";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.TARJETA )){vTipPagTran="T";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.CHEQUE )){vTipPagTran="C";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.REMESA )){vTipPagTran="R";}
                break;
            case 506:
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.EFECTIVO )){vTipPagTran="E";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.TARJETA )){vTipPagTran="T";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.CHEQUE )){vTipPagTran="C";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.REMESA )){vTipPagTran="E";}
                break;
            case 507:
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.EFECTIVO )){vTipPagTran="E";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.TARJETA )){vTipPagTran="T";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.CHEQUE )){vTipPagTran="C";}
                if(TipoFormaPago.equals( wsTipoFormaPagos.wsTipoFormaPago.REMESA )){vTipPagTran="R";}
                break;
        }
        return vTipPagTran;
    }


    public String GetCodOperador(wsInstancias.wsInstancia Instancia,wsOperacionServicios.wsOperacionServicio OperacionServicio, Boolean WithoutFact ){
        String vCodOperador="998"; int Country= Integer.parseInt(getCountryCode(Instancia));
        switch ( Country ){
            case 502:
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL )){ vCodOperador= (WithoutFact)?"996":"998";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.FIJO )){ vCodOperador= (WithoutFact)?"557":"555";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.REGISTRO_VENTA )){ vCodOperador= "50";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.OPERACIONES_VARIAS )){ vCodOperador= "100";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.PAQUETE )){ vCodOperador= "50";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.RECARGA )){ vCodOperador= "50";}
                break;
            case 503:
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL )){ vCodOperador= (WithoutFact)?"996":"998";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.FIJO )){ vCodOperador= (WithoutFact)?"305":"555";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.REGISTRO_VENTA )){ vCodOperador= "50";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.OPERACIONES_VARIAS )){ vCodOperador= "100";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.PAQUETE )){ vCodOperador= "50";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.RECARGA )){ vCodOperador= "50";}
                break;
            case 504:
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL )){ vCodOperador= (WithoutFact)?"996":"998";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.FIJO )){ vCodOperador= (WithoutFact)?"576":"642";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.REGISTRO_VENTA )){ vCodOperador= "50";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.OPERACIONES_VARIAS )){ vCodOperador= "100";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.PAQUETE )){ vCodOperador= "50";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.RECARGA )){ vCodOperador= "50";}
                break;
            case 505:
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL )){ vCodOperador= (WithoutFact)?"908":"995";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.FIJO )){ vCodOperador= (WithoutFact)?"576":"556";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.REGISTRO_VENTA )){ vCodOperador= "50";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.OPERACIONES_VARIAS )){ vCodOperador= "100";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.PAQUETE )){ vCodOperador= "50";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.RECARGA )){ vCodOperador= "50";}
                break;
            case 506:
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL )){ vCodOperador= (WithoutFact)?"908":"995";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.FIJO )){ vCodOperador= (WithoutFact)?"576":"556";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.REGISTRO_VENTA )){ vCodOperador= "50";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.OPERACIONES_VARIAS )){ vCodOperador= "100";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.PAQUETE )){ vCodOperador= "50";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.RECARGA )){ vCodOperador= "50";}
                break;
            case 507:
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.MOVIL )){ vCodOperador= (WithoutFact)?"231":"231";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.FIJO )){ vCodOperador= (WithoutFact)?"230":"230";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.REGISTRO_VENTA )){ vCodOperador= "130";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.OPERACIONES_VARIAS )){ vCodOperador= "130";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.PAQUETE )){ vCodOperador= "130";}
                if(OperacionServicio.equals( wsOperacionServicios.wsOperacionServicio.RECARGA )){ vCodOperador= "130";}
                break;
        }
        return vCodOperador;
    }

    public String GetTipoMoneda(wsInstancias.wsInstancia Instancia , wsS_RegistroFormaPago FormaPago ){
        String vTipoMoneda="1"; int Country= Integer.parseInt(getCountryCode(Instancia));
        switch ( Country ){
            case 502:
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.LOCAL) ){vTipoMoneda="1";}
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.DOLAR) ){vTipoMoneda="2";}
                break;
            case 503:
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.LOCAL) ){vTipoMoneda="2";}
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.DOLAR) ){vTipoMoneda="2";}
                break;
            case 504:
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.LOCAL) ){vTipoMoneda="1";}
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.DOLAR) ){vTipoMoneda="2";}
                break;
            case 505:
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.LOCAL) ){vTipoMoneda="1";}
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.DOLAR) ){vTipoMoneda="2";}
                break;
            case 506:
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.LOCAL) ){vTipoMoneda="1";}
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.DOLAR) ){vTipoMoneda="2";}
                break;
            case 507:
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.LOCAL) ){vTipoMoneda="1";}
                if( FormaPago.Moneda.equals(wsMonedas.wsMoneda.DOLAR) ){vTipoMoneda="2";}
                break;
        }
        return vTipoMoneda;
    }

}
