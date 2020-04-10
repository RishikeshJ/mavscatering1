@ECHO OFF
set abort=0
IF "%JAVA_HOME%"=="" (
    ECHO.
    ECHO ***Environment variable JAVA_HOME Not Defined - please correct - terminating
    set abort=1
)
IF "%TESTJAR_HOME%"=="" (
    ECHO.
    ECHO ***Environment variable TESTJAR_HOME Not Defined - please correct - terminating
    set abort=1
)
IF "%PITJAR_HOME%"=="" (
    ECHO.
    ECHO ***Environment variable PITJAR_HOME Not Defined - please correct - terminating
    set abort=1
)
IF "%abort%"=="1" GOTO:eof
ECHO ON
java -cp "%PITJAR_HOME%\pitest-command-line-1.4.6.jar";"%PITJAR_HOME%\pitest-1.4.6.jar";"%PITJAR_HOME%\pitest-entry-1.4.6.jar";"%TESTJAR_HOME%\junit.jar";"%TESTJAR_HOME%\org.hamcrest.core_1.3.0.v20180420-1519.jar";"%TESTJAR_HOME%\easymock-3.4.jar";"%TESTJAR_HOME%\JUnitParams-1.0.4.jar";bin   org.pitest.mutationtest.commandline.MutationCoverageReport  --reportDir=pitResults  --targetClasses=controller.eventController,controller.LoginController,controller.userController,data.EventDAO,data.UserDAO,model.Event,model.EventErrorMsgs,model.User,model.UserErrorMsgs,util.SQLConnection  --sourceDirs="C:\Users\DELL\Music\event_catering_management\src" --verbose=true --failWhenNoMutations=false --mutators=CONSTRUCTOR_CALLS,NON_VOID_METHOD_CALLS,CONDITIONALS_BOUNDARY,INCREMENTS,INVERT_NEGS,MATH,RETURN_VALS,VOID_METHOD_CALLS,EMPTY_RETURNS,FALSE_RETURNS,INLINE_CONSTS,NULL_RETURNS,PRIMITIVE_RETURNS,REMOVE_CONDITIONALS,REMOVE_INCREMENTS,TRUE_RETURNS,EXPERIMENTAL_ARGUMENT_PROPAGATION,EXPERIMENTAL_BIG_INTEGER,EXPERIMENTAL_NAKED_RECEIVER,EXPERIMENTAL_MEMBER_VARIABLE,EXPERIMENTAL_SWITCH,AOD,OBBN --excludedClasses=*Test*