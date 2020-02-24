//package com.camsys.carmonic.MapViews;
//
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//
//import com.camsys.carmonic.R;
//
//import java.sql.Array;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class AmazonTest extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_amazon_test);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//        int numFeatures = 2 ;
//        int topFeatures = 4 ;
//        ArrayList<String> possibleFeatures  =  new ArrayList<String>() ;
//        possibleFeatures.add("money");
//        possibleFeatures.add("bowl");
//        possibleFeatures.add("girl");
//
//        int numFeatureRequests = 4 ;
//        ArrayList<String> featureRequests =  new  ArrayList<String>();
//        featureRequests.add("girl  and  me");
//        featureRequests.add("i need  money for val");
//        featureRequests.add("money and  family");
//        featureRequests.add("nothing ");
//
//System.out.println("Testssttsst");
//        popularNFeatures(numFeatures,topFeatures,possibleFeatures,numFeatureRequests,featureRequests);
//
//
//    }
//
//
//
//    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
//    public ArrayList<String> popularNFeatures(int numFeatures,
//                                              int topFeatures,
//                                              List<String> possibleFeatures,
//                                              int numFeatureRequests,
//                                              List<String> featureRequests) {
//        // WRITE YOUR CODE HERE
//        ArrayList<String> popularFeatures = new ArrayList<String>();
//        ArrayList<String> popularResultFeatures = new ArrayList<String>();
//        for (int i = 0; i < featureRequests.size(); i++) {
//            for (int j = 0; j < possibleFeatures.size(); j++) {
//
//                if (featureRequests.get(i).contains(possibleFeatures.get(j))) {
//                    popularFeatures.add(possibleFeatures.get(j));
//                 }
//
//            }
//        }
//
//        popularResultFeatures = countFrequencies(popularFeatures);
//        return popularResultFeatures ;
//
//    }
//
//
//    public static ArrayList<String>  countFrequencies(ArrayList<String> popularFeatures)
//    {
//        ArrayList<String> popularResultFeatures = new ArrayList<String>();
//        Map<String, Integer> hm = new HashMap<String, Integer>();
//
//        for (String i : popularFeatures) {
//            Integer j = hm.get(i);
//            hm.put(i, (j == null) ? 1 : j + 1);
//        }
//
//        // displaying the occurrence of elements in the arraylist
//        for (Map.Entry<String, Integer> val : hm.entrySet()) {
//            System.out.println("Element " + val.getKey() + " "
//                    + "occurs"
//                    + ": " + val.getValue() + " times");
//            popularResultFeatures.add(val.getKey());
//
//
//
//        }
//
//        System.out.println("popularResultFeatures ::: " + popularResultFeatures.size());
//        return popularResultFeatures;
//    }
//
//
//    int minimumDays(int rows, int columns, ArrayList<ArrayList<Integer> > grid)
//    {
//
//        ArrayList<String> row1 =  new ArrayList<>();
//        ArrayList<String> column1 =  new ArrayList<>();
//
//        for(int i = 0;i> rows;i++){
//            ArrayList<Integer> grd = grid.get(1);
//
//            for(int j = 0;i> columns;i++){
//                  int newGrid  =  grd.get(j);
//
//                  if(newGrid == 0 ){
//
//                  }
//            }
//        }
//
//        return 0;
//    }
//
//}
