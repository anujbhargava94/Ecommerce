package com.anuj.ecommerce.productApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anuj.ecommerce.productApp.model.Product;
import com.anuj.ecommerce.productApp.service.Operations;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

	@Autowired
	Operations operations;

	@RequestMapping(method = RequestMethod.POST, value = "")
	public ResponseEntity createProduct(@RequestBody Product product) {// Welcome page, non-rest

		Product result = operations.addProduct(product);

		if (result == null) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{product_id}")
	public ResponseEntity updateProduct(@RequestBody Product product, @PathVariable("product_id") Long product_id) {// Welcome
																													// page,
																													// non-rest

		Product result = operations.updateProduct(product_id, product);

		if (result == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/{product_id}")
	public ResponseEntity findProductById(@PathVariable("product_id") Long product_id) {// Welcome
																						// page,
																						// non-rest

		Product result = operations.getProduct(product_id);

		if (result == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "", params = "category")
	public ResponseEntity findProductByCategory(@RequestParam("category") String category) {// Welcome
		// page,
		// non-rest

		List<Product> result = operations.getProduct(category); // first show available products based on there
																// discounted
		// pricesorted desc then outofstock

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "", params = { "category", "availability" })
	public ResponseEntity findProductByCategoryAndAvailability(@RequestParam("category") String category,
			@RequestParam("availability") boolean availability) {// Welcome
		// page,
		// non-rest

		List<Product> result = operations.getProduct(category, availability); // sort based on discount price descending

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "")
	public ResponseEntity getAllProducts() {// Welcome
		// page,
		// non-rest

		List<Product> result = operations.getProduct(); // (retail-discounted)/retail*100 sort descending

		if (result == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
