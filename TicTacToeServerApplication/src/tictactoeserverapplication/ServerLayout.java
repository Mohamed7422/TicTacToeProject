package tictactoeserverapplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
    private PieChart pieChart;
    DataBaseAccessLayer dao = new DataBaseAccessLayer();

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
                new PieChart.Data("Offline", 0),
                new PieChart.Data("Online", 0),
                new PieChart.Data("InGame", 0));
        
        pieChart = new PieChart(pieChartData);
        
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

        
        //Code for updating NumberAxis
        //step 1:Get games list
        List<Game> gamesList = dao.getGamesList();
        //retrive the games per each day in a list of game count per day
        List<GameCountPerDay> gamesPerDayList = new ArrayList<>();
        
        //step 2: create list of dates
        List<Date> dates = new ArrayList<>();
        //loop over gamelist and add date for every game with no duplicate
        for (Game game : gamesList) {
            
          Date gameDate=   game.getDateTime();
            if (!dates.contains(gameDate)) {
                dates.add(gameDate);
                
                //count games for this date
                Long gameCount =gamesList.stream()
                        .filter( g -> g.getDateTime().equals(gameDate)).count();
                
                //create object for game perday  and add the game date and game count
                GameCountPerDay gamesPerDay = new GameCountPerDay(gameDate, gameCount.intValue());
                gamesPerDayList.add(gamesPerDay);
            }
        }
        
        
        //Creating Line Graph
        NumberAxis xAxis = new NumberAxis(0, 30, 5);
        xAxis.setLabel("Number Of Days");

        //Defining the y axis   
        NumberAxis yAxis = new NumberAxis(0, 100, 10);
        yAxis.setLabel("Number Of Games");

        //Creating the line chart 
        LineChart linechart = new LineChart(xAxis, yAxis);

        //Prepare XYChart.Series objects by setting data 
        XYChart.Series series = new XYChart.Series();
        series.setName("Rate of Playing");
         
        //update data here
        for (GameCountPerDay gameCountPerDay : gamesPerDayList) {
            series.getData().add(new XYChart.Data(gameCountPerDay.getDate(),gameCountPerDay.getGameCount()));
        }
        //Setting the data to Line chart    
        linechart.getData().add(series);

        vbox.getChildren().addAll(pieChart, linechart);
        setCenter(vbox);

    }
    
    public void updatePieChart(){
    
       
      int onlinePlayers =   dao.getOnlinePlayers().size();
      int offlinePlayers = dao.getOfflinePlayers().size();
      int inGameCount = dao.getIngamePlayers().size();
      
      pieChart.getData().get(0).setPieValue(onlinePlayers);
      pieChart.getData().get(1).setPieValue(offlinePlayers);
      pieChart.getData().get(2).setPieValue(inGameCount);

    
    }
}