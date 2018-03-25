# Digital Preservation - practical assignment 1.1

This repository is here for the first part of the first assignment of the practical assignment of the course digital preservation from the technical university in vienna.

# EXPERIMENT
The experiment used here is pretty simple. Two different open data repositories have been chosen, namely the statistical federal office of germany and the open data repository of the United States of America. From each repository a dataset was chosen and the data got preprocessed to then get visualized in a JAVA application. 


# DATA
In the data folder are three files. Two raw input files, namely [crimes_in_chicago_2001_to_2018](https://doi.org/10.5281/zenodo.1205219) and   [crimes_in_germany_1976_to_2016.csv](https://zenodo.org/record/1205342). The third file can be produced by executing the mapreducer [crimes_in_chicago_summized_per_year.csv](https://zenodo.org/record/1205333). The map reducer makes the sum of all crimes per year in chicago. This was mainly done because of performance reasons, otherwise the complete map reduce job would need to run every time the visualization is made.

# Reproducability
To reproduce our experiment, please use the files linked under the data section or download them from their original sources (Please keep in mind that the german file is without header + footer). Further build the two separate jar files for the mapreduce module and visualization module with gradle (gradle wrapper can also be used).

## Mapreduce
The map reduce job needs as first argument the path to the original 1,43 GB data set and as second argument a path where you would like the minimized version to be stored.

## Visualization
The visualizer needs as first argument the minimized chicago data set and as second argument the german data set (without header and footer). It is possible to use the provided .csv files under the data folder in this repository.


# Requirements
* Please make sure that you have java installed and are able to run JavaFX programs.
* Make sure that you give the right order of arguments to the program and see the error output we provide on the console.  

# LICENCE - MIT

Copyright 2018 Lukas Kathrein, Michael Sober

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.