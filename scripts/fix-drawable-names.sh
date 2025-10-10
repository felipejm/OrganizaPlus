#!/bin/bash

# Fix Android drawable resource names by replacing hyphens with underscores
# Android resource names must contain only lowercase a-z, 0-9, or underscore

set -e

DRAWABLE_DIR="$1"

if [ -z "$DRAWABLE_DIR" ]; then
    echo "Usage: $0 <drawable-directory>"
    exit 1
fi

if [ ! -d "$DRAWABLE_DIR" ]; then
    echo "Error: Directory '$DRAWABLE_DIR' does not exist"
    exit 1
fi

echo "======================================"
echo "Android Drawable Name Fixer"
echo "======================================"
echo ""
echo "Directory: $DRAWABLE_DIR"
echo ""

# Count files with hyphens
file_count=$(find "$DRAWABLE_DIR" -name "*-*.xml" -type f | wc -l | tr -d ' ')
echo "Files to rename: $file_count"
echo ""

if [ "$file_count" -eq 0 ]; then
    echo "No files need renaming. All done!"
    exit 0
fi

# Ask for confirmation
read -p "Continue with renaming? (y/n) " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "Operation cancelled."
    exit 0
fi

echo ""
echo "Renaming files..."
echo ""

renamed=0
errors=0

# Find all files with hyphens and rename them
find "$DRAWABLE_DIR" -name "*-*.xml" -type f | while read -r file; do
    # Get directory and filename
    dir=$(dirname "$file")
    filename=$(basename "$file")
    
    # Replace hyphens with underscores
    new_filename="${filename//-/_}"
    new_file="$dir/$new_filename"
    
    # Rename the file
    if mv "$file" "$new_file" 2>/dev/null; then
        renamed=$((renamed + 1))
        if [ $((renamed % 100)) -eq 0 ]; then
            echo "Renamed $renamed files..."
        fi
    else
        errors=$((errors + 1))
        echo "Error renaming: $filename"
    fi
done

echo ""
echo "======================================"
echo "Renaming complete!"
echo "======================================"
echo ""

# Verify the fix
remaining=$(find "$DRAWABLE_DIR" -name "*-*.xml" -type f | wc -l | tr -d ' ')
echo "Files with hyphens remaining: $remaining"
echo ""

if [ "$remaining" -eq 0 ]; then
    echo "✅ All files successfully renamed!"
else
    echo "⚠️  Some files still contain hyphens. Please review."
fi

