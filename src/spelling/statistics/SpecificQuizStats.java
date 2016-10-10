package spelling.statistics;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;

import spelling.SpellingAidMain;

import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


///////// TESTER you don't have to read this as it is not used

// to be decided whether to use or not
public class SpecificQuizStats extends JPanel {
	private JTable table;
	private SpellingAidMain mainFrame;
	private SpellingAidStats mainStats;


	/**
	 * Create the panel after taking in the main frame so that panel can be switched based on state.
	 */
	public SpecificQuizStats(SpellingAidMain contentFrame, SpellingAidStats contentStats){
		this();
		mainFrame = contentFrame;		
		mainStats = contentStats;

	}
	/**
	 * Create the panel.
	 */
	public SpecificQuizStats() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{43, 10, 350, 10, 37, 0};
		gridBagLayout.rowHeights = new int[]{5, 23, 260, 20, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 2;
		gbc_verticalStrut.gridy = 0;
		add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblNewLabel = new JLabel("NZCER Lvl 1 Statistics\r\n");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 22));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setRowHeight(22);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"a", null},
				{null, "the"},
				{"hi", null},
				{null, "you"},
				{null, null},
				{"When you click one of the", ""},
				{"rows, it will display", ""},
				{"the 3 stats below", ""},
				{"for a specific", null},
				{"word", null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Last try Correct", "Last try Incorrect"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		scrollPane.setViewportView(table);
		
		JLabel lblMasteredFaulted = new JLabel("Mastered: 10   Faulted: 10   Failed: 10");
		lblMasteredFaulted.setFont(new Font("Arial", Font.PLAIN, 16));
		GridBagConstraints gbc_lblMasteredFaulted = new GridBagConstraints();
		gbc_lblMasteredFaulted.insets = new Insets(0, 0, 5, 5);
		gbc_lblMasteredFaulted.gridx = 2;
		gbc_lblMasteredFaulted.gridy = 3;
		add(lblMasteredFaulted, gbc_lblMasteredFaulted);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 4;
		add(panel, gbc_panel);
		
		JButton btnNewButton = new JButton("Overall Stats");
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainStats.changeCardPanel("Overall");
			}
		});
		btnNewButton.setPreferredSize(new Dimension(125, 23));
		panel.add(btnNewButton);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(25, 0));
		panel.add(horizontalStrut);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainStats.changeCardPanel("Overall");
				mainFrame.changeCardPanel("Main");
			}
		});
		btnNewButton_1.setPreferredSize(new Dimension(125, 23));
		panel.add(btnNewButton_1);

	}

}
