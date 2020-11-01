@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  7. Backend Ktor - CRUD startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and 7_BACKEND_KTOR_CRUD_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\7. Backend Ktor - CRUD-1.0-SNAPSHOT.jar;%APP_HOME%\lib\kodein-di-framework-ktor-server-jvm-6.3.3.jar;%APP_HOME%\lib\ktor-server-cio-1.2.4.jar;%APP_HOME%\lib\ktor-server-host-common-1.2.4.jar;%APP_HOME%\lib\ktor-gson-1.2.4.jar;%APP_HOME%\lib\ktor-http-cio-jvm-1.2.4.jar;%APP_HOME%\lib\ktor-network-1.2.4.jar;%APP_HOME%\lib\ktor-server-core-1.2.4.jar;%APP_HOME%\lib\ktor-http-jvm-1.2.4.jar;%APP_HOME%\lib\ktor-utils-jvm-1.2.4.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.3.72.jar;%APP_HOME%\lib\logback-classic-1.2.3.jar;%APP_HOME%\lib\kodein-di-generic-jvm-6.3.3.jar;%APP_HOME%\lib\kodein-di-core-jvm-6.3.3.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.3.72.jar;%APP_HOME%\lib\ktor-1.1.1.jar;%APP_HOME%\lib\kotlinx-coroutines-io-jvm-0.1.14.jar;%APP_HOME%\lib\kotlinx-io-jvm-0.1.14.jar;%APP_HOME%\lib\atomicfu-0.12.11.jar;%APP_HOME%\lib\kotlinx-coroutines-jdk8-1.3.0.jar;%APP_HOME%\lib\kotlinx-coroutines-core-1.3.0.jar;%APP_HOME%\lib\kotlin-reflect-1.3.50.jar;%APP_HOME%\lib\kotlin-stdlib-1.3.72.jar;%APP_HOME%\lib\slf4j-api-1.7.26.jar;%APP_HOME%\lib\config-1.3.1.jar;%APP_HOME%\lib\gson-2.8.5.jar;%APP_HOME%\lib\logback-core-1.2.3.jar;%APP_HOME%\lib\kotlinx-coroutines-io-0.1.14.jar;%APP_HOME%\lib\kotlinx-io-0.1.14.jar;%APP_HOME%\lib\kotlinx-coroutines-core-common-1.3.0.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.3.72.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\atomicfu-common-0.12.11.jar

@rem Execute 7. Backend Ktor - CRUD
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %7_BACKEND_KTOR_CRUD_OPTS%  -classpath "%CLASSPATH%" ApplicationKtPosts %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable 7_BACKEND_KTOR_CRUD_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%7_BACKEND_KTOR_CRUD_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
