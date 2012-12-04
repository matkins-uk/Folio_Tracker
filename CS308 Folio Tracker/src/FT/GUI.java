package FT;

import static org.mockito.Mockito.mock;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;


public class GUI extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private MyTableModel tableData;
	private JTable table;
	private JButton add, edit, close, remove, editStock;
	private ArrayList<IFolio> portfolios = new ArrayList<IFolio>();

	public GUI() {
		super(new GridLayout(1, 1));
		this.add(tabbedPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	public void addStock() {
		int optionChosen = 1;
		NumberFormat numFor = NumberFormat.getIntegerInstance();
		JTextField name = new JTextField(40);
		JTextField numShares = new JFormattedTextField(numFor);
		Object[] message = { "Stock Name:", name, "Num of Shares:", numShares };

		while (optionChosen != 0) {
			optionChosen = JOptionPane.showOptionDialog(null, message,
					"Create Folio", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, null, null);

			if (optionChosen == 2 || optionChosen == -1) {
				optionChosen = 0;
			} else {
				if (optionChosen == 0
						&& (name.getText().equals("") || numShares.getText()
								.equals(""))) {
					JOptionPane.showMessageDialog(null,
							"Invalid Input: No input entered", "Invalid Input",
							JOptionPane.ERROR_MESSAGE);
					optionChosen = 1;
				} else
					try {
						if (numShares.getText().trim().length() == 0
								|| numFor.parse(numShares.getText()).intValue() <= 0) {
							JOptionPane
									.showMessageDialog(
											null,
											"Invalid Input: Num of Shares must be greater than zero",
											"Invalid Input",
											JOptionPane.ERROR_MESSAGE);
							optionChosen = 1;
						} else if (optionChosen == 0
								&& numFor.parse(numShares.getText()).intValue() > 0) {
							JScrollPane pane = (JScrollPane) tabbedPane
									.getSelectedComponent();
							JViewport view = pane.getViewport();
							JTable aTable = (JTable) view.getView();
							DefaultTableModel model = (DefaultTableModel) aTable
									.getModel();
							
							IStock stock = mock(IStock.class);
							stock.setNoOfShares(numFor.parse(numShares.getText()).intValue());
							Object[] stockDetails = { name.getText(), numShares.getText(), stock.getPricePerShare(), stock.getValue(), stock.getHigh(), stock.getLow()};
							
							portfolios.get(tabbedPane.getSelectedIndex()).addStock(stock);
							model.addRow(stockDetails);
							editStock.setEnabled(true);
						}
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}

	public void newFolio() {

		int optionChosen = 1;

		tableData = new MyTableModel();

		tableData.addColumn("Stock Name");
		tableData.addColumn("Num of Shares");
		tableData.addColumn("Price per Share");
		tableData.addColumn("Value of Holding");
		tableData.addColumn("High");
		tableData.addColumn("Low");

		table = new JTable(tableData);

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		JTextField name = new JTextField(40);

		IFolio folio = mock(IFolio.class);
		
		while (optionChosen != 2 && optionChosen != -1
				&& name.getText().equals("")) {
			Object[] message = { "Folio Name:", name };
			optionChosen = JOptionPane.showOptionDialog(null, message,
					"Create Folio", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, null, null);

			if (optionChosen == 0 && name.getText().equals("")) {
				JOptionPane.showMessageDialog(null,
						"Invalid Input: No Name entered", "Invalid Input",
						JOptionPane.ERROR_MESSAGE);
			} else if (optionChosen == 0 && !name.getText().equals("")) {
				tabbedPane.addTab(name.getText() + " £" + folio.getValue(), scrollPane);
				folio.setName(name.getText());
				portfolios.add(folio);
			}
		}

		enableButtons();
		editStock.setEnabled(false);
	}

	public void createAndShowGUI() {
		JFrame frame = new JFrame("Folio Tracker");
		frame.setPreferredSize(new Dimension(620, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuB = new JMenuBar();
		frame.setJMenuBar(menuB);

		JMenu folio = new JMenu("Folio");
		menuB.add(folio);

		JMenuItem create = new JMenuItem("New");
		create.setName("create");
		folio.add(create);
		create.addActionListener(new FolioListener(this));

		JMenuItem open = new JMenuItem("Open");
		folio.add(open);

		JMenuItem save = new JMenuItem("Save");
		folio.add(save);

		JMenuItem exit = new JMenuItem("Exit");
		exit.setName("exit");
		folio.add(exit);
		exit.addActionListener(new FolioListener(this));

		frame.add(this, BorderLayout.CENTER);

		edit = new JButton("Edit Folio Name");
		edit.setName("edit");
		edit.addActionListener(new FolioListener(this));

		add = new JButton("Add a Stock");
		add.setName("add");
		add.addActionListener(new FolioListener(this));

		remove = new JButton("Remove Stock");
		remove.setName("remove");
		remove.addActionListener(new FolioListener(this));

		close = new JButton("Close Portfolio");
		close.setName("close");
		close.addActionListener(new FolioListener(this));
		
		editStock = new JButton("Buy/Sell Shares");
		editStock.setName("editStock");
		editStock.addActionListener(new FolioListener(this));

		JPanel buttonPanel = new JPanel();
		frame.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(edit);
		buttonPanel.add(editStock);
		buttonPanel.add(add);
		buttonPanel.add(remove);
		buttonPanel.add(close);

		disableButtons();

		frame.pack();
		frame.setVisible(true);
	}

	private void enableButtons() {
		edit.setEnabled(true);
		remove.setEnabled(true);
		editStock.setEnabled(true);
		add.setEnabled(true);
		close.setEnabled(true);
	}

	private void disableButtons() {
		edit.setEnabled(false);
		remove.setEnabled(false);
		editStock.setEnabled(false);
		add.setEnabled(false);
		close.setEnabled(false);
	}

	public void closeFolio() {

		if (tabbedPane.getSelectedIndex() != -1) {
			tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			portfolios.remove(tabbedPane.getSelectedIndex()+1);
		}
		if (tabbedPane.getTabCount() == 0) {
			disableButtons();
		}
	}

	public void removeStock() {
		JScrollPane pane = (JScrollPane) tabbedPane.getSelectedComponent();
		JViewport view = pane.getViewport();
		JTable aTable = (JTable) view.getView();
		MyTableModel model = (MyTableModel) aTable.getModel();
		if (aTable.getSelectedRow() != -1) {
			model.removeRow(aTable.getSelectedRow());
		} else {
			JOptionPane.showMessageDialog(null,
					"Invalid Input: No Stock Selected", "Invalid Input",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void editFolio() {
		int optionChosen = 1;
		JTextField name = new JTextField(40);
		Object[] message = { "New Folio Name:", name };
		while (optionChosen != 0) {
			optionChosen = JOptionPane.showOptionDialog(null, message,
					"Create Folio", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, null, null);

			if (optionChosen == 0) {
				tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),
						name.getText() + " £" + portfolios.get(tabbedPane.getSelectedIndex()).getValue());
				portfolios.get(tabbedPane.getSelectedIndex()).setName(name.getText());
			} else {
				optionChosen = 0;
			}
		}
	}
	
	public void editStock() {
		JScrollPane pane = (JScrollPane) tabbedPane.getSelectedComponent();
		JViewport view = pane.getViewport();
		JTable aTable = (JTable) view.getView();
		MyTableModel model = (MyTableModel) aTable.getModel();
		if (aTable.getSelectedColumn() == 1) {
			JTextField buy = new JFormattedTextField();
			JTextField sell = new JFormattedTextField();
			NumberFormat numFor = NumberFormat.getIntegerInstance();;
			Object[] message = { "Buy Shares:", buy, "Sell Shares:", sell };
			JOptionPane.showOptionDialog(null, message,
					"Create Folio", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, null, null);
			
			try {
				if (numFor.parse(buy.getText()).intValue() <= 0 && numFor.parse(sell.getText()).intValue() <= 0) {
					JOptionPane.showMessageDialog(null,
							"Invalid Input: No input entered", "Invalid Input",
							JOptionPane.ERROR_MESSAGE);
				}
				else if (numFor.parse(buy.getText()).intValue() > 0 && numFor.parse(sell.getText()).intValue() > 0) {
					JOptionPane.showMessageDialog(null,
							"Invalid Input: Only one field may be entered at a time", "Invalid Input",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					portfolios.get(tabbedPane.getSelectedIndex());
					model.setValueAt(78, aTable.getSelectedRow(), aTable.getSelectedColumn());
				}
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable ob, Object obj) {
		tableData.fireTableDataChanged();
	}
}