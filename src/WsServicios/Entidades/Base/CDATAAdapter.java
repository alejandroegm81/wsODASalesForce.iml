package WsServicios.Entidades.Base;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CDATAAdapter extends XmlAdapter<String, String> {

    public String unmarshal(String value) {
        return (CDataAdapter_.parse(value));
    }

    public String marshal(String value) {
        return (CDataAdapter_.print(value));
    }
}
