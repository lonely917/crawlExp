package yan.javatips.closure;

import java.util.ArrayList;
import java.util.List;


interface Action
{
    void Run();
}

public class ShareClosure {

    List<Action> list = new ArrayList<Action>();
    
    public void Input()
    {
        for(int i=0;i<10;i++)
        {
            final int copy = i;
            list.add(new Action() {    
                @Override
                public void Run() {
                    System.out.println(copy);
                }
            });
        }
    }
    
    public void Output()
    {
        for(Action a : list){a.Run();}
    }
    
    public static void main(String[] args) {
        ShareClosure sc = new ShareClosure();
        sc.Input();
        sc.Output();

        for(int i=0;i<5;i++)
        {
        	int a = 1;
        	a++;
        	System.out.println(a);
        }
        
        for(int i=0;i<5;i++)
        {
        	int a = i;
        	System.out.println(a);
        }
    }

}

/**
 * for循环内部定义变量，变量的数值在第一次赋值后再次进入，定义同时初始化则此语句只执行一次，
 * 但是如果定义初始化表达式中含有变量，则每次都会进行一次赋值，编译时jvm处理。
 * 这里是对这样一种形式进行探讨，这种定义赋值也较为常见，作用域为for循环内部，也有一定合理性，但是
 * 还是可能会造成判断困扰，还是那句话：不清楚的情况下，使用简单明了区分度高的方式处理问题，有新的途径可选
 * 或者工具可以使用必须对其进行探索，否则会有隐患。
 */
