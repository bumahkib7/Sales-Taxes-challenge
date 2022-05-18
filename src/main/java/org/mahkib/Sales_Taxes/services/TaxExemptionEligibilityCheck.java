package org.mahkib.Sales_Taxes.services;

import org.mahkib.Sales_Taxes.constants.ProductType;
import org.mahkib.Sales_Taxes.interfaces.OrderEntry;
import org.mahkib.Sales_Taxes.interfaces.TaxEligibilityCheck;
import org.mahkib.Sales_Taxes.models.Tax;

import java.util.List;

public class TaxExemptionEligibilityCheck implements TaxEligibilityCheck {

	private final List<ProductType> types;

	private TaxExemptionEligibilityCheck(ProductType[] types) {
		this.types = List.of(types);
	}

	public static TaxExemptionEligibilityCheck exempt(ProductType... types) {
		return new TaxExemptionEligibilityCheck(types);
	}

	@Override
	public boolean isEligible(Tax tax) {
		return false;
	}

	@Override
	public boolean isEligible(OrderEntry entry) {
		return !types.contains(entry.getProducts().getType());
	}
}
