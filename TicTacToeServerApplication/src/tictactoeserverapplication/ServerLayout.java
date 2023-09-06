package tictactoeserverapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ServerLayout extends BorderPane {

    protected final ToggleButton btnToggle;
    protected boolean toggle;
    protected TicTacToeServer server;

    public ServerLayout() {
        
        
        toggle = false;

        btnToggle = new ToggleButton();

        btnToggle.setOnAction((e) -> {
            if (toggle) {
                toggle = false;
                btnToggle.setText("On");
                server.closeServer();
                server.stop();
            } else {
                toggle = true;
                btnToggle.setText("Off");
                server = new TicTacToeServer();
                server.start();
            }

        });

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(btnToggle, javafx.geometry.Pos.TOP_RIGHT);
        btnToggle.setMnemonicParsing(false);
        btnToggle.setText("On");
        BorderPane.setMargin(btnToggle, new Insets(8.0, 8.0, 0.0, 0.0));
        setTop(btnToggle);
        
        
        //Creating PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
         new PieChart.Data("Offline", 13), 
         new PieChart.Data("Online", 25), 
         new PieChart.Data("InGame", 10)); 
          
        VBox vbox = new VBox();
        
        
      //Creating a Pie chart 
      PieChart pieChart = new PieChart(pieChartData); 
              
      //Setting the title of the Pie chart 
      pieChart.setTitle("Server"); 
       
      //setting the direction to arrange the data 
      pieChart.setClockwise(true); 
       
      //Setting the length of the label line 
      pieChart.setLabelLineLength(50); 

      //Setting the labels of the pie chart visible  
      pieChart.setLabelsVisible(true);
      
      
      
      //Creating Line Graph
      
      NumberAxis xAxis = new NumberAxis(0, 30, 5); 
      xAxis.setLabel("Number OfDays"); 
        
      //Defining the y axis   
      NumberAxis yAxis = new NumberAxis   (0, 100, 10); 
      yAxis.setLabel("Number Of Games"); 
        
      //Creating the line chart 
      LineChart linechart = new LineChart(xAxis, yAxis);  
        
      //Prepare XYChart.Series objects by setting data 
      XYChart.Series series = new XYChart.Series(); 
      series.setName("Rate of Playing"); 
        
      series.getData().add(new XYChart.Data(1, 15)); 
      series.getData().add(new XYChart.Data(2, 30)); 
      series.getData().add(new XYChart.Data(25, 60)); 
      series.getData().add(new XYChart.Data(40, 80)); 
      series.getData().add(new XYChart.Data(30, 50)); 
      series.getData().add(new XYChart.Data(10, 20)); 
            
      //Setting the data to Line chart    
      linechart.getData().add(series);
      
      
      
      
      vbox.getChildren().addAll(pieChart,linechart);
        setCenter(vbox);
        
        
        

    }
}
