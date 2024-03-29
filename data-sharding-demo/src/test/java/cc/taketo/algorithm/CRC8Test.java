package cc.taketo.algorithm;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @author Zhangp
 * @date 2024/3/10 17:25
 */
public class CRC8Test {

    private static final int POLYNOMIAL = 0x07; // CRC8多项式
    private static final int INITIAL_VALUE = 0x00; // 初始值

    /**
     * 计算CRC8校验码
     *
     * @param data 要计算校验码的字节数组
     * @return CRC8校验码
     */
    public static int calculateCRC8(byte[] data) {
        int crc = INITIAL_VALUE;
        for (byte b : data) {
            crc ^= (b & 0xFF);
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x80) != 0) {
                    crc = (crc << 1) ^ POLYNOMIAL;
                } else {
                    crc <<= 1;
                }
            }
        }
        return crc & 0xFF;
    }

    @Test
    public void testCRC8() {
        int[] count = new int[8]; // 数字出现次数统计数组
        // 分布式ID
        long snowflakeId = 1766762715877081088L;

        for (int i = 0; i < 1000; i++) {
            // 计算CRC8值
            int crc8 = calculateCRC8(String.valueOf(snowflakeId + i).getBytes());
            // 统计数字出现次数
            count[crc8 % 8]++;
        }

        // 打印每个数字出现的次数
        for (int i = 0; i < 8; i++) {
            System.out.println("数字 " + (i) + " 出现次数: " + count[i]);
        }
    }
}
