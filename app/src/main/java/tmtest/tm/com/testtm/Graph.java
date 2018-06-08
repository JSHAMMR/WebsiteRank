package tmtest.tm.com.testtm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Graph extends AppCompatActivity {

    ArrayList<String> myList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");

//        Pie pie = AnyChart.pie();
//
//        List<DataEntry> data = new ArrayList<>();
//
//        for (int i=0;i<myList.size();i++) {
//
//            WebsiteModel item = myList.get(i);
//            data.add(new ValueDataEntry(item.getWebsite_name(), item.getTotal_visits()));
//
//
//        }
//
//        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
//        anyChartView.setChart(pie);
    }
}
