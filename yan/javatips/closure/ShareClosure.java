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
 * forѭ���ڲ������������������ֵ�ڵ�һ�θ�ֵ���ٴν��룬����ͬʱ��ʼ��������ִֻ��һ�Σ�
 * ������������ʼ�����ʽ�к��б�������ÿ�ζ������һ�θ�ֵ������ʱjvm����
 * �����Ƕ�����һ����ʽ����̽�֣����ֶ��帳ֵҲ��Ϊ������������Ϊforѭ���ڲ���Ҳ��һ�������ԣ�����
 * ���ǿ��ܻ�����ж����ţ������Ǿ仰�������������£�ʹ�ü��������ֶȸߵķ�ʽ�������⣬���µ�;����ѡ
 * ���߹��߿���ʹ�ñ���������̽�����������������
 */
