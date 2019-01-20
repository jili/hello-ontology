package com.joy.company.repository;

/**
 * Interface for generic operations on a repository for a specific type.
 * <p>
 * <b>Note</b>: Just an example for exercise, not well designed.
 * 
 * @author Joy Li <joooy.li@gmail.com>
 *
 * @param <T> the domain type the repository manages
 * @param <ID> the type of the id of the entity the repository manages
 */
public interface CustomRepository<T, ID> {
	ID save(T entity);

	void delete(ID id);
}
