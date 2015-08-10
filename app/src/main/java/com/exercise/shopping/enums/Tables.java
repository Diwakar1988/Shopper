package com.exercise.shopping.enums;

public enum Tables {
	PRODUCTS("products"), BASKET("basket");

	private String label;

	private Tables(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public enum Products {
		COL_ID("id"), COL_TITLE("title"), COL_PRICE("price"), COL_QTY("qty");

		private String label;

		private Products(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public static String createTableQuery() {
			return "CREATE TABLE IF NOT EXISTS " + PRODUCTS + " ("
					+ COL_ID.getLabel() + " INTEGER PRIMARY KEY, "
					+ COL_TITLE.getLabel() + " TEXT, " + COL_PRICE.getLabel()
					+ " REAL, " + COL_QTY.getLabel() + " INTEGER)";
		}
	}

	public enum Basket {
		COL_PRODUCT_ID("id"),COL_STATUS("status"), COL_QTY("qty");

		private String label;

		private Basket(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public static String createTableQuery() {
			return "CREATE TABLE IF NOT EXISTS " + BASKET + " ("
					+ COL_PRODUCT_ID.getLabel() + " INTEGER PRIMARY KEY, "
					+ COL_STATUS.getLabel() + " INTEGER, "
					+ COL_QTY.getLabel() + " INTEGER" + ")";
		}
	}

}
