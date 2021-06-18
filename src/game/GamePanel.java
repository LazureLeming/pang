package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Tworzenie okna gry
 *
 * @author Paweł Rutkowski
 */
public class GamePanel extends JPanel implements ActionListener {

    /**
     * Obiekt klasy Player
     */
    protected Player player;
    /**
     * Obiekt klasy GameBar (@see GameBar)
     */
    GameBar game_bar;
    /**
     * Obiekt klasy Timer
     */
    Timer timer;
    /**
     * Obiekt klasy Number reprezentujący liczbę puntków zdobytych przez gracza
     */
    Number points_number;
    /**
     * Obiekt klasy Controller
     */
    private final Controller control;
    /**
     * Obiekt klasy Bullet
     */
    private Bullet bullet;
    /**
     * Lista przechowująca wszystkie poziomy gry
     */
    private final List<Level> level_list;
    /**
     * Lista przechowująca wszystkie pociski występujące w danej chwili w grze
     */
    private final List<Bullet> bullet_list;
    /**
     * Lista przechowująca wszystkie bonusy występujące w danej chwili w grze
     */
    private final List<Bonus> bonus_list;
    /**
     * Lista przechowująca serca reprezentujące liczbę żyć gracza
     */
    private final List<Heart> heart_list;
    /**
     * Lista przechowująca tła serc reprezentujących liczbę żyć gracza
     */
    private final List<Heart> heart_background_list;
    /**
     * Zmienna określająca obecny poziom
     */
    private int current_level;
    /**
     * Zmienna pomocnicza, określa, czy przed chwilą był stworzony obiekt typy Bullet
     */
    private boolean bullet_created = false;
    /**
     * Zmienna pomocnicza, przechowuje liczbę punktów zdobytą przez gracza na początku poziomu, aby móc je przywrócić w razie potrzeby powtórzenia poziomu
     */
    private int score_flag;
    /**
     * Obiekt klasy Number reprezentujący number aktualnego poziomu
     */
    private final Number level_number;

    /**
     * Obiekt klasy Word, napis "POZIOM"
     */
    private final Word level_img;
    /**
     * Obiekt klasy Word, napis "PUNKTY"
     */
    private final Word points_img;


    /**
     * Tworzy elementy planszy oraz przyciski.
     *
     * @param parent Sprawia, że ten panel jest w stanie wywołać metody jego rodzica
     * @throws IOException
     */
    public GamePanel(MainMenu parent) throws IOException {

        control = new Controller();
        player = new Player();
        points_number = new Number(0);
        level_number = new Number(0);

        level_img = new Word(20, 100, ImageIO.read(new File("res/level.gif")));
        points_img = new Word(20, 120, ImageIO.read(new File("res/points.gif")));

        level_list = new ArrayList<Level>();
        bullet_list = new ArrayList<Bullet>();
        bonus_list = new ArrayList<Bonus>();
        heart_list = new ArrayList<Heart>();
        heart_background_list = new ArrayList<Heart>();

        for (int i = 0; i < player.lives; i++) {
            Heart heart = new Heart(HeartType.BACKGROUND);
            heart.x += i * 32;
            heart_background_list.add(heart);
        }

        current_level = 0;

        for (int i = 1; i < Configuration.levels + 1; i++) {
            Level level = new Level(i);
            level_list.add(level);
        }

        score_flag = 0;

        timer = new Timer(Configuration.timer, this);
        timer.start();

        startNewGame();

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 0, 0);

        game_bar = new GameBar(parent, this);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.NORTHEAST;
        add(game_bar, constraints);

