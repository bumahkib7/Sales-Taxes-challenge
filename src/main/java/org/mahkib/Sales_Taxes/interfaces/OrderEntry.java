package org.mahkib.Sales_Taxes.interfaces;

import org.mahkib.Sales_Taxes.models.Money;
import org.mahkib.Sales_Taxes.models.Products;

public interface OrderEntry {
	Products getProducts();
	Money getAmount();

}