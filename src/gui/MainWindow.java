package gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
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
import java.net.URL;
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

import javafx.scene.shape.Circle;
import javax.swing.JMenuItem;
import javax.swing.JCheckBox;

public class MainWindow
{

	private JFrame frame;
	private MainGroceryMap groceryMap;
	private MainStoreMap storeMap;
	JCheckBox chckbxShowHeatMap;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
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



		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenu mnNewMenu = new JMenu("New");
		mnFile.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Grocery Map");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				changeGroceryMap();
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Mall Map");
		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				changeStoreMap();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenu mnNewMenu_1 = new JMenu("Open");
		mnFile.add(mnNewMenu_1);

		JMenu mnNewMenu_2 = new JMenu("Save");
		mnFile.add(mnNewMenu_2);

		JMenu mnNewMenu_3 = new JMenu("Exit");
		mnNewMenu_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mnNewMenu_3);

		JMenu mnNewMenu_4 = new JMenu("Edit");
		menuBar.add(mnNewMenu_4);
		
		chckbxShowHeatMap = new JCheckBox("Show Heat Map");
		chckbxShowHeatMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(chckbxShowHeatMap.isSelected());
				showHeatMap();
			}
		});
		menuBar.add(chckbxShowHeatMap);
	}
	
	private void changeGroceryMap()
	{
		if(storeMap != null)
		{
			storeMap.close();
			frame.getContentPane().remove(storeMap);
			frame.revalidate();
		}
		if(groceryMap != null)
		{
			groceryMap.close();
			frame.getContentPane().remove(groceryMap);
			frame.revalidate();
		}
		groceryMap = new MainGroceryMap();
		frame.getContentPane().add(groceryMap, BorderLayout.CENTER);
		frame.setBounds(100, 100, 1015, 834);
		frame.revalidate();
		showHeatMap();
	}
	
	private void changeStoreMap()
	{
		if(storeMap != null)
		{
			storeMap.close();
			frame.getContentPane().remove(storeMap);
			frame.revalidate();
		}
		if(groceryMap != null)
		{
			groceryMap.close();
			frame.getContentPane().remove(groceryMap);
			frame.revalidate();
		}
		storeMap = new MainStoreMap();
		frame.getContentPane().add(storeMap, BorderLayout.CENTER);
		frame.setBounds(100, 100, 1500, 834);
		frame.revalidate();
		showHeatMap();
	}
	
	private void showHeatMap()
	{
		if(storeMap != null)
		{
			storeMap.showHeatMap = chckbxShowHeatMap.isSelected();
			storeMap.repaint();
		}
		if(groceryMap != null)
		{
			groceryMap.showHeatMap = chckbxShowHeatMap.isSelected();
			groceryMap.repaint();
		}
		
	}

}
