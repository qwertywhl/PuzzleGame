package whl.ui;
import javax.swing.*;
public class LoginFrame extends JFrame{
    private String Name;
    private String PassWord;
    //创建一个登录界面
    public LoginFrame(){
        this.setSize(388,330);
        this.setVisible(true);
        this.setTitle("拼图 登录");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
    }
}
