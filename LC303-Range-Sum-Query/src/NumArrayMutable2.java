public class NumArrayMutable2 {

    private SegmentTree<Integer> segmentTree;

    public NumArrayMutable2(int[] nums) {

        if (nums.length > 0) {
            Integer[] data = new Integer[nums.length];
            for (int i = 0; i < nums.length; i++)
                data[i] = nums[i];

            segmentTree = new SegmentTree<>(data, (a, b) -> a + b);
        }
    }

    public void update(int index, int val) {

        if (segmentTree == null)
            throw new RuntimeException("Segment is null.");

        segmentTree.set(index, val);
    }

    private int SumRange(int l, int r) {

        if (segmentTree == null)
            throw new IllegalArgumentException("Segment Tree is null.");

        return segmentTree.query(l, r);
    }
}
