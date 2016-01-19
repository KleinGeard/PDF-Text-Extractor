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
	
	public ConvertListener (JTextField targetFolderField, JTextField destinationFolderField, JLabel lblProgressInfo, JButton btnConvert) {
		
		this.targetFolderField = targetFolderField;
		this.destinationFolderField = destinationFolderField;
		this.lblProgressInfo = lblProgressInfo;
		this.btnConvert = btnConvert;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Thread t = new Thread(new Runnable() { //enables me to update lblProgressInfo JLabel while running the parser.
	        @Override
	        public void run() {
	        	
	        	btnConvert.setEnabled(false);
	        	
	        	targetFileDirectory = new File(targetFolderField.getText());
	    		destinationFileDirectory = new File(destinationFolderField.getText());
	    		
	    		if (targetFileDirectory.exists() && destinationFileDirectory.exists()) {
	    			
	    			Parser parser = new Parser(targetFileDirectory, destinationFileDirectory, lblProgressInfo);
	    			parser.parseAll();
	    			
	    		} else {
	    			
	    			lblProgressInfo.setText("Incorrect file(s)");
	    			
	    		}
	    		
	    		btnConvert.setEnabled(true);
	        	
	        }    
	        
	    });
		
		t.start();
		
	}

}
