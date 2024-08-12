import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class binarySearchExample {

    private binarySearchExample() {}

    public static int searchAlbum(List<String> albums, String targetName) {
        if (albums == null || targetName == null || albums.isEmpty()) {
            throw new IllegalArgumentException("Albums cannot be empty and target name cannot be null!");
        }

        Collections.sort(albums);

        return binarySearch(albums, targetName.toLowerCase(), 0, albums.size() - 1);
    }

    private static int binarySearch(List<String> list, String target, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;
        String midValue = list.get(mid).toLowerCase();

        int comparisonResult = midValue.compareTo(target);

        if (comparisonResult == 0) {
            return mid;
        } else if (comparisonResult < 0) {
            return binarySearch(list, target, mid + 1, right);  // 目标在右半部分
        } else {
            return binarySearch(list, target, left, mid - 1);  // 目标在左半部分
        }
    }

    public static void main(String[] args) {
        ArrayList<String> albums = new ArrayList<>();
        albums.add("Tony");
        albums.add("Yolanda");
        albums.add("Anna");

        // Sort before using binary sort
        Collections.sort(albums);
        System.out.println(albums);

        String[] namesToSearch = {"Tony", "Yolanda", "Anna", "Bob"};
        for (String name : namesToSearch) {
            int index = searchAlbum(albums, name);
            if (index != -1) {
                System.out.println(name + " found at index: " + index);
            } else {
                System.out.println(name + " not found in the album list.");
            }
        }
    }
}
