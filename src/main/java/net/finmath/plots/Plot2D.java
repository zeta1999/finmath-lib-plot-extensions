/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 21 May 2018
 */

package net.finmath.plots;

import java.text.NumberFormat;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import net.finmath.plots.jfreechart.JFreeChartUtilities;

/**
 * Small convenient wrapper for JFreeChart line plot derived.
 * 
 * @author Christian Fries
 */
public class Plot2D implements Plot {

	private double xmin, xmax;
	private int numberOfPointsX;
	private DoubleUnaryOperator function;

	private String title = "";
	private String xAxisLabel = "x";
	private String yAxisLabel = "y";
	private NumberFormat xAxisNumberFormat;
	private NumberFormat yAxisNumberFormat;

	private transient JFreeChart chart;

	public Plot2D(double xmin, double xmax, int numberOfPointsX, DoubleUnaryOperator function) {
		super();
		this.xmin = xmin;
		this.xmax = xmax;
		this.numberOfPointsX = numberOfPointsX;
		this.function = function;

		if(numberOfPointsX < 2) throw new IllegalArgumentException("Number of points needs to be larger than 1.");
	}

	private void init() {
		double[] xValues = new double[numberOfPointsX];
		double[] yValues = new double[numberOfPointsX];
		for(int i = 0; i<xValues.length; i++) {
			xValues[i] = xmin + i * ((xmax-xmin) / (numberOfPointsX-1));
			yValues[i] = function.applyAsDouble(xValues[i]);
		}
		chart = JFreeChartUtilities.getXYLinesPlotChart(title, xAxisLabel, "#.#" /* xAxisNumberFormat */, yAxisLabel, "#.#" /* yAxisNumberFormat */, xValues, yValues);
	}

	@Override
	public void show() throws Exception {
		init();
		JPanel chartPanel = new ChartPanel(chart, 
				800, 400,   // size
				128, 128,   // minimum size
				2024, 2024, // maximum size
				false, true, true, false, true, false);    // useBuffer, properties, save, print, zoom, tooltips

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.add(chartPanel);
				frame.setVisible(true);
				frame.pack();
			}
		});	
	}

	@Override
	public Plot2D setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public Plot2D setXAxisLabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
		return this;
	}

	@Override
	public Plot2D setYAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
		return this;
	}

	@Override
	public Plot setZAxisLabel(String zAxisLabel) {
		throw new UnsupportedOperationException("The 2D plot does not suport a z-axis. Try 3D plot instead.");
	}
}