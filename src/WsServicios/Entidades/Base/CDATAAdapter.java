package WsServicios.Entidades.Base;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CDATAAdapter extends XmlAdapter<String, String> {

    /*@Override
    public String marshal(String v) throws Exception {
        return "<![CDATA[" + v + "]]>";
    }

    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }*/


    public String unmarshal(String value) {
        return (CDataAdapter_.parse(value));
    }

    public String marshal(String value) {
        return (CDataAdapter_.print(value));
    }
}
