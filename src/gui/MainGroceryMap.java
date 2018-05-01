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

public class MainGroceryMap extends JPanel
{

	ArrayList<Customer> Customers;
	ArrayList<Product> Products;
	ArrayList<LocationDeal> LocationDeals;

	ArrayList<Double> advertisementRevenue;
	ArrayList<String> advertisementNames;

	BufferedImage storeMap;

	Timer timer;
	Timer heatMapTimer;
	Timer dragHeatMapTimer;
	Timer locationDealTimer;
	Customer selectedCustomer;
	ChartFrame chartFrame;

	DefaultCategoryDataset objDataset;

	JFreeChart objChart;

	float heatMapDist[];
	Color heatMapColors[];
	
	Boolean showHeatMap;

	public MainGroceryMap()
	{
		objDataset = new DefaultCategoryDataset();
		Customers = new ArrayList<Customer>();
		Products = new ArrayList<Product>();
		LocationDeals = new ArrayList<LocationDeal>();

		advertisementNames = new ArrayList<String>(4);
		advertisementRevenue = new ArrayList<Double>();

		heatMapDist = new float[] { 0.25f, 0.8f };
		heatMapColors = new Color[] { new Color(1f, 0f, 0f, .05f), new Color(0f, 1f, 0f, .025f) };


		objDataset.setValue(1, "A", "A");
		objDataset.setValue(0, "B", "B");
		objDataset.setValue(0, "C", "C");
		objDataset.setValue(0, "D", "D");

		objChart = ChartFactory.createBarChart("", // Chart title
				"Prducts", // Domain axis label
				"Revenue", // Range axis label
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
		// blue products
		Products.add(new Product("B", 169, 376, 20, 70, new Color(0f, 0f, 1f, .5f)));
		Products.add(new Product("B", 267, 516, 46, 25, new Color(0f, 0f, 1f, .5f), 5));// green end isle
		// green products
		Products.add(new Product("C", 368, 169, 20, 70, new Color(0f, 1f, 0f, .5f)));
		Products.add(new Product("C", 94, 378, 20, 70, new Color(0f, 1f, 0f, .5f)));
		// yellow products
		Products.add(new Product("D", 265, 169, 20, 70, new Color(1f, 1f, 0f, .5f)));
		// customers
		Customers.add(new Customer(45, 700, 8, new Color(0.5f, 1f, 1f, .5f)));
		Customers.add(new Customer(45, 750, 8, new Color(0.5f, 0.8f, 1f, .5f)));
		Customers.add(new Customer(45, 650, 8, new Color(0.5f, 0.9f, 1f, .5f)));

		// green location deal
		LocationDeals.add(
				new LocationDeal(new Rectangle2D.Double(315, 124, 50, 140), "Brand C (Green)", new Color(0f, 1f, 0f, .15f)));
		LocationDeals.add(
				new LocationDeal(new Rectangle2D.Double(217, 540, 150, 100), "Brand B (Blue)", new Color(0f, 0f, 1f, .15f)));

		addMouseListener(new MouseAdapter() {
			// get the NGZ which contains this click
			public void mousePressed(MouseEvent e)
			{
				// check if this click is on any of the customers, if it is then begin dragging
				// it around
				for (Customer customer : Customers)
				{

					Ellipse2D.Double el = new Ellipse2D.Double();
					el.setFrame(customer.getPosition().x, customer.getPosition().y, customer.getRadius() * 2,
							customer.getRadius() * 2);
					if (el.contains(e.getX(), e.getY()))
					{

						selectedCustomer = customer;
						repaint();
						break;
					}

				}

				/*
				 * for (Product product : Products) { Rectangle2D.Double rect = new
				 * Rectangle2D.Double(); rect.setRect(product.getPosition().getX(),
				 * product.getPosition().getY(), product.getWidth(), product.getHeight());
				 * if(rect.contains(e.getX(),e.getY())) { Products.remove(product); repaint();
				 * break; } }
				 */

			}

			public void mouseReleased(MouseEvent e)
			{
				selectedCustomer = null;
			}

			public void mouseMoved(MouseEvent e)
			{

			}
		});

		// set the timer for how often to charge the advertiser for each customer thats
		// within its radius
		timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				chargeAdvertisement();
			}
		});
		timer.start();

		// timer for how often the customer heat map should be updated
		heatMapTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				updateHeatMap();
			}
		});
		heatMapTimer.start();

		// timer for how often LocationDeals should be checked
		locationDealTimer = new Timer(2000, new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				checkLocationDeals();
			}
		});
		locationDealTimer.start();

		// have the heat map update more rapidly for dragged customers
		dragHeatMapTimer = new Timer(200, new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				if (selectedCustomer != null)
				{
					updateHeatMap(selectedCustomer);
				}

			}
		});
		dragHeatMapTimer.start();

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				if (selectedCustomer != null)
				{
					selectedCustomer.setPosition(new Point(e.getX() - selectedCustomer.getRadius(),
							e.getY() - selectedCustomer.getRadius()));
					// updateHeatMap();
					repaint();
				}
			}
		});

		// import storeMap image
		try
		{
			URL url = MainWindow.class.getResource("/floor_plan_grid.PNG");
			storeMap = ImageIO.read(url);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	// update the heatmap
	public void updateHeatMap()
	{
		for (Customer customer : Customers)
		{
			customer.addHeatMap(customer.getPosition().getX() + customer.getRadius(),
					customer.getPosition().getY() + customer.getRadius());
		}
		repaint();
	}

	public void updateHeatMap(Customer customer)
	{
		customer.addHeatMap(customer.getPosition().getX() + customer.getRadius(),
				customer.getPosition().getY() + customer.getRadius());
		repaint();
	}

	// charge each advertiser for each customer, based on the charging threshold and
	// the price function
	public void chargeAdvertisement()
	{
		for (Customer customer : Customers)
		{
			for (Product product : Products)
			{
				if (adChargingThreshold(customer, product))
				{
					int key = advertisementNames.indexOf(product.getName());
					advertisementRevenue.set(key, advertisementRevenue.get(key) + priceFunction(customer, product));
					objDataset.setValue(advertisementRevenue.get(key), advertisementNames.get(key),
							advertisementNames.get(key));
					System.out.printf("A:%f B:%f C:%f D:%f \n", advertisementRevenue.get(0),
							advertisementRevenue.get(1), advertisementRevenue.get(2), advertisementRevenue.get(3));
				}

			}
		}
	}

	// the function to determine after what threshold should charging begin for
	// advertisers
	public Boolean adChargingThreshold(Customer c, Product p)
	{
		Ellipse2D.Double el = new Ellipse2D.Double();
		Rectangle2D.Double rect = new Rectangle2D.Double();

		el.setFrame(c.getPosition().x - c.getAdvertisementThreshold() + c.getRadius(),
				c.getPosition().y - c.getAdvertisementThreshold() + c.getRadius(), c.getAdvertisementThreshold() * 2,
				c.getAdvertisementThreshold() * 2);
		rect.setRect(p.getPosition().getX(), p.getPosition().getY(), p.getWidth(), p.getHeight());

		if (el.intersects(rect))
		{
			return true;
		}
		return false;
	}

	// determine how much the advertiser should pay
	public double priceFunction(Customer c, Product p)
	{
		double distance = Math.sqrt(Math
				.pow((c.getPosition().x + c.getRadius()) - (p.getPosition().getX() + p.getWidth() / 2), 2)
				+ Math.pow((c.getPosition().getY() + c.getRadius()) - (p.getPosition().getY() + p.getHeight() / 2), 2));

		if (distance != 0)
		{
			return (1 / Math.pow(distance, 2)) * 100 * p.getCostModifier();
		}

		return 0;
	}

	public void checkLocationDeals()
	{
		Ellipse2D.Double el = new Ellipse2D.Double();

		for (LocationDeal locationDeal : LocationDeals)
		{
			for (Customer c : Customers)
			{
				el.setFrame(c.getPosition().x - c.getAdvertisementThreshold() + c.getRadius(),
						c.getPosition().y - c.getAdvertisementThreshold() + c.getRadius(),
						c.getAdvertisementThreshold() * 2, c.getAdvertisementThreshold() * 2);

				if (el.intersects(locationDeal.getBounds()))
				{
					//check if this deal has been displayed in the last 30 seconds for this customer
					if(!c.getDealFlag(locationDeal.getProductName()))
					{
						c.startDealTimer(locationDeal.getProductName());
						// display that a deal has happened
						JOptionPane.showMessageDialog(this,
								"Customer has recieved deal for " + locationDeal.getProductName(), "Deal Alert",
								JOptionPane.PLAIN_MESSAGE);
						//de select the customer as well
						selectedCustomer = null;
					}
				}

			}
		}
	}

	public void close()
	{
		heatMapTimer.stop();
		dragHeatMapTimer.stop();
		locationDealTimer.stop();
		timer.stop();
		chartFrame.setVisible(false);
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.drawImage(storeMap, 0, 0, 1023, 834, null);

		for (Product p : Products)
		{
			g2d.setColor(p.getColor());
			g2d.fillRect(p.getPosition().x, p.getPosition().y, p.getWidth(), p.getHeight());
			g2d.setColor(Color.BLACK);
			g2d.drawRect(p.getPosition().x, p.getPosition().y, p.getWidth(), p.getHeight());
			g2d.setColor(new Color(0f, 0f, 0.5f, .1f));
		}

		for (LocationDeal l : LocationDeals)
		{
			g2d.setColor(l.getColor());
			g2d.fillRect((int) l.getBounds().getX(), (int) l.getBounds().getY(), (int) l.getBounds().getWidth(),
					(int) l.getBounds().getHeight());
		}

		for (Customer c : Customers)
		{
			g2d.setColor(c.getColor());
			g2d.fillOval(c.getPosition().x, c.getPosition().y, c.getRadius() * 2, c.getRadius() * 2);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(c.getPosition().x, c.getPosition().y, c.getRadius() * 2, c.getRadius() * 2);
			g2d.setColor(new Color(0f, 0f, 0.5f, .1f));
			g2d.fillOval(c.getPosition().x - c.getAdvertisementThreshold() + c.getRadius(),
					c.getPosition().y - c.getAdvertisementThreshold() + c.getRadius(),
					c.getAdvertisementThreshold() * 2, c.getAdvertisementThreshold() * 2);

			
			if(!showHeatMap)
				continue;
			
			for (Circle circ : c.getHeatMap())
			{
				RadialGradientPaint p = new RadialGradientPaint(
						new Point((int) circ.getCenterX(), (int) circ.getCenterY()), (int) circ.getRadius(),
						heatMapDist, heatMapColors, CycleMethod.NO_CYCLE);
				g2d.setPaint(p);
				g2d.fillOval((int) (circ.getCenterX() - circ.getRadius()), (int) (circ.getCenterY() - circ.getRadius()),
						(int) circ.getRadius() * 2, (int) circ.getRadius() * 2);
			}

		}

	}
}