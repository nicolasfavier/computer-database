package com.nicolas.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Utils {
	public static Timestamp getTimestamp(LocalDate ld){
		if (ld == null)
			return null;
		Instant instantIntroduced = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return Timestamp.from(instantIntroduced);
	}
	
	public static LocalDate getLocalDate(Timestamp ts){
		LocalDate ld = null;
		if (ts != null)
			ld = ts.toLocalDateTime().toLocalDate();
		return ld;
	}	
}
