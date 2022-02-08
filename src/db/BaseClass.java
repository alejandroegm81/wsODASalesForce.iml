package db;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseClass
{
  private static String vErrMsg = "";
  
  public void setErr(String txt, Exception e) {
    System.out.println(getClass().getName() + ".SetErr: " + txt);
    e.printStackTrace();
  }

  
  public static void setInfo(String txt) { System.out.println("SetInfo: " + txt); }


  
  public void setInfo(String txt, String txt2) { System.out.println(getClass().getName() + "SetInfo: [" + txt + "]" + txt2); }





  
  public static String getErrMsg() { return vErrMsg; }

  
  public void clearErrMsg() { vErrMsg = ""; }
  
  public static void setErrMsg(Exception e) {
    e.printStackTrace();
    vErrMsg = e.getMessage();
    System.out.println(".SetErr: " + e.getMessage());
  }

  
  public Date getToday() {
    Date now = new Date();
    return now;
  }
  public String DateToString(Date vDateValue, String vDateFormat) {
    String vResult = "";

    
    try { DateFormat formatter = new SimpleDateFormat(vDateFormat);
      vResult = formatter.format(vDateValue); }
    catch (Exception e) { setErrMsg(e); }
     return vResult;
  }
  
  public Date StringToDate(String vDateValue, String vDateFormat) {
    Date dt = new Date();

    
    try { DateFormat formatter = new SimpleDateFormat(vDateFormat);
      dt = formatter.parse(vDateValue); }
    catch (Exception e) { setErrMsg(e); }
     return dt;
  }

  
  public boolean isValidDate(String inDate, String vFormat) {
    if (vFormat.length() <= 0) vFormat = "dd/MM/yyyy"; 
    if (inDate == null) {
      return false;
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat(vFormat);
    if (inDate.trim().length() != dateFormat.toPattern().length())
      return false; 
    dateFormat.setLenient(false);
    
    try {
      dateFormat.parse(inDate.trim());
    }
    catch (ParseException pe) {
      return false;
    } 
    return true;
  }
}
