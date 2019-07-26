# What is a sparse matrix

Basically a sparseMatrix is a very big matrix where only a few entrances are different than zero.
In a 1 000 000 x 1 000 000 matrix, where only 5 elements are different than zero, it would be very inneficient to store all those values, when in fact we could only store 5.

This type of matrix is used a lot in math and physics with real world applications specially in the climate area, there they deal a lot with huge calculations.
It makes sense that storing huge amounts of data, should be done as efficiently as possible.


# My implementation
The language used was Java.

For efficiency reasons it is completely out of question to use the naïf approach of employing a bidimensional array to save all the matrix elements including the zero ones. That would simplify the implementation of operations, but would be also extremely expensive in terms of the required storage space.

![alt text](https://github.com/pedromartins200/SparseMatrix/blob/master/sparse_matrix.png)

I used a doubly linked list, with sentinelNodes as shown in the picture above.

SentinelNodes makes it easier to navigate in the matrix and also its more efficiently memory and time-wise.

This is a complex implementation, but it's in my opinion the best way to implement this complex data structure.

# Example Usage

Currently, there are 3 operations, sum, multiplication by a scalar and matrix transpose. You can easily create more operations since the code is very generic.


**Input**
The first row of the input has three spaced separated natural numbers R, C, and N standing for the
number of rows, columns, and the number of non zero elements of the matrix, respectively.
The next N lines, are the non zero elements of the matrix, in the format, row number rn, column
number cn, and value, where rn ∈{0, 1, …, R-1}, cn ∈{0, 1, …, C-1}, and value is an Integer. The
elements are inputted in an ordered fashion, i.e., ascending in the number of rows and, in each row,
ascending in the number of columns.
The next input line has one (and only one) of the following possible characters:
 + (meaning that a sum of matrices should be computed)
 * (meaning that a product of matrix by a scalar should be computed)
t (meaning that the transposed of the matrix should be computed)
Depending on the previous inputted character, a new matrix will follow in the exact some format as
the initial one (case +), an integer will follow (case *) or nothing else will be given (case t).



**Output**
The output is always a matrix in exactly the same format of the inputted matrix


Sample input 1
10 100 2
0 0 1
9 99 2
+
10 100 3
0 0 1
2 2 3
9 99 -2

Sample output 1
10 100 2
0 0 2
2 2 3

Sample input 2
10 100 2
0 0 1
9 99 2
*
2

Sample output 2
10 100 2
0 0 2
9 99 4

Sample input 3
10 100 2
0 0 1
9 99 2
t

Sample output 3
100 10 2
0 0 1
99 9 2