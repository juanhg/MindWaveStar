package hellomindwavepro;

import java.net.ConnectException;

import processing.core.PApplet;
import processing.core.PFont;
import processing.video.Capture;

public class HelloMindWavePro extends PApplet {

	ThinkGearSocket neuroSocket;
	int attention = 0;
	int meditation = 0;
	int blinkSt = 0;
	PFont font;
	int blink = 0;
	Capture cap;
	
	
	public void setup() 
	{
	  size(640, 480);
	  ThinkGearSocket neuroSocket = new ThinkGearSocket(this);
	  try 
	  {
	    neuroSocket.start();
	  } 
	  catch (ConnectException e) {
	    e.printStackTrace();
	  }
	  smooth();
	  font = loadFont("MyriadPro-Regular-24.vlw");
	  textFont(font);
	  frameRate(25);
	  cap = new Capture(this, width, height);
	  noStroke();
	}
	 
	public void draw() 
	{
	  background(0);
	 
	  image(cap, 0, 0);
	  fill(255, 255, 0);
	  text("Attention: "+attention, 20, 150);
	  fill(255, 255, 0, 160);
	  rect(200, 130, attention*3, 40);
	  fill(255, 255, 0);
	  text("Meditation: "+meditation, 20, 250);
	  fill(255, 255, 0, 160);
	  rect(200, 230, meditation*3, 40);
	 
	  if (blink>0) 
	  {
	    fill(255, 255, 0);
	    text("Blink: " + blinkSt, 20, 350);
	    if (blink>15) 
	    {
	      blink = 0;
	    } 
	    else 
	    {
	      blink++;
	    }
	  }
	}
	 
	void captureEvent(Capture _c) 
	{
	  _c.read();
	}
	 
	void attentionEvent(int attentionLevel) 
	{
	  attention = attentionLevel;
	}
	 
	void meditationEvent(int meditationLevel) 
	{
	  meditation = meditationLevel;
	}
	 
	void blinkEvent(int blinkStrength) 
	{
	  blinkSt = blinkStrength;
	  blink = 1;
	}
	 
	public void stop() {
	  neuroSocket.stop();
	  super.stop();
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { hellomindwavepro.HelloMindWavePro.class.getName() });
	}
}
