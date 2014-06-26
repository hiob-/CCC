package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.net.URL;

/**
 * Customized JTextField.
 * This Class adds following functions:
 * - Reset Button
 * 
 * @author Luca T�nnler
 * @version 1.0
 * */
public class JCustomTextField extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JButton btnReset;
	/**
	 * Create the panel.
	 */
	public JCustomTextField() {
		this("");
	}
	
	public JCustomTextField(String pText){
		super();
		textField = new JTextField("");
		btnReset = new JButton();
		setLayout(new BorderLayout(0, 0));
		initResetButton();
		setBorder(textField.getBorder());
		textField.setBorder(null);
		textField.setText(pText);
		
		add(this.textField, BorderLayout.CENTER);
		add(this.btnReset, BorderLayout.EAST);
		setBackground(textField.getBackground());
	}
	
	private void initResetButton(){
		
		URL iconDelete = JCustomTextField.class.getResource("/ui/images/icon_delete.png");
		if(iconDelete == null){
			System.err.println("iconDelete missing in JCustomTextField");
		}
		Image resetImage = Toolkit.getDefaultToolkit().getImage(iconDelete).getScaledInstance(20,20, java.awt.Image.SCALE_SMOOTH);

		ImageIcon resetIcon = new ImageIcon(resetImage);
		resetIcon.setDescription("Reset");
		ActionListener btnResetAL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
			}
		};
		
		btnReset.setBorder(null);
		btnReset.setIcon(resetIcon);
		btnReset.setBackground(Color.white);
		btnReset.setPreferredSize(new Dimension(20, 20));
		btnReset.addActionListener(btnResetAL);
	}
	
	public String getText(){
		if(textField != null){
			return textField.getText();
		}else{
			return "";
		}
	}
	
	public void setText(String pText){
		textField.setText(pText);
	}
	
	public void setColumns(int pColumns){
		if(textField != null)
		textField.setColumns(pColumns);
	}
	
	public void addKeyListener(KeyListener pKeyListener){
		if(textField != null)
		textField.addKeyListener(pKeyListener);
	}
	
	public void addDocumentListener(DocumentListener pDocumentListener){
		if(textField != null)
		textField.getDocument().addDocumentListener(pDocumentListener);
	}
	
	public void setBackground(Color pColor){
		super.setBackground(pColor);
		if(textField != null && btnReset != null)
		{
			textField.setBackground(pColor);
			btnReset.setBackground(pColor);
		}
	}

}
