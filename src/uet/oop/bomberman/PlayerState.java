package uet.oop.bomberman;

public class PlayerState {
    private int level = 1;
    private int remainingTime = 120;
    private int score = 0;
    private int speed = 120;
    private int flame = 1;
    private int bomb = 1;
    private int life = 3;

    public void increaseFlame() {
        flame++;
    }

    public void increaseBomb() {
        bomb++;
    }

    public void increaseSpeed() {
        speed += 60;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFlame() {
        return flame;
    }

    public void setFlame(int flame) {
        this.flame = flame;
    }

    public int getBomb() {
        return bomb;
    }

    public void setBomb(int bomb) {
        this.bomb = bomb;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void minusLife() {
        life--;
    }
}
