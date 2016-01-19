import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class SelectFolderListener implements ActionListener {

	private JTextField folderField;
	private File fileDirectory;
	private JFileChooser fileChooser = new JFileChooser();
	
	public SelectFolderListener(JTextField folderField) {
		
		this.folderField = folderField;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

        this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = this.fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            this.fileDirectory = this.fileChooser.getSelectedFile();

        } 
        
        this.folderField.setText(this.fileDirectory.toString());
        
	}

}
