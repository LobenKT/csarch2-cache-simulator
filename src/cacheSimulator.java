import java.util.HashMap;
import java.util.Map;

public class CacheSimulator {
    private int numberOfCacheBlocks;
    private int cacheLineSize;
    private int numberOfMemoryBlocks;
    private Map<Integer, String[]> cacheData; // Using a Map to simulate cache blocks

    public CacheSimulator(int numberOfCacheBlocks, int cacheLineSize, int numberOfMemoryBlocks) {
        this.numberOfCacheBlocks = numberOfCacheBlocks;
        this.cacheLineSize = cacheLineSize;
        this.numberOfMemoryBlocks = numberOfMemoryBlocks;
        this.cacheData = new HashMap<>();

        // Initialize cache data
        for (int i = 0; i < numberOfCacheBlocks; i++) {
            cacheData.put(i, new String[cacheLineSize]);
        }
    }

    public void accessMemory(int memoryAddress) {
        int cacheBlockIndex = memoryAddress % numberOfCacheBlocks;

        // Simulate cache hit or miss and update cache
        if (cacheData.containsKey(cacheBlockIndex)) {
            // Cache hit
            System.out.println("Cache Hit: Block " + cacheBlockIndex);
        } else {
            // Cache miss
            System.out.println("Cache Miss: Block " + cacheBlockIndex);

            // Simulate loading data into the cache block (replace existing data)
            String[] newData = new String[cacheLineSize];
            for (int i = 0; i < cacheLineSize; i++) {
                newData[i] = "Data" + (memoryAddress * cacheLineSize + i);
            }
            cacheData.put(cacheBlockIndex, newData);
        }
    }

    public void printCacheSnapshot() {
        System.out.println("Cache Memory Snapshot:");
        for (int blockIndex : cacheData.keySet()) {
            System.out.print("Block " + blockIndex + ": ");
            for (String data : cacheData.get(blockIndex)) {
                System.out.print(data + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
