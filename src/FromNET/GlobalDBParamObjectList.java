package FromNET;

public class GlobalDBParamObjectList
{
  GlobalDBParamObject[] vParams = new GlobalDBParamObject[100];
  int vItems = 0;
  
  public GlobalDBParamObjectList() {
    for (int i = 0; i < 100; i++) {
      this.vParams[i] = new GlobalDBParamObject();
    }
  }
  
  public void Add(GlobalDBParamObject vParam) {
    this.vItems++;
    this.vParams[this.vItems - 1] = vParam;
  }

  
  public GlobalDBParamObject get(int index) { return this.vParams[index]; }


  
  public int Count() { return this.vItems; }
}
