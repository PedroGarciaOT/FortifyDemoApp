# Fortify Injection Demo

This is an example project for the demonstration of Fortify SAST and DAST vulnerability scanning. In particular
this application has various injection vulnerabilities that can be found by Fortify

To use this demo in full you will need the following software installed:

* [Fortify SCA and Tools](https://www.microfocus.com/en-us/cyberres/application-security/static-code-analyzer)
* [Fortify WebInspect](https://www.microfocus.com/en-us/cyberres/application-security/webinspect)  

Setup
-----

First create a file called `.env` in the project root directory with content similar to the following:

```
# The URL of Software Security Center
SSC_URL=http://ftfydemo:8080/ssc
SSC_USERNAME=admin
SSC_PASSWORD=admin
# SSC Authentication Token (recommended to use CIToken)
SSC_AUTH_TOKEN=XXXXX
# Name of the application in SSC
SSC_APP_NAME=FortifyInjectionDemo
# Name of the application version in SSC
SSC_APP_VER_NAME=main
```

If you want to upload the results to Fortify Software Security Center, make sure you set
appropriate values for `SSC_URL` and `SSC_AUTH_TOKEN`.

Security Scan
-------------

To run a Fortify Static Code Analyzer scan you can use the included script `fortify-sca.ps1` as follows:

```
powershell .\bin\fortify-sca.ps1
```

This will scan the applications source code.

An example results file (in PDF) is available [here](samples/FortifyWebAppDemo.pdf).

To view the full results yourself you can use:

```
auditworkbench .\FortifyInjectionDemo.fpr
```

There should be a number of issues including (but not limited to):

- TBD

Run Application (locally)
-------------------------

You can the run the application using the following:

```
.\gradlew.bat bootRun
```

The application should then be available at the URL: `http://localhost:8088`. If it fails to start make sure you have
no other applications running on port 8088.


Deploy Application
------------------

TBD

---

Kevin A. Lee (kadraman) - kevin.lee@microfocus.com
