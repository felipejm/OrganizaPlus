#!/bin/bash

# Analyze SVG categories to identify what can be removed

DRAWABLE_DIR="composeApp/src/commonMain/composeResources/drawable"

echo "======================================"
echo "SVG Category Analysis"
echo "======================================"
echo ""

echo "📊 Total SVG files: $(find $DRAWABLE_DIR -name "*.svg" | wc -l | tr -d ' ')"
echo ""

echo "🏷️  Top 50 Icon Categories (by prefix):"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
find $DRAWABLE_DIR -name "*.svg" | sed 's/.*\///' | cut -d'-' -f1 | sort | uniq -c | sort -rn | head -50

echo ""
echo "🎨 Icon Variants Analysis:"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "Outlined: $(find $DRAWABLE_DIR -name "*_outlined.svg" | wc -l | tr -d ' ')"
echo "Filled: $(find $DRAWABLE_DIR -name "*_filled.svg" | wc -l | tr -d ' ')"
echo "Off variants: $(find $DRAWABLE_DIR -name "*-off_*.svg" | wc -l | tr -d ' ')"

echo ""
echo "🎮 Likely Unnecessary Categories:"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "Brand icons: $(find $DRAWABLE_DIR -name "brand-*" | wc -l | tr -d ' ')"
echo "Gaming icons: $(find $DRAWABLE_DIR -name "*game*" -o -name "*xbox*" -o -name "*playstation*" -o -name "*controller*" | wc -l | tr -d ' ')"
echo "Sport icons: $(find $DRAWABLE_DIR -name "*sport*" -o -name "*soccer*" -o -name "*basketball*" -o -name "*football*" | wc -l | tr -d ' ')"
echo "Currency icons: $(find $DRAWABLE_DIR -name "currency-*" | wc -l | tr -d ' ')"
echo "Zodiac icons: $(find $DRAWABLE_DIR -name "zodiac-*" | wc -l | tr -d ' ')"
echo "Vehicle icons: $(find $DRAWABLE_DIR -name "*car*" -o -name "*truck*" -o -name "*plane*" -o -name "*helicopter*" | wc -l | tr -d ' ')"
echo "Weather icons: $(find $DRAWABLE_DIR -name "*weather*" -o -name "*cloud*" -o -name "*rain*" -o -name "*snow*" | wc -l | tr -d ' ')"
echo "Medical icons: $(find $DRAWABLE_DIR -name "*medical*" -o -name "*pill*" -o -name "*vaccine*" | wc -l | tr -d ' ')"

