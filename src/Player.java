//GROUP MEMBERS:
// MELINA BEHZADI NEJAD - 101447858
// MOBINASADAT ZARGARY - 101472495


public class Player {
    private final  String name;
    private char symbol;

    public Player(String name, char symbol) {
        this.name = name;
        setSymbol(symbol);
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    private void setSymbol(char symbol) {

        if (symbol == 'R' || symbol == 'Y') {
            this.symbol = symbol;
        } else {
            throw new IllegalArgumentException("Invalid symbol: " + symbol + ". Symbol must be 'R' (Red) or 'Y' (Yellow).");
        }
    }
}
