import java.util.*;

public class KrushkalsMST {
    public static void main(String[] args){
        int V=4;

        List<Edge> edges=new ArrayList<>();

        edges.add(new Edge(0,1,10));
        edges.add(new Edge(0,2,6));
        edges.add(new Edge(0,3,5));
        edges.add(new Edge(1,3,15));
        edges.add(new Edge(2,3,4));

        Collections.sort(edges,(a,b)->a.weight-b.weight);
        KrushkalsTree kt=new KrushkalsTree(V);

        long cost=0;
        for(Edge e:edges){
            if(kt.union(e.a,e.b)) cost+=e.weight;
        }
        System.out.println("cost of building MST is: "+cost);

    }
}

class KrushkalsTree{
    int components;
    int[] parent,size;
    KrushkalsTree(int n){
        components=n;
        size=new int[n];
        parent=new int[n];
        for(int i=0;i<n;i++){
            size[i]=1;
            parent[i]=i;
        }
    }
    public int find(int a){
        if(parent[a]!=a) parent[a]=find(parent[a]);
        return parent[a];
    }
    public boolean union(int a,int b){
        int pa=find(a);
        int pb=find(b);
        if(pa==pb) return false;
        components--;
        if(size[pa]>=size[pb]){
            parent[pb]=pa;
            size[pa]+=size[pb];
        }else{
            parent[pa]=pb;
            size[pb]+=size[pa];
        }
        return true;
    }
}


class Edge{
    int a,b;
    int weight;
    Edge(int a,int b,int weight){
        this.a=a;this.b=b;this.weight=weight;
    }
}

//Time: E Log E
//Space: E + V