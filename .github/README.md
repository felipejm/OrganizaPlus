# GitHub Actions Workflows

This directory contains GitHub Actions workflows for the OrganizePlus project.

## Workflows

### 1. CI (`ci.yml`)
**Triggers:** Push to main/develop branches, Pull Requests

**Purpose:** Main continuous integration pipeline that runs tests and builds the project.

**Jobs:**
- **Test and Build** (Ubuntu): Runs unit tests, linting, and builds Android APK and JS distribution
- **iOS Build** (macOS): Builds iOS framework for iOS devices

**Features:**
- Caches Gradle and Node.js dependencies for faster builds
- Runs detekt static analysis
- Executes unit tests for all modules
- Builds Android APK and JS distribution
- Builds iOS framework
- Uploads test results and build artifacts

### 2. Code Quality (`code-quality.yml`)
**Triggers:** Push to main/develop branches, Pull Requests

**Purpose:** Runs code quality checks and generates coverage reports.

**Features:**
- Runs detekt static analysis
- Generates detekt baseline
- Runs ktlint formatting checks
- Generates test coverage reports
- Uploads coverage to Codecov
- Uploads coverage and detekt reports as artifacts

### 3. Security (`security.yml`)
**Triggers:** Push to main/develop branches, Pull Requests, Weekly schedule (Mondays at 2 AM)

**Purpose:** Scans for security vulnerabilities in dependencies.

**Features:**
- Runs OWASP dependency check
- Generates security reports in multiple formats
- Uploads security reports as artifacts
- Comments on PRs with security scan results

### 4. Release (`release.yml`)
**Triggers:** Git tags (v*), Manual workflow dispatch

**Purpose:** Creates releases with build artifacts.

**Features:**
- Runs full test suite
- Builds release APK and JS distribution
- Creates GitHub release with artifacts
- Generates release notes automatically

## Build Commands

The workflows use the following Gradle commands:

```bash
# Run static analysis
./gradlew detekt

# Run unit tests
./gradlew testDebugUnitTest

# Run lint checks
./gradlew lintDebug

# Build Android APK
./gradlew assembleDebug
./gradlew assembleRelease

# Build iOS framework
./gradlew :composeApp:linkDebugFrameworkIosArm64

# Build JS distribution
./gradlew :composeApp:jsBrowserDistribution

# Generate test coverage
./gradlew jacocoTestReport
```

## Caching

The workflows implement intelligent caching for:
- Gradle dependencies (`~/.gradle/caches`, `~/.gradle/wrapper`)
- Node.js modules (`build/js/node_modules`, `kotlin-js-store/node_modules`)

## Artifacts

The workflows generate and upload the following artifacts:
- **test-results**: Test reports and results
- **build-artifacts**: Android APK and JS distribution
- **ios-build-artifacts**: iOS framework
- **coverage-reports**: Test coverage and detekt reports
- **security-reports**: OWASP dependency check reports

## Requirements

- JDK 11 (Temurin distribution)
- Node.js 18
- Gradle (via wrapper)
- Xcode (for iOS builds)

## Environment Variables

No additional environment variables are required. The workflows use GitHub's built-in secrets and the `GITHUB_TOKEN` for release creation.

## Troubleshooting

### Common Issues

1. **Build failures**: Check the logs for specific error messages
2. **Test failures**: Review test results in the uploaded artifacts
3. **Security vulnerabilities**: Check the security reports for dependency issues
4. **iOS build failures**: Ensure Xcode is properly set up on macOS runners

### Debugging

- All workflows run with verbose logging enabled
- Artifacts are uploaded even on failure for debugging
- Coverage reports help identify untested code
- Security reports highlight vulnerable dependencies
