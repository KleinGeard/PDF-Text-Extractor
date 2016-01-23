package ButtonPressed;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public abstract class ButtonPressedSuperClass implements ActionListener {

	protected File targetFileDirectory;
	protected File destinationFileDirectory;
	protected JLabel lblProgressInfo;
	
	private JTextField targetFolderField;
	private JTextField destinationFolderField;
	private JButton btnConvert;
	private JButton btnExtractInfo;
	
	public ButtonPressedSuperClass (JTextField targetFolderField, JTextField destinationFolderField, JLabel lblProgressInfo, JButton btnConvert, JButton btnExtractInfo) {
		
		this.targetFolderField = targetFolderField;
		this.destinationFolderField = destinationFolderField;
		this.lblProgressInfo = lblProgressInfo;
		this.btnConvert = btnConvert;
		this.btnExtractInfo = btnExtractInfo;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Thread t = new Thread(new Runnable() { //enables lblProgressInfo to be changed while parser is running.
	        @Override
	        public void run() {
	        	
	        	setButtonEnabled(false);
	        	
	        	createFiles();
	    		performAction();
	    		
	    		setButtonEnabled(true);
	        	
	        }    
	        
	    });
		
		t.start();

	}
	
	private void setButtonEnabled(Boolean bool) {
		
		this.btnConvert.setEnabled(bool);
		this.btnExtractInfo.setEnabled(bool);
		
	}
	
	private void createFiles() {
		
		targetFileDirectory = new File(targetFolderField.getText());
		destinationFileDirectory = new File(destinationFolderField.getText());
		
	}
	
	private void performAction() {
		
		if (targetFileDirectory.exists() && destinationFileDirectory.exists()) {
			
			parse();
			
		} else {
			
			lblProgressInfo.setText("Incorrect file(s)");
			
		}
		
	}
	
	protected abstract void parse();

}
