package org.mahkib.Sales_Taxes.models;

import org.mahkib.Sales_Taxes.constants.ProductType;
import org.mahkib.Sales_Taxes.interfaces.OrderEntry;
import org.mahkib.Sales_Taxes.services.ProductsEntry;
import org.mahkib.Sales_Taxes.services.TaxMethod;

import java.math.BigDecimal;

public class Tax implements OrderEntry {

	public static Tax create(ProductsEntry taxedEntry, TaxMethod tax, Money amount) {
		return new Tax(taxedEntry, tax, amount);
	}

	private final ProductsEntry taxedEntry;
	private final TaxMethod tax;
	private final Money amount;

	private Tax(ProductsEntry taxedEntry, TaxMethod tax, Money amount) {
		this.taxedEntry = taxedEntry;
		this.tax = tax;
		this.amount = amount;
	}


	@Override
	public Money getAmount() {
		return amount;
	}

	@Override
	public Products getProducts() {
		return taxedEntry.getProducts();
	}

	public String getProductName() {
		return getProducts().getName();
	}

	public String getTaxName() {
		return tax.getName();
	}

	public BigDecimal getTaxRate() {
		return tax.getRate();
	}

	public ProductType getProductType() {
		return (ProductType) getProducts().getType();
	}
}