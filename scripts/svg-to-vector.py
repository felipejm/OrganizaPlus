#!/usr/bin/env python3
"""
SVG to Vector Drawable Converter
This script converts SVG files to Android Vector Drawable XML format.
"""

import os
import sys
import subprocess
import xml.etree.ElementTree as ET
from pathlib import Path
import re

# Color mapping for SVG to Android Vector Drawable
SVG_TO_ANDROID_ATTRS = {
    'fill': 'android:fillColor',
    'stroke': 'android:strokeColor',
    'stroke-width': 'android:strokeWidth',
    'stroke-linecap': 'android:strokeLineCap',
    'stroke-linejoin': 'android:strokeLineJoin',
    'fill-opacity': 'android:fillAlpha',
    'stroke-opacity': 'android:strokeAlpha',
}

def convert_svg_to_vector(svg_path, output_path):
    """
    Convert an SVG file to Android Vector Drawable format.
    Uses Android Studio's svg2vector if available, otherwise does basic conversion.
    """
    try:
        # Try using svg2vector if available (part of Android SDK)
        android_sdk = os.environ.get('ANDROID_SDK_ROOT') or os.environ.get('ANDROID_HOME')
        
        if android_sdk:
            svg2vector_path = os.path.join(android_sdk, 'tools', 'bin', 'svg2vector')
            if os.path.exists(svg2vector_path):
                result = subprocess.run(
                    [svg2vector_path, '-i', svg_path, '-o', output_path],
                    capture_output=True,
                    text=True
                )
                if result.returncode == 0:
                    return True, "Converted using svg2vector"
        
        # Fallback: Basic manual conversion
        return basic_svg_conversion(svg_path, output_path)
        
    except Exception as e:
        return False, f"Error: {str(e)}"

def basic_svg_conversion(svg_path, output_path):
    """
    Basic SVG to Vector Drawable conversion (simplified).
    This is a basic converter - complex SVGs may need manual adjustment.
    """
    try:
        # Parse SVG
        tree = ET.parse(svg_path)
        root = tree.getroot()
        
        # Extract viewBox or width/height
        viewbox = root.get('viewBox', '0 0 24 24')
        viewbox_parts = viewbox.split()
        
        if len(viewbox_parts) == 4:
            width = viewbox_parts[2]
            height = viewbox_parts[3]
        else:
            width = root.get('width', '24').replace('px', '')
            height = root.get('height', '24').replace('px', '')
        
        # Create vector drawable XML
        vector = ET.Element('vector')
        vector.set('xmlns:android', 'http://schemas.android.com/apk/res/android')
        vector.set('android:width', f'{width}dp')
        vector.set('android:height', f'{height}dp')
        vector.set('android:viewportWidth', str(width))
        vector.set('android:viewportHeight', str(height))
        
        # Process paths
        svg_ns = {'svg': 'http://www.w3.org/2000/svg'}
        
        # Handle paths
        for path_elem in root.findall('.//svg:path', svg_ns) or root.findall('.//path'):
            if path_elem.get('d'):
                path_node = ET.SubElement(vector, 'path')
                path_node.set('android:pathData', path_elem.get('d'))
                
                # Convert attributes
                for svg_attr, android_attr in SVG_TO_ANDROID_ATTRS.items():
                    if path_elem.get(svg_attr):
                        value = path_elem.get(svg_attr)
                        # Handle 'none' values
                        if value.lower() != 'none':
                            path_node.set(android_attr, value)
                
                # Set default fill color if not specified
                if not path_elem.get('fill') and not path_elem.get('stroke'):
                    path_node.set('android:fillColor', '#FF000000')
        
        # Handle groups
        for group_elem in root.findall('.//svg:g', svg_ns) or root.findall('.//g'):
            process_group(vector, group_elem, svg_ns)
        
        # Write output
        tree_out = ET.ElementTree(vector)
        ET.indent(tree_out, space='    ')
        tree_out.write(output_path, encoding='utf-8', xml_declaration=True)
        
        return True, "Basic conversion completed"
        
    except Exception as e:
        return False, f"Basic conversion error: {str(e)}"

