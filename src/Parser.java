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
				
				String fileCurrentlyBeingConverted = file.toString().substring(file.toString().lastIndexOf("\\") + 1, file.toString().length());				
				this.lblProgressInfo.setText("Converting " + fileCurrentlyBeingConverted);
				
				this.setDestination(file);
				this.createTextFile(file);
				this.parse(file);
				
			}
			
		}
		
		this.lblProgressInfo.setText("Done.");
		
	}
	
	private void parse(File file) {
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.destination), "utf-8"))) {
            writer.write(getText(file));
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
	}
	
	private void createTextFile(File file) {
		
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.destination), "utf-8"))) {
            writer.write("");
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
	}
	
	private void setDestination(File file) {
		
		String fileString = file.toString().substring(file.toString().lastIndexOf('\\') + 1);
        fileString = fileString.replace(".pdf", "") + ".txt";
        String destinationString = this.destinationFileDirectory.toString().replace("F:\\", "F:/") + "/" + fileString;
        this.destination = destinationString;
        
	}
	
	private String getText(File file) {
		
		String text = "";
		COSDocument cosDoc = null;
		PDDocument pdDoc = null;
		
	   	try {
	   		
	   		this.parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
	   		parser.parse();
	   		
	   		cosDoc = parser.getDocument();
	   		pdDoc = new PDDocument(cosDoc);
	   		
	   		text = textStripper.getText(pdDoc);
	   		
	   	} catch (Exception e) {
	   		e.printStackTrace();
	   	} finally {
	   		
	   		try {
	   			
	   			if (cosDoc != null) {
                    cosDoc.close();
                }
	   			if (pdDoc != null) {
	   				pdDoc.close();
	   			}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	   		
	   	}
	   	
	   	return text;
		  
	  }
	
}