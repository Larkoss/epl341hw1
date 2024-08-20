# A* Algorithm Implementation - EPL341 Artificial Intelligence

## Overview

This repository contains the implementation of the A* search algorithm as part of the first programming assignment for the Artificial Intelligence course (EPL341) at the University of Cyprus. The goal of the assignment is to guide a robot from a start position to a treasure in a maze, avoiding bombs and finding the optimal path with the lowest cost.

## Problem Description

The task involves navigating a robot from the start position `(0, 0)` to the treasure located at `(4, 10)` in a 5x11 grid maze. The robot can move up, down, left, or right, but not diagonally or into positions containing bombs. The goal is to implement the A* algorithm in either C or Java, utilizing two different heuristics to solve the problem and comparing their performance.

## Files Included

- `a_star_algorithm.c` or `a_star_algorithm.java`: The main implementation of the A* algorithm.
- `maze_input.txt`: Example input file defining the maze layout, start position, and treasure location.
- `123_xyz_asgn1.pdf`: Report including the comparison of the heuristics, analysis, and graphical representations.
- `README.md`: This file, providing an overview of the assignment and repository.

## Input File Structure

The input file (`maze_input.txt`) should be structured as follows:

5 11
00000001000
11001000010
00000000000
00100000001
00000101000
0 0
4 10

- The first line contains the maze dimensions (rows and columns).
- The following lines represent the maze, where `0` indicates an open space and `1` indicates a bomb.
- The penultimate line specifies the robot's starting position.
- The last line specifies the treasure's position.

## Compilation and Execution

To compile the code:

### For C:
```bash
gcc -o a_star a_star_algorithm.c
./a_star maze_input.txt
```

### For Java:
```
javac AStarAlgorithm.java
java AStarAlgorithm maze_input.txt
```

### Program Output
The program will output the optimal path if a solution exists, similar to the example below:
```
Path: [(0, 0), (0, 1), (0, 2), (0, 3), (0, 4), (0, 5), (0, 6), (1, 6), (1, 7), (1, 8), (2, 8), (2, 9), (3, 9), (4, 9), (4, 10)]
```
