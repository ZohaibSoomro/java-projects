import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.Timer;

public class MainPage {
	// Eigenschaften definieren
	private JFrame frame;
	private JPanel mainPanel;
	private President currentPresident;
	private JButton playWithFrndBtn;
	private JButton playWithPcBtn;
	private ButtonGroup radioButtonGroup;
	private JLabel gameModeLabel;
	private String player1;
	private String player2;
	private Color defaultColor = new Color(0xFFEEEEEE);
	private Color cardPageColor = Color.CYAN.darker();
	private GameMode gameMode;
	private ArrayList<String> imageFileNames = new ArrayList<>();
	private JLabel player1TurnLabel, player2TurnLabel;
	private ArrayList<President> presidentInfoList = new ArrayList<>();
	private boolean isPlayer1sTurn = true;
	private JButton presidentNbtn1, presidentNbtn2, inagurationAgeBtn1, inagurationAgeBtn2, noOfChildrenBtn1,
			noOfChildrenBtn2, noOfVetoesBtn1, noOfVetoesBtn2, tenureInDaysBtn1, tenureInDaysBtn2, lengthOfSpeechBtn1,
			lengthOfSpeechBtn2;
	JLabel presidentNameLbl1, presidentNameLbl2, presidentImgLbl1, presidentImgLbl2;
	int player1Score = 16, player2Score = 16;
	private final String[] categories = { "PNo", "IAg", "Tid", "NoV", "NoC", "LoS" };
	private JLabel cardsNoPlayer2Label, cardsNoPlayer1Label;
	int counter = 0;

	// Aufzählung zum Definieren eines Spielmodus
	private enum GameMode {
		Vs_Computer, Vs_Friend
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Objekt jetzt erstellen
					MainPage window = new MainPage();
				}

				catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	/**
	 * Erstellen Sie die Anwendung.
	 */
	public MainPage() {
		initialize();
		frame.setVisible(true); // Rahmen sichtbar machen
	}

	/**
	 * Initialisieren Sie den Inhalt des Frames.
	 */
	private void initialize() {
		getPresidentsInfo(); // Einstellung von Präsidenteninformationen
		getPresidentsImagePaths(); // Einstellen des Bildpfades des Präsidenten

		// Rahmen initialisieren
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 650);
		frame.setLocationRelativeTo(null); // so dass es in der Mitte des Bildschirms aussieht
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setTitle("USA Presidents Quartet Game");

		gameModeLabel = new JLabel();
		gameModeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		gameModeLabel.setBounds(10, 10, 300, 38);
		gameModeLabel.setForeground(Color.blue);
		frame.getContentPane().add(gameModeLabel);

		playWithFrndBtn = new JButton("Play With A Friend");
		// aHinzufügen von Funktionen zum Spielen mit einem Freund-Button-Button
		playWithFrndBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				gameMode = GameMode.Vs_Friend; // Spielmodus einstellen
				gameModeLabel.setText("Game Mode: " + gameMode.toString());

				mainPanel = new JPanel();
				mainPanel.setBounds(181, 160, 420, 271);
				mainPanel.setLayout(null);

				// Beschriftungen, Textfelder und Startbutton definieren

				JLabel player1NameLabel = new JLabel("Player 1 name");
				player1NameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
				player1NameLabel.setBounds(15, 40, 155, 33);
				mainPanel.add(player1NameLabel);

				JLabel player2NameLabel = new JLabel("Player 2 name");
				player2NameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
				player2NameLabel.setBounds(15, 106, 155, 33);
				mainPanel.add(player2NameLabel);

				JTextField player1NameTf = new JTextField();
				player1NameTf.setBounds(166, 42, 233, 33);
				player1NameTf.setFont(new Font("Tahoma", Font.PLAIN, 20));
				player1NameTf.setColumns(10);
				mainPanel.add(player1NameTf);

				JTextField player2NameTf = new JTextField();
				player2NameTf.setColumns(10);
				player2NameTf.setFont(new Font("Tahoma", Font.PLAIN, 20));
				player2NameTf.setBounds(166, 108, 233, 33);
				mainPanel.add(player2NameTf);

				JButton startBtn = new JButton("Start");
				// Hinzufügen von Funktionen zum Startknopf
				startBtn.addActionListener(new ActionListener() {
					String pl1 = "", pl2 = "";

					public void actionPerformed(ActionEvent e) {
						// Spielernamen bekommen
						pl1 = player1NameTf.getText().toString();
						pl2 = player2NameTf.getText().toString();
						if (!pl1.isEmpty() && !pl2.isEmpty()) {
							player1 = pl1;
							player2 = pl2;
							mainPanel.setVisible(false);
							frame.repaint();
							// Beschriftungstext einstellen
							player1TurnLabel.setText(player1 + "'s turn");
							player2TurnLabel.setText(player2 + "'s turn");
							goToCardPage(); // Navigieren zur Kartenseite
							player1TurnLabel.setVisible(true);
							cardsNoPlayer1Label.setVisible(true);
							frame.repaint();
						} else {
							JOptionPane.showMessageDialog(frame, "Please enter names!");
						}
					}

				});
				startBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
				startBtn.setBounds(138, 166, 165, 42);

