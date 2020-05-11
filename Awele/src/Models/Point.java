/****************************************************************************************************/
/*  Class Point                                                                                     */
/*  Deals with Points coordinates manipulation (x, y)                                               */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/

package Models;

import java.util.Objects;

public class Point {
    private int m_x;
    private int m_y;
    
    /**
     * Creates a point
     * @param x X value of the point
     * @param y Y value of the point
     */
    public Point(int x, int y) {
        this.m_x = x;
        this.m_y = y;
    }

    /**
     * Creates a point by copying another one
     * @param point Point to copy
     */
    public Point(Point point) {
        this.m_x = point.getX();
        this.m_y = point.getY();
    }

    /**
     * Assign both coordinates at once
     * @param x X coordinate of the Point
     * @param y Y coordinate of the Point
     */
    public void setCoordinates(int x, int y){
        this.setX(x);
        this.setY(y);
    }

    /**
     * Assign both coordinates at once
     * @param p Point of which copy the coordinates
     */
    public void setCoordinates(Point p){
        this.setX(p.getX());
        this.setY(p.getY());
    }

    /**
     * Returns the X value of the current point
     * @return X
     */
    public int getX() {
        return this.m_x;
    }

    /**
     * Set the X value of the current point
     * @param x Value to set
     */
    public void setX(int x){
        this.m_x = x;
    }

    /**
     * Returns the Y value of the current point
     * @return Y
     */
    public int getY() {
        return this.m_y;
    }

    /**
     * Set the Y value of the current point
     * @param y Value to set
     */
    public void setY(int y){
        this.m_y = y;
    }

    /**
     * Returns the X and Y values of the point
     * @return 
     */
    @Override
    public String toString() {
        return "Point{" + m_x + ", " + m_y + '}';
    }

    /**
     * Returns the hashcode of the point object
     * @return Hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getX(), this.getY());
    }

    /**
     * Check whether the two points X and Y values are equal
     * @param obj Object to which compare the current Point
     * @return A bool indicating whether the two points are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.m_x != other.m_x) {
            return false;
        }
        if (this.m_y != other.m_y) {
            return false;
        }
        return true;
    }

    /**
     * Get the distance between the current point and p
     * @param p Point from which get the distance
     * @return Distance with P
     */
    public double getDistance(Point p){
        return Math.sqrt(Math.pow((double)p.getX() - (double)this.m_x, 2) + Math.pow((double)p.getY() - (double)this.m_y, 2));
    }
}
