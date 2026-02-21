import java.util.*;

//Binary Trie with count (for this use case)
public class BinaryTrie{
    TrieNode root;
    BinaryTrie(){
        root=new TrieNode();
    }
    void insert(int n){
        TrieNode curr=root;
        curr.count++;
        for(int i=30;i>=0;i--){ //Change this value according to the maximum number of bits
            int bit=(n>>i)&1;
            if(bit==0){
                if(curr.zero==null) curr.zero=new TrieNode();
                curr=curr.zero;
                curr.count++;
            }else{
                if(curr.one==null) curr.one=new TrieNode();
                curr=curr.one;
                curr.count++;
            }
        }
    }
    void remove(int n){
        TrieNode curr=root;
        curr.count--;
        for(int i=30;i>=0;i--){
            int bit=(n>>i)&1;
            if(bit==0){
                if(curr.zero.count==1){
                    curr.zero=null;
                    break;
                }
                curr=curr.zero;
                curr.count--;
            }else{
                if(curr.one.count==1){
                    curr.one=null;
                    break;
                }
                curr=curr.one;
                curr.count--;
            }
        }
    }
    int getMaxXor(int n){
        TrieNode curr=root;
        int res=0;
        for(int i=30;i>=0;i--){
            int bit=(n>>i)&1;
            if(curr==null) return res;
            if(bit==0){
                if(curr.one!=null){
                    res|=(1<<i);
                    curr=curr.one;
                }else{
                    curr=curr.zero;
                }
            }else{
                if(curr.zero!=null){
                    res|=(1<<i);
                    curr=curr.zero;
                }else{
                    curr=curr.one;
                }
            }
        }
        return res;
    }
    void print(TrieNode root){
        Queue<TrieNode> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            int size=q.size();
            for(int i=0;i<size;i++){
                TrieNode temp=q.poll();
                if(temp==null){
                    System.out.print("null ");
                    continue;
                }
                System.out.print(temp.count+" ");
                q.add(temp.one);
                q.add(temp.zero);
            }
            System.out.println();
        }
    }
    public static void main(String[] args){
        BinaryTrie bt=new BinaryTrie();
        bt.insert(9);
        bt.insert(31);
        bt.print(bt.root);
        System.out.println("Maximum xor is: "+ bt.getMaxXor(16));
    }
}

//BASE CELL (SEED CLASS)
class TrieNode{
    int count;
    TrieNode one,zero;
}

// link for practice problem: https://leetcode.com/problems/maximum-subarray-xor-with-bounded-range/
// practice problem-2: https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/