package org.mahkib.Sales_Taxes.interfaces;

import org.mahkib.Sales_Taxes.models.Tax;
import org.mahkib.Sales_Taxes.services.ProductsEntry;

import java.util.Collection;
import java.util.List;

public interface TaxingPractice {

	Collection<Tax> apply(ProductsEntry entry);


	void calculateTax(ProductsEntry entry);

	void calculateReceipt(List<ProductsEntry> entries);


}