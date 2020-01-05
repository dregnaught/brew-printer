package com.frank.brewprinter;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;

@Component
@Profile("pi")
public class ControllerConfig {

	private final GpioController gpio = GpioFactory.getInstance();
	
	@PostConstruct
	public void setup() {
		
		GpioPinDigitalInput topLeftButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, "TopLeft");
		GpioPinDigitalInput topMiddleButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, "TopMiddle");
		GpioPinDigitalInput topRightButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "TopRight");
		GpioPinDigitalInput middleLeftButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, "MiddleLeft");
		GpioPinDigitalInput CenterButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "Center");
		GpioPinDigitalInput middleRightButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, "MiddleRight");
		GpioPinDigitalInput bottomLeftButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, "BottomLeft");
		GpioPinDigitalInput bottomMiddleButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_21, "BottomMiddle");
		GpioPinDigitalInput bottomRightButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_22, "BottomRight");
		
		topLeftButton.addListener(new ButtonListener(1));
		topMiddleButton.addListener(new ButtonListener(2));
		topRightButton.addListener(new ButtonListener(3));
		middleLeftButton.addListener(new ButtonListener(4));
		CenterButton.addListener(new ButtonListener(5));
		middleRightButton.addListener(new ButtonListener(6));
		bottomLeftButton.addListener(new ButtonListener(7));
		bottomMiddleButton.addListener(new ButtonListener(8));
		bottomRightButton.addListener(new ButtonListener(9));
		
	}
}
