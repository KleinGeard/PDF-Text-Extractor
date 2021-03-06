package main;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import ButtonPressed.*;

public class Main {

	private JFrame frmPdfTextExtractor;
	private JButton btnSelectTargetFolder;
	private JButton btnSelectDestinationFolder;
	private JTextField targetFolderField;
	private JTextField destinationFolderField;
	private JLabel lblProgressInfo;
	private JButton btnConvert;
	private JButton btnExtractInfo;

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

	public Main() {
		initialise();
	}

	private void initialise() {
		
		this.addTheme();
		this.initialiseFrame();
		this.initialiseTargetFolderSelectionComponents();
		this.initialiseDestinationFolderSelectionComponents();
		this.initialiseProgressInfoLabel();
		this.initialiseConvertButton();
		this.initialiseExtractInfoButton();
		this.addActionListeners();
		
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
		this.frmPdfTextExtractor.setTitle("pdf text extractor");
		this.frmPdfTextExtractor.setResizable(false);
		this.frmPdfTextExtractor.setBounds(100, 100, 900, 200);
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
		this.frmPdfTextExtractor.getContentPane().add(this.targetFolderField);
		
		this.btnSelectTargetFolder = new JButton("Select Target Folder");
		this.btnSelectTargetFolder.setToolTipText("Select the folder containing the .pdf documents you wish to convert into .txt.");
		this.btnSelectTargetFolder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.btnSelectTargetFolder.setBounds(12, 13, 185, 25);
		this.frmPdfTextExtractor.getContentPane().add(this.btnSelectTargetFolder);
		
	}
	
	public void initialiseDestinationFolderSelectionComponents() {
		
		this.destinationFolderField = new JTextField();
		this.destinationFolderField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.destinationFolderField.setText("No Destination Folder Selected");
		this.destinationFolderField.setBackground(Color.WHITE);
		this.destinationFolderField.setBounds(240, 53, 631, 22);
		this.frmPdfTextExtractor.getContentPane().add(this.destinationFolderField);
		
		this.btnSelectDestinationFolder = new JButton("Select Destination Folder");
		this.btnSelectDestinationFolder.setToolTipText("Select the folder you wish the converted .txt files to be saved to.");
		this.btnSelectDestinationFolder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.btnSelectDestinationFolder.setBounds(12, 51, 185, 25);
		this.frmPdfTextExtractor.getContentPane().add(this.btnSelectDestinationFolder);
		
	}
	
	public void initialiseProgressInfoLabel() {
		
		this.lblProgressInfo = new JLabel("");
		this.lblProgressInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.lblProgressInfo.setBounds(240, 88, 631, 22);
		this.frmPdfTextExtractor.getContentPane().add(this.lblProgressInfo);
		
	}
	
	public void initialiseConvertButton() {
		
		this.btnConvert = new JButton("Extract Text");
		this.btnConvert.setToolTipText("Extract the plain text from all of the .pdf files in your target folder and save them as .txt files in your destination folder");
		this.btnConvert.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.btnConvert.setBounds(12, 89, 185, 25);
		this.frmPdfTextExtractor.getContentPane().add(this.btnConvert);
		
		
	}
	
	public void initialiseExtractInfoButton() {
		
		this.btnExtractInfo = new JButton("Extract Info");
		this.btnExtractInfo.setToolTipText("Extract info (such as file size, author, etc) from all of the .pdf files in your target folder and store it in a single .csv file in your destination folder.");
		this.btnExtractInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.btnExtractInfo.setBounds(12, 127, 185, 25);
		this.frmPdfTextExtractor.getContentPane().add(this.btnExtractInfo);
		
		
	}
	
	public void addActionListeners() {
		
		SelectFolderListener selectTargetFolderListener = new SelectFolderListener(this.targetFolderField);
		this.btnSelectTargetFolder.addActionListener(selectTargetFolderListener);
		
		SelectFolderListener selectDestinationFolderListener = new SelectFolderListener(this.destinationFolderField);
		this.btnSelectDestinationFolder.addActionListener(selectDestinationFolderListener);
		
		ConvertListener convertListener = new ConvertListener(this.targetFolderField, this.destinationFolderField, this.lblProgressInfo, this.btnConvert, this.btnExtractInfo);
		this.btnConvert.addActionListener(convertListener);
		
		ExtractInfoListener extractInfoListener = new ExtractInfoListener(this.targetFolderField, this.destinationFolderField, this.lblProgressInfo, this.btnConvert, this.btnExtractInfo);
		this.btnExtractInfo.addActionListener(extractInfoListener);
		
	}
	
}
