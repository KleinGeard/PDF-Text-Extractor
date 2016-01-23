package Parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;

public class InfoExtractor extends ParserSuperClass {

	private BufferedWriter writer;
	
	public InfoExtractor(File targetFileDirectory, File destinationFileDirectory, JLabel lblProgressInfo) {
		super(targetFileDirectory, destinationFileDirectory, lblProgressInfo);
		this.setDestination();
	}
	
	private void setDestination() {
		
		super.destination = super.destinationFileDirectory + "/pdf_info.csv";

	}

	@Override
	protected String getUpdateProgressVerb() {
		return "Extracting Info From ";
	}
	
	@Override
	protected void parse(File file) {
		
		this.setText(file);
		this.write();

	}

	private void setText(File file) {

		String c = ",";
		String convertedText = super.getText();
		
		super.text = "";
		super.text += this.getSuitableCSVText(super.pdDocInfo.getTitle());
		super.text += c + super.nameOfFileCurrentlyBeingParsed;
		super.text += c + this.getSuitableCSVText(super.pdDocInfo.getSubject());
		super.text += c + this.getSuitableCSVText(super.pdDocInfo.getKeywords());
		super.text += c + this.getSuitableCSVText(super.pdDocInfo.getCreator());
		super.text += c + this.getSuitableCSVText(super.pdDocInfo.getProducer());
		super.text += c + super.pdDoc.getNumberOfPages(); 
		super.text += c + this.getNumberOfLines(convertedText); 
		super.text += c + this.getNumberOfWords(convertedText);
		super.text += c + super.pdDoc.isEncrypted(); 
		
	}
	
	private String getSuitableCSVText(String s) { //ensures that commas, linebreaks and nulls are not inserted into the .csv file
		
		if (s != null) {
			return s.replace(",", "/").replace("\n", "/");
		} else {
			return " ";
		}
		
	}
	
	private int getNumberOfLines(String convertedText) {
		
		int numberOfLines = 0;
		
		String[] lines = convertedText.split("\n");
		numberOfLines = lines.length;
		
		return numberOfLines;
		
	}
	
	private int getNumberOfWords(String convertedText) {
		
		int numberOfWords = 0;
		
		String[] words = convertedText.split(" ");
		numberOfWords = words.length;
		
		return numberOfWords;
		
	}
	
	private void write() {
		
        try {
        	this.writer = new BufferedWriter(new FileWriter(this.destination, true));
			this.writer.write(super.text);
			this.writer.newLine();
	        this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        

	}

}
