package com.nicolas.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScriptRunner {

	public static void runScript() {
		String line;
		Process p;
		try {
			p = Runtime.getRuntime().exec("./script.sh");
		} catch (Exception err) {
			throw new RuntimeException(err);
		}
		BufferedReader input = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		try {
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
