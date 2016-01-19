import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import java.awt.Toolkit;

public class Main {

	private JFrame frmPdfTextExtractor;
	private JTextField targetFolderField;
	private JTextField destinationFolderField;
	private JLabel lblProgressInfo;
	private JButton btnConvert;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmPdfTextExtractor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialise();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialise() {
		
		this.addTheme();
		this.initialiseFrame();
		this.initialiseTargetFolderSelectionComponents();
		this.initialiseDestinationFolderSelectionComponents();
		this.initialiseProgressInfoLabel();
		this.initialiseConvertButton();
		
	}
	
	public void addTheme() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void initialiseFrame() {
		
		this.frmPdfTextExtractor = new JFrame();
		frmPdfTextExtractor.setTitle("pdf text extractor");
		frmPdfTextExtractor.setResizable(false);
		this.frmPdfTextExtractor.setBounds(100, 100, 900, 175);
		this.frmPdfTextExtractor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frmPdfTextExtractor.getContentPane().setLayout(null);
		
	}
	
	public void initialiseTargetFolderSelectionComponents() {
		
		this.targetFolderField = new JTextField();
		this.targetFolderField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.targetFolderField.setText("No Target Folder Selected.");
		this.targetFolderField.setForeground(Color.BLACK);
		this.targetFolderField.setBackground(Color.WHITE);
		this.targetFolderField.setBounds(240, 15, 631, 22);
		this.frmPdfTextExtractor.getContentPane().add(targetFolderField);
		
		JButton btnSelectTargetFolder = new JButton("Select Target Folder");
		btnSelectTargetFolder.setToolTipText("Select the folder containing the .pdf documents you wish to convert into .txt.");
		btnSelectTargetFolder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSelectTargetFolder.setBounds(12, 13, 185, 25);
		this.frmPdfTextExtractor.getContentPane().add(btnSelectTargetFolder);
		
		String targetOpenDialogTitleText = "Choose folder containing PDFs";
		SelectFolderListener selectTargetFolderListener = new SelectFolderListener(this.targetFolderField, targetOpenDialogTitleText);
		btnSelectTargetFolder.addActionListener(selectTargetFolderListener);
		
	}
	
	public void initialiseDestinationFolderSelectionComponents() {
		
		this.destinationFolderField = new JTextField();
		this.destinationFolderField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.destinationFolderField.setText("No Destination Folder Selected");
		this.destinationFolderField.setBackground(Color.WHITE);
		this.destinationFolderField.setBounds(240, 53, 631, 22);
		this.frmPdfTextExtractor.getContentPane().add(destinationFolderField);
		
		JButton btnSelectDestinationFolder = new JButton("Select Destination Folder");
		btnSelectDestinationFolder.setToolTipText("Select the folder you wish the converted .txt files to be saved to.");
		btnSelectDestinationFolder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSelectDestinationFolder.setBounds(12, 51, 185, 25);
		this.frmPdfTextExtractor.getContentPane().add(btnSelectDestinationFolder);
		
		String destinationOpenDialogTitleText = "Choose folder containing PDFs";
		SelectFolderListener selectDestinationFolderListener = new SelectFolderListener(this.destinationFolderField, destinationOpenDialogTitleText);
		btnSelectDestinationFolder.addActionListener(selectDestinationFolderListener);
		
	}
	
	public void initialiseProgressInfoLabel() {
		
		this.lblProgressInfo = new JLabel("");
		this.lblProgressInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.lblProgressInfo.setBounds(240, 88, 631, 22);
		this.frmPdfTextExtractor.getContentPane().add(lblProgressInfo);
		
	}
	
	public void initialiseConvertButton() {
		
		this.btnConvert = new JButton("Convert");
		this.btnConvert.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.btnConvert.setBounds(12, 89, 185, 25);
		this.frmPdfTextExtractor.getContentPane().add(btnConvert);
		ConvertListener convertListener = new ConvertListener(this.targetFolderField, this.destinationFolderField, this.lblProgressInfo, this.btnConvert);
		this.btnConvert.addActionListener(convertListener);
		
	}
	
}
