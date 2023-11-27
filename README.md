# CSARCH2 Cache Simulation System Project A.Y. 123T1

#### Submitted by:
##### Group 9
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

### Test Cases (*n* is the number of cache blocks):
1. Sequential sequence: up to 2*n* cache block. Repeat the sequence four times. Example: 0,1,2,3,...,2*n*-1 {4x}
2. Random sequence: containing 4*n* blocks.
3. Mid-repeat blocks: Start at block 0, repeat the sequence in the middle two times up to *n*-1 blocks, after which continue up to 2*n*. Then, repeat the sequence four times. Example: if *n*=8, sequence=0,1,2,3,4,5,6, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 {4x}

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










https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet
