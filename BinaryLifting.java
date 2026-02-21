// Tree
// ./assets/binarylifinginput.png
// not that useful: https://shubham.awasthi.dev/graph-visualizer/
// useful: https://graphonline.top/create_graph_by_edge_list

import java.util.*;

public class BinaryLifting {
    public static void main(String args[]){
        int edgelist[][]=new int[][]{
            {18, 11}, {11, 15}, {5, 1}, {1, 4}, {4, 9}, 
            {4, 10}, {10, 17}, {10, 16}, {16, 13}, {13, 7}, 
            {7, 2}, {2, 0}, {2, 6}, {6, 12}, {12, 19}, 
            {0, 3}, {3, 8}, {8, 14}, {8, 15}
        };
        Map<Integer,List<Integer>> tree=new HashMap<>();
        for(int e[]:edgelist){
            tree.computeIfAbsent(e[0], k->new ArrayList<>()).add(e[1]);
            tree.computeIfAbsent(e[1], k->new ArrayList<>()).add(e[0]);
        }
        int n=20; // no of nodes;
        int l=(int)Math.ceil(Math.log(n)/Math.log(2))+1; //Math.log(n)/Math.log(2);
        int[][] up=new int[n+1][l+1];
        for(int i[]:up) Arrays.fill(i,-1);
        int[] depth=new int[n+1]; // can be ignored
        preprocess(tree,n,up,depth,l); // (1.)
        int a=0,b=1;
        int val=lca(a,b,up,depth,l); //(3.)
        System.out.println(val);
        a=14;b=19;
        val=lca(a,b,up,depth,l);
        System.out.println(val);
        a=18;b=18;
        val=lca(a,b,up,depth,l);
        System.out.println(val);
        a=18;b=11;
        val=lca(a,b,up,depth,l);
        System.out.println(val);
        a=18;b=6;
        val=lca(a,b,up,depth,l);
        System.out.println(val);
    }
    // (1.)
    public static void preprocess(Map<Integer,List<Integer>> tree,int n,int[][] up,int[] depth,int l){
        int root=17;
        // (2.)
        dfs(root,root,tree,up,depth,l); //parent of parent of root should always return root otherwise the function doesn't work properly
    }
    // (2.)
    public static void dfs(int root,int parent,Map<Integer,List<Integer>> tree,int[][] up,int[] depth,int l){
        up[root][0]=parent;
        for(int i=1;i<=l;i++) up[root][i]=up[up[root][i-1]][i-1]; //computes the parents in 2^k steps
        for(int child:tree.getOrDefault(root,new ArrayList<>())){
            if(child==parent) continue;
            depth[child]=1+depth[root];
            dfs(child,root,tree,up,depth,l);
        }
    }
    //(3.)
    public static int lca(int a,int b,int[][] up,int[] depth,int l){
        if(depth[a]<depth[b]){
            int temp=a;
            a=b;
            b=temp;
        }
        int diff=(depth[a]-depth[b]);
        a=kthAncestor(a, diff, up, l);
        if(a==b) return a;
        for(int i=l;i>=0;i--){
            if(up[a][i]!=up[b][i]){
                a=up[a][i];
                b=up[b][i];
            }
        }
        return up[a][0];
    }
    //(4.)
    public static int kthAncestor(int a,int k,int[][] up,int l){
        for(int i=0;i<=l;i++){
            if((k&(1<<i))>0) a=up[a][i];
        }
        return a;
    }
}



/*
input: 

[[18,11],[11,15],[5,1],[1,4],[4,9],[4,10],[10,17],[10,16],[16,13],[13,7],[7,2],[2,0],[2,6],[6,12],[12,19],[0,3],[3,8],[8,14],[8,15]]

18-11
11-15
5-1
1-4
4-9
4-10
10-17
10-16
16-13
13-7
7-2
2-0
2-6
6-12
12-19
0-3
3-8
8-14
8-15

*/