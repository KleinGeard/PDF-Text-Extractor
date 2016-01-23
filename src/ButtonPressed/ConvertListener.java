package ButtonPressed;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Parser.TextExtractor;

public class ConvertListener extends ButtonPressedSuperClass implements ActionListener {
	
	public ConvertListener(JTextField targetFolderField, JTextField destinationFolderField, JLabel lblProgressInfo,
			JButton btnConvert, JButton btnExtractInfo) {
		super(targetFolderField, destinationFolderField, lblProgressInfo, btnConvert, btnExtractInfo);
	}

	@Override
	protected void parse() {
		
		TextExtractor converter = new TextExtractor(super.targetFileDirectory, super.destinationFileDirectory, super.lblProgressInfo);
		converter.parseAll();
		
	}
	
	

}
