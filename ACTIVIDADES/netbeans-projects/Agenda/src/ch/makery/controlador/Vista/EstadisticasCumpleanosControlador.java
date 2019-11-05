package ch.makery.controlador.Vista;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import ch.makery.controlador.Modelo.Person;

/**
 * Controlador de la vista estadística de cumpleaños.
*/

public class EstadisticasCumpleanosControlador {
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    /**
     * Inicializa el controlador. El método es llamado     
     * una vez que haya sido cargada la vista fxml..
     */
    @FXML
    private void initialize() {
        // Carga el array con los nombres de mes.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convierte la lista y la añade a nuestra ObservableList de meses.
        monthNames.addAll(Arrays.asList(months));

        // Asigna los nombres de los meses como categorías en el eje de abcisas X.
        xAxis.setCategories(monthNames);
    }

    /**
     * Asigna las personas para mostrar la estadística.
     * @param persons
     */
    public void setPersonData(List<Person> persons) {
        // Cuenta el número de personas que cumplen los años en un determinado mes.
        int[] monthCounter = new int[12];
        for (Person p : persons) {
            int month = p.getBirthday().getMonthValue() - 1;
            monthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Crea el Objeto XYChart.Data de cada mes y añade las series.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }

        barChart.getData().add(series);
    }

}
