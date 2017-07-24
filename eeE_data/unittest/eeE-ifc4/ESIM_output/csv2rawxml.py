import os.path
import sys
import csv

def outputRowXml(row,tag):
	line = '    <row ' + tag + '>'
	colno = 0
	for cell in row:
		colno = colno + 1
		ctag = 'n="' + str(colno) + '"'
		line = line + '<cell ' + ctag + '>' + cell + '</cell>'
	line = line + '</row>'
	print line
	return

def outputFileXml(filename):
	print '  <file name="' + filename + '">'
	with open(filename) as csvfile:    
		spamreader = csv.reader(csvfile, delimiter=';', quotechar='"')
		rowno = 0
		tag = ''
		for row in spamreader:
			rowno = rowno + 1
			tag = 'n="' + str(rowno) + '"'
			outputRowXml(row,tag)
	print '  </file>'		 
	
################
# main program #
################
	
filename = ''

if len(sys.argv) > 1:
	filename = sys.argv[1]
else:
	print 'Usage: csv2rawxml inputfile'
	exit()
	
print '<?xml version="1.0" encoding="UTF-8"?>'
print '<fileset>'
outputFileXml(filename)
print '</fileset>'
