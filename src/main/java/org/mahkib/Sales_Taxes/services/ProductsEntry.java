package org.mahkib.Sales_Taxes.services;

import org.mahkib.Sales_Taxes.interfaces.OrderEntry;
import org.mahkib.Sales_Taxes.models.Money;
import org.mahkib.Sales_Taxes.models.Products;

public class ProductsEntry implements OrderEntry {


	public ProductsEntry(Money price, Products products, int quantity) {
		this.price = price;
		this.products = products;
		this.quantity = quantity;
	}

	public static ProductsEntry create(Products products, Money price, int quantity) {
		return new ProductsEntry(price, products, quantity);
	}

	private Money price;
	private Products products;
	private int quantity;

	private void ProductEntry(Products products, Money price, int quantity) {
		this.products = products;
		this.price = price;
		this.quantity = quantity;
	}

	@Override
	public Money getAmount() {
		return price.multiply(quantity);
	}

	@Override
	public Products getProducts() {
		return products;
	}

	public String getProductName() {
		return products.getName();
	}

	public int getQuantity() {
		return quantity;
	}
}

// Language: java