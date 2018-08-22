package com.codemo.www.shoppingassistant.LocationFindingAlgorithm;

public class LocationFinder {

    /**
     *
     * @param beacon_1_position
     * @param beacon_2_position
     * @param beacon_3_position
     * @param r1_fromB1
     * @param r2_fromB2
     * @param r3_fromB3
     * @return estimated location of the point
     */
    public static Coordinate2D getLocation(Coordinate2D beacon_1_position, Coordinate2D beacon_2_position, Coordinate2D beacon_3_position,
                                           double r1_fromB1,double r2_fromB2,double r3_fromB3){

        double a1 = beacon_1_position.getX();
        double a2 = beacon_2_position.getX();
        double a3 = beacon_3_position.getX();

        double b1 = beacon_1_position.getY();
        double b2 = beacon_2_position.getY();
        double b3 = beacon_3_position.getY();

        double r1 = r1_fromB1;
        double r2 = r2_fromB2;
        double r3 = r3_fromB3;

        double Y=calcY(a1,b1,a2,b2,a3,b3,r1,r2,r3);
        double X=calcX(a1,b1,a2,b2,a3,b3,r1,r2,r3,Y);


        return new Coordinate2D(X,Y);
    }

    private static double calcY(double a1,double b1,double a2,double b2,double a3, double b3,double r1,double r2, double r3){

        double numerator =(a2-a1)*(a3*a3+b3*b3-r3*r3)+(a1-a3)*(a2*a2+b2*b2-r2*r2)+(a3-a2)*(a1*a1+b1*b1-r1*r1);
        double denominator=2*(b3*(a2-a1)+b2*(a1-a3)+b1*(a3-a2));

        return numerator/denominator;
    }

    private static double calcX(double a1,double b1,double a2,double b2,double a3, double b3,double r1,double r2, double r3, double Y){
        double numerator =(r2*r2+a1*a1+b1*b1-r1*r1-a2*a2-b2*b2-2*(b1-b2)*Y);
        double denominator =2*(a1-a2);

        return numerator/denominator;
    }


}
