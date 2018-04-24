package gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import objects.*;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class MainWindow
{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		
	    try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch(Exception e) {
	        System.out.println("Error setting native LAF: " + e);
	    }
	    
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try
				{
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 1015, 834);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel map = new MainMap();
		frame.getContentPane().add(map, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnNewMenu = new JMenu("New");
		mnFile.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("Open");
		mnFile.add(mnNewMenu_1);
		
		JMenu mnNewMenu_2 = new JMenu("Save");
		mnFile.add(mnNewMenu_2);
		
		JMenu mnNewMenu_3 = new JMenu("Exit");
		mnFile.add(mnNewMenu_3);
		
		JMenu mnNewMenu_4 = new JMenu("Edit");
		menuBar.add(mnNewMenu_4);
	}

}

class MainMap extends JPanel
{

	ArrayList<Customer> Customers;
	ArrayList<Product> Products;

	ArrayList<Double> advertisementRevenue;
	ArrayList<String> advertisementNames;

	BufferedImage storeMap;

	Timer timer;
	Customer selectedCustomer;
	
	DefaultCategoryDataset objDataset;
	
	JFreeChart objChart;

	public MainMap()
	{
		objDataset = new DefaultCategoryDataset();
		Customers = new ArrayList<Customer>();
		Products = new ArrayList<Product>();

		advertisementNames = new ArrayList<String>(4);
		advertisementRevenue = new ArrayList<Double>();

		
		objDataset.setValue(1,"A","A");
		objDataset.setValue(0,"B","B");
		objDataset.setValue(0,"C","C");
		objDataset.setValue(0,"D","D");
		
		objChart = ChartFactory.createBarChart(
			       "",     //Chart title
				    "Prducts",     //Domain axis label
				    "Revenue",         //Range axis label
				    objDataset,         //Chart Data 
				    PlotOrientation.VERTICAL, // orientation
				    false,             // include legend?
				    false,             // include tooltips?
				    false             // include URLs?
				);

		ChartFrame frame = new ChartFrame("Charge Rate", objChart);
		frame.pack();
		frame.setVisible(true);

		//initialize both arrays
		advertisementRevenue.add(1d);
		advertisementRevenue.add(0d);
		advertisementRevenue.add(0d);
		advertisementRevenue.add(0d);
		
		advertisementNames.add("A");
		advertisementNames.add("B");
		advertisementNames.add("C");
		advertisementNames.add("D");

		// red products
		Products.add(new Product("A", 67, 516, 46, 25, new Color(1f, 0f, 0f, .5f),5));//red end isle
		// blue products
		Products.add(new Product("B", 169, 376, 20, 70, new Color(0f, 0f, 1f, .5f)));
		Products.add(new Product("B", 267, 516, 46, 25, new Color(0f, 0f, 1f, .5f),5));//green end isle
		// green products
		Products.add(new Product("C", 368, 169, 20, 70, new Color(0f, 1f, 0f, .5f)));
		Products.add(new Product("C", 94, 378, 20, 70, new Color(0f, 1f, 0f, .5f)));
		// yellow products
		Products.add(new Product("D", 265, 169, 20, 70, new Color(1f, 1f, 0f, .5f)));
		// customers
		Customers.add(new Customer(100, 100, 8, new Color(0.5f, 1f, 1f, .5f)));
		Customers.add(new Customer(200, 200, 8, new Color(0.5f, 0.8f, 1f, .5f)));
		Customers.add(new Customer(300, 300, 8, new Color(0.5f, 0.9f, 1f, .5f)));

		addMouseListener(new MouseAdapter() {
			// get the NGZ which contains this click
			public void mousePressed(MouseEvent e)
			{
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

		// set the timer for
		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				chargeAdvertisement();
			}
		});
		timer.start();

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
					repaint();
				}
			}
		});

		// import storeMap image
		try
		{
			storeMap = ImageIO.read(new File("images/floor_plan_grid.PNG"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
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
					advertisementRevenue.set(key, advertisementRevenue.get(key)+priceFunction(customer,product));
					objDataset.setValue(advertisementRevenue.get(key),advertisementNames.get(key),advertisementNames.get(key));
					System.out.printf("A:%f B:%f C:%f D:%f \n",advertisementRevenue.get(0),advertisementRevenue.get(1),
							advertisementRevenue.get(2),advertisementRevenue.get(3));
				}

			}
		}
	}

	// the function to determine after what threshold should charging begin for advertisers
	public Boolean adChargingThreshold(Customer c, Product p)
	{
		Ellipse2D.Double el = new Ellipse2D.Double();
		Rectangle2D.Double rect = new Rectangle2D.Double();
		
		el.setFrame(c.getPosition().x - c.getAdvertisementThreshold() + c.getRadius(),
				c.getPosition().y - c.getAdvertisementThreshold() + c.getRadius(),
				c.getAdvertisementThreshold() * 2, c.getAdvertisementThreshold() * 2);
		rect.setRect(p.getPosition().getX(), p.getPosition().getY(), p.getWidth(), p.getHeight());
		
		if(el.intersects(rect))
		{
			return true;
		}
		return false;
	}

	//determine how much the advertiser should pay
	public double priceFunction(Customer c, Product p)
	{
		double distance = Math.sqrt(Math.pow((c.getPosition().x+c.getRadius())-(p.getPosition().getX()+p.getWidth()/2),2)
				+Math.pow((c.getPosition().getY()+c.getRadius())-(p.getPosition().getY()+p.getHeight()/2),2));
		
		if(distance != 0)
		{
			return (1/Math.pow(distance, 2))*100*p.getCostModifier();
		}
		
		return 0;
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

		}

	}
}
