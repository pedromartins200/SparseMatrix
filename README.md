# What is a sparse matrix

Basically a sparseMatrix is a very big matrix where only a few entrances are different than zero.
In a 1 000 000 x 1 000 000 matrix, where only 5 elements are different than zero, it would be very inneficient to store all those values, when in fact we could only store 5.

This type of matrix is used a lot in math and physics with real world applications specially in the climate area, there they deal a lot with huge calculations.
It makes sense that storing huge amounts of data, should be done as efficiently as possible.
```
![alt text](https://github.com/pedromartins200/SparseMatrix/blob/master/sparse_matrix.png)
```

# My implementation
The language used was Java.
For efficiency reasons it is completely out of question to use the naïf approach of employing a bidimensional array to save all the matrix elements including the zero ones. That would simplify the implementation of operations, but would be also extremely expensive in terms of the required storage space.