package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import nz.ac.vuw.ecs.swen225.a3.commons.GameConstants;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsAction;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsEvent;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsModel;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsModelFactory;
import nz.ac.vuw.ecs.swen225.a3.render.ChapsView;
import nz.ac.vuw.ecs.swen225.a3.render.ChapsViewFactory;

/**
 * @author Max 300465155
 */
public class ChapsControllerImpl extends JFrame implements ChapsController {

	private static final long serialVersionUID = -6798111243001006598L;

	private final int windowHeight = 800;
	private final int windowLength = 800;

	//game components - model and view
	private final ChapsModel model;
	private final ChapsView view;

	//menuBar
	private JMenuBar menuBar;
	private JMenu gameOptions, help;

	//Menu panel
	private JPanel mainMenu;
	private JButton resumeButton, saveGame, startNewGame, loadGame, gameControls, gameHelp, exitGame;
	private boolean currentGameBeingPlayed=false;
	private GridBagLayout grid = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

	//Game board panel
	private JSplitPane gamePanel;

	//Game state variables
	private boolean gamePaused=true;
	private EnumSet<ChapsEvent> chapsEvents;

	//Game timer- controls  events
	private Timer timer;


	/**
	 * Constructor for ChapsControllerImpl.
	 * Set ups
	 *
	 * @param factorymodel
	 * @param factoryview
	 */
	ChapsControllerImpl(ChapsModelFactory factorymodel, ChapsViewFactory factoryview)
	{
		model = factorymodel.produce();
		view = factoryview.produce();

		this.addKeyListener(this);
		this.addWindowListener(this);
		start();
	}

	/**
	 * Starts the game, initializing setup
	 */
	@Override
	public void start() {
		setupMainMenu();
		setUpWindow();
		//Starts timer
		setupTimer();
		pauseGame();
	}


