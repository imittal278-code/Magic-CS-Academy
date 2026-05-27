/* -------------------------------------------------------------------------
 *
 *	$1 Java
 *
 * 	This is a Java port of the $1 Gesture Recognizer by
 *	Jacob O. Wobbrock, Andrew D. Wilson, Yang Li.
 * 
 *	"The $1 Unistroke Recognizer is a 2-D single-stroke recognizer designed for 
 *	rapid prototyping of gesture-based user interfaces."
 *	 
 *	http://depts.washington.edu/aimgroup/proj/dollar/
 *
 *	Copyright (C) 2009, Alex Olwal, www.olwal.com
 *
 *	$1 Java free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	$1 Java is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with $1 Java.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  -------------------------------------------------------------------------
 */

package io.github.magiccsacademy.csa_project;

/**
 * The point class used for recognizing shapes
 */
public class Point
{

	/**
	 * the coordinates of this point
	 */
	public double X, Y;

	/**
	 * Constructs a point class, initializing the fields
	 *
	 * @param x the x position of the point
	 * @param y the y positions of the point
	 */
	public Point(double x, double y)
	{	this.X = x; this.Y = y;	}

	/**
	* copies a point, setting this point's fields to the other point's fields
	* @param point the point to copy from
	*/
	public void copy(Point point){
		this.X = point.X;
		this.Y = point.Y;
	}

}
