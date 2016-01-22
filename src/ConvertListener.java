import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ConvertListener implements ActionListener {
	
	private File targetFileDirectory;
	private File destinationFileDirectory;
	
	private JTextField targetFolderField;
	private JTextField destinationFolderField;
	private JLabel lblProgressInfo;
	private JButton btnConvert;
	private JButton btnExtractInfo;
	
	public ConvertListener (JTextField targetFolderField, JTextField destinationFolderField, JLabel lblProgressInfo, JButton btnConvert, JButton btnExtractInfo) {
		
		this.targetFolderField = targetFolderField;
		this.destinationFolderField = destinationFolderField;
		this.lblProgressInfo = lblProgressInfo;
		this.btnConvert = btnConvert;
		this.btnExtractInfo = btnExtractInfo;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Thread t = new Thread(new Runnable() { //enables me to update lblProgressInfo and btnConvert while running the parser.
	        @Override
	        public void run() {
	        	
	        	btnConvert.setEnabled(false);
	        	btnExtractInfo.setEnabled(false);
	        	
	        	targetFileDirectory = new File(targetFolderField.getText());
	    		destinationFileDirectory = new File(destinationFolderField.getText());
	    		
	    		if (targetFileDirectory.exists() && destinationFileDirectory.exists()) {
	    			
	    			Parser parser = new Parser(targetFileDirectory, destinationFileDirectory, lblProgressInfo);
	    			parser.parseAll();
	    			
	    		} else {
	    			
	    			lblProgressInfo.setText("Incorrect file(s)");
	    			
	    		}
	    		
	    		btnConvert.setEnabled(true);
	    		btnExtractInfo.setEnabled(true);
	        	
	        }    
	        
	    });
		
		t.start();
		
	}

}
