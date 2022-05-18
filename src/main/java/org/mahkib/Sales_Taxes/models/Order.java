package org.mahkib.Sales_Taxes.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;




@Getter
@Setter
@ToString
@NoArgsConstructor




public class Order {

	public static Order create(TaxingPractice taxingPractice) {
		return new Order(taxingPractice);
	}

	private  TaxingPractice taxingPractice;
	private final List<ProductsEntry> entries = new ArrayList<>();
	private final List<Tax> taxEntries = new ArrayList<>();

	public Order(TaxingPractice taxingPractice) {
		this.taxingPractice = taxingPractice;
	}

	public void add(Products product, int quantity) {
		ProductsEntry entry = ProductsEntry.create(product, product.getPrice(), quantity);
		entries.add(entry);
		taxEntries.addAll(taxingPractice.apply(entry));
	}

	public Money getSubTotal() {
		return getTotal(entries);
	}

	public Money getTaxTotal() {
		return getTotal(taxEntries);
	}

	public Money getTotal() {
		return getSubTotal().add(getTaxTotal());
	}

	private Money getTotal(Iterable<? extends OrderEntry> entries) {
		Money total = dollars("0.00");
		for (OrderEntry entry : entries) {
			total = total.add(entry.getAmount());
		}
		return total;
	}

	public void print(Receipt receipt) {
		receipt.start();
		for (ProductsEntry entry : entries) receipt.printProduct(entry);
		for (Tax entry : taxEntries) receipt.printTax(entry);
		receipt.printSubTotal(getSubTotal());
		receipt.printTaxTotal(getTaxTotal());
		receipt.printTotal(getTotal());
		receipt.end();
	}


	public Receipt getReceipt() {
		Receipt receipt = new Receipt( this.getReceiptEntries());
		print(receipt);
		return receipt;
	}

	private PrintStream getReceiptEntries() {
		return null;
	}

	public void add(ProductsEntry entry) {
		entries.add(entry);
		taxEntries.addAll(taxingPractice.apply(entry));
	}
}