import java.util.*;

public class KMP{
    public void getLps(String s,int[] lps){
        int i=1,j=0,n=lps.length;
        while(i<n){
            if(s.charAt(i)==s.charAt(j)){
                j++;
                lps[i]=j;
                i++;
            }else if(j>0){
                j=lps[j-1];
            }else{
                i++;
            }
        }
    }
    public List<Integer> allOccurences(String hayStack,String needle){
        List<Integer> ans=new ArrayList<>();
        int n=needle.length();
        int[] lps=new int[n];
        getLps(needle, lps);
        int i=0;int j=0;
        while(i<hayStack.length()){
            if(hayStack.charAt(i)==needle.charAt(j)){
                i++;j++;
            }else if(j>0){
                j=lps[j-1];
            }else i++;
            if(j==n){
                ans.add(i-n);
                j=lps[j-1];
            }
        }
        return ans;
    }
}
