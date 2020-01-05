package com.frank.brewprinter.controller;

import java.awt.print.PrinterException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.frank.brewprinter.LabelWriter;
import com.frank.brewprinter.model.Brew;
import com.frank.brewprinter.repository.BrewRepository;

@RestController()
public class BrewsApi {
	
	public BrewsApi(BrewRepository brewRepo) {
		super();
		this.brewRepo = brewRepo;
	}

	private BrewRepository brewRepo;
	private LabelWriter labelWriter;
	
	@GetMapping("/brews")
	public List<Brew> getBrews() {
		return brewRepo.findAll();
	}
	
	@PutMapping("/brews")
	public Brew updateBrew(@RequestBody Brew brew) {
		return brewRepo.save(brew);
	}
	
	@PostMapping("/brews")
	public Brew printBrew(@RequestBody Brew brew) throws PrinterException {
		labelWriter.printLabel(brew.getName(), "", brew.getAbv());
		return brew;
	}
	
	@PostMapping("/brews/{id}")
	public Brew printBrewById(@PathParam("id") Integer id) throws PrinterException {
		Brew myBrew = brewRepo.getOne(id);
		labelWriter.printLabel(myBrew.getName(), "", myBrew.getAbv());
		return myBrew;
	}
	
//	pre-populate if needed.
//	@PostConstruct
//	public void create() {
//		brewRepo.save(new Brew(1, "Thunderbolt IPA", "6.5"));
//		brewRepo.save(new Brew(2, "Straight River IPA", "6.3"));
//		brewRepo.save(new Brew(3, "Cinder Hill Cream Ale", "4.4"));
//		brewRepo.save(new Brew(4, "Cornell Scotch", "5.6"));
//		brewRepo.save(new Brew(5, "Emmett & Phyllis Amber", "5.0"));
//		brewRepo.save(new Brew(6, "Hope Chocolate Milk Stout", "4.1"));
//		brewRepo.save(new Brew(7, "Kaplan's Kolsch", "6.1"));
//		brewRepo.save(new Brew(8, "Sullivan Stout", "4.2"));
//		brewRepo.save(new Brew(9, "Rye Ale", "5.6"));
//	}

}
