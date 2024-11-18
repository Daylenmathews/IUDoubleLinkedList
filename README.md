****************
* Double Linked List with tester
* CS221
* 11/14/2024
* Daylen Mathews
**************** 

OVERVIEW:

 This program creates a double linked list data structure that can store objects of any type. It also includes a test file to verify the correct behavior and proper functiality of the double linked list. It covers methods such as addition, removal, iteration, and boundary checks. 


INCLUDED FILES:

 * IUDoubleLinkedList.java - main fil that creates dll, source file
 * ListTester.java - tester scenario file, source file
 *IndexedUnsortedList.java - An interface that defines behavior of dll, source file
 *Node.java - A helper class used to define nodes for the dll, source file
 * README - this file, explains usages of each part of the project


COMPILING AND RUNNING:

 To run the program correctly, place and ensure all 5 files are in the same directory.
 Then, compile the driver file and list tester by putting into the terminal;

   $ javac ListTester.java

This generates the compiles files in the same directory. Once this is verified, the tester is ran using 

    $java ListTester

This output will display numerous tests to ensure the dll functions as expected. Including all of the methods such as adding,removing, and iterating over elements. The program runs over 9,000 test scenarios to check all possible outcomes and behaviors. 


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 The first part of the Program design is the IndexedUnsortedList. 
 This functions as a basic interface for the double linked list, and also defines the expected behaviors such as adding, setting, getting, and removing elements. It also specifies how errors should be handled when runnning the programs. 

 The next part of the design is the Node.java class. 
 This class represents the building blocks of the list. This class has instance variables that hold any object, a nextNode, or a previousNode. Each nodes stores a generic element. The node class also has constructors for creating nodes with or without connections to other nodes. Some of the methods include getNextNode, setNextNode, getPreviousNode, and setPreviousNode.

 The third part of the design is the IUDoubleLinkedList.
 This class implments the indexedUnsortedList interface. This provides details to managae the linked list. It includes instance variables such as the head and tail which are references to first and last nodes, also the size to keep track of the eleemnts, and versionNumber to help the iterator detect modiications to the list. It also contains a private inner class, DLLIterator to support bidirectional traversal and modification of the list. 

 The next part is the List Iterator itself or the DLLIterator. This is a private class within the dll and implments ListIterator. Some key features to this is that it can traverse backwards and forwards.Some of the methods for this include next, previous, add, remove, set, and more. This also allows you to modify the list during iteration.It also ensures conistency by comparing the iterators versionNumber with the lists versionNumber. 

 The last part is the ListTester class.
 The ListTester acts as a comprehensive test suite for the dll. It tests various scenarios that include edge cases as well as error handling to ensure that all methods function as expected. There are rougly around 9000 tests being ran. This ListTester uses different element test such as 1 element or 2-3 element tests to run. It also uses concurrency checks to test the dll.  

I designed my program this way because it made the most sense to be able to allow my dll and listtester to work together. This program design ensures and prioritizes reusability, clarity, and modularity. I designed the dll the way it is for its ability to traverse in both directions which reduces the runtime for certain operations in a single linked list. I also decided for minimal code duplication by reusing methods and leveraging helper functions for index bounds checks and element traversal. Although, I do think I could have done a little better in this part. I also tried my best to have the iterator to modify the list during concurrent additions/removals from the main thread, but not sure that I designed this the right way. 


TESTING:

 My testing strategy for this program was utilizing the ListTester class. This ListTester class ran extensive test cases that run automatically. These test include valid operations, boundary conditions, and invalid inputs. There are also concurrency tests which ensure iterators detect structural modificatiions. With the results of this Tester, there are valid operations which produce expected outputs such as successfully adding/removing elements or updating node values. Invalid scenarios throw appropriate excpetions. There also may be some known isssues with the results.Some scenarios lists of size >3 may not be throughly tested. Also, possible oppurtunities for further reducing code duplication, especially in index validation logic. 


DISCUSSION:
 
 Overall, I enjoyed creating and designing the double linked list. Creating this list highlighted the balance between functionality and complexity. Although I ran into many challenges and faced many bugs and errors, I enjoyed the process. Implementing some of the elements and add methods was harder initially, but came more natural as I went on. I also had some trouble figuring out the Iterator and how to correctly handle it and the concurrent modifications required. I also had some trouble with the ListTester, not fully figuring it out. I could add to the test class and expand it to cover larger lists and more edge classes. Some methods could be refactored to reduce redundancy, particularly with the index validation side. The design demonstrates understanding of data structures and their practical applications. In the end, I enoyed this project and the creation process of it. 


----------------------------------------------------------------------------

