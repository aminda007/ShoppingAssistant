package com.codemo.www.shoppingassistant.LocationFindingAlgorithm;

import java.text.DecimalFormat;

public class Util2D {


    private static DecimalFormat df=new DecimalFormat("#.###");

    public static Coordinate2D shiftXY_intoOrigin(Coordinate2D pointToBeShifted,double shiftingValueX,double shiftingValueY){
       return new Coordinate2D(pointToBeShifted.getX()-shiftingValueX,pointToBeShifted.getY()-shiftingValueY);
    }
    public static Coordinate2D shiftXY_outOfOrigin(Coordinate2D pointToBeShifted,double shiftingValueX,double shiftingValueY){
        return new Coordinate2D(pointToBeShifted.getX()+shiftingValueX,pointToBeShifted.getY()+shiftingValueY);
    }

    public static Coordinate2D rotateAntiClockwiseXY(Coordinate2D pointToBeRotated, double angle){

        //  x_ = x cos (antiClockwiseAngle) - y sin (antiClockwiseAngle)
        //  y_ = y cos (antiClockwiseAngle) + x sin (antiClockwiseAngle)

        double x_= pointToBeRotated.getX()*Math.cos(angle) -pointToBeRotated.getY()*Math.sin(angle);
        double y_ = pointToBeRotated.getY()*Math.cos(angle) + pointToBeRotated.getX()*Math.sin(angle);
        
        return new Coordinate2D(x_,y_);
    }

    public static Coordinate2D rotateClockwiseXY(Coordinate2D pointToBeRotated,double angle){
        return rotateAntiClockwiseXY(pointToBeRotated,-angle);
    }

    public static double rotateClockwiseX(Coordinate2D pointToBeRotated, double clockWiseAngle){
        double x_= pointToBeRotated.getX()*Math.cos(-clockWiseAngle) -pointToBeRotated.getY()*Math.sin(-clockWiseAngle);
        return x_;
    }

    public static double getDistance(Coordinate2D point1,Coordinate2D point2){
        double distance=Math.sqrt( Math.pow((point1.getX()-point2.getX()),2) + Math.pow((point1.getY()-point2.getY()),2) );
        return distance;
    }

    /**
     *
     * @param point1 p1
     * @param point2 p2
     * @param ratio p1N/p2N
     * @return coordinates of point(N) on line
     */
    public static Coordinate2D pointOnLineByRatio(Coordinate2D point1,Coordinate2D point2,double ratio){
        // important |=>  ratio=p1N/p2N
        double x=(ratio*point2.getX() + point1.getX()) / (ratio+1);
        double y=(ratio*point2.getY() + point1.getY()) / (ratio+1);
        return new Coordinate2D(x,y);
    }

    public static Double round3deci(double x){
        return Double.valueOf(df.format(x));
    }
}
