package ma.ac.uhp.dataLoader;

import ma.ac.uhp.dataLoader.extractors.CustomerExtractor;
import ma.ac.uhp.dataLoader.extractors.LineItemExtractor;
import ma.ac.uhp.dataLoader.extractors.PartExtractor;
import ma.ac.uhp.dataLoader.extractors.SupplierExtractor;
import ma.ac.uhp.dataLoader.tables.CustomerTable;
import ma.ac.uhp.dataLoader.tables.LineItemTable;
import ma.ac.uhp.dataLoader.tables.PartTable;
import ma.ac.uhp.dataLoader.tables.SupplierTable;
import ma.ac.uhp.dataLoader.types.Entity;
import ma.ac.uhp.dataLoader.types.TableNames;
import oracle.kv.FaultException;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.table.PrimaryKey;
import oracle.kv.table.Row;
import oracle.kv.table.Table;
import oracle.kv.table.TableAPI;
import oracle.kv.table.TableIterator;

/**
 * Class that creates sample records and uses the Table API to populate a NoSQL
 * Database with those records.
 *
 * This class assumes that a table of the following name and format has been
 * created and added to the store:
 * 
 * You can  disable the security of the store. For kvlite, you can use the
 * following command to start a non-secure store.
 * 
 * <pre>
 *  &gt; java -jar KVHOME/lib/kvstore.jar kvlite -secure-config disable
 * </pre>
 */
public final class DBLoad<T> {

	private final KVStore store;
	private final TableAPI tableAPI;
	private final Table table;
	private String tableName;
	private String filePATH;

	private long nOps = 10;

	private boolean deleteExisting = false;

	static final String USER_OBJECT_TYPE = "user";

	public static void main(final String[] args) {
		try {
			final DBLoad loadData = new DBLoad(args);
			loadData.run();
		} catch (FaultException e) {
			e.printStackTrace();
			System.out.println("Please make sure a store is running " + "and a table is created.");
			System.out.println(
					"The error could be caused by a security " + "mismatch. If the store is configured secure, you "
							+ "should specify a user login file " + "with system property oracle.kv.security. "
							+ "For example, \n" + "\tjava -Doracle.kv.security=<user security login file>"
							+ " externaltables.table.LoadCookbookTable\n" + "KVLite generates the security file in "
							+ "$KVHOME/kvroot/security/user.security ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses command line args and opens the KVStore.
	 */
	private DBLoad(final String[] argv) {

		String storeName = "";
		String hostName = "";
		String hostPort = "";

		final int nArgs = argv.length;
		int argc = 0;
		if (nArgs == 0) {
			usage(null);
		}

		while (argc < nArgs) {
			final String thisArg = argv[argc++];

			if ("-store".equals(thisArg)) {
				if (argc < nArgs) {
					storeName = argv[argc++];
				} else {
					usage("-store requires an argument");
				}
			} else if ("-host".equals(thisArg)) {
				if (argc < nArgs) {
					hostName = argv[argc++];
				} else {
					usage("-host requires an argument");
				}
			} else if ("-port".equals(thisArg)) {
				if (argc < nArgs) {
					hostPort = argv[argc++];
				} else {
					usage("-port requires an argument");
				}
			} else if ("-nops".equals(thisArg)) {
				if (argc < nArgs) {
					nOps = Long.parseLong(argv[argc++]);
				} else {
					usage("-nops requires an argument");
				}
			} else if ("-table".equals(thisArg)) {
				if (argc < nArgs) {
					tableName = argv[argc++];
				} else {
					usage("-table requires an argument");
				}
			} else if ("-file".equals(thisArg)) {
				if (argc < nArgs) {
					filePATH = argv[argc++];
				} else {
					usage("-file requires an argument");
				}
			} else if ("-delete".equals(thisArg)) {
				deleteExisting = true;
			} else {
				usage("Unknown argument: " + thisArg);
			}
		}

		store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));

		tableAPI = store.getTableAPI();

		table = tableAPI.getTable(tableName);
		if (table == null) {
			final String msg = "Store does not contain table [name=" + tableName + "]";
			throw new RuntimeException(msg);
		}
	}

	private void usage(final String message) {
		if (message != null) {
			System.out.println("\n" + message + "\n");
		}

		System.out.println("usage: " + getClass().getName());
		System.out.println("\t-store <instance name>\n" + "\t-host <host name>\n" + "\t-port <port number>\n"
				+ "\t-nops <total records to create>\n" + "\t-delete (default: false) [delete all existing data]\n");
		System.exit(1);
	}

	private void run() {
		if (deleteExisting) {
			deleteExistingData();
		}
		doLoad();
	}

	private void doLoad() {
		TableNames TABLE = TableNames.fromString(tableName);
		switch (TABLE) {
		case LINEITEM:
			LineItemExtractor lineItemExtractor = new LineItemExtractor(filePATH);
			for (LineItemTable lineItemTable : lineItemExtractor) {
				addRow(lineItemTable);
			}
			displayRow(table);
			System.out.println(nOps + " new records added");
			store.close();
			break;
		case CUSTOMER:
			CustomerExtractor customerExtractor = new CustomerExtractor(filePATH);
			for (CustomerTable customerTable : customerExtractor) {
				addRow(customerTable);
			}
			displayRow(table);
			System.out.println(nOps + " new records added");
			store.close();
			break;
		case SUPPLIER:
			SupplierExtractor supplierExtractor = new SupplierExtractor(filePATH);
			for (SupplierTable supplierTable : supplierExtractor) {
				addRow(supplierTable);
			}
			displayRow(table);
			System.out.println(nOps + " new records added");
			store.close();
			break;
		case PART:
			PartExtractor partExtractor = new PartExtractor(filePATH);
			for (PartTable partTable : partExtractor) {
				addRow(partTable);
			}
			displayRow(table);
			System.out.println(nOps + " new records added");
			store.close();
			break;
		default:
			System.out.println("table unknown");
			break;
		}
	}

	private void addRow(Entity table) {
		tableAPI.putIfAbsent(table.getRow(tableAPI), null, null);
	}

	private void deleteExistingData() {

		/* Get an iterator over all the primary keys in the table. */
		final TableIterator<PrimaryKey> itr = tableAPI.tableKeysIterator(table.createPrimaryKey(), null, null);

		/* Delete each row from the table. */
		long cnt = 0;
		while (itr.hasNext()) {
			tableAPI.delete(itr.next(), null, null);
			cnt++;
		}
		itr.close();
		System.out.println(cnt + " records deleted");
	}

	/*
	 * Convenience method for displaying output when debugging.
	 */
	private void displayRow(Table tbl) {
		final TableIterator<Row> itr = tableAPI.tableIterator(tbl.createPrimaryKey(), null, null);
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
		itr.close();
	}
}
