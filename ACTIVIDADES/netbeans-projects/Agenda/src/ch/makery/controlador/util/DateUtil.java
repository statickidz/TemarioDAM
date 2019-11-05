package ch.makery.controlador.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class DateUtil {
    /** Patrón de fecha usado para esta conversión. Puedes cambiarlo a tu gusto. */
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    /** Formato de la fecha. */
    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Devuelve el valor de la fecha con el formato de string definido en la parte superior.  
     * {@link DateUtil#DATE_PATTERN} es el utilizado.
     * @ el parámetro fecha lo convertirá  a tipo string
     * @ lo devuelve en formato string
     */
public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Corvertir un string al formato definido {@link DateUtil#DATE_PATTERN} 
     * a un objeto fecha {@link LocalDate}.
     * 
     * Devolverá null si el string no puede ser convertido.
     * 
     * @param dateString la fecha con formato string
     * @devuelve el objeto fecha o nulo si o puede convertirlo
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Chequeo de si el string es una fecha válida.
     * 
     * @parametro  dateString
     * @devuelve true si el string es una fecha válida
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }

}
