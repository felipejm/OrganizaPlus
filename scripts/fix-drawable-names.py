#!/usr/bin/env python3
"""
Fix Android drawable resource names by replacing hyphens with underscores.
Android resource names must contain only lowercase a-z, 0-9, or underscore.
"""

import os
import sys
from pathlib import Path

def fix_drawable_names(drawable_dir):
    """Rename all files with hyphens to use underscores instead."""
    
    drawable_path = Path(drawable_dir)
    
    if not drawable_path.exists():
        print(f"Error: Directory '{drawable_dir}' does not exist")
        return False
    
    print("=" * 50)
    print("Android Drawable Name Fixer")
    print("=" * 50)
    print()
    print(f"Directory: {drawable_dir}")
    print()
    
    # Find all XML files with hyphens
    files_to_rename = []
    for file in drawable_path.glob("*.xml"):
        if "-" in file.name:
            files_to_rename.append(file)
    
    total_files = len(files_to_rename)
    print(f"Files to rename: {total_files}")
    print()
    
    if total_files == 0:
        print("✅ No files need renaming. All done!")
        return True
    
    print("Starting rename process...")
    print()
    
    renamed = 0
    errors = 0
    
    for file in files_to_rename:
        try:
            # Replace hyphens with underscores
            new_name = file.name.replace("-", "_")
            new_path = file.parent / new_name
            
            # Rename the file
            file.rename(new_path)
            renamed += 1
            
            # Show progress every 500 files
            if renamed % 500 == 0:
                print(f"Progress: {renamed}/{total_files} files renamed...")
        
        except Exception as e:
            errors += 1
            print(f"Error renaming {file.name}: {str(e)}")
    
    print()
    print("=" * 50)
    print("Renaming complete!")
    print("=" * 50)
    print()
    print(f"✅ Successfully renamed: {renamed}")
    if errors > 0:
        print(f"❌ Errors: {errors}")
    print()
    
    # Verify the fix
    remaining = len(list(drawable_path.glob("*-*.xml")))
    print(f"Files with hyphens remaining: {remaining}")
    print()
    
    if remaining == 0:
        print("✅ All files successfully renamed!")
        return True
    else:
        print("⚠️  Some files still contain hyphens. Please review.")
        return False

def main():
    """Main function."""
    import argparse
    
    parser = argparse.ArgumentParser(
        description='Fix Android drawable resource names by replacing hyphens with underscores'
    )
    parser.add_argument('directory', help='Directory containing drawable XML files')
    
    args = parser.parse_args()
    
    success = fix_drawable_names(args.directory)
    sys.exit(0 if success else 1)

if __name__ == '__main__':
    main()

