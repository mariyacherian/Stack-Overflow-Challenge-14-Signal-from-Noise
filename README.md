# Stack-Overflow-Challenge-14-Signal-from-Noise
This is a solution to the https://stackoverflow.com/beta/challenges/79838396/challenge-14-signal-from-noise

**Table of contents**

* Overview
* The challenge
* Links
* Author

**Overview**

Decorative image showing signal and noise
For this challenge you will be given a list of integers that is supposed to form a continuous range from A to B, but:

A and B are unknown.

Exactly one integer is missing from within the sequence.

Exactly one integer appears twice.

The array may contain extra random noise values outside the range. (It is possible that the noise values could form a mini-sequence, but the noise sequence will be shorter than the primary sequence)

The challenge is to determine the start value, end value, missing value and duplicate value.

Here are some examples:

Example 1:

[5, 9, 7, 6, 7, 4, 100, -2]

Start value: 4

End value: 9

Missing value: 8

Duplicate value: 7

Noise: -2, 100

Example 2:

[4, 5, 6, -1, 8, 8, -2, -3, -2]

Start value: 4

End value: 8

Missing value: 7

Duplicate value: 8

Noise: -1, -2, -2, -3

Note: The noise values of -1, -2, -3 form a sequence but this smaller than the full sequence

In the attached file, we have 100 lists. Please figure out the sequences for each list.

Please also sum up the sequence start values and end values across the 100 sequences and include that in your solution. For example, the sum across the two provided examples is '25' (4+9+4+8=25). That will help us quickly grade the responses.

**Links**
Solution URL: [https://github.com/mariyacherian/Stack-Overflow-Challenge-14-Signal-from-Noise]



**Author**

Mariya Cherian
