package WsServicios.OperacionesAutogestion.proceso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class wsR_InformacionClientes_Item implements Serializable {

    public String RazonSocial="";
    public String Identificacion="";
    public String NombrePlan="";
    public String FechaVencimiento="";
    public String FechaActivacion="";
    public String FechaRenovacionPlan="";
    public String EstadoPlan="";
    public double PrecioPlan=0.0;
    public String CodigoPlan="";
    public String NumeroContrato="";
    public String Ciclo="";
    public String VelocidadNavegacion="";
    public List<wsR_InformacionClietnes_Item_AddOn> Addons=new ArrayList<>();

}
