package freegui.step1;

public class Condition {

    public static void main(String[] args) {

        int a;//定义一个整数 a
        int b;//定义一个整数 b

        a = 1;
        b = 2;

        int result = a + b;//定义一个整数存放 a+b 的结果

        System.out.println(result);//在控制台打印 result 变量存放的数据










        //条件判断语句 if（逻辑运算表达式）
        if (result == 3){
            System.out.println("程序是正确的");
        }else {
            System.out.println("程序有问题");
        }
    }
}
