package at.jstrillinger.strillinger_uc_aufgabe_03;

public class Card {
    private boolean visible = false;
    private int value = -1;

    public Card(){}

    public Card(boolean visible, int value) {
        this.visible = visible;
        this.value = value;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "visible=" + visible +
                ", value=" + value +
                '}';
    }
}
