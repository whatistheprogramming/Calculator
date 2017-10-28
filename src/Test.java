import java.text.DecimalFormat;
import java.util.Scanner;

public class Test {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        while (in.hasNext())
        {
            double number = in.nextDouble();

            System.out.println(number);                         //12.0

            System.out.println(Double.toString(number));        //12.0

            DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
            System.out.println(decimalFormat.format(number));   //12
        }
    }

//    public static void main(String[] args) {
//
//        int num=78631;//数字字符串
//
//        String str=num+"";
//        StringBuffer sb=new StringBuffer(str);
//        StringBuffer rsb=sb.reverse();
//
//
//        System.out.println(rsb);
//
//    }
}