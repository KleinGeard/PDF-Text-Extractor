# PDF-Text-Extractor

This is a program made to convert directories of pdf files into txt or extract their properties into a csv file. 

It uses Apache PDFBox 2.0.0 which you can download here: https://pdfbox.apache.org/download.cgi 

(pdfbox-2.0.0-RC3.jar)

## HOW TO USE THIS PROGRAM

#### To get it running

• Import the project into eclipse.

• Add a user library using "pdfbox-2.0.0-RC3.jar" (see download above).

#### To extract text

• Run the program.

• Select a target folder (The folder containing the pdf documents you wish to have converted).

• Select a destination folder (The folder you wish the converted txt documents to be saved to).

• Click "Extract Text".

• Once it is done the txt files should be in the your chosen destination folder.

#### To get information about your pdf files

• Run the program.

• Select a target folder (The folder containing the pdf documents you wish to extract information from).

• Select a destination folder (The folder you wish the csv file to be written to).

• If the file_info.csv file already exists in your destination folder it will continue writing to the one that is already there.

• Click "Extract Info".

• Once it is done the information extracted from your pdf files should be in pdf_info.csv in your chosen destination folder.

###### The pdf_info.csv will contain the follow data: 

title, file name, subject, keywords, creator, producer, file size(bytes), number of pages, number of lines, number of words, encrypted(true/false)

## Limitations
 
#### Extracting text
 
• Some pdf files are made up of scanned images rather than actual text. Unfortunately these can not be converted.

• Tables, graphs, maps, lists, indexes, etc, may come out funny. This just happens to be the way the text is stored in the file.

• Sometimes what looks like a group of linebreaks or whitespaces is actually just a gap so linebreaks or spaces can't be added.

#### Extracting info

• A lot of pdf files don't have certain properties such as a title or a subject so these come out empty.

![picture alt](http://i.imgur.com/JnqNwAh.png "screenshot")


