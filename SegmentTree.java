public class SegmentTree{
    int[] tree;
    int[] nums;
    int n;
    SegmentTree(int[] nums){
        this.nums=nums;
        n=nums.length;
        tree=new int[4*n];
        build(0,n-1,0);
    }
    void build(int l,int r,int pos){
        if(l==r){//leaf node
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
    int query(int l,int r){
        return queryHelp(l,r,0,n-1,0);
    }
    int queryHelp(int ql,int qr,int l,int r,int pos){
        if(qr<l||ql>r) return 0;
        if(ql<=l&&r<=qr) return tree[pos];
        int mid=(r-l)/2+l;
        int lc=2*pos+1;
        int rc=2*pos+2;
        int a=queryHelp(ql,qr,l,mid,lc);
        int b=queryHelp(ql,qr,mid+1,r,rc);
        return a+b;
    }
    void update(int index,int val){
        updateHelp(index,val,0,n-1,0);
    }
    void updateHelp(int index,int val,int l,int r,int pos){
        if(index<l||index>r) return;
        if(l==r){
            tree[pos]=val;
            return;
        }
        int mid=(r-l)/2+l;
        int lc=2*pos+1;
        int rc=2*pos+2;
        updateHelp(index,val,l,mid,lc);
        updateHelp(index,val,mid+1,r,rc);
        tree[pos]=tree[lc]+tree[rc];
    }
}