package org.mahkib.Sales_Taxes.interfaces;

import org.mahkib.Sales_Taxes.models.Money;
import org.mahkib.Sales_Taxes.models.Tax;

public interface ReceiptInterface {
	void start();
	<ProductEntry> void printProduct(ProductEntry entry);

	void printTax(Tax entry);
	void printProduct(ProductsEntry entry);
	void printSubTotal(Money subTotal);
	void printTaxTotal(Money taxTotal);
	void printTotal(Money total);
	void end();
}