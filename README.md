# File system visitor pattern
Zhannur Bakyt SE - 2524
## Project overview
This project demonstrates the Visitor Design Pattern using a hierarchical file system.\
The Visitor Pattern separates operations from the file system elements, making it possible to add new operations without modifying the existing element classes.
## Project structure
```
src/
|-main/
|   |-java/
|   |   |-model/
|   |   |   |-FileSystemElement.java
|   |   |   |-AbstractFileSystemElement.java
|   |   |   |-FileNode.java
|   |   |   |-DirectoryNode.java
|   |   |-visitor/
|   |   |   |-FileSystemVisitor.java
|   |   |   |-RecursiveFileSystemVisitor.java
|   |   |   |-concrete/
|   |   |   |   |-SizeVisitor.java
|   |   |   |   |-SearchVisitor.java
|   |   |   |   |-StatisticVisitor.java
|   |   |-Main.java
|-test/
|   |-java/
|   |   |-Tests.java
```
## Architecture and design
### 1. Object model
The file system uses the following classes:
- FileSystemElement defines the common interface for all file system elements.
- AbstractFileSystemElement provides shared parent-directory functionality.
- FileNode represents a file with a name and size.
- DirectoryNode represents a directory containing files and other directories.
### 2. Visitor pattern
The FileSystemVisitor interface defines two visit methods:
- visitFile(FileNode file)
- visitDirectory(DirectoryNode directory)

Each element implements the accept() method and delegates the operation to the corresponding visitor method.\
This implements double dispatch and keeps operations separate from the element classes.
### 3. Recursive traversal
RecursiveFileSystemVisitor provides a shared recursive traversal implementation.\
When a directory is visited, the visitor processes each child by calling accept().\
This ensures that nested directories and their contents are traversed.
### 4. Concrete visitors
The project contains three visitors:
- SizeVisitor - calculates the total size of all files in the file system subtree.
- SearchVisitor - searches for files by filename and minimum file size. Search results contain root-relative paths and are sorted alphabetically.
- StatisticVisitor - Calculates total number of files, directories and maximum directory depth. The root directory has depth 0.
## Demonstration
The Main class creates a sample file system:
```
root/
|-documents/
|   |-report.pdf  110bytes
|   |-notes.txt  70bytes
|-images/
|   |-photo.png  205bytes
|   |-report.pdf  350bytes
|-backup/
|   |-report.pdf  380bytes
```
The application demonstrates all three visitors.
### Expected output
```
Total size: 1115
Search result: [backup/report.pdf, documents/report.pdf, images/report.pdf]
File count: 5
Directory count: 4
Max depth: 2
```
## Testing
This project uses JUnit 5 to verify the implementation.
Test suite covers:
1. Correct visitor method dispatch.
2. Total file size calculation.
3. Search using filename and minimum size conditions.
4. Alphabetically sorted search paths.
5. File and directory statistics.
6. Empty directories and searches without matches.
7. Independent visitor instances.
8. File system tree integrity.

## How to run
Compile the project
```
mvn compile
```
Run the tests
```
mvn clean test
```
Run the project
## Extensibility
The Visitor Pattern allows new operations to be added by implementing the existing FileSystemVisitor interface.\
For example, StatisticVisitor was added without modifying FileNode, DirectoryNode, or their accept() methods.\
This demonstrates the extensibility of the design and follows the Open/Closed principle.
