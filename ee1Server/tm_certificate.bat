rem @echo off
rem batch file for generating an SSL certificate for TOMCAT
rem not very nice, improvement wanted
rem
cd C:\inetpub\ftproot\ee1Server
call ee_environment.bat
set TOUTPUT1=tm_certificate.inp
call :input
rem call :generate
pause
goto end

:generate
rem On Windows Server 2012, line below does not work, even when running as administrator
rem "%JAVA_HOME%\bin\keytool" -genkey -alias tomcat -keyalg RSA  -keystore %CATALINA_BASE%\temp <%TOUTPUT1%
rem generated .keystore will be placed under C:\windows\<username>
rem if keystore exists, it must be deleted to regenerate this single key pair
"%JAVA_HOME%\bin\keytool" -genkey -alias tomcat -keyalg RSA <%TOUTPUT1%
goto end

:input

rem 
rem C:\inetpub\ftproot\ee1Server>"C:\apps\Java\jdk1.8.0_45\bin\keytool" -genkey -alias tomcat -keyalg RSA  -keystore C:\inetpub\ftproot\ee1Server\conf
rem Enter keystore password:
echo changeit> %TOUTPUT1%
rem Re-enter new password:
echo changeit>> %TOUTPUT1%
rem What is your first and last name?
rem   [Unknown]:  Arne Ton
echo Arne Ton>> %TOUTPUT1%
rem What is the name of your organizational unit?
rem   [Unknown]:  eeEmbedded
echo eeEmbedded>> %TOUTPUT1%
rem What is the name of your organization?
rem   [Unknown]:  Jotne EPM Technology AS
echo Jotne EPM Technology AS>> %TOUTPUT1%
rem What is the name of your City or Locality?
rem   [Unknown]:  Oslo
echo Oslo>> %TOUTPUT1%
rem What is the name of your State or Province?
rem   [Unknown]:  Norway
echo Norway>> %TOUTPUT1%
rem What is the two-letter country code for this unit?
rem   [Unknown]:  no
echo NO>> %TOUTPUT1%
rem Is CN=Arne Ton, OU=eeEmbedded, O=Jotne EPM Technology AS, L=Oslo, ST=Norway, C=no correct?
rem   [no]:  yes
echo yes>> %TOUTPUT1%
rem Enter key password for <tomcat>
rem         (RETURN if same as keystore password):
echo changeit>> %TOUTPUT1%
rem Re-enter new password:
echo changeit>> %TOUTPUT1%
rem keytool error: java.io.FileNotFoundException: C:\inetpub\ftproot\ee1Server\conf (Access is denied)
goto end

:end


