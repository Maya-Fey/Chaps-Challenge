package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EnumSet;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
	
	private final ChapsModel model;
	private final ChapsView view;
	
	//menuBar
	private JMenuBar menuBar;
	private JMenu gameOptions, help;
	
	//Game state variables
	private boolean gamePaused=false;
	private EnumSet<ChapsEvent> chapsEvents;

	
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
	}
	
	/**
	 * Starts the game, initializing setup
	 */
	@Override
	public void start() {
		setUpWindow();
	}

	
	
	/**
	 * Sets up all window settings on initalisation.
	 * Adds window closing confirmation.
	 * Sets window size.
	 */
	private void setUpWindow() {
		this.add(view.getRootPanel());
		this.setSize(windowHeight, windowLength);
		// Adds a window confirmation for closing game
		addWindowListener(new WindowAdapter() { 
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Yes", "No" };
				int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Chip's Challenge",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
		setupMenuBars();
		
		this.setVisible(true);
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
	 * SPACE - pause the game and display a “game is paused” dialog
	 * ESC - close the “game is paused” dialog and resume the game
     * UP, DOWN, LEFT, RIGHT ARROWS -- move Chap within the maze
	 * @param e - KeyEvent
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// CTRL-X
		
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
			//pauseGame();
			model.onAction(ChapsAction.TICK);
			view.updateRemainingTime(model.getTimeRemaining());
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
	 */
	private void updateChapMove(ChapsAction action) {

		if (!gamePaused) {
			chapsEvents = model.onAction(action);

		}
	}

	/**
	 * Updates the application graphics.
	 */
	private void updateGraphics() {
		model.getInventoryIcons();// needs updating

		view.updateBoard(model.getVisibleArea());
		// view.getRootPanel();
		this.add(view.getRootPanel());
		this.pack();
	}

	/**
	 * 
	 */
	private void exitGame() {
	}

	/**
	 * 
	 */
	private void exitGameWithSave() {
	}
	
	/**
	 * Saves the game.
	 */
	private void saveGame() {
		
		
	}

	/**
	 * 
	 */
	private void resumeGame() {
	}

	/**
	 * 
	 */
	private void restartGame() {
	}

	/**
	 * 
	 */
	private void restartLevel() {
	}

	/**
	 * 
	 */
	private void pauseGame() {
	}
	
	/**
	 * 
	 */
	private void instructionsHelp() {}
	
	/**
	 * 
	 */
	private void controlsHelp() {}

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