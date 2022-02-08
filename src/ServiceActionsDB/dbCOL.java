package ServiceActionsDB;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;

public class dbCOL implements Serializable {
  @XmlAttribute(name = "id")
  public int id = 0;

  @XmlAttribute(name = "name")
  public String name = "";

  @XmlAttribute(name = "colname")
  public String colname = "";

  @XmlAttribute(name = "type")
  public int type = 0;

  @XmlAttribute(name = "typename")
  public String typename = "";

  @XmlAttribute(name = "scale")
  public int scale = 0;

  @XmlAttribute(name = "precision")
  public int precision = 0;

  @XmlAttribute(name = "value_string")
  public String value_string = "";

  @XmlAttribute(name = "value_int")
  public int value_int = 0;

  @XmlAttribute(name = "value_double")
  public double value_double = 0.0D;

  @XmlAttribute(name = "value_bigdecimal")
  public BigDecimal value_bigdecimal = new BigDecimal(0);


  public Object value = null;
}
