package tictactoeserverapplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
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

        // Creating PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Offline", 0),
                new PieChart.Data("Online", 0),
                new PieChart.Data("InGame", 0));

        pieChart = new PieChart(pieChartData);

        VBox vbox = new VBox();

        // Creating a Pie chart
        PieChart pieChart = new PieChart(pieChartData);

        // Setting the title of the Pie chart
        pieChart.setTitle("Server");

        // Setting the direction to arrange the data
        pieChart.setClockwise(true);

        // Setting the length of the label line
        pieChart.setLabelLineLength(50);

        // Setting the labels of the pie chart visible
        pieChart.setLabelsVisible(true);

        // Creating Line Graph
        NumberAxis xAxis = new NumberAxis(0, 30, 5);
        xAxis.setLabel("Number Of Days");

        // Defining the y axis
        NumberAxis yAxis = new NumberAxis(0, 100, 10);
        yAxis.setLabel("Number Of Games");

        // Creating the line chart
        LineChart linechart = new LineChart<>(xAxis, yAxis);

        // Prepare XYChart.Series objects by setting data
        XYChart.Series<Date, Integer> series = new XYChart.Series<>();
        series.setName("Rate of Playing");

        // Update data here
        List<Date> dates = new ArrayList<>();
        List<GameCountPerDay> gamesPerDayList = new ArrayList<>();
        updateData(series, dates, gamesPerDayList);

        // Setting the data to Line chart
        linechart.getData().add(series);

        vbox.getChildren().addAll(pieChart, linechart);
        setCenter(vbox);

        // Start a background thread to update the UI
        startBackgroundUpdateThread(series, dates, gamesPerDayList);
    }

    private void startBackgroundUpdateThread(XYChart.Series<Date, Integer> series, List<Date> dates, List<GameCountPerDay> gamesPerDayList) {
        Thread updateThread = new Thread(() -> {
            while (true) {
                try {
                    // Sleep for some time (e.g., 1 second) to avoid constant updates
                    Thread.sleep(1000);

                    // Update the UI on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        updatePieChart(); // Update the Pie Chart
                        updateData(series, dates, gamesPerDayList); // Update the Line Chart data
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        updateThread.setDaemon(true);
        updateThread.start();
    }

    private void updateData(XYChart.Series<Date, Integer> series, List<Date> dates, List<GameCountPerDay> gamesPerDayList) {
        List<Game> gamesList = dao.getGamesList();
        series.getData().clear();
        dates.clear();
        gamesPerDayList.clear();

        for (Game game : gamesList) {
            Date gameDate = game.getDateTime();
            if (!dates.contains(gameDate)) {
                dates.add(gameDate);

                long gameCount = gamesList.stream()
                        .filter(g -> g.getDateTime().equals(gameDate))
                        .count();

                GameCountPerDay gamesPerDay = new GameCountPerDay(gameDate, (int) gameCount);
                gamesPerDayList.add(gamesPerDay);
            }
        }

        for (GameCountPerDay gameCountPerDay : gamesPerDayList) {
            series.getData().add(new XYChart.Data<>(gameCountPerDay.getDate(), gameCountPerDay.getGameCount()));
        }
    }

    private void updatePieChart() {
        int onlinePlayers = dao.getOnlinePlayers().size();
        int offlinePlayers = dao.getOfflinePlayers().size();
        int inGameCount = dao.getIngamePlayers().size();

        pieChart.getData().get(0).setPieValue(onlinePlayers);
        pieChart.getData().get(1).setPieValue(offlinePlayers);
        pieChart.getData().get(2).setPieValue(inGameCount);
    }
}