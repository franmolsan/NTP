import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Clase para visualizar rutas
 */
public class Visualizador {
   /**
    * Dato miembro para almacenar los datos de la ruta
    */
   private XYSeriesCollection coordenadas;

   /**
    * Dato miembro para el titulo
    */
   private String titulo;

   /**
    * Constructor de la clase, recibiendo como argumento
    * una ruta
    */
   public Visualizador(String nombre, Ruta ruta) {
      // se crea la serie de datos
      coordenadas = new XYSeriesCollection();
      
      // se copia el titulo
      titulo=nombre;
      
      // se crea la serie de datos de la coleccion
      XYSeries datosRuta = new XYSeries("ruta", false);
      
      // se obtienen las coordenadas de las ciudades de la ruta
      ArrayList<ArrayList<Double>> puntos = ruta.obtenerCoordenadas_funcional();
      
      // se agregan los puntos a la serie
      for(int i=0; i < ruta.obtenerLongitud(); i++){
         datosRuta.add(puntos.get(0).get(i), puntos.get(1).get(i));
      }
      
      // se agrega la serie al conjunto global de coordenadas a 
      // pintar
      coordenadas.addSeries(datosRuta);
      
      // se muestra el grafico
      mostrar();
   }

   /**
    * Constructor de la clase, recibiendo como argumento
    * dos rutas
    */
   public Visualizador(String nombre, Ruta ruta1, Ruta ruta2) {
      // se crea la serie de datos
      coordenadas = new XYSeriesCollection();

      // se copia el titulo
      titulo=nombre;

      // se crea la serie de datos de la coleccion
      generarSerie("ruta1", ruta1);
      generarSerie("ruta2", ruta2);

      // se muestra el grafico
      mostrar();
   }

   /**
    * Metodo privado para crear la ventana con el grafico
    */
   public void mostrar() {
      final JFreeChart chart = crearGrafico(coordenadas);
      final ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setPreferredSize(new java.awt.Dimension(1000, 1000));
      final ApplicationFrame frame = new ApplicationFrame("Mapa TSP");
      frame.setContentPane(chartPanel);
      frame.pack();
      frame.setVisible(true);
   }

   private void generarSerie(String nombre, Ruta ruta){
      // se crea la serie de datos de la coleccion
      XYSeries datosRuta = new XYSeries(nombre, false);

      // se obtienen las coordenadas de las ciudades de la ruta
      ArrayList<ArrayList<Double>> puntos = ruta.obtenerCoordenadas_funcional();

      // se agregan los puntos a la serie
      for(int i=0; i < ruta.obtenerLongitud(); i++){
         datosRuta.add(puntos.get(0).get(i), puntos.get(1).get(i));
      }

      // se agrega la serie al conjunto global de coordenadas a
      // pintar
      coordenadas.addSeries(datosRuta);
   }

   /**
    * Metodo de creacion del panel del grafico
    * @param dataset
    * @return
    */
   private JFreeChart crearGrafico(final XYDataset dataset) {
      final JFreeChart chart = ChartFactory.createScatterPlot(
              titulo,                  // chart title
              "X",                      // x axis label
              "Y",                      // y axis label
              dataset,                  // data
              PlotOrientation.VERTICAL,
              true,                     // include legend
              true,                     // tooltips
              false                     // urls
      );
      XYPlot plot = (XYPlot) chart.getPlot();
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
      renderer.setSeriesLinesVisible(0, true);
      // se incrementa el grosor de la linea
      renderer.setSeriesStroke(0, new BasicStroke(2.0f));

      // se cambia la forma de los puntos a circulos
      Shape shape  = new Ellipse2D.Double(-2.5,-2.5,5,5);
      //renderer.setBaseShape(shape);
      renderer.setSeriesPaint(0, Color.blue);
      renderer.setSeriesShape(0, shape);

      plot.setRenderer(renderer);
      return chart;
   }
}