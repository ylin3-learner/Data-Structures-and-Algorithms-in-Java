public class NumArray {

    private SegmentTree<Integer> segmentTree;

    public NumArray(int[] nums) {

        // basic type (int) to packaging type (Integer)
        Integer[] data = new Integer[nums.length];

        if (nums.length > 0) {
            for (int i = 0; i < nums.length; i++)
                data[i] = nums[i];
            segmentTree = new SegmentTree<>(data, (a, b) -> a + b);
        }
    }

    public int sumRange(int left, int right) {

        if (segmentTree == null)
            throw new RuntimeException("SegmentTree is null.");

        return segmentTree.query(left, right);
    }
}
