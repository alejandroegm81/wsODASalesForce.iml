package FromNET;


public class GlobalDBParamObject
{
  public String name;
  public Object value;
  
  public GlobalDBParamObject(String vName, Object vObject) { this.name = vName; this.value = vObject; }
  
  public Boolean isCursor;
  
  public GlobalDBParamObject() {}
}
