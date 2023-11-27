import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

public class CacheSimulation {
    // Constants
    private static final int NUM_CACHE_BLOCKS = 32;
    private static final int NUM_SETS = 8;
    private static final int WAYS_PER_SET = 4; // Assuming 4 ways per set

    // Cache data structure
    private String[][] cacheData;
    private Map<Integer, ArrayList<Integer>> mruTracker; // To track MRU for each set

    // Constructor
    public CacheSimulation() {
        // Initialize cache data structure and MRU tracker
        cacheData = new String[NUM_CACHE_BLOCKS][3]; // [Set, Way, Data]
        mruTracker = new HashMap<>();
        initializeCache();
    }

    // Initialize cache with empty data
    private void initializeCache() {
        for (int i = 0; i < NUM_CACHE_BLOCKS; i++) {
            cacheData[i][0] = String.valueOf(i / WAYS_PER_SET); // Set number
            cacheData[i][1] = String.valueOf(i % WAYS_PER_SET); // Way number
            cacheData[i][2] = ""; // Data initially empty

            // Initialize MRU tracker
            int setNumber = i / WAYS_PER_SET;
            mruTracker.putIfAbsent(setNumber, new ArrayList<>());
        }
    }

    // Simulate cache method
    public String[][] simulate(int numMemoryBlocks, ArrayList<String> inputSequence, boolean finalSnapshot) {
        for (String input : inputSequence) {
            try {
                int memoryBlock = Integer.parseInt(input);
                if (memoryBlock < numMemoryBlocks) {
                    int setIndex = memoryBlock % NUM_SETS;
                    processMemoryAccess(memoryBlock, setIndex);
                }
            } catch (NumberFormatException e) {
                System.out.println("Skipping invalid input: " + input);
            }
        }
        return cacheData; // Return the final state of the cache
    }

    private void processMemoryAccess(int memoryBlock, int setIndex) {
        int startIndex = setIndex * WAYS_PER_SET;
        int endIndex = startIndex + WAYS_PER_SET;
        boolean hit = false;

        // Check for hit
        for (int i = startIndex; i < endIndex; i++) {
            if (cacheData[i][2].equals(String.valueOf(memoryBlock))) {
                hit = true;
                updateMRU(setIndex, memoryBlock);
                break;
            }
        }

        // Process miss
        if (!hit) {
            handleCacheMiss(setIndex, memoryBlock);
        }
    }

    // Update MRU list for a set
    private void updateMRU(int setIndex, int memoryBlock) {
        ArrayList<Integer> mruList = mruTracker.get(setIndex);
        mruList.remove(Integer.valueOf(memoryBlock)); // Remove if present
        mruList.add(0, memoryBlock); // Add to the front as most recently used
    }

    // Handle a cache miss
    private void handleCacheMiss(int setIndex, int memoryBlock) {
        ArrayList<Integer> mruList = mruTracker.get(setIndex);
        int startIndex = setIndex * WAYS_PER_SET;

        if (mruList.size() < WAYS_PER_SET) {
            // There's space in the cache set
            cacheData[startIndex + mruList.size()][2] = String.valueOf(memoryBlock);
            mruList.add(0, memoryBlock);
        } else {
            // Replace the least recently used block
            int lruBlock = mruList.remove(mruList.size() - 1);
            for (int i = startIndex; i < startIndex + WAYS_PER_SET; i++) {
                if (cacheData[i][2].equals(String.valueOf(lruBlock))) {
                    cacheData[i][2] = String.valueOf(memoryBlock);
                    break;
                }
            }
            mruList.add(0, memoryBlock);
        }
    }
}
