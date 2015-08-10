package com.exercise.shopping.db;

public class Order {

	public static final int STATUS_QUEUED=0X0;
	public static final int STATUS_PROCESSING=0X1;
	public static final int STATUS_SUCCESS=0X2;
	public static final int STATUS_FAILED=0X3;
	
	private Product product;
	private int quantity;
	private int status;
	
	protected Order(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
