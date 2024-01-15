package com.app.rystr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
public class RystrApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(RystrApplication.class, args);

		FileSystem fs = ctx.getBean(FileSystem.class);

		/*
		 * Für die Konvertierung in Gigabytes würde man vermutlich mit einer Division arbeiten,
		 * aber das Schöne ist, dass das Spring Framework für Datengrößen einen eigenen
		 * Datentyp hat, und zwar DataSize. Die statische Fabrikmethode ofBytes(…) baut das
		 * DataSize-Objekt mit Bytes auf und toGigabytes() liefert die Ausgabe in Gigabytes.
		 */
		System.out.println(
				DataSize.ofBytes(fs.getFreeDiskSpace()).toGigabytes() + "GB"
		);

	}

}
