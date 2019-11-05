
package ch.makery.controlador.util;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adaptador  JAXB para la conversión de LocalDate y de  ISO 8601 
 * Quedará una mascara de fecha '2012-12-03'.
 */
public class AdaptadorFecha extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}

