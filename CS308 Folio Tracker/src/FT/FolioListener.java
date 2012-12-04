package FT;

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

		if (item.getName().equals("create")) {
			g.newFolio();
		}
		else if (item.getName().equals("editStock")) {
			g.editStock();
		}
		else if (item.getName().equals("exit")) {
			System.exit(0);
		}
		else if (item.getName().equals("edit")) {
			g.editFolio();
		}
		else if (item.getName().equals("add")) {
			g.addStock();
		}
		else if (item.getName().equals("close")) {
			g.closeFolio();
		}
		else if (item.getName().equals("remove")) {
			g.removeStock();
		}

	}
}
