import java.util.*;
//link: https://cses.fi/problemset/task/1138/

public class EulerTourWithSegTree{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int q=sc.nextInt();
        int[] vals=new int[n];
        for(int i=0;i<n;i++) vals[i]=sc.nextInt();
        Map<Integer,List<Integer>> tree=new HashMap<>();
        for(int i=0;i<n-1;i++){
            int a=sc.nextInt()-1;
            int b=sc.nextInt()-1;
            tree.computeIfAbsent(a, k->new ArrayList<>()).add(b);
            tree.computeIfAbsent(b, k->new ArrayList<>()).add(a);
        }
        int[][] queries=new int[q][];
        for(int i=0;i<q;i++){
            int type=sc.nextInt();
            if(type==1){
                queries[i]=new int[]{1,sc.nextInt(),sc.nextInt()};
            }else queries[i]=new int[]{2,sc.nextInt()};
        }
        EulerPathSegTree st=new EulerPathSegTree(vals,tree);
        for(int[] query:queries){
            if(query[0]==1) st.update(query[1]-1, query[2]);
            else System.out.println(st.query(query[1]-1));
        }
        sc.close();
    }
}

class EulerPathSegTree{
    int[] start,end;
    long[] segTree;
    int[] vals;
    List<Integer> lst;
    int n;
    int time;
    EulerPathSegTree(int[] vals,Map<Integer,List<Integer>> tree){
        time=0;
        this.vals=vals;
        start=new int[vals.length];
        end=new int[vals.length];
        int root=0;
        lst=new ArrayList<>();
        dfs(root,-1,tree);
        segTree=new long[4*lst.size()];
        n=lst.size();
        build(0,n-1,0);
    }
    void dfs(int root,int parent,Map<Integer,List<Integer>> tree){
        start[root]=time;
        lst.add(vals[root]);
        time++;
        List<Integer> children=tree.get(root);
        if(children!=null){
            for(int child:children){
                if(child==parent) continue;
                dfs(child,root,tree);
            }
        }
        lst.add(-vals[root]);
        end[root]=time;
        time++;
    }
    void build(int l,int r,int pos){
        if(l==r){
            segTree[pos]=lst.get(l);
            return;
        }
        int mid=(r-l)/2+l;
        int lc=2*pos+1;
        int rc=2*pos+2;
        build(l,mid,lc);
        build(mid+1,r,rc);
        segTree[pos]=segTree[lc]+segTree[rc];
    }
    void update(int s,int x){
        updateHelp(0,n-1,0,start[s],(long)x);
        updateHelp(0,n-1,0,end[s],-(long)x);
    }
    void updateHelp(int l,int r,int pos,int index,long val){
        if(index<l||r<index) return;
        if(l==r){
            segTree[pos]=val;
            return;
        }
        int mid=(r-l)/2+l;
        int lc=2*pos+1;
        int rc=2*pos+2;
        updateHelp(l,mid,lc,index,val);
        updateHelp(mid+1,r,rc,index,val);
        segTree[pos]=segTree[lc]+segTree[rc];
    }
    long query(int s){
        return queryHelp(0,n-1,0,0,start[s]);
    }
    long queryHelp(int l,int r,int pos,int ql,int qr){
        if(qr<l||ql>r) return 0;
        if(ql<=l&&r<=qr) return segTree[pos];
        int mid=(r-l)/2+l;
        int lc=2*pos+1;
        int rc=2*pos+2;
        long a=queryHelp(l,mid,lc,ql,qr);
        long b=queryHelp(mid+1,r,rc,ql,qr);
        return a+b;
    }
}