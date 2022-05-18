import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.mahkib.Sales_Taxes.models.Order;
import org.mahkib.Sales_Taxes.models.Products;
import org.mahkib.Sales_Taxes.models.Receipt;
import org.mahkib.Sales_Taxes.models.Tax;
import org.mahkib.Sales_Taxes.services.ProductsEntry;
import org.mahkib.Sales_Taxes.services.TaxMethod;
import org.mahkib.Sales_Taxes.services.TaxMethodsPractice;

import static org.hamcrest.core.Is.is;
import static org.mahkib.Sales_Taxes.constants.ProductOrigin.IMPORTED;
import static org.mahkib.Sales_Taxes.constants.ProductOrigin.LOCAL;
import static org.mahkib.Sales_Taxes.constants.ProductType.*;
import static org.mahkib.Sales_Taxes.models.Money.dollars;
import static org.mahkib.Sales_Taxes.services.TaxExemptionEligibilityCheck.exempt;
import static org.mahkib.Sales_Taxes.services.TaxImportedEligibilityCheck.imported;

public class TestFactory {


	private final Mockery context = new JUnit4Mockery();

	private TaxMethodsPractice taxingPractice;
	private Products exemptLocal;
	private Products exemptImported;
	private Products taxedLocal;
	private Products taxedImported;

	@Before
	public void setUp() {
		setUpTaxes();
		setUpProducts();
	}

	private void setUpTaxes() {
		TaxMethod basicSalesTax = TaxMethod.create("BST", "0.10", exempt(BOOK, FOOD, MEDICAL));
		TaxMethod importDuty = TaxMethod.create("IMD", "0.05", imported(MEDICAL));
		taxingPractice = TaxMethodsPractice.create();
		taxingPractice.add(basicSalesTax);
		taxingPractice.add(importDuty);
	}

	private void setUpProducts() {
		exemptLocal = Products.create(1, "book", BOOK, LOCAL, dollars(String.valueOf(12.49)));
		exemptImported = Products.create(1, "music CD", MUSIC, IMPORTED, dollars(String.valueOf(14.99)));
		taxedLocal = Products.create(1, "chocolate bar", FOOD, LOCAL, dollars(String.valueOf(0.85)));
		taxedImported = Products.create(1, "imported box of chocolates", FOOD, IMPORTED, dollars(String.valueOf(10.00)));
	}

	@Test
	public void Purchase_Exempt_Local_Product() {
		Order order = Order.create(taxingPractice);
		order.add(exemptLocal, 1);
		MatcherAssert.assertThat(order.getSubTotal(), is(dollars("10")));
		MatcherAssert.assertThat(order.getTaxTotal(), is(dollars("1")));
		MatcherAssert.assertThat(order.getTotal(), is(dollars("11")));

	}


	@Test
	public void Purchase_Exempt_Imported_Product() {
		Order order = Order.create(taxingPractice);
		order.add(exemptImported, 1);
		MatcherAssert.assertThat(order.getSubTotal(), is(dollars("10")));
		MatcherAssert.assertThat(order.getTaxTotal(), is(dollars("1")));
		MatcherAssert.assertThat(order.getTotal(), is(dollars("11")));

	}

	@Test
	public void Purchase_Taxed_Local_Product() {
		Order order = Order.create(taxingPractice);
		order.add(taxedLocal, 1);
		MatcherAssert.assertThat(order.getSubTotal(), is(dollars("10")));
		MatcherAssert.assertThat(order.getTaxTotal(), is(dollars("1")));
		MatcherAssert.assertThat(order.getTotal(), is(dollars("11")));
	}

	@Test
	public void Purchase_Two_Taxed_Imported_Products() {
		Order order = Order.create(taxingPractice);
		order.add(taxedImported, 2);
		MatcherAssert.assertThat(order.getSubTotal(), is(dollars("20")));
		MatcherAssert.assertThat(order.getTaxTotal(), is(dollars("3"))); // 15%
		MatcherAssert.assertThat(order.getTotal(), is(dollars("23")));
	}


	@Test
	public void Purchase_Taxed_Imported_Product_And_Print_Receipt() {
		Order order = Order.create(taxingPractice);
		order.add(taxedImported, 1);

		final Receipt receipt = context.mock(Receipt.class);
		final Sequence printing = context.sequence("printing");
		context.checking(new Expectations() {{
			oneOf(receipt).start(); inSequence(printing);
			oneOf(receipt).printProduct(with(any(ProductsEntry.class))); inSequence(printing);
			oneOf(receipt).printTax(with(any(Tax.class))); inSequence(printing);
			oneOf(receipt).printTax(with(any(Tax.class))); inSequence(printing);
			oneOf(receipt).printSubTotal(dollars("10")); inSequence(printing);
			oneOf(receipt).printTaxTotal(dollars("1.50")); inSequence(printing);
			oneOf(receipt).printTotal(dollars("11.50")); inSequence(printing);
			oneOf(receipt).end(); inSequence(printing);
		}});

		order.print(receipt);
	}



	@Test
	public void Purchase_All_Products() {
		Order order = Order.create(taxingPractice);
		order.add(exemptLocal, 1);    // $0.00 tax
		order.add(exemptImported, 1); // $0.50 tax
		order.add(taxedLocal, 1);     // $1.00 tax
		order.add(taxedImported, 1);  // $1.50 tax
		// _________ +
		// TOTAL $3.00 tax

		MatcherAssert.assertThat(order.getSubTotal(), is(dollars("40")));
		MatcherAssert.assertThat(order.getTaxTotal(), is(dollars("3")));
		MatcherAssert.assertThat(order.getTotal(), is(dollars("43")));
	}

}