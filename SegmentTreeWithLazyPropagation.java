
public class SegmentTreeWithLazyPropagation{
    public static void main(String[] args){
        int[] nums=new int[]{1,2,3,4,5,6};
        SegTree st=new SegTree(nums);
        int ans=st.queryRange(0,nums.length-1);
        st.updateRange(0,nums.length-1,1);
        ans=st.queryRange(0,nums.length-1);
        System.out.println(ans);
    }
}

class SegTree{
    int[] nums;
    int[] lazy;
    int n;
    int[] tree;
    SegTree(int[] nums){
        this.nums=nums;
        n=nums.length;
        tree=new int[4*n];
        lazy=new int[4*n];
        build(0,n-1,0);
    }
    void build(int l,int r,int pos){
        if(l==r){
            tree[pos]=nums[l];
            return;
        }
        int mid=(r-l)/2+l;
        int lc=2*pos+1;
        int rc=2*pos+2;
        build(l,mid,lc);
        build(mid+1,r,rc);
        tree[pos]=tree[lc]+tree[rc];
    }
    void updateRange(int ql,int qr,int val){
        updateRangeHelp(ql,qr,0,n-1,0,val);
    }
    //updateleft, updateright
    void updateRangeHelp(int ul,int ur,int l,int r,int pos,int val){
        // clear pending updates for the current node
        if(lazy[pos]!=0){
            tree[pos]+=(r-l+1)*lazy[pos];
            if(l!=r){ //if l==r that means pos is a leaf node
                lazy[2*pos+1]+=lazy[pos];
                lazy[2*pos+2]+=lazy[pos];
            }
            lazy[pos]=0;
        }
        //no intersection
        if(ur<l||r<ul) return;
        //if full intersection
        if(ul<=l&&r<=ur){
            tree[pos]+=(r-l+1)*val;
            if(l!=r){
                lazy[2*pos+1]+=val;
                lazy[2*pos+2]+=val;
            }
            return;
        }
        //if covered only partially
        int mid=(r-l)/2+l;
        int lc=2*pos+1;
        int rc=2*pos+2;
        updateRangeHelp(ul,ur,l,mid,lc,val);
        updateRangeHelp(ul,ur,mid+1,r,rc,val);
        tree[pos]=tree[lc]+tree[rc];
    }
    int queryRange(int ql,int qr){
        return queryRangeHelp(ql,qr,0,n-1,0);
    }
    //updateleft, updateright
    int queryRangeHelp(int ql,int qr,int l,int r,int pos){
        //clear any pendinig updates in lazy
        if(lazy[pos]!=0){
            tree[pos]+=(r-l+1)*lazy[pos];
            if(l!=r){
                lazy[2*pos+1]+=lazy[pos];
                lazy[2*pos+2]+=lazy[pos];
            }
            lazy[pos]=0;
        }
        //no intersection
        if(qr<l||r<ql) return 0;
        //full intersection
        if(ql<=l&&r<=qr) return tree[pos];
        // no intersection
        int mid=(r-l)/2+l;
        int lc=2*pos+1;
        int rc=2*pos+2;
        int a=queryRangeHelp(ql,qr,l,mid,lc);
        int b=queryRangeHelp(ql,qr,mid+1,r,rc);
        return a+b;
    }
}