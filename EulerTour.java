//take ./assests/binarylifinginput.png as input
import java.util.*;

//link: https://cses.fi/problemset/task/1138/

public class EulerTour{
    public static void main(String[] args){
        int edges[][]=new int[][]{
            {17,10},{10,4},{10, 16},{9,4},
            {4,1},{5, 1},{16, 13},{13, 7},
            {7, 2},{2, 0},{2, 6},{0, 3},{3, 8},
            {8, 15},{11, 15},
            {18, 11},{8, 14},
            {6, 12}, {12, 19}
        };
        Map<Integer,List<Integer>> tree=new HashMap<>();
        for(int e[]:edges){
            tree.computeIfAbsent(e[0], k->new ArrayList<>()).add(e[1]);
            tree.computeIfAbsent(e[1], k->new ArrayList<>()).add(e[0]);
        }
        int root=17;
        List<Integer> eulerTour=new ArrayList<>();
        EulerTour et=new EulerTour();
        et.compute(root,-1,tree,eulerTour);
        System.out.println(eulerTour);
    }
    public void compute(int root,int parent,Map<Integer,List<Integer>> tree,List<Integer> et){
        et.add(root);
        for(int child:tree.getOrDefault(root,new ArrayList<>())){
            if(child==parent) continue;
            compute(child,root,tree,et);
        }
        et.add(root);
    }
}
