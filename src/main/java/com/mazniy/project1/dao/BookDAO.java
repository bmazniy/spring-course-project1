package com.mazniy.project1.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.mazniy.project1.models.Book;

@Component
public class BookDAO {

	private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	public List<Book> index() {
		return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
	}

	@SuppressWarnings("deprecation")
	public Book show(int id) {
		return jdbcTemplate.query("SELECT * FROM books WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
	}

	public void save(Book book) {
		jdbcTemplate.update("INSERT INTO books (title, author, year) VALUES(?, ?, ?)", book.getTitle()
				, book.getAuthor(), book.getYear());
	 }

	public void update(int id, Book updatedBook) {
		jdbcTemplate.update("UPDATE books SET title=?, author=?, year=? WHERE id=?", updatedBook.getTitle(),
				updatedBook.getAuthor(), updatedBook.getYear(), id);
		
	}

	public void delete(int id) {
		 jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
		
	}

	public void release(int id) {
		jdbcTemplate.update("DELETE FROM Library_Data WHERE book_id=?", id);
		
	}

	@SuppressWarnings("deprecation")
	public boolean bookHasTaken(int id) {
		Book foundBook = jdbcTemplate.query("SELECT * FROM books \n"
				+ "	INNER Join library_Data \n"
				+ "		on books.id = library_data.book_id\n"
				+ "where library_data.book_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
        .stream().findAny().orElse(null);
		
		if (foundBook == null) {
			return false;
		} else {
			return true;
		}
	}

	public void takeABook(int bookId, int personId) {
		jdbcTemplate.update("insert into library_data (book_id, person_id) values (?, ?)", bookId, personId);		
	}

	@SuppressWarnings("deprecation")
	public List<Book> getPersonBook(int id) {
		return jdbcTemplate.query("SELECT * FROM books \n"
				+ "	INNER Join library_Data \n"
				+ "		on books.id = library_data.book_id\n"
				+ "where library_data.person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
	}

}
