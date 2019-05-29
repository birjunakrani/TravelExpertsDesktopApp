package SwitchBoard;

import org.apache.commons.codec.digest.DigestUtils;

public class AgentLogIn {

    public String username;
    public String password;


    public AgentLogIn(String username, String password) {
        this.username = username;
       // this.password = DigestUtils.shaHex(password);
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.shaHex(password);
    }
}
