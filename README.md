# CSARCH2 Cache Simulation System Project A.Y. 123T1

#### Submitted by:
##### CSARCH2 S12 Group 9
ARCETA, Althea Zyrie M.

DEQUICO, Beverly Joyce P.

TAN, Jose Tristan T.

TIPAN, Loben Klien A.
---
### Walkthrough Video: https://drive.google.com/file/d/1d2ewiHJMMZ4alPS1fYgELQPkVDW3aecG/view?usp=sharing

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
###### 8-way BSA Cache Table Format
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

In the sequential sequence, the memory blocks to be passed are 0, 1, 2, 3, ..., 63 {4x}. thus, with the cache initially empty, we expect to populate and miss. Given that we are using the Most Recently Used replacement algorithm, in the first pass after utilizing all of the cache blocks we will constantly be replacing the blocks in block 7 of each set. The output after the first pass is as follows: 

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

Using the formula `Total Access Time = H*C + M*(1+CL*MA)` where H is the hit count, C is the cache access time, M is the miss count, CL is the cache line size, and MA is the memory access time, we have `C = 1`, `CL = 64`, and `MA = 10` for L/T read policy.

Plugging in the values, we get `TAT = 96*1 + 160*(1+64*10) = 102656`.

Thus, the total memory access time is ***102656***.

##### b.) Random Sequence

In the random sequence, we are passing a total of 128 blocks, in no particular order and assumed to be not necessarily unique. With that, we are unable to determine for certain the exact counts, rates, and access times for sure. This is because a random non unique sequence can vary heavily, particularly depending on the maximum memory block value that is set in generating the sequence. In the best case, the number of misses will be equal to the number of unique memory blocks that are present in the sequence, and the remaining blocks are hits. This is assuming that there is enough space in the cache to accomodate the number of unique blocks, i.e. at most 32 unique values in this case. In the worst case, all the blocks are unique and every memory access is a cache miss. This 0 hit all miss result is also possible if the sequence 
just so happens to only repeat a specific memory block in a set after it has been filled up and then overwritten by another block. This is due to the 8-way block set associative design.

Thus, this results in ***128*** memory accesses, a cache hit count that ***can be as low as 0 and can only be as high as 1 less than the memory accesses***, a cache miss count that is ***at least 1 and can be as high as equal to the memory accesses***, with its corresponding hit/miss rates, as well as largely varying access times.


##### c.) Mid-Repeat Blocks: 

In the mid-repeat blocks test case, the memory blocks to be passed are 0, 1, 2, 3, ..., 29, 30, 1, 2, 3, ... 29, 30, ..., 62, 63 {4x}. Thus, we again are expected to populate and miss, however having a mid repeat allows us to make use of our initially populated blocks and have hits on the first pass. The output after the first pass is as follows:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     12    |     16    |     20    |     24    |     28    |
|**Age**|     0     |     1     |     2     |     3     |     4     |     5     |     6     |     7     |
| **1** |     1     |     5     |     9     |     13    |     17    |     21    |     25    |     29    |
|**Age**|     0     |     1     |     2     |     3     |     4     |     5     |     6     |     7     |
| **2** |     2     |     6     |     10    |     14    |     18    |     22    |     26    |     30    |
|**Age**|     0     |     1     |     2     |     3     |     4     |     5     |     6     |     7     |
| **3** |     3     |     7     |     11    |     15    |     19    |     23    |     27    |           |
|**Age**|     0     |     1     |     2     |     3     |     4     |     5     |     6     |           |

This is the inititial 0-30 of the sequence. The midrepeat sequence thus accesses the memory blocks 1-30 once again, providing a hit on all accesses. The output now looks as follows:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     12    |     16    |     20    |     24    |     28    |
|**Age**|     0     |     8     |     9     |     10    |     11    |     12    |     13    |     14    |
| **1** |     1     |     5     |     9     |     13    |     17    |     21    |     25    |     29    |
|**Age**|     8     |     9     |     10    |     11    |     12    |     13    |     14    |     15    |
| **2** |     2     |     6     |     10    |     14    |     18    |     22    |     26    |     30    |
|**Age**|     8     |     9     |     10    |     11    |     12    |     13    |     14    |     15    |
| **3** |     3     |     7     |     11    |     15    |     19    |     23    |     27    |           |
|**Age**|     7     |     8     |     9     |     10    |     11    |     12    |     13    |           |

Then, the sequence proceeds from 31-63. Given that we are using the Most Recently Used replacement algorithm, 31 would first fill out the last remaining available block in set 3 then the rest would proceed in constantly replacing the blocks in block 7 of each set. The result of this is now as shown below:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     12    |     16    |     20    |     24    |     60    |
|**Age**|     0     |     8     |     9     |     10    |     11    |     12    |     13    |     22    |
| **1** |     1     |     5     |     9     |     13    |     17    |     21    |     25    |     61    |
|**Age**|     8     |     9     |     10    |     11    |     12    |     13    |     14    |     23    |
| **2** |     2     |     6     |     10    |     14    |     18    |     22    |     26    |     62    |
|**Age**|     8     |     9     |     10    |     11    |     12    |     13    |     14    |     23    |
| **3** |     3     |     7     |     11    |     15    |     19    |     23    |     27    |     63    |
|**Age**|     7     |     8     |     9     |     10    |     11    |     12    |     13    |     22    |

