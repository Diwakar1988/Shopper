package com.exercise.shopping.db;

import com.exercise.shopping.exception.BasketOverflowException;

import java.util.ArrayList;


/**
 * Represents a Basket of orders.
 * 
 * @author Diwakar Mishra<BR>
 *         diwakarmishra.himt@gmail.com
 * 
 */
public class Basket {

	/**
	 * This will be the upper limit for product selection. User is not permitted
	 * to add more than 10 product for one purchase cycle as of now.
	 */
	public static final int MAX_PRODUCTS_TO_SHOP = 10;

	private ArrayList<Order> basket;
	private static Basket instance;

	/**
	 * @return An instance of {@link Basket}
	 */
	public static synchronized Basket getInstance() {
		if (instance == null) {
			instance = new Basket();
		}
		return instance;
	}

	private Basket() {
		basket = new ArrayList<Order>();
	}

	/**
	 * Adds a product into basket and if a product already ordered then it will
	 * only increase quantity of a product to be ordered
	 * 
	 * @param product
	 *            - Product to be added
	 * @param newQuantity
	 *            - New quantity to be added for existing/new product
	 * @throws BasketOverflowException
	 *             - If user consumes allowed product limit
	 */
	public Order add(Product product, int newQuantity)
			throws BasketOverflowException {
		int addedQuantity = calculateExistingProductQuantity();
		if ((addedQuantity + newQuantity) >= MAX_PRODUCTS_TO_SHOP) {
			throw new BasketOverflowException("You can not add more than"
					+ MAX_PRODUCTS_TO_SHOP + " products for one order");
		}
		Order order = getOrder(product);
		if (order == null) {
			order = new Order(product, newQuantity);
			basket.add(order);
		} else {
			order.setQuantity(order.getQuantity() + newQuantity);
		}
		return order;
	}

	/**
	 * Gets an order for a product if it exists in {@link Basket}
	 * 
	 * @param product
	 *            - {@link Product} to be ordered
	 * @return Order for a {@link Product}
	 */
	public Order getOrder(Product product) {
		for (int i = 0; i < basket.size(); i++) {
			Order order = basket.get(i);
			if (order.getProduct().equals(product)) {
				return order;
			}
		}
		return null;
	}

	/**
	 * @return quantity of added products
	 */
	public int calculateExistingProductQuantity() {
		int count = 0;
		Order order = null;
		for (int i = 0; i < basket.size(); i++) {
			order = basket.get(i);
			count += order.getQuantity();
		}
		return count;
	}

	/**
	 * Searches for a {@link Product} in {@link Basket}
	 * 
	 * @param product
	 *            - Product to be searched
	 * @return True is a product exists in basket otherwise false
	 */
	public boolean contains(Product product) {
		return getOrder(product) != null;
	}

	/**
	 * @return gets an order by index otherwise NULL
	 */
	public Order getOrder(int index) {
		if (index < 0 || index >= basket.size()) {
			return null;
		}
		return basket.get(index);
	}

	/**
	 * Deleted an order by index
	 */
	public Order deleteOrder(int index) {
		if (index < 0 || index >= basket.size()) {
			return null;
		}
		return basket.remove(index);
	}

	/**
	 * Deleted an {@link Order}
	 * 
	 * @param order
	 *            - {@link Order} to be deleted
	 * @return true if order deleted otherwise false
	 */
	public boolean deleteOrder(Order order) {
		Order o = null;
		for (int i = 0; i < basket.size(); i++) {
			o = basket.get(i);
			if (o.getProduct().equals(order.getProduct())) {
				basket.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove all items from basket
	 */
	public void clear() {
		basket.clear();
	}

	public int size() {
		return basket.size();
	}
}
