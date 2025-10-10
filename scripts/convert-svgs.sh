#!/bin/bash

# SVG to Vector Drawable Converter Script
# This script converts all SVG files to Android Vector Drawable format

set -e

# Configuration
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
SVG_DIR="$PROJECT_ROOT/composeApp/src/commonMain/composeResources/drawable"
OUTPUT_DIR="$PROJECT_ROOT/composeApp/src/androidMain/res/drawable"

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "======================================"
echo "SVG to Vector Drawable Converter"
echo "======================================"
echo ""

# Check if Python is available
if ! command -v python3 &> /dev/null; then
    echo -e "${RED}Error: Python 3 is not installed${NC}"
    exit 1
fi

# Display configuration
echo "Configuration:"
echo "  SVG Directory:    $SVG_DIR"
echo "  Output Directory: $OUTPUT_DIR"
echo ""

# Check if SVG directory exists
if [ ! -d "$SVG_DIR" ]; then
    echo -e "${RED}Error: SVG directory not found: $SVG_DIR${NC}"
    exit 1
fi

# Count SVG files
SVG_COUNT=$(find "$SVG_DIR" -name "*.svg" -type f | wc -l | tr -d ' ')
echo -e "${YELLOW}Found $SVG_COUNT SVG files to convert${NC}"
echo ""

# Ask for confirmation
read -p "Continue with conversion? (y/n) " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "Conversion cancelled."
    exit 0
fi

echo ""
echo "Starting conversion..."
echo ""

# Run the Python converter
python3 "$SCRIPT_DIR/svg-to-vector.py" -d "$SVG_DIR" -o "$OUTPUT_DIR"

echo ""
echo -e "${GREEN}Conversion complete!${NC}"
echo ""
echo "Vector drawables have been generated in:"
echo "  $OUTPUT_DIR"
echo ""
echo "Note: Some complex SVGs may need manual adjustments."
echo "Please review the generated files."