	/**
	 * Sets up all window settings on initalisation. Adds window closing
	 * confirmation. Sets window size.
	 */
	private void setUpWindow() {
		gamePanel = view.getRootPanel();
		gamePanel.setVisible(true);
		this.setTitle("Chaps's Challenge");
		this.setSize(windowHeight, windowLength);
		// Adds a window confirmation for closing game
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				exitPrompt();
			}
		});
		setupMenuBars();
		this.setMinimumSize(new Dimension(windowHeight, windowLength));
		this.setVisible(true);
	}

	/**
	 * sets up main menu for the game.
	 */
	private void setupMainMenu() {
		mainMenu = new JPanel();
		Dimension buttonDimension = new Dimension(300, 70);

		startNewGame = new JButton("New Game");
		startNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				restartGame();
			}
		});
		startNewGame.setPreferredSize(buttonDimension);

		exitGame = new JButton("Exit Game");
		exitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitPrompt();
			}
		});

		exitGame.setPreferredSize(buttonDimension);

		loadGame = new JButton("Load Game");
		loadGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadGame();
			}
		});
		loadGame.setPreferredSize(buttonDimension);

		resumeButton = new JButton("Resume Game");
		resumeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resumeGame();
			}
		});
		resumeButton.setPreferredSize(buttonDimension);

		saveGame = new JButton("Save Game");
		startNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveGame();
			}
		});

		saveGame.setPreferredSize(buttonDimension);

		gameControls = new JButton("Game Controls");
		gameControls.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controlsHelp();
			}
		});
		gameControls.setPreferredSize(buttonDimension);

		gameHelp = new JButton("Game Help");
		gameHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				instructionsHelp();
			}
		});
		gameHelp.setPreferredSize(buttonDimension);

		// Adds main menu buttons

		addButtons();


		mainMenu.setVisible(true);
	}

	/**
	 * Adds the buttons to main menu
	 */
	private void addButtons() {

		mainMenu.removeAll();
		gbc.insets = new Insets(20, 0, 0, 0); // top padding
		mainMenu.setLayout(grid);
		resumeButton.setVisible(true);
		if (currentGameBeingPlayed) {
			System.out.println("made it");
			gbc.gridx = 0;
			gbc.gridy = 0;
			mainMenu.add(resumeButton, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			mainMenu.add(saveGame, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			mainMenu.add(startNewGame, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			mainMenu.add(loadGame, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			mainMenu.add(gameControls, gbc);

			gbc.gridx = 0;
			gbc.gridy = 5;
			mainMenu.add(gameHelp, gbc);

			gbc.gridx = 0;
			gbc.gridy = 6;
			mainMenu.add(exitGame, gbc);
		} else {

			gbc.gridx = 0;
			gbc.gridy = 0;
			mainMenu.add(startNewGame, gbc);

			// startNewGame, loadGame, gameControls, gameHelp, exitGame;
			gbc.gridx = 0;
			gbc.gridy = 1;
			mainMenu.add(loadGame, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			mainMenu.add(gameControls, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			mainMenu.add(gameHelp, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			mainMenu.add(exitGame, gbc);
		}
		mainMenu.revalidate();

	}

	/**
	 * Sets up timer that schedules tick events
	 */
	private void setupTimer() {
		timer=new Timer("Game Timer");
		TimerTask tickTask= new TimerTask() { //Task that get called repeatedly after fixed duration - for game tick
			@Override
			public void run() {
					updateChapMove(ChapsAction.TICK);
			};
		};
		long tickCallTime=1000/GameConstants.TICKS_TO_SECONDS_RATIO;//In milliseconds
	//	timer.schedule(TimerTask task, Date firstTime, long period)
		timer.schedule(tickTask, new Date(), tickCallTime);
	}

	/**
	 * Sets up the menubars for the window and add their respective actionlistners.
	 */
	private void setupMenuBars() {
		menuBar = new JMenuBar();
		// Build the game options menubar
		gameOptions = new JMenu("Game Options");
		// save
		JMenuItem save = new JMenuItem("Save game");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveGame();
			}
		});
		// resume
		JMenuItem resume = new JMenuItem("Resume");
		resume.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				resumeGame();
			}
		});
		// pause
		JMenuItem pause = new JMenuItem("Pause");
		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pauseGame();
			}
		});
		// exit
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitGame();
			}
		});


		// Building help menu
		help = new JMenu("Help");
		// controls
		JMenuItem controls = new JMenuItem("Controls");
		controls.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				controlsHelp();
			}
		});


		// Instructions
		JMenuItem instructions = new JMenuItem("Instructions");
		instructions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				instructionsHelp();
			}
		});

		gameOptions.add(save);
		gameOptions.add(resume);
		gameOptions.add(pause);
		gameOptions.add(exit);


		help.add(controls);
		help.add(instructions);

		menuBar.add(gameOptions);
		menuBar.add(help);
		this.setJMenuBar(menuBar);
	}

	/**
	 * CTRL-X  - exit the game, the current game state will be lost, the next time the game is started, it will resume from the last unfinished level
	 * CTRL-S  - exit the game, saves the game state, game will resume next time the application will be started
	 * CTRL-R  - resume a saved game
	 * CTRL-P  - start a new game at the last unfinished level
	 * CTRL-1 - start a new game at level 1
	 * SPACE - pause the game and display a �game is paused� dialog
	 * ESC - close the �game is paused� dialog and resume the game
     * UP, DOWN, LEFT, RIGHT ARROWS -- move Chap within the maze
	 * @param e - KeyEvent
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
			exitGame();
		}

		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
			exitGameWithSave();
		}
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R) {
			resumeGame();
		}
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_P) {
			restartLevel();
		} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_1) {
			restartGame();
		} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			pauseGame();
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {

		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			updateChapMove(ChapsAction.UP);
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			updateChapMove(ChapsAction.DOWN);
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			updateChapMove(ChapsAction.LEFT);
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			updateChapMove(ChapsAction.RIGHT);
		}
	}




	/**
	 * Updates the game whenever a tick goes through or when a move has been made
	 *
	 */
	private void updateChapMove(ChapsAction action) {
		if (!gamePaused) {
			chapsEvents = model.onAction(action);
			updateGraphics();//might not be correct place for this?
		}
	}

	/**
	 * Updates the application graphics. Updates timer, inventory and view of the
	 * board. Does this by getting information from the maze package and sending all
	 * the information to the Renderer package.
	 */
	private void updateGraphics() {
		model.getInventoryIcons();// needs updating
		view.updateBoard(model.getVisibleArea());
		// view.getRootPanel();
		//view.updateInventory(model.getInventoryIcons()); //not yet implemented
		view.updateRemainingChips(model.getChipsRemaining());
		view.updateRemainingTime(model.getTimeRemaining());
	}

	/**
	 * Exit the game, the current game state will be lost, the next time the game is
	 * started, it will resume from the last unfinished level.
	 */
	private void exitGame() {
		System.exit(0);
	}

	/**
	 * exit the game, saves the game state, game will resume next time the
	 * application will be started.
	 */
	private void exitGameWithSave() {
		saveGame();
		exitGame();
	}

	/**
	 * Saves the game.
	 */
	private void saveGame() {


	}

	/**
	 * Resume a saved game. Enables save game and resume options if clicked when
	 * game is in motion.
	 */
	private void resumeGame() {
		currentGameBeingPlayed=true;
		this.getContentPane().removeAll();
		this.setJMenuBar(menuBar);
		this.getContentPane().add(gamePanel);
		gamePaused=false;
		this.getRootPane().repaint();
	}

	/**
	 * start a new game at level 1.
	 */
	private void restartGame() {
		//dosomething


		resumeGame();
	}

	/**
	 * start a new game at the last unfinished level.
	 */
	private void restartLevel() {
		//dosomething

		resumeGame();
	}

	/**
	 * Loads a game from json file.
	 */
	private void loadGame() {
		//dosomething

		resumeGame();
	}

	/**
	 *  pause the game and display a game is paused dialog.
	 */
	private void pauseGame() {
		//Shows certain buttons depending on if a game is being played or not.
		addButtons();


		gamePaused=true;
		this.getContentPane().removeAll();
		this.setJMenuBar(null);
		this.getContentPane().add(mainMenu);
		this.getRootPane().repaint();


	}


	/**
	 *Help message that displays the information on how to play the game.
	 *
	 */
	private void instructionsHelp() {
		JOptionPane.showMessageDialog(this,
			    "To pass a level all the computer chip's must be collected.\n" +
			    "\n" +
			    "There are keys spread across the levels in which must be collected to unlock\n" +
			    "rooms that contains chips. \n" +
			    "\n" +
			    "The goal is to find all the chips before the timer \n" +
			    "runs out and walk to the finished level tile.", "Chips Challenge - Game Information",
			    JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Information the explains the in game controls.
	 *
	 */
	private void controlsHelp() {
		JOptionPane.showMessageDialog(this, "CTRL-X  - Exit's the game without saving\n" + // resumes at start of last
																							// unfinished level
				"CTRL-S  - Exit and saves the game\n" + "CTRL-R  - Resume's a saved game\n"
				+ "CTRL-P  - Start's a new game at the last unfinished level\n"
				+ "CTRL-1 - Start's a new game at level 1\n" + "SPACE - Pause's the game\n"
				+ "ESC - Resume's the game\n" + "UP, DOWN, LEFT, RIGHT ARROWS -- Move's Chap within the maze\n" + "",
				"Chip's Challenge Game Controls", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Called for confirmation of exiting th game.
	 */
	private void exitPrompt() {

		String ObjButtons[] = { "Yes", "No" };
		int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Chip's Challenge",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
		if (PromptResult == JOptionPane.YES_OPTION) {
			exitGame();
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

}