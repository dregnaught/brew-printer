package com.frank.brewprinter;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.print.PrintService;

import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

@Component
public class LabelWriter {

	private static final int MAX_CHARS_PER_NAME_LINE = 15;
	private static final String PRINTERNAME = "DYMO_LabelWriter_450";
	private static final double PAPER_WIDTH_INCHES = 1;
	private static final double PAPER_HEIGHT_INCHES = 1;
	private static final double PAPER_WIDTH_PT = (PAPER_WIDTH_INCHES * 72);
	private static final double PAPER_HEIGHT_PT = (PAPER_HEIGHT_INCHES * 72);
	private static final int BASE_MARGIN_X = 0;
	private static final int BASE_MARGIN_Y = 25;
	private static final String LABEL_LOGO = "MSB";
	private static final Logger logger = Logger.getLogger(LabelWriter.class.getName());

	PrinterJob printerJob = PrinterJob.getPrinterJob();
	PageFormat pageFormat = printerJob.defaultPage();
	PrintService printService;
	Paper paper = new Paper();

	@PostConstruct
	public void init() {
		paper.setSize(PAPER_WIDTH_PT, PAPER_HEIGHT_PT);
		paper.setImageableArea(0, 0, PAPER_WIDTH_PT, PAPER_HEIGHT_PT);

		pageFormat.setPaper(paper);

		pageFormat.setOrientation(PageFormat.LANDSCAPE);

		PrintService[] printServices = PrinterJob.lookupPrintServices();
		for (int i = 0; i < printServices.length; i++) {
			logger.log(Level.INFO, "Printer available: " + printServices[i].getName());

			if (printServices[i].getName().contains(PRINTERNAME)) {
				logger.log(Level.INFO, "Dymo Printer " + PRINTERNAME + " found: " + printServices[i].getName());
				printService = printServices[i];
			}
		}
		
		if(printService == null) {
			throw new RuntimeException("Dymo Printer " + PRINTERNAME + " not found. Bailing.");
		}
	}

	public void printLabel(final String beername1, final String abv)
			throws PrinterException {
		printerJob.setPrintService(printService);
		printerJob.setPrintable(new Printable() {
			@Override
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
				if (pageIndex < getPageNumbers()) {
					Graphics2D g = (Graphics2D) graphics;
					g.translate(10, 10);

					int working_height = BASE_MARGIN_Y;
					String[] myName = wrap(beername1);
					
					Font nameFont = new Font("Courier New", g.getFont().getStyle(), 7);
					g.setFont(nameFont);
					
					// beer name
					//g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 7));
					working_height += 10;
					g.drawString(myName[0], BASE_MARGIN_X, working_height);

					// beer name line 2 (if available)
					if (myName.length > 1) {
						working_height += 8;
						g.drawString(myName[1], BASE_MARGIN_X, working_height);
					}
					
					// beer name line 3 (if available)
					if (myName.length > 2) {
						working_height += 8;
						g.drawString(myName[2], BASE_MARGIN_X, working_height);
					}

					// ABV
					g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 6));
					working_height += 10;
					g.drawString("ABV: " + abv + "%", BASE_MARGIN_X + 10, working_height);

					// LOGO
					InputStream ttf = this.getClass().getClassLoader().getResourceAsStream("3of9.TTF");

					Font font = null;
					try {
						font = Font.createFont(Font.TRUETYPE_FONT, ttf);
					} catch (FontFormatException | IOException ex) {
						logger.log(Level.SEVERE, null, ex);
					}
					g.setFont(font);
					g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 24));
					g.drawString(LABEL_LOGO, BASE_MARGIN_X, BASE_MARGIN_Y);

					return PAGE_EXISTS;
				} else {
					return NO_SUCH_PAGE;
				}
			}
		}, pageFormat); // The 2nd param is necessary for printing into a label width a right landscape
						// format.
		logger.log(Level.INFO, "Printing page for " + beername1);
		printerJob.print();
	}

	public int getPageNumbers() {
		return 1;
	}
	
	private String[] wrap(String theLine) {
		String F = WordUtils.wrap(theLine, MAX_CHARS_PER_NAME_LINE);
	    return centerLines(F.split(System.lineSeparator()));
	}
	
	private String[] centerLines(String[] theLines) {
		for(int i = 0; i < theLines.length; i++) {
			int myPads = (int)((MAX_CHARS_PER_NAME_LINE - theLines[i].trim().length()) / 2);
			for(int j = 0; j < myPads; j++) {
				theLines[i] = " " + theLines[i];
			}
		}
		return theLines;
	}
}
