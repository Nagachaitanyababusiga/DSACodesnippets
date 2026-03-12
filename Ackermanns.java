import java.util.*;

public class Ackermanns {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        long a=sc.nextLong();
        long b=sc.nextLong();
        System.out.println(akrmns(a,b));
        sc.close();
    }
    public static long akrmns(long m,long n){
        if(m==0) return n+1;
        if(n==0) return akrmns(m-1,1);
        return akrmns(m-1,akrmns(m,n-1));
    }
}
