package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javafx.scene.shape.Circle;
import objects.*;

public class MainGroceryMap extends DisplayMap
{
	public MainGroceryMap()
	{
		mapWidth = 1023;
		mapHeight = 834;
		importMap("/floor_plan_grid.PNG"); 
		objDataset.setValue(1, "A", "A");
		objDataset.setValue(0, "B", "B");
		objDataset.setValue(0, "C", "C");
		objDataset.setValue(0, "D", "D");

		objChart = ChartFactory.createBarChart("", // Chart title
				"Products", // Domain axis label
				"Time", // Range axis label
				objDataset, // Chart Data
				PlotOrientation.VERTICAL, // orientation
				false, // include legend?
				false, // include tooltips?
				false // include URLs?
		);

		chartFrame = new ChartFrame("Charge Rate", objChart);
		chartFrame.pack();
		chartFrame.setVisible(true);

		// initialize both arrays
		advertisementRevenue.add(1d);
		advertisementRevenue.add(0d);
		advertisementRevenue.add(0d);
		advertisementRevenue.add(0d);

		advertisementNames.add("A");
		advertisementNames.add("B");
		advertisementNames.add("C");
		advertisementNames.add("D");

		// red products
		Products.add(new Product("A", 67, 516, 46, 25, new Color(1f, 0f, 0f, .5f), 5));// red end isle
		Products.add(new Product("A", 680, 237, 20, 70, new Color(1f, 0f, 0f, .5f)));
		Products.add(new Product("A", 293, 308, 20, 70, new Color(1f, 0f, 0f, .5f)));
		// blue products
		Products.add(new Product("B", 169, 376, 20, 70, new Color(0f, 0f, 1f, .5f)));
		Products.add(new Product("B", 368, 376, 20, 70, new Color(0f, 0f, 1f, .5f)));
		Products.add(new Product("B", 267, 516, 46, 25, new Color(0f, 0f, 1f, .5f), 5));// blue end isle
		// green products
		Products.add(new Product("C", 368, 169, 20, 70, new Color(0f, 1f, 0f, .5f)));
		Products.add(new Product("C", 94, 378, 20, 70, new Color(0f, 1f, 0f, .5f)));
		// yellow products
		Products.add(new Product("D", 265, 169, 20, 70, new Color(1f, 1f, 0f, .5f)));
		Products.add(new Product("D", 293, 446, 20, 70, new Color(1f, 1f, 0f, .5f)));
		// customers
		Customers.add(new Customer(45, 700, 8, new Color(0.5f, 1f, 1f, .5f)));
		Customers.add(new Customer(45, 750, 8, new Color(0.5f, 0.8f, 1f, .5f)));
		Customers.add(new Customer(45, 650, 8, new Color(0.5f, 0.9f, 1f, .5f)));

		// green location deal
		
		/*LocationDeals.add(
				new LocationDeal(new Rectangle2D.Double(315, 124, 50, 140), "Brand C (Green)", new Color(0f, 1f, 0f, .15f)));
		ocationDeals.add(
				new LocationDeal(new Rectangle2D.Double(217, 540, 150, 100), "Brand B (Blue)", new Color(0f, 0f, 1f, .15f)));
	*/
	}
}