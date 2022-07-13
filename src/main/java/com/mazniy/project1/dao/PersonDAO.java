package com.mazniy.project1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.mazniy.project1.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    @SuppressWarnings("deprecation")
	public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (fullname, year) VALUES(?, ?)", person.getFullName(), person.getYear());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET fullname=?, year=? WHERE id=?", updatedPerson.getFullName(),
                updatedPerson.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

	@SuppressWarnings("deprecation")
	public Person showByBookId(int bookId) {
		return jdbcTemplate.query("SELECT * FROM person \n"
				+ "	INNER Join library_Data \n"
				+ "		on person.id = library_data.person_id\n"
				+ "where library_data.book_id = ?", new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
	}

	@SuppressWarnings("deprecation")
	public boolean personTakeABook(int id) {
		Person foundPerson = jdbcTemplate.query("SELECT * FROM person \n"
				+ "	INNER Join library_Data \n"
				+ "		on person.id = library_data.person_id\n"
				+ "where library_data.person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
        .stream().findAny().orElse(null);
		
		if (foundPerson == null) {
			return false;
		} else {
			return true;
		}
	}
}
