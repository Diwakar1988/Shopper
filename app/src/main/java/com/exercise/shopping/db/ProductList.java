package com.exercise.shopping.db;

import java.util.ArrayList;

/**
 * @author Diwakar Mishra<BR>
 *         diwakarmishra.himt@gmail.com
 * 
 */
public class ProductList {

	/**
	 * A cache limit to load max products in memory from DB to avoid waste of
	 * memory
	 */
	public static final int MAX_PRODUCTS_TO_LOAD = 30;

	private ArrayList<Product> products;
	private static ProductList instance;

	private ProductList() {
		products = new ArrayList<Product>();
	}

	public static ProductList getInstance() {
		if (instance==null) {
			instance=new ProductList();
		}
		return instance;
	}

	public void add(ArrayList<Product> newItems) {
		products.addAll(newItems);
	}
//	public void add(ArrayList<Product> newItems, boolean scrollUp) {
//
//		if (scrollUp) {
//			int itemIndexToRemove = 0;
//			for (int i = 0; i < newItems.size(); i++) {
//				// If products in cache increases upper limit then we need to
//				// remove invisible Products
//				if (products.size() >= MAX_PRODUCTS_TO_LOAD) {
//					products.remove(itemIndexToRemove);
//				}
//				// append product on scroll up
//				products.add(newItems.get(i));
//			}
//		} else {
//			int itemIndexToRemove = size() - 1;
//			for (int i = newItems.size() - 1; i >= 0; i--) {
//				// If products in cache increases upper limit then we need to
//				// remove invisible Products
//				if (products.size() >= MAX_PRODUCTS_TO_LOAD) {
//					products.remove(itemIndexToRemove);
//				}
//
//				// insert product on scroll down
//				products.add(0, newItems.get(i));
//			}
//
//		}
//	}

	public Product get(int position) {
		if (position < 0 && position >= size()) {
			return null;
		}
		return products.get(position);
	}

	public int size() {
		return products.size();
	}

	public void clear() {
		products.clear();
	}



}
