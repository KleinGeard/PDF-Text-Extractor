package Parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JLabel;

public class TextExtractor extends ParserSuperClass {

	public TextExtractor(File targetFileDirectory, File destinationFileDirectory, JLabel lblProgressInfo) {
		super(targetFileDirectory, destinationFileDirectory, lblProgressInfo);
	}

	@Override
	protected String getUpdateProgressVerb() {
		return "Extracting Text From ";
	}
	
	@Override
	protected void parse(File file) {
		
		this.setDestination(file);
		this.setText();
		this.write();
		
	}
	
	private void setDestination(File file) {
		
		super.destination = super.destinationFileDirectory.toString() + "\\" + super.nameOfFileCurrentlyBeingParsed + ".txt";
        
	}
	
	private void setText() {
		
		super.text = super.getTextFromPDFFile();
		
	}
	
	private void write() {
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(super.destination), "utf-8"))) {
            writer.write(super.text);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
	}
	
}