        setOpaque(false);
        setFocusable(true);
        addKeyListener(control);
        setDoubleBuffered(true);
        requestFocusInWindow();
    }

    /**
     * Metoda wywoływana, kiedy zostanie wygenerowane zdarzenie na obiekcie powiązanym z danym słuchaczem
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        collisionPlayerBall();
        collisionBulletPlayer();
        collisionBonusPlayer();
        collisionBonusBullet();
        collisionBulletBall();

        playerShoot();

        movePlayer();

        moveBall();
        moveBullet();
        moveBonus();

        repaint();
    }

    /**
     * Metoda wstrzymująca/wznawiająca rozgrywkę
     */
    public void playPause() {
        if (timer.isRunning())
            timer.stop();
        else {
            timer.start();
            addKeyListener(control);
            requestFocusInWindow();
        }
    }

    /**
     * Metoda sprawdzająca, czy należy przejść do następnego etapu lub zakończyć grę
     */
    private void checkGameState() {
        if (level_list.get(current_level).ball_list.size() == 0) {
            if (current_level == Configuration.levels - 1) {
                endGame();
            } else {
                nextLevel();
            }
        }
    }

    /**
     * Metoda ustawiające piłki i gracza w pozycji początkowej danego poziomu
     */
    private void startConditions() {

        bullet_list.clear();

        score_flag = player.points;

        player.x = Configuration.width / 2 - player.width / 2;

        level_list.get(current_level).initialSettings(current_level + 1);

        level_number.updateList(this.current_level + 1);
    }

    /**
     * Metoda tworząca warunki nowej gry
     */
    void startNewGame() {
        current_level = 0;

        player.initialSettings();

        bullet_list.clear();

        bonus_list.clear();

        heart_list.clear();
        createHearts();

        level_list.get(current_level).initialSettings(current_level + 1);

        level_number.updateList(this.current_level + 1);
        points_number.updateList(player.points);
        repaint();
    }

    /**
     * Metoda pozwalająca na przejście do następnego poziomu
     */
    private void nextLevel() {
        player.points += 100;
        points_number.updateList(player.points);
        current_level++;
        startConditions();
        SoundEffect.NEXT_LEVEL.play();
    }

    /**
     * Metoda zajmująca się końcem gry
     */
    private void endGame() {
        if (player.lives <= 0) {
            timer.stop();
            SoundEffect.LOSE.play();
            JOptionPane.showMessageDialog(null, "Przegrałeś! Twój wynik: " + player.points, "Przegrałeś!", JOptionPane.INFORMATION_MESSAGE);
            startNewGame();
        }

        if (current_level == Configuration.levels - 1) {
            if (bonus_list.size() > 0) {
                for (int i = 0; i < bonus_list.size(); i++) {
                    bonus_list.get(i).grantBonus(player, this);
                }
            }

            SoundEffect.END_GAME.play();

            player.points += 100;
            player.points += 10 * player.lives;
            points_number.updateList(player.points);
            repaint();
            timer.stop();
            JOptionPane.showMessageDialog(null, "Wygrałeś! Twój wynik: " + player.points, "Wygrałeś!", JOptionPane.INFORMATION_MESSAGE);
            if (player.points > Score.score_list.get(0).score) {

                String name;

                JOptionPane.showMessageDialog(null, "Nowy najlepszy wynik!");
                name = JOptionPane.showInputDialog("Podaj imię: ");
                name = name.replace("$", "");
                HighScore high_score = new HighScore(name, player.points);
                Score.score_list.add(high_score);
                Score.sortScore();
                if (Score.score_list.size() > Configuration.number_of_scores) {
                    Score.score_list.remove(Score.score_list.size() - 1);
                } else {
                    Score.number_of_scores++;
                }
                Score.writeScore();
            } else if (player.points > Score.score_list.get(Score.score_list.size() - 1).score || Score.score_list.size() < Configuration.number_of_scores) {

                String name;

                JOptionPane.showMessageDialog(null, "Znalazłeś się na liście najlepszych wyników");
                name = JOptionPane.showInputDialog("Podaj imię: ");
                name = name.replace("$", "");
                HighScore high_score = new HighScore(name, player.points);
                Score.score_list.add(high_score);
                Score.sortScore();
                if (Score.score_list.size() > Configuration.number_of_scores) {
                    Score.score_list.remove(Score.score_list.size() - 1);
                } else {
                    Score.number_of_scores++;
                }
                Score.writeScore();
            } else {
                JOptionPane.showMessageDialog(null, "Niestety nie znalazłeś się na liście najlepszych wyników");
            }
            game_bar.changeIconToPlay();
            startNewGame();
        }
    }

    /**
     * Metoda zajmująca się kolizją piłki z graczem
     */
    private void collisionPlayerBall() {
        for (int i = 0; i < level_list.get(current_level).ball_list.size(); i++) {
            if (checkCollision(level_list.get(current_level).ball_list.get(i), player)) {
                if (!level_list.get(current_level).ball_list.get(i).hit_flag) {
                    SoundEffect.PLAYER_HIT.play();

                    if (!player.immunity) {
                        hitPlayer();
                    } else {
                        level_list.get(current_level).ball_list.get(i).hit_flag = true;
                        player.immunity = false;
                    }
                }
            } else {
                level_list.get(current_level).ball_list.get(i).hit_flag = false;
            }
        }
    }

    /**
     * Metoda zajmująca się kolizją pocisku z piłką
     */
    private void collisionBulletBall() {
        if (bullet_list.size() > 0) {
            for (int j = 0; j < bullet_list.size(); j++) {
                if (bullet_list.get(j).bullet_direction == Direction.UP) {
                    for (int i = 0; i < level_list.get(current_level).ball_list.size(); i++) {
                        if (checkCollision(level_list.get(current_level).ball_list.get(i), bullet_list.get(j))) {

                            bullet_list.remove(j);

                            createBonus(level_list.get(current_level).ball_list.get(i));

                            splitBall(level_list.get(current_level).ball_list.get(i));
                            ballShoot(level_list.get(current_level).ball_list.get(i));

                            level_list.get(current_level).ball_list.remove(i);

                            player.points += 10;
                            points_number.updateList(player.points);

                            if (level_list.get(current_level).ball_list.size() > 0) {
                                SoundEffect.EXPLOSION.play();
                            }

                            checkGameState();

                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Metoda zajmująca się zderzeniem pocisku z graczem
     */
    private void collisionBulletPlayer() {
        if (bullet_list.size() > 0) {
            for (int i = 0; i < bullet_list.size(); i++) {
                if (bullet_list.get(i).bullet_direction == Direction.DOWN) {
                    if (checkCollision(player, bullet_list.get(i))) {
                        SoundEffect.PLAYER_HIT.play();

                        if (!player.immunity) {
                            bullet_list.remove(i);
                            hitPlayer();
                        } else {
                            bullet_list.remove(i);
                            player.immunity = false;
                        }
                    }
                }
            }
        }
    }

    /**
     * Metoda zajmująca się zderzeniem bonusu z graczem
     */
    private void collisionBonusPlayer() {
        if (bonus_list.size() > 0) {
            for (int i = 0; i < bonus_list.size(); i++) {
                if (checkCollision(player, bonus_list.get(i))) {

                    bonus_list.get(i).grantBonus(player, this);

                    if (bonus_list.get(i).bonus_type == BonusType.LIFE) {
                        createHearts();
                    }

                    bonus_list.remove(i);

                    SoundEffect.BONUS.play();
                }
            }
        }
    }

    /**
     * Metoda zajmująca się kolizją pocisku z bonusem
     */
    private void collisionBonusBullet() {
        if (bonus_list.size() > 0 && bullet_list.size() > 0) {
            for (int i = 0; i < bonus_list.size(); i++) {
                for (int j = 0; j < bullet_list.size(); j++) {
                    if (checkCollision(bonus_list.get(i), bullet_list.get(j)) && bullet_list.get(j).bullet_direction == Direction.UP) {
                        bonus_list.get(i).grantBonus(player, this);

                        if (bonus_list.get(i).bonus_type == BonusType.LIFE) {
                            createHearts();
                        }

                        bonus_list.remove(i);
                        bullet_list.remove(j);

                        SoundEffect.BONUS.play();

                        break;
                    }
                }
            }
        }
    }

    /**
     * Funkcja sprawdzająca kolizję między dwoma obiektami
     *
     * @param a Obiekt typu GraphicsObject
     * @param b Obiekt typu GraphicsObject
     * @return Funkcja zwraca true gdy dochodzi do kolizji, lub false, gdy nie dochodzi do kolizji
     */
    private boolean checkCollision(GraphicsObject a, GraphicsObject b) {
        return a.x + a.width > b.x && a.x < (b.x + b.width) && a.y < b.y + b.height && a.y + a.height > b.y;
    }

    /**
     * Funkcja zajmująca się rozbiciem piłki
     *
     * @param b Obiekt typy Ball
     */
    private void splitBall(Ball b) {
        if (b.split > 0) {

            Ball ball1 = new Ball(b);
            Ball ball2 = new Ball(b);

            ball1.x += ball1.width / 2;
            ball1.y += ball1.height / 2;
            ball1.horizontal_speed += 1;
            ball1.width = (int) (ball1.width / 1.2);
            ball1.height = (int) (ball1.height / 1.2);
            ball1.ball_horizontal_direction = Direction.RIGHT;
            ball1.ball_vertical_direction = Direction.UP;
            ball1.split -= 1;
            ball1.shoots = false;
            try {
                ball1.setObjectImage();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ball2.x += ball2.width / 2;
            ball2.y += ball2.height / 2;
            ball2.horizontal_speed += 1;
            ball2.width = (int) (ball2.width / 1.2);
            ball2.height = (int) (ball2.height / 1.2);
            ball2.ball_horizontal_direction = Direction.LEFT;
            ball2.ball_vertical_direction = Direction.UP;
            ball2.split -= 1;
            ball2.shoots = false;
            try {
                ball2.setObjectImage();
            } catch (IOException e) {
                e.printStackTrace();
            }

            level_list.get(current_level).ball_list.add(ball1);
            level_list.get(current_level).ball_list.add(ball2);
        }
    }

    /**
     * Funkcja odpowiedzialna za wystrzelenie pocisku przez piłkę
     *
     * @param b Obiekt typu Ball wystrzeliwujący pocisk w stronę gracza
     */
    private void ballShoot(Ball b) {
        if (b.shoots) {
            bullet = new Bullet("DOWN");
            bullet.x = (int) (b.x + b.width / 2 - bullet.width / 2);
            bullet.y = (int) (b.y + b.height / 2);
            bullet.vertical_speed = (int) (bullet.vertical_speed / 3);
            bullet.bullet_direction = Direction.DOWN;
            bullet_list.add(bullet);

            SoundEffect.SHOOT.play();
        }
    }

    /**
     * Metoda odpowiedzialna za tworzenie bonusu
     *
     * @param g Obiekt który wytworzył bonus
     */
    private void createBonus(GraphicsObject g) {
        Random random_generator = new Random();
        int random_bonus_chance = random_generator.nextInt(100) + 1;
        int random_bonus_type = random_generator.nextInt(3);

        if (random_bonus_chance <= level_list.get(current_level).bonus_chance) {
            if (random_bonus_type == 0) {
                Bonus bonus = new Bonus("LIFE");
                bonus.x = g.x;
                bonus.y = g.y;
                bonus_list.add(bonus);
            } else if (random_bonus_type == 1) {
                Bonus bonus = new Bonus("POINTS");
                bonus.x = g.x;
                bonus.y = g.y;
                bonus_list.add(bonus);
            } else if (random_bonus_type == 2) {
                Bonus bonus = new Bonus("IMMUNITY");
                bonus.x = g.x;
                bonus.y = g.y;
                bonus_list.add(bonus);
            }
        }
    }

    /**
     * Ustala rozmiar okna
     */
    public Dimension getPreferredSize() {
        return new Dimension(getParent().getSize());
    }

    /**
     * Metoda odpowiedzialna za ruch gracza oraz ograniczenia jego ruchu wynikające z wielkości okna gry
     */
    private void movePlayer() {
        if (control.right) {
            player.x += player.horizontal_speed;
        }

        if (control.left) {
            player.x -= player.horizontal_speed;
        }

        if (player.x <= 0) {
            player.x = 0;
        }
        if (player.x * getSize().getWidth() / Configuration.width >= (getSize().getWidth() - player.width * getSize().getWidth() / Configuration.width)) {
            player.x = (int) (Configuration.width - player.width);
        }
    }

    /**
     * Metoda odpowiedzialna za wystrzelenie pocisku przez gracza
     */
    private void playerShoot() {
        if (control.shoot) {
            if (!bullet_created) {
                bullet = new Bullet("UP");
                bullet.x = (int) (player.x + (player.width / 2) - (bullet.width / 2));
                bullet.y = Configuration.height - player.height;
                bullet_list.add(bullet);

                SoundEffect.SHOOT.play();
            }

            bullet_created = true;
        } else {
            bullet_created = false;
        }
    }

    /**
     * Metoda odpisująca konsekwencje uderzenie pilki, lub pocisku w gracza
     */
    private void hitPlayer() {
        if (player.immunity) {
            player.immunity = false;
        } else {
            if (player.points >= 10) {
                player.points -= 10;
                points_number.updateList(player.points);
            }
            player.lives--;

            createHearts();

            player.points = score_flag;
            points_number.updateList(player.points);

            if (player.lives <= 0) {
                endGame();
            }
            bonus_list.clear();
            startConditions();
        }
    }

    /**
     * Metoda odpowiedzialna za ruch pocisków
     */
    private void moveBullet() {
        if (bullet_list.size() > 0) {
            for (int i = 0; i < bullet_list.size(); i++) {
                if (bullet_list.get(i).y >= 0 && bullet_list.get(i).y <= Configuration.height - bullet_list.get(i).height) {
                    if (bullet_list.get(i).bullet_direction == Direction.UP) {
                        bullet_list.get(i).y -= bullet_list.get(i).vertical_speed;
                    } else {
                        bullet_list.get(i).y += bullet_list.get(i).vertical_speed;
                    }
                } else if (bullet_list.get(i).bullet_direction == Direction.UP) {
                    bullet_list.remove(i);
                    if (player.points > 0) {
                        player.points -= 1;
                        points_number.updateList(player.points);
                    }
                } else {
                    bullet_list.remove(i);
                }
            }
        }
    }

    /**
     * Metoda zajmująca się ruchem piłek
     */
    private void moveBall() {
        if (level_list.get(current_level).ball_list.size() > 0) {
            for (int i = 0; i < level_list.get(current_level).ball_list.size(); i++) {
                checkBallPosition(level_list.get(current_level).ball_list.get(i));
            }
        }
    }

    /**
     * Metoda zajmująca się ruchem bonusów
     */
    private void moveBonus() {
        if (bonus_list.size() > 0) {
            for (int i = 0; i < bonus_list.size(); i++) {
                if (bonus_list.get(i).y >= Configuration.height - bonus_list.get(i).height) {
                    bonus_list.get(i).y = Configuration.height - bonus_list.get(i).height;
                } else {
                    bonus_list.get(i).y += bonus_list.get(i).vertical_speed;
                }
            }
        }
    }

    /**
     * Metoda obliczająca pozycję piłki
     *
     * @param b Obiekt typu Ball którego pozycja jest obliczana
     */
    private void checkBallPosition(Ball b) {
        //Wpółrzędne X
        if (b.x >= 0 && b.x <= (Configuration.width - b.width) && b.ball_horizontal_direction == Direction.RIGHT) {
            b.x += b.horizontal_speed;
        } else if (b.x >= 0 && b.x <= (Configuration.width - b.width) && b.ball_horizontal_direction == Direction.LEFT) {
            b.x -= b.horizontal_speed;
        } else if (b.x <= 0) {
            b.ball_horizontal_direction = Direction.RIGHT;
            b.x += b.horizontal_speed;
        } else if (b.x >= (Configuration.width - b.width)) {
            b.ball_horizontal_direction = Direction.LEFT;
            b.x -= b.horizontal_speed;
        }

        //Współrzędne Y
        if (b.y <= (Configuration.height - b.height) && b.ball_vertical_direction == Direction.DOWN) {
            b.vertical_speed = b.vertical_speed + level_list.get(current_level).gravity;
            b.y += b.vertical_speed;
        } else if (b.y <= (Configuration.height - b.height) && b.ball_vertical_direction == Direction.UP) {
            if (b.vertical_speed > 0) {
                b.vertical_speed = b.vertical_speed - level_list.get(current_level).gravity;
                b.y -= b.vertical_speed;
            } else {
                b.ball_vertical_direction = Direction.DOWN;
                b.y += b.vertical_speed;
            }
        } else if (b.y >= (Configuration.height - b.height)) {
            b.ball_vertical_direction = Direction.UP;
            b.y -= b.vertical_speed;
        }
    }

    /**
     * Metoda tworząca odpowiednią liczbę serc do narysowania
     */
    private void createHearts() {
        heart_list.clear();

        for (int i = 0; i < player.lives; i++) {
            Heart heart = new Heart(HeartType.LIFE);
            heart.x += i * 32;
            heart_list.add(heart);
        }
    }

    /**
     * Metoda rysująca postać gracza i piłki w oknie gry
     */
    public void paintComponent(Graphics g) {

        game_bar.repaint();
        getParent().repaint();

        Graphics2D player_image = (Graphics2D) g;
        player.draw(player_image, getWidth(), getHeight());

        if (bullet_list.size() > 0) {
            for (int i = 0; i < bullet_list.size(); i++) {
                Graphics2D bullet = (Graphics2D) g;
                bullet_list.get(i).draw(bullet, getWidth(), getHeight());
            }
        }

        if (bonus_list.size() > 0) {
            for (int i = 0; i < bonus_list.size(); i++) {
                Graphics2D bonus_image = (Graphics2D) g;
                bonus_list.get(i).draw(bonus_image, getWidth(), getHeight());
            }
        }

        if (level_list.get(current_level).ball_list.size() > 0) {
            for (int i = 0; i < level_list.get(current_level).ball_list.size(); i++) {
                Graphics2D ball = (Graphics2D) g;
                level_list.get(current_level).ball_list.get(i).draw(ball, getWidth(), getHeight());
            }
        }

        if (heart_background_list.size() > 0) {
            for (int i = 0; i < heart_background_list.size(); i++) {
                Graphics2D heart_background = (Graphics2D) g;
                heart_background_list.get(i).draw(heart_background, getWidth(), getHeight());
            }
        }

        if (heart_list.size() > 0) {
            for (int i = 0; i < heart_list.size(); i++) {
                Graphics2D heart = (Graphics2D) g;
                heart_list.get(i).draw(heart, getWidth(), getHeight());
            }
        }

        Graphics2D level_image = (Graphics2D) g;
        level_img.draw(level_image, 10, 50, getWidth(), getHeight());

        Graphics2D level_number_image = (Graphics2D) g;
        level_number.draw(level_number_image, 130, 50, getWidth(), getHeight());

        Graphics2D points_image = (Graphics2D) g;
        points_img.draw(points_image, 10, level_img.height + 60, getWidth(), getHeight());

        Graphics2D points_number_image = (Graphics2D) g;
        points_number.draw(points_number_image, 150, level_img.height + 60, getWidth(), getHeight());

        requestFocusInWindow();
    }

    /**
     * Funkcja rysująca tło aktualnego poziomu
     */
    public void paint(Graphics g) {
        Graphics2D back = (Graphics2D) g;
        back.drawImage(level_list.get(current_level).background, 0, 0, getWidth(), getHeight(), null);
        super.paint(g);
    }
}	