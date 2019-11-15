#!/bin/bash
#################################################################
# Test report generator
#################################################################
#
#  EXAMPLE:
#    ${SCRIPT_NAME} arg1 arg2 arg2
#
#  ARGUMENTS:
#    arg1 - total number of tests to generate (i.e. 50000)
#    arg2 - percentage of failed tests in the run without % sign (i.e. 30 - means 30% from the total number of tests)
#    arg3 - number of words to be generated as a "fish" for each test output (i.e. 500)
#
##################################################################

IFS=$'\n' read -d '' -r -a classes < templates/classes
IFS=$'\n' read -d '' -r -a tests < templates/tests
IFS=$'\n' read -d '' -r -a exceptions < templates/exceptions

TARGET_NUM=${1:-10000}
FAILED_RATE=${2:-30}
LOG_LINES_NUM=${3:-400}
FAILED_NUM=$(( FAILED_RATE*TARGET_NUM / 100 ))

LOG_CONTENT=`./log_generator.sh $LOG_LINES_NUM`

SUITE_COUNTER=1
TEST_COUNTER=1

################################################################
# Genererates test result with a dummy log output
# Arguments:
#   arg1 - full name name of the tests
#   arg2 - test status tio be genreated - passsed or failed
# Returns:
#   None
################################################################
function test_result() {
  local name=$1
  local status=$2

  echo "##teamcity[testStarted name='$name']"

  if [[ "$status" == "failed" ]]; then
    local rand=$(( ( RANDOM % ${#exceptions[@]} )  + 0 ))
    echo "##teamcity[testFailed name='$name' message='${exceptions[rand]}' details='${exceptions[rand]}']"
  fi
  
  while IFS= read -r line; do
    echo "##teamcity[testStdOut name='className.$name' out='$line']"
  done < <(printf '%s\n' "$LOG_CONTENT")

  echo "##teamcity[testFinished name='$name']"
}

# Generating service messages with the tests
while [ "$TEST_COUNTER" -le "$TARGET_NUM" ];
do

  for i in "${classes[@]}"
  do
    echo "##teamcity[testSuiteStarted name='$i.$SUITE_COUNTER']"

    for j in "${tests[@]}"
    do
      name="$i.$j.$TEST_COUNTER"
      [[ $TEST_COUNTER -lt FAILED_NUM+1 ]] && status="failed" || status="passed"

      test_result "$name" "$status"
      ((TEST_COUNTER++))
      if [[ "$TEST_COUNTER" -gt "$TARGET_NUM" ]]; then
          break
      fi

    done

    echo "##teamcity[testSuiteFinished name='$i.$SUITE_COUNTER']"
    ((SUITE_COUNTER++))

  done

done

