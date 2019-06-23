/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmss_project;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.TextAnchor;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 *
 * @author omar shalaby
 */
public class Barchart extends ApplicationFrame {
     static String chartTitle = "Lowest Price for medicine";

    public Barchart(String title) {
        super(title);
    }
     
   

    public Barchart(String title, String[] names, double[] values){

        super(title);
        this.chartTitle = title;
        final CategoryDataset dataset = createDataset(names, values);
      final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 650));
           setContentPane(chartPanel); 

        try {

            saveToFile(chart, 900, 650, 100);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
        

    public static void saveToFile(JFreeChart chart, int width, int height, double quality)
            throws FileNotFoundException, IOException {
        BufferedImage img = draw(chart, width, height);

        File outputfile = new File(System.getProperty("user.dir") + "/" + chartTitle + "_savedChart.png");
        ImageIO.write(img, "png", outputfile);

    }

    protected static BufferedImage draw(JFreeChart chart, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));

        g2.dispose();
        return img;
    }

 /**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
    private CategoryDataset createDataset(String[] names, double[] values) {

        // row keys...
                final String series1 = "Transaction of Withdrow";
                final String series2 = "Transaction of deposit";
                final String series3 = "Transaction of transfer";
                

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < names.length; i++) {
            if(names[i].contains("withdrow")){
               String seprator[]=names[i].split("/");
            dataset.addValue(values[i], series1, seprator[0]);}
        else if (names[i].contains("deposit")){
               String seprator[]=names[i].split("/");
            dataset.addValue(values[i], series2, seprator[0]);}
                
        else{
               String seprator[]=names[i].split("/");
            dataset.addValue(values[i], series3, seprator[0]);}
                
        
        }
                
        
        return dataset;

    }

    /**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     *
     * @return The chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(chartTitle, // chart
                // title
                "Customer Name", // domain axis label
                "Transaction money", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        plot.getRenderer().setSeriesPaint(0, Color.green);
        plot.getRenderer().setSeriesPaint(1, Color.yellow);
        plot.getRenderer().setSeriesPaint(2, Color.red);
        //plot.getRenderer().setSeriesPaint( 3, Color.red);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperMargin(0.15);

        // disable bar outlines...
        final CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        Font f = new Font("TimesRoman", Font.BOLD, 14);
        Font f1 = new Font("TimesRoman", Font.PLAIN, 12);

        domainAxis.setLabelFont(f);
        domainAxis.setTickLabelFont(f1);

        rangeAxis.setLabelFont(f);

        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
                TextAnchor.HALF_ASCENT_CENTER);
        renderer.setBasePositiveItemLabelPosition(position);

        return chart;

    }
    
}
