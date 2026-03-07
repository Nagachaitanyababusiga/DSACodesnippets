import java.util.*;

//image reference: ./assets/HLDinput.png

public class HLDforMinimum {
    public static void main(String[] args){
        int n=6;
        int[] values=new int[]{10,1,2,5,0,3};
        HLD hld=new HLD(n, values);
        hld.addEdge(1, 5);
        hld.addEdge(0, 1);
        hld.addEdge(0, 2);
        hld.addEdge(2, 4);
        hld.addEdge(2, 3);
        hld.initialize();
        int ans=hld.query(3, 4);
        System.out.println(ans);
        ans=hld.query(2, 5);
        System.out.println(ans);
        ans=hld.query(1, 5);
        System.out.println(ans);
        ans=hld.query(0, 0);
        System.out.println(ans);
        // hld.printInfo();
    }
}


class HLD{
    int n;
    int[] values;
    int[] arr,segTree,pos,size,head,heavyChild,parent,depth;
    int index;
    Map<Integer,List<Integer>> tree;
    HLD(int n,int[] values){
        this.n=n;
        this.values=values;
        tree=new HashMap<>();
        arr=new int[n];
        segTree=new int[4*n];
        size=new int[n];
        depth=new int[n];
        head=new int[n];
        heavyChild=new int[n];
        pos=new int[n];
        Arrays.fill(heavyChild,-1);
        Arrays.fill(segTree,Integer.MAX_VALUE);
        parent=new int[n];
    }

    void addEdge(int a,int b){
        tree.computeIfAbsent(a, k->new ArrayList<>()).add(b);
        tree.computeIfAbsent(b, k->new ArrayList<>()).add(a);
    }

    void initialize(){
        index=0;
        dfs(0,-1);
        decompose(0,0);
        build(0,n-1,0);
    }
    
    //dfs: calculate depth, parent,size,heavyChild
    int dfs(int curr,int par){
        int s=1;
        int maxsize=0;
        parent[curr]=par;
        for(int child:tree.getOrDefault(curr, new ArrayList<>())){
            if(child==par) continue;
            depth[child]=1+depth[curr];
            int cs=dfs(child,curr);
            s+=cs;
            if(cs>maxsize){
                heavyChild[curr]=child;
                maxsize=cs;
            }
        }
        size[curr]=s;
        return s;
    }

    //decompose into heavy and light
    void decompose(int curr,int start){
        head[curr]=start;
        pos[curr]=index;
        arr[index]=values[curr];
        index++;
        if(heavyChild[curr]!=-1) decompose(heavyChild[curr], start);
        for(int child:tree.getOrDefault(curr, new ArrayList<>())){
            if(child==heavyChild[curr]||child==parent[curr]) continue;
            decompose(child,child);
        }
    }

    void build(int l,int r,int pos){
        if(l==r){
            segTree[pos]=arr[l];
            return;
        }
        int mid=(r-l)/2+l;
        int lc=2*pos+1;
        int rc=2*pos+2;
        build(l,mid,lc);
        build(mid+1,r,rc);
        segTree[pos]=Math.min(segTree[lc],segTree[rc]);
    }

    //very critical section 
    int query(int a,int b){
        int ans=Integer.MAX_VALUE;

        //continue until both are in the same segment or component
        while(head[a]!=head[b]){
            if(depth[head[a]]<depth[head[b]]){
                int temp=a;
                a=b;
                b=temp;
            }
            ans=Math.min(ans,queryHelp(0, n-1, 0, pos[head[a]], pos[a]));
            a=parent[head[a]];
        }
        if(depth[a]<depth[b]){
            int temp=a;
            a=b;
            b=temp;
        }
        // System.out.println("a: "+a+" b:"+b);
        // System.out.println("pos[a]: "+pos[a]+" pos[b]:"+pos[b]);
        ans=Math.min(ans,queryHelp(0, n-1, 0, pos[b],pos[a]));
        return ans;
    }

    int queryHelp(int l,int r,int pos,int ql,int qr){
        if(r<ql||qr<l) return Integer.MAX_VALUE;
        if(ql<=l&&r<=qr) return segTree[pos];
        int mid=(r-l)/2+l;
        int lc=2*pos+1;
        int rc=2*pos+2;
        int a=queryHelp(l,mid,lc,ql,qr);
        int b=queryHelp(mid+1,r,rc,ql,qr);
        return Math.min(a,b);
    }

    void update(int node,int value){
        updateHelp(0, n-1, 0, pos[node], value);
    }

    void updateHelp(int l,int r,int pos,int index,int value){
        if(index<l||index>r) return;
        if(l==r){
            segTree[pos]=value;
            return;
        }
        int mid=(r-l)/2+l;
        int lc=2*pos+1;
        int rc=2*pos+2;
        updateHelp(l, mid, lc, index, value);
        updateHelp(mid+1, r, rc, index, value);
        segTree[pos]=Math.min(segTree[lc],segTree[rc]);
    }

    void printInfo(){
        System.out.println("Heads: "+Arrays.toString(head));
        System.out.println("heavy children: "+Arrays.toString(heavyChild));
    }

}