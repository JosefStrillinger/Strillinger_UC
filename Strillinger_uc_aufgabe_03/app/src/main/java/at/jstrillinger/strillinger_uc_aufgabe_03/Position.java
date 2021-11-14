package at.jstrillinger.strillinger_uc_aufgabe_03;

public class Position {

    public int x;
    public int y;

    public Position(){}

    public Position(int xHelp, int yHelp){
        x = xHelp;
        y = yHelp;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }


}
