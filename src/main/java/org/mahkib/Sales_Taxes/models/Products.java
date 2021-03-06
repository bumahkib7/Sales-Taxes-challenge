package org.mahkib.Sales_Taxes.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mahkib.Sales_Taxes.constants.ProductOrigin;
import org.mahkib.Sales_Taxes.constants.ProductType;

@ToString
@Getter
@Setter

public class Products {


	public static Products create(int quantity, String name, ProductType type, ProductOrigin origin, Money price) {
		return new Products(quantity, name, type, origin, price);
	}

	private final String name;
	private final ProductType type;
	private final ProductOrigin origin;
	private final Money price;
	private final int quantity;

	private Products(int Quantity, String name, ProductType type, ProductOrigin origin, Money price) {
		this.name = name;
		this.type = type;
		this.origin = origin;
		this.price = price;
		this.quantity = Quantity;
	}

	public String getName() {
		return name;
	}

	public Object getType() {
		return type;
	}

	public Money getPrice() {
		return price;
	}

	public boolean isImported() {
		return origin == ProductOrigin.IMPORTED;
	}

	public int getQuantity() {
		return quantity;
	}

}