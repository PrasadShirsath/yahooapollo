import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class YahooEventReader {
	
	public List<YahooEvent> fetchScrubbedEvent() throws NumberFormatException, IOException{
		
		BufferedReader br = new BufferedReader(new FileReader("output.txt"));

		String line;

		List<YahooEvent> yahooEvents = new ArrayList<YahooEvent>();

		while((line = br.readLine()) != null) {

			String[] elems = line.split("\t");

			int severity = Integer.parseInt(elems[0]);
			int count = Integer.parseInt(elems[1]);

			String node = elems[2];
			String property = elems[3];
			String firstOccurence = elems[4];

			firstOccurence = firstOccurence.replaceAll("-", "\\/");

			String summary = elems[5];
			
			
			// Create event object
			YahooEvent event = new YahooEvent();
			
			event.severity = severity;
			event.count = count;
			
			event.node = node;
			event.property = property;
			
			Calendar ct_new = Calendar.getInstance();
			ct_new.setTime(new Date(firstOccurence));
			
			event.firstOccurence = ct_new;
			
			event.summary = summary;
			
			Calendar ct_lat = Calendar.getInstance();
			ct_lat.setTime(new Date(firstOccurence));
			ct_lat.add(Calendar.MINUTE, count);
			
			event.latestTime = ct_lat;
			
			// put 
			yahooEvents.add(event);
		}
		
		br.close();
		
		return yahooEvents;
		
	}

	public static void main(String args[]) throws IOException{

		
	}

}
