package org.mahkib.Sales_Taxes;

import org.mahkib.Sales_Taxes.constants.ProductOrigin;
import org.mahkib.Sales_Taxes.constants.ProductType;
import org.mahkib.Sales_Taxes.models.Order;
import org.mahkib.Sales_Taxes.models.Products;
import org.mahkib.Sales_Taxes.models.Receipt;
import org.mahkib.Sales_Taxes.services.TaxMethod;
import org.mahkib.Sales_Taxes.services.TaxMethodsPractice;

import java.util.Scanner;

import static org.mahkib.Sales_Taxes.constants.ProductOrigin.IMPORTED;
import static org.mahkib.Sales_Taxes.constants.ProductOrigin.LOCAL;
import static org.mahkib.Sales_Taxes.constants.ProductType.*;
import static org.mahkib.Sales_Taxes.models.Money.dollars;
import static org.mahkib.Sales_Taxes.services.TaxExemptionEligibilityCheck.exempt;
import static org.mahkib.Sales_Taxes.services.TaxImportedEligibilityCheck.imported;

public class Presentation {
	public static void main(String[] args) {
		/*Forever loop*/
		TaxMethod basicSalesTax = TaxMethod.create("BST", "0.10", exempt(BOOK, FOOD, MEDICAL));
		TaxMethod importDuty = TaxMethod.create("IMD", "0.05", imported(MEDICAL));

		TaxMethodsPractice taxes = TaxMethodsPractice.create();

		taxes.add(basicSalesTax);
		taxes.add(importDuty);


		// DONE: Storing is not done well.
		Order order = Order.create(taxes);

		for (; ; ) {

			Scanner sc = new Scanner(System.in);
			ProductType productTypeFinal = null;
			ProductOrigin productOriginFinal = null;

			System.out.println("Enter status");
			String status = sc.next();
			if (status.equals("y")) {
				System.out.print("Enter Quantity: ");
				int quantity = sc.nextInt();
				System.out.print("Enter  Name: ");
				String name = sc.next();
				System.out.print("Enter product Type");
				String productType = sc.next();

				switch (productType) {
					case "BOOK" -> productTypeFinal = BOOK;
					case "MEDICAL" -> productTypeFinal = MEDICAL;
					case "OTHER" -> productTypeFinal = OTHER;
				}

				System.out.print("Enter product Origin");
				String productOrigin = sc.next();

				switch (productOrigin) {
					case "LOCAL" -> productOriginFinal = LOCAL;
					case "IMPORTED" -> productOriginFinal = IMPORTED;
				}

				System.out.print("Enter price: ");
				String price = sc.next();

				Products p = Products.create(quantity, name, productTypeFinal, productOriginFinal, dollars(String.valueOf(price)));
				order.add(p, quantity);

				System.out.println("Print receipt");
				String decide = sc.next();

				if (decide.equals("y")) {

					Receipt receipt = Receipt.create(System.out);
					order.print(receipt);

					break;
				}

			} else {
				System.out.println("Program exited.");
				break;
			}
		}
	}
}