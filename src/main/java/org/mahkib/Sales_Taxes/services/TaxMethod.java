package org.mahkib.Sales_Taxes.services;

import org.mahkib.Sales_Taxes.interfaces.OrderEntry;
import org.mahkib.Sales_Taxes.interfaces.TaxEligibilityCheck;
import org.mahkib.Sales_Taxes.models.Money;

import java.math.BigDecimal;

public class TaxMethod {

	public static TaxMethod create(String name, String rate) {
		return new TaxMethod(name, new BigDecimal(rate), null);
	}

	public static TaxMethod create(String name, String rate, TaxEligibilityCheck eligibilityCheck) {
		return new TaxMethod(name, new BigDecimal(rate), eligibilityCheck);
	}

	private final String name;
	private final BigDecimal rate;
	private final TaxEligibilityCheck eligibilityCheck;

	TaxMethod(String name, BigDecimal rate, TaxEligibilityCheck eligibilityCheck) {
		this.name = name;
		this.rate = rate;
		this.eligibilityCheck = eligibilityCheck;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public boolean isEligible(OrderEntry entry) {
		return eligibilityCheck == null || eligibilityCheck.isEligible(entry);
	}

	public Money calculate(Money amount) {
		return amount.multiply(rate);
	}
}
