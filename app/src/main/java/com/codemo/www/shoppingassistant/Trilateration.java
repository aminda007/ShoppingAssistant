package com.codemo.www.shoppingassistant;

import java.util.ArrayList;

/**
 * Created by aminda on 8/22/2018.
 */

public class Trilateration {

    public Pointer findCenter(ArrayList<Pointer> beacons){

        float top = 0;
        float bot = 0;
        for (int i=0; i<3; i++) {
            Pointer c = beacons.get(i);
            Pointer c2, c3;
            if (i==0) {
                c2 = beacons.get(1);
                c3 = beacons.get(2);
            }
            else if (i==1) {
                c2 = beacons.get(0);
                c3 = beacons.get(2);
            }
            else {
                c2 = beacons.get(0);
                c3 = beacons.get(1);
            }

            float d = c2.getX() - c3.getY();

            float v1 = (c.getX() * c.getX() + c.getY() * c.getY()) - (c.getRadius() * c.getRadius());
            top += d*v1;

            float v2 = c.getY() * d;
            bot += v2;

        }

        float y = top / (2*bot);
        Pointer c1 = beacons.get(0);
        Pointer c2 = beacons.get(1);
        top = c2.getRadius()*c2.getRadius()+c1.getX()*c1.getX()+c1.getY()*c1.getY()-c1.getRadius()*c1.getRadius()-c2.getX()*c2.getX()-c2.getY()*c2.getY()-2*(c1.getY()-c2.getY())*y;
        bot = c1.getX()-c2.getX();
        float x = top / (2*bot);

        Pointer user = new Pointer();
        user.setX(x);
        user.setY(y);
        return user;
    }

}
