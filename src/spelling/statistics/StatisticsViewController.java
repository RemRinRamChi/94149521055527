package spelling.statistics;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import spelling.SpellingAidMain;
import spelling.quiz.SpellList;
import spelling.settings.ClearStatistics;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
/**
 * This is the GUI for the spelling aid statistics
 * @author yyap601
 *
 */
public class StatisticsViewController extends JPanel {
	private SpellingAidMain mainFrame;
	private JPanel specificQuiz;
	private JLabel lblHi;
	private JTabbedPane lvlAndWordStatsPane;
	private JTable triedWordsTable;
	private JTable levelTable;

	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public StatisticsViewController(SpellingAidMain contentFrame){
		this();
		mainFrame = contentFrame;
	}
	
	/**
	 * Create the panel.
	 */
	public StatisticsViewController() {
		setLayout(null);
		
		lvlAndWordStatsPane = new JTabbedPane(JTabbedPane.TOP);
		lvlAndWordStatsPane.setFont(new Font("Arial", Font.PLAIN, 14));
		lvlAndWordStatsPane.setBounds(10, 191, 450, 358);
		add(lvlAndWordStatsPane);
		
		JScrollPane levelScrollPane = new JScrollPane();
		lvlAndWordStatsPane.addTab("Level Statistics", null, levelScrollPane, null);
		
		levelTable = new JTable();
		levelTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"LEVEL", "Attempts", "Longest Streak", "Accuracy"
			}
		));
		levelScrollPane.setViewportView(levelTable);
		levelTable.setShowHorizontalLines(false);
		levelTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		levelTable.setRowHeight(22);
		levelTable.setFont(new Font("Arial", Font.PLAIN, 15));
		
		JScrollPane wordScrollPane = new JScrollPane();
		wordScrollPane.setFont(new Font("Arial", Font.PLAIN, 12));
		lvlAndWordStatsPane.addTab("Tried Words Statistics", null, wordScrollPane, null);
		
		triedWordsTable = new JTable();
		triedWordsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		triedWordsTable.setRowHeight(22);
		triedWordsTable.setShowHorizontalLines(false);
		triedWordsTable.setFont(new Font("Arial", Font.PLAIN, 15));
		triedWordsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"WORD", "Mastered", "Faulted", "Failed"
			}
		));
		
		wordScrollPane.setViewportView(triedWordsTable);
		
		JButton btnBack = new JButton("BACK");
		btnBack.setBounds(329, 560, 119, 31);
		add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.changeCardPanel("Main");
			}
		});
		btnBack.setFont(new Font("Arial", Font.PLAIN, 13));
		
		JPanel friendlyPanel = new JPanel();
		friendlyPanel.setLayout(null);
		friendlyPanel.setBackground(Color.WHITE);
		friendlyPanel.setBounds(0, 0, 475, 180);
		add(friendlyPanel);
		
		JLabel avatar = new JLabel("");
		avatar.setIcon(new ImageIcon("img/avatar.png"));
		avatar.setBounds(24, 11, 159, 155);
		friendlyPanel.add(avatar);
		
		lblHi = new JLabel("Hi Sherlock");
		lblHi.setFont(new Font("Arial", Font.PLAIN, 40));
		lblHi.setBounds(193, 31, 235, 64);
		friendlyPanel.add(lblHi);
		
		JLabel lblHereAreSomeStats = new JLabel("Here are some statistics");
		lblHereAreSomeStats.setFont(new Font("Arial", Font.PLAIN, 22));
		lblHereAreSomeStats.setBounds(193, 95, 272, 53);
		friendlyPanel.add(lblHereAreSomeStats);
		
		JButton btnClearStatistics = new JButton("Clear Statistics");
		btnClearStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//CLEAR STATS info dialog
				mainFrame.changeCardPanel("Main");
				JOptionPane.showMessageDialog(mainFrame, ClearStatistics.clearStats(), "VoxSpell Statistics Cleared", JOptionPane.INFORMATION_MESSAGE);
				mainFrame.updateSpellingList(new SpellList());
			}
		});
		btnClearStatistics.setFont(new Font("Arial", Font.PLAIN, 13));
		btnClearStatistics.setBounds(20, 560, 175, 31);
		add(btnClearStatistics);



	}
	
	public void addToWordTable(Object[] addRow){
		DefaultTableModel tableModel = (DefaultTableModel) triedWordsTable.getModel();
		tableModel.insertRow(tableModel.getRowCount(),addRow);
	}
	public void clearWordTable(){
		DefaultTableModel tableModel = (DefaultTableModel) triedWordsTable.getModel();
		int rowCount = tableModel.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
	}
	public void addToLevelTable(Object[] addRow){
		DefaultTableModel tableModel = (DefaultTableModel) levelTable.getModel();
		tableModel.insertRow(tableModel.getRowCount(),addRow);
	}
	public void clearLevelTable(){
		DefaultTableModel tableModel = (DefaultTableModel) levelTable.getModel();
		int rowCount = tableModel.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
	}
	
	
	public void setUserName(String name){
		lblHi.setText("Hi "+name);
	}
}
