package com.app.rystr;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
*   Dieses logische Dateisystem nutzt einen Teil des physikalischen Dateisystems, und
*   zwar im Benutzerverzeichnis ein Unterverzeichnis fs. Das Wurzelverzeichnis des logischen
    Dateisystems steht in der Objektvariablen root für die Methodenaufrufe bereit.
    In diesem fs-Verzeichnis werden alle Ressourcen gespeichert. Der Konstruktor prüft,
    ob dieses Verzeichnis existiert, und wenn nicht, legt er es an.
    Zu den Methoden:
    ## getFreeDiskSpace() liefert die freien Bytes. Da Path keine Methoden besitzt, um
                           den Speicherplatz zu erfragen, läuft die Abfrage über java.io.File.

    ## load(…) lädt eine Datei. Die Pfadangabe setzt sich aus dem root-Objekt und dem
               übergebenen Dateinamen zusammen. Die geprüfte Ausnahme wird aufgefangen
                und in eine UncheckedIOException gewrappt.

    ## store(…) speichert ein byte-Array und löst ebenfalls relativ den Pfad auf. Die Ausnahmebehandlung
                ist wie bei load(…).
 */

@Service
public class FileSystem {

    // Der Stammverzeichnispfad, in dem die Dateien gespeichert werden
    private final Path root = Paths.get(System.getProperty("user.home")).resolve("fs");

    // Konstruktor der Klasse FileSystem
    public FileSystem() {
        try {
            // Überprüfen, ob das Stammverzeichnis existiert. Wenn nicht, erstelle es.
            if (!Files.isDirectory(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            // Bei Fehlern während des Erstellens des Verzeichnisses wird eine UncheckedIOException geworfen.
            throw new UncheckedIOException(e);
        }
    }

    // Methode, um den freien Speicherplatz auf dem Laufwerk zu erhalten
    public long getFreeDiskSpace() {
        // Verwendung von toFile() und getFreeSpace(), um den freien Speicherplatz des Laufwerks zu erhalten.
        return root.toFile().getFreeSpace();
    }

    // Methode zum Laden von Dateiinhalten als Byte-Array
    public byte[] load(String filename) {
        try {
            // Lese alle Bytes aus der Datei, die durch den angegebenen Dateinamen unter dem Stammverzeichnis identifiziert wird.
            return Files.readAllBytes(root.resolve(filename));
        } catch (IOException e) {
            // Bei Fehlern während des Lesevorgangs wird eine UncheckedIOException geworfen.
            throw new UncheckedIOException(e);
        }
    }

    // Methode zum Speichern von Byte-Array-Daten in eine Datei
    public void store(String filename, byte[] bytes) {
        try {
            // Schreibe die Byte-Array-Daten in die Datei, die durch den angegebenen Dateinamen unter dem Stammverzeichnis identifiziert wird.
            Files.write(root.resolve(filename), bytes);
        } catch (IOException e) {
            // Bei Fehlern während des Schreibvorgangs wird eine UncheckedIOException geworfen.
            throw new UncheckedIOException(e);
        }
    }
}
