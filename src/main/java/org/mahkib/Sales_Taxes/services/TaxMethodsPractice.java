package org.mahkib.Sales_Taxes.services;

import org.mahkib.Sales_Taxes.interfaces.TaxingPractice;
import org.mahkib.Sales_Taxes.models.Money;
import org.mahkib.Sales_Taxes.models.Tax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaxMethodsPractice implements TaxingPractice {
	public static TaxMethodsPractice create() {
		return new TaxMethodsPractice();
	}

	private final List<TaxMethod> taxes = new ArrayList<>();

	public TaxMethodsPractice() {
	}

	public void add(TaxMethod tax) {
		taxes.add(tax);
	}

	@Override
	public Collection<Tax> apply(ProductsEntry entry) {
		List<Tax> entries = new ArrayList<>();
		for (TaxMethod tax : taxes) {
			if (tax.isEligible(entry)) {
				Tax taxEntry = apply(tax, entry);
				entries.add(taxEntry);
			}
		}
		return entries;
	}

	@Override
	public void calculateTax(ProductsEntry entry) {
		for (TaxMethod tax : taxes) {
			if (tax.isEligible(entry)) {
				apply(tax, entry);
			}
		}
	}

	@Override
	public void calculateReceipt(List<ProductsEntry> entries) {
		for (ProductsEntry entry : entries) {
			calculateTax(entry);
		}

	}

	private Tax apply(TaxMethod tax, ProductsEntry entry) {
		Money taxAmount = tax.calculate(entry.getAmount());
		return Tax.create(entry, tax, taxAmount);
	}

}
