package Parser;

import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

public abstract class ParserSuperClass {

	private File[] targetPDFFiles;
	private JLabel lblProgressInfo;
	private PDFTextStripper textStripper;
	private PDFParser parser;
	private COSDocument cosDoc = null;
	protected PDDocument pdDoc = null;
	protected File destinationFileDirectory;
	protected String destination;
	protected String nameOfFileCurrentlyBeingParsed;
	protected String text;
	
	public ParserSuperClass(File targetFileDirectory, File destinationFileDirectory, JLabel lblProgressInfo) {
		
		this.targetPDFFiles = targetFileDirectory.listFiles();
		this.destinationFileDirectory = destinationFileDirectory;
		this.lblProgressInfo = lblProgressInfo;
		this.initialiseTextStripper();
		
	}
	
	private void initialiseTextStripper() {
		
		try {
			this.textStripper = new PDFTextStripper();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void parseAll() {
		
		for (File file : this.targetPDFFiles) {
			
			if (file.toString().contains(".pdf")) {
				
				this.setNameOfFileCurrentlyBeingParsed(file);
				this.updateProgressInfo(file);
				this.initialisePDFBoxDocs(file);
				this.parse(file);
				this.closeDocs();
				
			}
			
		}
		
		this.lblProgressInfo.setText("Done.");
		
	}
	
	private void setNameOfFileCurrentlyBeingParsed (File file) {
		
		//e.g. "Bible_King_James_Version"
		this.nameOfFileCurrentlyBeingParsed = (file.toString().substring(file.toString().lastIndexOf("\\") + 1)).replace(".pdf", "");
		
	}
	
	private void updateProgressInfo(File file) {
		
		this.lblProgressInfo.setText(this.getUpdateProgressVerb() + this.nameOfFileCurrentlyBeingParsed);
		
	}
	
	protected abstract String getUpdateProgressVerb();
	
	private void initialisePDFBoxDocs(File file) {
		
		try {
			
			this.parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
			this.parser.parse();
	   		this.cosDoc = this.parser.getDocument();
	   		this.pdDoc = new PDDocument(this.cosDoc);
	   		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	protected abstract void parse(File file);
	
	private void closeDocs() {
		
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
	
	protected String getTextFromPDFFile() {
		
		String text = "";
		
		try {
			text = this.textStripper.getText(this.pdDoc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text;
		
	}

}
