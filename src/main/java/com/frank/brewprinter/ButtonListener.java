package com.frank.brewprinter;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.frank.brewprinter.model.Brew;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ButtonListener implements GpioPinListenerDigital {

	private static final int DELAY_BETWEEN_CLICKS = 300;
	private RestTemplate restTemplate = new RestTemplate();
	private static final Logger logger = Logger.getLogger(ButtonListener.class.getName());
	
	public ButtonListener(int buttonNumber) {
		super();
		this.buttonNumber = buttonNumber;
	}

	private int buttonNumber;
	private Date lastHit = new Date();
	
	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if(PinState.LOW.equals(event.getState()) && (new Date().getTime() - lastHit.getTime() > DELAY_BETWEEN_CLICKS)) { //delay, in case of double-click
			logger.log(Level.FINE, "Button:" + buttonNumber + " pressed " + event.getPin().getName());
			lastHit = new Date();
			logger.log(Level.FINER, "Invoking API: " + "http://localhost:8080/brews/" + buttonNumber);
			ResponseEntity<Brew> myBrew = restTemplate.postForEntity("http://localhost:8080/brews/" + buttonNumber, null, Brew.class);
		}
	}

}
