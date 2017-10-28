import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

/**
 * 计算器
 * 2017/10/20
 */


public class Calculator extends JFrame
        implements ActionListener
{
    //顶部和底部面板
    private JPanel panelTop;
    private JPanel panelButton;

    //程序宽度和高度
    private int WIDTH;
    private int HEIGHT;

    //显示框
    private JLabel label;

    //按钮
    private JButton[] btn;

    //菜单
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;

    //按钮的数字
    private String[] numbers = {"√", "x²", "n!", "7", "8", "9", "4", "5", "6", "1", "2", "3", "±", "0", "."};

    /**
     * 构造器
     */
    public Calculator()
    {
        //布局
        init();

        //添加菜单点击事件
        addMenuBarClick();

        //设置关闭模式
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口可见
        setVisible(true);

    }

    /**
     * 初始化窗口
     */
    private void init()
    {
        //设置窗口布局
        setLayout(new BorderLayout());

        //标题
        setTitle("计算器");

        //设置窗口不可改变大小
        setResizable(false);

        //设置窗口图标
        Image image = new ImageIcon("计算器.png").getImage();
        setIconImage(image);

        //设置自适应屏幕
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        //获取屏幕的宽度
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        //本窗口大小
        WIDTH = screenWidth / 4 - screenWidth / 60;
        HEIGHT = screenHeight / 2 + screenHeight / 4;

        //设置窗口大小
        setSize(WIDTH, HEIGHT);

        //设置窗口显示位置，居中显示
        setLocation(screenWidth/2-WIDTH/2, screenHeight/2-HEIGHT/2);


        //创建顶部面板
        panelTop = new JPanel(new BorderLayout());
//        panelTop.setLayout(null);
//        panelTop.setBackground(Color.RED);
//        panelTop.setOpaque(true);

        /**
         *
         */
        label = new JLabel("0");
        label.setFont(new Font("微软雅黑", Font.BOLD, 40));

        //设置顶部大小
        panelTop.setPreferredSize(new Dimension(WIDTH, HEIGHT / 4));


        //设置显示框内容靠右
        label.setHorizontalAlignment(JLabel.RIGHT);
//        label.setBorder();
//        label.setBackground(Color.BLUE);
//        label.setOpaque(true);

        //设置显示框大小
        label.setPreferredSize(new Dimension(WIDTH, HEIGHT / 8));
        /**
         * /4  /8  /5
         */
        //设置显示框的边框
        label.setBorder(BorderFactory.createEmptyBorder(0,16,0,16));

        //添加显示框到顶部面板
        panelTop.add(label, BorderLayout.SOUTH);

        //创建菜单
        menuBar = new JMenuBar();
        menu = new JMenu("选项");
        menuItem = new JMenuItem("关于");

        //将菜单添加到顶部面板
        setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(menuItem);

        //创建底部面板
        panelButton = new JPanel(new GridLayout(6, 4));

        //创建按钮
        btn = new JButton[24];
        for (int i = 0; i < btn.length; i++)
        {
            btn[i] = new JButton();
            btn[i].setBackground(new Color(225,225, 225));
            btn[i].setFocusPainted(false); //设置无焦点边框
            btn[i].setBorder(null); //取消按钮的边框
        }

        //设置个别按钮颜色
        btn[0].setBackground(new Color(241, 241, 241));
        btn[1].setBackground(new Color(241, 241, 241));
        btn[2].setBackground(new Color(241, 241, 241));
        btn[3].setBackground(new Color(241, 241, 241));
        btn[23].setBackground(new Color(255, 148, 2));

        //给按钮设置字体及样式
        for (int i = 4,j = 0; i < btn.length; i++)
        {
            if (i!=7&&i!=11&&i!=15&&i!=19&&i!=23)
            {
                btn[i].setText(numbers[j]);
                btn[i].setFont(new Font("微软雅黑", Font.BOLD, 21));
                j++;
            }
        }

        //给特殊按钮设置图标
        btn[3].setIcon(new ImageIcon("退格.png"));
        btn[7].setIcon(new ImageIcon("除号.png"));
        btn[11].setIcon(new ImageIcon("乘号.png"));
        btn[15].setIcon(new ImageIcon("减号.png"));
        btn[19].setIcon(new ImageIcon("加号.png"));
        btn[0].setIcon(new ImageIcon("清空.png"));
//        btn[1].setIcon(new ImageIcon("百分号.png"));
        btn[2].setIcon(new ImageIcon("pi.png"));
        btn[23].setIcon(new ImageIcon("等于.png"));
        btn[1].setText("1/X");
        btn[1].setFont(new Font("微软雅黑", Font.BOLD, 14));


        //按钮添加到面板
        for (int i = 0; i < btn.length; i++)
        {
            panelButton.add(btn[i]);
        }


        //添加面板到窗口
        add(panelTop, BorderLayout.NORTH);
        add(panelButton, BorderLayout.CENTER);

    }

    /**
     * 菜单栏添加点击事件
     */
    private void addMenuBarClick()
    {
        menuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(Calculator.this, "说明："+"\n"+"支持100以内的阶乘运算，支持浮点数和负数的四则运算。" + "\n" +"by yuanshijia","计算器1.0", JOptionPane.INFORMATION_MESSAGE );

            }
        });

        //给所有按钮添加监听事件
        for (int i = 0; i < btn.length; i++)
        {
            btn[i].addActionListener(this);
        }

    }


    /**
     * 点击事件
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //获取按钮的变量名
        Object source = e.getSource();
        //获取按钮内容
        String s = e.getActionCommand();

        String oldStr = null;

        /**
         * 如果按钮有内容，判断是否是数字按钮
         */
        if (!s.equals(""))
        {
            //数字按钮
            if (s.compareTo("0")>=0&&s.compareTo("9")<=0 && !s.contains("X"))
            {
                oldStr = label.getText();
                //如果显示框有内容
                if (!oldStr.equals("0"))
                {
                    if (oldStr.length() < 14)
                    {
                        label.setText(label.getText()+s);
                        isTextToLong();
                    }
                }
                else
                {
                    label.setText(s);
                }
            }
            //.小数点
            else if (s.equals("."))
            {
                oldStr = label.getText();
                System.out.println(oldStr);
                if (oldStr.equals("0"))
                {
                    label.setText("0.");
                }
                else if (isLastTextIsNum(oldStr) && oldStr.length()<14)
                {
                    boolean ok = true;
                    //保证小数点只能同时出现一次
                    for (int i = 0; i < oldStr.length(); i++)
                    {
                        if (oldStr.charAt(i)=='+' || oldStr.charAt(i)=='-' || oldStr.charAt(i)=='×' || oldStr.charAt(i)=='÷')
                        {
                            ok = true;
                        }
                        else if (oldStr.charAt(i) == '.')
                        {
                            ok = false;
                        }
                    }

                    if (ok)
                    {
                        label.setText(oldStr + ".");
                        isTextToLong();
                    }
                }
            }
            //±正负号
            else if (s.equals("±"))
            {
                oldStr = label.getText();
                if (!oldStr.equals("0"))
                {
                    //最后一个字符要是数字才能操作
                    if (isLastTextIsNum(oldStr))
                    {
                        oldStr = label.getText();
                        int i;
                        for (i=oldStr.length()-1;i>=0;i--)
                        {
                            if (oldStr.charAt(i) == '+' || oldStr.charAt(i) == '-' || oldStr.charAt(i) == '×' || oldStr.charAt(i) == '÷')
                            {
                                break;
                            }
                        }
                        //一个正数情况，如89
                        if (i == -1)
                        {
                            oldStr = "-" + oldStr;
                        }
                        //一个负数情况，如-89
                        else if (i==0)
                        {

                            oldStr = oldStr.substring(1);
                        }
                        //有运算符情况
                        else
                        {
                            //运算符不是-号，如8 + 3 的情况
                            if (oldStr.charAt(i) != '-')
                            {
                                oldStr = oldStr.substring(0, i+1) + "-" + oldStr.substring(i+1, oldStr.length());
                            }
                            //有-号，分下面三种情况
                            //6 - 3
                            //6 - -3
                            //6 + -3
                            else
                            {
                                if (oldStr.charAt(i-1) >= '0' && oldStr.charAt(i-1) <= '9' && oldStr.charAt(i+1) >= '0' && oldStr.charAt(i+1) <= '9')
                                {
                                    oldStr = oldStr.substring(0, i) + "-" + oldStr.substring(i, oldStr.length());
                                }
                                else if (oldStr.charAt(i + 1) >= '0' && oldStr.charAt(i + 1) <= '9' && oldStr.charAt(i - 1) == '-')
                                {
                                    oldStr = oldStr.substring(0, i) + oldStr.substring(i + 1, oldStr.length());
                                }
                                else if ((oldStr.charAt(i + 1) >= '0' && oldStr.charAt(i + 1) <= '9')&&(oldStr.charAt(i-1) == '+' || oldStr.charAt(i-1) == '×') || oldStr.charAt(i-1) == '÷')
                                {
                                    oldStr = oldStr.substring(0, i) + oldStr.substring(i + 1, oldStr.length());

                                }

                            }
                        }
                        label.setText(oldStr);
                        System.out.println(oldStr);
                        isTextToLong();
                    }
                }
            }
            //平方
            else if (s.equals("x²"))
            {
                oldStr = label.getText();
                if (!oldStr.equals("0") && !oldStr.contains("+") && !oldStr.contains("×") && !oldStr.contains("÷") && !oldStr.contains("%"))
                {
                    BigDecimal bigDecimal = new BigDecimal(oldStr);
                    bigDecimal = bigDecimal.multiply(bigDecimal);
                    oldStr = bigDecimal.toPlainString().toString();
                    System.out.println(bigDecimal.toString());
                    label.setText(oldStr);
                    isTextToLong();
                }
            }
            //n阶乘
            else if (s.equals("n!"))
            {
                oldStr = label.getText();

                if (oldStr.equals("0")) //0的阶乘为1
                {
                    label.setText("1");
                }
                //不含运算符和小数点
                else if (!oldStr.contains(".") && !isContainOperator(oldStr))
                {
                    //仅支持100以下阶乘
                    if(Integer.parseInt(oldStr) <= 100)
                    {
                        BigInteger bigInteger = BigInteger.ONE;
                        int n = Integer.parseInt(oldStr);
                        for (int i = 1; i <= n; i++)
                        {
                            bigInteger = bigInteger.multiply(BigInteger.valueOf(i));
                        }
                        System.out.println(bigInteger.toString());
                        label.setText(bigInteger.toString());
                        isTextToLong();
                    }

                }
            }
            //根号
            else if (s.equals("√"))
            {
                oldStr = label.getText();

                if (!oldStr.equals("0") && !isContainOperator(oldStr))
                {
                    double d = Double.parseDouble(oldStr);
                    if (d > 0)
                    {
                        d = Math.sqrt(d);
                        oldStr = d + "";
                        //如果是整数去掉小数点及末尾的0
                        if (oldStr.charAt(oldStr.length()-1) == '0' )
                        {
                            System.out.println(oldStr);
                            oldStr = oldStr.substring(0, oldStr.length() - 2);

                        }
                        System.out.println(d);
                        label.setText(oldStr);
                        isTextToLong();
                    }

                }
            }
            //1/X
//            else if (s.equals("1/X"))
            else if (source == btn[1])
            {
                oldStr = label.getText();
                if (!oldStr.equals("0"))
                {
                    if (!oldStr.contains("+") && !oldStr.contains("×") && !oldStr.contains("÷") && oldStr.charAt(oldStr.length()-1) != '-')
                    {
                        double d = Double.parseDouble(oldStr);
                        double a = 1.0;
                        double ans = a / d;
                        oldStr = ans + "";
                        System.out.println(ans);
                        //去掉小数点后面多余的0
                        DecimalFormat decimalFormat = new DecimalFormat("###################.################");

                        System.out.println(decimalFormat.format(ans));
                        label.setText(decimalFormat.format(ans));
//                    System.out.println(d);
//                    BigDecimal a = BigDecimal.valueOf(1.0);
//                    BigDecimal b = BigDecimal.valueOf(d);
//                    System.out.println(a.divide(b));
//                    oldStr = a.divide(b).toPlainString().toString();
//                    label.setText(oldStr);
                        isTextToLong();
                    }

                }
            }
        }

        /**
         * 图片按钮
         */
        else
        {
            //清空键
            if (source == btn[0])
            {
                setShortText();
                label.setText("0");
            }
            //退格键
            else if (source == btn[3])
            {
                isTextToLong();
                oldStr = label.getText();
                if (!oldStr.equals("0") && oldStr.length() > 1)
                {
                    oldStr = oldStr.substring(0, oldStr.length() - 1);
                    System.out.println(oldStr);
                    label.setText(oldStr);
                } else if (oldStr.length() == 1)
                {
                    label.setText("0");
                }

            }
            //π
            else if (source == btn[2])
            {
                label.setText("3.1415926535898");
                isTextToLong();
            }
            //除法
            else if (source == btn[7])
            {
                oldStr = label.getText();
                String textLast = oldStr.substring(oldStr.length() - 1, oldStr.length());
                //如果最后一个是运算符
                if (textLast.equals("+") || textLast.equals("-") || textLast.equals("×") || textLast.equals("÷") || textLast.equals("%"))
                {
                    oldStr = oldStr.substring(0, oldStr.length() - 1) + "÷";
                    System.out.println(textLast);
                    label.setText(oldStr);
                    isTextToLong();
                }
                else
                {
                    label.setText(oldStr + "÷");
                    isTextToLong();
                }
            }
            //乘法
            else if (source == btn[11])
            {
                oldStr = label.getText();
                String textLast = oldStr.substring(oldStr.length() - 1, oldStr.length());
                //如果最后一个是运算符
                if (textLast.equals("+") || textLast.equals("-") || textLast.equals("×") || textLast.equals("÷") || textLast.equals("%"))
                {
                    oldStr = oldStr.substring(0, oldStr.length() - 1) + "×";
                    System.out.println(textLast);
                    label.setText(oldStr);
                    isTextToLong();
                } else
                {
                    label.setText(oldStr + "×");
                    isTextToLong();
                }
            }
            //减法
            else if (source == btn[15])
            {
                oldStr = label.getText();
                String textLast = oldStr.substring(oldStr.length() - 1, oldStr.length());
                //如果最后一个是运算符
                if (textLast.equals("+") || textLast.equals("-") || textLast.equals("×") || textLast.equals("÷") || textLast.equals("%"))
                {
                    oldStr = oldStr.substring(0, oldStr.length() - 1) + "-";
                    System.out.println(textLast);
                    label.setText(oldStr);
                    isTextToLong();
                }
                else
                {
                    label.setText(oldStr + "-");
                    isTextToLong();
                }
            }
            //加法
            else if (source == btn[19])
            {
                oldStr = label.getText();
                String textLast = oldStr.substring(oldStr.length() - 1, oldStr.length());
                //如果最后一个是运算符
                if (textLast.equals("+") || textLast.equals("-") || textLast.equals("×") || textLast.equals("÷") || textLast.equals("%"))
                {
                    oldStr = oldStr.substring(0, oldStr.length() - 1) + "+";
                    System.out.println(textLast);
                    label.setText(oldStr);
                    isTextToLong();
                } else
                {
                    label.setText(oldStr + "+");
                    isTextToLong();
                }
            }
            //等于
            else if (source == btn[23])
            {
                oldStr = label.getText();
                //计算结果
                double ans = Calc.calcBracket(oldStr);
                //去掉小数点后面多余的0
                DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

                System.out.println(Calc.calcBracket(oldStr));
                label.setText(decimalFormat.format(ans));
                isTextToLong();
            }

        }
    }

    /**
     * 判断是否需要缩小字体
     */
    private boolean isTextToLong()
    {
        if (label.getText().length() > 11)
        {
            label.setPreferredSize(new Dimension(WIDTH, HEIGHT / 5));
            label.setFont(new Font("微软雅黑", Font.BOLD, 30));
            return true;
        }
        else
        {
            label.setPreferredSize(new Dimension(WIDTH, HEIGHT / 8));
            label.setFont(new Font("微软雅黑", Font.BOLD, 40));
            return false;
        }
    }

    private void setLongText()
    {
        label.setPreferredSize(new Dimension(WIDTH, HEIGHT / 4));
        label.setFont(new Font("微软雅黑", Font.BOLD, 25));
    }

    private void setShortText()
    {
        label.setPreferredSize(new Dimension(WIDTH, HEIGHT / 8));
        label.setFont(new Font("微软雅黑", Font.BOLD, 40));
    }

    /**
     * 判断最后一个字符是否是数字
     */
    private boolean isLastTextIsNum(String s)
    {
        s = s.substring(s.length() - 1, s.length());
        if (s.compareTo("0")>=0 && s.compareTo("9") <=0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 判断最后一个字符是否是运算符
     */
    private boolean isLastTextIsOperator(String s)
    {
        s = s.substring(s.length() - 1, s.length());
        if (isContainOperator(s))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 判断是否包含运算符+-x÷%
     */
    private boolean isContainOperator(String s)
    {
        if (s.contains("+") || s.contains("-") || s.contains("×") || s.contains("÷") || s.contains("%"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void main(String[] args)
    {
        /**
         * 这里运行程序
         */
        new Calculator();
    }
}
