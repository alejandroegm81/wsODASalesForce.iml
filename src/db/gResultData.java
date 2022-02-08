package db;

import java.sql.ResultSet;
import java.util.Properties;



public class gResultData
{
  private ResultSet rs = null;
  private ResultSet rsDis = null;
  private boolean info = false;
  
  public ResultSet getRs() { return this.rs; }
  public void setRs(ResultSet r) { this.rs = r; }
  
  public boolean getInfo() { return this.info; }
  public void setInfo(boolean i) { this.info = i; }
  
  private Properties MailJobProperties = new Properties();
  public Properties getMailJobProperties() { return this.MailJobProperties; }
}
