package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
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
import nz.ac.vuw.ecs.swen225.a3.persistence.LevelInterface;
import nz.ac.vuw.ecs.swen225.a3.persistence.SaveFileInterface;
import nz.ac.vuw.ecs.swen225.a3.plugin.Level;
import nz.ac.vuw.ecs.swen225.a3.render.ChapsView;
import nz.ac.vuw.ecs.swen225.a3.render.ChapsViewFactory;

/**
 * @author Max 300465155
 */
public class ChapsControllerImpl extends JFrame implements ChapsController {

	private static final long serialVersionUID = -6798111243001006598L;

	//game components - model and view
	private final ChapsModel model;
	private final ChapsView view;

	//menuBar
	private JMenuBar menuBar;
	private JMenu gameOptions, help, recordedPlayback;

	//Menu panel
	private JPanel mainMenuInGame, mainMenuNotInGame;
	private JButton resumeButton, saveGame, startNewGame, loadGame, gameControls, gameHelp, exitGame, playRecordedGame;
	private boolean currentGameBeingPlayed;
	private GridBagLayout grid = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

	//Game board panel
	private JSplitPane gamePanel;

	//Game state variables
	private boolean gamePaused=true;

	//Game timer- controls  events
	private Timer timer;

	//Record and playback
	private boolean inPlayBackMode;
	
	/**
	 * The current level
	 */
	private int currentlevel = 0;
	

	/**
	 * Constructor for ChapsControllerImpl.
	 *
	 * @param factorymodel
	 * @param factoryview
	 */
	ChapsControllerImpl(ChapsModelFactory factorymodel, ChapsViewFactory factoryview)
	{
		model = factorymodel.produce();
		view = factoryview.produce();

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		this.addWindowListener(this);
		
		start();
	}

	/**
	 * Starts the game, initializing setup
	 */
	@Override
	public void start() 
	{
		gamePaused = true;
		currentGameBeingPlayed =  false;
		inPlayBackMode = false;

		setupMainMenu();
		setupWindow();
		setupTimer();
		
		this.getContentPane().add(mainMenuNotInGame);
	}
	
	////////Gameplay functions\\\\\\\\\\
	
	/**
	 * Updates the game whenever a tick goes through or when a move has been made
	 *
	 */
	private void updateChapMove(ChapsAction action) 
	{
		if(!gamePaused) 
			eventHandler(model.onAction(action));
	}
	
	private void eventHandler(EnumSet<ChapsEvent> chapsEvents)
	{
		for(ChapsEvent event : chapsEvents)
		{
			switch(event)
			{
				case CHIPS_UPDATE_REQUIRED:
					view.updateRemainingChips(model.getChipsRemaining());
					break;
				case DISPLAY_UPDATE_REQUIRED:
					view.updateBoard(model.getVisibleArea());
					break;
				case GAME_LOST_PLAYER_DIED:
					JOptionPane.showMessageDialog(this, "You died!");
					this.restartLevel();
					break;
				case GAME_LOST_TIME_OUT:
					JOptionPane.showMessageDialog(this, "Time's up!");
					this.restartLevel();
					break;
				case HIDE_TUTORIAL_MESSAGE:
					view.clearDisplayTutorialMessage();
					break;
				case INV_UPDATE_REQUIRED:
					view.updateInventory(model.getInventoryIcons());
					break;
				case PLAYER_WINS:
					//TODO: Win message
					break;
				case SHOW_TUTORIAL_MESSAGE:
					view.setDisplayTutorialMessage(model.getTutorialMessage());
					break;
				case TIME_UPDATE_REQUIRED:
					view.updateRemainingTime(model.getTimeRemaining());
					break;
				default:
					break;
			}
		}
	}

	//Game state functions

	/**
	 * Exit the game, the current game state will be lost, the next time the game is
	 * started, it will resume from the last unfinished level.
	 */
	private void exitGame() {
		//exitPrompt();

		//dosomething
	}

