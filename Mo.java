import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Mo {
    public static void main(String[] args){
        Mo mo=new Mo();
        System.out.println(Arrays.toString(mo.solveQueries(new int[]{1,1,2,1,3}, new int[][]{{1,5},{2,4}}, 1)));
    }
    public int[] solveQueries(int[] nums, int[][] Queries, int k) {
        int n=nums.length,q=Queries.length;
        int[][] queries=new int[Queries.length][3];
        for(int i=0;i<q;i++){
            queries[i]=new int[]{Queries[i][0]-1,  Queries[i][1]-1,  i}; 
        }
        int limit=(int)(Math.sqrt(n));
        Arrays.sort(queries,(a,b)->{
           int al=a[0],bl=b[0],ar=a[1],br=b[1];
           if((al/limit)!=(bl/limit)) return al-bl;
           return (al/limit)%2==0?ar-br:br-ar;
        });
        int count=0;
        int l=0,r=-1;
        int[] ans=new int[q];
        Map<Integer,Integer> mp=new HashMap<>();
        for(int query[]:queries){
            int ql=query[0],qr=query[1];
            //expand
            while(qr>r){
                int val=nums[++r];
                int c=mp.getOrDefault(val,0);
                if(c==k-1) count++;
                mp.put(val,c+1);
            }
            while(ql<l){
                int val=nums[--l];
                int c=mp.getOrDefault(val,0);
                if(c==k-1) count++;
                mp.put(val,c+1);
            }
            //compress
            while(l<ql){
                int val=nums[l++];
                int c=mp.getOrDefault(val,0);
                if(c==k) count--;
                mp.put(val,c-1);
            }
            while(r>qr){
                int val=nums[r--];
                int c=mp.getOrDefault(val,0);
                if(c==k) count--;
                mp.put(val,c-1);
            }
            ans[query[2]]=count;
        }
        return ans;
    }
}
