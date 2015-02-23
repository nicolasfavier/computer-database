package com.nicolas.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 * 
 * @author nicolas
 *
 *map a row from the db to an object
 */
public interface RowMappable<T> {
	/**
	 * get a list of object from a result set
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<T> getList(ResultSet rs) throws SQLException;
	
	/**
	 * get an object from a result set
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public T getObject(ResultSet rs) throws SQLException;
}
