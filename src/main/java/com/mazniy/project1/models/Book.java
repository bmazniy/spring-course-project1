package com.mazniy.project1.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Book {
	
	private int id;
	
	@NotEmpty(message = "Name should not be empty")
	private String title;
	
	@NotEmpty(message = "Name should not be empty")
	private String author;
	
	@Min(value = 0, message = "Year should be greater than 1900")
	private int year;
	
	public Book() {
		
	}
	
	public Book(String title, String author, int year) {
		super();
		this.title = title;
		this.author = author;
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
