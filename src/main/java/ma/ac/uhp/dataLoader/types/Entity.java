package ma.ac.uhp.dataLoader.types;

import oracle.kv.table.Row;
import oracle.kv.table.TableAPI;

public interface Entity {
	Row getRow(TableAPI tableAPI);

}
