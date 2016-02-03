# eee-development

Repository contains files related to the eeEmbedded EDM BIM Model Server.  


* [documentation](documentation/README.md) is a collection of documents of various relevance and quality
* [ee1Server](ee1Server/README.md) is an image of the eeE EDM BIM Model Server installation (bim-api.xyz)
* [eeE-BIM-API](eeE-BIM-API/README.md) is a work version of the official [BIM-API](https://github.com/BIMit/ModelAPI)
* [eeE-IFC-API](eeE-IFC-API/README.md) contains description of the IFC services implemented by bim-api.xyz server
* [xampp](xampp/README.md) is an image of the apache front-end (proxy) used for bim-api.xyz server


<html>

<head>
<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
<meta name=Generator content="Microsoft Word 12 (filtered)">
<title>Jotne eeEmbedded BIM Model Server</title>
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;}
@font-face
	{font-family:"Arial Unicode MS";
	panose-1:2 11 6 4 2 2 2 2 2 4;}
@font-face
	{font-family:Cambria;
	panose-1:2 4 5 3 5 4 6 3 2 4;}
@font-face
	{font-family:Calibri;
	panose-1:2 15 5 2 2 2 4 3 2 4;}
@font-face
	{font-family:Tahoma;
	panose-1:2 11 6 4 3 5 4 4 2 4;}
@font-face
	{font-family:Verdana;
	panose-1:2 11 6 4 3 5 4 4 2 4;}
@font-face
	{font-family:"Arial Rounded MT Bold";
	panose-1:2 15 7 4 3 5 4 3 2 4;}
