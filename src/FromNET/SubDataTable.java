package FromNET;

import ServiceActionsDB.dbDATA;
import ServiceActionsDB.dbROW;
import db.BaseClass;
import java.io.Serializable;

public class SubDataTable
  extends BaseClass
  implements Serializable {
  public Boolean vData = Boolean.valueOf(false);
  public int vRows = 0;
  public dbDATA Datos = new dbDATA();
  
  public dbROW subDatos = new dbROW();
  
  public String vMessage = "";
  public String vDBMessage = "";
  public String vCorrelativoGroup = "";
  
  public SubDataTable() {}
  
  public SubDataTable(Boolean _Data, dbDATA _Datos) {
    try {
      this.Datos = _Datos;
      this.vData = _Data;
      this.vRows = this.Datos.rows.size();
    } catch (Exception e) {
      setErrMsg(e);
    } 
  }

  
  public void Assign(Boolean _Data, dbDATA _Datos) {
    try {
      this.Datos = _Datos;
      this.vData = _Data;
      this.vRows = this.Datos.rows.size();
    } catch (Exception e) {
      setErrMsg(e);
    } 
  }
}
