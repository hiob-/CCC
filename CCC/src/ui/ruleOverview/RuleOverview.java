package ui.ruleOverview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ui.internationalization.Strings;

/**
 * Ruleoverview
 * 
 * @author (Oussama Zgheb)
 * @version 1.0
 * */

public class RuleOverview extends JPanel {

	private static final long serialVersionUID = 1L;
	private RuleOverviewTableModel ruleOverviewTableModel;
	private JTable ruleOverviewTable;
	private JScrollPane scrollPane;
	private JButton Button_EditRule;
	private JButton Button_NewRule;
	private JButton Button_RemoveRule;

	public RuleOverview() {

		ruleOverviewTable = new JTable();
		ruleOverviewTable.setSize(100, 100);
		ruleOverviewTableModel = new RuleOverviewTableModel();
		setLayout(new BorderLayout(0, 0));
		ruleOverviewTable.setModel(ruleOverviewTableModel);

		scrollPane = new JScrollPane();
		this.add(scrollPane, BorderLayout.NORTH);
		scrollPane.setViewportView(ruleOverviewTable);
		scrollPane.setPreferredSize(new Dimension(300, 300));
		scrollPane.setMinimumSize(new Dimension(300, 300));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		Button_EditRule = new JButton(Strings.getString("RuleOverview.editRule")); //$NON-NLS-1$
		GridBagConstraints gbc_Button_EditRule = new GridBagConstraints();
		gbc_Button_EditRule.insets = new Insets(0, 0, 0, 5);
		gbc_Button_EditRule.gridx = 1;
		gbc_Button_EditRule.gridy = 0;
		panel.add(Button_EditRule, gbc_Button_EditRule);

		Button_NewRule = new JButton(Strings.getString("RuleOverview.newRule")); //$NON-NLS-1$
		GridBagConstraints gbc_Button_NewRule = new GridBagConstraints();
		gbc_Button_NewRule.insets = new Insets(0, 0, 0, 5);
		gbc_Button_NewRule.gridx = 2;
		gbc_Button_NewRule.gridy = 0;
		panel.add(Button_NewRule, gbc_Button_NewRule);

		Button_RemoveRule = new JButton(Strings.getString("RuleOverview.removeRule")); //$NON-NLS-1$

		GridBagConstraints gbc_Button_RemoveRule = new GridBagConstraints();
		gbc_Button_RemoveRule.insets = new Insets(0, 0, 0, 5);
		gbc_Button_RemoveRule.gridx = 3;
		gbc_Button_RemoveRule.gridy = 0;
		panel.add(Button_RemoveRule, gbc_Button_RemoveRule);
		
		
		ruleOverviewTable.getColumnModel().getColumn(0).setPreferredWidth(180);
		ruleOverviewTable.getColumnModel().getColumn(0).setMinWidth(180);

		ruleOverviewTable.getColumnModel().getColumn(1).setPreferredWidth(180);
		ruleOverviewTable.getColumnModel().getColumn(1).setMinWidth(180);

		ruleOverviewTable.getColumnModel().getColumn(2).setMinWidth(50);
		ruleOverviewTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		ruleOverviewTable.getColumnModel().getColumn(2).setMaxWidth(50);
		
		ruleOverviewTable.getColumnModel().getColumn(3).setMinWidth(50);
		ruleOverviewTable.getColumnModel().getColumn(3).setPreferredWidth(50);
		ruleOverviewTable.getColumnModel().getColumn(3).setMaxWidth(50);
		
		ruleOverviewTable.getColumnModel().getColumn(4).setMinWidth(50);
		ruleOverviewTable.getColumnModel().getColumn(4).setPreferredWidth(50);
		ruleOverviewTable.getColumnModel().getColumn(4).setMaxWidth(50);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.LEFT );
		ruleOverviewTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		
	
	}

	public JTable getRuleOverviewTable() {
		return ruleOverviewTable;
	}

	public JButton getButton_EditRule() {
		return Button_EditRule;
	}

	public JButton getButton_NewRule() {
		return Button_NewRule;
	}

	public JButton getButton_RemoveRule() {
		return Button_RemoveRule;
	}

	public RuleOverviewTableModel getRuleOverviewTableModel() {
		return ruleOverviewTableModel;
	}

}
