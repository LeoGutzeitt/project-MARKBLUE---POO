package MARKBLUE;

public class Contato {
    private String email;
    private String cel;
    private boolean ehZap;

    public Contato (String email, String cel, boolean ehZap) {
        this.email = email;
        this.cel = cel;
        this.ehZap = ehZap;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return cel;
    }
    public void setTel(String cel) {
        this.cel = cel;
    }
    public boolean isEhZap() {
        return ehZap;
    }
    public void setEhZap(boolean ehZap) {
        this.ehZap = ehZap;
    }
}
