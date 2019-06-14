package program;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.util.Duration;

public class Timer {

	private long ticks = 0; // The number of ticks since start() has been called
	private Timeline t; // The timeline for the entire program
	private boolean isCounting; // Is the Timer counting time since an interval?
	private long count; // Count for tracking intervals
	
	/* Initialize the Timer */
	public Timer(){
		
		t  = new Timeline();
		t.setCycleCount(Timeline.INDEFINITE);
		
		KeyFrame kt = new KeyFrame(Duration.millis(1000 / 60), inc -> {
			this.ticks ++;
			if(isCounting){
				count ++;
			}
		}); 
		t.getKeyFrames().add(kt);
		
	}
	
	/* Adds a new action to the TimeLine to be performed */
	public void addKeyFrame(EventHandler<ActionEvent> ae){
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / 60), ae);
		t.getKeyFrames().add(kf);
	}
	
	/* Returns a String representation of the Timer */
	public String toString(){
		return "Timer [ticks: " + this.ticks + ", isCounting: " + this.isCounting + "]";
	}
	
	/* Starts the Timer */
	public void start(){
		t.play();
	}
	
	/* Starts a count to get the number of ticks passed in an interval */
	public void startCount(){
		this.isCounting = true;
	}
	
	/* Stops the count and returns the number of ticks since startCount() has been called */
	public int stopCount(){
		this.isCounting = false;
		return (int) this.count;
	}
	
	/* */
	
}
