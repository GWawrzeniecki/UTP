package zad1;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.table.AbstractTableModel;

public class Database extends AbstractTableModel {
	private String url;
	private TravelData dataTrips;
	private Connection con;
	private DatabaseMetaData md;
	private final String driverName = "org.apache.derby.jdbc.ClientDriver";
	private Statement stm;
	private PreparedStatement preparedStm;
	private SimpleDateFormat simpleDate;
	private ResultSetMetaData rmd;
	private ResultSet rs;
	private String[] columnNames;
	private int[] columnTypes;
	private boolean[] readOnly;
	private String tableName = "";
	private List rows;
	private boolean editable = false;

	public Database(String url, TravelData dataTrips) {
		this.url = url;
		this.dataTrips = dataTrips;

	}

	public void create() {
		try {
			Class.forName(driverName).newInstance();// załadowanie sterowników
			con = DriverManager.getConnection(url);

			md = con.getMetaData();
			stm = con.createStatement();

			// report();
			dropTableTrip();
			createTableTrip();
			insertTripsIntoTrip();
			// getTrips();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void dropTableTrip() {
		String drop = "DROP TABLE TRIP";
		try {
			stm.execute(drop);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void createTableTrip() {

		String crt = "CREATE TABLE TRIP ( " + "    ID_Trip  INTEGER,      " + "    Country VARCHAR(30), "
				+ "    dateFrom date, " + "    dateTo date, " + "    Place VARCHAR(30), " + "    Price VARCHAR(30), "
				+ "    Currency VARCHAR(30), " + "    CONSTRAINT Trip_pk PRIMARY KEY(ID_Trip))";

		try {
			stm.executeUpdate(crt);

		} catch (SQLException exc) {
			System.out.println("SQL except.: " + exc.getMessage()); // komunikat
			System.out.println("SQL state  : " + exc.getSQLState()); // kod
																		// standardowy
			System.out.println("Vendor errc: " + exc.getErrorCode()); // kod
																		// zależny
																		// od
																		// RDBMS
			System.exit(1);
		}
	}

	private void insertTripsIntoTrip() {
		simpleDate = dataTrips.getDateFormat();
		Date date = new Date();

		int counter = 1;
		String insert = "INSERT INTO TRIP VALUES" + "(?,?,?,?,?,?,?)";
		try {
			preparedStm = con.prepareStatement(insert);
		} catch (SQLException exc) {
			System.out.println("tutaj1");
			System.out.println("SQL except.: " + exc.getMessage()); // komunikat
			System.out.println("SQL state  : " + exc.getSQLState()); // kod
																		// standardowy
			System.out.println("Vendor errc: " + exc.getErrorCode()); // kod
																		// zależny
																		// od
																		// RDBMS
			System.exit(1);
		}

		for (String row : dataTrips.getListOfTrips()) {
			String[] tripInfo = row.split("sep");

			try {
				preparedStm.setInt(1, counter++);
				preparedStm.setString(2, tripInfo[0]);
				// date = simpleDate.parse(tripInfo[2]);
				date = simpleDate.parse(tripInfo[1]);
				String formatedDate = simpleDate.format(date);
				preparedStm.setDate(3, java.sql.Date.valueOf(formatedDate));
				date = simpleDate.parse(tripInfo[2]);
				formatedDate = simpleDate.format(date);
				preparedStm.setDate(4, java.sql.Date.valueOf(formatedDate));
				preparedStm.setString(5, tripInfo[3]);
				preparedStm.setString(6, tripInfo[4]);
				preparedStm.setString(7, tripInfo[5]);
				preparedStm.executeUpdate();
			} catch (SQLException exc) {
				System.out.println("tutaj");
				System.out.println("SQL except.: " + exc.getMessage()); // komunikat
				System.out.println("SQL state  : " + exc.getSQLState()); // kod
																			// standardowy
				System.out.println("Vendor errc: " + exc.getErrorCode()); // kod
																			// zależny
																			// od
																			// RDBMS
				System.exit(1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	protected void prepareForGUI(String query) {

		tableName = getTableName(query);
		try {
			rs = stm.executeQuery(query);
			rmd = rs.getMetaData();
			int cc = rmd.getColumnCount();
			columnNames = new String[cc];
			columnTypes = new int[cc];
			readOnly = new boolean[cc];
			for (int col = 0; col < cc; col++) {
				columnNames[col] = rmd.getColumnName(col + 1);
				columnTypes[col] = rmd.getColumnType(col + 1);
				readOnly[col] = rmd.isReadOnly(col + 1);
			}

			rows = new ArrayList();
			while (rs.next()) {
				List row = new ArrayList();
				for (int i = 1; i <= getColumnCount(); i++) {
					row.add(rs.getObject(i));
				}
				rows.add(row);
			}
			rs.close();
			fireTableChanged(null); // Nowa tabela
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	// Niedoskonala wersja
	private String getTableName(String q) {
		if (q == null || q.equals(""))
			return "";
		StringTokenizer st = new StringTokenizer(q);
		while (st.hasMoreTokens()) {
			String w = st.nextToken();
			w = w.toUpperCase();
			if (w.equals("FROM")) {
				String t = st.nextToken();
				if (t.indexOf(',') == -1)
					return t;
				break;
			}
		}
		return "";
	}

	public void showGui() {
		FrameGUI startGUI = new FrameGUI(url, driverName, this);
	}

	// Obowiązkowe metody interfejsu TableModel
	public String getColumnName(int column) {
		if (columnNames[column] != null)
			return columnNames[column];
		else
			return "";
	}

	public Class getColumnClass(int column) {
		String type;
		Class c = null;
		try {
			type = rmd.getColumnClassName(column + 1);
			c = Class.forName(type);
		} catch (Exception e) {
			return super.getColumnClass(column);
		}
		return c;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return rows.size();
	}

	public Object getValueAt(int r, int c) {
		List row = (List) rows.get(r);
		return row.get(c);
	}

	protected TravelData getTV() {
		return dataTrips;
	}

	protected void changeLangugage() {
		String delete = "Delete from TRIP";
		try {
			stm.execute(delete);
			insertTripsIntoTrip();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
