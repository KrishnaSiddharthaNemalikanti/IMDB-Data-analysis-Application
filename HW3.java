import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class HW3 extends JFrame {

	private JPanel contentPane;
	private JTextField noOfReviowsValueTextField;
	private JTextField criticsRatingValueTextField;
	private static HW3 frame;
	private String url = "com.mysql.jdbc.Driver";
	private String user = "root";
	private String password = "NEWPASSWORD";
	private Connection connection;
	private String query = "";
	private String queryOr = "";
	private String finalQuery = "";
	private String finalGenresQuery = "";
	private String finalCountryQuery = "";
	private String finalFilLocQuery = "";
	private String finalLocSelectionQuery = "";
	private String finalMovieQuery = "";
	private JPanel countryPanel;
	private JPanel filmingLocationPanel;
	private JPanel moviePanel;
	private JPanel searchPanel;
	private JPanel ratingPanel;
	private JPanel reviewPanel;
	private JPanel datePanel;
	private JPanel genresPanel;
	private JPanel genresLabelPanel;
	private JPanel countryLabelPanel;
	private JPanel filmingLocationLabelPanel;
	private ActionListener countryActionListener;
	private ActionListener countryActionListenerForOr;
	private ActionListener filmLocationListener;
	private ActionListener filmLocationListenerForOr;
	private GridBagConstraints gbc;
	private JComboBox andOrComboBox;
	private JComboBox noOfReviowsComboBox;
	private JComboBox criticsRatingComboBox;
	private JLabel criticsLabel;
	private JLabel usersLabel;
	private JLabel searchForTextField;
	private JLabel fromLabel;
	private JLabel toLabel;
	private JLabel numOfReviewsLabel;
	private JLabel starValueLabel;
	private JLabel criticsRatingLabel;
	private JLabel ratingValueLabel;
	private JLabel GenresLabel;
	private JLabel countryLabel;
	private JLabel filmingLocLabel;
	private JScrollPane genresScrollPane;
	private JScrollPane countryScrollPane;
	private JScrollPane filmingLocScrollPane;
	private static final String andOrOperators[] = new String[] { "AND", "OR" };
	private static final String comboBoxOperators[] = new String[] { "<", "=", ">" };
	private Properties properties;
	private Properties fromProperties;
	private Properties toProperties;
	private JTable table;
	private Object[][] data;
	private JTextArea queryAreaTextArea;
	private JPanel panel_1;
	private JButton btnExecuteQuery;
	private JTextField textFieldTagWeightValue;
	private JPanel panelMovieTagValues;
	public JComboBox comboBoxTagWeight;
	public JTextArea textAreaMovieTags;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_2;
	private JPanel panel;
	private JTable resultTable;
	private JScrollPane scrollPane_1;
	private JLabel resultLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new HW3();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public HW3() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1166, 750);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		moviePanel = new JPanel();
		moviePanel.setForeground(Color.GRAY);
		moviePanel.setBackground(UIManager.getColor("Button.select"));
		moviePanel.setBounds(51, 13, 1071, 49);
		contentPane.add(moviePanel);
		moviePanel.setLayout(null);
		JLabel lblMovie = new JLabel("MOVIE");
		lblMovie.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMovie.setHorizontalAlignment(SwingConstants.CENTER);
		lblMovie.setForeground(Color.WHITE);
		lblMovie.setBounds(495, 11, 196, 22);
		moviePanel.add(lblMovie);
		searchPanel = new JPanel();
		searchPanel.setForeground(Color.BLACK);
		searchPanel.setBackground(UIManager.getColor("Button.darkShadow"));
		searchPanel.setBounds(51, 415, 431, 73);
		contentPane.add(searchPanel);
		searchPanel.setLayout(null);
		searchForTextField = new JLabel("SEARCH BETWEEN ATTRIBUTES VALUE: ");
		searchForTextField.setFont(new Font("Tahoma", Font.BOLD, 13));
		searchForTextField.setForeground(Color.WHITE);
		searchForTextField.setBounds(10, 17, 246, 16);
		searchPanel.add(searchForTextField);
		andOrComboBox = new JComboBox(andOrOperators);
		andOrComboBox.setBounds(280, 13, 95, 27);
		searchPanel.add(andOrComboBox);
		usersLabel = new JLabel("Users");
		usersLabel.setForeground(Color.BLACK);
		usersLabel.setBackground(Color.CYAN);
		usersLabel.setOpaque(true);
		usersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usersLabel.setBounds(30, 123, 792, 31);
		searchPanel.add(usersLabel);
		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				System.out.println("****************************");
			}
		});

		properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");

		datePanel = new JPanel();
		datePanel.setBounds(552, 329, 288, 87);
		contentPane.add(datePanel);
		datePanel.setLayout(null);
		fromLabel = new JLabel("From");
		fromLabel.setBounds(10, 11, 50, 29);
		datePanel.add(fromLabel);

		fromProperties = new Properties();
		fromProperties.put("text.today", "Today");
		fromProperties.put("text.month", "Month");
		fromProperties.put("text.year", "Year");

		toLabel = new JLabel("To");
		toLabel.setBounds(10, 51, 50, 29);
		datePanel.add(toLabel);

		toProperties = new Properties();
		toProperties.put("text.today", "Today");
		toProperties.put("text.month", "Month");
		toProperties.put("text.year", "Year");

		reviewPanel = new JPanel();
		reviewPanel.setBounds(552, 203, 288, 99);
		contentPane.add(reviewPanel);
		reviewPanel.setLayout(null);
		numOfReviewsLabel = new JLabel("Num of Reviews:");
		numOfReviewsLabel.setBounds(10, 11, 81, 29);
		reviewPanel.add(numOfReviewsLabel);
		starValueLabel = new JLabel("Value :");
		starValueLabel.setBounds(12, 53, 65, 29);
		reviewPanel.add(starValueLabel);
		noOfReviowsComboBox = new JComboBox(comboBoxOperators);
		noOfReviowsComboBox.setBounds(101, 11, 171, 29);
		reviewPanel.add(noOfReviowsComboBox);
		noOfReviowsValueTextField = new JTextField();
		noOfReviowsValueTextField.setBounds(101, 53, 171, 29);
		reviewPanel.add(noOfReviowsValueTextField);
		noOfReviowsValueTextField.setColumns(10);
		ratingPanel = new JPanel();
		ratingPanel.setBounds(552, 87, 288, 116);
		contentPane.add(ratingPanel);
		ratingPanel.setLayout(null);
		criticsRatingLabel = new JLabel("Critics Rating :");
		criticsRatingLabel.setBounds(10, 41, 70, 14);
		ratingPanel.add(criticsRatingLabel);
		criticsRatingComboBox = new JComboBox(comboBoxOperators);
		criticsRatingComboBox.setModel(new DefaultComboBoxModel(new String[] { "<", "=", ">" }));
		criticsRatingComboBox.setBounds(90, 34, 164, 29);
		ratingPanel.add(criticsRatingComboBox);
		ratingValueLabel = new JLabel("Value :");
		ratingValueLabel.setBounds(10, 73, 65, 29);
		ratingPanel.add(ratingValueLabel);
		criticsRatingValueTextField = new JTextField();
		criticsRatingValueTextField.setColumns(10);
		criticsRatingValueTextField.setBounds(90, 74, 164, 29);
		ratingPanel.add(criticsRatingValueTextField);
		genresPanel = new JPanel();
		genresPanel.setBounds(51, 87, 264, 600);
		genresPanel.setLayout(new GridBagLayout());
		genresScrollPane = new JScrollPane(genresPanel);
		genresScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		genresScrollPane.setBounds(51, 87, 173, 329);
		genresScrollPane.setBorder(new LineBorder(Color.BLACK));
		genresScrollPane.setPreferredSize(new Dimension(264, 426));
		genresScrollPane.setViewportView(genresPanel);
		contentPane.add(genresScrollPane);
		countryPanel = new JPanel();
		countryPanel.setBounds(317, 87, 264, 426);
		countryPanel.setLayout(new GridBagLayout());
		countryScrollPane = new JScrollPane(countryPanel);
		countryScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		countryScrollPane.setBounds(227, 89, 149, 329);
		countryScrollPane.setBorder(new LineBorder(Color.BLACK));
		countryScrollPane.setPreferredSize(new Dimension(264, 426));
		countryScrollPane.setViewportView(countryPanel);
		contentPane.add(countryScrollPane);
		filmingLocationPanel = new JPanel();
		filmingLocationPanel.setBounds(583, 87, 264, 426);
		filmingLocationPanel.setLayout(new GridBagLayout());
		filmingLocScrollPane = new JScrollPane(filmingLocationPanel);
		filmingLocScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		filmingLocScrollPane.setBounds(378, 89, 173, 329);
		filmingLocScrollPane.setBorder(new LineBorder(Color.BLACK));
		filmingLocScrollPane.setPreferredSize(new Dimension(264, 426));
		filmingLocScrollPane.setViewportView(filmingLocationPanel);
		contentPane.add(filmingLocScrollPane);
		genresLabelPanel = new JPanel();
		genresLabelPanel.setBounds(53, 58, 173, 31);
		contentPane.add(genresLabelPanel);
		genresLabelPanel.setLayout(null);
		GenresLabel = new JLabel("GENRES");
		GenresLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		GenresLabel.setBounds(0, 0, 173, 31);
		genresLabelPanel.add(GenresLabel);
		GenresLabel.setOpaque(true);
		GenresLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GenresLabel.setForeground(Color.WHITE);
		GenresLabel.setBackground(UIManager.getColor("Button.darkShadow"));
		countryLabelPanel = new JPanel();
		countryLabelPanel.setBounds(227, 58, 149, 29);
		contentPane.add(countryLabelPanel);
		countryLabelPanel.setLayout(null);
		countryLabel = new JLabel("COUNTRY");
		countryLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		countryLabel.setBounds(0, 0, 149, 31);
		countryLabelPanel.add(countryLabel);
		countryLabel.setOpaque(true);
		countryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		countryLabel.setForeground(Color.WHITE);
		countryLabel.setBackground(UIManager.getColor("Button.darkShadow"));
		filmingLocationLabelPanel = new JPanel();
		filmingLocationLabelPanel.setBounds(378, 60, 173, 29);
		contentPane.add(filmingLocationLabelPanel);
		filmingLocationLabelPanel.setLayout(null);
		filmingLocLabel = new JLabel("FILMING LOCATION");
		filmingLocLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		filmingLocLabel.setBounds(0, 0, 173, 31);
		filmingLocationLabelPanel.add(filmingLocLabel);
		filmingLocLabel.setOpaque(true);
		filmingLocLabel.setHorizontalAlignment(SwingConstants.CENTER);
		filmingLocLabel.setForeground(Color.WHITE);
		filmingLocLabel.setBackground(UIManager.getColor("Button.darkShadow"));
		JLabel MovieYear = new JLabel("MOVIE YEAR");
		MovieYear.setBackground(Color.DARK_GRAY);
		MovieYear.setFont(new Font("Tahoma", Font.PLAIN, 13));
		MovieYear.setHorizontalAlignment(SwingConstants.CENTER);
		MovieYear.setForeground(Color.BLACK);
		MovieYear.setBounds(552, 301, 293, 31);
		contentPane.add(MovieYear);
		criticsLabel = new JLabel("CRITICS' RATING");
		criticsLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		criticsLabel.setBounds(552, 58, 288, 31);
		contentPane.add(criticsLabel);
		criticsLabel.setOpaque(true);
		criticsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		criticsLabel.setForeground(Color.WHITE);
		criticsLabel.setBackground(UIManager.getColor("Button.darkShadow"));
		panel_1 = new JPanel();
		panel_1.setBounds(51, 489, 419, 211);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 419, 155);
		panel_1.add(scrollPane);
		queryAreaTextArea = new JTextArea();
		scrollPane.setViewportView(queryAreaTextArea);
		queryAreaTextArea.setText("<SHOW QUERY HERE>");
		btnExecuteQuery = new JButton("Execute Query");
		btnExecuteQuery.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnExecuteQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateResults();
			}
		});
		btnExecuteQuery.setBounds(100, 166, 187, 34);
		panel_1.add(btnExecuteQuery);
		JLabel labelMovieTagValues = new JLabel("MOVIE TAG VALUES");
		labelMovieTagValues.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelMovieTagValues.setOpaque(true);
		labelMovieTagValues.setHorizontalAlignment(SwingConstants.CENTER);
		labelMovieTagValues.setForeground(Color.WHITE);
		labelMovieTagValues.setBackground(UIManager.getColor("Button.darkShadow"));
		labelMovieTagValues.setBounds(834, 58, 288, 31);
		contentPane.add(labelMovieTagValues);
		JPanel panelTagVariable = new JPanel();
		panelTagVariable.setLayout(null);
		panelTagVariable.setBounds(842, 301, 280, 115);
		contentPane.add(panelTagVariable);
		JLabel labelTagWeight = new JLabel("Tag Weight");
		labelTagWeight.setBounds(10, 18, 70, 14);
		panelTagVariable.add(labelTagWeight);
		comboBoxTagWeight = new JComboBox(new Object[] {});
		comboBoxTagWeight.setModel(new DefaultComboBoxModel(new String[] { "<", "=", ">" }));
		comboBoxTagWeight.setBounds(90, 11, 164, 29);
		panelTagVariable.add(comboBoxTagWeight);
		JLabel labelTagWeightValue = new JLabel("Value :");
		labelTagWeightValue.setBounds(10, 50, 65, 29);
		panelTagVariable.add(labelTagWeightValue);
		textFieldTagWeightValue = new JTextField();
		textFieldTagWeightValue.setColumns(10);
		textFieldTagWeightValue.setBounds(90, 51, 164, 29);
		panelTagVariable.add(textFieldTagWeightValue);
		panelMovieTagValues = new JPanel();
		panelMovieTagValues.setBounds(842, 87, 280, 215);
		contentPane.add(panelMovieTagValues);
		panelMovieTagValues.setLayout(new GridLayout(1, 0, 0, 0));
		scrollPane_2 = new JScrollPane();
		panelMovieTagValues.add(scrollPane_2);
		textAreaMovieTags = new JTextArea();
		scrollPane_2.setViewportView(textAreaMovieTags);
		panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.darkShadow"));
		panel.setForeground(Color.WHITE);
		panel.setBounds(480, 416, 642, 285);
		contentPane.add(panel);
		panel.setLayout(null);
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(23, 27, 609, 245);
		panel.add(scrollPane_1);
		resultLabel = new JLabel("RESULT");
		resultLabel.setBackground(Color.LIGHT_GRAY);
		resultLabel.setForeground(Color.WHITE);
		resultLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setBounds(300, 2, 73, 26);
		panel.add(resultLabel);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql:" +"//"+ "localhost" + ":" + 3306 + "/" + "siddhu", user, password);
			if (connection != null) {
				System.out.println("CONNECTED TO DATABASE " + connection.toString());
				gbc = new GridBagConstraints();
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.anchor = GridBagConstraints.NORTHWEST;
				filmLocationListenerForOr = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ArrayList<String> locationSelected = (ArrayList<String>) filmingLocation();
						String query = "(SELECT ML.movieID FROM Movie_location ML WHERE ";
						for (int i = 0; i < locationSelected.size(); i++) {
							if (i > 0) {
								query += " OR ";
							}
							query += "ML.location1 = '" + locationSelected.get(i) + "'";
						}
						query += ")";
					}
				};
				filmLocationListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ArrayList<String> locationSelected = (ArrayList<String>) filmingLocation();
						String query = "";
						for (int i = 0; i < locationSelected.size(); i++) {
							if (i > 0) {
								query += " AND ML" + (i - 1) + ".movieID IN ";
							}
							query += "(SELECT ML" + i + ".movieID FROM Movie_locations ML" + i + " WHERE ML" + i
									+ ".location1 = '" + locationSelected.get(i) + "'";
						}
						query += " AND ML" + (locationSelected.size() - 1) + ".movieID IN";
						System.out.println(query);
						finalLocSelectionQuery = query;
						finalMovieQuery = finalLocSelectionQuery + finalMovieQuery;
						for (int i = 0; i < locationSelected.size(); i++) {
							finalMovieQuery += ")";
						}
						System.out.println(finalMovieQuery);
					}
				};
				countryActionListenerForOr = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ArrayList<String> countrySelected = (ArrayList<String>) getCountry();
						String query = "(SELECT DISTINCT ML.location1 FROM Movie_countries MC, Movie_locations ML WHERE MC.movieID = ML.movieID AND ";
						PreparedStatement statement;

						for (int i = 0; i < countrySelected.size(); i++) {
							if (i > 0) {
								query += " OR ";
							}
							query += "MC.country = '" + countrySelected.get(i) + "'";
						}
						query += ")";
						System.out.println(query);
						if (countrySelected.size() == 0) {
							filmingLocationPanel.removeAll();
							filmingLocationPanel.revalidate();
							filmingLocationPanel.repaint();
							return;
						}
						try {
							statement = (PreparedStatement) connection.prepareStatement(query);
							ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(query);
							boolean emptyResult = true;

							while (resultSet.next()) {
								if (null != resultSet && null != resultSet.getString(1)
										&& !resultSet.getString(1).isEmpty()) {
									JCheckBox cb = new JCheckBox(resultSet.getString(1));
									filmingLocationPanel.add(cb, gbc);
									cb.addActionListener(filmLocationListenerForOr);
									filmingLocationPanel.revalidate();
									System.out.println(resultSet.getString(1));
									emptyResult = false;
								}
							}
							if (emptyResult) {
								filmingLocationPanel.removeAll();
								filmingLocationPanel.revalidate();
								filmingLocationPanel.repaint();
								return;
							}
						} catch (SQLException exception) {
							exception.printStackTrace();
						}
					}
				};
				countryActionListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ArrayList<String> countrySelected = (ArrayList<String>) getCountry();
						String query = "";

						for (int i = 0; i < countrySelected.size(); i++) {
							if (i > 0) {
								query += " AND MC" + (i - 1) + ".movieID IN ";
							}
							query += "(SELECT MC" + i + ".movieID FROM Movie_countries MC" + i + " WHERE MC" + i
									+ ".country = '" + countrySelected.get(i) + "'";
						}

						if (countrySelected.size() == 0) {
							filmingLocationPanel.removeAll();
							filmingLocationPanel.revalidate();
							filmingLocationPanel.repaint();
							return;
						}
						query += " AND MC" + (countrySelected.size() - 1) + ".movieID IN ";
						finalMovieQuery = query + finalGenresQuery;
						finalFilLocQuery = "SELECT DISTINCT location1 FROM Movie_locations WHERE movieID IN " + query
								+ finalGenresQuery;
						System.out.println("in action listener" + finalMovieQuery);
						for (int i = 0; i < countrySelected.size(); i++) {
							finalFilLocQuery += ")";
							finalMovieQuery += ")";
						}
						PreparedStatement statement;
						boolean emptyResult = true;
						System.out.println(finalFilLocQuery);
						try {
							statement = (PreparedStatement) connection.prepareStatement(finalFilLocQuery);
							ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalFilLocQuery);

							while (resultSet.next()) {
								if (null != resultSet && null != resultSet.getString(1)
										&& !resultSet.getString(1).isEmpty()) {
									JCheckBox cb = new JCheckBox(resultSet.getString(1));
									filmingLocationPanel.add(cb, gbc);
									cb.addActionListener(filmLocationListener);
									filmingLocationPanel.revalidate();
									System.out.println(resultSet.getString(1));
									emptyResult = false;
								}
							}

							if (emptyResult) {
								filmingLocationPanel.removeAll();
								filmingLocationPanel.revalidate();
								filmingLocationPanel.repaint();
								return;
							}
						} catch (SQLException exception) {
							exception.printStackTrace();
						}
					}
				};
				addGenres();

			} else {
				System.out.println("CONNECTION NOT ESTABLISHED!!! ");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void addGenres() {
		PreparedStatement statement;
		finalQuery = "SELECT DISTINCT M.genre  FROM Movie_Genres M";

		String tagsQuery = "select value from Tags";

		System.out.println(finalQuery);

		try {
			boolean emptyResult = true;
			statement = (PreparedStatement) connection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);

			while (resultSet.next()) {
				JCheckBox cb = new JCheckBox(resultSet.getString(1));
				genresPanel.add(cb, gbc);
				cb.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (andOrComboBox.getSelectedIndex() == 0) {
							getCountries();
						} else {
							getCountriesForOr();
						}
					}
				});
				genresPanel.revalidate();
				emptyResult = false;
			}

			if (emptyResult) {
				genresPanel.removeAll();
				genresPanel.revalidate();
				genresPanel.repaint();
				filmingLocationPanel.removeAll();
				filmingLocationPanel.revalidate();
				filmingLocationPanel.repaint();
			}
			// To generate Tags Section
			statement = (PreparedStatement) connection.prepareStatement(tagsQuery);
			resultSet = ((java.sql.Statement) statement).executeQuery(tagsQuery);
			String labels = "";
			while (resultSet.next()) {
				// System.out.println(resultSet.getString(1));
				labels = labels + "\n\r" + resultSet.getString(1);
			}
			textAreaMovieTags.setText(labels);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	private void removeCountry(String genName) {
		for (Component c : countryPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).getText().equals(genName)) {
				countryPanel.remove(c);
				countryPanel.revalidate();
				break;
			}
		}
		countryPanel.repaint();
		countryPanel.revalidate();
	}

	private void removeloc() {
		boolean subCategorySelected = false;
		for (Component c : countryPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {
				subCategorySelected = true;
				break;
			}
		}
		if (!subCategorySelected) {
			filmingLocationPanel.removeAll();
			filmingLocationPanel.repaint();
			filmingLocationPanel.revalidate();
		}
	}

	private void getCountriesForOr() {
		PreparedStatement statement;
		ArrayList<String> genreSelected = (ArrayList<String>) getGenres();
		queryOr = "(SELECT DISTINCT MC.country FROM Movie_genres MG, Movie_countries MC WHERE MC.movieID = MG.movieID AND ";
		String tagQuery = "(SELECT DISTINCT T.value from Tags T, Movie_genres MG, Movie_countries MC where T.ID = MG.movieID AND MC.movieID = MG.movieID AND ";
		int k = 0;
		for (int i = 0; i < genreSelected.size(); i++) {
			if (i > 0) {
				queryOr += " OR ";
				tagQuery += " OR ";
			}
			queryOr += "MG.Genre = '" + genreSelected.get(i) + "'";
			tagQuery += "MG.Genre = '" + genreSelected.get(i) + "'";

		}
		queryOr += ")";
		tagQuery += ")";
		if (genreSelected.size() == 0) {
			countryPanel.removeAll();
			countryPanel.revalidate();
			countryPanel.repaint();
			filmingLocationPanel.removeAll();
			filmingLocationPanel.revalidate();
			filmingLocationPanel.repaint();
			textAreaMovieTags.setText("");
			return;
		}
		System.out.println(queryOr);
		try {
			boolean emptyResult = true;
			statement = (PreparedStatement) connection.prepareStatement(queryOr);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(queryOr);
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
					JCheckBox cb = new JCheckBox(resultSet.getString(1));
					countryPanel.add(cb, gbc);
					cb.addActionListener(countryActionListenerForOr);
					countryPanel.revalidate();
					emptyResult = false;
				}
			}
			if (emptyResult) {
				countryPanel.removeAll();
				countryPanel.revalidate();
				countryPanel.repaint();
				filmingLocationPanel.removeAll();
				filmingLocationPanel.revalidate();
				filmingLocationPanel.repaint();
			}

			emptyResult = true;
			statement = (PreparedStatement) connection.prepareStatement(tagQuery);
			resultSet = ((java.sql.Statement) statement).executeQuery(tagQuery);
			String label = "";
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
					label = label + "\n\r" + resultSet.getString(1);
					emptyResult = false;
				}
			}
			textAreaMovieTags.setText(label);
			if (emptyResult) {
				countryPanel.removeAll();
				countryPanel.revalidate();
				countryPanel.repaint();
				filmingLocationPanel.removeAll();
				filmingLocationPanel.revalidate();
				filmingLocationPanel.repaint();
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}

	private void getCountries() {
		PreparedStatement statement;
		ArrayList<String> genresSelected = (ArrayList<String>) getGenres();
		String query = "";
		boolean andRequired = true;
		String tabQuery = "";
		int k = 0;
		for (int i = 0; i < genresSelected.size(); i++) {
			if (i > 0) {
				query += " AND M" + (i - 1) + ".movieID IN ";
				tabQuery += " AND M" + (i - 1) + ".movieID IN ";
			}
			query += "(SELECT M" + i + ".movieID FROM Movie_genres M" + i + " WHERE M" + i + ".Genre = '"
					+ genresSelected.get(i) + "'";
			tabQuery += "(SELECT M" + i + ".movieID FROM Movie_genres M" + i + " WHERE M" + i + ".Genre = '"
					+ genresSelected.get(i) + "'";
		}
		for (int i = 0; i < genresSelected.size(); i++) {
			query += ")";
			tabQuery += ")";
		}

		finalGenresQuery = query;
		finalQuery = "SELECT DISTINCT M.country  FROM Movie_countries M WHERE M.movieID IN " + query;
		finalCountryQuery = "SELECT DISTINCT M.movieID  FROM Movie_countries M WHERE M.movieID IN " + query;
		String finalTagQuery = "SELECT DISTINCT T.value  FROM Tags T WHERE T.id IN " + query;

		System.out.println(finalCountryQuery);

		try {
			if (query.isEmpty()) {
				countryPanel.removeAll();
				countryPanel.revalidate();
				countryPanel.repaint();
				filmingLocationPanel.removeAll();
				filmingLocationPanel.revalidate();
				filmingLocationPanel.repaint();
				textAreaMovieTags.setText("");
				return;
			}
			boolean emptyResult = true;
			statement = (PreparedStatement) connection.prepareStatement(finalQuery);
			ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(finalQuery);

			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
					JCheckBox cb = new JCheckBox(resultSet.getString(1));
					countryPanel.add(cb, gbc);
					cb.addActionListener(countryActionListener);
					countryPanel.revalidate();
					emptyResult = false;
				}
			}
			emptyResult = true;
			statement = (PreparedStatement) connection.prepareStatement(finalTagQuery);
			resultSet = ((java.sql.Statement) statement).executeQuery(finalTagQuery);
			String labels = "";
			while (resultSet.next()) {
				if (null != resultSet && null != resultSet.getString(1) && !resultSet.getString(1).isEmpty()) {
//						System.out.println(resultSet.getString(1));
					labels = labels + "\n\r" + resultSet.getString(1);
					emptyResult = false;
				}
			}
			textAreaMovieTags.setText(labels);
			if (emptyResult) {
				countryPanel.removeAll();
				countryPanel.revalidate();
				countryPanel.repaint();
				filmingLocationPanel.removeAll();
				filmingLocationPanel.revalidate();
				filmingLocationPanel.repaint();
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private boolean checkGenresSelected() {

		for (Component c : genresPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {

				return true;
			}
		}

		JOptionPane.showMessageDialog(null, "PLEASE SELECT ATLEAST ONE GENRES");
		return false;
	}

	private boolean checkIfCountrySelected() {

		for (Component c : countryPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {

				return true;
			}
		}
		return false;
	}

	private boolean checkIfFilmLocationSelected() {

		for (Component c : filmingLocationPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {

				return true;
			}
		}
		return false;
	}

	private ArrayList<String> getGenres() {
		ArrayList<String> newString = new ArrayList<String>();
		int i = 0;
		for (Component c : genresPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {
				newString.add(((JCheckBox) c).getText());
				i++;
			}
		}

		return newString;
	}

	private ArrayList<String> getCountry() {
		ArrayList<String> newString = new ArrayList<String>();
		int i = 0;
		for (Component c : countryPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {
				newString.add(((JCheckBox) c).getText());
				i++;
			}
		}

		return newString;
	}

	private ArrayList<String> filmingLocation() {
		ArrayList<String> newString = new ArrayList<String>();
		int i = 0;
		for (Component c : filmingLocationPanel.getComponents()) {
			if (c != null && (c instanceof JCheckBox) && ((JCheckBox) c).isSelected()) {
				newString.add(((JCheckBox) c).getText());
				i++;
			}
		}

		return newString;
	}

	private String getCriticsRatingCriteria() {
		return criticsRatingComboBox.getSelectedItem().toString();
	}

	private String getCriticsRatingValue() {
		return criticsRatingValueTextField.getText();
	}

	private String getNoOfReviewsCriteria() {
		return noOfReviowsComboBox.getSelectedItem().toString();
	}

	private String getNoOfReviewsValue() {
		return noOfReviowsValueTextField.getText();
	}



	private String getTagWeightCriteria() {
		return comboBoxTagWeight.getSelectedItem().toString();
	}

	private String getTagWeight() {
		return textFieldTagWeightValue.getText();
	}

	private String getTagValues() {
		return textAreaMovieTags.getText();
	}

	private void generateResults() {
		ArrayList<String> genres = getGenres();
		ArrayList<String> countries = getCountry();
		ArrayList<String> filmingLocations = filmingLocation();


		String tagWeightCriteria = getTagWeightCriteria();
		String tagWeight = getTagWeight();
		String tagValues = getTagValues();
		String criticsRatingCriteria = getCriticsRatingCriteria();
		String criticsRatingValue = getCriticsRatingValue();
		String noOfReviewsCriteria = getNoOfReviewsCriteria();
		String noOfReviewsValue = getNoOfReviewsValue();

		System.out.println("Genres ");
		genres.forEach(genre -> System.out.println(genre));
		System.out.println("countries ");
		countries.forEach(country -> System.out.println(country));
		System.out.println("filmingLocations ");
		filmingLocations.forEach(filmingLocation -> System.out.println(filmingLocation));


		System.out.println("tagWeightCriteria " + tagWeightCriteria);
		System.out.println("tagWeight " + tagWeight);
		System.out.println("tagValues " + tagValues);
		System.out.println("criticsRatingCriteria " + criticsRatingCriteria);
		System.out.println("criticsRatingValue " + criticsRatingValue);
		System.out.println("noOfReviewsCriteria " + noOfReviewsCriteria);
		System.out.println("noOfReviewsValue " + noOfReviewsValue);

		query = "";
		if (andOrComboBox.getSelectedIndex() == 0) {
			int k = 0;
			for (int i = 0; i < genres.size(); i++) {
				if (i > 0) {
					query += " AND MG" + (i - 1) + ".movieID IN ";
				}
				query += "(SELECT MG" + i + ".movieID FROM Movie_genres MG" + i + " WHERE MG" + i + ".genre = '"
						+ genres.get(i) + "'";// + andOrOperators[searchForComboBox.getSelectedIndex()] + "
												// BC.categoryName = '" + categorySelected.get(i) + "'";
			}

			if (checkIfCountrySelected()) {
				query += " AND MG" + (genres.size() - 1) + ".movieID IN ";
				for (int i = 0; i < countries.size(); i++) {
					if (i > 0) {
						query += " AND MC" + (i - 1) + ".movieID IN ";
					}
					query += "(SELECT MC" + i + ".movieID FROM Movie_countries MC" + i + " WHERE MC" + i
							+ ".country = '" + countries.get(i) + "'";// +
																		// andOrOperators[searchForComboBox.getSelectedIndex()]
																		// + " BC.categoryName = '" +
																		// categorySelected.get(i) + "'";
				}

				if (checkIfFilmLocationSelected()) {
					query += " AND MC" + (countries.size() - 1) + ".movieID IN ";

					for (int i = 0; i < filmingLocations.size(); i++) {
						if (i > 0) {
							query += " AND ML" + (i - 1) + ".movieID IN ";
						}
						query += "(SELECT ML" + i + ".movieID FROM Movie_locations ML" + i + " WHERE ML" + i
								+ ".location1 = '" + filmingLocations.get(i) + "'";// +
																					// andOrOperators[searchForComboBox.getSelectedIndex()]
																					// + " BC.categoryName = '" +
																					// categorySelected.get(i) + "'";
					}

				}
			}

			System.out.println("Final Query: " + query);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date dateObject;
			boolean andRequiredForReviews = false;



			if (!criticsRatingValue.isEmpty()) {
				if (andRequiredForReviews) {
					query += " AND ((m.rtAllCriticsRating + m.rtTopCriticsRating + m.rtAudienceRating)/3) "
							+ criticsRatingCriteria + " " + criticsRatingValue;
				} else {
					if (!checkIfCountrySelected()) {
						query += " AND MG" + (genres.size() - 1) + ".movieID IN " + "(SELECT M.id FROM Movies M";
					} else if (!checkIfFilmLocationSelected()) {
						query += " AND MC" + (countries.size() - 1) + ".movieID IN " + "(SELECT M.id FROM Movies M";
					} else {
						query += " AND ML" + (filmingLocations.size() - 1) + ".movieID IN "
								+ "(SELECT M.id FROM Movies M";
					}
					query += " WHERE ((m.rtAllCriticsRating + m.rtTopCriticsRating + m.rtAudienceRating)/3) "
							+ criticsRatingCriteria + " " + criticsRatingValue;
					andRequiredForReviews = true;
				}
			}

			if (!noOfReviewsValue.isEmpty()) {
				if (andRequiredForReviews) {
					query += " AND ((m.rtAllCriticsNumReviews+ m.rtTopCriticsNumReviews+ m.rtAudienceNumRatings)/3) "
							+ noOfReviewsCriteria + " " + noOfReviewsValue;
				} else {
					if (!checkIfCountrySelected()) {
						query += " AND MG" + (genres.size() - 1) + ".movieID IN " + "(SELECT M.id FROM Movies M";
					} else if (!checkIfFilmLocationSelected()) {
						query += " AND MC" + (countries.size() - 1) + ".movieID IN " + "(SELECT M.id FROM Movies M";
					} else {
						query += " AND ML" + (filmingLocations.size() - 1) + ".movieID IN "
								+ "(SELECT M.id FROM Movies M";
					}
					query += " WHERE ((m.rtAllCriticsNumReviews+ m.rtTopCriticsNumReviews+ m.rtAudienceNumRatings)/3) "
							+ noOfReviewsCriteria + " " + noOfReviewsValue;
					andRequiredForReviews = true;
				}
			}

			if (!tagWeight.isEmpty()) {
				if (andRequiredForReviews) {
					query += " AND (t.tagWeight) " + tagWeightCriteria + " " + tagWeight;
				} else {
					if (!checkIfCountrySelected()) {
						query += " AND MG" + (genres.size() - 1) + ".movieID IN " + "(SELECT M.id FROM Movies M";
					} else if (!checkIfFilmLocationSelected()) {
						query += " AND MC" + (countries.size() - 1) + ".movieID IN " + "(SELECT M.id FROM Movies M";
					} else {
						query += " AND ML" + (filmingLocations.size() - 1) + ".movieID IN "
								+ "(SELECT M.id FROM Movies M";
					}
					query += " WHERE (t.tagWeight) " + tagWeightCriteria + " " + tagWeight;
					andRequiredForReviews = true;
				}
			}

			if (andRequiredForReviews) {
				query += ")";
			}

			for (int i = 0; i < genres.size(); i++) {
				query += ")";
			}

			for (int i = 0; i < countries.size(); i++) {
				query += ")";
			}

			for (int i = 0; i < filmingLocations.size(); i++) {
				query += ")";
			}
			query = "select distinct m.id, m.title,mg.genre, m.year, mc.country,ml.location1, ((m.rtAllCriticsRating + m.rtTopCriticsRating + m.rtAudienceRating)/3)AS AVERAGE_RATING ,((m.rtAllCriticsNumReviews+ m.rtTopCriticsNumReviews+ m.rtAudienceNumRatings)/3) AS AVERAGE_REVIEWS\r\n"
					+ "from movie_genres mg, movies m,movie_locations ml, Movie_countries mc, Movie_tags t where t.movieID=m.id AND m.id=mg.movieid and m.id=mc.movieid and m.id=ml.movieid and mg.movieid IN"
					+ query;

			System.out.println(query);
			try {
				queryAreaTextArea.setText(query);
				PreparedStatement statement;
				statement = (PreparedStatement) connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(query);
				ResultSetMetaData rsmd = resultSet.getMetaData();

				ResultSetMetaData rsMetaData = resultSet.getMetaData();

				int numberOfColumns = rsMetaData.getColumnCount();
				System.out.println("resultSet MetaData column Count=" + numberOfColumns);

				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.println("column MetaData ");
					System.out.println("column number " + i);

					System.out.println(rsMetaData.getColumnName(i));
				}

				int columnsNumber = rsMetaData.getColumnCount();
				HashMap<String, ArrayList<String>> map = new HashMap<>();
				for (int j = 1; j <= columnsNumber; j++) {
					String Colkey = rsmd.getColumnName(j);
					map.put(Colkey, new ArrayList<String>());
				}
				while (resultSet.next()) {
					for (Entry<String, ArrayList<String>> e : map.entrySet()) {
						String Colkey = e.getKey();
						List<String> list = e.getValue();
						list.add(resultSet.getString(Colkey));
					}
				}

				columnsNumber = rsmd.getColumnCount();
				resultSet.last();

				System.out.println("resultSet.getRow() " + resultSet.getRow());
				data = new Object[resultSet.getRow() + 1][columnsNumber];
				resultSet.beforeFirst();
				int i = 0;

				String[] columns = new String[] { "ID", "TITLE", "GENRE", "YEAR", "COUNTRY", "FILIM LOC", "AVG RATING",
						"AVG REVIEWS" };
				i = 1;
				while (resultSet.next()) {
					System.out.print(resultSet.getObject(1) + " ");
					System.out.print(resultSet.getObject(2) + " ");
					System.out.print(resultSet.getObject(3) + " ");
					System.out.print(resultSet.getObject(4) + " ");
					System.out.print(resultSet.getObject(5) + " ");
					System.out.print(resultSet.getObject(6) + " ");
					System.out.print(resultSet.getObject(7) + " ");
					System.out.print(resultSet.getObject(8) + " ");

					data[i][0] = resultSet.getObject(1);
					data[i][1] = resultSet.getObject(2);
					data[i][2] = resultSet.getObject(3);
					data[i][3] = resultSet.getObject(4);
					data[i][4] = resultSet.getObject(5);
					data[i][5] = resultSet.getObject(6);
					data[i][6] = resultSet.getObject(7);
					data[i][7] = resultSet.getObject(8);
					i++;
				}
				resultTable = new JTable(data, columns);
				resultTable.getColumnModel().getColumn(0).setPreferredWidth(30);
				resultTable.getColumnModel().getColumn(1).setPreferredWidth(80);
				resultTable.getColumnModel().getColumn(2).setPreferredWidth(50);
				resultTable.getColumnModel().getColumn(3).setPreferredWidth(50);
				resultTable.getColumnModel().getColumn(4).setPreferredWidth(50);
				resultTable.getColumnModel().getColumn(5).setPreferredWidth(50);
				resultTable.getColumnModel().getColumn(6).setPreferredWidth(50);
				resultTable.getColumnModel().getColumn(7).setPreferredWidth(50);
				resultTable.setFillsViewportHeight(true);
				resultTable.setRowSelectionAllowed(true);
				resultTable.setBounds(0, 0, 599, 440);
				resultTable.revalidate();
				panel.revalidate();
				contentPane.revalidate();
				scrollPane_1.setViewportView(resultTable);

			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		} else {

			// For OR

			ArrayList<String> orQueries = new ArrayList<String>();
			ArrayList<String> orQueriesToDate = new ArrayList<String>();
			ArrayList<String> orQueriesStars = new ArrayList<String>();
			ArrayList<String> orQueriesVote = new ArrayList<String>();

			if (filmingLocations.size() > 0) {
				for (int i = 0; i < genres.size(); i++) {
					for (int j = 0; j < countries.size(); j++) {
						for (int k = 0; k < filmingLocation().size(); k++) {
							orQueries.add(
									"SELECT MG.movieID FROM Movie_genres MG, Movie_countries MC, Movie_locations ML WHERE MG.movieID = "
											+ "MC.movieID AND MG.movieID = ML.movieID AND MG.genre = '" + genres.get(i)
											+ "'" + " AND MC.country = '" + countries.get(j) + "'"
											+ " AND ML.location1 = '" + filmingLocations.get(k) + "'");
						}
					}
				}
			}

			else if (countries.size() > 0) {
				for (int i = 0; i < genres.size(); i++) {
					for (int j = 0; j < countries.size(); j++) {

						orQueries.add("SELECT MG.movieID FROM Movie_genres MG, Movie_countries MC WHERE MG.movieID = "
								+ "MC.movieID AND MG.genre = '" + genres.get(i) + "'" + " AND MC.country = '"
								+ countries.get(j) + "'");
					}
				}
			} else {
				for (int i = 0; i < genres.size(); i++) {
					orQueries.add(
							"SELECT MG.movieID FROM Movie_Genres MG WHERE " + "MG.genre = '" + genres.get(i) + "'");
				}
			}
			queryOr = "";
			for (int i = 0; i < orQueries.size(); i++) {
				queryOr += orQueries.get(i);
				if (i != orQueries.size() - 1) {
					queryOr += " UNION ALL ";
				}
			}

			// Check For Reviews
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date dateObject;




			for (int i = 0; i < orQueries.size(); i++) {
				boolean orRequiredForReviews = false;
				if (!criticsRatingValueTextField.getText().isEmpty()) {
					String newQuery = orQueries.get(i);
					String queryString[] = newQuery.split("FROM");
					if (!orRequiredForReviews) {
						queryString[0] += "FROM movies M, ";
					} else {
						queryString[0] += "FROM";
					}

					String whereString[] = queryString[1].split("WHERE");

					if (!orRequiredForReviews) {
						whereString[0] += "WHERE M.ID = MG.movieID AND ((m.rtAllCriticsRating + m.rtTopCriticsRating + m.rtAudienceRating)/3) "
								+ criticsRatingCriteria + " " + criticsRatingValue;
					} else {
						whereString[0] += "WHERE ((m.rtAllCriticsRating + m.rtTopCriticsRating + m.rtAudienceRating)/3) "
								+ criticsRatingCriteria + " " + criticsRatingValue;
					}

					newQuery = queryString[0] + whereString[0] + " AND " + whereString[1];

					orRequiredForReviews = true;
					orQueriesStars.add(i, newQuery);

				}

			}

			for (int i = 0; i < orQueries.size(); i++) {
				boolean orRequiredForReviews = false;
				if (!noOfReviowsValueTextField.getText().isEmpty()) {
					String newQuery = orQueries.get(i);
					String queryString[] = newQuery.split("FROM");
					if (!orRequiredForReviews) {
						queryString[0] += "FROM Movies M, ";
					} else {
						queryString[0] += "FROM";
					}

					String whereString[] = queryString[1].split("WHERE");

					if (!orRequiredForReviews) {
						whereString[0] += "WHERE M.ID = MG.movieID AND ((m.rtAllCriticsNumReviews+ m.rtTopCriticsNumReviews+ m.rtAudienceNumRatings)/3) "
								+ noOfReviewsCriteria + " " + noOfReviewsValue;
					} else {
						whereString[0] += "WHERE ((m.rtAllCriticsNumReviews+ m.rtTopCriticsNumReviews+ m.rtAudienceNumRatings)/3) "
								+ noOfReviewsCriteria + " " + noOfReviewsValue;
					}

					newQuery = queryString[0] + whereString[0] + " AND " + whereString[1];

					orRequiredForReviews = true;
					orQueriesVote.add(i, newQuery);

				}
			}

			if (!tagWeight.isEmpty()) {
				boolean orRequiredForReviews = false;
				if (!orRequiredForReviews) {
					query += " AND (t.tagWeight) " + tagWeightCriteria + " " + tagWeight;
				} else {
					if (!checkIfCountrySelected()) {
						query += " AND MG" + (genres.size() - 1) + ".movieID IN " + "(SELECT M.id FROM Movies M";
					} else if (!checkIfFilmLocationSelected()) {
						query += " AND MC" + (countries.size() - 1) + ".movieID IN " + "(SELECT M.id FROM Movies M";
					} else {
						query += " AND ML" + (filmingLocations.size() - 1) + ".movieID IN "
								+ "(SELECT M.id FROM Movies M";
					}
					query += " WHERE (t.tagWeight) " + tagWeightCriteria + " " + tagWeight;
					orRequiredForReviews = true;
				}
			}
			queryOr = "";
			for (int i = 0; i < orQueries.size(); i++) {
				queryOr += orQueries.get(i);
				if (i != orQueries.size() - 1) {
					queryOr += " UNION ALL ";
				}
			}

			for (int i = 0; i < orQueriesToDate.size(); i++) {
				if (i == 0 && orQueries.size() > 0) {
					queryOr += " UNION ALL ";
				}
				queryOr += orQueriesToDate.get(i);
				if (i != orQueriesToDate.size() - 1) {
					queryOr += " UNION ALL ";
				}
			}

			for (int i = 0; i < orQueriesStars.size(); i++) {
				if (i == 0 && (orQueries.size() > 0 || orQueriesToDate.size() > 0)) {
					queryOr += " UNION ALL ";
				}
				queryOr += orQueriesStars.get(i);
				if (i != orQueriesStars.size() - 1) {
					queryOr += " UNION ALL ";
				}
			}

			for (int i = 0; i < orQueriesVote.size(); i++) {
				if (i == 0 && (orQueries.size() > 0 || orQueriesStars.size() > 0 || orQueriesToDate.size() > 0)) {
					queryOr += " UNION ALL ";
				}
				queryOr += orQueriesVote.get(i);
				if (i != orQueriesVote.size() - 1) {
					queryOr += " UNION ALL ";
				}
			}

			queryOr = "select distinct m.id, m.title,mg.genre, m.year, mc.country,ml.location1, ((m.rtAllCriticsRating + m.rtTopCriticsRating + m.rtAudienceRating)/3)AS AVERAGE_RATING ,((m.rtAllCriticsNumReviews+ m.rtTopCriticsNumReviews+ m.rtAudienceNumRatings)/3) AS AVERAGE_REVIEWS\r\n"
					+ "from movie_genres mg, movies m,movie_locations ml, Movie_countries mc where m.id=mg.movieid and m.id=mc.movieid and m.id=ml.movieid and mg.movieid IN ("
					+ queryOr + ")";

			// queryOr = "SELECT businessName, city, state, stars FROM Business B WHERE
			// B.businessId IN (" + queryOr + ")";
			System.out.println("Final OR Query: " + queryOr);
			try {
				queryAreaTextArea.setText(queryOr);
				PreparedStatement statement;
				statement = (PreparedStatement) connection.prepareStatement(queryOr, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				ResultSet resultSet = ((java.sql.Statement) statement).executeQuery(queryOr);
				ResultSetMetaData rsmd = resultSet.getMetaData();

				int columnsNumber = rsmd.getColumnCount();
				resultSet.last();

				System.out.println("resultSet.getRow() " + resultSet.getRow());
				data = new Object[resultSet.getRow() + 1][columnsNumber];
				resultSet.beforeFirst();
				int i = 0;

				String[] columns = new String[] { "ID", "TITLE", "GENRE", "YEAR", "COUNTRY", "FILIM LOC", "AVG RATING",
						"AVG REVIEWS" };
				i = 1;
				while (resultSet.next()) {
					System.out.print(resultSet.getObject(1) + " ");
					System.out.print(resultSet.getObject(2) + " ");
					System.out.print(resultSet.getObject(3) + " ");
					System.out.print(resultSet.getObject(4) + " ");
					System.out.print(resultSet.getObject(5) + " ");
					System.out.print(resultSet.getObject(6) + " ");
					System.out.print(resultSet.getObject(7) + " ");
					System.out.print(resultSet.getObject(8) + " ");

					data[i][0] = resultSet.getObject(1);
					data[i][1] = resultSet.getObject(2);
					data[i][2] = resultSet.getObject(3);
					data[i][3] = resultSet.getObject(4);
					data[i][4] = resultSet.getObject(5);
					data[i][5] = resultSet.getObject(6);
					data[i][6] = resultSet.getObject(7);
					data[i][7] = resultSet.getObject(8);
					i++;
				}
				resultTable = new JTable(data, columns);
				resultTable.getColumnModel().getColumn(0).setPreferredWidth(30);
				resultTable.getColumnModel().getColumn(1).setPreferredWidth(80);
				resultTable.getColumnModel().getColumn(2).setPreferredWidth(50);
				resultTable.getColumnModel().getColumn(3).setPreferredWidth(50);
				resultTable.getColumnModel().getColumn(4).setPreferredWidth(50);
				resultTable.getColumnModel().getColumn(5).setPreferredWidth(50);
				resultTable.getColumnModel().getColumn(6).setPreferredWidth(50);
				resultTable.getColumnModel().getColumn(7).setPreferredWidth(50);
				resultTable.setFillsViewportHeight(true);
				resultTable.setRowSelectionAllowed(true);
				resultTable.setBounds(0, 0, 599, 440);
				resultTable.revalidate();
				panel.revalidate();
				contentPane.revalidate();
				scrollPane_1.setViewportView(resultTable);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}

	}
}
