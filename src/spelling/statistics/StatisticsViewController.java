package spelling.statistics;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import spelling.SpellingAidMain;

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
/**
 * This is the GUI for the spelling aid statistics
 * @author yyap601
 *
 */
public class StatisticsViewController extends JPanel {
	private SpellingAidMain mainFrame;
	private JPanel specificQuiz;
	private JTextArea statsTextArea;
	private JLabel lblHi;
	private JTabbedPane lvlAndWordStatsPane;
	private JTable triedWordsTable;

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
		
		JScrollPane scrollPane = new JScrollPane();
		lvlAndWordStatsPane.addTab("Level Statistics", null, scrollPane, null);
		
		statsTextArea = new JTextArea();
		scrollPane.setViewportView(statsTextArea);
		statsTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
		statsTextArea.setEditable(false);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lvlAndWordStatsPane.addTab("Tried Words Statistics", null, scrollPane_1, null);
		
		triedWordsTable = new JTable();
		triedWordsTable.setFont(new Font("Arial", Font.PLAIN, 15));
		triedWordsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"WORD", "Mastered", "Faulted", "Failed"
			}
		));
		scrollPane_1.setViewportView(triedWordsTable);
		
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



	}
	
	public void addToTable(Object[] addRow){
		DefaultTableModel tableModel = (DefaultTableModel) triedWordsTable.getModel();
		tableModel.insertRow(tableModel.getRowCount(),addRow);
	}
	public void clearTable(){
		DefaultTableModel tableModel = (DefaultTableModel) triedWordsTable.getModel();
		int rowCount = tableModel.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
	}
	
	public void scrollToTop(){
		statsTextArea.setCaretPosition(0);
	}

	public void appendText(String txt){
		statsTextArea.append(txt);
	}
	
	public void clearStatsArea(){
		statsTextArea.setText("");
	}
	
	public void setUserName(String name){
		lblHi.setText("Hi "+name);
	}
	
}
