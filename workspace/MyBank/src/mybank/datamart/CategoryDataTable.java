package mybank.datamart;

import mybank.dataaccess.DataAccessSQLite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CategoryDataTable {
	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_CATEGORY = "name";
	public static final String KEY_SUMMARY = "summary";
	public static final String KEY_DESCRIPTION = "description";
	private static final String DATABASE_TABLE = "Category";
	private Context context;
	private SQLiteDatabase database;
	private DataAccessSQLite dbHelper;

	public CategoryDataTable(Context context) {
		this.context = context;
	}

	public CategoryDataTable open() throws SQLException {
		dbHelper = new DataAccessSQLite(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * Create a new Category If the Category is successfully created return the new
	 * rowId for that note, otherwise return a -1 to indicate failure.
	 */
	public long createCategory(String name) {
		ContentValues initialValues = createContentValues(name);

		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	/**
	 * Update the Category
	 */
	public boolean updateCategory(long rowId, String name) {
		ContentValues updateValues = createContentValues(name);

		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
				+ rowId, null) > 0;
	}

	/**
	 * Deletes Category
	 */
	public boolean deleteCategory(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all Category in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAllCategorys() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_CATEGORY }, null, null, null,
				null, null);
	}

	/**
	 * Return a Cursor positioned at the defined Category
	 */
	public Cursor fetchCategory(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_CATEGORY },
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String name) {
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY, name);
		return values;
	}
}
