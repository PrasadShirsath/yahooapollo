import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class ScrubEvents {
	
	public static void main(String args[]) throws IOException {
		
		HashMap<String, YahooEvent> yahooEvents = new HashMap<String, YahooEvent>();
		
		BufferedReader br = new BufferedReader(new FileReader("final_alert_file.csv"));
		
		int i=0;
		String line;
		
		while((line = br.readLine()) != null) {
			
			if( i == 0) {
				i++;
				continue;
			}
			
			String[] elems = line.split("\\|");
			
			int severity = Integer.parseInt(elems[0]);
			int count = Integer.parseInt(elems[1]);
			
			String node = elems[2];
			String property = elems[3];
			String firstOccurence = elems[4];
			
			firstOccurence = firstOccurence.replaceAll("-", "\\/");
			
			String summary = elems[5];
			
			String key = node + "-" + property ;
			
			if(yahooEvents.containsKey(key)) {
				
				YahooEvent event = yahooEvents.get(key);
				
				Calendar ct_curr = Calendar.getInstance();
				ct_curr.setTime(new Date(firstOccurence));
				ct_curr.add(Calendar.DATE, count);
				
				
				if(ct_curr.compareTo(event.latestTime) < 0){
					
					System.out.println("later found.");
					System.out.println(event.node + " " + event.property + " " + event.firstOccurence.getTime());
					System.out.println(node + " " + property + " " + ct_curr.getTime());
					
					event.count = count;
					event.severity = severity;
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
					
					yahooEvents.put(key, event);
					
				} else {
					System.out.println("Lesssss");
				}
				
			} else {
				YahooEvent event = new YahooEvent();
				
				event.severity = severity;
				event.count = count;
				
				event.node = node;
				event.property = property;
				
				Calendar ct_new = Calendar.getInstance();
				System.out.println(firstOccurence);
				ct_new.setTime(new Date(firstOccurence));
				
				event.firstOccurence = ct_new;
				
				event.summary = summary;
				
				Calendar ct_lat = Calendar.getInstance();
				ct_lat.setTime(new Date(firstOccurence));
				ct_lat.add(Calendar.MINUTE, count);
				
				event.latestTime = ct_lat;
				
				yahooEvents.put(key, event);
			}
			
		}
		
		br.close();
		
		
		PrintWriter pw = new PrintWriter("output.txt");
		
		for(String key : yahooEvents.keySet() ) {
		
			YahooEvent event = yahooEvents.get(key);
			
			pw.println(event.severity + "\t" 
						+ event.count + "\t"
						+ event.node + "\t"
						+ event.property + "\t"
						+ event.firstOccurence.getTime() + "\t"
						+ event.summary);
			
			pw.flush();
			
			
		}
		
		pw.close();
		
		
	}

}

class YahooEvent{
	
	int severity;
	int count;
	
	String node;
	String property;
	Calendar firstOccurence;
	
	String summary;
	
	Calendar latestTime;
	
}
