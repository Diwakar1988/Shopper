package com.exercise.shopping.db;

/**
 * Manages information about a product, this is a generic product we can further
 * specialize it in future for electronics, consumer, fashion etc products as
 * required.
 * 
 * @author Diwakar Mishra<BR>
 *         diwakarmishra.himt@gmail.com
 * 
 */
public class Product {

	private int id;
	private String title;
	private double price;
	private int quantity;

	protected Product(int id, String title, double price, int quantity) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * Used for dummy data creation only
	 */
	public Product(String title, double price, int quantity) {
		this(0, title, price, quantity);
	}

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public double getPrice() {
		return this.price;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int newQuantity) {
		this.quantity = newQuantity;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Product)) {
			return false;
		}
		if (o == this) {
			return true;
		}
		Product p = (Product) o;
		return p.getId() == this.getId();
	}

	@Override
	public int hashCode() {
		return new StringBuilder(getId()).append(getTitle()).hashCode();
	}
}
