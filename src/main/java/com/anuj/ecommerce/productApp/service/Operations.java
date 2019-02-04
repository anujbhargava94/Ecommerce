package com.anuj.ecommerce.productApp.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.anuj.ecommerce.productApp.model.Product;

@Service
public class Operations {

	static Map<Long, Product> productsStore = new HashMap<>();

	public Product addProduct(Product product) {
		return productsStore.put(product.getId(), product);
	}

	public Product updateProduct(Long product_id, Product product) {
		// TODO Auto-generated method stub
		if (productsStore == null || !productsStore.containsKey(product_id)) {
			return null;

		} else {
			Product productEntry = productsStore.get(product_id);
			productEntry.setAvailability(product.getAvailability());
			productEntry.setDiscountedPrice(product.getDiscountedPrice());
			productEntry.setRetailPrice(product.getRetailPrice());
		}

		return product;
	}

	public Product getProduct(Long product_id) {
		if (productsStore == null || !productsStore.containsKey(product_id)) {
			return null;
		} else {
			return productsStore.get(product_id);
		}
	}

	// first show available products based on there discounted
	// pricesorted desc then outofstock
	public List<Product> getProduct(String category) {
		List<Product> result = new ArrayList<Product>();

		for (Product entry : productsStore.values()) {
			if (entry.getCategory().equals(category)) {
				result.add(entry);
			}
		}
		result.sort(availabilityComparator);

		return null;
	}

	// sort based on discount price descending
	public List<Product> getProduct(String category, boolean availability) {
		List<Product> result = new ArrayList<Product>();

		for (Product entry : productsStore.values()) {
			if (entry.getCategory().equals(category) && entry.getAvailability()) {
				result.add(entry);
			}
		}
		result.sort(discountCentComparator);

		return null;
	}

	// ((retail-discounted)/retail)*100 sort descending
	public List<Product> getProduct() {
		List<Product> result = new ArrayList<Product>();
		result.addAll(productsStore.values());
		result.sort(idComparator);
		return null;
	}

	public static final Comparator<Product> idComparator = new Comparator<Product>() {

		@Override
		public int compare(Product o1, Product o2) {
			int result = (int) (o1.getId() - o2.getId());

			return result;
		}

	};

	/**
	 * availability discounted price id
	 */
	public static final Comparator<Product> availabilityComparator = new Comparator<Product>() {

		@Override
		public int compare(Product o1, Product o2) {
			int result = 1;
			if (o1.getAvailability() && !o2.getAvailability()) {
				result = 1;
			} else if (!o1.getAvailability() && o2.getAvailability()) {
				result = -1;
			} else {
				result = (int) (o1.getDiscountedPrice() - o2.getDiscountedPrice());
			}

			if (result == 0) {
				result = (int) (o1.getId() - o2.getId());
			}
			return result;
			// This will work because age is positive integer
		}

	};

	public static final Comparator<Product> discountCentComparator = new Comparator<Product>() {

		@Override
		public int compare(Product o1, Product o2) {
			int product1;
			int product2;

			product1 = (int) (((o1.getRetailPrice() - o1.getDiscountedPrice()) / o1.getRetailPrice()) / 100);
			product2 = (int) (((o2.getRetailPrice() - o2.getDiscountedPrice()) / o2.getRetailPrice()) / 100);
			int result = product1 - product2; // This will work because age is positive integer
			if (result == 0) {
				result = (int) (o1.getId() - o2.getId());
			}
			return result;
		}

	};

}
