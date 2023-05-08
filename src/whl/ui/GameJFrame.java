package whl.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //定义一个二维数组来存放当前位下的图片编号
    private int[][] data = new int[4][4];
    //找到空白图片所在的位置
    int x = 0, y = 0;

    String choice_1="animal";//美女、动物、运动
    int choice_2=2;//选择动物、美女或运动中那张图片
    int win[][]={{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
    int step=0;
    //放在这里方便后续比较动作监听
    JMenuItem replayGame = new JMenuItem("重新游戏");
    JMenuItem relog = new JMenuItem("重新登陆");
    JMenuItem closeGame = new JMenuItem("关闭游戏");
    JMenuItem accountGame = new JMenuItem("lucky");
    JMenuItem girl = new JMenuItem("美女");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");
    //空参构造，创建一个主界面
    public GameJFrame() {
        //初始化界面
        InitJFrame();
        //初始化菜单
        InitJMenuBar();
        //打乱图片
        InitData();
        //设置图片
        InitImage();
        //设置界面可视
        this.setVisible(true);
    }

    private void InitJFrame() {
        //设置界面宽高
        this.setSize(603, 680);
        //设置界面标题
        this.setTitle("拼图游戏单机版 v1.0");
        //设置游戏在界面最上方
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(3);
        //取消默认居中
        this.setLayout(null);
        this.addKeyListener(this);
    }

    private void InitJMenuBar() {
        //设置主菜单
        JMenuBar jMenuBar = new JMenuBar();
        //设置功能和关于我们
        JMenu functionMenu = new JMenu("功能");
        JMenu aboutMenu = new JMenu("关于我们");
        JMenu changeMenu=new JMenu("更换图片");

        //将JMenu添加到主菜单
        jMenuBar.add(functionMenu);
        jMenuBar.add(aboutMenu);

        //将JMenuItem添加到下拉框
        //功能
        functionMenu.add(changeMenu);
        functionMenu.add(replayGame);
        functionMenu.add(relog);
        functionMenu.add(closeGame);
        //关于我们
        aboutMenu.add(accountGame);
        //更换图片里再加
        changeMenu.add(girl);
        changeMenu.add(animal);
        changeMenu.add(sport);

        //给条目绑定事件
        replayGame.addActionListener(this);//重新游戏
        relog.addActionListener(this);//重新登陆
        closeGame.addActionListener(this);//关闭游戏
        girl.addActionListener(this);//美女
        animal.addActionListener(this);//动物
        sport.addActionListener(this);//运动

        //将JMenuBar添加到整个JFrame中
        this.setJMenuBar(jMenuBar);
    }

    private void InitData() {
        int tempArr[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        //获取随机数
        Random r = new Random();
        //打乱数组内容
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        //将数组元素添加到二维数组中
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }
    }

    private void InitImage() {
        //删除所有图片，重新更新
        this.getContentPane().removeAll();
        //显示步数
        JLabel stepLabel=new JLabel("步数："+step);
        stepLabel.setBounds(50,30,100,20);
        this.getContentPane().add(stepLabel);
        //若胜利，显示胜利图片
        if(victory()){
            JLabel winjLabel=new JLabel(new ImageIcon("D:\\develop\\代码\\PuzzleGame\\image\\win.png"));
            winjLabel.setBounds(203,283,193,73);
            this.getContentPane().add(winjLabel);
        }

        //循环遍历data数组
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = data[i][j];  //获取当前索引下的图片
                //新建图片，用容器装起来
                if (num != 0) {
                    JLabel jLabel = new JLabel(new ImageIcon("../PuzzleGame\\image\\"+choice_1+"\\"+choice_1+choice_2+"\\" + num + ".jpg"));
                    //设置图片坐标，以左上角方位为准
                    jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                    //给图片添加边框，0表示凸起来，1表示凹下去
                    jLabel.setBorder(new BevelBorder(1));
                    //获取JFrame内容面板，同时将图片容器添加到面板中
                    this.getContentPane().add(jLabel);
                }
            }
        }
        //先添加的图片会放在上方
        JLabel background = new JLabel(new ImageIcon("D:\\develop\\代码\\PuzzleGame\\image\\background.png"));
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);

        //刷新一下界面
        this.getContentPane().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //按下不松时，调用这个个方法
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 65) {       //长按A查看完整图片
            //删除所有照片
            this.getContentPane().removeAll();
            //加载第一张完整的图片
            JLabel all = new JLabel(new ImageIcon("../PuzzleGame\\image\\"+choice_1+"\\"+choice_1+choice_2+"\\"  + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            //加载背景照片
            JLabel background = new JLabel(new ImageIcon("D:\\develop\\代码\\PuzzleGame\\image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            //刷新界面
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //退出方法
        if(victory()){
            return;
        }
        int code = e.getKeyCode();
        //图片左移到空白位置
        if (code == 37) {
            if (y == 3) return;
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            step++;
            InitImage();
        } else if (code == 38) {//图片上移到空白位置
            if (x == 3) return;
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            step++;
            InitImage();
        } else if (code == 39) {//图片右移到空白位置
            if (y == 0) return;
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            step++;
            InitImage();
        } else if (code == 40) {//图片下移到空白位置
            if (x == 0) return;
            data[x][y] = data[x - 1][y];//将上面那张图片赋值到下方
            data[x - 1][y] = 0;
            x--;
            step++;
            InitImage();
        } else if (code == 65) {
            InitImage();
        }else if(code==87){
            data=new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
            InitImage();
        }
    }

    //判断data数组与win数组是否相同，若相同，返回true，否则返回false
    //循环遍历data和win
    public boolean victory(){
        for(int i=0;i<data.length;i++){
            for(int j=0;j<data[i].length;j++){
                if(data[i][j]!=win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前被点击的条目对象
        Object obj=e.getSource();
        //判断
        if(obj==replayGame){//重新游戏
            //计步器清零
            step=0;
            //重新打乱图片顺序
            InitData();
            //重新显示图片
            InitImage();
        }else if(obj==relog){//重新登陆
            this.setVisible(false);
            new LoginFrame();
        }else if(obj==closeGame){//关闭游戏
            System.exit(0);
        }else if(obj==accountGame){//关于我们下拉框
            //创建一个弹框
            JDialog jDialog=new JDialog();
            //创建一个容器装图片
            JLabel lucky=new JLabel(new ImageIcon("../PuzzleGame\\image\\lucky.jpg"));
            //设置位置和宽高
            lucky.setBounds(0,0,258,258);
            //图片添加到弹框中
            jDialog.getContentPane().add(lucky);
            //设置弹框大小
            jDialog.setSize(344,344);
            //设置弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭无法进行其他操作
            jDialog.setModal(true);
            jDialog.setVisible(true);
        }else if(obj==girl){
            //首先步数置0
            step=0;
            //第二步：设置随机数，选择哪张美女图片
            choice_1="girl";
            Random r=new Random();
            choice_2=r.nextInt(12)+1;
            //第三步：打乱图片顺序
            InitData();
            //第四步：显示图片
            InitImage();
        }else if(obj==animal){
            //首先步数置0
            step=0;
            //第二步：设置随机数，选择哪张美女图片
            choice_1="animal";
            Random r=new Random();
            choice_2=r.nextInt(7)+1;
            //第三步：打乱图片顺序
            InitData();
            //第四步：显示图片
            InitImage();
        }else if(obj==sport){
            //首先步数置0
            step=0;
            //第二步：设置随机数，选择哪张美女图片
            choice_1="sport";
            Random r=new Random();
            choice_2=r.nextInt(9)+1;
            //第三步：打乱图片顺序
            InitData();
            //第四步：显示图片
            InitImage();
        }
    }
}