				mainPanel.add(startBtn);
				frame.getContentPane().add(mainPanel);
				playWithFrndBtn.setVisible(false);
				playWithPcBtn.setVisible(false);
				frame.repaint();

			}
		});
		playWithFrndBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		playWithFrndBtn.setBounds(72, 214, 282, 160);
		frame.getContentPane().add(playWithFrndBtn);

		playWithPcBtn = new JButton("Play With Computer");
		// Hinzufügen von Funktionen zum Spielen mit der Computertaste
		playWithPcBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameMode = GameMode.Vs_Computer; // Spielmodus einstellen
				gameModeLabel.setText("Game Mode: " + gameMode.toString());
				mainPanel = new JPanel();
				mainPanel.setBounds(180, 160, 420, 270);
				mainPanel.setLayout(null);

				// Beschriftungen, Textfelder und Startbutton definieren

				JLabel yourNameLabel = new JLabel("Your name");
				yourNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
				yourNameLabel.setBounds(15, 36, 114, 38);
				mainPanel.add(yourNameLabel);

				JTextField player1NameTf = new JTextField();
				player1NameTf.setBounds(149, 33, 220, 41);
				player1NameTf.setFont(new Font("Tahoma", Font.PLAIN, 20));
				mainPanel.add(player1NameTf);
				player1NameTf.setColumns(10);

				// Optionsfeldgruppe, sodass nur eine Option ausgewählt werden kann
				radioButtonGroup = new ButtonGroup();

				JRadioButton easyLevelRadioBtn = new JRadioButton("Easy");
				easyLevelRadioBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
				easyLevelRadioBtn.setBounds(52, 104, 89, 33);
				easyLevelRadioBtn.setSelected(true);
				easyLevelRadioBtn.setActionCommand("Easy");
				radioButtonGroup.add(easyLevelRadioBtn);
				mainPanel.add(easyLevelRadioBtn);

				JRadioButton hardLevelRadioBtn = new JRadioButton("Hard");
				hardLevelRadioBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
				hardLevelRadioBtn.setBounds(159, 104, 97, 33);
				hardLevelRadioBtn.setActionCommand("Hard");
				radioButtonGroup.add(hardLevelRadioBtn);
				mainPanel.add(hardLevelRadioBtn);

				JRadioButton superHardRadioBtn = new JRadioButton("Super Hard");
				superHardRadioBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
				superHardRadioBtn.setBounds(257, 104, 145, 33);
				superHardRadioBtn.setActionCommand("super hard");
				radioButtonGroup.add(superHardRadioBtn);
				mainPanel.add(superHardRadioBtn);

				// Start-Taste, um das Spiel zu starten
				JButton startBtn = new JButton("Start");
				startBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
				startBtn.setBounds(138, 166, 165, 42);
				// Hinzufügen von Funktionen zur Startschaltfläche
				startBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// den Namen von Spieler 1 erhalten
						String pl1 = player1NameTf.getText().toString();
						if (!pl1.isEmpty()) {
							player1 = pl1;
							player1TurnLabel.setText(player1 + "'s turn");
							mainPanel.setVisible(false);
							player1TurnLabel.setVisible(true);
							cardsNoPlayer1Label.setVisible(true);
							goToCardPage(); // Navigieren Sie zur Kartenseite der Spieler
							frame.repaint();

						} else {
							JOptionPane.showMessageDialog(frame, "Please enter a name!");
						}
					}

				});
				mainPanel.add(startBtn);

				frame.getContentPane().add(mainPanel);
				playWithFrndBtn.setVisible(false);
				playWithPcBtn.setVisible(false);
				frame.repaint();

			}
		});
		playWithPcBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		playWithPcBtn.setBounds(418, 214, 282, 160);
		frame.getContentPane().add(playWithPcBtn);

		// Labels für Spieler1 und Spieler2/Computer definieren
		player1TurnLabel = new JLabel();

		player1TurnLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		player1TurnLabel.setBounds(10, 414, 159, 50);
		player1TurnLabel.setForeground(Color.BLUE);
		frame.getContentPane().add(player1TurnLabel);
		player1TurnLabel.setVisible(isPlayer1sTurn);

		player2TurnLabel = new JLabel();

		player2TurnLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		player2TurnLabel.setBounds(640, 414, 159, 50);
		player2TurnLabel.setForeground(Color.RED);
		frame.getContentPane().add(player2TurnLabel);
		player2TurnLabel.setVisible(!isPlayer1sTurn);

		cardsNoPlayer1Label = new JLabel("You have " + player1Score + " cards.");
		cardsNoPlayer1Label.setForeground(Color.BLUE);
		cardsNoPlayer1Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cardsNoPlayer1Label.setBounds(10, 457, 149, 38);
		frame.getContentPane().add(cardsNoPlayer1Label);
		cardsNoPlayer1Label.setVisible(isPlayer1sTurn);

		cardsNoPlayer2Label = new JLabel("You have " + player2Score + " cards.");
		cardsNoPlayer2Label.setForeground(Color.RED);
		cardsNoPlayer2Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cardsNoPlayer2Label.setBounds(640, 457, 149, 38);
		frame.getContentPane().add(cardsNoPlayer2Label);
		cardsNoPlayer2Label.setVisible(!isPlayer1sTurn);

		player1TurnLabel.setVisible(false);
		player2TurnLabel.setVisible(false);
		cardsNoPlayer1Label.setVisible(false);
		cardsNoPlayer2Label.setVisible(false);

	}

	// Methode, um zur Kartenseite zu navigieren
	private void goToCardPage() {
		playWithFrndBtn.setVisible(false);
		playWithPcBtn.setVisible(false);

		President president = presidentInfoList.get(new Random().nextInt(presidentInfoList.size()));
		String presidentImagePath = getPresidentImagePath(president.name);
		currentPresident = president;
		mainPanel = cardPanel(president, presidentImagePath);
		mainPanel.setBackground(defaultColor);
		mainPanel.setVisible(true);
		frame.getContentPane().add(mainPanel);
		frame.repaint(); // Aktualisieren Sie den Rahmen

	}

	// Methode, die das Kartenfeld (Karte von Spieler1 + Karte von
	// Spieler2/Computer) zurückgibt
	public JPanel cardPanel(President president, String imagePath) {

		JPanel panel = new JPanel();
		panel.setBounds(120, 20, 550, 610);
		panel.setLayout(null);
		panel.setBackground(Color.CYAN.darker());

		// Namensschild für Spieler1
		presidentNameLbl1 = new JLabel(president.name);
		presidentNameLbl1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		presidentNameLbl1.setHorizontalAlignment(SwingConstants.CENTER);
		presidentNameLbl1.setBounds(5, 0, 303, 56);
		panel.add(presidentNameLbl1);

		// Namensschild für Spieler2/computer Spieler2
		presidentNameLbl2 = new JLabel(president.name);
		presidentNameLbl2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		presidentNameLbl2.setHorizontalAlignment(SwingConstants.CENTER);
		presidentNameLbl2.setBounds(250, 0, 303, 56);
		panel.add(presidentNameLbl2);

		// Bildbezeichnung für Spieler1
		presidentImgLbl1 = new JLabel();
		presidentImgLbl1.setBounds(60, 60, 179, 177);
		presidentImgLbl1.setIcon(getIcon(imagePath));
		panel.add(presidentImgLbl1);

		// Bildbezeichnung für Spieler2/Computerspieler
		presidentImgLbl2 = new JLabel();
		presidentImgLbl2.setBounds(310, 60, 179, 177);
		presidentImgLbl2.setIcon(getIcon(imagePath));
		panel.add(presidentImgLbl2);

		// Trennzeichen, um Namen und Bilder von den übrigen Eigenschaften des
		// Präsidenten zu trennen
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 250, 510, 8);
		panel.add(separator);

		presidentNbtn1 = new JButton("President-N          " + president.presidentNumber);
		presidentNbtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Definieren der Funktionalität der Schaltfläche presidentNbtn1 im Modus
				// vs_computer
				if (gameMode == GameMode.Vs_Computer) {
					int score = setNewContentOfPlayer1Card(categories[0]);
					player1Score += score;
					// Wenn die falsche Kategorie ausgewählt wurde, ist der Computer an der Reihe
					if (score < 0) {
						changePlayer1CardVisibility();
						changePlayer2CardVisibility();
						// Spielen Sie den Zug des Computers nach 1 Sekunde
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								int score = setNewContentOfPlayer2Card(randomCategory());
								player2Score += score;
								if (score < 0) {
									changePlayer1CardVisibility();
									changePlayer2CardVisibility();
								}
							}
						}, 1000);
					}

				}
				// Definieren der Funktionalität der Schaltfläche presidentNbtn1 im Modus
				// vs_friend
				else {
					playWithFriendLogic(categories[0]);
//					updatePlayer2TurnLabels();
				}
				updateScoreLabels();
				frame.repaint();
			}
		});

		presidentNbtn1.setHorizontalAlignment(SwingConstants.LEFT);
		presidentNbtn1.setFont(new Font("Tahoma", Font.BOLD, 16));
		presidentNbtn1.setBounds(30, 269, 230, 42);
		panel.add(presidentNbtn1);

		presidentNbtn2 = new JButton("President-N          " + president.presidentNumber);
		presidentNbtn2.addActionListener(new ActionListener() {
			// Definieren der Funktionalität der Schaltfläche presidentNbtn2
			public void actionPerformed(ActionEvent e) {
				if (gameMode == GameMode.Vs_Friend) {
					playWithFriendLogic(categories[0]);
					updatePlayer2TurnLabels();
					updatePlayer2TurnLabels();

				} else if (gameMode == GameMode.Vs_Computer) {
					int score = player2Score;
					player2Score += setNewContentOfPlayer2Card(randomCategory());
					if (score > player2Score) {
						changePlayer2CardVisibility();
						changePlayer1CardVisibility();
						updatePlayer2TurnLabels();
						updatePlayer2TurnLabels();

					}
				}
				updateScoreLabels();
				frame.repaint();
			}
		});

		presidentNbtn2.setHorizontalAlignment(SwingConstants.LEFT);
		presidentNbtn2.setFont(new Font("Tahoma", Font.BOLD, 16));
		presidentNbtn2.setBounds(280, 269, 230, 42);
		panel.add(presidentNbtn2);

		inagurationAgeBtn1 = new JButton("InAguration Age  " + president.inaugurationAge);
		inagurationAgeBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Definieren der Funktionalität der Schaltfläche inagurationAgeBtn1 im Modus
				// vs_computer
				if (gameMode == GameMode.Vs_Computer) {
					int score = setNewContentOfPlayer1Card(categories[1]);
					player1Score += score;
					// Wenn die falsche Kategorie ausgewählt wurde, ist der Computer an der Reihe
					if (score < 0) {
						changePlayer1CardVisibility();
						changePlayer2CardVisibility();
						// Spielen Sie den Zug des Computers nach 1 Sekunde
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								int score = setNewContentOfPlayer2Card(randomCategory());
								player2Score += score;
								if (score < 0) {
									changePlayer1CardVisibility();
									changePlayer2CardVisibility();
								}
							}
						}, 1000);
					}
				}
				// Definieren der Funktionalität der Schaltfläche inagurationAgeBtn1 im Modus
				// vs_friend
				else {
					playWithFriendLogic(categories[1]);
//					updatePlayer2TurnLabels();
				}
				updateScoreLabels();
				frame.repaint();
			}

		});
		inagurationAgeBtn1.setHorizontalAlignment(SwingConstants.LEFT);
		inagurationAgeBtn1.setFont(new Font("Tahoma", Font.BOLD, 16));
		inagurationAgeBtn1.setBounds(30, 317, 230, 42);
		panel.add(inagurationAgeBtn1);

		inagurationAgeBtn2 = new JButton("InAguration Age  " + president.inaugurationAge);
		inagurationAgeBtn2.addActionListener(new ActionListener() {
			// Definieren der Funktionalität der Schaltfläche inaugurationAgeBtn2
			public void actionPerformed(ActionEvent e) {
				if (gameMode == GameMode.Vs_Friend) {
					playWithFriendLogic(categories[1]);
					updatePlayer2TurnLabels();
					updatePlayer2TurnLabels();
				} else if (gameMode == GameMode.Vs_Computer) {
					int score = player2Score;
					player2Score += setNewContentOfPlayer2Card(randomCategory());
					if (score > player2Score) {
						changePlayer2CardVisibility();
						changePlayer1CardVisibility();
						updatePlayer2TurnLabels();
						updatePlayer2TurnLabels();

					}
				}
				updateScoreLabels();
				frame.repaint();
			}
		});
		inagurationAgeBtn2.setHorizontalAlignment(SwingConstants.LEFT);
		inagurationAgeBtn2.setFont(new Font("Tahoma", Font.BOLD, 16));
		inagurationAgeBtn2.setBounds(280, 317, 230, 42);
		panel.add(inagurationAgeBtn2);

		tenureInDaysBtn1 = new JButton("Tenure in days     " + president.tenureInDays);
		tenureInDaysBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Definieren der Funktionalität der Schaltfläche tenureInDaysBtn1 im Modus
				// vs_computer
				if (gameMode == GameMode.Vs_Computer) {
					int score = setNewContentOfPlayer1Card(categories[2]);
					player1Score += score;
					// Wenn die falsche Kategorie ausgewählt wurde, ist der Computer an der Reihe
					if (score < 0) {
						changePlayer1CardVisibility();
						changePlayer2CardVisibility();
						// Spielen Sie den Zug des Computers nach 1 Sekunde
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								int score = setNewContentOfPlayer2Card(randomCategory());
								player2Score += score;
								if (score < 0) {
									changePlayer1CardVisibility();
									changePlayer2CardVisibility();
								}
							}
						}, 1000);
					}
				}
				// Definieren der Funktionalität der Schaltfläche tenureInDaysBtn1 im Modus
				// vs_friend
				else {
					playWithFriendLogic(categories[2]);
//					updatePlayer2TurnLabels();

				}
				updateScoreLabels();
				frame.repaint();
			}
		});
		tenureInDaysBtn1.setHorizontalAlignment(SwingConstants.LEFT);
		tenureInDaysBtn1.setFont(new Font("Tahoma", Font.BOLD, 16));
		tenureInDaysBtn1.setBounds(30, 366, 230, 42);
		panel.add(tenureInDaysBtn1);

		tenureInDaysBtn2 = new JButton("Tenure in days     " + president.tenureInDays);
		tenureInDaysBtn2.addActionListener(new ActionListener() {
			// Definieren der Funktionalität der Schaltfläche tenureInDaysBtn2
			public void actionPerformed(ActionEvent e) {
				if (gameMode == GameMode.Vs_Friend) {
					playWithFriendLogic(categories[2]);
					updatePlayer2TurnLabels();
					updatePlayer2TurnLabels();

				} else if (gameMode == GameMode.Vs_Computer) {
					int score = player2Score;
					player2Score += setNewContentOfPlayer2Card(randomCategory());
					if (score > player2Score) {
						changePlayer2CardVisibility();
						changePlayer1CardVisibility();
						updatePlayer2TurnLabels();
						updatePlayer2TurnLabels();

					}
				}
				updateScoreLabels();
				frame.repaint();
			}
		});
		tenureInDaysBtn2.setHorizontalAlignment(SwingConstants.LEFT);
		tenureInDaysBtn2.setFont(new Font("Tahoma", Font.BOLD, 16));
		tenureInDaysBtn2.setBounds(280, 366, 230, 42);
		panel.add(tenureInDaysBtn2);

		noOfVetoesBtn1 = new JButton("Nr of Vetoes         " + president.noOfVetoes);
		noOfVetoesBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Definieren der Funktionalität der Schaltfläche noOfVetoesBtn1 im Modus
				// vs_computer
				if (gameMode == GameMode.Vs_Computer) {
					int score = setNewContentOfPlayer1Card(categories[3]);
					player1Score += score;
					// Wenn die falsche Kategorie ausgewählt wurde, ist der Computer an der Reihe
					if (score < 0) {
						changePlayer1CardVisibility();
						changePlayer2CardVisibility();
						// Spielen Sie den Zug des Computers nach 1 Sekunde
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								int score = setNewContentOfPlayer2Card(randomCategory());
								player2Score += score;
								if (score < 0) {
									changePlayer1CardVisibility();
									changePlayer2CardVisibility();
								}
							}
						}, 1000);
					}
				}
				// Definieren der Funktionalität der Schaltfläche noOfVetoesBtn1 im Modus
				// vs_friend
				else {
					playWithFriendLogic(categories[3]);
//					updatePlayer2TurnLabels();

				}
				updateScoreLabels();
				frame.repaint();
			}
		});
		noOfVetoesBtn1.setHorizontalAlignment(SwingConstants.LEFT);
		noOfVetoesBtn1.setFont(new Font("Tahoma", Font.BOLD, 16));
		noOfVetoesBtn1.setBounds(30, 415, 230, 42);
		panel.add(noOfVetoesBtn1);

		noOfVetoesBtn2 = new JButton("Nr of Vetoes         " + president.noOfVetoes);
		noOfVetoesBtn2.addActionListener(new ActionListener() {

			// Definieren der Funktionalität der Schaltfläche noOfVetoesBtn2
			public void actionPerformed(ActionEvent e) {
				if (gameMode == GameMode.Vs_Friend) {
					playWithFriendLogic(categories[3]);
					updatePlayer2TurnLabels();
					updatePlayer2TurnLabels();

				} else if (gameMode == GameMode.Vs_Computer) {
					int score = player2Score;
					player2Score += setNewContentOfPlayer2Card(randomCategory());
					if (score > player2Score) {
						changePlayer2CardVisibility();
						changePlayer1CardVisibility();
						updatePlayer2TurnLabels();
						updatePlayer2TurnLabels();

					}
				}
				updateScoreLabels();
				frame.repaint();
			}
		});
		noOfVetoesBtn2.setHorizontalAlignment(SwingConstants.LEFT);
		noOfVetoesBtn2.setFont(new Font("Tahoma", Font.BOLD, 16));
		noOfVetoesBtn2.setBounds(280, 415, 230, 42);
		panel.add(noOfVetoesBtn2);

		noOfChildrenBtn1 = new JButton("Nr of Children       " + president.noOfChildren);
		noOfChildrenBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Definieren der Funktionalität der Schaltfläche noOfChildrenBtn1 im Modus
				// vs_computer
				if (gameMode == GameMode.Vs_Computer) {
					int score = setNewContentOfPlayer1Card(categories[4]);
					player1Score += score;
					// Wenn die falsche Kategorie ausgewählt wurde, ist der Computer an der Reihe
					if (score < 0) {
						changePlayer1CardVisibility();
						changePlayer2CardVisibility();
						// Spielen Sie den Zug des Computers nach 1 Sekunde
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								int score = setNewContentOfPlayer2Card(randomCategory());
								player2Score += score;

								if (score < 0) {
									changePlayer1CardVisibility();
									changePlayer2CardVisibility();
								}
							}
						}, 1000);
					}
				}
				// Definieren der Funktionalität der Schaltfläche noOfChildrenBtn1 im Modus
				// vs_friend
				else {
					playWithFriendLogic(categories[4]);
//					updatePlayer2TurnLabels();

				}
				updateScoreLabels();
				frame.repaint();
			}
		});
		noOfChildrenBtn1.setHorizontalAlignment(SwingConstants.LEFT);
		noOfChildrenBtn1.setFont(new Font("Tahoma", Font.BOLD, 16));
		noOfChildrenBtn1.setBounds(30, 465, 230, 42);
		panel.add(noOfChildrenBtn1);

		noOfChildrenBtn2 = new JButton("Nr of Children       " + president.noOfChildren);
		noOfChildrenBtn2.addActionListener(new ActionListener() {
			// Definieren der Funktionalität der Schaltfläche noOfChildrenBtn2
			public void actionPerformed(ActionEvent e) {
				if (gameMode == GameMode.Vs_Friend) {
					playWithFriendLogic(categories[4]);
					updatePlayer2TurnLabels();
					updatePlayer2TurnLabels();
				} else if (gameMode == GameMode.Vs_Computer) {
					int score = player2Score;
					player2Score += setNewContentOfPlayer2Card(randomCategory());
					if (score > player2Score) {
						changePlayer2CardVisibility();
						changePlayer1CardVisibility();
						updatePlayer2TurnLabels();
						updatePlayer2TurnLabels();

					}
				}
				updateScoreLabels();
				frame.repaint();
			}
		});
		noOfChildrenBtn2.setHorizontalAlignment(SwingConstants.LEFT);
		noOfChildrenBtn2.setFont(new Font("Tahoma", Font.BOLD, 16));
		noOfChildrenBtn2.setBounds(280, 465, 230, 42);
		panel.add(noOfChildrenBtn2);

		lengthOfSpeechBtn1 = new JButton("Length of Speech " + president.lengthOfSpeech);
		lengthOfSpeechBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Definieren der Funktionalität der Schaltfläche lengthOfSpeechBtn1 im Modus
				// vs_computer
				if (gameMode == GameMode.Vs_Computer) {
					int score = setNewContentOfPlayer1Card(categories[5]);
					player1Score += score;
					// Wenn die falsche Kategorie ausgewählt wurde, ist der Computer an der Reihe
					if (score < 0) {
						changePlayer1CardVisibility();
						changePlayer2CardVisibility();
						// Spielen Sie den Zug des Computers nach 1 Sekunde
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								int score = setNewContentOfPlayer2Card(randomCategory());
								player2Score += score;
								if (score < 0) {
									changePlayer1CardVisibility();
									changePlayer2CardVisibility();
								}
							}
						}, 1000);
					}
				}
				// Definieren der Funktionalität der Schaltfläche lengthOfSpeechBtn1 im Modus
				// vs_friend
				else {
					playWithFriendLogic(categories[5]);
//					updatePlayer2TurnLabels();
				}
				updateScoreLabels();
				frame.repaint();
			}
		});
		lengthOfSpeechBtn1.setHorizontalAlignment(SwingConstants.LEFT);
		lengthOfSpeechBtn1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lengthOfSpeechBtn1.setBounds(30, 515, 230, 42);
		panel.add(lengthOfSpeechBtn1);

		lengthOfSpeechBtn2 = new JButton("Length of Speech " + president.lengthOfSpeech);
		// Definieren der Funktionalität der Schaltfläche lengthOfSpeechBtn2
		lengthOfSpeechBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gameMode == GameMode.Vs_Friend) {
					playWithFriendLogic(categories[5]);
					updatePlayer2TurnLabels();
					updatePlayer2TurnLabels();
				} else if (gameMode == GameMode.Vs_Computer) {
					int score = player2Score;
					player2Score += setNewContentOfPlayer2Card(randomCategory());
					if (score > player2Score) {
						changePlayer2CardVisibility();
						changePlayer1CardVisibility();
						updatePlayer2TurnLabels();
						updatePlayer2TurnLabels();

					}
				}
				updateScoreLabels();
				frame.repaint();
			}
		});
		lengthOfSpeechBtn2.setHorizontalAlignment(SwingConstants.LEFT);
		lengthOfSpeechBtn2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lengthOfSpeechBtn2.setBounds(280, 515, 230, 42);
		panel.add(lengthOfSpeechBtn2);

		changePlayer2CardVisibility();
		player2TurnLabel.setVisible(false);
		cardsNoPlayer2Label.setVisible(false);

		frame.repaint(); // Rahmen auffrischen
		return panel;

	}

	// Methode, um Karte 1 sichtbar zu machen, wenn der Zug von Spieler 1 sie
	// ansonsten unsichtbar macht
	private void changePlayer1CardVisibility() {
		presidentNbtn1.setEnabled(!presidentNbtn1.isEnabled());
		inagurationAgeBtn1.setEnabled(!inagurationAgeBtn1.isEnabled());
		tenureInDaysBtn1.setEnabled(!tenureInDaysBtn1.isEnabled());
		noOfVetoesBtn1.setEnabled(!noOfVetoesBtn1.isEnabled());
		noOfChildrenBtn1.setEnabled(!noOfChildrenBtn1.isEnabled());
		lengthOfSpeechBtn1.setEnabled(!lengthOfSpeechBtn1.isEnabled());
		cardsNoPlayer1Label.setVisible(true);
		if (gameMode == GameMode.Vs_Computer)
			player2TurnLabel.setText("Computer's turn");
		else {
			player2TurnLabel.setText(player2 + "'s turn");
		}
//		isPlayer1sTurn = !isPlayer1sTurn;
		player2TurnLabel.setVisible(!isPlayer1sTurn);
		cardsNoPlayer2Label.setVisible(!isPlayer1sTurn);
	}

	// Methode, um Karte 2 sichtbar zu machen, wenn der Zug ihres Computers/Spielers
	// 2 sie sonst unsichtbar macht
	private void changePlayer2CardVisibility() {
		presidentNameLbl2.setVisible(!presidentNameLbl2.isVisible());
		presidentImgLbl2.setVisible(!presidentImgLbl2.isVisible());
		presidentNbtn2.setVisible(!presidentNbtn2.isVisible());
		inagurationAgeBtn2.setVisible(!inagurationAgeBtn2.isVisible());
		tenureInDaysBtn2.setVisible(!tenureInDaysBtn2.isVisible());
		noOfVetoesBtn2.setVisible(!noOfVetoesBtn2.isVisible());
		noOfChildrenBtn2.setVisible(!noOfChildrenBtn2.isVisible());
		lengthOfSpeechBtn2.setVisible(!lengthOfSpeechBtn2.isVisible());

		frame.repaint();
	}

	// Methode, die die Logik für das Spielen mit dem Freund-Spielmodus definiert
	private void playWithFriendLogic(String selectedCategory) {
		int score = 0;
		if (isPlayer1sTurn) {
			score = setNewContentOfPlayer1Card(selectedCategory);
			player1Score += score;
		} else {
			score = setNewContentOfPlayer2Card(selectedCategory);
			player2Score += score;
		}

		if (score < 0) {
			isPlayer1sTurn = !isPlayer1sTurn;
			changePlayer2CardVisibility();
			changePlayer1CardVisibility();
		}

		frame.repaint();
	}

	// Methode zum Festlegen des Inhalts von Spieler1/Computerkarte und gibt die
	// Punktzahl zurück
	public int setNewContentOfPlayer1Card(String categorySelected) {
		counter++;

		int score = 0;
		// Machen Sie eine Punktzahl von +1, wenn die gewählte Kategorie des Spielers
		// die beste Chance ist, andernfalls machen Sie sie -1
		if (currentPresident.isBestChance(presidentInfoList, categorySelected))
			score = 1;
		else
			score = -1;

		// Abrufen eines zufälligen Präsidenten
		President president = presidentInfoList.get(new Random().nextInt(presidentInfoList.size()));
		String presidentImagePath = getPresidentImagePath(president.name);
		currentPresident = president;

		// Aktualisieren des Karteninhalts 1

		presidentImgLbl1.setIcon(getIcon(presidentImagePath));
		presidentNameLbl1.setText(president.name);
		presidentNbtn1.setText("President-N          " + president.presidentNumber);
		inagurationAgeBtn1.setText("InAguration Age  " + president.inaugurationAge);
		tenureInDaysBtn1.setText("Tenure in days     " + president.tenureInDays);
		noOfVetoesBtn1.setText("Nr of Vetoes         " + president.noOfVetoes);
		noOfChildrenBtn1.setText("Nr of Children     " + president.noOfChildren);
		lengthOfSpeechBtn1.setText("Length of Speech " + president.lengthOfSpeech);
		mainPanel.repaint();
		frame.repaint();

		if (counter == 32) {
			frame.dispose();
			JOptionPane.showMessageDialog(null, getWinner(), "Winner", JOptionPane.INFORMATION_MESSAGE);
		}
		return score;
	}

	// Methode zum Festlegen des Inhalts von Spieler2/Computerkarte und gibt die
	// Punktzahl zurück
	public int setNewContentOfPlayer2Card(String categorySelected) {
		counter++;

		int score = 0;
		// Machen Sie eine Punktzahl von +1, wenn die gewählte Kategorie des Spielers
		// die beste Chance ist, andernfalls machen Sie sie -1
		if (currentPresident.isBestChance(presidentInfoList, categorySelected))
			score = 1;
		else
			score = -1;

		// Abrufen eines zufälligen Präsidenten
		President president = presidentInfoList.get(new Random().nextInt(presidentInfoList.size()));
		String presidentImagePath = getPresidentImagePath(president.name);
		currentPresident = president;

		// Aktualisieren des Karteninhalts 2
		presidentImgLbl2.setIcon(getIcon(presidentImagePath));
		presidentNameLbl2.setText(president.name);
		presidentNbtn2.setText("President-N          " + president.presidentNumber);
		inagurationAgeBtn2.setText("InAguration Age  " + president.inaugurationAge);
		tenureInDaysBtn2.setText("Tenure in days     " + president.tenureInDays);
		noOfVetoesBtn2.setText("Nr of Vetoes         " + president.noOfVetoes);
		noOfChildrenBtn2.setText("Nr of Children     " + president.noOfChildren);
		lengthOfSpeechBtn2.setText("Length of Speech " + president.lengthOfSpeech);
		mainPanel.repaint();
		frame.repaint();

		if (player1Score == 0 || player2Score == 0) {
			frame.dispose();
			JOptionPane.showMessageDialog(null, getWinner(), "Winner", JOptionPane.INFORMATION_MESSAGE);
		}
		return score;
	}

	// Methode zum Aktualisieren von Player2/Computer-Turn-Labels
	private void updatePlayer2TurnLabels() {
		player2TurnLabel.setVisible(!player2TurnLabel.isVisible());
		cardsNoPlayer2Label.setVisible(!cardsNoPlayer2Label.isVisible());
		frame.repaint();
	}

	// Methode, um eine zufällige Kategorie für Computerspieler auszuwählen
	private String randomCategory() {

		return categories[new Random().nextInt(categories.length)];
	}

	// Methode zum Aktualisieren von Partituren auf ihren jeweiligen Labels
	private void updateScoreLabels() {
		cardsNoPlayer1Label.setText("You have " + player1Score + " cards.");
		cardsNoPlayer2Label.setText("You have " + player2Score + " cards.");
	}

	// Methode, die die Winner-Anweisung zurückgibt
	private String getWinner() {
		String winnerStatement = player1 + ": " + player1Score + "\n";
		if (gameMode == GameMode.Vs_Computer)
			winnerStatement += "Computer: ";
		else
			winnerStatement += player2 + ": ";
		winnerStatement += player2Score + "\n\n\t";
		if (player1Score > player2Score && player1Score >= 16) { // Spieler 1 gewinnt zurück, wenn er gewinnt
			winnerStatement += player1 + " wins.";
		} else if (player2Score > player1Score && player2Score >= 16) {
			if (gameMode == GameMode.Vs_Friend)
				winnerStatement += player2 + " wins.";
			else
				winnerStatement += "You lose!";
		} else {
			// Wenn keine Bedingung erfüllt ist und beide Spieler weniger als 16 Karten
			// haben, ist das Spiel vorbei
			winnerStatement += "Game Over!";
		}
		return winnerStatement;
	}

	// Methode, um den Bildpfad des Präsidenten abzurufen, der in der Array-Liste
	// imageFileNames für den angegebenen Präsidentennamen gespeichert ist
	private String getPresidentImagePath(String presidentName) {
		for (int i = 0; i < imageFileNames.size(); i++) {
			String path = imageFileNames.get(i);
			if (path.contains(presidentName)) {
				return path;
			}
		}
		throw new IllegalArgumentException("Invalid President Name!");
	}

	// Methode, die nach dem Abrufen von Daten aus der Textdatei die Informationen
	// des Präsidenten in der Array-Liste PresidentInfoList festlegt
	void getPresidentsInfo() {
		try {
			Scanner in = new Scanner(new File("presidents-info - Copy.txt"));
			// Sammeln aller Dateinamen und Speichern in einer Array-Liste
			while (in.hasNextLine()) {
				String name = in.nextLine();
				String presidentNumber = in.nextLine();
				String inaugurationAge = in.nextLine();
				String tenureInDays = in.nextLine();
				String noOfVetoes = in.nextLine();
				String noOfChildren = in.nextLine();
				String lengthOfSpeech = in.nextLine();
				// Erstellen eines President-Objekts und Hinzufügen zur Liste
				President president = new President(name, presidentNumber, inaugurationAge, tenureInDays, noOfVetoes,
						noOfChildren, lengthOfSpeech);
				presidentInfoList.add(president);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}

	// Methode, die Bildpfade in der Arrayliste imageFileNames festlegt
	void getPresidentsImagePaths() {
		File[] files = new File("./presidents").listFiles();
		for (int i = 0; i < files.length; i++) {
			imageFileNames.add("./presidents/" + files[i].getName());
		}
		// Sortieren der Dateinamen
		imageFileNames.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}

		});
	}

	// Methode zum Ändern der Bildgröße, um das Symbol zu erhalten
	public ImageIcon getIcon(String imagePath) {
		ImageIcon imageIcon = new ImageIcon(imagePath); // Laden Sie das Bild in ein imageIcon
		Image image = imageIcon.getImage(); // transformiere es
		Image newimg = image.getScaledInstance(presidentImgLbl1.getWidth(), presidentImgLbl1.getHeight(),
				java.awt.Image.SCALE_SMOOTH); // skalieren Sie es auf die reibungslose Weise
		imageIcon = new ImageIcon(newimg);
		return imageIcon;

	}

}
