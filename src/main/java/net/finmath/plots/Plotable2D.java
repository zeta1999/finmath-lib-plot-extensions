/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 21 May 2018
 */
package net.finmath.plots;

import java.util.List;

public interface Plotable2D {
	
	String getName();

	List<Point2D> getSeries();

	GraphStyle getStyle();
}