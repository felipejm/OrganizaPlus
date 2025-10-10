#!/usr/bin/env python3
"""
Remove unnecessary SVG icons for OrganizePlus task management app
"""

import os
from pathlib import Path

DRAWABLE_DIR = "composeApp/src/commonMain/composeResources/drawable"

# Define categories to remove (not needed for task management)
PATTERNS_TO_REMOVE = {
    "Brand icons": ["brand-"],
    "Gaming": ["xbox-", "playstation-", "controller-", "game-pad"],
    "Sports": ["sport-", "soccer-", "basketball-", "football-", "baseball-", "tennis-", "golf-"],
    "Currency": ["currency-"],
    "Zodiac": ["zodiac-"],
    "Vehicles": [
        "car-", "truck-", "plane-", "helicopter-", "boat-", "ship-", "submarine-",
        "ufo-", "rocket-", "tractor-", "bus-", "train-", "scooter-", "skateboard-",
        "bike-", "motorcycle-", "sailboat-", "speedboat-"
    ],
    "Weather": ["cloud-rain", "cloud-snow", "tornado-", "hurricane-", "snowflake-"],
    "Medical": ["medical-", "pill-", "vaccine-", "syringe-", "bandage-", "dental-"],
    "Wash/Laundry": ["wash-"],
    "Entertainment": ["tv-", "radio-", "vinyl-", "cassette-", "vhs-"],
    "Food": [
        "pizza-", "burger-", "sausage-", "soup-", "salad-", "beer-", "wine-",
        "coffee-off", "meat-", "bread-", "cheese-", "egg-", "fish-", "lemon-",
        "pepper-", "apple-", "carrot-", "cherry-", "cookie-", "ice-cream"
    ],
    "Gaming/Cards": ["poker-", "dice-", "casino-", "playing-card"],
    "Construction": ["hammer-", "drill-", "saw-", "construction-"],
    "Letter variants": [
        "square-letter-", "circle-letter-", "hexagon-letter-", "pentagon-letter-",
        "square-rounded-letter-", "octagon-letter-"
    ],
    "Number variants": [
        "square-number-", "circle-number-", "hexagon-number-", "pentagon-number-",
        "square-rounded-number-", "octagon-number-", "rosette-number-"
    ],
    "Rewind specific": [
        "rewind-backward-10", "rewind-backward-15", "rewind-backward-20",
        "rewind-backward-30", "rewind-backward-40", "rewind-backward-50",
        "rewind-backward-60",
        "rewind-forward-10", "rewind-forward-15", "rewind-forward-20",
        "rewind-forward-30", "rewind-forward-40", "rewind-forward-50",
        "rewind-forward-60"
    ],
    "Badge variants": [
        "badge-1k", "badge-2k", "badge-3k", "badge-4k",
        "badge-5k", "badge-6k", "badge-7k", "badge-8k"
    ],
    "Multiplier": ["multiplier-"],
    "Signal specific": ["signal-2g", "signal-3g", "signal-4g", "signal-5g", "signal-6g"],
}

def main():
    drawable_path = Path(DRAWABLE_DIR)
    
    if not drawable_path.exists():
        print(f"Error: Directory not found: {DRAWABLE_DIR}")
        return
    
    print("=" * 60)
    print("SVG Cleanup Tool for OrganizePlus")
    print("=" * 60)
    print()
    
    # Count current files
    all_svgs = list(drawable_path.glob("*.svg"))
    print(f"📊 Current total: {len(all_svgs)} SVG files")
    print()
    
    # Analyze what will be removed
    to_remove = []
    category_counts = {}
    
    for svg_file in all_svgs:
        filename = svg_file.name
        for category, patterns in PATTERNS_TO_REMOVE.items():
            for pattern in patterns:
                if pattern in filename:
                    to_remove.append(svg_file)
                    category_counts[category] = category_counts.get(category, 0) + 1
                    break
    
    # Remove duplicates
    to_remove = list(set(to_remove))
    
    print("📋 Categories to remove:")
    print("━" * 60)
    for category, count in sorted(category_counts.items(), key=lambda x: x[1], reverse=True):
        print(f"  • {category}: {count} files")
    
    print()
    print(f"📉 Total files to remove: {len(to_remove)}")
    print(f"📈 Files remaining: {len(all_svgs) - len(to_remove)}")
    print()
    
    response = input("Continue with removal? (y/n): ")
    if response.lower() != 'y':
        print("Cancelled.")
        return
    
    print()
    print("🗑️  Removing files...")
    print()
    
    removed_count = 0
    for file_path in to_remove:
        try:
            file_path.unlink()
            removed_count += 1
            if removed_count % 100 == 0:
                print(f"  Removed {removed_count} files...")
        except Exception as e:
            print(f"Error removing {file_path.name}: {e}")
    
    remaining_svgs = list(drawable_path.glob("*.svg"))
    
    print()
    print("✅ Cleanup complete!")
    print()
    print(f"📊 Files removed: {removed_count}")
    print(f"📊 Files remaining: {len(remaining_svgs)}")
    print()
    print("💡 Tip: Review remaining icons and remove any others you don't need")

if __name__ == "__main__":
    main()

