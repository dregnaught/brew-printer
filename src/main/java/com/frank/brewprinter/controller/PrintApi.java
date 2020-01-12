package com.frank.brewprinter.controller;

import java.awt.print.PrinterException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frank.brewprinter.LabelWriter;
import com.frank.brewprinter.model.Brew;

@RestController()
@RequestMapping("/print")
public class PrintApi {

	public PrintApi(LabelWriter labelWriter) {
		super();
		this.labelWriter = labelWriter;
	}

	private LabelWriter labelWriter;

	@PostMapping("/")
	public Brew printBrew(@RequestBody Brew brew) throws PrinterException {
		labelWriter.printLabel(brew.getName(), brew.getAbv());
		return brew;
	}

}
