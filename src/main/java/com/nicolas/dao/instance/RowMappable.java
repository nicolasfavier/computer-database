package com.nicolas.dao.instance;

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
	public List<T> getList(ResultSet rs) throws SQLException;
	public T getObject(ResultSet rs) throws SQLException;
}
