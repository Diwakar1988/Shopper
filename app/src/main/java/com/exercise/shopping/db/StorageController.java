package com.exercise.shopping.db;

import java.util.ArrayList;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.exercise.shopping.enums.Tables;


/**
 * @author Diwakar Mishra<BR>
 *         diwakarmishra.himt@gmail.com *
 */
public class StorageController extends SQLiteOpenHelper {

	private static final int DB_VERSION = 1;
	private static final String TAG = StorageController.class.getSimpleName();
	private static String DB_NAME = "db_shopping";

	private SQLiteDatabase database;

	public StorageController(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		database = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Tables.Products.createTableQuery());
		db.execSQL(Tables.Basket.createTableQuery());

		this.database=db;
		// insert dummy data
		createDummyData();

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.database=db;
	}

	private void createDummyData() {
		String[] brands = { "Samsung Mobile", "Lenovo Mobile",
				"Motorola Mobile", "Asus Mobile", "Micromax Mobile" };
		double[] prices = { 14500, 10900, 11000, 8999, 7687 };
		int[] baseValues = { 0, 0, 0, 0, 0 };
		final int MAX_QUANTITY = 10;
		Random r = new Random();
		int baseId = 1000;
		for (int i = 0; i < brands.length; i++) {

			for (int j = 0; j < 20; j++) {
				int index = r.nextInt(brands.length);
				String brand = brands[index];
				int baseValue = baseValues[index];
				baseValue+=1;
				baseValues[index] = baseValue;

				String title = brand + " " + (++baseValue);
				double price = prices[index];
				int qty = r.nextInt(MAX_QUANTITY);
				baseId+=1;
				addProduct(baseId, title, price, qty);
			}
		}

	}

	public void addProduct(Product product) {
		addProduct(product.getId(), product.getTitle(), product.getPrice(),
				product.getQuantity());
	}

	private void addProduct(int id, String title, double price, int quantity) {

		try {
			openDBIfClosed();
			String sql = "INSERT INTO " + Tables.PRODUCTS.getLabel()
					+ " VALUES (?,?,?,?);";
			SQLiteStatement statement = database.compileStatement(sql);
			database.beginTransaction();

			statement.clearBindings();

			statement.bindLong(1, id);
			statement.bindString(2, title);
			statement.bindDouble(3, price);
			statement.bindLong(4, quantity);
			statement.execute();

			database.setTransactionSuccessful();
			database.endTransaction();

			Log.d(TAG, "addProduct()- ROW INSERTED : ID=" + id + ", TITLE="
					+ title + ", PRICE=" + price + ", QTY=" + quantity);
		} catch (Exception e) {
			Log.e(TAG, "ERROR- addProduct() : ", e);
		} finally {
			closeDB();
		}

	}

	public void updateProductQuantity(Product product) {

		try {
			openDBIfClosed();

			ContentValues values = new ContentValues();
			values.put(Tables.Products.COL_QTY.getLabel(),
					product.getQuantity());
			database.update(Tables.PRODUCTS.getLabel(), values,
					Tables.Products.COL_ID.getLabel() + "=" + product.getId(), null);

		} catch (Exception e) {
			Log.e(TAG, "ERROR- updateProductQuantity() : ", e);
		} finally {
			closeDB();
		}

	}

	private void openDBIfClosed() {

	}

	private void closeDB() {

	}

	public void updateOrder(Order order) {

		try {
			openDBIfClosed();
			String sql = "INSERT OR REPLACE INTO " + Tables.BASKET.getLabel()
					+ " VALUES (?,?);";
			SQLiteStatement statement = database.compileStatement(sql);
			database.beginTransaction();

			statement.clearBindings();

			statement.bindLong(1, order.getProduct().getId());
			statement.bindLong(2, order.getStatus());
			statement.bindLong(3, order.getQuantity());
			statement.execute();

			database.setTransactionSuccessful();
			database.endTransaction();

			Log.d(TAG,
					"updateOrder()- ROW updated :  TITLE="
							+ order.getProduct().getTitle() + ", STATUS="
							+ order.getStatus() + ", QTY="
							+ order.getQuantity());

		} catch (Exception e) {
			Log.e(TAG, "ERROR- saveOrder() : ", e);
		} finally {
			closeDB();
		}
	}

	public void deleteOrder(Order order) {

		try {
			openDBIfClosed();
			String whereClause = Tables.Basket.COL_PRODUCT_ID.getLabel() + "=?";
			String[] whereArgs = new String[] { String.valueOf(order
					.getProduct().getId()) };

			database.delete(Tables.BASKET.getLabel(), whereClause, whereArgs);
		} catch (Exception e) {
			Log.e(TAG, "ERROR- deleteOrder() : ", e);
		} finally {
			closeDB();
		}

	}

	public void loadOrders() {
		Basket basket = Basket.getInstance();
		Cursor cursor = null;
		try {

			String query = "SELECT * FROM " + Tables.BASKET.getLabel();

			cursor = database.rawQuery(query, null);

			if (cursor != null) {
				basket.clear();
				int id = cursor.getColumnIndex(Tables.Basket.COL_PRODUCT_ID
						.getLabel());
				int status = cursor.getColumnIndex(Tables.Basket.COL_STATUS
						.getLabel());
				int qty = cursor.getColumnIndex(Tables.Basket.COL_QTY
						.getLabel());

				cursor.moveToFirst();
				while (cursor.moveToNext()) {
					Order order = basket.add(getProduct(cursor.getInt(id)),
							cursor.getInt(qty));
					order.setStatus(cursor.getInt(status));
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "ERROR: loadOrders() -", e);
		} finally {
			try {
				cursor.close();
			} catch (Exception e2) {
			}
		}
	}

	private Product getProduct(int id) {
		Product p = null;
		Cursor cursor = null;
		try {

			String query = "SELECT * FROM " + Tables.PRODUCTS.getLabel()
					+ " WHERE " + Tables.Products.COL_ID.getLabel() + "=" + id;

			cursor = database.rawQuery(query, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					p = new Product(cursor.getInt(0), cursor.getString(1),
							cursor.getDouble(2), cursor.getInt(3));
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "ERROR: getProduct() ID=" + id, e);
		} finally {
			try {
				cursor.close();
			} catch (Exception e2) {
			}
		}
		return p;
	}

	public ArrayList<Product> loadProducts(int offset, int chunkSize) {
		ArrayList<Product> list = new ArrayList<Product>();
		Cursor cursor = null;
		try {

			String query = "SELECT * FROM " + Tables.PRODUCTS.getLabel()
					+ " LIMIT " + offset + ", " + chunkSize;

			cursor = database.rawQuery(query, null);

			if (cursor != null) {
				cursor.moveToFirst();
				while (cursor.moveToNext()) {
					Product p = new Product(cursor.getInt(0),
							cursor.getString(1), cursor.getDouble(2),
							cursor.getInt(3));
					list.add(p);
				}
			}
			Log.d(TAG, "Products Loaded successfully! COUNT="+list.size());
		} catch (Exception e) {
			Log.e(TAG, "ERROR: loadProducts() OFFSET=" + offset
					+ " CHUNK_SIZE= " + chunkSize, e);
		} finally {
			try {
				cursor.close();
			} catch (Exception e2) {
			}
		}
		return list;
	}
}
