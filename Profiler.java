import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;


public class Profiler {
	//reference to a shared profiler
	private static Profiler instance = null;

	//hashmap to hold array list of Durations
	private static HashMap<String, ArrayList<Duration>> durations = new HashMap<String, ArrayList<Duration>>();

	//hasmap to hold all of the counters
	private static HashMap<String, Integer> counters = new HashMap<String, Integer>();

	//switch for whether or not the functions for the timer will be used
	boolean enabled = true;



	//==========================================================================
	//private constructor can only be called from a method
	private Profiler() {};
	//==========================================================================


	//==========================================================================
	//static method that returns a reference to the one Profiler object
	public static Profiler getInstance() {

		//if the one instance has not been created yet
		if(instance == null) {
			//create the one instance
			instance = new Profiler();
		}
		return instance;
	}
	//==========================================================================



	//==========================================================================
	//start method take in a name as the identifier
	public void start(String ident) {
		//check to make sure that everything is enabled
		System.out.println(enabled);
		if(enabled == true) {
			//check to see if the timer with the identifier is already running
			if(durations.containsKey(ident)) {
				if(durations.get(ident).get(durations.get(ident).size() - 1).getEndTime() == null) {
					System.out.println("TRIED TO START TIMER WITH IDENTIFER: " + ident + "WITHOUT STOPING");
					ProfilerException startException = new ProfilerException();
					return;
				}
			}

			//create a new duration
			Duration d = new Duration();
			Long start = System.nanoTime();

			d.setStartTime(start);


			//if the key does not exist
			if(!durations.containsKey(ident)) {
				//add an array list with the key = the identifier to the hashmap
				durations.put(ident, new ArrayList<Duration>());
			}

			//add the duration to the array list in the hashmap
			durations.get(ident).add(d);

		}
	}
	 //start method takes in an identifier and a message
	 public void start(String ident, String message) {
	 	//check to make sure that everything is enabled
		if(enabled == true) {
		 	//check to see if the timer with the identifier is already running
			if(durations.containsKey(ident)) {
				if(durations.get(ident).get(durations.get(ident).size() - 1).getEndTime() == null) {
					System.out.println("TRIED TO START TIMER WITH IDENTIFER: " + ident + "WITHOUT STOPING");
					ProfilerException startException = new ProfilerException();
					return;
				}
			}

		 	//create a new duration
		 	Duration d = new Duration();
		 	Long start = System.nanoTime();

		 	//set the values of the duration
		 	d.setStartTime(start);
		 	d.setStartMessage(message);

		 	if(!durations.containsKey(ident)) {
		 		//add an array list with the key = the identifier to the hashmap
		 		durations.put(ident, new ArrayList<Duration>());
		 	}

		 	//add the duration to the array list in the hashmap
		 	durations.get(ident).add(d);
		}	
	 }
	//==========================================================================


	//==========================================================================
	 //stop method takes in just an identifier
	 public void stop(String ident) {
	 	//check to make sure that everything is enabled
		if(enabled == true) {
		 	//if there is no duration / timer with that identifier
		 	if (!durations.containsKey(ident)) {
		 		System.out.println("IDENTIFER " + ident + "DOES NOT EXIST");
				ProfilerException startException = new ProfilerException();
				return;
		 	}

		 	else if(durations.containsKey(ident)) {
				if(durations.get(ident).get(durations.get(ident).size() - 1).getEndTime() != null) {
					System.out.println("TRIED STOPING TIMER WITH IDENTIFER " + ident + "WITHOUT STARTING IT");
					ProfilerException startException = new ProfilerException();
					return;
				}
			}

		 	Long stop = System.nanoTime();

		 	//search through the hashmap or arraylist for the element
		 	durations.get(ident).get(durations.get(ident).size() - 1).setEndTime(stop);
		}
	}

	 //stop method takes in an identifier and a message
	 public void stop(String ident, String message) {
	 	//check to make sure that everything is enabled
		if(enabled == true) {
		 	 //if there is no duration / timer with that identifier
		 	if (!durations.containsKey(ident)) {
		 		System.out.println("IDENTIFER " + ident + "DOES NOT EXIST");
				ProfilerException startException = new ProfilerException();
				return;
		 	}

		 	else if(durations.containsKey(ident)) {
				if(durations.get(ident).get(durations.get(ident).size() - 1).getEndTime() != null) {
					System.out.println("TRIED STOPING TIMER WITH IDENTIFER " + ident + "WITHOUT STARTING IT");
					ProfilerException startException = new ProfilerException();
					return;
				}
			}


		 	Long stop = System.nanoTime();

		 	//search through the hashmap and arraylist fo the element
		 	durations.get(ident).get(durations.get(ident).size() - 1).setEndTime(stop);
		 	durations.get(ident).get(durations.get(ident).size() - 1).setEndMessage(message);
		 }
	}
	//==========================================================================


	//==========================================================================
	 //takes in a identifier
	 public void count(String ident) {
	 	//check to make sure that everything is enabled
		if(enabled == true) {
		 	//if the key doesn't exist yet
		 	if(!counters.containsKey(ident)) {
		 		counters.put(ident, 0);
		 	}

		 	//else its in there
		 	//always add one onto the counter when method is called
		 	int temp = counters.get(ident) + 1;
		 	counters.put(ident, temp);
		}
	 }
	//==========================================================================

	//==========================================================================
	//toggle for enabling the timer
	public void setEnabled(boolean b) {
		//change the value to what ever the user enters
		enabled = b;
	}
	//==========================================================================


	//==========================================================================
	 //generates the report
	 public void generateReport() {
	 	ProfilerGUI report = new ProfilerGUI(durations, counters);
	 }
	//==========================================================================
}