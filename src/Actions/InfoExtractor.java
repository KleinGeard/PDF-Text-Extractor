package Actions;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

public class InfoExtractor {

	private File[] targetPDFFiles;
	private File destinationFileDirectory;
	private String destination;
	private PDFTextStripper textStripper;
	private PDFParser parser;
	private JLabel lblProgressInfo;
	private String nameOfFileCurrentlyBeingExtracted;
	private COSDocument cosDoc = null;
	private PDDocument pdDoc = null;
	private BufferedWriter writer;
	
	public InfoExtractor(File targetFileDirectory, File destinationFileDirectory, JLabel lblProgressInfo) {
		
		this.targetPDFFiles = targetFileDirectory.listFiles();
		this.destinationFileDirectory = destinationFileDirectory;
		this.lblProgressInfo = lblProgressInfo;
		this.destination = this.destinationFileDirectory + "/pdf_info.csv";
		
		try {
			this.textStripper = new PDFTextStripper();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void extractAll() {
		
		this.addCSVHeaders();
		
		for (File file : this.targetPDFFiles) {
			
			if (file.toString().contains(".pdf")) {
				
				this.setNameOfFileCurrentlyBeingExtracted(file);
				this.updateProgressInfo(file);
				
				this.extract(file);
				
			}
			
		}
		
		this.lblProgressInfo.setText("Done.");
		
	}
	
	private void addCSVHeaders() {
		
		if (!new File(this.destination).exists()) {
			
			try {
				
				String csvHeaders = "Title, File Name, Subject, Keywords, Creator, Producer, File Size (Bytes), Number Of Pages, Number Of Lines, Number Of Words, Encrypted";
				this.writer = new BufferedWriter(new FileWriter(this.destination, true));
	            this.writer.write(csvHeaders);
	            this.writer.newLine();
	            this.writer.close();
	            
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void setNameOfFileCurrentlyBeingExtracted (File file) {
		
		//e.g. "Bible_King_James_Version"
		this.nameOfFileCurrentlyBeingExtracted = (file.toString().substring(file.toString().lastIndexOf("\\") + 1)).replace(".pdf", "");
		
	}
	
	private void updateProgressInfo(File file) {
			
		this.lblProgressInfo.setText("Extracting Info From " + this.nameOfFileCurrentlyBeingExtracted);
		
	}
	
	private void extract(File file) {
		
		try {
			
			this.writer = new BufferedWriter(new FileWriter(this.destination, true));
            this.writer.write(this.getInfo(file));
            this.writer.newLine();
            this.writer.close();
            
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String getInfo(File file) {
		
		String info = "";
		String text = "";
		
	   	try {
	   		
	   		this.parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
	   		this.parser.parse();
	   		
	   		this.cosDoc = this.parser.getDocument();
	   		this.pdDoc = new PDDocument(this.cosDoc);
	   		PDDocumentInformation pdDocInfo = this.pdDoc.getDocumentInformation();
	   		
			try {
				text = this.textStripper.getText(pdDoc);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			info = this.addCSVInfo(text, pdDocInfo);
	   		
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
	   	
	   	return info;
		  
	  }
	
	private String addCSVInfo(String text, PDDocumentInformation pdDocInfo) {
		
		String s = "";
		String c = ",";
		
		s += this.getSuitableCSVText(pdDocInfo.getTitle());
   		s += c + this.nameOfFileCurrentlyBeingExtracted;
   		s += c + this.getSuitableCSVText(pdDocInfo.getSubject());
   		s += c + this.getSuitableCSVText(pdDocInfo.getKeywords());
   		s += c + this.getSuitableCSVText(pdDocInfo.getCreator());
   		s += c + this.getSuitableCSVText(pdDocInfo.getProducer());
   		s += c + this.pdDoc.getNumberOfPages(); 
   		s += c + this.getNumberOfLines(text); 
   		s += c + this.getNumberOfWords(text);
   		s += c + this.pdDoc.isEncrypted(); 
		
   		return s;
   		
	}
	
	private String getSuitableCSVText(String s) { //ensures that commas, linebreaks and nulls are not inserted into the .csv file
		
		if (s != null) {
			return s.replace(",", "/").replace("\n", "/");
		} else {
			return " ";
		}
		
	}
	
	private int getNumberOfLines(String text) {
		
		int numberOfLines = 0;
		
		String[] lines = text.split("\n");
		numberOfLines = lines.length;
		
		return numberOfLines;
		
	}
	
	private int getNumberOfWords(String text) {
		
		int numberOfWords = 0;
		
		String[] words = text.split(" ");
		numberOfWords = words.length;
		
		return numberOfWords;
		
	}
	
}