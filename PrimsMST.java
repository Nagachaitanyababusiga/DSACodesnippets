import java.util.*;

public class PrimsMST {
    public static void main(String[] args){
        int v=5;
        List<List<Node>> adj=new ArrayList<>();
        for(int i=0;i<v;i++) adj.add(new ArrayList<>());

        adj.get(0).add(new Node(1,2));
        adj.get(1).add(new Node(0,2));

        adj.get(0).add(new Node(3,6));
        adj.get(3).add(new Node(0,6));

        adj.get(1).add(new Node(2,3));
        adj.get(2).add(new Node(1,3));

        adj.get(1).add(new Node(3,8));
        adj.get(3).add(new Node(1,8));

        adj.get(1).add(new Node(4,5));
        adj.get(4).add(new Node(1,5));

        adj.get(2).add(new Node(4,7));
        adj.get(4).add(new Node(2,7));

        int weight=0;
        boolean visited[]=new boolean[v];

        PriorityQueue<Node> pq=new PriorityQueue<>((a,b)->Integer.compare(a.weight,b.weight));
        pq.add(new Node(0,0));

        while(!pq.isEmpty()){
            Node curr=pq.poll();
            if(visited[curr.node]) continue;
            visited[curr.node]=true;
            weight+=curr.weight;
            for(Node nei:adj.get(curr.node)){
                if(visited[nei.node]) continue;
                pq.add(nei);
            }
        }

        System.out.println("The weight of the spanning Tree is: "+weight);

    }
}

class Node{
    int node,weight;
    Node(int node,int weight){
        this.node=node;
        this.weight=weight;
    }
}

// Time: E Log V
// Space: E + V
