package com.juanhg.mindwavestar;

import java.net.ConnectException;
import processing.core.PApplet;
import processing.core.PFont;
import processing.video.Capture;

import neurosky.*;


/**
 * @author Juan Hernández García
 */
public class MindWaveStar extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Declaracion de variables
	ThinkGearSocket neuroSocket;
	//Medidas
	int attention = 0;
	int meditation = 0;
	int blinkSt = 0;
	int poorSignal = 0;
	int blink = 0;
	//Fuente de letra
	PFont font;
	
	public void setup() 
	{
	  //Establecemos el tamaño de la aplicacion
	  size(640, 360);
	  
	  //Creamos el nuevo socket
	  ThinkGearSocket neuroSocket = new ThinkGearSocket(this);
	  try 
	  {
	    //Comenzamos la conexion
	    neuroSocket.start();
	  } 
	  catch (ConnectException e) {
	    e.printStackTrace();
	  }
	  
	  //Para dibujar suavizados los elementos geometricos
	  smooth();
	  //Cargamos fuente de letra
	  font = loadFont("D:\\EclipseWorkSpace\\Processing\\HelloMindWavePro\\data\\TimesNewRomanPSMT-20.vlw");
	  textFont(font);
	  //Indicamos los frames por segundo
	  frameRate(30);
	  //Deshabilitamos el dibujado de bordes
	  noStroke();
	}
	 
	//Bucle de dibujo
	public void draw() 
	{
	  //Fondo negro
	  background(0);
	  
	  //Calculos para correcta visualizacion y eliminacion
	  //del valor del parpadeo
	  if (blink>0) 
	  {
	    if (blink>15) 
	    {
	      blink = 0;
	    } 
	    else 
	    {
	      blink++;
	    }
	  }
	  else{
		  blinkSt = 0;
	  }
	 
	  //Mostramos los valores por pantalla
	  //con color blanco.
	  fill(255, 255, 255);
	  text("Attention: "+attention, 75, 60);
	  text("Meditation: "+meditation, 275, 60);
	  text("Blink: " + blinkSt, 480, 60);
	  text("PoorSignal: " + poorSignal, 275, 300);

	  
	  //Dibujamos una estrella de tres puntas 
	  //de color rojo
	  fill(255, 0, 0);
	  pushMatrix();
	  translate((float)(width*0.2),(float)(height*0.5));
	  rotate((float) (frameCount / 200.0));
	  star(0, 0, 5, 40, 3); 
	  popMatrix();
	  
	  //Dibujamos una estrella de veinte puntas 
	  //de color azul
	  fill(0, 0, 255);
	  pushMatrix();
	  translate((float)(width*0.5),(float)(height*0.5));
	  rotate((float) (frameCount / 50.0));
	  star(0, 0,20, 40, 20); 
	  popMatrix();
	  
	  //Dibujamos una estrella de cinco puntas 
	  //de color amarillo
	  fill(255, 255, 0);
	  pushMatrix();
	  translate((float)(width*0.8),(float)(height*0.5));
	  rotate((float) (frameCount / -100.0));
	  star(0, 0, 10, 40, 5); 
	  popMatrix();
	  
	  //** SHADOWS **/
	  
	  //Dibujamos las sombras, con los mismos colores
	  //que las estrellas originales pero transparentes
	  
	  fill(255, 0, 0,160);
	  pushMatrix();
	  translate((float)(width*0.2),(float)(height*0.5));
	  rotate((float) (frameCount / 200.0));
	  star(0, 0, 5 + attention/2, 40 + attention/2, 3); 
	  popMatrix();
	  
	  fill(0, 0, 255, 160);
	  pushMatrix();
	  translate((float)(width*0.5),(float)(height*0.5));
	  rotate((float) (frameCount / 50.0));
	  star(0, 0,20 + meditation/2, 40 + meditation/2, 20); 
	  popMatrix();
	  
	  fill(255, 255, 0, 160);
	  pushMatrix();
	  translate((float)(width*0.8), (float)(height*0.5));
	  rotate((float) (frameCount / -100.0));
	  star(0, 0, 10 + blinkSt/4, 40 + blinkSt/2, 5); 
	  popMatrix();
	}
	 
	/** EVENTS **/
	/**
	 * Eventos que se lanzan cuando se recibe el mensaje apropiado
	 */
	public void captureEvent(Capture _c) 
	{
	  _c.read();
	}
	 
	public void attentionEvent(int attentionLevel) 
	{
	  attention = attentionLevel;
	}
	 
	public void meditationEvent(int meditationLevel) 
	{
	  meditation = meditationLevel;
	}
	 
	public void blinkEvent(int blinkStrength) 
	{
	  blinkSt = blinkStrength;
	  blink = 1;
	}
	
	public void poorSignalEvent(int signalLevel){
		poorSignal = signalLevel;
	}
	
	//Eventos vacios.
	public void eegEvent(int delta, int theta, int low_alpha, int high_alpha, int low_beta, int high_beta, int low_gamma, int mid_gamma){}
	public void rawEvent(int [] valor){}
	
	/**
	 * Encargada de dibujar estrellas
	 * @param x Posicion espacial x
	 * @param y Posicion espacial y
	 * @param radius1 Primer radio
	 * @param radius2 Segundo radio
	 * @param npoints Numero de puntas
	 */
	void star(float x, float y, float radius1, float radius2, int npoints) {
		  float angle = TWO_PI / npoints;
		  float halfAngle = (float) (angle/2.0);
		  beginShape();
		  for (float a = 0; a < TWO_PI; a += angle) {
		    float sx = x + cos(a) * radius2;
		    float sy = y + sin(a) * radius2;
		    vertex(sx, sy);
		    sx = x + cos(a+halfAngle) * radius1;
		    sy = y + sin(a+halfAngle) * radius1;
		    vertex(sx, sy);
		  }
		  endShape(CLOSE);
		}
	 
	/**
	 * Será invocado cuando se destroya la aplicacion
	 */
	public void stop() {
	  neuroSocket.stop();
	  super.stop();
	}
}