package com.nicolas.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	public default List<T> getList(ResultSet r) throws SQLException {
		List<T> objectList = new ArrayList<T>();
		while (r.next()) {
		T t = getObject(r);
		objectList.add(t);
		}
		return objectList;
		}
	
	/**
	 * get an object from a result set
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public T getObject(ResultSet rs) throws SQLException;
}
