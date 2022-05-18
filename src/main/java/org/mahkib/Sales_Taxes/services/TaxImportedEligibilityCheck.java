package org.mahkib.Sales_Taxes.services;

import org.mahkib.Sales_Taxes.constants.ProductType;
import org.mahkib.Sales_Taxes.interfaces.OrderEntry;
import org.mahkib.Sales_Taxes.interfaces.TaxEligibilityCheck;
import org.mahkib.Sales_Taxes.models.Tax;

public class TaxImportedEligibilityCheck implements TaxEligibilityCheck {

	public static TaxImportedEligibilityCheck imported(ProductType type) {
		return new TaxImportedEligibilityCheck();
	}

	private TaxImportedEligibilityCheck() {
	}

	@Override
	public boolean isEligible(Tax tax) {
		return false;
	}

	@Override
	public boolean isEligible(OrderEntry entry) {
		return entry.getProducts().isImported();
	}

}