	/**
	 * exit the game, saves the game state, game will resume next time the
	 * application will be started.
	 */
	private void exitGameWithSave() 
	{
		saveGame();
		exitGame();
	}
	
	/**
	 * Resume a saved game. Enables save game and resume options if clicked when
	 * game is in motion.
	 */
	private void resumeGame() 
	{
		this.setTitle("Chaps's Challenge");
		
		this.getContentPane().removeAll();
		
		this.setJMenuBar(menuBar);
		
		this.getContentPane().add(gamePanel);
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
		
		gamePaused = false;
		currentGameBeingPlayed = true;
		
		this.eventHandler(EnumSet.of(ChapsEvent.DISPLAY_UPDATE_REQUIRED, ChapsEvent.INV_UPDATE_REQUIRED, ChapsEvent.CHIPS_UPDATE_REQUIRED));
	}

	/**
	 *  pause the game and display a game is paused dialog.
	 */
	private void pauseGame() {
		this.setTitle("Chaps's Challenge - Game Paused");

		gamePaused=true;
		this.getContentPane().removeAll();
		this.setJMenuBar(null);
		addButtons(); //Get's called because buttons will show up differently if there currently is  game being played or not

		//Shows certain buttons depending on if a game is being played or not.
		if(currentGameBeingPlayed)
			this.getContentPane().add(mainMenuInGame);
		else
			this.getContentPane().add(mainMenuNotInGame);
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
	}
	
	private void loadLevel()
	{
		Level theLevel = LevelInterface.getInstance().getLevel(currentlevel);
		model.setState(theLevel.load());
		view.updateCurrentLevel(this.currentlevel);
	}

	/**
	 * Start a new game at level 1.
	 */
	private void restartGame() {
		currentlevel = 0;
		
		loadLevel();
		resumeGame();
	}

	/**
	 * start a new game at the last unfinished level.
	 */
	private void restartLevel() 
	{
		loadLevel();
		resumeGame();
	}
	
