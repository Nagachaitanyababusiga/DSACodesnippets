public class SparseTableRMQ {
    public static void main(String[] args){
        int arr[]=new int[]{1,2,3,4,1,2,3,5,67,9};
        SparseTable st=new SparseTable(arr);
        System.out.println(st.RMQ(0, arr.length-1));
        System.out.println(st.RMQ(1, 3));
        System.out.println(st.RMQ(8,8));
    }
}

class SparseTable{
    int st[][]; //actual sparse table
    int log[]; //to store precomputed log base 2 values
    int n;
    SparseTable(int[] arr){
        //initialization
        n=arr.length;
        int k=(int)(Math.log(n)/Math.log(2))+1;
        st=new int[n][k];
        log=new int[n+1];

        //calculation of log values
        for(int i=2;i<=n;i++) log[i]=log[i/2]+1;

        //intialization of sparse tree table
        for(int i=0;i<n;i++) st[i][0]=arr[i];

        //preprocessing
        for(int j=1;j<k;j++){ //power of length
            for(int index=0;index<=(n-(1<<j));index++){
                st[index][j]=Math.min(st[index][j-1],st[index+(1<<(j-1))][j-1]);
            }
        }
    }
    int RMQ(int l,int r){
        if(r<l){
            System.out.println("Invalid bounds");
            return -1;
        }
        int len=log[r-l+1]; //largest 2's power that can be fitted
        // System.out.println(len);
        return Math.min(st[l][len],st[r-(1<<len)+1][len]);
    }
}