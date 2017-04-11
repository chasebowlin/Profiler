import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class ProfilerGUI extends JFrame {
	
	//allTimes holds data for each and every time
	//a timer was called and ran
	JTable allTimes;
	//results holds the data for the average time etc.
	JTable results;
	//counters holds the data for all of the counters
	JTable counters;

	JScrollPane aTimesPane;
	JScrollPane resultsPane;
	JScrollPane countersPane;

	JLabel aTimes;
	JLabel resultsLabel;
	JLabel counterLabel;

	public ProfilerGUI(HashMap<String, ArrayList<Duration>> durs, HashMap<String, Integer> counts) {
		//set up the window
		super("report");

		//set the layout of the frame
		GridLayout layout = new GridLayout(4,1);
		layout.setVgap(0);
		setLayout(layout);

		//set the location of the window
		setLocation(100, 10);
		//set the size of the window
		setSize(500, 700);

		//-----
		//set up the label for the table and scroll pane
		aTimes = new JLabel("<html><head><body><font face = 'Tahoma' size='8'>Individual Timers</font></body></html>", SwingConstants.CENTER);

		//set up the allTimes table using html and gathering all the
		//data from the durations hash map
		allTimes = new JTable(new DefaultTableModel(new Object[]{"Timer","Duration","Start Message", "End Message"}, 0)){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		DefaultTableModel timesModel = (DefaultTableModel) allTimes.getModel();
		
		//go through duration hashmap and get all of the data
		for(Map.Entry<String, ArrayList<Duration>> entry: durs.entrySet()) {
			String key = entry.getKey();
			ArrayList<Duration> durations = entry.getValue();
			for(Duration duration : durations) {
				Long time = duration.getEndTime() - duration.getStartTime();
				timesModel.addRow(new Object[]{key, time, duration.getStartMessage(), duration.getEndMessage()});
			}
		}
		aTimesPane = new JScrollPane(allTimes);

		JPanel timesPanel = new JPanel();
		timesPanel.setLayout(new GridLayout(2, 1));
		timesPanel.add(aTimes);
		timesPanel.add(aTimesPane);
		//-----


		//-----
		//set up the label
		resultsLabel = new JLabel("<html><body><font face = 'Tahoma' size='8'>Timer Stats</font></body></html>", SwingConstants.CENTER);
		//set up the results table 
		results = new JTable(new DefaultTableModel(new Object[]{"Timer", "Average Time", "Longest Time", "Shortest Time"}, 0)) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};
		DefaultTableModel resultModel = (DefaultTableModel) results.getModel();

		//go through the duration hashmap and get all the data needed
		for(Map.Entry<String, ArrayList<Duration>> entry: durs.entrySet()) {
			String key = entry.getKey();
			ArrayList<Duration> durations = entry.getValue();

			Long avgTime = new Long(0);
			Long longTime = new Long(0);
			Long shortTime = new Long(0);


			for(Duration duration : durations) {
				//get the duration of the specific timer
				Long time = duration.getEndTime() - duration.getStartTime();
				avgTime = avgTime + time;

				//find and set the longest time
				if(longTime == 0) {
					longTime = time;
				}
				else if(time > longTime) {
					longTime = time;
				}
				else{} //stay the same
				//find and set the shortest time
				if(shortTime == 0) {
					shortTime = time;
				}
				else if(time < shortTime) {
					shortTime = time;
				}
				else{} //stay the same
			}	
			//find the average
			avgTime = avgTime / durations.size();

			//add the row
			//timesModel.addRow(new Object[]{key, time, duration.getStartMessage(), duration.getEndMessage()});
			resultModel.addRow(new Object[]{key, avgTime, longTime, shortTime});

		}
		//add the table to the scroll pane
		resultsPane = new JScrollPane(results);

		//create a new panel to hold info
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(2, 1));
		resultPanel.add(resultsLabel);
		resultPanel.add(resultsPane);
		//-----


		//-----
		//set up the table for the counters
		counterLabel = new JLabel("<html><head><body><font face = 'Tahoma' size='8'>Individual Counters</font></body></html>", SwingConstants.CENTER);

		//set up the counters table
		counters = new JTable(new DefaultTableModel(new Object[]{"Counter", "value"}, 0)) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		DefaultTableModel counterModel = (DefaultTableModel) counters.getModel();

		//go through the counter hash map
		for(Map.Entry<String, Integer> entry: counts.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			//add the row to the table
			counterModel.addRow(new Object[]{key, value});
		}
		countersPane = new JScrollPane(counters);

		JPanel counterPanel = new JPanel();
		counterPanel.setLayout(new GridLayout(2, 1));
		counterPanel.add(counterLabel);
		counterPanel.add(countersPane);

		//-----




		add(timesPanel);
		add(resultPanel);
		add(counterPanel);

		//set the default close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//make the window visible
		setVisible(true);
	}
}