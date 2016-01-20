import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JLabel;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Parser {

	private File[] targetPDFFiles;
	private File destinationFileDirectory;
	private String destination;
	private PDFTextStripper textStripper;
	private PDFParser parser;
	private JLabel lblProgressInfo;
	private String nameOfFileCurrentlyBeingConverted;
	private COSDocument cosDoc = null;
	private PDDocument pdDoc = null;
	
	public Parser(File targetFileDirectory, File destinationFileDirectory, JLabel lblProgressInfo) {
		
		this.targetPDFFiles = targetFileDirectory.listFiles();
		this.destinationFileDirectory = destinationFileDirectory;
		this.lblProgressInfo = lblProgressInfo;
		
		try {
			this.textStripper = new PDFTextStripper();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void parseAll() {
		
		for (File file : this.targetPDFFiles) {
			
			if (file.toString().contains(".pdf")) {
				
				this.setNameOfFileCurrentlyBeingConverted(file);
				this.setDestination(file);
				this.updateProgressInfo(file);
				
				this.parse(file);
				
			}
			
		}
		
		this.lblProgressInfo.setText("Done.");
		
	}
	
	private void setNameOfFileCurrentlyBeingConverted (File file) {
		
		//e.g. "Bible_King_James_Version"
		this.nameOfFileCurrentlyBeingConverted = (file.toString().substring(file.toString().lastIndexOf("\\") + 1)).replace(".pdf", "");
		
	}
	
	private void setDestination(File file) {
		
		//e.g. "C:\Users\klein\Documents\pdf work related\txtDirectories\religious\Bible_King_James_Version.txt"
        this.destination = this.destinationFileDirectory.toString() + "\\" + this.nameOfFileCurrentlyBeingConverted + ".txt";
        
	}
	
	private void updateProgressInfo(File file) {
			
		this.lblProgressInfo.setText("Converting " + this.nameOfFileCurrentlyBeingConverted);
		
	}
	
	private void parse(File file) {
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.destination), "utf-8"))) {
            writer.write(getText(file));
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
	}
	
	private String getText(File file) {
		
		String text = "";
		
	   	try {
	   		
	   		this.parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
	   		this.parser.parse();
	   		
	   		this.cosDoc = this.parser.getDocument();
	   		this.pdDoc = new PDDocument(this.cosDoc);
	   		
	   		text = this.textStripper.getText(this.pdDoc);
	   		
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	} finally {
	   		
	   		try {
	   			
	   			if (this.cosDoc != null) {
                    this.cosDoc.close();
                }
	   			if (this.pdDoc != null) {
	   				this.pdDoc.close();
	   			}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	   		
	   	}
	   	
	   	return text;
		  
	  }
	
}