	/**
	 * Saves the game state.
	 */
	private void saveGame() 
	{
		try {
			SaveFileInterface.save(model.getState());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "There was an error in saving: " + e.getClass().getSimpleName(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	/**
	 * Loads a game from json file.
	 */
	private void loadGame() {
		GameState state = SaveFileInterface.load();
		model.setState(state);
		resumeGame();
	}

	/**
	 * Starts the recorded game
	 * Adds recorded game to menu bar.
	 */
	private void startRecordedGame() {
		inPlayBackMode=true;
		menuBar.add(recordedPlayback);
		resumeGame();
		//dosomething
	}

	/**
	 * Stops the recorded game.
	 * Removes recored game from menu bar.
	 */
	private void stopRecordedGame() {
		inPlayBackMode=false;
		menuBar.remove(recordedPlayback);
		//dosomething
	}

	/**
	 * Initates the start of a recorded playback of a game to tick through.
	 */
	private void playRecordedGame() {
		//dosomething
	}

	/**
	 * Pauses the recorded playback of a game to tick through.
	 */
	private void pauseRecordedGame() {
		//dosomething
	}

	/**
	 * Steps one step forwards in the recorded game
	 */
	private void stepForwardRecordedGame() {
		//dosomething
	}

	/**
	 * Steps one step backwards in the recorded game
	 */
	private void stepBackwardRecordedGame() {
		//dosomething
	}

	//Timer

	/**
	 * Sets up timer that schedules tick events
	 */
	private void setupTimer() {
		
		timer = new Timer("Game Timer");
		
		TimerTask tickTask = new TimerTask() { //Task that get called repeatedly after fixed duration - for game tick
			
			@Override
			public void run() {
				updateChapMove(ChapsAction.TICK);
			};
			
		};

		long tickCallTime = 1000 / GameConstants.TICKS_TO_SECONDS_RATIO; //In milliseconds
		
		timer.schedule(tickTask, new Date(), tickCallTime);
	}
	
	////////Window, main menu, button setup and functions\\\\\\\\\\

	/**
	 * Sets up all window settings on initalisation. Adds window closing
	 * confirmation. Sets window size.
	 */
	private void setupWindow() {
		gamePanel = view.getRootPanel();
		
		this.setTitle("Chaps's Challenge");
		this.setSize((int) ((8 + GameConstants.ICON_SIZE) * GameConstants.VISIBILE_SIZE * 1.4), (8 + GameConstants.ICON_SIZE) * GameConstants.VISIBILE_SIZE);
		
		setupMainMenu();
		setupMenuBars();
		
		//this.setMinimumSize(new Dimension(windowHeight, windowLength));
		this.setVisible(true);
	}

	/**
	 * sets up main menu for the game.
	 */
	private void setupMainMenu() {
		mainMenuInGame = new JPanel();
		mainMenuNotInGame = new JPanel();
		Dimension buttonDimension = new Dimension(300, 70);

		startNewGame = new JButton("New Game");
		startNewGame.addActionListener((e) -> {
		
			restartGame();
		
		});
		startNewGame.setPreferredSize(buttonDimension);

		exitGame = new JButton("Exit Game");
		exitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				currentGameBeingPlayed=!currentGameBeingPlayed;
				pauseGame();
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
		saveGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveGame();
			}
		});
		saveGame.setPreferredSize(buttonDimension);

		playRecordedGame = new JButton("Play Recorded Game");
		playRecordedGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				startRecordedGame();
			}
		});
		playRecordedGame.setPreferredSize(buttonDimension);

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
	}

	/**
	 * Adds the buttons to main menu. Adds resume game and save game buttons only if
	 * currently in a game.
	 */
	private void addButtons() {

		gbc.insets = new Insets(20, 0, 0, 0); // top padding
		mainMenuInGame.setLayout(grid);
		mainMenuNotInGame.setLayout(grid);

		resumeButton.setVisible(true);

		// whilst in game
		if (currentGameBeingPlayed) {
			System.out.println("made it");
			gbc.gridx = 0;
			gbc.gridy = 0;
			mainMenuInGame.add(resumeButton, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			mainMenuInGame.add(saveGame, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			mainMenuInGame.add(startNewGame, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			mainMenuInGame.add(loadGame, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			mainMenuInGame.add(playRecordedGame, gbc);

			gbc.gridx = 0;
			gbc.gridy = 5;
			mainMenuInGame.add(gameControls, gbc);

			gbc.gridx = 0;
			gbc.gridy = 6;
			mainMenuInGame.add(gameHelp, gbc);

			gbc.gridx = 0;
			gbc.gridy = 7;
			mainMenuInGame.add(exitGame, gbc);
		} else {

			gbc.gridx = 0;
			gbc.gridy = 0;
			mainMenuNotInGame.add(startNewGame, gbc);

			// whilst not in game
			gbc.gridx = 0;
			gbc.gridy = 1;
			mainMenuNotInGame.add(loadGame, gbc);

			gbc.gridx = 0;
			gbc.gridy = 2;
			mainMenuNotInGame.add(playRecordedGame, gbc);

			gbc.gridx = 0;
			gbc.gridy = 3;
			mainMenuNotInGame.add(gameControls, gbc);

			gbc.gridx = 0;
			gbc.gridy = 4;
			mainMenuNotInGame.add(gameHelp, gbc);

			gbc.gridx = 0;
			gbc.gridy = 5;
			mainMenuNotInGame.add(exitGame, gbc);
		}
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
				exitPrompt();
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

		// Recorded game playback

		recordedPlayback = new JMenu("Recorded Playback");

		// play
		JMenuItem playRecorded = new JMenuItem("Play");
		playRecorded.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				playRecordedGame();
			}
		});

		// pause
		JMenuItem pauseRecorded = new JMenuItem("Pause");
		pauseRecorded.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pauseRecordedGame();

			}
		});

		// step forwards
		JMenuItem stepForward = new JMenuItem("Step Forward");
		stepForward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stepForwardRecordedGame();
			}
		});

		// step back
		JMenuItem stepBackward = new JMenuItem("Step Backward");
		stepBackward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stepBackwardRecordedGame();
			}
		});

		gameOptions.add(save);
		gameOptions.add(resume);
		gameOptions.add(pause);
		gameOptions.add(exit);

		help.add(controls);
		help.add(instructions);

		recordedPlayback.add(playRecorded);
		recordedPlayback.add(pauseRecorded);
		recordedPlayback.add(stepForward);
		recordedPlayback.add(stepBackward);

		menuBar.add(gameOptions);
		menuBar.add(help);
		this.setJMenuBar(menuBar);
	}

	///////////////Action listeners\\\\\\\\\\\\\\\\\\\\\\\\

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
	public boolean dispatchKeyEvent(KeyEvent e) {
		if(e.getID() != KeyEvent.KEY_PRESSED)
			return false;
		
		if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
			exitGame();
		} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
			exitGameWithSave();
		} else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R) {
			resumeGame();
		} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_P) {
			restartLevel();
		} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_1) {
			restartGame();
		} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			//System.out.println("space - pause");
			pauseGame();
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			//System.out.println("escape - resume");

			resumeGame();
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			
			if(inPlayBackMode)
				playRecordedGame();
			else
				updateChapMove(ChapsAction.UP);
			
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			
			if(inPlayBackMode)
				pauseRecordedGame();
			else
				updateChapMove(ChapsAction.DOWN);
			
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			
			if(inPlayBackMode)
				stepBackwardRecordedGame();
			else
				updateChapMove(ChapsAction.LEFT);
			
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			if(inPlayBackMode)
				stepForwardRecordedGame();
			else
				updateChapMove(ChapsAction.RIGHT);
		}
		
		return true;
	}



