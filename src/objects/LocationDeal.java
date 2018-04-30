package objects;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class LocationDeal
{
	private Color color;
	private String productName;
	private Rectangle2D.Double bounds;
	
	public LocationDeal(Rectangle2D.Double bounds,String productName, Color color)
	{
		this.bounds = bounds;
		this.productName = productName;
		this.color = color;
	}

	public Rectangle2D.Double getBounds()
	{
		return bounds;
	}

	public void setBounds(Rectangle2D.Double bounds)
	{
		this.bounds = bounds;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
}
