package FT;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class MainMethod {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI gui = new GUI();
				//Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				gui.createAndShowGUI();
			}
		});
	}

}
