package com.codemo.www.shoppingassistant;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aminda on 8/22/2018.
 */

public class MarketMap {

    private ScatterChart chart;
    private ScatterDataSet beacon1;
    private ScatterDataSet beacon2;
    private ScatterDataSet beacon3;
    private ScatterDataSet rack1;
    private ScatterDataSet rack2;
    private ScatterDataSet rack3;
    private ScatterDataSet user;

    public MarketMap(ScatterChart chart) {
        super();
        setChart(chart);
    }

    public void createMap(){
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setTouchEnabled(false);
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.getAxisLeft().setDrawGridLines(false);
        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(false);
        xAxis.setDrawGridLines(false);
        chart.setDrawBorders(true);
    }

    public void showBeacons(Pointer p1, Pointer p2, Pointer p3){
        // BEACON 1
        List<Entry> entry1 = new ArrayList<Entry>();
        entry1.add(new Entry(p1.getX(), p1.getY()));
        beacon1 = new ScatterDataSet(entry1, "b-1");
        beacon1.setColor(Color.RED);
        beacon1.setValueTextColor(Color.BLACK);
        beacon1.setScatterShape(ScatterChart.ScatterShape.TRIANGLE);
        beacon1.setScatterShapeSize(30);
        beacon1.setScatterShapeHoleRadius(0);
        beacon1.setScatterShapeHoleColor(Color.RED);
        beacon1.setDrawValues(false);
        // BEACON 2
        List<Entry> entry2 = new ArrayList<Entry>();
        entry2.add(new Entry(p2.getX(), p2.getY()));
        beacon2 = new ScatterDataSet(entry2, "b-2");
        beacon2.setColor(Color.RED);
        beacon2.setValueTextColor(Color.BLACK);
        beacon2.setScatterShape(ScatterChart.ScatterShape.TRIANGLE);
        beacon2.setScatterShapeSize(30);
        beacon2.setScatterShapeHoleRadius(0);
        beacon2.setScatterShapeHoleColor(Color.RED);
        beacon2.setDrawValues(false);
        // BEACON 3
        List<Entry> entry3 = new ArrayList<Entry>();
        entry3.add(new Entry(p3.getX(), p3.getY()));
        beacon3 = new ScatterDataSet(entry3, "b-3");
        beacon3.setColor(Color.RED);
        beacon3.setValueTextColor(Color.BLACK);
        beacon3.setScatterShape(ScatterChart.ScatterShape.TRIANGLE);
        beacon3.setScatterShapeSize(30);
        beacon3.setScatterShapeHoleRadius(0);
        beacon3.setScatterShapeHoleColor(Color.RED);
        beacon3.setDrawValues(false);

        ScatterDataSet[] dataSets={beacon1,beacon2,beacon3, rack1,rack2,rack3};
        ScatterData scatterData= new ScatterData(dataSets);
        getChart().setData(scatterData);
        getChart().invalidate();
    }

    public void showRacks(Pointer p1, Pointer p2, Pointer p3){
        // BEACON 1
        List<Entry> entry1 = new ArrayList<Entry>();
        entry1.add(new Entry(p1.getX(), p1.getY()));
        rack1 = new ScatterDataSet(entry1, "Pointer-1");
        rack1.setColor(Color.rgb(73, 75, 74));
        rack1.setValueTextColor(Color.BLACK);
        rack1.setScatterShape(ScatterChart.ScatterShape.SQUARE);
        rack1.setScatterShapeSize(150);
        rack1.setScatterShapeHoleRadius(35);
        rack1.setScatterShapeHoleColor(Color.rgb(144, 141, 141));
        rack1.setDrawValues(false);
        // BEACON 2
        List<Entry> entry2 = new ArrayList<Entry>();
        entry2.add(new Entry(p2.getX(), p2.getY()));
        rack2 = new ScatterDataSet(entry2, "Pointer-2");
        rack2.setColor(Color.rgb(73, 75, 74));
        rack2.setValueTextColor(Color.BLACK);
        rack2.setScatterShape(ScatterChart.ScatterShape.SQUARE);
        rack2.setScatterShapeSize(150);
        rack2.setScatterShapeHoleRadius(35);
        rack2.setScatterShapeHoleColor(Color.rgb(144, 141, 141));
        rack2.setDrawValues(false);
        // BEACON 3
        List<Entry> entry3 = new ArrayList<Entry>();
        entry3.add(new Entry(p3.getX(), p3.getY()));
        rack3 = new ScatterDataSet(entry3, "Pointer-3");
        rack3.setColor(Color.rgb(73, 75, 74));
        rack3.setValueTextColor(Color.BLACK);
        rack3.setScatterShape(ScatterChart.ScatterShape.SQUARE);
        rack3.setScatterShapeSize(150);
        rack3.setScatterShapeHoleRadius(35);
        rack3.setScatterShapeHoleColor(Color.rgb(144, 141, 141));
        rack3.setDrawValues(false);

        ScatterDataSet[] dataSets={rack1,rack2,rack3};
        ScatterData scatterData= new ScatterData(dataSets);
        getChart().setData(scatterData);
        getChart().invalidate();
    }

    public void updateLocation(Pointer point){
        List<Entry> userEntry = new ArrayList<Entry>();
        userEntry.add(new Entry(point.getX(), point.getY()));
        user = new ScatterDataSet(userEntry, "User");
        user.setColor(Color.rgb(0,220,255));
        user.setValueTextColor(Color.BLACK);
        user.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        user.setScatterShapeSize(50);
        user.setScatterShapeHoleColor(Color.rgb(0,160,255));
        user.setScatterShapeHoleRadius(8);
        user.setDrawValues(false);
//        user.setHighlightEnabled(true);
        ScatterDataSet[] dataSets={beacon1,beacon2,beacon3, rack1,rack2,rack3 ,user};
        ScatterData scatterData= new ScatterData(dataSets);
        getChart().setData(scatterData);
        getChart().invalidate();
    }

    public ScatterChart getChart() {
        return chart;
    }

    public void setChart(ScatterChart chart) {
        this.chart = chart;
    }
}
