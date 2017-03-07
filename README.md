# multithreaded-fault-tolerant-sorter
### follows Recovery Blocks algorithm (RcB) as specified by Pullum 
Fault-tolerant sorter of number files using recovery blocks. Comes with number file generator:

java DataGenerator \<output_file\> \<amount_of_ints_to_generate\>

Simulates faults with given seeded random probabilities via command line:

java Data Sorter \<input_file\> \<output_file\> \<primary_failure_probability\> \<secondary_failure_probability\> \<watchdog_timeout\>

Needs to export LD_LIBRARY variable properly to use insertionsort native method

Design Note:Tried to move all simulated errors down to the sorting routine level, and have the adjudicator handle it as if it was a real error

Will update this README

Some links I used to increase my knowledge
http://stackoverflow.com/questions/877096/how-can-i-pass-a-parameter-to-a-java-thread
http://stackoverflow.com/questions/6456219/java-checking-if-parseint-throws-exception
http://stackoverflow.com/questions/10706721/convert-liststring-to-listinteger-directly
