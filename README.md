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




  