And this is the first pass output. This results to 94 memory accesses, 30 cache hits, and 64 cache misses.

On the second pass, we again now have memory blocks in the cache. From the table above, we see that we are able to hit blocks 0-27, and will miss blocks 28-30. The result of this is as shown:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     12    |     16    |     20    |     28    |     60    |
|**Age**|     23    |     24    |     25    |     26    |     27    |     28    |     30    |     22    |
| **1** |     1     |     5     |     9     |     13    |     17    |     21    |     29    |     61    |
|**Age**|     24    |     25    |     26    |     27    |     28    |     29    |     31    |     23    |
| **2** |     2     |     6     |     10    |     14    |     18    |     22    |     30    |     62    |
|**Age**|     24    |     25    |     26    |     27    |     28    |     29    |     31    |     23    |
| **3** |     3     |     7     |     11    |     15    |     19    |     23    |     27    |     63    |
|**Age**|     23    |     24    |     25    |     26    |     27    |     28    |     29    |     22    |

The midrepeat then accesses blocks 1-30 again, providing a hit on 1-23, missing 24-26, and hitting 27-30. The output now looks as follows:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     12    |     16    |     24    |     28    |     60    |
|**Age**|     23    |     31    |     32    |     33    |     34    |     36    |     37    |     22    |
| **1** |     1     |     5     |     9     |     13    |     17    |     25    |     29    |     61    |
|**Age**|     32    |     33    |     34    |     35    |     36    |     38    |     39    |     23    |
| **2** |     2     |     6     |     10    |     14    |     18    |     26    |     30    |     62    |
|**Age**|     32    |     33    |     34    |     35    |     36    |     38    |     39    |     23    |
| **3** |     3     |     7     |     11    |     15    |     19    |     23    |     27    |     63    |
|**Age**|     30    |     31    |     32    |     33    |     34    |     35    |     36    |     22    |

Then, the sequence proceeds again from 31-63. Using MRU, this sequence would constantly replace the blocks in block 6 of each set for 31-59, then hits 60-63. The result of this is as shown below:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     12    |     16    |     24    |     56    |     60    |
|**Age**|     23    |     31    |     32    |     33    |     34    |     36    |     44    |     45    |
| **1** |     1     |     5     |     9     |     13    |     17    |     25    |     57    |     61    |
|**Age**|     32    |     33    |     34    |     35    |     36    |     38    |     46    |     47    |
| **2** |     2     |     6     |     10    |     14    |     18    |     26    |     58    |     62    |
|**Age**|     32    |     33    |     34    |     35    |     36    |     38    |     46    |     47    |
| **3** |     3     |     7     |     11    |     15    |     19    |     23    |     59    |     63    |
|**Age**|     30    |     31    |     32    |     33    |     34    |     35    |     44    |     45    |

This is then the second pass output. This results to another 94 memory accesses, 59 cache hits, and 35 cache misses.

The third pass will encounter a similar situation as the second pass. We will hit 0-19, miss 20-22, hit 23-26, and miss 27-30. The result of this is shown below:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     12    |     20    |     28    |     56    |     60    |
|**Age**|     46    |     47    |     48    |     49    |     51    |     53    |     44    |     45    |
| **1** |     1     |     5     |     9     |     13    |     21    |     29    |     57    |     61    |
|**Age**|     48    |     49    |     50    |     51    |     53    |     55    |     46    |     47    |
| **2** |     2     |     6     |     10    |     14    |     22    |     30    |     58    |     62    |
|**Age**|     48    |     49    |     50    |     51    |     53    |     55    |     46    |     47    |
| **3** |     3     |     7     |     11    |     15    |     19    |     27    |     59    |     63    |
|**Age**|     46    |     47    |     48    |     49    |     50    |     52    |     44    |     45    |

The midrepeat accesses 1-30 again, which gives a hit for 1-15, miss 16-18, hit 19-22, miss 23-26, and hit 27-30. The output is then updated below:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     16    |     24    |     28    |     56    |     60    |
|**Age**|     46    |     54    |     55    |     57    |     59    |     60    |     44    |     45    |
| **1** |     1     |     5     |     9     |     17    |     25    |     29    |     57    |     61    |
|**Age**|     56    |     57    |     58    |     60    |     62    |     63    |     46    |     47    |
| **2** |     2     |     6     |     10    |     18    |     26    |     30    |     58    |     62    |
|**Age**|     56    |     57    |     58    |     60    |     62    |     63    |     46    |     47    |
| **3** |     3     |     7     |     11    |     15    |     23    |     27    |     59    |     63    |
|**Age**|     53    |     54    |     55    |     56    |     58    |     59    |     44    |     45    |