//////////////Message Dialogs\\\\\\\\\\\\\\\

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
		JOptionPane.showMessageDialog(this, "CTRL-X  - Exit's the game without saving\n" +
				"CTRL-S  - Exit and saves the game\n" + "CTRL-R  - Resume's a saved game\n"
				+ "CTRL-P  - Start's a new game at the last unfinished level\n"
				+ "CTRL-1 - Start's a new game at level 1\n" + "SPACE - Pause's the game\n"
				+ "ESC - Resume's the game\n" + "UP, DOWN, LEFT, RIGHT ARROWS -- Move's Chap within the maze\n"
				+"",
				"Chap's Challenge Game Controls", JOptionPane.INFORMATION_MESSAGE);
	}

	private void playBackRecordedGameControls() {

		JOptionPane.showMessageDialog(this,
				"Up Arrow  - Plays the recorded game through" +
						"Down Arrow  - Pauses the play of the recorded game\n" +
						"Left Arrow  - Steps Backward in the recored game\n" +
						"Right Arrow  - Steps forward in the recorded game\n",
						"Chap's Challenge Recorded game Controls",
						JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Called for confirmation of exiting the game.
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
	
	/**
	 * Getter for testing
	 * @return .
	 */
	public boolean isCurrentGameBeingPlayed()
	{
		return this.currentGameBeingPlayed;
	}
	
	/**
	 * Getter for testing
	 * @return .
	 */
	public boolean isGamePaused()
	{
		return this.gamePaused;
	}
	
	/**
	 * Getter for testing
	 * @return .
	 */
	public JPanel getMainMenuIngame()
	{
		return this.mainMenuInGame;
	}
	
	/**
	 * Getter for testing
	 * @return .
	 */
	public JPanel getMainMenuNotIngame()
	{
		return this.mainMenuNotInGame;
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		exitPrompt();
	}
	
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	
	@Override
	public void windowIconified(WindowEvent arg0) {}
	
	@Override
	public void windowOpened(WindowEvent arg0) {}
	
	@Override
	public void windowActivated(WindowEvent arg0) {}
	
	@Override
	public void windowClosed(WindowEvent arg0) {}
}