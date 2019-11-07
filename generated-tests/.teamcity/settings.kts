import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.exec
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.
VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.
To debug settings scripts in command-line, run the
    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate
command and attach your debugger to the port 8000.
To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2019.1"

project {

    vcsRoot(HttpsGheLabsIntellijNetTeamcityUserTestDataGit)

    buildType(RunTests)
}

object RunTests : BuildType({
    name = "Run tests"

    vcs {
        root(HttpsGheLabsIntellijNetTeamcityUserTestDataGit, "generated-tests =>")
    }

    steps {
        exec {
            name = "Fake tests"
            executionMode = BuildStep.ExecutionMode.ALWAYS
            path = "./generate_fake_run.sh"
            arguments = "12000"
        }
    }
})

object HttpsGheLabsIntellijNetTeamcityUserTestDataGit : GitVcsRoot({
    name = "test-data-repo"
    url = "https://ghe.labs.intellij.net/teamcity-user/test-data.git"
    authMethod = password {
        userName = "teamcity-user"
        password = "credentialsJSON:13a671a2-b7f7-4c5d-bbf5-84ccfe3cf7cc"
    }
})