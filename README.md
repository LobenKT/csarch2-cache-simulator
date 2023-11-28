# CSARCH2 Cache Simulation System Project A.Y. 123T1

#### Submitted by:
##### CSARCH2 S12 Group 9
ARCETA, Althea Zyrie M.

DEQUICO, Beverly Joyce P.

TAN, Jose Tristan T.

TIPAN, Loben Klien A.

## Full Specifications

1. Number of cache blocks = 32 blocks
2. Cache line = 64 words
3. Read policy: load-through
4. Number of memory blocks = user inputs
5. Type of cache memory: ***8-way Block Set Associative + Most Recently Used***

---
### Test Cases (*n* is the number of cache blocks):
1. Sequential sequence: up to 2*n* cache block. Repeat the sequence four times. Example: 0,1,2,3,...,2*n*-1 {4x}
2. Random sequence: containing 4*n* blocks.
3. Mid-repeat blocks: Start at block 0, repeat the sequence in the middle two times up to *n*-1 blocks, after which continue up to 2*n*. Then, repeat the sequence four times. Example: if *n*=8, sequence=0,1,2,3,4,5,6, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 {4x}
---
### Test Case Detail Analyses:
###### Cache Table Format
|  Set  | Block 0 | Block 1 | Block 2 | Block 3 | Block 4 | Block 5 | Block 6 | Block 7 |
| :---: | ------- | ------- | ------- | ------- | ------- | ------- | ------- | ------- |
|   0   |         |         |         |         |         |         |         |         |
|  Age  |         |         |         |         |         |         |         |         |
|   1   |         |         |         |         |         |         |         |         |
|  Age  |         |         |         |         |         |         |         |         |
|   2   |         |         |         |         |         |         |         |         |
|  Age  |         |         |         |         |         |         |         |         |
|   3   |         |         |         |         |         |         |         |         |
|  Age  |         |         |         |         |         |         |         |         |

##### a.) Sequential Sequence

In the sequential sequence, the memory blocks to be passed are 0, 1, 2, 3, ..., 63 {4x}. thus, with the cache initially empty, we expect to populate and miss. given that we are using the Most Recently Used replacement algorithm, in the first pass after utilizing all of the cache blocks we will constantly be replacing the blocks in block 7 of each set. The output after the first pass is as follows: 

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     12    |     16    |     20    |     24    |     60    |
|**Age**|     0     |     1     |     2     |     3     |     4     |     5     |     6     |     15    |
| **1** |     1     |     5     |     9     |     13    |     17    |     21    |     25    |     61    |
|**Age**|     0     |     1     |     2     |     3     |     4     |     5     |     6     |     15    |
| **2** |     2     |     6     |     10    |     14    |     18    |     22    |     26    |     62    |
|**Age**|     0     |     1     |     2     |     3     |     4     |     5     |     6     |     15    |
| **3** |     3     |     7     |     11    |     15    |     19    |     23    |     27    |     63    |
|**Age**|     0     |     1     |     2     |     3     |     4     |     5     |     6     |     15    |

This results to 64 memory accesses, 0 cache hits, and 64 cache misses.

On the second pass, we now have memory blocks in the cache. Thus, as we can see from the table above, we are able to hit blocks 0-27, and since we are using MRU, we then will be constantly replacing the blocks in block 6 of each set, from 28-59, and finally will hit from 60-63. The output after the second pass is as follows:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     12    |     16    |     20    |     56    |     60    |
|**Age**|     16    |     17    |     18    |     19    |     20    |     21    |     30    |     31    |
| **1** |     1     |     5     |     9     |     13    |     17    |     21    |     57    |     61    |
|**Age**|     16    |     17    |     18    |     19    |     20    |     21    |     30    |     31    |
| **2** |     2     |     6     |     10    |     14    |     18    |     22    |     58    |     62    |
|**Age**|     16    |     17    |     18    |     19    |     20    |     21    |     30    |     31    |
| **3** |     3     |     7     |     11    |     15    |     19    |     23    |     59    |     63    |
|**Age**|     16    |     17    |     18    |     19    |     20    |     21    |     30    |     31    |

This results to 64 memory accesses, 32 cache hits, and 32 cache misses.

On the third pass, we will be encountering a similar situationas the second pass, albeit slightly shifted. We will be able to hit blocks 0-23, constantly replacing the blocks in block 5 of each set, from 24-55, and hit from 56-63. The output after the third pass is as follows:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     12    |     16    |     52    |     56    |     60    |
|**Age**|     32    |     33    |     34    |     35    |     36    |     45    |     46    |     47    |
| **1** |     1     |     5     |     9     |     13    |     17    |     53    |     57    |     61    |
|**Age**|     32    |     33    |     34    |     35    |     36    |     45    |     46    |     47    |
| **2** |     2     |     6     |     10    |     14    |     18    |     54    |     58    |     62    |
|**Age**|     32    |     33    |     34    |     35    |     36    |     45    |     46    |     47    |
| **3** |     3     |     7     |     11    |     15    |     19    |     55    |     59    |     63    |
|**Age**|     32    |     33    |     34    |     35    |     36    |     45    |     46    |     47    |

This results to 64 memory accesses, 32 cache hits, and 32 cache misses.

On the fourth pass, we again will encounter a similar situation. We are able to hit blocks 0-19, constantly replacing the blocks in block 4 of each set, from 20-51, and hit from 52-63. The output after the fourth pass is as follows:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     12    |     48    |     52    |     56    |     60    |
|**Age**|     48    |     49    |     50    |     51    |     60    |     61    |     62    |     63    |
| **1** |     1     |     5     |     9     |     13    |     49    |     53    |     57    |     61    |
|**Age**|     48    |     49    |     50    |     51    |     60    |     61    |     62    |     63    |
| **2** |     2     |     6     |     10    |     14    |     50    |     54    |     58    |     62    |
|**Age**|     48    |     49    |     50    |     51    |     60    |     61    |     62    |     63    |
| **3** |     3     |     7     |     11    |     15    |     51    |     55    |     59    |     63    |
|**Age**|     48    |     49    |     50    |     51    |     60    |     61    |     62    |     63    |

This results to 64 memory accesses, 32 cache hits, and 32 cache misses.

In total, this results in ***256*** memory accesses, ***96*** cache hits, and ***160*** cache misses.

The cache hit rate is ***0.375***, and the cache miss rate is ***0.625***.

From the formula `Average Access Time = h*C + (1-h)M` where h is the hit rate, C is the cache access time and M is the average miss penalty, we have `C = 1`, and `M = ((10 + 1) + (640 + 1))/2 = 326` for L/T read policy.

Plugging in the values, we get `AAT = (0.375)*1 + (0.625)*326 = 204.125`.

Thus, the average memory access time is ***204.125***.

Using the  formula `Total Access Time = H*C + M*(1+CL*MA)` where H is the hit count, C is the cache access time, M is the miss count, CL is the cache line size, and MA is the memory access time, we have `C = 1`, `CL = 64`, and `MA = 10` for L/T read policy.

Plugging in the values, we get `TAT = 96*1 + 160*(1+64*10) = 102656`.

Thus, the total memory access time is ***102656***.
