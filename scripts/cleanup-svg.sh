#!/bin/bash

# Clean up unnecessary SVG icons for OrganizePlus app
# This removes icons that are not relevant for a task/duty management app

set -e

DRAWABLE_DIR="composeApp/src/commonMain/composeResources/drawable"

echo "======================================"
echo "SVG Cleanup Tool"
echo "======================================"
echo ""

# Count function
count_files() {
    find $DRAWABLE_DIR -name "$1" 2>/dev/null | wc -l | tr -d ' '
}

echo "📊 Current total: $(find $DRAWABLE_DIR -name "*.svg" | wc -l | tr -d ' ') SVG files"
echo ""

# Define categories to remove (definitely not needed for task management)
declare -A CATEGORIES=(
    ["Brand icons (social media, companies)"]="brand-*"
    ["Gaming icons"]="*xbox* *playstation* *controller* *game-pad* *game-*"
    ["Sport icons"]="*sport* *soccer* *basketball* *football* *baseball* *tennis*"
    ["Currency icons"]="currency-*"
    ["Zodiac signs"]="zodiac-*"
    ["Vehicles (most)"]="*car* *truck* *plane* *helicopter* *boat* *ship* *submarine* *ufo* *rocket*"
    ["Weather icons"]="*weather* *cloud-rain* *cloud-snow* *tornado* *hurricane*"
    ["Medical/Health"]="*medical* *pill* *vaccine* *syringe* *bandage* *dental*"
    ["Wash/Laundry"]="wash-*"
    ["Entertainment"]="*tv* *radio* *vinyl* *cassette* *vhs*"
    ["Food/Drink (detailed)"]="*pizza* *burger* *sausage* *soup* *salad* *beer* *wine* *coffee-off*"
    ["Gaming/Cards"]="*poker* *card* *dice* *casino*"
    ["Zodiac & Astrology"]="*moon-stars* *constellation*"
    ["Construction tools"]="*hammer* *drill* *saw* *construction*"
    ["Specific letter/number variants"]="square-letter-* circle-letter-* hexagon-letter-* pentagon-letter-*"
    ["Specific number variants"]="square-number-* circle-number-* hexagon-number-* pentagon-number-*"
    ["Rewind variants"]="rewind-backward-* rewind-forward-*"
    ["Badge variants (many)"]="badge-1k* badge-2k* badge-3k* badge-4k* badge-5k* badge-6k* badge-7k* badge-8k*"
)

echo "📋 Categories to remove:"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
for category in "${!CATEGORIES[@]}"; do
    pattern="${CATEGORIES[$category]}"
    count=0
    for p in $pattern; do
        c=$(count_files "$p")
        count=$((count + c))
    done
    echo "  • $category: ~$count files"
done

echo ""
total_to_remove=0
for category in "${!CATEGORIES[@]}"; do
    pattern="${CATEGORIES[$category]}"
    for p in $pattern; do
        c=$(count_files "$p")
        total_to_remove=$((total_to_remove + c))
    done
done

echo "📉 Estimated files to remove: ~$total_to_remove"
echo "📈 Estimated files remaining: ~$((5963 - total_to_remove))"
echo ""

read -p "Continue with removal? (y/n) " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "Cancelled."
    exit 0
fi

echo ""
echo "🗑️  Removing unnecessary icons..."
echo ""

removed=0
for category in "${!CATEGORIES[@]}"; do
    pattern="${CATEGORIES[$category]}"
    for p in $pattern; do
        files=$(find $DRAWABLE_DIR -name "$p" 2>/dev/null)
        if [ ! -z "$files" ]; then
            echo "$files" | while read file; do
                rm "$file" && removed=$((removed + 1))
            done
        fi
    done
done

echo ""
echo "✅ Cleanup complete!"
echo ""
echo "📊 Final count: $(find $DRAWABLE_DIR -name "*.svg" | wc -l | tr -d ' ') SVG files"
echo ""
echo "💡 Tip: Review the remaining icons and remove any other unnecessary ones"

