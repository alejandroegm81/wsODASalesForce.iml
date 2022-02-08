package FromNET;

import ServiceActionsDB.dbCOL;
import ServiceActionsDB.dbDATA;
import ServiceActionsDB.dbROW;
import db.BaseClass;
import java.math.BigDecimal;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import oracle.jdbc.OracleConnection;



public class GlobalDB
  extends BaseClass
{
  private int getTransactionNumber() { return 1; }


  
  public dbDATA RsToRows(CachedRowSet Rs) {
    dbDATA data = new dbDATA();
    
    try { if (Rs != null) {
        ResultSetMetaData RsMD = Rs.getMetaData();
        dbROW rowTitle = new dbROW();
        if (RsMD.getColumnCount() > 0) {
          for (int iCol = 1; iCol <= RsMD.getColumnCount(); iCol++) {
            dbCOL col = new dbCOL();
            col.id = iCol;
            col.name = RsMD.getColumnName(iCol);
            col.precision = RsMD.getPrecision(iCol);
            col.scale = RsMD.getScale(iCol);
            col.type = RsMD.getColumnType(iCol);
            col.typename = RsMD.getColumnTypeName(iCol);
            rowTitle.cols.add(col);
          } 
        }
        data.rows.add(rowTitle);
        while (Rs.next()) {
          dbROW row = new dbROW();
          for (int iCol = 1; iCol <= RsMD.getColumnCount(); iCol++) {
            dbCOL col = new dbCOL();
            col.id = iCol;
            col.name = RsMD.getColumnName(iCol);
            col.colname = RsMD.getColumnName(iCol).toUpperCase();
            col.precision = RsMD.getPrecision(iCol);
            col.scale = RsMD.getScale(iCol);
            col.type = RsMD.getColumnType(iCol);
            col.typename = RsMD.getColumnTypeName(iCol);
            col.value = Rs.getObject(iCol);
            col.value_string = (Rs.getString(iCol)==null)?"":Rs.getString(iCol);
            try {
              col.value_int = Rs.getInt(iCol);
            } catch (Exception exception) {}
            try {
              col.value_double = Rs.getDouble(iCol);
            } catch (Exception exception) {}
            try {
              col.value_bigdecimal = Rs.getBigDecimal(iCol);
            } catch (Exception exception) {}

            row.cols.add(col);
          } 
          data.rows.add(row);
        } 
      }  }
    catch (Exception e) { setErrMsg(e); }
     return data;
  }

















  
  public SubDataTable getQuery(String vQuery, ConnectTo vConnecTo) {
    SubDataTable dt = new SubDataTable();
    int vSeqExecution = getTransactionNumber();
    OracleConnection Con = null;
    Statement st = null;
    ResultSet Rs = null;
    
    try { Con = getODADBConnection(vConnecTo);
      p("getQuery+" + vConnecTo.toString() + "Query:" + vQuery);
      st = Con.createStatement(1004, 1007);
      Rs = st.executeQuery(vQuery);
      
      RowSetFactory rowSetFactory = RowSetProvider.newFactory();
      CachedRowSet crs = rowSetFactory.createCachedRowSet();
      crs.populate(Rs, 1);
      
      dt.Assign(Boolean.valueOf(true), RsToRows(crs));
      p("getQuery-" + vConnecTo.toString() + "Query:" + vQuery); }
    catch (Exception e) { setErrMsg(e); dt.vDBMessage = e.getMessage(); }
    finally { 
      try { st.close(); } catch (Exception exception) {} 
      try { Rs.close(); } catch (Exception exception) {} 
      try { Con.close(); } catch (Exception exception) {} }
    
    return dt;
  }

  
  public SubDataTable getQuery(String vQuery, GlobalDBParamObjectList vParams, ConnectTo vConnecTo) {
    SubDataTable dt = new SubDataTable();
    int vSeqExecution = getTransactionNumber();
    OracleConnection Con = null;
    PreparedStatement st = null;
    ResultSet Rs = null;

    
    try { Con = getODADBConnection(vConnecTo);
      p("getQuery+" + vConnecTo.toString() + "Query:" + vQuery);


      
      st = Con.prepareStatement(vQuery, 1004, 1007);
      ParameterMetaData pm = st.getParameterMetaData();
      for (int k = 0; k < pm.getParameterCount(); k++) {
        if (k < vParams.Count())
          switch (pm.getParameterType(k + 1)) { case -16: case -1: case 1: case 12:
              st.setString(k + 1, (vParams.get(k)).value.toString()); break;
            case 2: case 3: st.setBigDecimal(k + 1, BigDecimal.valueOf(Long.valueOf((vParams.get(k)).value.toString()).longValue())); break;
            case -7: st.setBoolean(k + 1, Boolean.valueOf((vParams.get(k)).value.toString()).booleanValue()); break;
            case -6: st.setByte(k + 1, Byte.valueOf((vParams.get(k)).value.toString()).byteValue()); break;
            case 5: st.setShort(k + 1, Short.valueOf((vParams.get(k)).value.toString()).shortValue()); break;
            case 4: st.setInt(k + 1, Integer.valueOf((vParams.get(k)).value.toString()).intValue()); break;
            case -5: st.setLong(k + 1, Long.valueOf((vParams.get(k)).value.toString()).longValue()); break;
            case 7: st.setFloat(k + 1, Float.valueOf((vParams.get(k)).value.toString()).floatValue()); break;
            case 6: case 8: st.setDouble(k + 1, Double.valueOf((vParams.get(k)).value.toString()).doubleValue());
              break; }
           
      } 
      Rs = st.executeQuery();

      
      RowSetFactory rowSetFactory = RowSetProvider.newFactory();
      CachedRowSet crs = rowSetFactory.createCachedRowSet();
      crs.populate(Rs, 1);
      
      dt.Assign(Boolean.valueOf(true), RsToRows(crs));
      
      p("getQuery-" + vConnecTo.toString() + "Query:" + vQuery); }
    catch (Exception e) { setErrMsg(e); dt.vDBMessage = e.getMessage(); }
    finally { 
      try { st.close(); } catch (Exception exception) {} 
      try { Rs.close(); } catch (Exception exception) {} 
      try { Con.close(); } catch (Exception exception) {} }
    
    return dt;
  }



  
  public SubDataTable getQuery(String vQuery, OracleConnection ORACon) {
    SubDataTable dt = new SubDataTable();
    int vSeqExecution = getTransactionNumber();
    Statement st = null;
    ResultSet Rs = null;
    
    try { st = ORACon.createStatement(1004, 1007);
      p("getQuery>>ORACon>>" + "Query:" + vQuery);

      Rs = st.executeQuery(vQuery);
      
      RowSetFactory rowSetFactory = RowSetProvider.newFactory();
      CachedRowSet crs = rowSetFactory.createCachedRowSet();
      crs.populate(Rs);
      
      dt.Assign(Boolean.valueOf(true), RsToRows(crs)); }
    catch (Exception e) { setErrMsg(e); dt.vDBMessage = e.getMessage(); }
    finally { 
      try { st.close(); } catch (Exception exception) {} 
      try { Rs.close(); } catch (Exception exception) {} }
    
    return dt;
  }
  
  public SubDataTable setQuery(PreparedStatement prep) {
    SubDataTable dt = new SubDataTable();
    int vSeqExecution = getTransactionNumber();

    try{
      p("setQuery>>PreparedStatement:" +  prep.toString() );
    } catch(Exception eQ){}

    try {
      try { int res = prep.executeUpdate();
        dbDATA dData = new dbDATA();
        dbROW dRow = new dbROW();
        dbCOL dCol = new dbCOL();
        dCol.name = "response"; dCol.colname = "response"; dCol.value = Integer.valueOf(res); dCol.value_string = String.valueOf(res);
        dRow.cols.add(dCol);
        dData.rows.add(dRow);
        dt.Assign(Boolean.valueOf(true), dData); }
      catch (Exception ex) { setErrMsg(ex); dt.vDBMessage = ex.getMessage(); }
      //finally {
       // try { prep.close(); } catch (Exception exception) {} }
       }
    catch (Exception e) { setErrMsg(e); dt.vDBMessage = e.getMessage(); }
     return dt;
  }



  
  public SubDataTable setQuery(String vQuery, GlobalDBParamObjectList vParams, ConnectTo vConnecTo) {
    SubDataTable dt = new SubDataTable();
    int vSeqExecution = getTransactionNumber(); int vParameterType=-1;
    OracleConnection Con = null;
    PreparedStatement st = null;
    ResultSet Rs = null;
    
    try { 
      try { Con = getODADBConnection(vConnecTo);
        p("setQuery+" + vConnecTo.toString() + "Query:" + vQuery);
        
        st = Con.prepareStatement(vQuery, 1004, 1007);
        ParameterMetaData pm = st.getParameterMetaData();

        for (int k = 0; k < pm.getParameterCount(); k++) {
          if (k < vParams.Count()) {
            p(String.valueOf(k) + (vParams.get(k)).name);
            try{
              vParameterType=pm.getParameterType(k + 1);
            }catch(Exception ePP){p("Error:GetParameterType:"+ePP.getMessage()); vParameterType=-1; }

              switch (vParameterType) {
                case -16: case -1: case 1: case 12:
                  p(">>STRING"); st.setString(k + 1, (vParams.get(k)).value.toString()); break;
                case 2: case 3: st.setBigDecimal(k + 1, BigDecimal.valueOf(Long.valueOf((vParams.get(k)).value.toString()).longValue())); break;
                case -7: st.setBoolean(k + 1, Boolean.valueOf((vParams.get(k)).value.toString()).booleanValue()); break;
                case -6: st.setByte(k + 1, Byte.valueOf((vParams.get(k)).value.toString()).byteValue()); break;
                case 5: st.setShort(k + 1, Short.valueOf((vParams.get(k)).value.toString()).shortValue()); break;
                case 4: st.setInt(k + 1, Integer.valueOf((vParams.get(k)).value.toString()).intValue()); break;
                case -5: st.setLong(k + 1, Long.valueOf((vParams.get(k)).value.toString()).longValue()); break;
                case 7: st.setFloat(k + 1, Float.valueOf((vParams.get(k)).value.toString()).floatValue()); break;
                case 6: case 8: st.setDouble(k + 1, Double.valueOf((vParams.get(k)).value.toString()).doubleValue()); break;
                case 91: case 92: case 93: p(">>DATE");
                  st.setString(k + 1, (vParams.get(k)).value.toString());
                  break;
              }

          }
        } 
        int res = st.executeUpdate();
        dbDATA dData = new dbDATA();
        dbROW dRow = new dbROW();
        dbCOL dCol = new dbCOL();
        dCol.name = "response"; dCol.colname = "response"; dCol.value = Integer.valueOf(res); dCol.value_string = String.valueOf(res);
        dRow.cols.add(dCol);
        dData.rows.add(dRow);
        dt.Assign(Boolean.valueOf(true), dData); }
      catch (Exception ex) { setErrMsg(ex); dt.vDBMessage = ex.getMessage(); }
      finally { 
        try { st.close(); } catch (Exception exception) {} 
        try { Con.close(); } catch (Exception exception) {} }
       }
    catch (Exception e) { setErrMsg(e); dt.vDBMessage = e.getMessage(); }
     return dt;
  }



  
  public SubDataTable getQuery(PreparedStatement prep) {
    SubDataTable dt = new SubDataTable();
    int vSeqExecution = getTransactionNumber();
    
    try { 
      try { boolean res = prep.execute();
        dbDATA dData = new dbDATA();
        dbROW dRow = new dbROW();
        dbCOL dCol = new dbCOL();
        dCol.name = "response"; dCol.colname = "response"; dCol.value = Boolean.valueOf(res); dCol.value_string = String.valueOf(res);
        dRow.cols.add(dCol);
        dData.rows.add(dRow);
        dt.Assign(Boolean.valueOf(res), dData); }
      catch (Exception ex) { setErrMsg(ex); dt.vDBMessage = ex.getMessage(); }
      finally { 
        try { prep.close(); } catch (Exception exception) {} }
       }
    catch (Exception e) { setErrMsg(e); dt.vDBMessage = e.getMessage(); }
     return dt;
  }



  
  void p(String t) { System.out.println(t); }


  
  public OracleConnection getODADBConnection(ConnectTo vConnecTo) throws Exception {
    Context context = new InitialContext();
    DataSource dsDB = null;
    if (vConnecTo == ConnectTo.ODA_502_db) dsDB = (DataSource)context.lookup("ODA_502"); 
    if (vConnecTo == ConnectTo.ODA_503_db) dsDB = (DataSource)context.lookup("ODA_503"); 
    if (vConnecTo == ConnectTo.ODA_504_db) dsDB = (DataSource)context.lookup("ODA_504"); 
    if (vConnecTo == ConnectTo.ODA_505_db) dsDB = (DataSource)context.lookup("ODA_505"); 
    if (vConnecTo == ConnectTo.ODA_506_db) dsDB = (DataSource)context.lookup("ODA_506"); 
    if (vConnecTo == ConnectTo.ODA_507_db) dsDB = (DataSource)context.lookup("ODA_507");
    if (vConnecTo == ConnectTo.ODA_507_BSCS_db) dsDB = (DataSource)context.lookup("ODA_507_BSCS");
    if (vConnecTo == ConnectTo.ODA_507_OPEN_db) dsDB = (DataSource)context.lookup("ODA_507_OPEN");
    if (vConnecTo == ConnectTo.ODA_507_BSCSFU_db) dsDB = (DataSource)context.lookup("ODA_507_BSCSFU");

    if (vConnecTo == ConnectTo.BSCS_db) dsDB = (DataSource)context.lookup("PPBSCS"); 
    if (vConnecTo == ConnectTo.OPEN_db) dsDB = (DataSource)context.lookup("OPEN_PAN_PROD"); 
    return (OracleConnection)dsDB.getConnection();
  }
}
