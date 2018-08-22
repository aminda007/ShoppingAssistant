package com.codemo.www.shoppingassistant.LocationFindingAlgorithm;

public class Coordinate2D {

        private double x;
        private double y;

        public Coordinate2D(double x, double y){
            this.x=x;
            this.y=y;
        }

        public float getX() {
            return (float) x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public float getY() {
            return (float) y;
        }

    @Override
    public String toString() {
        return "x=" + x +", y=" + y;
    }


    public String toRoundedString(){
            return "x=" + Util2D.round3deci(x) +", y=" + Util2D.round3deci(y);
    }
}
