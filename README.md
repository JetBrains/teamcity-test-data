# TeamCity test data repository
A data repository with a sample configurations which may be required for various kind of testing and development.
## Test configuration description and additional info
See the README.md in the appropriate config directory.
## Adding a test configuration to the existing TC instance
1. Create new build configuration in a project
2. Add the following VCS root for this configuration (supports anonymous read-only access):
```
https://github.com/JetBrains/teamcity-test-data.git
```
3. Edit checkout rules from for the newly created build configuration by the following pattern: 
```
%folder_name_in_this_repo% =>
```
I.e. for the **generated-test** build configuration the checkout rule would be **generated-tests =>**

4.  **Build steps** should be auto detected from the settings DSL file. Typically a maven step from teamcity's pom.xml can be ignored.




