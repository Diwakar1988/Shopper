package com.exercise.shopping;

import android.app.Application;

import com.exercise.shopping.db.DataController;


/**
 * @author Diwakar Mishra<BR>
 *         diwakarmishra.himt@gmail.com *
 */
public class ShoppingApplication extends Application {

	private static ShoppingApplication instance;

	public static ShoppingApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		// Initialize data controller
		DataController.init(this);

	}

	
}
