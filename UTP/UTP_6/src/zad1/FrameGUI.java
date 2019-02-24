// Testowy interfejs SQL
package zad1;

import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class FrameGUI extends JFrame  {

	private JTable table = new JTable();
	private final String exec = "select * from TRIP";
	private JMenuBar menuBar;
	private TravelData tD;
	private Database db;

	public FrameGUI(String URL, String driver, Database db) {
		super("Baza danych wycieczek");
		setDefaultCloseOperation(3);
		this.db = db;
		System.setProperty("apple.laf.useScreenMenuBar", "true"); // MAC OS

		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(600, 400));
		getContentPane().add(scrollpane, "Center");
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Language");
		JMenuItem jMenuitemPol = new JMenuItem("Polish");

		jMenuitemPol.addActionListener(e -> {
			tD.getOffersDescriptionsList("pl_PL", "yyyy-MM-dd");
			db.changeLangugage();
			execute(exec);
		});
		JMenu jMenuitemNie = new JMenu("German");
		JMenuItem jMenuitemAu = new JMenuItem("Austria");
		JMenuItem jMenuitemAsz = new JMenuItem("Switzerland ");
		JMenuItem jMenuitemAni = new JMenuItem("Germany");
		JMenuItem jMenuitemlu = new JMenuItem("Luxembourg");
		jMenuitemNie.add(jMenuitemAu);
		jMenuitemNie.add(jMenuitemAsz);
		jMenuitemNie.add(jMenuitemAni);
		jMenuitemNie.add(jMenuitemlu);

		jMenuitemAu.addActionListener(e -> {
			tD.getOffersDescriptionsList("de_AT", "yyyy-MM-dd");
			db.changeLangugage();
			execute(exec);
		});
		jMenuitemAsz.addActionListener(e -> {
			tD.getOffersDescriptionsList("de_CH", "yyyy-MM-dd");
			db.changeLangugage();
			execute(exec);
		});
		jMenuitemAni.addActionListener(e -> {
			tD.getOffersDescriptionsList("de_DE", "yyyy-MM-dd");
			db.changeLangugage();
			execute(exec);
		});
		jMenuitemlu.addActionListener(e -> {
			tD.getOffersDescriptionsList("de_LU", "yyyy-MM-dd");
			db.changeLangugage();
			execute(exec);
		});
		JMenuItem jMenuitemAng = new JMenuItem("English");
		jMenuitemAng.addActionListener(e -> {
			tD.getOffersDescriptionsList("EN_US", "yyyy-MM-dd");
			db.changeLangugage();
			execute(exec);
		});
		fileMenu.add(jMenuitemPol);
		fileMenu.add(jMenuitemNie);
		fileMenu.add(jMenuitemAng);
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		// createHistoryList();

		pack();
		setVisible(true);

		execute(exec);
	}

	void execute(String query) {

		db.prepareForGUI(query);
		tD = db.getTV();
		table.setModel(db);

	}


}