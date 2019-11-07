# Generated Tests
This build configuration provides a generated test run via TeamCity Service Messages interface.
The number of tests to be generated can be adjusted in build configuration step script execution parameters:
 - arg1 - total number of tests to be generated. The default value is 12000.
 - arg2 - percentage of failed tests in the run without % sign (i.e. 30 - means 30% from the total number of tests).
 - arg3 - number of words to be generated as a "fish" for each test output (i.e. 500)
 
## How to use it
1. Import the build configuration to a TC instance (see the general readme in root repo folder for how to on import procedure and setting up checkout rules)
2. Run imported configuration on linux based agent- test results will be available in an appropriate tab of build configuration page.

## Generated report example
http://tcqa-trunk.labs.intellij.net:8111/build/84226?buildTab=tests



