package WsServiceActions;

import ServiceActionsDB.dbDATA;
import java.io.Serializable;
import java.util.List;

public class wsResponse
        implements Serializable {
    public Boolean vResult = Boolean.valueOf(false);
    public String vMessage = "";
    public int vRows = 0;
    public List<dbDATA> vData = null;
}
