package objects;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import javafx.scene.shape.Circle;


public class Customer
{
	private Point position;
	private int radius;
	private Color color;
	private int advertisementThreshold;
	private ArrayList<Circle> heatMap;
	private Timer dealTimer;
	public Boolean hasRecievedDeal = false;
	private Map timerMap;
	private Map dealMap;
	
	public Customer(int x, int y,int radius,Color c)
	{
		this.radius = radius;
		position = new Point(x,y);
		color = c;
		advertisementThreshold = 26;
		heatMap = new ArrayList<Circle>();
		timerMap = new HashMap<String, Timer>();
		dealMap = new HashMap<String, Boolean>();
		
		dealTimer = new Timer(10000, new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				hasRecievedDeal = false;
				dealTimer.stop();
			}
		});
	}
	
	public void setPosition(Point p)
	{
		position = p;
	}
	
	public Point getPosition()
	{
		return position;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public Color getColor()
	{
		return color;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getAdvertisementThreshold() {
		return advertisementThreshold;
	}

	public void setAdvertisementThreshold(int advertisementThreshold) {
		this.advertisementThreshold = advertisementThreshold;
	}

	public void addHeatMap(double x, double y)
	{
		heatMap.add(new Circle(x,y,advertisementThreshold));
		if(heatMap.size() > 200)
		{
			heatMap.remove(0);
		}
	}
	
	public ArrayList<Circle> getHeatMap()
	{
		return heatMap;
	}

	public void setHeatMap(ArrayList<Circle> heatMap)
	{
		this.heatMap = heatMap;
	}
	
	public void startDealTimer(String productName)
	{
		if(timerMap.containsKey(productName))
		{
			Timer t = (Timer) timerMap.get(productName);
			t.start();
		}
		else
		{
			Timer t = new Timer(10000, new ActionListener() {
				public void actionPerformed(ActionEvent evt)
				{
					System.out.println("Deal timer");
					System.out.println(productName);
					
					dealMap.put(productName, false);
					stopDealTimer(productName);
					
				}
			});
			timerMap.put(productName, t);
			dealMap.put(productName, true);
		}
	}
	
	public void stopDealTimer(String productName)
	{
		if(timerMap.containsKey(productName))
		{
			Timer t = (Timer) timerMap.get(productName);
			t.stop();
		}
	}
	
	//returns if this customer has been given a deal for the given product name
	public Boolean getDealFlag(String productName)
	{
		if(dealMap.containsKey(productName))
		{
			Boolean b = (Boolean) dealMap.get(productName);
			return b;
		}
		return false;
	}
}
