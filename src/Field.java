public class Field {
    private char simbol; // x ou b
    public Field() {
        this.simbol = ' ';
    }
    public char getSimbol() {
        return this.simbol;
    };
    public void setSimbol(char simbol) {
        if (this.simbol == ' ') {
            this.simbol = simbol;
        } else {
            System.out.println("Campo já utilizado!");
        }
    }

}
