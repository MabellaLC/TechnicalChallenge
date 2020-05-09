# Technical Challenge

## Problem
Our product owner wants to build a new system to charge for the clicks that we get from the website. We want
to be able to use it in different systems (a web site, an API, a desktop application) but for now we don't care
about where is running.

## Project
The different sprints are divided by folders.

I used some design patterns as a command and builder.
- Command: i wanted to divided the different prizes depends of active campaign. If we would like to introduce
another prize per click, we just have to add a new command

- Builder: entities with too many parameters need a builder patter. This will improve the cohesion of the tests

INFRASTRUCTURE
Two repositories in local to save a list of clicks and a list of predeterminate bots user Id


## Rules
Just enjoy with the kata and thanks in advance for this 3 months of apprenticeship.
