package org.mahkib.Sales_Taxes.interfaces;


import org.mahkib.Sales_Taxes.models.Tax;

public interface TaxEligibilityCheck {
	boolean isEligible(Tax tax);

	boolean isEligible(OrderEntry entry);
}