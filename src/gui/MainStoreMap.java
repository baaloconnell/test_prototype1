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

public class MainStoreMap extends DisplayMap
{
	public MainStoreMap()
	{
		mapWidth = 1500;
		mapHeight = 851;
		importMap("/Floor_Plan_Of_Store.png");
		
		objDataset.setValue(0, "A", "A");
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

		// customers
		Customers.add(new Customer(45, 450, 8, new Color(0.5f, 1f, 1f, .5f)));
		Customers.add(new Customer(45, 500, 8, new Color(0.5f, 0.8f, 1f, .5f)));
		Customers.add(new Customer(45, 550, 8, new Color(0.5f, 0.9f, 1f, .5f)));

		// green location deal
		LocationDeals.add(
				new LocationDeal(new Rectangle2D.Double(508, 504, 500, 60), "Store C (Green)", new Color(0f, 1f, 0f, .15f)));
		//red location deal
		LocationDeals.add(
				new LocationDeal(new Rectangle2D.Double(508, 290, 500, 60), "Store A (Red)", new Color(1f, 0f, 0f, .15f)));
		//blue location deal
		LocationDeals.add(
				new LocationDeal(new Rectangle2D.Double(0, 290, 500, 60), "Store B (Blue)", new Color(0f, 0f, 1f, .15f)));
		//yellow
		LocationDeals.add(
				new LocationDeal(new Rectangle2D.Double(1005, 290, 500, 60), "Store D (Yellow)", new Color(1f, 1f, 0f, .15f)));
		

	}
}