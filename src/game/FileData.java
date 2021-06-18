package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Klasa czytająca pliki tekstowe używane w grze, w tym także pliki konfiguracyjnego przechowujące parametry gry
 *
 * @author Paweł Rutkowski
 */
public class FileData {

    /**
     * Zmienna typu string oznaczające nazwę pliku tekstowego,
     * który chcemy odczytać
     */
    private final String data_file_name;

    /**
     * Metoda pobierająca nazwę pliku tekstowego, który chcemy odczytać
     *
     * @param file_name Nazwa pliku tekstowego
     * @throws FileNotFoundException
     */
    public FileData(String file_name) throws FileNotFoundException {
        data_file_name = file_name;
    }

    /**
     * Metoda odpowiedzailna za szukanie w tekście podanego parametru
     *
     * @param line Nazwa poszukiwanej linii
     * @return Liczba linii, w których został znaleziony parametr bądź -1 jeśli nie znalazł
     * @throws IOException
     */
    int findInFile(String line) throws IOException {
        FileReader file_reader = new FileReader(data_file_name);
        BufferedReader buffered_reader = new BufferedReader(file_reader);
        int count = 0;
        String loaded_line;

        while ((loaded_line = buffered_reader.readLine()) != null) {
            count++;
            if (loaded_line.equals(line)) {
                buffered_reader.close();
                return count;
            }
        }

        buffered_reader.close();
        return 0;
    }

    /**
     * Metoda odpowiedzialna za odczytanie jednej linii pliku
     *
     * @param line Liczba linii do odczytania.
     * @return Odczytanie linii bądź zwrócenie 'null' jeśli parametr jest niezgodny
     * lub większy niż liczba linii
     * @throws IOException
     */
    String readOneLine(int line) throws IOException {
        FileReader file_reader = new FileReader(data_file_name);
        BufferedReader buffered_reader = new BufferedReader(file_reader);
        int count = 0;
        String loaded_line;

        while ((loaded_line = buffered_reader.readLine()) != null) {
            count++;
            if (count == line) {
                buffered_reader.close();
                return loaded_line;
            }
        }

        buffered_reader.close();

        return null;
    }

    /**
     * Pobieranie danych z pliku konfiguracyjnego.
     *
     * @param name Nazwa danej.
     * @return Nazwa danej.
     * @throws IOException
     */
    String getSetting(String name) throws IOException {
        return readOneLine(findInFile("$" + name) + 1);
    }

    /**
     * Funckja zliczająca wszystkie piłki w pliku konfiguracyjnym danego poziomu
     *
     * @return Funckja zwraca liczbę wszystkich piłek w pliku konfiguracyjnym poziomu
     * @throws IOException
     */
    int ballsInLevel() throws IOException {
        FileReader file_reader = new FileReader(data_file_name);
        BufferedReader buffered_reader = new BufferedReader(file_reader);
        int count = 0;
        String loaded_line;

        while ((loaded_line = buffered_reader.readLine()) != null) {
            if (loaded_line.contains("$BALL ")) {
                count++;
            }
        }
        buffered_reader.close();
        return count;
    }

    /**
     * Funkcja licząca wszystkie linie pliku
     *
     * @return Funkcja zwraca wszystkie linie pliku
     * @throws IOException
     */
    int linesInFile() throws IOException {
        FileReader file_reader = new FileReader(data_file_name);
        BufferedReader buffered_reader = new BufferedReader(file_reader);
        int count = 0;

        while ((buffered_reader.readLine()) != null) {
            count++;
        }

        buffered_reader.close();
        return count;
    }
}