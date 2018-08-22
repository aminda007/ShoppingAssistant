package com.codemo.www.shoppingassistant.LocationFindingAlgorithm;

import java.util.ArrayList;

public class TrilateriationSecondAlgo {
    

    public Coordinate2D findCenter(Coordinate2D beacon_1_position, Coordinate2D beacon_2_position, Coordinate2D beacon_3_position,
                            double r1_fromB1,double r2_fromB2,double r3_fromB3) {

        ArrayList<Circle> circles=new ArrayList<>();
        circles.add(new Circle(beacon_1_position.getX(),beacon_1_position.getY(),r1_fromB1));
        circles.add(new Circle(beacon_2_position.getX(),beacon_2_position.getY(),r2_fromB2));
        circles.add(new Circle(beacon_3_position.getX(),beacon_3_position.getY(),r3_fromB3));

        double top = 0;
        double bot = 0;
        for (int i=0; i<3; i++) {
            Circle c = circles.get(i);
            Circle c2, c3;
            if (i==0) {
                c2 = circles.get(1);
                c3 = circles.get(2);
            }
            else if (i==1) {
                c2 = circles.get(0);
                c3 = circles.get(2);
            }
            else {
                c2 = circles.get(0);
                c3 = circles.get(1);
            }

            double d = c2.x - c3.x;

            double v1 = (c.x * c.x + c.y * c.y) - (c.r * c.r);
            top += d*v1;

            double v2 = c.y * d;
            bot += v2;

        }

        double y = top / (2*bot);
        Circle c1 = circles.get(0);
        Circle c2 = circles.get(1);
        top = c2.r*c2.r+c1.x*c1.x+c1.y*c1.y-c1.r*c1.r-c2.x*c2.x-c2.y*c2.y-2*(c1.y-c2.y)*y;
        bot = c1.x-c2.x;
        double x = top / (2*bot);

        return new Coordinate2D(x,y);

    }


    private class Circle {

        double x;
        double y;
        double r;

        Circle(double x, double y,double  r){
            this.x=x;
            this.y=y;
            this.r=r;
        }


    }
}
