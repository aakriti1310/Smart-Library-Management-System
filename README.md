# Smart Library Management System (Java)

A console-based Java project designed to demonstrate object-oriented programming,
file handling, an AVL tree, graph traversal, shortest paths, greedy algorithms,
and dynamic programming.

## Requirements
- JDK 17 or newer (Java 21 recommended)
- VS Code with Java extensions OR Eclipse

## Run in VS Code / terminal
1. Extract the ZIP file.
2. Open the `SmartLibraryManagementSystem` folder.
3. Open a terminal in that folder.
4. Compile:

   javac -d out src/*.java

5. Run:

   java -cp out Main

## Run in Eclipse
1. Open Eclipse and choose **File > Open Projects from File System**.
2. Select the extracted `SmartLibraryManagementSystem` folder.
3. If Eclipse does not recognize it automatically, create a Java Project and
   copy all `.java` files from `src` into its `src` folder.
4. Right-click `Main.java` and select **Run As > Java Application**.

## Features
- Add, display, search, issue, return and remove books.
- Books are indexed by ID in an AVL tree.
- Book records are saved in `library-books.csv` in the working directory.
- Category graph demonstrates BFS, DFS and Dijkstra's shortest-path algorithm.
- Algorithm demonstrations include Activity Selection, 0/1 Knapsack and
  Fractional Knapsack.

## Sample data
On first run, four sample books are created automatically:
- 101 - The Alchemist
- 102 - Clean Code
- 103 - Wings of Fire
- 104 - Data Structures

The CSV data file is created automatically after the first save.

## Important
This is a console-based academic demonstration project. The graph categories and
algorithm examples use predefined demonstration data. No external libraries or
database installation are required.
