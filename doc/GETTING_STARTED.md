IDE Setup
=========

This project contains a copy of all of the source code and library files
required to compile and execute it. However, in an attempt to remain IDE
agnostic, it does not contain any IDE project files.

If you're using the NetBeans Java IDE to work with this project, follow
the following procedure to create a new project with the existing sources.

1.
  File -> New Project... -> Java -> Java Project with Existing Sources

  [Next]

2.
  Project Name: sudoku

  Project Folder: Path to current branch (e.g. folder containing src, doc, lib)

  Build Script Name: build.xml

  Use Dedicated Folder for Storing libraries:
    Checked: False

  [Next]

3.
  Source Package Folders: [Path to current branch/src]

  [Finish]

4.
  Right click sudoku in NetBeans' project listing and select Properties.

  Navigate to Libraries, select Add JAR/Folder, add all libraries in
    [Path to current branch/lib]

  [OK]

5.
  Run Project (Green Triangle)

  Select constraint_puzzle_gui.SudokuApplication as main class

  [OK]  