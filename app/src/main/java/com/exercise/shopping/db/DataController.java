package com.exercise.shopping.db;

import java.util.ArrayList;


import android.content.Context;
import android.util.Log;

import com.exercise.shopping.util.BackgroundExecutor;

/**
 * @author Diwakar Mishra<BR>
 *         diwakarmishra.himt@gmail.com
 * 
 */
public class DataController {

	private static final String TAG = DataController.class.getSimpleName();
	private static DataController instance;
	private StorageController storageController;

	private DataController(Context context) {

		ApplicationPreferences.init(context);
		storageController = new StorageController(context);
		// Init saved orders
		storageController.loadOrders();
		// Load a chunk of products to be displayed
		loadProducts(0, ProductList.MAX_PRODUCTS_TO_LOAD);

	}

	public void loadProducts(int offset, int maxProductsToLoad) {
		ProductList productList = ProductList.getInstance();
		ArrayList<Product> products = storageController.loadProducts(offset,
				maxProductsToLoad);
		productList.add(products);
	}

	/**
	 * Initialize all data models and storage
	 */
	public static synchronized void init(Context context) {
		instance = new DataController(context);

	}

	public static DataController getInstance() {
		return instance;
	}

	public void updateOrder(final Order order) {
		BackgroundExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				storageController.updateOrder(order);
			}
		});
	}

	public void deleteOrder(final Order order) {
		BackgroundExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				storageController.deleteOrder(order);
			}
		});
	}

}