@font-face
	{font-family:"\@Arial Unicode MS";
	panose-1:2 11 6 4 2 2 2 2 2 4;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
h1
	{mso-style-link:"Heading 1 Char";
	margin-top:12.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:21.6pt;
	margin-bottom:.0001pt;
	text-indent:-21.6pt;
	line-height:115%;
	page-break-after:avoid;
	font-size:14.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;
	font-style:italic;}
h2
	{mso-style-link:"Heading 2 Char";
	margin-top:6.0pt;
	margin-right:6.5pt;
	margin-bottom:2.0pt;
	margin-left:28.8pt;
	text-indent:-28.8pt;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
h3
	{mso-style-link:"Heading 3 Char";
	margin-top:6.0pt;
	margin-right:6.5pt;
	margin-bottom:2.0pt;
	margin-left:36.0pt;
	text-align:justify;
	text-indent:-36.0pt;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
h4
	{mso-style-link:"Heading 4 Char";
	margin-top:6.0pt;
	margin-right:6.5pt;
	margin-bottom:2.0pt;
	margin-left:43.2pt;
	text-indent:-43.2pt;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
h5
	{mso-style-link:"Heading 5 Char";
	margin-top:6.0pt;
	margin-right:6.5pt;
	margin-bottom:2.0pt;
	margin-left:50.4pt;
	text-indent:-50.4pt;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
h6
	{mso-style-link:"Heading 6 Char";
	margin-top:6.0pt;
	margin-right:6.5pt;
	margin-bottom:2.0pt;
	margin-left:57.6pt;
	text-indent:-57.6pt;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoHeading7, li.MsoHeading7, div.MsoHeading7
	{mso-style-link:"Heading 7 Char";
	margin-top:6.0pt;
	margin-right:6.5pt;
	margin-bottom:2.0pt;
	margin-left:64.8pt;
	text-indent:-64.8pt;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoHeading8, li.MsoHeading8, div.MsoHeading8
	{mso-style-link:"Heading 8 Char";
	margin-top:6.0pt;
	margin-right:6.5pt;
	margin-bottom:2.0pt;
	margin-left:72.0pt;
	text-indent:-72.0pt;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoHeading9, li.MsoHeading9, div.MsoHeading9
	{mso-style-link:"Heading 9 Char";
	margin-top:6.0pt;
	margin-right:6.5pt;
	margin-bottom:2.0pt;
	margin-left:79.2pt;
	text-indent:-79.2pt;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoIndex1, li.MsoIndex1, div.MsoIndex1
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:10.0pt;
	margin-bottom:.0001pt;
	text-indent:-10.0pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoIndex2, li.MsoIndex2, div.MsoIndex2
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:20.0pt;
	margin-bottom:.0001pt;
	text-indent:-10.0pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoIndex3, li.MsoIndex3, div.MsoIndex3
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:30.0pt;
	margin-bottom:.0001pt;
	text-indent:-10.0pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoIndex4, li.MsoIndex4, div.MsoIndex4
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:40.0pt;
	margin-bottom:.0001pt;
	text-indent:-10.0pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoIndex5, li.MsoIndex5, div.MsoIndex5
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:50.0pt;
	margin-bottom:.0001pt;
	text-indent:-10.0pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoIndex6, li.MsoIndex6, div.MsoIndex6
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:60.0pt;
	margin-bottom:.0001pt;
	text-indent:-10.0pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoIndex7, li.MsoIndex7, div.MsoIndex7
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:70.0pt;
	margin-bottom:.0001pt;
	text-indent:-10.0pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoIndex8, li.MsoIndex8, div.MsoIndex8
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:80.0pt;
	margin-bottom:.0001pt;
	text-indent:-10.0pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoIndex9, li.MsoIndex9, div.MsoIndex9
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:90.0pt;
	margin-bottom:.0001pt;
	text-indent:-10.0pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoToc1, li.MsoToc1, div.MsoToc1
	{margin-top:5.0pt;
	margin-right:0cm;
	margin-bottom:5.0pt;
	margin-left:18.0pt;
	page-break-after:avoid;
	font-size:12.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoToc2, li.MsoToc2, div.MsoToc2
	{margin-top:0cm;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:36.0pt;
	margin-bottom:.0001pt;
	font-size:10.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoToc3, li.MsoToc3, div.MsoToc3
	{margin-top:0cm;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:54.0pt;
	margin-bottom:.0001pt;
	font-size:10.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoToc4, li.MsoToc4, div.MsoToc4
	{margin-top:0cm;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:72.0pt;
	margin-bottom:.0001pt;
	font-size:10.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoToc5, li.MsoToc5, div.MsoToc5
	{margin-top:10.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:30.0pt;
	margin-bottom:.0001pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoToc6, li.MsoToc6, div.MsoToc6
	{margin-top:10.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:40.0pt;
	margin-bottom:.0001pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoToc7, li.MsoToc7, div.MsoToc7
	{margin-top:10.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:50.0pt;
	margin-bottom:.0001pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoToc8, li.MsoToc8, div.MsoToc8
	{margin-top:10.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:60.0pt;
	margin-bottom:.0001pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoToc9, li.MsoToc9, div.MsoToc9
	{margin-top:10.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:70.0pt;
	margin-bottom:.0001pt;
	font-size:9.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoFootnoteText, li.MsoFootnoteText, div.MsoFootnoteText
	{mso-style-link:"Footnote Text Char";
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	font-size:10.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoCommentText, li.MsoCommentText, div.MsoCommentText
	{mso-style-link:"Comment Text Char";
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	font-size:10.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoHeader, li.MsoHeader, div.MsoHeader
	{mso-style-link:"Header Char";
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	text-align:right;
	font-size:16.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;
	font-style:italic;}
p.MsoFooter, li.MsoFooter, div.MsoFooter
	{mso-style-link:"Footer Char";
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	text-align:right;
	font-size:10.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoIndexHeading, li.MsoIndexHeading, div.MsoIndexHeading
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	font-size:11.0pt;
	font-family:"Cambria","serif";
	font-weight:bold;}
p.MsoCaption, li.MsoCaption, div.MsoCaption
	{margin-top:6.0pt;
	margin-right:6.5pt;
	margin-bottom:6.0pt;
	margin-left:5.75pt;
	text-align:center;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoTof, li.MsoTof, div.MsoTof
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
span.MsoFootnoteReference
	{vertical-align:super;}
p.MsoList, li.MsoList, div.MsoList
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:2.4pt;
	margin-left:18.0pt;
	text-indent:-18.0pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoListBullet, li.MsoListBullet, div.MsoListBullet
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:2.4pt;
	margin-left:18.0pt;
	text-indent:-18.0pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoList2, li.MsoList2, div.MsoList2
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:2.4pt;
	margin-left:36.0pt;
	text-indent:-18.0pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoList3, li.MsoList3, div.MsoList3
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:42.45pt;
	margin-bottom:.0001pt;
	text-indent:-14.15pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoListBullet2, li.MsoListBullet2, div.MsoListBullet2
	{margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:0cm;
	margin-left:36.0pt;
	margin-bottom:.0001pt;
	text-indent:-18.0pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoTitle, li.MsoTitle, div.MsoTitle
	{mso-style-link:"Title Char";
	margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:3.0pt;
	margin-left:5.75pt;
	font-size:20.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoBodyText, li.MsoBodyText, div.MsoBodyText
	{mso-style-link:"Body Text Char";
	margin-top:4.0pt;
	margin-right:0cm;
	margin-bottom:6.0pt;
	margin-left:0cm;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoBodyTextIndent, li.MsoBodyTextIndent, div.MsoBodyTextIndent
	{mso-style-link:"Body Text Indent Char";
	margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:6.0pt;
	margin-left:18.0pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoSubtitle, li.MsoSubtitle, div.MsoSubtitle
	{mso-style-link:"Subtitle Char";
	margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:3.0pt;
	margin-left:5.65pt;
	font-size:16.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoBodyTextFirstIndent2, li.MsoBodyTextFirstIndent2, div.MsoBodyTextFirstIndent2
	{mso-style-link:"Body Text First Indent 2 Char";
	margin-top:0cm;
	margin-right:0cm;
	margin-bottom:6.0pt;
	margin-left:14.15pt;
	text-indent:10.5pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
a:link, span.MsoHyperlink
	{color:blue;
	text-decoration:underline;}
a:visited, span.MsoHyperlinkFollowed
	{color:purple;
	text-decoration:underline;}
em
	{text-decoration:none none;}
p.MsoDocumentMap, li.MsoDocumentMap, div.MsoDocumentMap
	{mso-style-link:"Document Map Char";
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	background:navy;
	font-size:10.0pt;
	font-family:"Tahoma","sans-serif";}
p
	{margin-right:0cm;
	margin-left:0cm;
	font-size:12.0pt;
	font-family:"Arial Unicode MS","sans-serif";}
p.MsoCommentSubject, li.MsoCommentSubject, div.MsoCommentSubject
	{mso-style-link:"Comment Subject Char";
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	font-size:10.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoAcetate, li.MsoAcetate, div.MsoAcetate
	{mso-style-link:"Balloon Text Char";
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	font-size:8.0pt;
	font-family:"Tahoma","sans-serif";}
p.MsoNoSpacing, li.MsoNoSpacing, div.MsoNoSpacing
	{margin-top:0cm;
	margin-right:0cm;
	margin-bottom:6.0pt;
	margin-left:0cm;
	text-align:justify;
	font-size:16.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoRMPane, li.MsoRMPane, div.MsoRMPane
	{margin:0cm;
	margin-bottom:.0001pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:10.0pt;
	margin-left:36.0pt;
	line-height:115%;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
span.MsoIntenseEmphasis
	{font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.MsoBibliography, li.MsoBibliography, div.MsoBibliography
	{margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.MsoTocHeading, li.MsoTocHeading, div.MsoTocHeading
	{margin-top:24.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	line-height:115%;
	page-break-after:avoid;
	font-size:14.0pt;
	font-family:"Cambria","serif";
	color:#365F91;
	font-weight:bold;
	font-style:italic;}
span.Heading1Char
	{mso-style-name:"Heading 1 Char";
	mso-style-link:"Heading 1";
	font-family:"Calibri","sans-serif";
	font-weight:bold;
	font-style:italic;}
span.Heading2Char
	{mso-style-name:"Heading 2 Char";
	mso-style-link:"Heading 2";
	font-family:"Cambria","serif";
	color:#4F81BD;
	font-weight:bold;}
span.Heading3Char
	{mso-style-name:"Heading 3 Char";
	mso-style-link:"Heading 3";
	font-family:"Cambria","serif";
	color:#4F81BD;
	font-weight:bold;}
span.Heading4Char
	{mso-style-name:"Heading 4 Char";
	mso-style-link:"Heading 4";
	font-family:"Cambria","serif";
	color:#4F81BD;
	font-weight:bold;
	font-style:italic;}
span.Heading5Char
	{mso-style-name:"Heading 5 Char";
	mso-style-link:"Heading 5";
	font-family:"Cambria","serif";
	color:#243F60;}
span.Heading6Char
	{mso-style-name:"Heading 6 Char";
	mso-style-link:"Heading 6";
	font-family:"Cambria","serif";
	color:#243F60;
	font-style:italic;}
span.Heading7Char
	{mso-style-name:"Heading 7 Char";
	mso-style-link:"Heading 7";
	font-family:"Cambria","serif";
	color:#404040;
	font-style:italic;}
span.Heading8Char
	{mso-style-name:"Heading 8 Char";
	mso-style-link:"Heading 8";
	font-family:"Cambria","serif";
	color:#404040;}
span.Heading9Char
	{mso-style-name:"Heading 9 Char";
	mso-style-link:"Heading 9";
	font-family:"Cambria","serif";
	color:#404040;
	font-style:italic;}
span.FootnoteTextChar
	{mso-style-name:"Footnote Text Char";
	mso-style-link:"Footnote Text";
	font-family:"Calibri","sans-serif";}
span.CommentTextChar
	{mso-style-name:"Comment Text Char";
	mso-style-link:"Comment Text";
	font-family:"Arial","sans-serif";}
span.HeaderChar
	{mso-style-name:"Header Char";
	mso-style-link:Header;
	font-family:"Calibri","sans-serif";}
span.FooterChar
	{mso-style-name:"Footer Char";
	mso-style-link:Footer;
	font-family:"Calibri","sans-serif";}
span.TitleChar
	{mso-style-name:"Title Char";
	mso-style-link:Title;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
span.BodyTextChar
	{mso-style-name:"Body Text Char";
	mso-style-link:"Body Text";
	font-family:"Arial","sans-serif";}
span.BodyTextIndentChar
	{mso-style-name:"Body Text Indent Char";
	mso-style-link:"Body Text Indent";
	font-family:"Arial","sans-serif";}
span.SubtitleChar
	{mso-style-name:"Subtitle Char";
	mso-style-link:Subtitle;
	font-family:"Cambria","serif";
	color:#4F81BD;
	letter-spacing:.75pt;
	font-style:italic;}
span.BodyTextFirstIndent2Char
	{mso-style-name:"Body Text First Indent 2 Char";
	mso-style-link:"Body Text First Indent 2";
	font-family:"Arial","sans-serif";}
span.DocumentMapChar
	{mso-style-name:"Document Map Char";
	mso-style-link:"Document Map";
	font-family:"Tahoma","sans-serif";}
span.CommentSubjectChar
	{mso-style-name:"Comment Subject Char";
	mso-style-link:"Comment Subject";
	font-family:"Arial","sans-serif";
	font-weight:bold;}
span.BalloonTextChar
	{mso-style-name:"Balloon Text Char";
	mso-style-link:"Balloon Text";
	font-family:"Tahoma","sans-serif";}
p.msolist3cxspfirst, li.msolist3cxspfirst, div.msolist3cxspfirst
	{mso-style-name:msolist3cxspfirst;
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:42.45pt;
	margin-bottom:.0001pt;
	text-indent:-14.15pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.msolist3cxspmiddle, li.msolist3cxspmiddle, div.msolist3cxspmiddle
	{mso-style-name:msolist3cxspmiddle;
	margin-top:0cm;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:42.45pt;
	margin-bottom:.0001pt;
	text-indent:-14.15pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.msolist3cxsplast, li.msolist3cxsplast, div.msolist3cxsplast
	{mso-style-name:msolist3cxsplast;
	margin-top:0cm;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:42.45pt;
	margin-bottom:.0001pt;
	text-indent:-14.15pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.msolistparagraphcxspfirst, li.msolistparagraphcxspfirst, div.msolistparagraphcxspfirst
	{mso-style-name:msolistparagraphcxspfirst;
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:36.0pt;
	margin-bottom:.0001pt;
	line-height:115%;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.msolistparagraphcxspmiddle, li.msolistparagraphcxspmiddle, div.msolistparagraphcxspmiddle
	{mso-style-name:msolistparagraphcxspmiddle;
	margin-top:0cm;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:36.0pt;
	margin-bottom:.0001pt;
	line-height:115%;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.msolistparagraphcxsplast, li.msolistparagraphcxsplast, div.msolistparagraphcxsplast
	{mso-style-name:msolistparagraphcxsplast;
	margin-top:0cm;
	margin-right:0cm;
	margin-bottom:10.0pt;
	margin-left:36.0pt;
	line-height:115%;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.Example, li.Example, div.Example
	{mso-style-name:Example;
	margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:0cm;
	margin-left:1.0cm;
	margin-bottom:.0001pt;
	font-size:9.0pt;
	font-family:"Courier New";
	color:green;}
p.Preformatted, li.Preformatted, div.Preformatted
	{mso-style-name:Preformatted;
	margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:0cm;
	margin-left:5.75pt;
	margin-bottom:.0001pt;
	font-size:11.0pt;
	font-family:"Courier New";}
p.TipNoteHeading, li.TipNoteHeading, div.TipNoteHeading
	{mso-style-name:"Tip\/Note Heading";
	margin-top:6.0pt;
	margin-right:6.5pt;
	margin-bottom:0cm;
	margin-left:5.75pt;
	margin-bottom:.0001pt;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.TipNoteText, li.TipNoteText, div.TipNoteText
	{mso-style-name:"Tip\/Note Text";
	margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:0cm;
	margin-left:15.1pt;
	margin-bottom:.0001pt;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";}
p.TipNoteTextBulleted, li.TipNoteTextBulleted, div.TipNoteTextBulleted
	{mso-style-name:"Tip\/Note Text Bulleted";
	margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:0cm;
	margin-left:15.1pt;
	margin-bottom:.0001pt;
	text-indent:-9.35pt;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";}
p.TopicTextBulleted, li.TopicTextBulleted, div.TopicTextBulleted
	{mso-style-name:"Topic Text Bulleted";
	margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:0cm;
	margin-left:15.1pt;
	margin-bottom:.0001pt;
	text-indent:-15.1pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
p.TopicTextIndent, li.TopicTextIndent, div.TopicTextIndent
	{mso-style-name:"Topic Text Indent";
	margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:0cm;
	margin-left:15.1pt;
	margin-bottom:.0001pt;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";}
p.TopicTextNumbered, li.TopicTextNumbered, div.TopicTextNumbered
	{mso-style-name:"Topic Text Numbered";
	margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:0cm;
	margin-left:15.1pt;
	margin-bottom:.0001pt;
	text-indent:-9.35pt;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";}
p.TopicTextOnestep, li.TopicTextOnestep, div.TopicTextOnestep
	{mso-style-name:"Topic Text Onestep";
	margin-top:4.0pt;
	margin-right:6.5pt;
	margin-bottom:0cm;
	margin-left:15.1pt;
	margin-bottom:.0001pt;
	text-indent:-9.35pt;
	font-size:8.0pt;
	font-family:"Calibri","sans-serif";}
p.Code, li.Code, div.Code
	{mso-style-name:Code;
	margin:0cm;
	margin-bottom:.0001pt;
	font-size:12.0pt;
	font-family:"Courier New";}
p.action, li.action, div.action
	{mso-style-name:action;
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:76.55pt;
	margin-bottom:.0001pt;
	text-indent:-76.55pt;
	font-size:10.0pt;
	font-family:"Verdana","sans-serif";}
p.TableHeader, li.TableHeader, div.TableHeader
	{mso-style-name:"Table Header";
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	font-size:11.0pt;
	font-family:"Arial Rounded MT Bold","sans-serif";}
p.StyleJustifiedAfter6pt, li.StyleJustifiedAfter6pt, div.StyleJustifiedAfter6pt
	{mso-style-name:"Style Justified After\:  6 pt";
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:54.0pt;
	margin-bottom:.0001pt;
	text-indent:-18.0pt;
	font-size:11.0pt;
	font-family:"Calibri","sans-serif";}
span.EKommentarChar
	{mso-style-name:"EKommentar Char";
	mso-style-link:EKommentar;
	font-family:"Cambria","serif";
	color:red;
	font-style:italic;}
p.EKommentar, li.EKommentar, div.EKommentar
	{mso-style-name:EKommentar;
	mso-style-link:"EKommentar Char";
	margin-top:6.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	font-size:11.0pt;
	font-family:"Cambria","serif";
	color:red;
	font-style:italic;}
span.AutorZchn
	{mso-style-name:"Autor Zchn";
	mso-style-link:Autor;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.Autor, li.Autor, div.Autor
	{mso-style-name:Autor;
	mso-style-link:"Autor Zchn";
	margin-top:30.0pt;
	margin-right:0cm;
	margin-bottom:0cm;
	margin-left:0cm;
	margin-bottom:.0001pt;
	text-align:center;
	line-height:115%;
	font-size:12.0pt;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
p.msochpdefault, li.msochpdefault, div.msochpdefault
	{mso-style-name:msochpdefault;
	margin-right:0cm;
	margin-left:0cm;
	font-size:10.0pt;
	font-family:"Arial Unicode MS","sans-serif";}
span.Typewriter
	{mso-style-name:Typewriter;
	font-family:"Courier New";}
span.apple-converted-space
	{mso-style-name:apple-converted-space;}
span.hps
	{mso-style-name:hps;}
span.bold
	{mso-style-name:bold;
	font-family:"Calibri","sans-serif";
	font-weight:bold;}
.MsoChpDefault
	{font-size:10.0pt;}
@page WordSection1
	{size:21.0cm 842.0pt;
	margin:72.0pt 2.0cm 2.0cm 77.95pt;}
div.WordSection1
	{page:WordSection1;}
 /* List Definitions */
 ol
	{margin-bottom:0cm;}
ul
	{margin-bottom:0cm;}
-->
</style>

</head>

<body lang=NO-BOK link=blue vlink=purple>

<div class=WordSection1>

<p class=MsoNoSpacing align=left style='text-align:left'><span
lang=EN-US style='font-size:26.0pt'>eeEmbedded BIM Model Server (bim-api.xyz)</span></p>

<h2><span lang=EN-US>eeEmbedded:</span></h2>

<p class=MsoNormal><span lang=EN-US>&nbsp;</span></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0
 style='border-collapse:collapse'>
 <tr>
  <td width=415 style='width:311.45pt;border:solid windowtext 1.0pt;padding:
  0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span class=MsoIntenseEmphasis><span lang=EN-US
  style='font-size:12.0pt'>OPTIMISED DESIGN METHODOLOGIES FOR ENERGY-EFFICIENT
  BUILDINGS INTEGRATED IN THE NEIGHBOURHOOD ENERGY SYSTEMS</span></span></p>
  </td>
  <td width=180 style='width:134.7pt;border:solid windowtext 1.0pt;border-left:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><img width=97 height=76 src="index_files/image002.gif"
  align=left hspace=12></p>
  </td>
 </tr>
 <tr>
  <td width=415 style='width:311.45pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US style='font-size:10.0pt'>The project has
  received funding from the European Union Seventh Framework Programme under
  grant agreement n� 609349</span></p>
  </td>
  <td width=180 style='width:134.7pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><img width=72 height=49 src="index_files/image001.jpg"
  align=left hspace=12></p>
  </td>
 </tr>
 <tr>
  <td width=415 style='width:311.45pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b><span lang=EN-US style='font-size:12.0pt'>Server built
  upon upon EDMModelServer � from </span></b></p>
  </td>
  <td width=180 style='width:134.7pt;border-top:none;border-left:none;
  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><img width=157 height=44 src="index_files/image003.gif"></p>
  </td>
 </tr>
 <tr>
  <td width=415 style='width:311.45pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><b><span lang=EN-US style='font-size:12.0pt'>Server
  powered by</span></b></p>
  </td>
  <td width=180 valign=top style='width:134.7pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><img width=153 height=52 src="index_files/image004.jpg"></p>
  </td>
 </tr>
</table>

<p class=Autor align=left style='margin-top:6.0pt;margin-right:0cm;margin-bottom:
6.0pt;margin-left:0cm;text-align:left'><span class=bold><span lang=EN-US
style='font-size:9.0pt;line-height:115%'>Version Timestamp</span></span><span
class=bold><span lang=EN-US style='font-size:9.0pt;line-height:115%;font-weight:
normal'>:&nbsp; 1.0 / 02.02.2016&nbsp; </span></span><span class=bold><span
lang=EN-US style='font-size:9.0pt;line-height:115%'>Nature:&nbsp; </span></span><span
class=bold><span lang=EN-US style='font-size:9.0pt;line-height:115%;font-weight:
normal'>Preliminary</span></span></p>

<p class=MsoNormal style='margin-top:0cm;text-align:justify'><span lang=EN-US>&nbsp;</span></p>

<p class=MsoNormal style='margin-top:0cm;text-align:justify'><b><i><span
lang=EN-US>eeEmbedded</span></i></b><span lang=EN-US> </span><span lang=EN-GB
style='font-size:10.0pt'>Project Coordinator: R. J. Scherer, Institute for
Construction Informatics, Technische Universit�t Dresden, Germany</span></p>

<p class=MsoNormal style='margin-bottom:4.0pt'><span lang=EN-US>&nbsp;</span></p>

<h3><a name="_Toc422482926"></a><a name="_Toc374097651"></a><span lang=EN-US>&nbsp;</span></h3>

<h3><span lang=EN-US>Executive Summary</span></h3>

<p class=MsoNormal><span lang=EN-US>The server contains web applications and
documents related to the EDMmodelServer� from Jotne provides as one of the BIM
Model Servers in the eeEmbedded project. &nbsp;The server is provided as an
server instance available for the duration of the project, and not as a
product. </span></p>

<p class=MsoNormal style='margin-bottom:4.0pt'><span lang=EN-US>Partners has
contributed from their expert viewpoint as follows:</span></p>

<p class=MsoListParagraph style='margin-top:0cm;margin-right:0cm;margin-bottom:
3.0pt;margin-left:2.0cm;text-align:justify;text-indent:-2.0cm;line-height:normal'><span
lang=EN-US style='font-family:Symbol'>�</span><span lang=EN-US
style='font-size:7.0pt;font-family:"Times New Roman","serif"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span><b><span lang=EN-US>EPM:</span></b><span lang=EN-US>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Responsible for providing necessary hardware,
software and supporting components to make the server available.</span></p>

<p class=MsoListParagraph style='margin-top:0cm;margin-right:0cm;margin-bottom:
3.0pt;margin-left:36.0pt;text-align:justify;text-indent:-36.0pt;line-height:
normal'><span lang=EN-US style='font-family:Symbol'>�</span><span lang=EN-US
style='font-size:7.0pt;font-family:"Times New Roman","serif"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span><b><span lang=EN-US>NEM:&nbsp;&nbsp;&nbsp;&nbsp; </span></b><span
lang=EN-US>Specification of the eeE BIM API implemented on the server</span></p>

<p class=MsoListParagraph style='margin-top:0cm;margin-right:0cm;margin-bottom:
3.0pt;margin-left:36.0pt;text-align:justify;text-indent:-36.0pt;line-height:
normal'><span lang=EN-US style='font-family:Symbol'>�</span><span lang=EN-US
style='font-size:7.0pt;font-family:"Times New Roman","serif"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span><b><span lang=EN-US>IAB: </span></b><span lang=EN-US>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Specification
of the eeE BIM API implemented on the server</span></p>

<p class=MsoListParagraph style='margin-top:0cm;margin-right:0cm;margin-bottom:
3.0pt;margin-left:36.0pt;text-align:justify;text-indent:-36.0pt;line-height:
normal'><span lang=EN-US style='font-family:Symbol'>�</span><span lang=EN-US
style='font-size:7.0pt;font-family:"Times New Roman","serif"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span><b><span lang=EN-US>CIB:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;</span></b><span
lang=EN-US>Input to specification and implementation</span></p>

<p class=MsoListParagraph style='margin-top:0cm;margin-right:0cm;margin-bottom:
3.0pt;margin-left:36.0pt;text-align:justify;text-indent:-36.0pt;line-height:
normal'><span style='font-family:Symbol'>�</span><span lang=EN-US
style='font-size:7.0pt;font-family:"Times New Roman","serif"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span><b><span lang=EN-US>XXX:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></b><span
lang=EN-US>contributions to parts xxx</span></p>

<p class=MsoListParagraph style='margin-top:0cm;margin-right:0cm;margin-bottom:
3.0pt;margin-left:36.0pt;text-align:justify;text-indent:-36.0pt;line-height:
normal'><span lang=EN-US style='font-family:Symbol'>�</span><span lang=EN-US
style='font-size:7.0pt;font-family:"Times New Roman","serif"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span><b><span lang=EN-US>ALL Partners</span></b><span lang=EN-US>: Providing
feedback and evaluation</span></p>

<p class=MsoNormal style='text-autospace:none'><span lang=EN-GB
style='font-size:12.0pt;font-family:"Times New Roman","serif"'>&nbsp;</span></p>

<p class=MsoNormal><em><span lang=EN-US style='font-size:8.0pt'>&nbsp;</span></em></p>

<p class=MsoNormal><em><span lang=EN-US style='font-size:8.0pt'>Copyright</span></em></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt;letter-spacing:-.1pt'>This
report is � <i>ee</i>Embedded Consortium 2014. Its duplication is restricted to
the personal use within the consortium, the funding agency and the project
reviewers. Its duplication is allowed in its integral form only for anyone's
personal use for the purposes of research or education.</span></p>

<p class=MsoNormal style='margin-top:2.0pt;margin-right:0cm;margin-bottom:2.0pt;
margin-left:0cm'><a name="_Toc374097652"><em><span lang=EN-US style='font-size:
8.0pt'>Citation</span></em></a></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt;letter-spacing:-.1pt'>eeE-D044
(2016); <i>ee</i>Embedded DeliverableD4.4: Jotne </span><span lang=EN-US
style='font-size:8.0pt'>BIM Model Server, <span style='letter-spacing:-.1pt'>� <i>ee</i>Embedded
Consortium, Brussels.</span></span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:12.0pt;letter-spacing:
-.1pt'>&nbsp;</span></p>

<p class=MsoNormal><a name="_Toc374097653"><em><span lang=EN-US
style='font-size:8.0pt'>Acknowledgements</span></em></a></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt'>The work presented
on this server has been conducted in the context of the seventh framework
programme of the European community project <i>ee</i>Embedded (n� 609349). <i>ee</i>Embedded
is a 48 month project that started in October 2013 and is funded by the
European Com�mission as well as by the industrial partners. Their support is
gratefully appreciated. The partners in the project are Technische Universit�t
Dresden (Germany), Fraunhofer-Gesellschaft zur F�rderung der angewandten
Forschung E.V (Germany), &nbsp;NEMETSCHEK Slovensko, S.R.O. (Slovakia), Data
Design System ASA (Norway), RIB Information Technologies AG (Germany), Jotne
EPM Technology AS (Norway), Granlund OY (Finland), SOFISTIK HELLAS AE (Greece),
Institute for applied Building Informatics IABI (Germany), FR. SAUTER AG
(Switzerland), , Obermeyer Planen + Beraten (Germany), Centro de Estudios
Materiales y Control de Obras S.A. (Spain), STRABAG AG (Austria) and
Koninklijke BAM Group NV (The Netherlands). This report owes to a collaborative
effort of the above organizations.</span></p>

<p class=MsoNormal><span lang=EN-US style='font-size:8.0pt'>&nbsp;</span></p>

<p class=MsoNormal style='margin-top:0cm;text-align:justify'><span lang=EN-US>&nbsp;</span></p>

</div>

</body>

</html>




  

