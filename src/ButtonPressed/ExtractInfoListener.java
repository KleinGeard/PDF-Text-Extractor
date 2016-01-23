package ButtonPressed;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Parser.InfoExtractor;

public class ExtractInfoListener extends ButtonPressedSuperClass implements ActionListener {
	
	public ExtractInfoListener(JTextField targetFolderField, JTextField destinationFolderField, JLabel lblProgressInfo,
			JButton btnConvert, JButton btnExtractInfo) {
		super(targetFolderField, destinationFolderField, lblProgressInfo, btnConvert, btnExtractInfo);
	}

	@Override
	protected void parse() {
		
		InfoExtractor infoExtractor = new InfoExtractor(super.targetFileDirectory, super.destinationFileDirectory, super.lblProgressInfo);
		infoExtractor.parseAll();
		
	}
	
	

}
