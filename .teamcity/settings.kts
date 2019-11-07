import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
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

    vcsRoot(HttpsGithubComJetBrainsTeamcityTestDataGit)
    vcsRoot(HttpsGithubComGarics2000tempConfigGit)

    buildType(Test)
}

object Test : BuildType({
    name = "Test"

    params {
        param("system.ALL_TESTS_NUM", "10000")
        param("system.TEST_LOG_LINES_NUM", "100")
        param("system.FAILED_TESTS_PERCENTAGE", "30")
    }

    vcs {
        root(HttpsGithubComJetBrainsTeamcityTestDataGit, " generated-tests =>")
    }

    steps {
        script {
            scriptContent = "./generate_fake_run.sh %system.ALL_TESTS_NUM% %system.FAILED_TESTS_PERCENTAGE% %system.TEST_LOG_LINES_NUM%"
        }
    }
})

object HttpsGithubComJetBrainsTeamcityTestDataGit : GitVcsRoot({
    name = "JBTestDataRepo"
    url = "https://github.com/JetBrains/teamcity-test-data.git"
})
