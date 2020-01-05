package com.frank.brewprinter;

import java.util.Date;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ButtonListener implements GpioPinListenerDigital {

	private static final int DELAY_BETWEEN_CLICKS = 300;

	public ButtonListener(int buttonNumber) {
		super();
		this.buttonNumber = buttonNumber;
	}

	private int buttonNumber;
	private Date lastHit = new Date();
	
	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if(PinState.LOW.equals(event.getState()) && (new Date().getTime() - lastHit.getTime() > DELAY_BETWEEN_CLICKS)) { //delay, in case of double-click
			System.out.println("Button:" + buttonNumber + " pressed " + event.getPin().getName());
			lastHit = new Date();
		}
	}

}