The sequence then proceeds again from 31-63. Using MRU, this sequence will constantly replace the blocks in block 5 of each set for 31-55, then hits 56-63. The result of this is as displayed:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     8     |     16    |     24    |     52    |     56    |     60    |
|**Age**|     46    |     54    |     55    |     57    |     59    |     66    |     67    |     68    |
| **1** |     1     |     5     |     9     |     17    |     25    |     53    |     57    |     61    |
|**Age**|     56    |     57    |     58    |     60    |     62    |     69    |     70    |     71    |
| **2** |     2     |     6     |     10    |     18    |     26    |     54    |     58    |     62    |
|**Age**|     56    |     57    |     58    |     60    |     62    |     69    |     70    |     71    |
| **3** |     3     |     7     |     11    |     15    |     23    |     55    |     59    |     63    |
|**Age**|     53    |     54    |     55    |     56    |     58    |     66    |     67    |     68    |

Shown above is the third pass output. This results in another 94 memory accesses, 55 cache hits, and 39 cache misses.

On the fourth pass, we will once again encounter a similar situation. We will hit 0-11, miss 12-14, hit 15-18, miss 19-22, hit 23-26, and miss 27-30. The result is shown below:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     4     |     12    |     20    |     28    |     52    |     56    |     60    |
|**Age**|     69    |     70    |     72    |     74    |     76    |     66    |     67    |     68    |
| **1** |     1     |     5     |     13    |     21    |     29    |     53    |     57    |     61    |
|**Age**|     72    |     73    |     75    |     77    |     79    |     69    |     70    |     71    |
| **2** |     2     |     6     |     14    |     22    |     30    |     54    |     58    |     62    |
|**Age**|     72    |     73    |     75    |     77    |     79    |     69    |     70    |     71    |
| **3** |     3     |     7     |     11    |     19    |     27    |     55    |     59    |     63    |
|**Age**|     69    |     70    |     71    |     73    |     75    |     66    |     67    |     68    |

The midrepeat accesses 1-30 once more, which gives a hit for 1-7, miss 8-10, hit 11-14, miss 15-18, hit 19-22, miss 23-26, and hit 27-30. The output is now shown as:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     8     |     16    |     24    |     28    |     52    |     56    |     60    |
|**Age**|     69    |     78    |     80    |     82    |     83    |     66    |     67    |     68    |
| **1** |     1     |     9     |     17    |     25    |     29    |     53    |     57    |     61    |
|**Age**|     80    |     82    |     84    |     86    |     87    |     69    |     70    |     71    |
| **2** |     2     |     10    |     18    |     26    |     30    |     54    |     58    |     62    |
|**Age**|     80    |     82    |     84    |     86    |     87    |     69    |     70    |     71    |
| **3** |     3     |     7     |     15    |     23    |     27    |     55    |     59    |     63    |
|**Age**|     76    |     77    |     79    |     81    |     82    |     66    |     67    |     68    |

The sequence finally proceeds from 31-63. With MRU, this will constantly replace the values in block 4 of each set for 31-51, then hit 52-63. This results in the following:

|  Set  |  Block 0  |  Block 1  |  Block 2  |  Block 3  |  Block 4  |  Block 5  |  Block 6  |  Block 7  |
| :---: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: | :-------: |
| **0** |     0     |     8     |     16    |     24    |     48    |     52    |     56    |     60    |
|**Age**|     69    |     78    |     80    |     82    |     88    |     89    |     90    |     91    |
| **1** |     1     |     9     |     17    |     25    |     49    |     53    |     57    |     61    |
|**Age**|     80    |     82    |     84    |     86    |     92    |     93    |     94    |     95    |
| **2** |     2     |     10    |     18    |     26    |     50    |     54    |     58    |     62    |
|**Age**|     80    |     82    |     84    |     86    |     92    |     93    |     94    |     95    |
| **3** |     3     |     7     |     15    |     23    |     51    |     55    |     59    |     63    |
|**Age**|     76    |     77    |     79    |     81    |     88    |     89    |     90    |     91    |

This results to 94 memory accesses, 51 cache hits, and 43 cache misses.

In total, this results in ***376*** memory accesses, ***195*** cache hits, and ***181*** cache misses.

The cache hit rate is ***0.5186***, and the cache miss rate is ***0.4814***.

From the same formula as above, we plug in the values and get `AAT = (0.5186)*1 + (0.4814)*326 = 157.455`.

Thus, the average memory access time is ***157.455***.

Also from the same formula previously we plug in the values and get `TAT = 195*1 + 181*(1+64*10) = 116216`.

Thus, the total memory access time is ***116216***.
