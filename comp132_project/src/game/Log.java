package game;

public class Log {
	
	private String events = "";
	
	public void writeEvent(String text){
		events += (text + "\n");
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}
	
}
