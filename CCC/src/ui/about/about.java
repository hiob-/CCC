package ui.about;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class about extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					about frame = new about();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public about() {
		setTitle("CCC");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 465, 510);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		
		JLabel lblLicenceGplv = new JLabel("<html><center><h2>Licence: GPLv3</h2><br>\r\n<u>Clicking will copy information into your clipboard</u><br><br>\r\n<h2>Contact</h2></center></html>");
		panel.add(lblLicenceGplv);
		
		JButton btnOussamaZgheboussamazghebcom = new JButton("Oussama Zgheb (oussama@zgheb.com)");
		btnOussamaZgheboussamazghebcom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClip("oussama@zgheb.com");
			}
		});
		GridBagConstraints gbc_btnOussamaZgheboussamazghebcom = new GridBagConstraints();
		gbc_btnOussamaZgheboussamazghebcom.insets = new Insets(0, 0, 5, 0);
		gbc_btnOussamaZgheboussamazghebcom.gridx = 0;
		gbc_btnOussamaZgheboussamazghebcom.gridy = 1;
		contentPane.add(btnOussamaZgheboussamazghebcom, gbc_btnOussamaZgheboussamazghebcom);
		
		JButton btnLucaTnnlerltaennlehsrch = new JButton("Luca T\u00E4nnler (ltaennle@hsr.ch)");
		btnLucaTnnlerltaennlehsrch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClip("ltaennle@hsr.ch");
			}
		});
		GridBagConstraints gbc_btnLucaTnnlerltaennlehsrch = new GridBagConstraints();
		gbc_btnLucaTnnlerltaennlehsrch.insets = new Insets(0, 0, 5, 0);
		gbc_btnLucaTnnlerltaennlehsrch.gridx = 0;
		gbc_btnLucaTnnlerltaennlehsrch.gridy = 2;
		contentPane.add(btnLucaTnnlerltaennlehsrch, gbc_btnLucaTnnlerltaennlehsrch);
		
		JButton btnDiegoEtterdetterhsrch = new JButton("Diego Etter (detter@hsr.ch)");
		btnDiegoEtterdetterhsrch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClip("detter@hsr.ch");
			}
		});
		GridBagConstraints gbc_btnDiegoEtterdetterhsrch = new GridBagConstraints();
		gbc_btnDiegoEtterdetterhsrch.insets = new Insets(0, 0, 5, 0);
		gbc_btnDiegoEtterdetterhsrch.gridx = 0;
		gbc_btnDiegoEtterdetterhsrch.gridy = 3;
		contentPane.add(btnDiegoEtterdetterhsrch, gbc_btnDiegoEtterdetterhsrch);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 4;
		contentPane.add(panel_1, gbc_panel_1);
		
		JLabel lblDonationsWelcome = new JLabel("<html><br><center><h2>Donations welcome!</h2><br>It will be invested in coffee and additions to CCC.</center></html>");
		panel_1.add(lblDonationsWelcome);
		
		JButton btnBtcAddress = new JButton("BTC Address");
		btnBtcAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClip("1BDVDRVnbamop74k2kZxLmbZNbLEmtshdU");
			}
		});
		GridBagConstraints gbc_btnBtcAddress = new GridBagConstraints();
		gbc_btnBtcAddress.insets = new Insets(0, 0, 5, 0);
		gbc_btnBtcAddress.gridx = 0;
		gbc_btnBtcAddress.gridy = 5;
		contentPane.add(btnBtcAddress, gbc_btnBtcAddress);
		
		JButton btnLtcAddress = new JButton("LTC Address");
		btnLtcAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClip("LSsiGa4mc6Woi1VEHAGYkurJHYRMAjjSM4");
			}
		});
		GridBagConstraints gbc_btnLtcAddress = new GridBagConstraints();
		gbc_btnLtcAddress.insets = new Insets(0, 0, 5, 0);
		gbc_btnLtcAddress.gridx = 0;
		gbc_btnLtcAddress.gridy = 6;
		contentPane.add(btnLtcAddress, gbc_btnLtcAddress);
		
		JButton btnDogeAddress = new JButton("DOGE Address");
		btnDogeAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyToClip("DSWLdTQzvAuQjiuGGxkmZEt6pTszUv7rGJ");
			}
		});
		GridBagConstraints gbc_btnDogeAddress = new GridBagConstraints();
		gbc_btnDogeAddress.gridx = 0;
		gbc_btnDogeAddress.gridy = 7;
		contentPane.add(btnDogeAddress, gbc_btnDogeAddress);
	}
	
	public static void copyToClip(String information){
		StringSelection stringSelection = new StringSelection (information);
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents (stringSelection, null);
	}

}
