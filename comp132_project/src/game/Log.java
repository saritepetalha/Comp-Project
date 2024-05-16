package game;

public class Log {
	
	private String events = "";
	
	public void writeEvent(String text){
		events += (text + "\n");
	}
	
	/**
	 * Writes an event to the log.
	 *
	 * @param text The text of the event to be written.
	 */
	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}
	
}
