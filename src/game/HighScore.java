package game;

/**
 * Klasa przechowujca jeden z najwyższych wyników
 *
 * @author Paweł Rutkowski
 */
public class HighScore implements Comparable<HighScore> {

    /**
     * Nazwa gracza który otrzymał dany wynik
     */
    String name;
    /**
     * Wynik gracza
     */
    int score;

    /**
     * Konstruktor domyślny
     *
     * @param name  Nazwa gracza który znalazł się na liście najlepszych wyników
     * @param score Liczba punktów osiągniętych przez gracza
     */
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Funkcja pozwalająca na porównywanie i sortowanie graczy
     */
    public int compareTo(HighScore high_score) {
        if (this.score < high_score.score) {
            return 1;
        } else if (this.score > high_score.score) {
            return -1;
        } else {
            return 0;
        }
    }
}
