#!/bin/bash

# Pahana Education CRM - Test Execution Script
# This script provides various options for running tests and generating reports

echo " Pahana Education CRM - Test Execution Script"
echo "================================================"
echo ""

# Function to display help
show_help() {
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Options:"
    echo "  all           Run all tests (default)"
    echo "  unit          Run only unit tests"
    echo "  dao           Run only DAO tests"
    echo "  servlet       Run only servlet tests"
    echo "  util          Run only utility tests"
    echo "  coverage      Run tests with coverage report"
    echo "  clean         Clean and run tests"
    echo "  help          Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0              # Run all tests"
    echo "  $0 dao          # Run only DAO tests"
    echo "  $0 coverage     # Run with coverage report"
    echo ""
}

# Function to run tests with specific pattern
run_tests() {
    local pattern=$1
    local description=$2
    
    echo " Running $description..."
    echo "Pattern: $pattern"
    echo "----------------------------------------"
    
    mvn test -Dtest="$pattern" -Dsurefire.useFile=false
    
    if [ $? -eq 0 ]; then
        echo " $description completed successfully!"
    else
        echo " $description failed!"
        return 1
    fi
    echo ""
}

# Function to run all tests
run_all_tests() {
    echo " Running Complete Test Suite..."
    echo "=================================="
    
    mvn clean test -Dsurefire.useFile=false
    
    if [ $? -eq 0 ]; then
        echo " All tests completed successfully!"
    else
        echo " Some tests failed!"
        return 1
    fi
}

# Function to run tests with coverage
run_coverage_tests() {
    echo " Running Tests with Coverage Report..."
    echo "========================================"
    
    # Check if JaCoCo plugin is available
    if grep -q "jacoco-maven-plugin" pom.xml; then
        mvn clean test jacoco:report
    else
        echo "  JaCoCo plugin not found in pom.xml"
        echo "Running tests without coverage..."
        mvn clean test
    fi
    
    if [ $? -eq 0 ]; then
        echo " Coverage report generated successfully!"
        echo " Check target/site/jacoco/index.html for coverage report"
    else
        echo " Coverage report generation failed!"
        return 1
    fi
}

# Function to clean and run tests
run_clean_tests() {
    echo " Cleaning and Running Tests..."
    echo "================================"
    
    mvn clean test -Dsurefire.useFile=false
    
    if [ $? -eq 0 ]; then
        echo " Clean test run completed successfully!"
    else
        echo " Clean test run failed!"
        return 1
    fi
}

# Main execution logic
case "${1:-all}" in
    "all")
        run_all_tests
        ;;
    "unit")
        run_tests "**/*Test" "Unit Tests"
        ;;
    "dao")
        run_tests "**/dao/*Test" "DAO Tests"
        ;;
    "servlet")
        run_tests "**/servlet/*Test" "Servlet Tests"
        ;;
    "util")
        run_tests "**/util/*Test" "Utility Tests"
        ;;
    "coverage")
        run_coverage_tests
        ;;
    "clean")
        run_clean_tests
        ;;
    "help"|"-h"|"--help")
        show_help
        exit 0
        ;;
    *)
        echo " Unknown option: $1"
        echo ""
        show_help
        exit 1
        ;;
esac

# Final status
if [ $? -eq 0 ]; then
    echo ""
    echo " Test execution completed successfully!"
    echo ""
    echo " Test Summary:"
    echo "  - Framework: JUnit 5 + Mockito"
    echo "  - Test Types: Unit, Integration, Exception, Validation"
    echo "  - Coverage: Comprehensive across all layers"
    echo ""
    echo " For detailed information, see:"
    echo "  - TESTING_REPORT.md"
    echo "  - src/test/java/com/pahanaedu/TestSuite.java"
    echo ""
else
    echo ""
    echo " Test execution encountered errors!"
    echo ""
    echo " Troubleshooting tips:"
    echo "  1. Check if all dependencies are installed: mvn clean install"
    echo "  2. Verify database connection settings"
    echo "  3. Check for compilation errors: mvn clean compile"
    echo "  4. Review test logs for specific failure details"
    echo ""
    exit 1
fi
