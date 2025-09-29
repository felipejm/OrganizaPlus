#!/bin/bash

# Script to check for hardcoded strings in the project
# This script should be run before commits to ensure no hardcoded strings are present

echo "🔍 Checking for hardcoded strings in the project..."

# Define patterns to search for hardcoded strings in UI components
PATTERNS=(
    'text\s*=\s*"[^"]*"'  # text = "string"
    'label\s*=\s*"[^"]*"'  # label = "string"
    'placeholder\s*=\s*"[^"]*"'  # placeholder = "string"
    'title\s*=\s*"[^"]*"'  # title = "string"
    'contentDescription\s*=\s*"[^"]*"'  # contentDescription = "string"
)

# Directories to check (exclude build directories and generated files)
DIRS=(
    "composeApp/src/commonMain/kotlin"
    "core/src/commonMain/kotlin"
)

# Files to exclude
EXCLUDE_PATTERNS=(
    "*/build/*"
    "*/generated/*"
    "*/test/*"
    "*/Test.kt"
    "*.test.kt"
    "*/catalog/*"  # Catalog files may have hardcoded strings for demonstration
    "*/designsystem/catalog/*"  # Design system catalog files
)

# Build exclude pattern for find command
EXCLUDE_CMD=""
for pattern in "${EXCLUDE_PATTERNS[@]}"; do
    EXCLUDE_CMD="$EXCLUDE_CMD -path '$pattern' -prune -o"
done

# Counter for found issues
ISSUES=0

# Check each directory
for dir in "${DIRS[@]}"; do
    if [ -d "$dir" ]; then
        echo "📁 Checking directory: $dir"
        
        # Check each pattern
        for pattern in "${PATTERNS[@]}"; do
            # Use find with exclude patterns and grep to search for hardcoded strings
            while IFS= read -r -d '' file; do
                # Skip if file matches exclude patterns
                skip=false
                for exclude in "${EXCLUDE_PATTERNS[@]}"; do
                    if [[ "$file" == *"$exclude"* ]]; then
                        skip=true
                        break
                    fi
                done
                
                if [ "$skip" = false ]; then
                    # Search for the pattern in the file
                    if grep -n -E "$pattern" "$file" > /dev/null 2>&1; then
                        echo "❌ Found hardcoded string in: $file"
                        grep -n -E "$pattern" "$file" | while read -r line; do
                            echo "   $line"
                        done
                        ISSUES=$((ISSUES + 1))
                    fi
                fi
            done < <(find "$dir" -name "*.kt" -type f -not -path "*/catalog/*" -not -path "*/designsystem/catalog/*" -print0)
        done
    else
        echo "⚠️  Directory not found: $dir"
    fi
done

# Report results
echo ""
if [ $ISSUES -eq 0 ]; then
    echo "✅ No hardcoded strings found! Great job!"
    exit 0
else
    echo "❌ Found $ISSUES file(s) with hardcoded strings."
    echo ""
    echo "📋 To fix hardcoded strings:"
    echo "1. Add the string to composeApp/src/commonMain/composeResources/values/strings.xml"
    echo "2. Add the translation to composeApp/src/commonMain/composeResources/values-pt/strings.xml"
    echo "3. Import the string resource in your Kotlin file"
    echo "4. Replace the hardcoded string with stringResource(Res.string.your_string_name)"
    echo ""
    echo "📖 See PROJECT_RULES.md for detailed guidelines."
    exit 1
fi
