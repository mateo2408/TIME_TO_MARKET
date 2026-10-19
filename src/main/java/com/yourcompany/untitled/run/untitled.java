package com.yourcompany.untitled.run;

import org.openxava.util.*;

/**
 * Execute this class to start the application.
 * With OpenXava Studio/Eclipse: Right mouse button > Run As > Java Application
 */

public class untitled {

	public static void main(String[] args) throws Exception {
		if (Boolean.getBoolean("untitled.useHsqlDb")) {
			DBServer.start("untitled-db"); // Legacy mode for HSQLDB-based local development and tests
		}
		AppServer.run("untitled"); // Use AppServer.run("") to run in root context
	}

}