def process_group(parent, group, svg_ns):
    """Process SVG group elements recursively."""
    for path_elem in group.findall('.//svg:path', svg_ns) or group.findall('.//path'):
        if path_elem.get('d'):
            path_node = ET.SubElement(parent, 'path')
            path_node.set('android:pathData', path_elem.get('d'))
            
            # Convert attributes
            for svg_attr, android_attr in SVG_TO_ANDROID_ATTRS.items():
                value = group.get(svg_attr) or path_elem.get(svg_attr)
                if value and value.lower() != 'none':
                    path_node.set(android_attr, value)
            
            # Set default fill color if not specified
            if not path_elem.get('fill') and not path_elem.get('stroke'):
                path_node.set('android:fillColor', '#FF000000')

def convert_directory(input_dir, output_dir, file_pattern='*.svg'):
    """Convert all SVG files in a directory."""
    input_path = Path(input_dir)
    output_path = Path(output_dir)
    
    if not input_path.exists():
        print(f"Error: Input directory '{input_dir}' does not exist")
        return
    
    # Create output directory if it doesn't exist
    output_path.mkdir(parents=True, exist_ok=True)
    
    # Find all SVG files
    svg_files = list(input_path.glob(file_pattern))
    
    if not svg_files:
        print(f"No SVG files found in '{input_dir}'")
        return
    
    print(f"Found {len(svg_files)} SVG files to convert...")
    
    success_count = 0
    error_count = 0
    
    for svg_file in svg_files:
        # Generate output filename (replace .svg with .xml)
        output_file = output_path / (svg_file.stem + '.xml')
        
        success, message = convert_svg_to_vector(str(svg_file), str(output_file))
        
        if success:
            success_count += 1
            print(f"✓ {svg_file.name} -> {output_file.name}")
        else:
            error_count += 1
            print(f"✗ {svg_file.name}: {message}")
    
    print(f"\nConversion complete!")
    print(f"Success: {success_count}, Errors: {error_count}")

def main():
    """Main function to handle command line arguments."""
    import argparse
    
    parser = argparse.ArgumentParser(
        description='Convert SVG files to Android Vector Drawable format',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  # Convert single file
  python svg-to-vector.py -i icon.svg -o icon.xml
  
  # Convert all SVG files in a directory
  python svg-to-vector.py -d composeApp/src/commonMain/composeResources/drawable -o drawable-vector
  
  # Convert with custom pattern
  python svg-to-vector.py -d drawable -o drawable-vector -p "*_outlined.svg"
        """
    )
    
    parser.add_argument('-i', '--input', help='Input SVG file')
    parser.add_argument('-o', '--output', help='Output Vector Drawable XML file or directory')
    parser.add_argument('-d', '--directory', help='Input directory containing SVG files')
    parser.add_argument('-p', '--pattern', default='*.svg', help='File pattern to match (default: *.svg)')
    
    args = parser.parse_args()
    
    # Check for ANDROID_SDK_ROOT
    android_sdk = os.environ.get('ANDROID_SDK_ROOT') or os.environ.get('ANDROID_HOME')
    if not android_sdk:
        print("Warning: ANDROID_SDK_ROOT or ANDROID_HOME not set.")
        print("Using basic conversion. For best results, set your Android SDK path.\n")
    
    if args.directory:
        # Convert directory
        if not args.output:
            print("Error: -o/--output is required when using -d/--directory")
            sys.exit(1)
        convert_directory(args.directory, args.output, args.pattern)
    
    elif args.input and args.output:
        # Convert single file
        success, message = convert_svg_to_vector(args.input, args.output)
        if success:
            print(f"✓ Converted successfully: {message}")
        else:
            print(f"✗ Conversion failed: {message}")
            sys.exit(1)
    
    else:
        parser.print_help()
        sys.exit(1)

if __name__ == '__main__':
    main()

