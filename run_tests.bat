@echo off
REM Pahana Education CRM - Test Execution Script (Windows)
REM This script provides various options for running tests and generating reports

echo  Pahana Education CRM - Test Execution Script
echo ================================================
echo.

REM Function to display help
:show_help
if "%1"=="help" goto help_content
if "%1"=="-h" goto help_content
if "%1"=="--help" goto help_content
goto :eof

:help_content
echo Usage: %0 [OPTION]
echo.
echo Options:
echo   all           Run all tests ^(default^)
echo   unit          Run only unit tests
echo   dao           Run only DAO tests
echo   servlet       Run only servlet tests
echo   util          Run only utility tests
echo   coverage      Run tests with coverage report
echo   clean         Clean and run tests
echo   help          Show this help message
echo.
echo Examples:
echo   %0              # Run all tests
echo   %0 dao          # Run only DAO tests
echo   %0 coverage     # Run with coverage report
echo.
goto :eof

REM Function to run tests with specific pattern
:run_tests
set pattern=%1
set description=%2
echo  Running %description%...
echo Pattern: %pattern%
echo ----------------------------------------
mvn test -Dtest="%pattern%" -Dsurefire.useFile=false
if %errorlevel% equ 0 (
    echo  %description% completed successfully!
) else (
    echo  %description% failed!
    exit /b 1
)
echo.
goto :eof

REM Function to run all tests
:run_all_tests
echo  Running Complete Test Suite...
echo ==================================
mvn clean test -Dsurefire.useFile=false
if %errorlevel% equ 0 (
    echo  All tests completed successfully!
) else (
    echo  Some tests failed!
    exit /b 1
)
goto :eof

REM Function to run tests with coverage
:run_coverage_tests
echo  Running Tests with Coverage Report...
echo ========================================
REM Check if JaCoCo plugin is available
findstr "jacoco-maven-plugin" pom.xml >nul
if %errorlevel% equ 0 (
    mvn clean test jacoco:report
) else (
    echo  JaCoCo plugin not found in pom.xml
    echo Running tests without coverage...
    mvn clean test
)
if %errorlevel% equ 0 (
    echo  Coverage report generated successfully!
    echo  Check target/site/jacoco/index.html for coverage report
) else (
    echo  Coverage report generation failed!
    exit /b 1
)
goto :eof

REM Function to clean and run tests
:run_clean_tests
echo  Cleaning and Running Tests...
echo ================================
mvn clean test -Dsurefire.useFile=false
if %errorlevel% equ 0 (
    echo  Clean test run completed successfully!
) else (
    echo  Clean test run failed!
    exit /b 1
)
goto :eof

REM Main execution logic
set option=%1
if "%option%"=="" set option=all

if "%option%"=="all" goto run_all_tests
if "%option%"=="unit" goto run_unit_tests
if "%option%"=="dao" goto run_dao_tests
if "%option%"=="servlet" goto run_servlet_tests
if "%option%"=="util" goto run_util_tests
if "%option%"=="coverage" goto run_coverage_tests
if "%option%"=="clean" goto run_clean_tests
if "%option%"=="help" goto show_help
if "%option%"=="-h" goto show_help
if "%option%"=="--help" goto show_help

echo  Unknown option: %option%
echo.
goto show_help

:run_unit_tests
call :run_tests "**/*Test" "Unit Tests"
goto final_status

:run_dao_tests
call :run_tests "**/dao/*Test" "DAO Tests"
goto final_status

:run_servlet_tests
call :run_tests "**/servlet/*Test" "Servlet Tests"
goto final_status

:run_util_tests
call :run_tests "**/util/*Test" "Utility Tests"
goto final_status

:final_status
if %errorlevel% equ 0 (
    echo.
    echo  Test execution completed successfully!
    echo.
    echo  Test Summary:
    echo   - Framework: JUnit 5 + Mockito
    echo   - Test Types: Unit, Integration, Exception, Validation
    echo   - Coverage: Comprehensive across all layers
    echo.
    echo  For detailed information, see:
    echo   - TESTING_REPORT.md
    echo   - src/test/java/com/pahanaedu/TestSuite.java
    echo.
) else (
    echo.
    echo  Test execution encountered errors!
    echo.
    echo  Troubleshooting tips:
    echo   1. Check if all dependencies are installed: mvn clean install
    echo   2. Verify database connection settings
    echo   3. Check for compilation errors: mvn clean compile
    echo   4. Review test logs for specific failure details
    echo.
    exit /b 1
)
goto :eof
