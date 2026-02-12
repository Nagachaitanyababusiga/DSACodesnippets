public class TernarySearch{
    public int findMinimumElementIndex(int[] nums){
        int n=nums.length;
        int l=0,r=n-1;
        int ans=-1;
        while(l<=r){
            int mid1=(r-l)/3+l;
            int mid2=r-(r-l)/3;
            if(nums[mid1]==nums[mid2]){
                l=mid1+1;
                r=mid2-1;
                ans=mid1;
            }else if(nums[mid1]>nums[mid2]){
                l=mid1+1;
            }else{
                r=mid2-1;
            }
        }
        return ans;
    }
}