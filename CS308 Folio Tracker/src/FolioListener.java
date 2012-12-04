

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

public class FolioListener implements ActionListener {

	private GUI g;

	public FolioListener(GUI g) {
		this.g = g;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComponent item = (JComponent) e.getSource();

		if (item.getName() == "create") {
			g.newFolio();
		}
		else if (item.getName() == "exit") {
			System.exit(0);
		}
		else if (item.getName() == "edit") {
			g.editFolio();
		}
		else if (item.getName() == "add") {
			g.addStock();
		}
		else if (item.getName() == "close") {
			g.closeFolio();
		}
		else if (item.getName() == "remove") {
			g.removeStock();
		}

	}
}
