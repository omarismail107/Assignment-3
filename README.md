# Assignment-3
Question 1: 

Summary/Proof: presents.java implements the slightly modified optimistic synchronization linked list algorithm from the "Art of Multiprocessor Programming" textbook. The premise of this algorithm is to take a chance by searching without locks, when the nodes are found, lock the nodes, and then validate that the locked nodes are correct in the linked list. Each of the 4 servants act as a thread either trying to remove or add an item to the linked list. Every servant first adds the next present to the linked list, then creates a thank you letter for that same present. After all the threads are done, and we remove the node from the linked list with a item value of 0 (we create this with our initialization of the linked list because we need to check pred and curr and we don't want to run into null pointers), we have successfully made 500,000 thank you letters.

Efficiency: The efficiency of this algorithm

Experimental Evaluation: I tested the program on various inputs ranging from 100,000 presents to 1,000,000 presents and the that number of thank you presents were generated everytime.

How to compile: First command: javac presents.java Second command: java presents

Question 2: 

Summary/Proof: 

Efficiency: 

Experimental Evaluation: I tested the program on various inputs ranging from 1 hour 100 hours and the program correctly displayed temperature information for each of these tests.

How to compile: First command: javac atmosphere.java Second command: java atmosphere
