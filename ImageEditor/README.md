# PPM Image Editor

This is a simple image editor to grayscale, emboss, invert, or blur your pictures. Now, to avoid having to deal with compression, this is written to edit PPM Images - not a typical format, but essentially just stores pixels as a series of integers storing RGB values. Very easy to edit. There are a series of images attached, you can use those, or try with your own. There are lots of free JPG/PNG to PPM editors on the web. The Main program takes as arguments your input file, an output file, and what effect you want to use.

```
java ImageEditor.java <your_input_file_path> <your_output_file_path> [grayscale | invert | emboss | motionblur { some int, the higher the number, the more blurred the image} ]

```
