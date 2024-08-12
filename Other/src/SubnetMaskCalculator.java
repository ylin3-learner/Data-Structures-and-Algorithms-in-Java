import java.util.ArrayList;
import java.util.List;

public class SubnetMaskCalculator {

    public static String IPv4calculateSubnetMask(int prefixLength) {

        // 初始化子網掩碼為 32 位的 0
        int mask = 0;

        // 將前 prefixLength 位設為 1
        for (int i = 0; i < prefixLength; i++)
            // 1 << (31 - i) 表示將 1 往最高位移 (左移), 在移動途中會添加0
            // mask |= (1 << (31 - i)) 使用 '|=' 將當前的值和 1 << (31 - i) 合併, 將 mask 特定位置設為 1
            mask |= (1 << (31 - i));



        String dottedDecimal = String.format("%d.%d.%d.%d",
                // 0xFF 在二進制中的表示是 11111111，即所有的 8 位都是 1。當我們對一個整數進行按位與操作（&）時，它可以用來提取該整數的最低 8 位。
                /*
                int value = 0x12345678;
                int result = value & 0xFF; // result = 0x78
                 */
                // 右移 24 位，獲取最高的 8 位
                (mask >> 24) & 0xFF,
                (mask >> 16) & 0xFF,
                (mask >> 8) & 0xFF,
                 mask & 0xFF
        );

        // 打印二進制表示
        String binaryString = String.format("%32s", Integer.toBinaryString(mask));
        StringBuilder formattedBinaryString = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i++) {
            if (i > 0 && i % 8 == 0) {
                formattedBinaryString.append(" ");
            }
            formattedBinaryString.append(binaryString.charAt(i));
        }

        // 打印二進制格式的子網掩碼
        System.out.println("Binary format with spaces: " + formattedBinaryString);

        return dottedDecimal;
    }

    public static boolean haveSameNetworkID(List<String> ipAddresses, int prefixLength) {
        if (ipAddresses == null && ipAddresses.size() < 2) {
            throw new IllegalArgumentException("Must provide at least two ip addresses!");
        }

        // Transfer Ipv4 into binary digits
        int subnetMask = 0;
        for (int i = 0; i < prefixLength; i++)
            subnetMask |= (1 << (31 - i));

        // Calculate network ID
        Integer networkID = null;
        for (String ipAddress: ipAddresses) {
            int ip = convertIpToInt(ipAddress);
            int currentNetworkID = ip & subnetMask;
            if (networkID == null)
                networkID = currentNetworkID;
            else if (!networkID.equals(currentNetworkID)) {
                System.out.println("\nIP address not in the same network ID: " + ipAddress);
                return false;
            }
        }

        return true;
    }

    private static int convertIpToInt(String ipAddress) {
        String[] parts = ipAddress.split("\\.");  // 255.255.0.0 -> "255", "255", "0", "0"
        int ip = 0;
        for (int i = 0; i < parts.length; i++) {
            ip |= Integer.parseInt(parts[i]) << (24 - (8 * i));
        }

        return ip;
    }

    public static void main(String[] args) {
        List<String> ipAddresses = new ArrayList<>();

        ipAddresses.add("42.31.1.2");
        ipAddresses.add("42.31.93.94");
        ipAddresses.add("125.125.3.60");

        int prefixLength = 14;
        System.out.println("Prefix Length: " + prefixLength + ", its subnetMask: "+ IPv4calculateSubnetMask(prefixLength));

        boolean result = haveSameNetworkID(ipAddresses, prefixLength);
        System.out.println("Do the IP addresses have the same network ID? " + result);
    }
}
