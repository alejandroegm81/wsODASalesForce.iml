package ServiceActionsDB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class dbROW
        implements Serializable {
    public List<dbCOL> cols = new ArrayList<>();
    public dbCOL getColByName(String ColumnName) throws Exception {
        dbCOL col = new dbCOL();
        if (this.cols.size() > 0) {
            for (int iCol = 0; iCol < this.cols.size(); iCol++) {
                if (((dbCOL)this.cols.get(iCol)).colname.equals(ColumnName)) {
                    col = this.cols.get(iCol);
                    break;
                }
            }
        }
        return col;
    }
}
